package com.basinda.repositories;

import com.basinda.models.entity.District;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByDivisionId(Long divisionId);
}