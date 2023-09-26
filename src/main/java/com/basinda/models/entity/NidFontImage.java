package com.basinda.models.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "nid_font_images")
@Setter
@Getter
public class NidFontImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id_for_nid_font")
    private Long userId;
    private String imageUrl;
}