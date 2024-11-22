package com.dranoj.SmartPark.repo;

import com.dranoj.SmartPark.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepo extends JpaRepository<ParkingLot, Long> {

    ParkingLot findByLotId(String lotId);
}
