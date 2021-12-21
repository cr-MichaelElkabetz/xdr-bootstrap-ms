package com.cybereason.xdr.bootstrap.model;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public
class CybereasonUser {
    Long sid;
    String status;
    String department;
    String displayName;
    String manager;
    String identity;
    String username;
    String domain;
    Boolean isDomainUser;
    String fqdn;
    String phone;
    String title;

    @Singular
    List<String> emailAddresses;

    @Singular
    List<String> permissions;

    @Singular
    List<String> groups;

    @Singular
    List<String> roles;
}
