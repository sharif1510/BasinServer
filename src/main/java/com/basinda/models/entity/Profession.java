package com.basinda.models.entity;

import lombok.*;
import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "professions")
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}