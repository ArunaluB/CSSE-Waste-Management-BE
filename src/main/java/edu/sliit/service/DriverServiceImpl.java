package edu.sliit.service;

import edu.sliit.document.Driver;
import edu.sliit.dto.DriverDto;
import edu.sliit.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Override
    public ResponseEntity<String> addDriver(DriverDto dto) {
        Driver newDriver = new Driver(null, dto.getDriverId(), dto.getDriverName(), dto.isAvailable());
        driverRepository.save(newDriver);
        return new ResponseEntity<>("Driver added successfully", HttpStatus.CREATED);
    }

    @Override
    public DriverDto getDriverById(String driverId) {
        Optional<Driver> driver = driverRepository.findByDriverId(driverId);
        return driver.map(d -> new DriverDto(d.getDriverId(), d.getDriverName(), d.isAvailable()))
                .orElse(null);
    }

    @Override
    public List<DriverDto> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(driver -> new DriverDto(driver.getDriverId(), driver.getDriverName(), driver.isAvailable()))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> updateDriver(DriverDto dto) {
        Optional<Driver> existingDriver = driverRepository.findByDriverId(dto.getDriverId());
        if (existingDriver.isPresent()) {
            Driver updatedDriver = existingDriver.get();
            updatedDriver.setDriverName(dto.getDriverName());
            updatedDriver.setAvailable(dto.isAvailable());
            driverRepository.save(updatedDriver);
            return new ResponseEntity<>("Driver updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Driver not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> deleteDriver(String driverId) {
        Optional<Driver> existingDriver = driverRepository.findByDriverId(driverId);
        if (existingDriver.isPresent()) {
            driverRepository.delete(existingDriver.get());
            return new ResponseEntity<>("Driver deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Driver not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> deleteAllDrivers() {
        driverRepository.deleteAll();
        return ResponseEntity.ok("All drivers deleted successfully.");
    }
}
