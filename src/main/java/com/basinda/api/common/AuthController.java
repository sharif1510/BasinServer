package com.basinda.api.common;

import com.basinda.utils.JwtUtils;
import com.basinda.models.eUserType;
import com.basinda.utils.EmailUtils;
import com.basinda.models.entity.User;
import com.basinda.services.UserService;
import org.springframework.http.HttpStatus;
import com.basinda.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletResponse;
import com.basinda.models.response.ResponseHeader;
import com.basinda.models.request.common.LoginRequest;
import com.basinda.exceptions.ResourceNotFoundException;
import com.basinda.models.request.user.RegistrationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtils jwtTokenUtil;
    private final UserService userService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate template;

    public AuthController(JwtUtils jwtTokenUtil, UserService userService, UserRepository userRepository, SimpMessagingTemplate template) {
        this.template = template;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    public class Response extends ResponseHeader{
        public Long id;
        public eUserType userType;
    }

    public class LoginResponse extends ResponseHeader{
        public Long id;
        public String userId;
        public String token;
        public eUserType userType;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> createUser(@RequestBody RegistrationRequest model, final HttpServletRequest request){
        Response response = new Response();

        if (!model.getPassword().equals(model.getConfirmPassword())){
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            response.setStatus(true);
            response.setContent("Password does not match. Please try again.");
        }
        else{
            String applicationUrl = EmailUtils.getApplicationUrl(request);
            User createdUser = userService.registerUser(model, applicationUrl);
            if (createdUser==null){
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                response.setStatus(true);
                response.setContent("Something went wrong please try again.");
            }
            else {
                response.setStatusCode(HttpStatus.CREATED);
                response.setStatus(true);
                response.id = createdUser.getId();
                response.userType = createdUser.getUserType();
                response.setContent("User created successfully please before login verify your email.");
                template.convertAndSend("/register/users",createdUser);
            }
        }
        return ResponseEntity.status(response.statusCode).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, final HttpServletResponse res) throws ResourceNotFoundException {
        LoginResponse response = new LoginResponse();
        String userLogin = userService.login(request, res);
        if (userLogin.equalsIgnoreCase("valid")){
            List<User> user = userRepository.findByPhone(request.getPhone());
            response.id = user.get(0).getId();
            response.userId = user.get(0).getUserId();
            response.userType = user.get(0).getUserType();
            response.setContent("User Login Successfully.");
            String token = jwtTokenUtil.generateToken();
            response.token = token;
        } else if (userLogin.equalsIgnoreCase("Redirect")) {
            
        } else{
            throw new ResourceNotFoundException("Username or Password incorrect.");
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload User user) {
        // receive message from client
    }

    @SendTo("/register/users")
    public User boardCastingUsersToAdmin(@Payload User user){
        return user;
    }
}