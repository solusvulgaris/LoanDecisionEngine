package com.ak.loanengine.loanengine.data;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A persistence Segment Entity
 * lightweight class persisted to a SEGMENT table in a H2 DB
 */
@Getter
@Entity
@Table(name = "SEGMENT")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEGMENT_ID")
    private long id;

    @Column(name = "SEGMENT_NAME")
    private String name;

    @Column(name = "CREDIT_MODIFIER")
    private int modifier;

    public Segment() {
    }

    @Override
    public String toString() {
        return "Segment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", modifier=" + modifier +
                '}';
    }
}
