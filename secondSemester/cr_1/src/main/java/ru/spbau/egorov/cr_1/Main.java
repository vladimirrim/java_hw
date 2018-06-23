package ru.spbau.egorov.cr_1;

import ru.spbau.egorov.cr_1.check_sum.CheckSum;
import ru.spbau.egorov.cr_1.check_sum.CheckSumMultiThread;
import ru.spbau.egorov.cr_1.check_sum.CheckSumOneThread;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        CheckSum checker1 = new CheckSumOneThread();
        CheckSum checker2 = new CheckSumMultiThread();
        long startTime = System.nanoTime();
        byte[] ans1 = checker1.checkSum(Paths.get(args[0]));
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("One thread: " + totalTime);
        System.out.println(Arrays.toString(ans1));
        startTime = System.nanoTime();
        byte[] ans2 = checker2.checkSum(Paths.get(args[1]));
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Multi thread: " + totalTime);
        System.out.println(Arrays.toString(ans2));
    }
}
