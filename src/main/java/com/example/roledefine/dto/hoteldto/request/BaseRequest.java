package com.example.roledefine.dto.hoteldto.request;

import lombok.Data;

@Data
public abstract class BaseRequest {

    private String user_id;
    private String user_password;
    private String access;
    private String ip_address;
}
