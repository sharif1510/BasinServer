package com.basinda.api.admin;

import com.basinda.exceptions.ResourceNotFoundException;
import com.basinda.models.eUserType;
import com.basinda.models.entity.AdminUser;
import com.basinda.models.request.admin.AdminUserRequest;
import com.basinda.models.request.common.LoginRequest;
import com.basinda.repositories.AdminUserRepository;
import com.basinda.services.AdminUserService;
import com.basinda.utils.EmailUtils;
import com.basinda.models.entity.User;
import com.basinda.services.UserService;
import com.basinda.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.basinda.models.response.ResponseHeader;
import com.basinda.models.request.admin.ApproveRequest;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final JwtUtils jwtTokenUtil;
    private final UserService userService;
    private final AdminUserService adminUserService;
    private final AdminUserRepository adminUserRepository;

    public AdminController(JwtUtils jwtTokenUtil, UserService userService, AdminUserService adminUserService, AdminUserRepository adminUserRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.adminUserService = adminUserService;
        this.adminUserRepository = adminUserRepository;
    }

    public class Response extends ResponseHeader{
        public Long id;
        public eUserType role;
    }

    public class UserResponse extends ResponseHeader{
        public List<User> data;
    }

    public class LoginResponse extends ResponseHeader{
        public Long id;
        public String userId;
        public String token;
        public eUserType userType;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody AdminUserRequest request){
        Response response = new Response();

        if (!request.getPassword().equals(request.getConfirmPassword())){
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            response.setStatus(true);
            response.setContent("Password does not match. Please try again.");
        }
        else{
            AdminUser createdAdminUser = adminUserService.registerUser(request);
            if (createdAdminUser==null){
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                response.setStatus(true);
                response.setContent("Something went wrong please try again.");
            }
            else {
                response.setStatusCode(HttpStatus.CREATED);
                response.setStatus(true);
                response.id = createdAdminUser.getId();
                response.role = createdAdminUser.getRole();
                response.setContent("User created successfully please before login verify your email.");
            }
        }
        return ResponseEntity.status(response.statusCode).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response = new LoginResponse();
        String userLogin = adminUserService.login(request);
        if (userLogin.equalsIgnoreCase("valid")){
            List<AdminUser> adminUsers = adminUserRepository.findByPhone(request.getPhone());
            response.id = adminUsers.get(0).getId();
            response.userType = adminUsers.get(0).getRole();
            response.setContent("User Login Successfully.");
            String token = jwtTokenUtil.generateToken();
            response.token = token;
        } else if (userLogin.equalsIgnoreCase("Redirect")) {

        } else{
            throw new ResourceNotFoundException("Username or Password incorrect.");
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<UserResponse> allUsers(){
        UserResponse response = new UserResponse();
        List<User> users = userService.allUsers();
        response.data = users;
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/approve")
    public ResponseEntity<Response> approved(@RequestBody ApproveRequest request, final HttpServletRequest servletRequest){
        Response response = new Response();
        String applicationUrl = EmailUtils.getApplicationUrl(servletRequest);

        try {
            boolean isregistered = userService.approveUser(request,applicationUrl);
            if (isregistered){
                response.setStatusCode(HttpStatus.OK);
                response.setStatus(true);
                response.setContent("User approved successfully.");
            }
            else{
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                response.setStatus(true);
                response.setContent("Something went wrong please try again.");
            }
        }
        catch (Exception ex){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatus(true);
            response.setContent("Something went wrong please try again.");
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/reject")
    public ResponseEntity<HttpStatus> reject(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}