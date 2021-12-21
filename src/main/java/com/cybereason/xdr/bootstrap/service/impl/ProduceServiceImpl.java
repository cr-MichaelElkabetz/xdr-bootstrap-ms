package com.cybereason.xdr.bootstrap.service.impl;

import com.cybereason.xdr.bootstrap.service.ProduceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ProduceServiceImpl implements ProduceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProduceServiceImpl.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Value(value = "${spring.kafka.produce.topic}")
    private String produceKafkaTopic;

    @Value(value = "${spring.pubsub.produce.topic}")
    private String producePubsubTopic;

    @Override
    public void sendPubsubMessage(String message) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("pluginType", "pipeline");
            pubSubTemplate.publish(producePubsubTopic, message, map).get();
            LOGGER.info("Published successfully A new PubSub message, topic: {}, message: {}", producePubsubTopic, message);
        } catch (InterruptedException e) {
            LOGGER.error("Unable to send Pubsub message: " + message + ", reason: " + e.getMessage(), e);
        } catch (ExecutionException e) {
            LOGGER.error("Unable to send Pubsub message: " + message + ", reason: " + e.getMessage(), e);
        }
    }


    @Override
    public void sendKafkaMessage(String message) {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(produceKafkaTopic, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOGGER.info("Sent Kafka message: " + message + " successfully");
            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("Unable to send Kafka message: " + message + ", reason: " + ex.getMessage(), ex);
            }
        });
    }
}
