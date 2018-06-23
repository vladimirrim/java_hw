package ru.spbau.egorov.cr_1.check_sum;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

/**
 * This class represents recursion task for hashing directory in forkJoinPool."
 */
public class ForkJoinHashWorker extends RecursiveTask<byte[]> {
    private Path path;

    public ForkJoinHashWorker(@NotNull Path path) {
        this.path = path;
    }


    @Override
    protected byte[] compute() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            ArrayList<ForkJoinHashWorker> workers = new ArrayList<>();
            if (Files.isDirectory(path)) {
                md.update(path.getFileName().toString().getBytes());
                try (Stream<Path> childs = Files.walk(path).filter(Files::isRegularFile)) {
                    childs.forEach(child -> {
                        ForkJoinHashWorker worker = new ForkJoinHashWorker(child);
                        worker.fork();
                        workers.add(worker);
                    });
                } catch (IOException e) {
                    completeExceptionally(e);
                    return null;
                }
                for (ForkJoinHashWorker worker : workers)
                    md.update(worker.join());
                return md.digest();
            } else {
                try (InputStream is = Files.newInputStream(path)) {
                    DigestInputStream dis = new DigestInputStream(is, md);
                    byte[] buffer = new byte[1024];
                    while (dis.read(buffer) != -1) {
                    }
                } catch (IOException e) {
                    completeExceptionally(e);
                    return null;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            completeExceptionally(e);
            return null;
        }
        return md.digest();
    }
}
