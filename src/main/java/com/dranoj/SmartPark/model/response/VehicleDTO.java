package com.dranoj.SmartPark.model.response;

import com.dranoj.SmartPark.entity.VehicleType;

public record VehicleDTO(
        long id,
        String licensePlate,
        VehicleType type,
        String ownerName
) {
}
