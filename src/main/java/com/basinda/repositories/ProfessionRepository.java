package com.basinda.repositories;

import com.basinda.models.entity.Profession;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Long> {

}