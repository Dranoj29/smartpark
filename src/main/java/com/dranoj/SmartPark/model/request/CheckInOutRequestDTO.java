package com.dranoj.SmartPark.model.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CheckInOutRequestDTO {
    @Positive(message = "Parking Lot Id should be positive number")
    private long parkingLotId;

    @Positive(message = "Vehicle Id should be positive number")
    private long vehicleId;
}
