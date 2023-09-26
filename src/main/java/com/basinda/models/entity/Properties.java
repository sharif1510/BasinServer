package com.basinda.models.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "properties")
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String property;
    private String value;
}