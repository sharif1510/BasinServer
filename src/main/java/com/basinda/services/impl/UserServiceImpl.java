package com.basinda.services.impl;

import com.basinda.exceptions.UserAlreadyExistException;
import com.basinda.file.S3Bucket;
import com.basinda.file.S3Service;
import com.basinda.models.entity.*;
import com.basinda.repositories.*;
import org.modelmapper.ModelMapper;
import com.basinda.models.eUserType;
import com.basinda.models.eStatusType;
import com.basinda.utils.SendOTPUtils;
import jakarta.mail.MessagingException;
import com.basinda.services.UserService;
import jakarta.mail.internet.MimeMessage;
import com.basinda.config.UserLoadService;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import com.basinda.contants.PropertiesConstants;
import com.basinda.models.request.common.LoginRequest;
import com.basinda.models.request.admin.ApproveRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import com.basinda.exceptions.ResourceNotFoundException;
import org.springframework.mail.javamail.JavaMailSender;
import com.basinda.models.request.user.RegistrationRequest;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final S3Bucket s3Bucket;
    private final S3Service s3Service;
    private final ModelMapper modelMapper;
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final AuthenticationManager manager;
    private final PasswordEncoder passwordEncoder;
    private final UserLoadService userLoadService;
    private final PropertiesRepository propertiesRepository;
    private final ProfileImageRepository profileImageRepository;
    private final NidFontImageRepository nidFontImageRepository;
    private final NidBackImageRepository nidBackImageRepository;

    public UserServiceImpl(S3Bucket s3Bucket, S3Service s3Service, ModelMapper modelMapper, JavaMailSender mailSender, UserRepository userRepository, AuthenticationManager manager, PasswordEncoder passwordEncoder, UserLoadService userLoadService, PropertiesRepository propertiesRepository, ProfileImageRepository profileImageRepository, NidFontImageRepository nidFontImageRepository, NidBackImageRepository nidBackImageRepository) {
        this.s3Bucket = s3Bucket;
        this.s3Service = s3Service;
        this.modelMapper = modelMapper;
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
        this.userLoadService = userLoadService;
        this.propertiesRepository = propertiesRepository;
        this.profileImageRepository = profileImageRepository;
        this.nidFontImageRepository = nidFontImageRepository;
        this.nidBackImageRepository = nidBackImageRepository;
    }

    @Override
    public String login(LoginRequest request, final HttpServletResponse res) throws ResourceNotFoundException {
        UserDetails userDetails = userLoadService.loadUserByUsername(request.getPhone());
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())){
            throw new ResourceNotFoundException("Phone or password does not match.");
        }
        else{
            List<Properties> properties = propertiesRepository.findByProperty(PropertiesConstants.twoFactorEnabled);
            if (properties.size() != 0){
                String twoFactorEnabled = properties.get(0).getValue();
                if (twoFactorEnabled.equalsIgnoreCase("False")){
                    Authentication authentication;
                    try {
                        authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhone(),request.getPassword(),userDetails.getAuthorities()));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                    catch (BadCredentialsException ex){
                        return "Invalid";
                    }
                }
                else{
                    /** TODO HERE SEND OTP  */
                    try{
                        SendOTPUtils.send(userDetails.getUsername());
                        return "Redirect";
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                        return "Invalid";
                    }
                }
            }
        }
        return "valid";
    }

    @Override
    public User registerUser(RegistrationRequest request, String applicationUrl) {
        List<User> users = null;
        users = userRepository.findByPhone(request.getPhone());
        if (users.size() != 0){
            throw new UserAlreadyExistException("User already exist with the provided phone number.");
        }
        users = userRepository.findByEmail(request.getEmail());
        if (users.size() != 0){
            throw new UserAlreadyExistException("User already exist with the provided email.");
        }
        User user = modelMapper.map(request, User.class);
        String randomCode = RandomString.make(64);
        /** set email verification code and enable false*/
        user.setVerificationCode(randomCode);
        user.setUserId(UUID.randomUUID().toString().substring(6));
        user.setEnabled(false);
        user.setIsRegistered(eStatusType.eRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User response = userRepository.save(user);
        /** send mail here */
        try {
            sendVerificationEmail(response, applicationUrl);
        }catch (Exception ex){
            // TODO : EXCEPTION HANDLE HERE
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user == null || user.getEnabled()){
            return false;
        }
        else{
            user.setEnabled(true);
            user.setVerificationCode(null);
            user.setIsRegistered(eStatusType.eConfirm);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean approveUser(ApproveRequest request, String applicationUrl)  {
        User user = userRepository.findById(request.getUserId()).orElseThrow( () -> new ResourceNotFoundException());
        if (user != null && user.getIsRegistered() == eStatusType.eConfirm){
            try {
                sendConfirmEmail(user, applicationUrl);
                user.setIsRegistered(eStatusType.eApproved);
                User response = userRepository.save(user);
                if (response != null){
                    return true;
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public void uploadProfileImage(Long userId, MultipartFile image) {
        userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found [%s]".formatted(userId)));

        String profileImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    s3Bucket.getBasinda(),
                    "profile-images/%s/%s".formatted(userId,profileImageId),
                    image.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /** TODO: storage image file here. */
        ProfileImage profileImage = new ProfileImage();
        profileImage.setUserId(userId);
        profileImage.setImageUrl(profileImageId);
        profileImageRepository.save(profileImage);
    }

    @Override
    public void uploadNidCardFontImage(Long userId, MultipartFile image) {
        userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found [%s]".formatted(userId)));

        String nidFontImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    s3Bucket.getBasinda(),
                    "nid-font-images/%s/%s".formatted(userId,nidFontImageId),
                    image.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /** TODO: storage image file here. */
        NidFontImage nidFontImage = new NidFontImage();
        nidFontImage.setUserId(userId);
        nidFontImage.setImageUrl(nidFontImageId);
        nidFontImageRepository.save(nidFontImage);
    }

    @Override
    public void uploadNidCardBackImage(Long userId, MultipartFile image) {
        userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found [%s]".formatted(userId)));

        String nidBackImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    s3Bucket.getBasinda(),
                    "nid-back-images/%s/%s".formatted(userId,nidBackImageId),
                    image.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /** TODO: storage image file here. */
        NidBackImage nidBackImage = new NidBackImage();
        nidBackImage.setUserId(userId);
        nidBackImage.setImageUrl(nidBackImageId);
        nidBackImageRepository.save(nidBackImage);
    }

    @Override
    public byte[] downloadProfileImage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->new ResourceNotFoundException("User not found [%s]".formatted(userId)));
        /** TODO : check if profile image found or not*/
        ProfileImage profileImage = profileImageRepository.findByUserId(userId);
        return s3Service.getObject(
                s3Bucket.getBasinda(),
                "profile-images/%s/%s".formatted(userId,profileImage.getImageUrl())
        );
    }

    @Override
    public byte[] downloadNidFontImage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->new ResourceNotFoundException("User not found [%s]".formatted(userId)));
        /** TODO : check if profile image found or not*/
        NidFontImage nidFontImage = nidFontImageRepository.findByUserId(userId);
        return s3Service.getObject(
                s3Bucket.getBasinda(),
                "nid-font-images/%s/%s".formatted(userId,nidFontImage.getImageUrl())
        );
    }

    @Override
    public byte[] downloadNidBackImage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->new ResourceNotFoundException("User not found [%s]".formatted(userId)));
        /** TODO : check if profile image found or not*/
        NidBackImage nidBackImage = nidBackImageRepository.findByUserId(userId);
        return s3Service.getObject(
                s3Bucket.getBasinda(),
                "nid-back-images/%s/%s".formatted(userId,nidBackImage.getImageUrl())
        );
    }

    /** for verify email sender*/
    private void sendVerificationEmail(User user, String applicationUrl)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "md.farid.ice@gmail.com";
        String senderName = "Teamwebsoft";
        String subject = "Verification";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Teamwebsoft";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());
        String verifyUrl = applicationUrl + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyUrl);

        helper.setText(content, true);

        mailSender.send(message);
    }

    /** for confirmation email sender*/
    private void sendConfirmEmail(User user, String applicationUrl)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "md.farid.ice@gmail.com";
        String senderName = "Teamwebsoft";
        String subject = "Confirmation";
        String content = "Dear [[name]],<br>"
                + "Your Account Approved You Can Login Now."
                + "Thank you,<br>"
                + "Teamwebsoft";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getName());
        helper.setText(content, true);
        mailSender.send(message);
    }
}