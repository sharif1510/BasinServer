package com.basinda.repositories;

import com.basinda.models.entity.Division;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {

}