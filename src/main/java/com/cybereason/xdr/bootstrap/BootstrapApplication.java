package com.cybereason.xdr.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootstrapApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapApplication.class);

    public static void main(String[] args) {
        LOGGER.info("*** Bootstrap MS is up and running ***");
        SpringApplication.run(BootstrapApplication.class, args);
    }
}
