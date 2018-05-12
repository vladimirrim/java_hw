package ru.spbau.egorov.hw_4.FTPGUI.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * This class implements list and get requests to FTPGUI server.
 */
public class Client {
    private String hostName;
    private int port;

    public Client(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
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
            outputStream.writeInt(1);
            outputStream.writeInt(path.getBytes().length);
            outputStream.write(path.getBytes());
            int size = inputStream.readInt();
            if (size < 0)
                throw new InvalidProtocolException();
            dos.write("Number of files in directory ".getBytes());
            dos.write(path.getBytes());
            dos.write(": ".getBytes());
            dos.write(Integer.toString(size).getBytes());
            dos.write(System.getProperty("line.separator").getBytes());
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
                dos.write(System.getProperty("line.separator").getBytes());
            }
        }
    }

    /**
     * Same as list function, writes to output stream only names of files in given directory.
     *
     * @param path is the given directory.
     * @param os   is the output stream.
     * @throws InvalidProtocolException if server`s answer doesn`t match the protocol.
     * @throws IOException              if there is problems with connection or output stream.
     */
    public void listNames(String path, OutputStream os) throws InvalidProtocolException, IOException {
        try (Socket socket = new Socket(hostName, port);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
             DataOutputStream dos = new DataOutputStream(os)) {
            outputStream.writeInt(1);
            outputStream.writeInt(path.getBytes().length);
            outputStream.write(path.getBytes());
            int size = inputStream.readInt();
            if (size < 0)
                throw new InvalidProtocolException();
            dos.writeInt(size);
            for (int i = 0; i < size; i++) {
                int stringSize = inputStream.readInt();
                if (stringSize <= 0)
                    throw new InvalidProtocolException();
                byte[] buffer = new byte[stringSize];
                inputStream.readFully(buffer);
                boolean isDir = inputStream.readBoolean();
                dos.writeInt(stringSize);
                dos.write(buffer);
                dos.writeBoolean(isDir);
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
            outputStream.writeInt(2);
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
