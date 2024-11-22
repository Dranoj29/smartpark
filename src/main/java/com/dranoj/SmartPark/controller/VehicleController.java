package com.dranoj.SmartPark.controller;

import com.dranoj.SmartPark.controller.interfaces.IVehicleController;
import com.dranoj.SmartPark.entity.VehicleType;
import com.dranoj.SmartPark.model.request.VehicleRequestDTO;
import com.dranoj.SmartPark.model.response.VehicleDTO;
import com.dranoj.SmartPark.service.IVehicleService;
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
@RequestMapping("v1/vehicles")
public class VehicleController implements IVehicleController {

    @Autowired
    private IVehicleService vehicleService;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@Valid @PathVariable long id){
        try {
            VehicleDTO vehicleDTO = this.vehicleService.findById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Vehicle found.");
            response.put("vehicle", vehicleDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (EntityNotFoundException e){
            return ResponseUtil.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/register")
    @Override
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO){
        try {
            VehicleDTO vehicleDTO = this.vehicleService.save(vehicleRequestDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Vehicle successfully save.");
            response.put("vehicle", vehicleDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (EntityExistsException e){
            return ResponseUtil.buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        }catch (EntityNotFoundException e){
            return ResponseUtil.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/types")
    public ResponseEntity<Map<String, Object>> findAllVehicleType(){
            List<VehicleType> vehicleTypes = this.vehicleService.findAllVehicleType();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Success");
            response.put("types", vehicleTypes);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
