package com.dranoj.SmartPark.controller;

import com.dranoj.SmartPark.entity.VehicleType;
import com.dranoj.SmartPark.model.request.VehicleRequestDTO;
import com.dranoj.SmartPark.model.response.VehicleDTO;
import com.dranoj.SmartPark.service.IVehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IVehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    private VehicleRequestDTO vehicleRequestDTO;
    private VehicleDTO vehicleDTO;

    @BeforeEach
    void setUp() {
        vehicleRequestDTO = new VehicleRequestDTO("ABC123", 1L, "Jonnard Baysa");
        vehicleDTO = new VehicleDTO(1L, "ABC123", new VehicleType(1, "CAR"), "Jonnard Baysa");
    }

    @Test
    void findById_ReturnsVehicleDTO_WhenVehicleExists() throws Exception {
        when(vehicleService.findById(1L)).thenReturn(vehicleDTO);

        mockMvc.perform(get("/v1/vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Vehicle found."))
                .andExpect(jsonPath("$.vehicle.id").value(1))
                .andExpect(jsonPath("$.vehicle.licensePlate").value("ABC123"))
                .andExpect(jsonPath("$.vehicle.ownerName").value("Jonnard Baysa"));

        verify(vehicleService).findById(1L);
    }

    @Test
    void findById_ReturnsNotFound_WhenVehicleDoesNotExist() throws Exception {
        when(vehicleService.findById(1L)).thenThrow(new EntityNotFoundException("Vehicle not found"));

        mockMvc.perform(get("/v1/vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Vehicle not found"));

        verify(vehicleService).findById(1L);
    }

    @Test
    void save_ReturnsVehicleDTO_WhenSuccessful() throws Exception {
        when(vehicleService.save(any(VehicleRequestDTO.class))).thenReturn(vehicleDTO);

        mockMvc.perform(post("/v1/vehicles/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Vehicle successfully registered."))
                .andExpect(jsonPath("$.vehicle.id").value(1))
                .andExpect(jsonPath("$.vehicle.licensePlate").value("ABC123"))
                .andExpect(jsonPath("$.vehicle.ownerName").value("Jonnard Baysa"));

        verify(vehicleService).save(any(VehicleRequestDTO.class));
    }

    @Test
    void save_ReturnsConflict_WhenVehicleAlreadyExists() throws Exception {
        when(vehicleService.save(any(VehicleRequestDTO.class))).thenThrow(new EntityExistsException("License Plate already in use"));

        mockMvc.perform(post("/v1/vehicles/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleRequestDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("License Plate already in use"));

        verify(vehicleService).save(any(VehicleRequestDTO.class));
    }

    @Test
    void findAllVehicleType_ReturnsListOfVehicleTypes() throws Exception {
        when(vehicleService.findAllVehicleType()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/vehicles/types")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.types").isArray())
                .andExpect(jsonPath("$.types").isEmpty());

        verify(vehicleService).findAllVehicleType();
    }
}
