package com.basinda.repositories;

import com.basinda.models.entity.Pourosova;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PourosovaRepository extends JpaRepository<Pourosova, Long> {
    List<Pourosova> findByUpozilaId(Long divisionId);
}