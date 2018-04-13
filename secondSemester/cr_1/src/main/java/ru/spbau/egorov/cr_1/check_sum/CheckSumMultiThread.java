package ru.spbau.egorov.cr_1.check_sum;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.ForkJoinPool;

/**
 * This class computes check-sum for directory in forkJoinPool.
 */
public class CheckSumMultiThread implements CheckSum {
    @Override
    public byte[] checkSum(@NotNull Path path) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ForkJoinHashWorker(path));
    }
}
