package edu.sliit.controller;

import edu.sliit.dto.DriverDto;
import edu.sliit.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DriverControllerTest {

    @InjectMocks
    private DriverController driverController;

    @Mock
    private DriverService driverService;

    private DriverDto driverDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        driverDto = new DriverDto("D001", "John Doe", true);
    }

    @Test
    void testAddDriver() {
        when(driverService.addDriver(any(DriverDto.class))).thenReturn(new ResponseEntity<>("Driver added successfully", HttpStatus.CREATED));

        ResponseEntity<String> response = driverController.addDriver(driverDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Driver added successfully", response.getBody());
    }

    @Test
    void testGetDriver() {
        when(driverService.getDriverById("D001")).thenReturn(driverDto);

        DriverDto result = driverController.getDriver("D001");

        assertNotNull(result);
        assertEquals("D001", result.getDriverId());
        assertEquals("John Doe", result.getDriverName());
    }

    @Test
    void testGetAllDrivers() {
        DriverDto anotherDriverDto = new DriverDto("D002", "Jane Doe", true);
        when(driverService.getAllDrivers()).thenReturn(Arrays.asList(driverDto, anotherDriverDto));

        List<DriverDto> result = driverController.getAllDrivers();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getDriverName());
        assertEquals("Jane Doe", result.get(1).getDriverName());
    }

    @Test
    void testDeleteDriver() {
        when(driverService.deleteDriver("D001")).thenReturn(new ResponseEntity<>("Driver deleted successfully", HttpStatus.OK));

        ResponseEntity<String> response = driverController.deleteDriver("D001");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver deleted successfully", response.getBody());
    }
}
