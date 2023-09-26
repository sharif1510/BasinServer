package com.basinda.repositories;

import com.basinda.models.eUserType;
import com.basinda.models.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
    List<User> findByPhone(String phone);
    Optional<User> findByUserId(String userId);
    User findByVerificationCode(String code);
    List<User> findByUserType(eUserType eUserType);
    List<User> findByPourosova(String name);
    List<User> findByUpozilla(String name);
    List<User> findByDistrict(String name);
    List<User> findByDivision(String name);
}