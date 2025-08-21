package com.example.roledefine.service;

import com.example.roledefine.dto.hoteldto.request.BaseRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CredentialProvider {

    @Value("${travelnext.api.user_id}")
    private String apiUserId;

    @Value("${travelnext.api.password}")
    private String apiPassword;

    @Value("${travelnext.api.access}")
    private String apiAccess;

    @Value("${travelnext.api.ip_address}")
    private String apiIpAddress;

    public void populate(BaseRequest request) {
        request.setUser_id(apiUserId);
        request.setUser_password(apiPassword);
        request.setAccess(apiAccess);
        request.setIp_address(apiIpAddress);
    }
}