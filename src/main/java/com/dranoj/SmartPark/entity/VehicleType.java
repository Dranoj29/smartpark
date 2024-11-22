package com.dranoj.SmartPark.entity;


import jakarta.persistence.*;

@Entity
@Table
public class VehicleType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 20)
    private String type;
}
