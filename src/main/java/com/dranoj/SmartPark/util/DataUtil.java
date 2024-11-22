package com.dranoj.SmartPark.util;

import com.dranoj.SmartPark.entity.ParkingLot;
import com.dranoj.SmartPark.entity.Vehicle;
import com.dranoj.SmartPark.model.response.ParkingLotDTO;
import com.dranoj.SmartPark.model.response.VehicleDTO;

import java.util.List;

public class DataUtil {

    public static ParkingLotDTO buildParkingLotDTO(ParkingLot parkingLot){

        List<VehicleDTO> vehicleDTOList = parkingLot.getVehicles().stream()
                .map(vehicle -> new VehicleDTO(
                        vehicle.getId(),
                        vehicle.getLicensePlate(),
                        vehicle.getType(),
                        vehicle.getOwnerName()
                ))
                .toList();

        return new ParkingLotDTO(
                parkingLot.getId(),
                parkingLot.getLotId(),
                parkingLot.getLocation(),
                parkingLot.getCapacity(),
                parkingLot.getOccupiedSpace(),
                parkingLot.getAvailableSpace(),
                vehicleDTOList
        );
    }

    public static VehicleDTO buidVehicleDTO(Vehicle vehicle){
        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getLicensePlate(),
                vehicle.getType(),
                vehicle.getOwnerName()
        );
    }
}
