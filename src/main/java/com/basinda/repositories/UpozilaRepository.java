package com.basinda.repositories;

import com.basinda.models.entity.Upozila;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface UpozilaRepository extends JpaRepository<Upozila, Long> {
    List<Upozila> findByDistrictId(Long districtId);
}