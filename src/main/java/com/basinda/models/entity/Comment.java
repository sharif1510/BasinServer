package com.basinda.models.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ownerid")
    private String ownerId;
    private String content;
    @Column(name = "userid")
    private String userId;
}