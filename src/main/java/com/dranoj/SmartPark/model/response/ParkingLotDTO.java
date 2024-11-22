package com.dranoj.SmartPark.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public record ParkingLotDTO(
        long id,
        String lotId,
        String location,
        int capacity,
        int occupiedSpace,
        int availableSpace,
        List<VehicleDTO> vehicles
){

}
