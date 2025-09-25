package edu.ccrm.io;

import edu.ccrm.config.AppConfig;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {
    private final Path root;

    public BackupService() {
        this.root = AppConfig.getInstance().getDataDir();
    }

    public Path backupFolder() throws IOException {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path dest = root.resolve("backup_" + ts);
        Files.createDirectories(dest);
        return dest;
    }

    // recursive utility: compute directory size
    public long sizeRec(Path p) throws IOException {
        final long[] total = {0};
        if (!Files.exists(p)) return 0;
        Files.walk(p).forEach(path -> {
            if (Files.isRegularFile(path)) {
                try { total[0] += Files.size(path); } catch (IOException ignored) {}
            }
        });
        return total[0];
    }
}
