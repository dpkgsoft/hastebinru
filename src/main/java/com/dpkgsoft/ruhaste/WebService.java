package com.dpkgsoft.ruhaste;

import com.dpkgsoft.spark.ratelimit.RateLimit;
import spark.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import static spark.Service.ignite;

public class WebService {
    private static final RateLimit rateLimit = new RateLimit(10, 1, TimeUnit.MINUTES);

    public static void start() {
        Service http = ignite()
                .ipAddress(Main.config.getListen())
                .port(Main.config.getPort())
                .threadPool(20);
        http.externalStaticFileLocation(new File("static").getAbsolutePath());

        rateLimit.map(http, "/documents");

        http.get("/raw/:id", WebRoutes.getRawDocument);
        http.head("/raw/:id", WebRoutes.getRawDocument);

        http.post("/documents", WebRoutes.postDocuments);

        http.get("/documents/:id", WebRoutes.getDocument);
        http.head("/documents/:id", WebRoutes.getDocument);

        http.get("/:id", (q, a) ->
                Files.readString(new File("static", "index.html").toPath()));

        http.notFound((r, a) -> {
            a.redirect("/");
            return null;
        });
        http.internalServerError((r, a) -> "500 - Internal Server Error");
    }
}
