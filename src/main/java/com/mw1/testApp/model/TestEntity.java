package com.mw1.testApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class TestEntity {
    @Id
    @GeneratedValue
    private int id;

    @Column(name="name")
    private String name;
}
