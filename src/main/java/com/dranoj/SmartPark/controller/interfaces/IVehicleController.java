package com.dranoj.SmartPark.controller.interfaces;

import com.dranoj.SmartPark.model.request.VehicleRequestDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "Vehicle")
public interface IVehicleController {
    @RequestBody(content = @Content(examples = {
            @ExampleObject(name="Sample Payload", value = "{\"licensePlate\":\"ABC100\",\"typeId\":1,\"ownerName\":\"Jonnard Baysa\"}"),
    }))
    ResponseEntity<Map<String, Object>> save(VehicleRequestDTO vehicleRequestDTO);

}
