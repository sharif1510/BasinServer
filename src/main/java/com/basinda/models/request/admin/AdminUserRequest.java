package com.basinda.models.request.admin;

import lombok.Getter;
import lombok.Setter;
import com.basinda.models.eUserType;

@Setter
@Getter
public class AdminUserRequest {
    private String name;
    private String phone;
    private String email;
    private String password;
    private String confirmPassword;
    private eUserType role;
}