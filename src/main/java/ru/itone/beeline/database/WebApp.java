package ru.itone.beeline.database;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationPath("/api")
public class WebApp extends Application {

    private static Logger log = LoggerFactory.getLogger(WebApp.class);

    public static void main(String[] args) {
        log.info("Application starting...");
    }

}
