package com.basinda.repositories;

import com.basinda.models.entity.FlatImage;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FlatImageRepository extends JpaRepository<FlatImage, Long> {
    List<FlatImage> findByFlatId(Long id);
}