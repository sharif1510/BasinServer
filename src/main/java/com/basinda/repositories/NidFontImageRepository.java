package com.basinda.repositories;

import com.basinda.models.entity.NidFontImage;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NidFontImageRepository extends JpaRepository<NidFontImage, Long> {
    NidFontImage findByUserId(Long id);
}