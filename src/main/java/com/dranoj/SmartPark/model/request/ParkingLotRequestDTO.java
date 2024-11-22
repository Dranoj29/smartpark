package com.dranoj.SmartPark.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingLotRequestDTO {

    @NotBlank(message = "Lot Id is required")
    @Size(max = 50, message = "The maximum allowed length is 50 characters")
    private String lotId;

    @NotBlank(message = "Location is required")
    private String location;

    @Positive(message = "Capacity should be positive number")
    private int capacity;

}
