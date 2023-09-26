package com.basinda.config;

import com.basinda.models.eStatusType;
import com.basinda.models.entity.AdminUser;
import com.basinda.models.entity.User;
import com.basinda.repositories.AdminUserRepository;
import org.springframework.stereotype.Service;
import com.basinda.repositories.UserRepository;
import com.basinda.exceptions.ResourceNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.ArrayList;

@Service
public class UserLoadService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AdminUserRepository adminUserRepository;

    public UserLoadService(UserRepository userRepository, AdminUserRepository adminUserRepository) {
        this.userRepository = userRepository;
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, ResourceNotFoundException {
        String phone, password = null;
        List<GrantedAuthority> authorities = null;
        String admin = username.substring(0,5);
        if (admin.equalsIgnoreCase("admin")){
            String adminPhone = username.substring(5);
            List<AdminUser> adminUsers = adminUserRepository.findByPhone(adminPhone);

            if (adminUsers.size() == 0){
                throw new ResourceNotFoundException("Admin is not registered yet. Please register first");
            }
            else{
                phone = adminUsers.get(0).getPhone();
                password = adminUsers.get(0).getPassword();
                authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(adminUsers.get(0).getRole().toString()));
            }
        }
        else{
            List<User> users = userRepository.findByPhone(username);

            if (users.size() == 0){
                throw new ResourceNotFoundException("User is not registered yet. Please register first");
            }
            else{
                if (!users.get(0).getEnabled() || !users.get(0).getIsRegistered().equals(eStatusType.eApproved)){
                    throw new ResourceNotFoundException("User is not enable or not approved yet.");
                }
                phone = users.get(0).getPhone();
                password = users.get(0).getPassword();
                authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(users.get(0).getUserType().toString()));
            }
        }
        return new org.springframework.security.core.userdetails.User(phone,password,authorities);
    }
}
