package com.dranoj.SmartPark.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 50, nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private String  ownerName;

    @OneToOne
    private VehicleType type;

    @ManyToOne
    private ParkingLot parkingLot;
}
