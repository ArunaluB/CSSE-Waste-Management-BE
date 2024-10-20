package edu.sliit.service;

import edu.sliit.dto.DriverDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DriverService {
    ResponseEntity<String> addDriver(DriverDto dto);
    DriverDto getDriverById(String driverId);
    List<DriverDto> getAllDrivers();
    ResponseEntity<String> updateDriver(DriverDto dto);
    ResponseEntity<String> deleteDriver(String driverId);
    ResponseEntity<String> deleteAllDrivers();
}
