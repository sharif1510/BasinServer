package com.basinda.services;

import com.basinda.models.entity.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import com.basinda.models.request.common.LoginRequest;
import com.basinda.models.request.admin.ApproveRequest;
import org.springframework.web.multipart.MultipartFile;
import com.basinda.models.request.user.RegistrationRequest;

import java.util.List;
import java.io.UnsupportedEncodingException;

public interface UserService {
    String login(LoginRequest request, final HttpServletResponse res);
    User registerUser(RegistrationRequest request, String applicationUrl);
    boolean verify(String verificationCode);
    boolean approveUser(ApproveRequest request, String applicationUrl) throws MessagingException, UnsupportedEncodingException;
    List<User> allUsers();

    void uploadProfileImage(Long userId, MultipartFile image);
    void uploadNidCardFontImage(Long userId, MultipartFile image);
    void uploadNidCardBackImage(Long userId, MultipartFile image);

    byte[] downloadProfileImage(Long userId);
    byte[] downloadNidFontImage(Long userId);
    byte[] downloadNidBackImage(Long userId);
}