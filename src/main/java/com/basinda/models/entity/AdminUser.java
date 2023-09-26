package com.basinda.models.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import com.basinda.models.eUserType;

@Entity
@Table(name = "admin")
@Setter
@Getter
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private eUserType role;
}