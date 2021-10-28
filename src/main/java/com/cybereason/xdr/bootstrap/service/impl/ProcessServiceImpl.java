package com.cybereason.xdr.bootstrap.service.impl;

import com.cybereason.xdr.bootstrap.service.ProcessService;
import com.cybereason.xdr.bootstrap.service.ProduceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessServiceImpl implements ProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessServiceImpl.class);

    @Autowired
    private ProduceService produceService;

    @Override
    public void process(String message) {
        LOGGER.info("*** Processing started ***");

        LOGGER.info("*** Processing completed ***");
        produceService.sendKafkaMessage(message);
    }
}
