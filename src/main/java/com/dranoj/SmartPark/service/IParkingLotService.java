package com.dranoj.SmartPark.service;

import com.dranoj.SmartPark.model.request.CheckInOutRequestDTO;
import com.dranoj.SmartPark.model.request.ParkingLotRequestDTO;
import com.dranoj.SmartPark.model.response.ParkingLotDTO;

import java.util.List;

public interface IParkingLotService {
    List<ParkingLotDTO> findAll();

    ParkingLotDTO findById(long id);

    ParkingLotDTO save(ParkingLotRequestDTO parkingLotRequestDTO);

    ParkingLotDTO checkInVehicle(CheckInOutRequestDTO checkInOutRequestDTO);

    ParkingLotDTO checkOutVehicle(CheckInOutRequestDTO checkInOutRequestDTO);
}
