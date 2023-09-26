package com.basinda.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profile_images")
@Setter
@Getter
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id_for_profile")
    private Long userId;
    private String imageUrl;
}