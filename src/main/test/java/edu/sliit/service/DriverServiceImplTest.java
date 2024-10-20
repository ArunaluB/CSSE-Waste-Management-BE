package edu.sliit.service;

import edu.sliit.document.Driver;
import edu.sliit.dto.DriverDto;
import edu.sliit.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DriverServiceImplTest {

    @InjectMocks
    private DriverServiceImpl driverService;

    @Mock
    private DriverRepository driverRepository;

    private Driver driver;
    private DriverDto driverDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        driver = new Driver(null, "D001", "John Doe", true);
        driverDto = new DriverDto("D001", "John Doe", true);
    }

    @Test
    void testAddDriver_Success() {
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        ResponseEntity<String> response = driverService.addDriver(driverDto);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Driver added successfully", response.getBody());
    }

    @Test
    void testGetDriverById_Success() {
        when(driverRepository.findByDriverId("D001")).thenReturn(Optional.of(driver));

        DriverDto result = driverService.getDriverById("D001");
        
        assertNotNull(result);
        assertEquals("D001", result.getDriverId());
        assertEquals("John Doe", result.getDriverName());
    }

    @Test
    void testDeleteDriver_NotFound() {
        when(driverRepository.findByDriverId("D002")).thenReturn(Optional.empty());

        ResponseEntity<String> response = driverService.deleteDriver("D002");
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Driver not found", response.getBody());
    }
}
