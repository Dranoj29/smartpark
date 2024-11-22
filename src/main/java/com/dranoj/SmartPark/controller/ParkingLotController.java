package com.dranoj.SmartPark.controller;

import com.dranoj.SmartPark.controller.interfaces.IParkingLotController;
import com.dranoj.SmartPark.model.request.CheckInOutRequestDTO;
import com.dranoj.SmartPark.model.request.ParkingLotRequestDTO;
import com.dranoj.SmartPark.model.response.ParkingLotDTO;
import com.dranoj.SmartPark.service.IParkingLotService;
import com.dranoj.SmartPark.util.ResponseUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/parking-lots")
public class ParkingLotController implements IParkingLotController {

    @Autowired
    private IParkingLotService parkingLotService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> findAll(){
        Map<String, Object> response = new HashMap<>();
        List<ParkingLotDTO> parkingLotsDTO = this.parkingLotService.findAll();
        response.put("message", "Success");
        response.put("parkingLots", parkingLotsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> find(@PathVariable long id){
        Map<String, Object> response = new HashMap<>();
        try {
            ParkingLotDTO parkingLotDTO = this.parkingLotService.findById(id);
            response.put("message", "Parking Lot found");
            response.put("parkingLot", parkingLotDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (EntityNotFoundException e){
            return ResponseUtil.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/register")
    @Override
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody ParkingLotRequestDTO parkingLotRequestDTO){
        try {
            ParkingLotDTO parkingLotDTO = this.parkingLotService.save(parkingLotRequestDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Parking Lot successfully registered.");
            response.put("parkingLot", parkingLotDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (EntityExistsException e){
            return ResponseUtil.buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/check-in")
    @Override
    public ResponseEntity<Map<String, Object>> checkInVehicle(@Valid @RequestBody CheckInOutRequestDTO checkInOutRequestDTO){
        Map<String, Object> response = new HashMap<>();
        try {
            ParkingLotDTO parkingLotDTO = this.parkingLotService.checkInVehicle(checkInOutRequestDTO);
            response.put("message", "Vehicle checked in successfully.");
            response.put("parkingLot", parkingLotDTO);
            return ResponseEntity.ok(response);
        }catch (EntityNotFoundException e){
            return ResponseUtil.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (IllegalStateException e) {
            return ResponseUtil.buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/check-out")
    @Override
    public ResponseEntity<Map<String, Object>> checkOutVehicle(@Valid @RequestBody CheckInOutRequestDTO checkInOutRequestDTO){
        Map<String, Object> response = new HashMap<>();
        try {
            ParkingLotDTO parkingLotDTO = this.parkingLotService.checkOutVehicle(checkInOutRequestDTO);
            response.put("message", "Vehicle checked out successfully.");
            response.put("parkingLot", parkingLotDTO);
            return ResponseEntity.ok(response);
        }catch (EntityNotFoundException e){
            return ResponseUtil.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (IllegalStateException e) {
            return ResponseUtil.buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
