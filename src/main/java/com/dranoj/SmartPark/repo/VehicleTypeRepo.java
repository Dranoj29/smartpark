package com.dranoj.SmartPark.repo;

import com.dranoj.SmartPark.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleTypeRepo extends JpaRepository<VehicleType, Long> {
}
