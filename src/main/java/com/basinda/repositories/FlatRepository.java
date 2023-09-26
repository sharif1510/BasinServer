package com.basinda.repositories;

import com.basinda.models.eFlatType;
import com.basinda.models.entity.Flat;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Long> {
    List<Flat> findByFlatTypeNot(eFlatType eFlatType);

    List<Flat> findByUserId(Long id);
}