package com.dranoj.SmartPark.service;

import com.dranoj.SmartPark.entity.VehicleType;
import com.dranoj.SmartPark.model.request.VehicleRequestDTO;
import com.dranoj.SmartPark.model.response.VehicleDTO;

import java.util.List;

public interface IVehicleService {

    VehicleDTO findById(long id);

    VehicleDTO save(VehicleRequestDTO vehicleRequestDTO);

    List<VehicleType> findAllVehicleType();
}
