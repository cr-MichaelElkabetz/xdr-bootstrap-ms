package com.cybereason.xdr.bootstrap.service;

import com.cybereason.xdr.bootstrap.model.CybereasonUser;

public interface StoreService {
    CybereasonUser getUser(String userID);

    void createUser(String userID);

    void updateUser(String userID);
}
