package com.basinda.services;

import com.basinda.models.entity.AdminUser;
import com.basinda.models.request.common.LoginRequest;
import com.basinda.models.request.admin.AdminUserRequest;

public interface AdminUserService {
    String login(LoginRequest request);
    AdminUser registerUser(AdminUserRequest request);

}