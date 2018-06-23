package ru.spbau.egorov.cr_1.check_sum;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CheckSumMultiThreadTest {
    @Test
    void checkSumEmptyDir() throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        CheckSum checker = new CheckSumMultiThread();
        byte[] ans = md.digest("emtyDirrrrrrrrrrrrrr12345".getBytes());
        assertEquals(Arrays.toString(ans), Arrays.toString(checker.checkSum(Paths.get("testDirs/emtyDirrrrrrrrrrrrrr12345"))));
    }

    @Test
    void checkSumOneFile() throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        CheckSum checker = new CheckSumMultiThread();
        md.update("jojo".getBytes());
        byte[] ans = md.digest();
        assertEquals(Arrays.toString(ans), Arrays.toString(checker.checkSum(Paths.get("testDirs/withOneFile/kek"))));
    }

    @Test
    void checkSumOneEmptyFile() throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        CheckSum checker = new CheckSumMultiThread();
        md.update("".getBytes());
        byte[] ans = md.digest();
        assertEquals(Arrays.toString(ans), Arrays.toString(checker.checkSum(Paths.get("testDirs/withEmptyFile/kek"))));
    }


}