package com.cybereason.xdr.bootstrap.config.pubsub;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.AlreadyExistsException;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.ProjectTopicName;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class PubsubAdmin {
    private static final Logger LOGGER = LoggerFactory.getLogger(PubsubAdmin.class);

    final CredentialsProvider credentials;
    final TransportChannelProvider channelProvider;

    @Value(value = "${spring.cloud.gcp.credentials.path}")
    private String credentialsPath;

    @Value(value = "${spring.cloud.gcp.project-id}")
    private String projectID;

    @Value(value = "${spring.pubsub.produce.topic}")
    private String topicID;


    public PubsubAdmin() {
        this.credentials = getCredentials(credentialsPath);
        this.channelProvider = getChannelProvider();
    }

    private CredentialsProvider getCredentials(String credentialsPath) {
        if (credentialsPath != null) {
            GoogleCredentials credentials = null;
            try {
                credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath));
            } catch (IOException e) {
                LOGGER.error("Unable to load credentials file", e.getMessage(), e);
            }
            return FixedCredentialsProvider.create(credentials);
        }
        return null;
    }

    private TransportChannelProvider getChannelProvider() {
        return null;
    }
}
