package com.basinda.repositories;

import com.basinda.models.entity.Properties;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PropertiesRepository extends JpaRepository<Properties, Long> {
    List<Properties> findByProperty(String property);
}