package com.dranoj.SmartPark.service.impl;

import com.dranoj.SmartPark.entity.Vehicle;
import com.dranoj.SmartPark.entity.VehicleType;
import com.dranoj.SmartPark.model.request.VehicleRequestDTO;
import com.dranoj.SmartPark.model.response.VehicleDTO;
import com.dranoj.SmartPark.repo.VehicleRepo;
import com.dranoj.SmartPark.repo.VehicleTypeRepo;
import com.dranoj.SmartPark.service.IVehicleService;
import com.dranoj.SmartPark.util.DataUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements IVehicleService {

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private VehicleTypeRepo vehicleTypeRepo;

    @Override
    public VehicleDTO findById(long id){
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parking Lot not found."));

        return DataUtil.buidVehicleDTO(vehicle);
    }

    @Override
    public VehicleDTO save(VehicleRequestDTO vehicleRequestDTO){
        Vehicle vehicleResult = vehicleRepo.findByLicensePlate(vehicleRequestDTO.getLicensePlate());
        if(vehicleResult != null)
            throw new EntityExistsException("License Plate already in used");

        VehicleType vehicleType = vehicleTypeRepo.findById(vehicleRequestDTO.getTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle Type doesn't exist"));

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(vehicleRequestDTO.getLicensePlate());
        vehicle.setType(vehicleType);
        vehicle.setOwnerName(vehicleRequestDTO.getOwnerName());

        vehicleRepo.save(vehicle);
        return DataUtil.buidVehicleDTO(vehicle);
    }

    @Override
    public List<VehicleType> findAllVehicleType(){
        return vehicleTypeRepo.findAll();
    }
}
