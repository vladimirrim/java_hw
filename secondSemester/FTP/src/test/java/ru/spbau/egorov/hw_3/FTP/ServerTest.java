package ru.spbau.egorov.hw_3.FTP;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServerTest {
    private static String hostName;
    private static Server server;
    private String lineSeparator = System.getProperty("line.separator");

    @BeforeAll
    static void setUp() throws InterruptedException, IOException {
        server = new Server(8888);
        new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        sleep(100);
        hostName = server.getHostName();
        if (!Files.exists(Paths.get("./testDirs/dir1/nothing")))
            Files.createDirectory(Paths.get("./testDirs/dir1/nothing"));
    }

    @Test
    void serverOnGetRequest() throws IOException, InvalidProtocolException {
        Server localServer = new Server(8887);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        String ansGet = "jojo";
        DataOutputStream dos = new DataOutputStream(bais);
        dos.writeInt("./testDirs/file.txt".length());
        dos.write("./testDirs/file.txt".getBytes());
        localServer.onGetRequest(new DataOutputStream(baos), new DataInputStream(new ByteArrayInputStream(bais.toByteArray())));
        assertEquals(ansGet, baos.toString());
    }

    @Test
    void serverOnListRequest() throws IOException, InvalidProtocolException {
        Server localServer = new Server(8886);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bais);
        dos.writeInt("./testDirs/file.txt".length());
        dos.write("./testDirs/file.txt".getBytes());
        localServer.onListRequest(new DataOutputStream(baos), new DataInputStream(new ByteArrayInputStream(bais.toByteArray())));
        assertEquals("Number of files in directory ./testDirs/file.txt: 0" + lineSeparator, baos.toString());
    }

    @Test
    void onGetRequestEmptyPathNameExceptions() throws InterruptedException {
        Client client = new Client(hostName, 8888);
        assertThrows(EOFException.class, () -> client.get("", new ByteArrayOutputStream()));
        sleep(100);
        assertEquals(InvalidProtocolException.class, server.getExceptionsHistory().get(server.getExceptionsHistory().size() - 1).getClass());
    }

    @Test
    void onListRequestEmptyPathNameExceptions() throws InterruptedException {
        Client client = new Client(hostName, 8888);
        assertThrows(EOFException.class, () -> client.list("", new ByteArrayOutputStream()));
        sleep(100);
        assertEquals(InvalidProtocolException.class, server.getExceptionsHistory().get(server.getExceptionsHistory().size() - 1).getClass());
    }

    @Test
    void onListRequestNotADirectory() throws IOException, InvalidProtocolException {
        Client client = new Client(hostName, 8888);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        client.list("./testDirs/file.txt", stream);
        assertEquals("Number of files in directory ./testDirs/file.txt: 0" + lineSeparator, stream.toString());
    }

    @Test
    void oneGetAndOneListRequests() throws IOException, InvalidProtocolException {
        Client client = new Client(hostName, 8888);
        String ansList = "Number of files in directory ./testDirs: 2" + lineSeparator +
                "name: dir1 isDirectory: true" + lineSeparator +
                "name: file.txt isDirectory: false" + lineSeparator;
        String ansGet = "jojo";
        ByteArrayOutputStream[] streams = new ByteArrayOutputStream[2];
        streams[0] = new ByteArrayOutputStream();
        client.list("./testDirs", streams[0]);
        streams[1] = new ByteArrayOutputStream();
        client.get("./testDirs/file.txt", streams[1]);
        assertEquals(ansGet, streams[1].toString());
        assertEquals(ansList, streams[0].toString());
    }

    @Test
    void onListRequestTenRequests() throws IOException, InvalidProtocolException {
        Client client = new Client(hostName, 8888);
        String ans = "Number of files in directory ./testDirs: 2" + lineSeparator +
                "name: dir1 isDirectory: true" + lineSeparator +
                "name: file.txt isDirectory: false" + lineSeparator;
        ByteArrayOutputStream[] streams = new ByteArrayOutputStream[10];
        for (int i = 0; i < 10; i++) {
            streams[i] = new ByteArrayOutputStream();
            client.list("./testDirs", streams[i]);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(ans, streams[i].toString());
        }
    }

    @Test
    void onListRequestEmptyDirectory() throws IOException, InvalidProtocolException {
        Client client = new Client(hostName, 8888);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        client.list("./testDirs/dir1/nothing", stream);
        assertEquals("Number of files in directory ./testDirs/dir1/nothing: 0" + lineSeparator, stream.toString());
    }

    @Test
    void onGetRequestTenRequests() throws IOException, InvalidProtocolException {
        Client client = new Client(hostName, 8888);
        ByteArrayOutputStream[] streams = new ByteArrayOutputStream[10];
        String ans = "jojo";
        for (int i = 0; i < 10; i++) {
            streams[i] = new ByteArrayOutputStream();
            client.get("./testDirs/file.txt", streams[i]);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(ans, streams[i].toString());
        }
    }

}