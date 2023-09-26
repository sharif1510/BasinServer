package com.basinda.models.entity;

import lombok.Data;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Data
@Entity
@Table(name = "districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "divisionid")
    @JsonIgnore
    private Long divisionId;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "districtid", referencedColumnName = "id")
    private List<Upozila> upozilas;
}