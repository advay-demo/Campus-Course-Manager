package edu.ccrm.io;

import edu.ccrm.config.AppConfig;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;

public class BackupService {
    private final Path base;

    public BackupService(Path base) {
        this.base = base;
    }

    public Path backup(Path sourceDir) throws IOException {
        String ts = LocalDateTime.now().format(AppConfig.getInstance().getTsFormatter());
        Path dest = base.resolve("backup_" + ts);
        Files.createDirectories(dest);
        // copy recursively
        Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path rel = sourceDir.relativize(file);
                Path target = dest.resolve(rel);
                Files.createDirectories(target.getParent());
                Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
        return dest;
    }
}