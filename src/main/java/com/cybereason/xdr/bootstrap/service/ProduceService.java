package com.cybereason.xdr.bootstrap.service;

public interface ProduceService {
    void sendKafkaMessage(String message);
    void sendPubsubMessage(String message);
}
