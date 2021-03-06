package ru.spbau.egorov.hw_4.zipfile;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class ZipFileTest {

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
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(new File("___test" + File.separator + "___test" + i + ".zip")));
            zipFile("___test" + i,
                    out, "" + i);
            out.close();
        }

        ZipFile.unzipFiles("___test", "___(.*)");

        for (int i = 0; i < 10; i++) {
            Scanner scanner = new Scanner(new File("___test" + File.separator + "___test" + i));
            assertEquals(i, scanner.nextInt());
            assertFalse(scanner.hasNextInt());
            scanner.close();
        }
        FileUtils.deleteDirectory(Paths.get("___test").toFile());
    }


    @Test
    void unzipFolderWithFoldersInside() throws IOException {

        Files.createDirectory(Paths.get("___test"));

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(new File("___test" + File.separator + "___test.zip")));


        zipFile("layer1" + File.separator + "layer2" + File.separator + "layer3" + File.separator + "layer4" + File.separator + "___test" + 2, out, "" + 2);
        zipFile("layer1" + File.separator + "layer2" + File.separator + "layer3" + File.separator + "___test" + 1, out, "" + 1);
        zipFile("layer1" + File.separator + "layer2" + File.separator + "___test" + 1, out, "" + 1);
        zipFile("layer1" + File.separator + "___test" + 1, out, "" + 1);

        out.close();

        ZipFile.unzipFiles("___test", "___test1");

        {
            Scanner scanner = new Scanner(new File("___test" + File.separator + "layer1" + File.separator + "___test1"));
            assertEquals(1, scanner.nextInt());
            assertFalse(scanner.hasNextInt());
            scanner.close();
        }

        {
            Scanner scanner = new Scanner(new File("___test" + File.separator + "layer1" + File.separator + "layer2" + File.separator + "___test1"));
            assertEquals(1, scanner.nextInt());
            assertFalse(scanner.hasNextInt());
            scanner.close();
        }

        {
            Scanner scanner = new Scanner(new File("___test" + File.separator + "layer1" + File.separator + "layer2" + File.separator + "layer3" + File.separator + "___test1"));
            assertEquals(1, scanner.nextInt());
            assertFalse(scanner.hasNextInt());
            scanner.close();
        }

        assertFalse((new File("___test" + File.separator + "layer1" + File.separator + "layer2" + File.separator + "layer3" + File.separator + "layer4" + File.separator + "___test2").exists()));

        FileUtils.deleteDirectory(Paths.get("___test").toFile());
    }

}