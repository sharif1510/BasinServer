package com.basinda.models.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Setter
@Getter
@Entity
@Table(name = "pourosovas")
public class Pourosova {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @Column(name = "upozilaid")
    private Long upozilaId;
}