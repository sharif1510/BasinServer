package com.basinda.models.request.common;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String password;
}