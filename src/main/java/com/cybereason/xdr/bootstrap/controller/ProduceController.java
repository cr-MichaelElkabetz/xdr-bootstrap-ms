package com.cybereason.xdr.bootstrap.controller;

import com.cybereason.xdr.bootstrap.service.ProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProduceController {

    @Autowired
    private ProduceService produceService;

    @RequestMapping(value = "/kafka/produce", method = RequestMethod.POST)
    public String produceKafka(@RequestBody String message) {
        produceService.sendKafkaMessage(message);
        return "OK";
    }

    @RequestMapping(value = "/pubsub/produce", method = RequestMethod.POST)
    public String producePubsub(@RequestBody String message) {
        produceService.sendPubsubMessage(message);
        return "OK";
    }
}
