package com.basinda.models.entity;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "upozila")
public class Upozila {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @Column(name = "districtid")
    private Long districtId;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "upozilaid", referencedColumnName = "id")
    private List<Pourosova> pourosovas;
}