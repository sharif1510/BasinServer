package com.basinda.repositories;

import com.basinda.models.entity.AdminUser;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    List<AdminUser> findByEmail(String email);
    List<AdminUser> findByPhone(String phone);
}