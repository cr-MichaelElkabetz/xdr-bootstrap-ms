package com.cybereason.xdr.bootstrap.controller;

import com.cybereason.xdr.bootstrap.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/produce", method = RequestMethod.POST)
    public String produce(@RequestBody String message) {
        businessService.process(message);
        return "OK";
    }
}
