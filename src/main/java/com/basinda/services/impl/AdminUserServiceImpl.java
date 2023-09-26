package com.basinda.services.impl;

import com.basinda.config.UserLoadService;
import com.basinda.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import com.basinda.models.entity.AdminUser;
import com.basinda.services.AdminUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import com.basinda.repositories.AdminUserRepository;
import com.basinda.models.request.common.LoginRequest;
import com.basinda.exceptions.UserAlreadyExistException;
import com.basinda.models.request.admin.AdminUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final ModelMapper modelMapper;
    private final AuthenticationManager manager;
    private final PasswordEncoder passwordEncoder;
    private final UserLoadService userLoadService;
    private final AdminUserRepository adminUserRepository;

    public AdminUserServiceImpl(ModelMapper modelMapper, AuthenticationManager manager, PasswordEncoder passwordEncoder, UserLoadService userLoadService, AdminUserRepository adminUserRepository) {
        this.modelMapper = modelMapper;
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
        this.userLoadService = userLoadService;
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public String login(LoginRequest request) {
        UserDetails userDetails = userLoadService.loadUserByUsername("admin"+request.getPhone());
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())){
            throw new ResourceNotFoundException("Phone or password does not match.");
        }
        else{
            Authentication authentication;
            try {
                authentication = manager.authenticate(new UsernamePasswordAuthenticationToken("admin"+userDetails.getUsername(),request.getPassword(),userDetails.getAuthorities()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch (BadCredentialsException ex){
                return "Invalid";
            }
        }
        return "valid";
    }

    @Override
    public AdminUser registerUser(AdminUserRequest request) {
        List<AdminUser> adminUsers = null;
        adminUsers = adminUserRepository.findByPhone(request.getPhone());
        if (adminUsers.size() != 0){
            throw new UserAlreadyExistException("User already exist with the provided phone number.");
        }
        adminUsers = adminUserRepository.findByEmail(request.getEmail());
        if (adminUsers.size() != 0){
            throw new UserAlreadyExistException("User already exist with the provided email.");
        }
        AdminUser adminUser = modelMapper.map(request, AdminUser.class);
        adminUser.setPassword(passwordEncoder.encode(request.getPassword()));
        AdminUser response = adminUserRepository.save(adminUser);
        return response;
    }
}
