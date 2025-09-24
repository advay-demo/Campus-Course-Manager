package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private Path dataDir;
    private DateTimeFormatter tsFormatter;
    private int maxCreditsPerSemester;

    private AppConfig() {
        // default configuration
        this.dataDir = Paths.get(System.getProperty("user.home"), "ccrm_data");
        this.tsFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        this.maxCreditsPerSemester = 24;
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public Path getDataDir() {
        return dataDir;
    }

    public DateTimeFormatter getTsFormatter() {
        return tsFormatter;
    }

    public int getMaxCreditsPerSemester() {
        return maxCreditsPerSemester;
    }
    public static class Builder {
        private Path dataDir = AppConfig.getInstance().dataDir;
        private DateTimeFormatter tsFormatter = AppConfig.getInstance().tsFormatter;
        private int maxCredits = AppConfig.getInstance().maxCreditsPerSemester;

        public Builder dataDir(Path path) { this.dataDir = path; return this; }
        public Builder tsFormatter(DateTimeFormatter fmt) { this.tsFormatter = fmt; return this; }
        public Builder maxCreditsPerSemester(int c) { this.maxCredits = c; return this; }

        public void apply() {
            AppConfig cfg = AppConfig.getInstance();
            cfg.dataDir = this.dataDir;
            cfg.tsFormatter = this.tsFormatter;
            cfg.maxCreditsPerSemester = this.maxCredits;
        }
    }
}