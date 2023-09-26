package com.basinda.repositories;

import com.basinda.models.entity.NidBackImage;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NidBackImageRepository extends JpaRepository<NidBackImage, Long> {
    NidBackImage findByUserId(Long id);
}