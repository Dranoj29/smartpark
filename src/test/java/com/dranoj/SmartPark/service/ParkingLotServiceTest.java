package com.dranoj.SmartPark.service;

import com.dranoj.SmartPark.entity.ParkingLot;
import com.dranoj.SmartPark.entity.Vehicle;
import com.dranoj.SmartPark.entity.VehicleType;
import com.dranoj.SmartPark.model.request.CheckInOutRequestDTO;
import com.dranoj.SmartPark.model.request.ParkingLotRequestDTO;
import com.dranoj.SmartPark.model.response.ParkingLotDTO;
import com.dranoj.SmartPark.repo.ParkingLotRepo;
import com.dranoj.SmartPark.repo.VehicleRepo;
import com.dranoj.SmartPark.service.impl.ParkingLotServiceImpl;
import com.dranoj.SmartPark.util.DataUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ParkingLotServiceTest {

    @Mock
    private ParkingLotRepo parkingLotRepo;

    @Mock
    private VehicleRepo vehicleRepo;

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    private ParkingLot lot1;
    private ParkingLot lot2;
    private ParkingLotDTO dto1;
    private ParkingLotDTO dto2;
    private ParkingLotRequestDTO parkingLotRequestDTO;
    private CheckInOutRequestDTO checkInOutRequestDTO;
    private Vehicle vehicle;
    private static MockedStatic<DataUtil> mockedDataUtil;

    @BeforeAll
    static void setUpStaticMock() {
        mockedDataUtil = mockStatic(DataUtil.class);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        lot1 = new ParkingLot(1L, "LotA001", "TEST1", 50, new ArrayList<>());
        lot2 = new ParkingLot(2L, "LotA002", "TEST2", 100, new ArrayList<>());
        dto1 = new ParkingLotDTO(1L, "LotA003", "TEST3", 50, 0, 100, Collections.emptyList());
        dto2 = new ParkingLotDTO(2L, "LotA004", "TEST4",100, 0, 100, Collections.emptyList());
        parkingLotRequestDTO = new ParkingLotRequestDTO("Lot1", "Location1", 100);
        checkInOutRequestDTO = new CheckInOutRequestDTO(1L, 1L);
        vehicle = new Vehicle(1L, "ABC123", "Jonnard", new VehicleType(1, "Car"), lot1);
    }

    @AfterAll
    static void tearDownStaticMock() {
        mockedDataUtil.close();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoParkingLotsExist() {
        when(parkingLotRepo.findAll()).thenReturn(Collections.emptyList());
        List<ParkingLotDTO> result = parkingLotService.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_ReturnsListOfParkingLotDTOs_WhenParkingLotsExist() {
        when(parkingLotRepo.findAll()).thenReturn(Arrays.asList(lot1, lot2));
        mockedDataUtil.when(() -> DataUtil.buildParkingLotDTO(lot1)).thenReturn(dto1);
        mockedDataUtil.when(() -> DataUtil.buildParkingLotDTO(lot2)).thenReturn(dto2);

        List<ParkingLotDTO> result = parkingLotService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }

    @Test
    void findById_ReturnsParkingLotDTO_WhenIdExists() {
        when(parkingLotRepo.findById(1L)).thenReturn(Optional.of(lot1));
        mockedDataUtil.when(() -> DataUtil.buildParkingLotDTO(any(ParkingLot.class))).thenReturn(dto1);

        ParkingLotDTO result = parkingLotService.findById(1L);
        assertNotNull(result);
        assertEquals(dto1, result);
    }

    @Test
    void save_ReturnsParkingLotDTO_WhenLotIdDoesNotExist() {
        when(parkingLotRepo.findByLotId(parkingLotRequestDTO.getLotId())).thenReturn(null);
        mockedDataUtil.when(() -> DataUtil.buildParkingLotDTO(any(ParkingLot.class))).thenReturn(dto1);

        ParkingLotDTO result = parkingLotService.save(parkingLotRequestDTO);

        verify(parkingLotRepo).save(any(ParkingLot.class));
        assertNotNull(result);
        assertEquals(dto1, result);
    }

    @Test
    void checkInVehicle_ReturnsParkingLotDTO_WhenCheckInIsSuccessful() {
        vehicle.setParkingLot(null);
        when(parkingLotRepo.findById(checkInOutRequestDTO.getParkingLotId())).thenReturn(Optional.of(lot1));
        when(vehicleRepo.findById(checkInOutRequestDTO.getVehicleId())).thenReturn(Optional.of(vehicle));
        mockedDataUtil.when(() -> DataUtil.buildParkingLotDTO(any(ParkingLot.class))).thenReturn(dto1);

        ParkingLotDTO result = parkingLotService.checkInVehicle(checkInOutRequestDTO);

        verify(parkingLotRepo).save(lot1);
        verify(vehicleRepo).save(vehicle);
        assertNotNull(result);
        assertEquals(dto1, result);
        assertEquals(lot1, vehicle.getParkingLot());
    }

    @Test
    void checkOutVehicle_ReturnsParkingLotDTO_WhenCheckOutIsSuccessful() {
        lot1.getVehicles().add(vehicle);
        lot1.setVehicles(lot1.getVehicles());
        when(parkingLotRepo.findById(checkInOutRequestDTO.getParkingLotId())).thenReturn(Optional.of(lot1));
        when(vehicleRepo.findById(checkInOutRequestDTO.getVehicleId())).thenReturn(Optional.of(vehicle));
        mockedDataUtil.when(() -> DataUtil.buildParkingLotDTO(any(ParkingLot.class))).thenReturn(dto1);

        ParkingLotDTO result = parkingLotService.checkOutVehicle(checkInOutRequestDTO);

        verify(parkingLotRepo).save(lot1);
        verify(vehicleRepo).save(vehicle);
        assertNotNull(result);
        assertEquals(dto1, result);
        assertNull(vehicle.getParkingLot());
        assertFalse(lot1.getVehicles().contains(vehicle));
    }
}
