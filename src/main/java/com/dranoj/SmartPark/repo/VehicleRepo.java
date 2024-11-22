package com.dranoj.SmartPark.repo;

import com.dranoj.SmartPark.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {

    Vehicle findByLicensePlate(String licensePate);
}
