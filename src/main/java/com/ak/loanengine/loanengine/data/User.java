package com.ak.loanengine.loanengine.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A persistence User Entity
 * lightweight class persisted to a USER table in a H2 DB
 */
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private long id;

    @Column(name = "PERSONAL_CODE")
    private String code;
/*
    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_SURNAME")
    private String surname;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phone;

    @Column(name = "DEBT")
    private boolean debt;

    @Column(name = "SEGMENT_ID")
    private long segmentId;
    */
}
