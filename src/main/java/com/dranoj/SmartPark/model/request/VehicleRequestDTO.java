package com.dranoj.SmartPark.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleRequestDTO {

    @NotBlank(message = "License Plate is required")
    @Pattern(regexp = "^[A-Za-z-0-9]+$", message = "Invalid License Plate, allowed only letters, numbers and dashes")
    private String licensePlate;

    @NotNull(message = "Type is required")
    private String type;

    @NotBlank(message = "Owner Name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid Owner Name, allowed only letters and spaces")
    private String ownerName;
}