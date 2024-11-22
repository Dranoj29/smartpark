package com.dranoj.SmartPark.controller;

import com.dranoj.SmartPark.model.request.CheckInOutRequestDTO;
import com.dranoj.SmartPark.model.request.ParkingLotRequestDTO;
import com.dranoj.SmartPark.model.response.ParkingLotDTO;
import com.dranoj.SmartPark.service.IParkingLotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkingLotController.class)
public class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IParkingLotService parkingLotService;

    @Autowired
    private ObjectMapper objectMapper;

    private ParkingLotDTO parkingLotDTO;
    private ParkingLotRequestDTO parkingLotRequestDTO;
    private CheckInOutRequestDTO checkInOutRequestDTO;

    @BeforeEach
    void setUp() {
        parkingLotDTO = new ParkingLotDTO(1L, "Lot001", "Test Location", 100, 0, 100, Collections.emptyList());
        parkingLotRequestDTO = new ParkingLotRequestDTO("Lot001", "Test Location", 100);
        checkInOutRequestDTO = new CheckInOutRequestDTO(1L, 1L);
    }

    @Test
    void findAll_ReturnsListOfParkingLots() throws Exception {
        when(parkingLotService.findAll()).thenReturn(Collections.singletonList(parkingLotDTO));

        mockMvc.perform(get("/v1/parking-lots")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.parkingLots[0].id").value(1))
                .andExpect(jsonPath("$.parkingLots[0].lotId").value("Lot001"));

        verify(parkingLotService).findAll();
    }

    @Test
    void findById_ReturnsParkingLot_WhenExists() throws Exception {
        when(parkingLotService.findById(1L)).thenReturn(parkingLotDTO);

        mockMvc.perform(get("/v1/parking-lots/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Parking Lot found"))
                .andExpect(jsonPath("$.parkingLot.id").value(1))
                .andExpect(jsonPath("$.parkingLot.lotId").value("Lot001"));

        verify(parkingLotService).findById(1L);
    }

    @Test
    void findById_ReturnsNotFound_WhenParkingLotDoesNotExist() throws Exception {
        when(parkingLotService.findById(1L)).thenThrow(new EntityNotFoundException("Parking Lot not found"));

        mockMvc.perform(get("/v1/parking-lots/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Parking Lot not found"));

        verify(parkingLotService).findById(1L);
    }

    @Test
    void register_ReturnsCreatedParkingLot_WhenSuccessful() throws Exception {
        when(parkingLotService.save(any(ParkingLotRequestDTO.class))).thenReturn(parkingLotDTO);

        mockMvc.perform(post("/v1/parking-lots/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parkingLotRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Parking Lot successfully registered."))
                .andExpect(jsonPath("$.parkingLot.id").value(1))
                .andExpect(jsonPath("$.parkingLot.lotId").value("Lot001"));

        verify(parkingLotService).save(any(ParkingLotRequestDTO.class));
    }

    @Test
    void register_ReturnsConflict_WhenParkingLotExists() throws Exception {
        when(parkingLotService.save(any(ParkingLotRequestDTO.class)))
                .thenThrow(new EntityExistsException("Lot ID already exists"));

        mockMvc.perform(post("/v1/parking-lots/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parkingLotRequestDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Lot ID already exists"));

        verify(parkingLotService).save(any(ParkingLotRequestDTO.class));
    }

    @Test
    void checkInVehicle_ReturnsParkingLot_WhenSuccessful() throws Exception {
        when(parkingLotService.checkInVehicle(any(CheckInOutRequestDTO.class))).thenReturn(parkingLotDTO);

        mockMvc.perform(post("/v1/parking-lots/check-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(checkInOutRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Vehicle checked in successfully."))
                .andExpect(jsonPath("$.parkingLot.id").value(1));

        verify(parkingLotService).checkInVehicle(any(CheckInOutRequestDTO.class));
    }

    @Test
    void checkInVehicle_ReturnsConflict_WhenLotIsFull() throws Exception {
        when(parkingLotService.checkInVehicle(any(CheckInOutRequestDTO.class)))
                .thenThrow(new IllegalStateException("Parking lot is full"));

        mockMvc.perform(post("/v1/parking-lots/check-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(checkInOutRequestDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Parking lot is full"));

        verify(parkingLotService).checkInVehicle(any(CheckInOutRequestDTO.class));
    }

    @Test
    void checkOutVehicle_ReturnsParkingLot_WhenSuccessful() throws Exception {
        when(parkingLotService.checkOutVehicle(any(CheckInOutRequestDTO.class))).thenReturn(parkingLotDTO);

        mockMvc.perform(post("/v1/parking-lots/check-out")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(checkInOutRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Vehicle checked out successfully."))
                .andExpect(jsonPath("$.parkingLot.id").value(1));

        verify(parkingLotService).checkOutVehicle(any(CheckInOutRequestDTO.class));
    }
}
