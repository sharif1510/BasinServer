package com.basinda.models.entity;

import lombok.*;
import jakarta.persistence.*;
import com.basinda.models.eFlatType;
import com.basinda.models.eAnswerType;
import org.checkerframework.checker.calledmethods.qual.RequiresCalledMethods;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "flats")
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "housename")
    private String houseName;
    private String address;
    @Column(name = "flatside")
    private String flatSide;
    private String floor;
    @Column(name = "flattype")
    private eFlatType flatType;
    private String sqr;
    private String bed;
    private String drawing;
    private String dining;
    private String washroom;
    private String kitchen;
    private String baranda;
    private eAnswerType lift;
    private eAnswerType parking;
    private eAnswerType guard;
    private eAnswerType gas;
    private Long rent;
    @Column(name = "currentmeter")
    private String currentMeter;
    @Column(name = "userid")
    private Long userId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_user", referencedColumnName = "id")
    private List<User> requestUser = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "approved_user", referencedColumnName = "id")
    private User approvedUser;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "flatid", referencedColumnName = "id")
    private FlatImage flatImage;
}