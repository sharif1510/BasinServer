package com.basinda.models.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import com.basinda.models.eUserType;
import com.basinda.models.eGenderType;
import com.basinda.models.eStatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userid")
    private String userId;
    private String nid;
    private String name;
    @Column(name = "fathername")
    private String fatherName;
    @Column(name = "mothername")
    private String motherName;
    @Column(name = "gendertype")
    private eGenderType genderType;
    private Date birthday;
    private String phone;
    private String email;
    private String profession;
    private String division;
    private String district;
    private String upozilla;
    private String pourosova;
    private String area;
    @Column(name = "wordno")
    private String wordNo;
    @Column(name = "postcode")
    private String postCode;
    @Column(name = "holdingnumber")
    private String holdingNumber;
    @JsonIgnore
    private String password;
    @Column(name = "usertype")
    private eUserType userType;
    @Column(name = "verificationcode", length = 64)
    private String verificationCode;
    private Boolean enabled;
    @Column(name = "isregistered")
    private eStatusType isRegistered;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private List<Flat> flats;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_for_profile", referencedColumnName = "id")
    private ProfileImage profileImage;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_for_nid_font", referencedColumnName = "id")
    private NidFontImage fontImage;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_for_nid_back", referencedColumnName = "id")
    private NidBackImage backImage;

    /**@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private List<Comment> comments1;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ownerid", referencedColumnName = "id")
    private List<Comment> comments2;*/
}