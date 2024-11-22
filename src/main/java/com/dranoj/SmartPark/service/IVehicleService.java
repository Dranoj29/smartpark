package com.dranoj.SmartPark.service;

import com.dranoj.SmartPark.model.request.VehicleRequestDTO;
import com.dranoj.SmartPark.model.response.VehicleDTO;

public interface IVehicleService {

    VehicleDTO findById(long id);

    VehicleDTO save(VehicleRequestDTO vehicleRequestDTO);
}
