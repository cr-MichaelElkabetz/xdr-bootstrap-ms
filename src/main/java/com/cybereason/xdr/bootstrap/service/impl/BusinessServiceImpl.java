package com.cybereason.xdr.bootstrap.service.impl;

import com.cybereason.xdr.bootstrap.service.BusinessService;
import com.cybereason.xdr.bootstrap.service.ProduceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessServiceImpl.class);

    @Autowired
    private ProduceService produceService;

    @Override
    public void process(String message) {
        LOGGER.info("*** Processing message ***");
        //do staff

        //produce message
        produceService.sendMessage(message);
    }
}
