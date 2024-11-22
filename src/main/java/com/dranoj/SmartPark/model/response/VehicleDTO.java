package com.dranoj.SmartPark.model.response;

public record VehicleDTO(
        long id,
        String licensePlate,
        String type,
        String ownerName
) {
}
