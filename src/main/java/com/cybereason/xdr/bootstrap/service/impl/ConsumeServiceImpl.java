package com.cybereason.xdr.bootstrap.service.impl;

import com.cybereason.xdr.bootstrap.service.ConsumeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumeServiceImpl implements ConsumeService {

    @KafkaListener(topics = "${spring.kafka.consume.topic}")
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println(
                "Received Message: " + message + " from partition: " + partition);
    }
}
