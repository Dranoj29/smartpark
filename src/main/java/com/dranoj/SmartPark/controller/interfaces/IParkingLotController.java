package com.dranoj.SmartPark.controller.interfaces;

import com.dranoj.SmartPark.model.request.CheckInOutRequestDTO;
import com.dranoj.SmartPark.model.request.ParkingLotRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "Parking Lot")
public interface IParkingLotController {

    ResponseEntity<Map<String, Object>> register(
            @RequestBody(content = @Content(examples = {
                    @ExampleObject(name="Sample Payload", value = "{\"lotId\":\"LOT21345\",\"location\":\"Test Location\",\"capacity\":10}"),
            })) ParkingLotRequestDTO parkingLotRequestDTO);

    @Operation(summary = "Check in using parkingLot id and vehicle id")
    ResponseEntity<Map<String, Object>> checkInVehicle(
            @RequestBody(content = @Content(examples = {
                    @ExampleObject(name="Sample Payload", value = "{\"parkingLotId\":1,\"vehicleId\":1}"),
            })) CheckInOutRequestDTO checkInOutRequestDTO);

    @Operation(summary = "Check out using parkingLot id and vehicle id")
    ResponseEntity<Map<String, Object>> checkOutVehicle(
            @RequestBody(content = @Content(examples = {
                    @ExampleObject(name="Sample Payload", value = "{\"parkingLotId\":1,\"vehicleId\":1}"),
            })) CheckInOutRequestDTO checkInOutRequestDTO);
}
