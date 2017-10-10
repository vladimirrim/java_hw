package ru.spbau.egorov.hw_4.zipfile;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class ZipFileTest {


    private void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        directory.delete();
    }

    private void zipFile(String filename, ZipOutputStream out, String text) throws IOException {
        ZipEntry e = new ZipEntry(filename);
        out.putNextEntry(e);

        byte[] data = text.getBytes();
        out.write(data, 0, data.length);
        out.closeEntry();
    }

    @Test
    void unzipTenFiles() throws IOException {

        Files.createDirectory(Paths.get("___test"));


        for (int i = 0; i < 10; i++) {
            zipFile("___test" + i,
                    new ZipOutputStream(new FileOutputStream(new File("___test/___test" + i + ".zip"))), "" + i);
        }

        ZipFile.unzipFiles("___test", "___(.*)");

        for (int i = 0; i < 10; i++) {
            Scanner scanner = new Scanner(new File("___test/___test" + i));
            assertEquals(i, scanner.nextInt());
            assertFalse(scanner.hasNextInt());
        }
        deleteDirectory(new File("___test"));
    }


    @Test
    void unzipFolderWithFoldersInside() throws IOException {

        Files.createDirectory(Paths.get("___test"));

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(new File("___test/___test.zip")));


        zipFile("layer1/layer2/layer3/layer4/___test" + 2, out, "" + 2);
        zipFile("layer1/layer2/layer3/___test" + 1, out, "" + 1);
        zipFile("layer1/layer2/___test" + 1, out, "" + 1);
        zipFile("layer1/___test" + 1, out, "" + 1);

        out.close();

        ZipFile.unzipFiles("___test", "___test1");

        {
            Scanner scanner = new Scanner(new File("___test/layer1/___test1"));
            assertEquals(1, scanner.nextInt());
            assertFalse(scanner.hasNextInt());
        }

        {
            Scanner scanner = new Scanner(new File("___test/layer1/layer2/___test1"));
            assertEquals(1, scanner.nextInt());
            assertFalse(scanner.hasNextInt());
        }

        {
            Scanner scanner = new Scanner(new File("___test/layer1/layer2/layer3/___test1"));
            assertEquals(1, scanner.nextInt());
            assertFalse(scanner.hasNextInt());
        }

        assertFalse((new File("___test/layer1/layer2/layer3/layer4/___test2").exists()));

        deleteDirectory(new File("___test"));
    }

}