package ru.spbau.egorov.cr_1.check_sum;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public interface CheckSum {
    /**Computes check sum for file or directory.
     * @param path is the file or directory.
     * @return hash.
     * @throws NoSuchAlgorithmException if there is no hashing algorithm.
     * @throws IOException if there is problem with accessing files.
     */
    byte[] checkSum(@NotNull Path path) throws NoSuchAlgorithmException, IOException;
}
