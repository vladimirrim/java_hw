package ru.spbau.egorov.hw_3.FTP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class implements list and get requests to FTP server.
 */
public class Client {
    private String hostName;
    private int port;

    public Client(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    /**
     * Runs client.All requests are printed in terminal.Print exit to stop the program.
     *
     * @param args are hostname and port of the server.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Wrong number of arguments.Expected 2(hostname, port).");
            return;
        }
        try {
            String hostname = args[0];
            int port = Integer.parseInt(args[1]);
            Client client = new Client(hostname, port);
            while (true) {
                Scanner s = new Scanner(System.in);
                String request = s.nextLine();
                String[] params = request.split(" ");
                if (params[0].equals("exit")) {
                    return;
                }
                if (params.length != 2) {
                    System.out.println("Wrong request format.Expected request type and ath to the file.");
                }
                try {
                    switch (params[0]) {
                        case "list":
                            client.list(params[1], System.out);
                            break;
                        case "get":
                            client.get(params[1], System.out);
                            break;
                        default:
                            System.out.println("Unknown request type.");
                            break;
                    }
                } catch (InvalidProtocolException | IOException e) {
                    System.out.println("Error occurred during your request.Error: " + e.getLocalizedMessage());
                }

            }
        } catch (NumberFormatException e) {
            System.out.println("Unable to parse port number.");
        }
    }

    /**
     * Requests all files in the specified directory.The protocol for request: <1:Int><path:String>
     *
     * @param path is the path to the directory.
     * @param os   is stream where the request result will be written.
     * @throws InvalidProtocolException if the protocol from server didn`t match with the actual protocol.
     * @throws IOException              if server didn`t respond correctly or the was connection problems.
     */
    public void list(String path, OutputStream os) throws InvalidProtocolException, IOException {
        try (Socket socket = new Socket(hostName, port);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
             DataOutputStream dos = new DataOutputStream(os)) {
            String sep = System.getProperty("line.separator");
            outputStream.writeInt(RequestType.LIST_REQUEST.getCode());
            outputStream.writeInt(path.getBytes().length);
            outputStream.write(path.getBytes());
            int size = inputStream.readInt();
            if (size < 0)
                throw new InvalidProtocolException();
            dos.write("Number of files in directory ".getBytes());
            dos.write(path.getBytes());
            dos.write(": ".getBytes());
            dos.write(Integer.toString(size).getBytes());
            dos.write(sep.getBytes());
            for (int i = 0; i < size; i++) {
                int stringSize = inputStream.readInt();
                if (stringSize <= 0)
                    throw new InvalidProtocolException();
                byte[] buffer = new byte[stringSize];
                inputStream.readFully(buffer);
                boolean isDir = inputStream.readBoolean();
                dos.write("name: ".getBytes());
                dos.write(buffer);
                dos.write(" isDirectory: ".getBytes());
                dos.write(Boolean.toString(isDir).getBytes());
                dos.write(sep.getBytes());
            }
        }
    }

    /**
     * Requests specified file content.The protocol for request: <2:Int><path:String>
     *
     * @param path is the path to the file.
     * @param os   is stream where the request result will be written.
     * @throws InvalidProtocolException if the answer from server didn`t match with the actual protocol.
     * @throws IOException              if server didn`t respond correctly or the was connection problems.
     */
    public void get(String path, OutputStream os) throws InvalidProtocolException, IOException {
        try (Socket socket = new Socket(hostName, port);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            outputStream.writeInt(RequestType.GET_REQUEST.getCode());
            outputStream.writeInt(path.getBytes().length);
            outputStream.write(path.getBytes());
            outputStream.flush();
            long size = inputStream.readLong();
            if (size < 0)
                throw new InvalidProtocolException();
            byte[] buffer = new byte[1024];
            if (size != 0) {
                for (int read = inputStream.read(buffer); read != -1; read = inputStream.read(buffer)) {
                    size -= read;
                    if (size < 0)
                        throw new InvalidProtocolException();
                    os.write(buffer, 0, read);
                }
                if (size > 0)
                    throw new InvalidProtocolException();
            }
        }
    }
}
