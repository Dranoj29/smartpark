package com.dranoj.SmartPark.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotRequestDTO {

    @NotBlank(message = "Lot Id is required")
    @Size(max = 50, message = "The maximum allowed length is 50 characters")
    private String lotId;

    @NotBlank(message = "Location is required")
    private String location;

    @Positive(message = "Capacity should be positive number")
    private int capacity;

}
