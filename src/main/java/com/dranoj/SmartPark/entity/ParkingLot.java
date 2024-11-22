package com.dranoj.SmartPark.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 50, nullable = false)
    private String lotId;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "parkingLot", fetch = FetchType.LAZY)
    private List<Vehicle> vehicles = new ArrayList<>();

    @Transient
    public int getOccupiedSpace() {
        return vehicles.size();
    }

    @Transient
    public int getAvailableSpace() {
        return capacity - vehicles.size();
    }

    @Transient
    public boolean isFull() {
        return vehicles.size() >= capacity;
    }


}
