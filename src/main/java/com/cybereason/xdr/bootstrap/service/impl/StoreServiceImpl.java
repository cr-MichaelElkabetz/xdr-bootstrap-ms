package com.cybereason.xdr.bootstrap.service.impl;

import com.cybereason.xdr.bootstrap.config.Bigtable.BigtableConfig;
import com.cybereason.xdr.bootstrap.model.CybereasonUser;
import com.cybereason.xdr.bootstrap.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    BigtableConfig bigtableConfig = new BigtableConfig();

    @Override
    public CybereasonUser getUser(String userID) {
        String userString = bigtableConfig.getData(userID);
        LOGGER.info("Found user's data: " + userString);
        try {
            return objectMapper.readValue(userString, CybereasonUser.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Unable to parse user data: ", e.getMessage(), e);
        }

        return null;
    }

    @Override
    public void createUser(String userID) {
        CybereasonUser cybereasonUser = CybereasonUser.builder().identity(userID).status("IT's working!").build();
        try {
            bigtableConfig.setData(cybereasonUser.getIdentity(), objectMapper.writeValueAsString(cybereasonUser));
        } catch (JsonProcessingException e) {
            LOGGER.error("Unable to write create a new user: ", e.getMessage(), e);
        }
    }

    @Override
    public void updateUser(String userID) {
    }
}
