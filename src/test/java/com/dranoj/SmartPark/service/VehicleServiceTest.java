package com.dranoj.SmartPark.service;

import com.dranoj.SmartPark.entity.Vehicle;
import com.dranoj.SmartPark.entity.VehicleType;
import com.dranoj.SmartPark.model.request.VehicleRequestDTO;
import com.dranoj.SmartPark.model.response.VehicleDTO;
import com.dranoj.SmartPark.repo.VehicleRepo;
import com.dranoj.SmartPark.repo.VehicleTypeRepo;
import com.dranoj.SmartPark.service.impl.VehicleServiceImpl;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VehicleServiceTest {

    @Mock
    private VehicleRepo vehicleRepo;

    @Mock
    private VehicleTypeRepo vehicleTypeRepo;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    VehicleRequestDTO vehicleRequestDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicleRequestDTO = new VehicleRequestDTO("ABC123", 1L, "Card");
    }


    @Test
    void findById_ThrowsEntityNotFoundException_WhenRepoReturnsEmpty(){
        when(vehicleRepo.findById(anyLong())).thenReturn(Optional.empty());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> {
                vehicleService.findById(1L);
            });
        assertEquals("Vehicle not found.", e.getMessage());
    }

    @Test
    void findById_ReturnVehicleDTO_WhenRepoReturnsNotEmpty(){
        when(vehicleRepo.findById(anyLong())).thenReturn(Optional.of(new Vehicle()));
        VehicleDTO vehicleDto = vehicleService.findById(1L);
        assertNotNull(vehicleDto);
    }

    @Test
    void save_ThrowsEntityExistsException_WhenLicensePlateAlreadyExists() {
        Vehicle vehicleResult = new Vehicle();
        when(vehicleRepo.findByLicensePlate(anyString())).thenReturn(vehicleResult);

        EntityExistsException e = assertThrows(EntityExistsException.class, () -> {
            vehicleService.save(vehicleRequestDTO);
        });
        assertEquals("License Plate already in used.", e.getMessage());
    }

    @Test
    void save_ThrowsEntityNotFoundException_WhenVehicleTypeDoesNotExist() {
        when(vehicleRepo.findByLicensePlate(anyString())).thenReturn(null);
        when(vehicleTypeRepo.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> {
            vehicleService.save(vehicleRequestDTO);
        });
        assertEquals("Vehicle Type doesn't exist.", e.getMessage());
    }

    @Test
    void save_ReturnsVehicleDTO_WhenVehicleSavedSuccessfully() {
        VehicleType vehicleType = new VehicleType(1L, "Car");
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(vehicleRequestDTO.getLicensePlate());
        vehicle.setType(vehicleType);
        vehicle.setOwnerName(vehicleRequestDTO.getOwnerName());

        when(vehicleRepo.findByLicensePlate(anyString())).thenReturn(null);
        when(vehicleTypeRepo.findById(anyLong())).thenReturn(Optional.of(vehicleType));
        when(vehicleRepo.save(any(Vehicle.class))).thenReturn(vehicle);

        VehicleDTO result = vehicleService.save(vehicleRequestDTO);

        assertNotNull(result);
        assertEquals(vehicleRequestDTO.getLicensePlate(), result.licensePlate());
        assertEquals(vehicleRequestDTO.getOwnerName(), result.ownerName());
    }

    @Test
    void save_ThrowsUnexpectedException_WhenUnexpectedErrorOccurs() {
        when(vehicleRepo.findByLicensePlate(vehicleRequestDTO.getLicensePlate())).thenThrow(new RuntimeException("Unexpected error"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            vehicleService.save(vehicleRequestDTO);
        });
        assertEquals("Unexpected error", exception.getMessage());
    }

    @Test
    void save_ThrowsNullPointerException_WhenVehicleRequestDTOIsNull() {
        assertThrows(NullPointerException.class, () -> {
            vehicleService.save(null);
        });
    }

    @Test
    void save_ConvertsToVehicleDTO_WhenVehicleSavedSuccessfully() {
        VehicleRequestDTO vehicleRequestDTO = new VehicleRequestDTO("ABC123", 1L, "John Doe");
        VehicleType vehicleType = new VehicleType(1L, "Car");
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(vehicleRequestDTO.getLicensePlate());
        vehicle.setType(vehicleType);
        vehicle.setOwnerName(vehicleRequestDTO.getOwnerName());

        when(vehicleRepo.findByLicensePlate(anyString())).thenReturn(null);
        when(vehicleTypeRepo.findById(anyLong())).thenReturn(Optional.of(vehicleType));
        when(vehicleRepo.save(any(Vehicle.class))).thenReturn(vehicle);

        VehicleDTO result = vehicleService.save(vehicleRequestDTO);

        assertNotNull(result);
        assertEquals(vehicleRequestDTO.getLicensePlate(), result.licensePlate());
        assertEquals(vehicleRequestDTO.getOwnerName(), result.ownerName());
        assertNotNull(result.type());
    }

    @Test
    void findAllVehicleType_ReturnsEmptyList_WhenNoVehicleTypesExist() {
        when(vehicleTypeRepo.findAll()).thenReturn(Collections.emptyList());
        List<VehicleType> result = vehicleService.findAllVehicleType();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllVehicleType_ReturnsListOfVehicleTypes_WhenVehicleTypesExist() {
        VehicleType type1 = new VehicleType(1L, "Car");
        VehicleType type2 = new VehicleType(2L, "Motorcycle");
        List<VehicleType> mockVehicleTypes = Arrays.asList(type1, type2);

        when(vehicleTypeRepo.findAll()).thenReturn(mockVehicleTypes);
        List<VehicleType> result = vehicleService.findAllVehicleType();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Car", result.get(0).getName());
        assertEquals("Motorcycle", result.get(1).getName());
    }

}
