package com.dranoj.SmartPark.service.impl;

import com.dranoj.SmartPark.entity.ParkingLot;
import com.dranoj.SmartPark.entity.Vehicle;
import com.dranoj.SmartPark.model.request.CheckInOutRequestDTO;
import com.dranoj.SmartPark.model.request.ParkingLotRequestDTO;
import com.dranoj.SmartPark.model.response.ParkingLotDTO;
import com.dranoj.SmartPark.repo.ParkingLotRepo;
import com.dranoj.SmartPark.repo.VehicleRepo;
import com.dranoj.SmartPark.service.IParkingLotService;
import com.dranoj.SmartPark.util.DataUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotServiceImpl implements IParkingLotService {

    @Autowired
    private ParkingLotRepo parkingLotRepo;

    @Autowired
    private VehicleRepo vehicleRepo;

    @Override
    public List<ParkingLotDTO> findAll(){
        List<ParkingLot> parkingLots = parkingLotRepo.findAll();

        return parkingLots.stream()
                .map(DataUtil::buildParkingLotDTO)
                .toList();
    }

    @Override
    public ParkingLotDTO findById(long id){
        ParkingLot parkingLot = parkingLotRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parking Lot not found."));
        return DataUtil.buildParkingLotDTO(parkingLot);
    }

    @Override
    public ParkingLotDTO save(ParkingLotRequestDTO parkingLotRequestDTO){
        ParkingLot parkingLotRecord = this.parkingLotRepo.findByLotId(parkingLotRequestDTO.getLotId());
        if(parkingLotRecord != null)
            throw new EntityExistsException("Lot Id already exist.");

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId(parkingLotRequestDTO.getLotId());
        parkingLot.setLocation(parkingLotRequestDTO.getLocation());
        parkingLot.setCapacity(parkingLotRequestDTO.getCapacity());

        parkingLotRepo.save(parkingLot);
        return DataUtil.buildParkingLotDTO(parkingLot);
    }

    @Override
    public ParkingLotDTO checkInVehicle(CheckInOutRequestDTO checkInOutRequestDTO){
        ParkingLot parkingLot = parkingLotRepo.findById(checkInOutRequestDTO.getParkingLotId())
                .orElseThrow(() -> new EntityNotFoundException("No Parking Lot found using parking lot id."));
        Vehicle vehicle = vehicleRepo.findById(checkInOutRequestDTO.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException("No Vehicle found using vehicle id."));

        if(vehicle.getParkingLot() != null)
            throw new IllegalStateException("The Vehicle has already been checked into a parking lot. checkout first before checking in.");

        if(parkingLot.isFull()){
            throw new IllegalStateException("Parking Lot is full");
        }

        parkingLot.getVehicles().add(vehicle);
        parkingLot.setVehicles(parkingLot.getVehicles());
        parkingLotRepo.save(parkingLot);

        vehicle.setParkingLot(parkingLot);
        vehicleRepo.save(vehicle);
        return DataUtil.buildParkingLotDTO(parkingLot);
    }

    @Override
    public ParkingLotDTO checkOutVehicle(CheckInOutRequestDTO checkInOutRequestDTO){
        ParkingLot  parkingLot = parkingLotRepo.findById(checkInOutRequestDTO.getParkingLotId())
                .orElseThrow(() -> new EntityNotFoundException("No Parking Lot found."));
        Vehicle vehicle = vehicleRepo.findById(checkInOutRequestDTO.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException("No Vehicle found."));

        boolean hasVehicle = parkingLot.getVehicles().stream()
                .anyMatch(vehicleItem -> vehicleItem.getId() == checkInOutRequestDTO.getVehicleId());

        if (!hasVehicle) throw new IllegalStateException("Vehicle is not checked into this parking lot.");

        parkingLot.getVehicles().remove(vehicle);
        parkingLot.setVehicles(parkingLot.getVehicles());
        parkingLotRepo.save(parkingLot);

        vehicle.setParkingLot(null);
        vehicleRepo.save(vehicle);
        return DataUtil.buildParkingLotDTO(parkingLot);
    }

}
