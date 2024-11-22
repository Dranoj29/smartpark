package com.dranoj.SmartPark.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckInOutRequestDTO {
    private long parkingLotId;
    private long vehicleId;
}
