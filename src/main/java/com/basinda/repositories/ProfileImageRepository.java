package com.basinda.repositories;

import com.basinda.models.entity.ProfileImage;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    ProfileImage findByUserId(Long id);
}