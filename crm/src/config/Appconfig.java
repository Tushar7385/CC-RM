package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

/**
 * Singleton configuration holder. Demonstrates Singleton pattern.
 */
public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();
    private final Path dataDir;
    private final Instant startedAt;

    private AppConfig() {
        this.dataDir = Paths.get(System.getProperty("user.dir"), "data");
        this.startedAt = Instant.now();
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public Path getDataDir() {
        return dataDir;
    }

    public Instant getStartedAt() {
        return startedAt;
    }
}
