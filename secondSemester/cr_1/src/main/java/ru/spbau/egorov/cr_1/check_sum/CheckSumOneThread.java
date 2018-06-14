package ru.spbau.egorov.cr_1.check_sum;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

/**
 * This class compute check-sum for directory in one thread."
 */
public class CheckSumOneThread implements CheckSum {
    @Override
    public byte[] checkSum(@NotNull Path path) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        if (Files.isDirectory(path)) {
            md.update(path.getFileName().toString().getBytes());
            try (Stream<Path> childs = Files.walk(path).filter(Files::isRegularFile)) {
                childs.forEach(child -> {
                    try {
                        md.update(checkSum(child));
                    } catch (NoSuchAlgorithmException | IOException e) {
                        e.printStackTrace();
                    }
                });
                return md.digest();
            }
        }
        try (InputStream is = Files.newInputStream(path)) {
            DigestInputStream dis = new DigestInputStream(is, md);
            byte[] buffer = new byte[1024];
            while (dis.read(buffer) != -1) {
            }
            return dis.getMessageDigest().digest();
        }

    }
}
