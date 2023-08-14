package com.dpkgsoft.ruhaste;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import spark.Route;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class WebRoutes {
    private static final Logger LOG = LogManager.getLogger(WebRoutes.class);
    public static Route postDocuments = (q, a) -> {
        String content = q.body();
        if (Main.config.getMaxLength() != 0 && content.length() > Main.config.getMaxLength()) {
            LOG.warn(" document > maxLength ");
            a.status(400);
            a.type("application/json");
            return new JSONObject()
                    .put("message", "Document exceeds maximum length.")
                    .toString();
        }

        String key = Utils.generateKey();
        String keyMD5 = DigestUtils.md5Hex(key);

        try {
            Files.writeString(new File(Utils.getDirectory(), keyMD5).toPath(), content, StandardCharsets.UTF_8);

            a.status(200);
            a.type("application/json");
            return new JSONObject()
                    .put("key", key)
                    .toString();
        } catch (Exception i) {
            a.status(500);
            a.type("application/json");
            return new JSONObject()
                    .put("message", "Error adding document.")
                    .toString();
        }
    };

    public static Route getRawDocument = (q, a) -> {
        String key = q.params("id").split("\\.")[0];
        File file;
        if (Main.config.getDocs().containsKey(key)) {
            file = new File(Main.config.getDocs().get(key));
        } else {
            file = new File(Utils.getDirectory(), DigestUtils.md5Hex(key));
        }

        if (!file.exists()) {
            LOG.warn("Raw document " + key + " not found");
            a.type("application/json");
            a.status(404);
            if (!q.requestMethod().equalsIgnoreCase("HEAD")) {
                return new JSONObject().put("message", "Document not found.").toString();
            }
            return null;
        }

        LOG.info("Retrieved raw document " + key);
        a.status(200);
        a.type("text/plain; charset=UTF-8");
        if (q.requestMethod().equalsIgnoreCase("HEAD")) {
            return null;
        }
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    };

    public static Route getDocument = (q, a) -> {
        String key = q.params("id").split("\\.")[0];
        File file;
        if (Main.config.getDocs().containsKey(key)) {
            file = new File(Main.config.getDocs().get(key));
        } else {
            file = new File(Utils.getDirectory(), DigestUtils.md5Hex(key));
        }

        if (!file.exists()) {
            LOG.warn("Raw document " + key + " not found");
            a.type("application/json");
            a.status(404);
            if (!q.requestMethod().equalsIgnoreCase("HEAD")) {
                return new JSONObject().put("message", "Document not found.").toString();
            }
            return null;
        }

        LOG.info("Retrieved document " + key);
        a.status(200);
        a.type("application/json");
        if (q.requestMethod().equalsIgnoreCase("HEAD")) {
            return null;
        }
        return new JSONObject()
                .put("key", key)
                .put("data", Files.readString(file.toPath(), StandardCharsets.UTF_8))
                .toString();
    };
}
