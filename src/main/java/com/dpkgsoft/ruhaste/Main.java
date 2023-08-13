package com.dpkgsoft.ruhaste;

import com.google.gson.Gson;
import io.sentry.Sentry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);
    public static String VERSION = "Development Version";
    public static Config config;

    public static void main(String[] args) throws IOException {
        LOG.info("Initializing config...");
        Gson gson = new Gson();
        String configString = Files.readString(new File("config.json").toPath());
        config = gson.fromJson(configString, Config.class);

        LOG.info("Loading version information...");
        try {
            String v = Main.class.getPackage().getImplementationVersion();
            if (v != null) {
                VERSION = v;
            }
        } catch (Exception ignored) {}
        LOG.info("Working on version " + VERSION);

        if (!Objects.equals(config.getSentryDSN(), "")) {
            LOG.info("Initializing Sentry...");
            Sentry.init(options -> {
                options.setDsn(config.getSentryDSN());
            });
        } else {
            LOG.warn("Sentry DSN not specified. Working without Sentry. Warning!!! Might be Runtime Exceptions!");
        }


    }
}
