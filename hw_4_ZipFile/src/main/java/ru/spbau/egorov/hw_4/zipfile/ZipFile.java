package ru.spbau.egorov.hw_4.zipfile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jetbrains.annotations.NotNull;


/**
 * This class gets directory name and regex and extracts files matching regex from all zip archives in this directory.
 */
public class ZipFile {
    private static void unzip(@NotNull Path zipFile, @NotNull Path extractDir, @NotNull String name) {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry entry = zis.getNextEntry();
            byte[] buffer = new byte[1024];

            while (entry != null) {

                if (!new File(entry.getName()).getName().matches(name)) {
                    entry = zis.getNextEntry();
                    continue;
                }

                String fileName = entry.getName();
                File newFile = new File(extractDir.toFile().getName() + File.separator + fileName);

                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                entry = zis.getNextEntry();
            }

        } catch (IOException e) {
            System.out.println("Errors occurred during unzipping " + zipFile.getFileName());
        }
    }

    /**
     * Unzip files matching regex from all archives in given directory.
     *
     * @param name is regex for matching file names in archives.
     * @throws IOException if unable to create or work with stream of files from given path.
     */
    public static void unzipFiles(@NotNull String path, @NotNull String name) throws IOException {
        Path dir = Paths.get(path);
        final PathMatcher matcher = dir.getFileSystem().getPathMatcher("glob:**.zip");
        try (final Stream<Path> stream = Files.list(dir)) {
            stream.filter(matcher::matches)
                    .forEach(zipFile -> unzip(zipFile, dir, name));
        }
    }

    public static void main(String[] args) {
        try {
            unzipFiles(args[0], args[1]);
        } catch (IOException e) {
            System.out.println("Unable to unzip files.\n");
            e.printStackTrace();
        }
    }
}
