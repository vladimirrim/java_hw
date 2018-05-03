package ru.spbau.egorov.hw_3.FTP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This class implement FTP server that can handle get and list requests.
 */
public class Server {
    private volatile String hostName;
    private volatile boolean isRunning = true;
    private int port;
    private volatile ArrayList<Exception> exceptionsHistory = new ArrayList<>();

    public Server(int port) {
        this.port = port;
    }

    /**
     * Starts the server.It will listen for the connection and work with each client in new thread.
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            InetAddress address = serverSocket.getInetAddress();
            hostName = address.getHostName();
            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                Runnable worker = () -> {
                    try (DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                         DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream())) {
                        int type = inputStream.readInt();
                        if (type == 2) {
                            onGetRequest(outputStream, inputStream);
                        } else if (type == 1) {
                            onListRequest(outputStream, inputStream);
                        } else
                            throw new InvalidProtocolException();
                    } catch (IOException | InvalidProtocolException e) {
                        e.printStackTrace();
                        exceptionsHistory.add(e);
                    }
                };
                new Thread(worker).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Exception> getExceptionsHistory() {
        return new ArrayList<>(exceptionsHistory);
    }

    /**
     * Stops the server.
     */
    public void stop() {
        isRunning = false;
    }

    public String getHostName() {
        return hostName;
    }

    /**
     * Returns data written in specified file to the client.The protocol: <size:Long><contents:byte[]>
     *
     * @param dos is the output stream that will go to the client.
     * @param dis is the input stream from the client.
     * @throws IOException              if there are a connection problems or problems with specified file.
     * @throws InvalidProtocolException if the protocol from the client doesn`t match actual protocol.
     */
    private void onGetRequest(DataOutputStream dos, DataInputStream dis) throws IOException, InvalidProtocolException {
        int stringSize = dis.readInt();
        if (stringSize <= 0)
            throw new InvalidProtocolException();
        byte[] path = new byte[stringSize];
        dis.readFully(path);
        Path file = Paths.get(new String(path));
        if (!Files.exists(file) || Files.isDirectory(file)) {
            dos.writeLong(0);
        } else {
            dos.writeLong(Files.size(file));
            byte[] buffer = new byte[1024];
            try (InputStream is = Files.newInputStream(file)) {
                for (int read = is.read(buffer); read != -1; read = is.read(buffer)) {
                    dos.write(buffer, 0, read);
                }
            }
        }
    }

    /**
     * Returns all files in specified directory to the client.The protocol: <count:Int>(<filename:String><isDir:Boolean>)*
     *
     * @param dos is the output stream that will go to the client.
     * @param dis is the input stream from the client.
     * @throws IOException              if there are a connection problems or problems with specified directory.
     * @throws InvalidProtocolException if the protocol from the client doesn`t match actual protocol.
     */
    private void onListRequest(DataOutputStream dos, DataInputStream dis) throws IOException, InvalidProtocolException {
        int stringSize = dis.readInt();
        if (stringSize <= 0)
            throw new InvalidProtocolException();
        byte[] dirName = new byte[stringSize];
        dis.readFully(dirName);
        Path dir = Paths.get(new String(dirName));
        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            dos.writeInt(0);
        } else {
            ArrayList<Path> paths = Files.walk(dir, 1).collect(Collectors.toCollection(ArrayList::new));
            paths.remove(0);
            dos.writeInt(paths.size());
            for (Path path : paths) {
                dos.writeInt(path.getFileName().toString().getBytes().length);
                dos.write(path.getFileName().toString().getBytes());
                dos.writeBoolean(Files.isDirectory(Paths.get((path.getFileName().toString()))));
            }
        }
    }
}
