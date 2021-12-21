package com.cybereason.xdr.bootstrap;

import com.cybereason.xdr.bootstrap.model.CybereasonUser;
import com.cybereason.xdr.bootstrap.service.StoreService;
import com.cybereason.xdr.bootstrap.service.impl.StoreServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootstrapApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapApplication.class);

    static StoreService storeService = new StoreServiceImpl();

    public static void main(String[] args) {
        LOGGER.info("*** Bootstrap MS is up and running ***");
        SpringApplication.run(BootstrapApplication.class, args);
        //storeService.createUser("123");
        //CybereasonUser user = storeService.getUser("123");
    }
}
