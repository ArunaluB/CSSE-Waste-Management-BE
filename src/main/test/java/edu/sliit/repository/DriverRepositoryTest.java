package edu.sliit.repository;

import edu.sliit.document.Driver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class DriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepository;

    private Driver driver;

    @BeforeEach
    void setUp() {
        driver = new Driver(null, "D001", "John Doe", true);
        driverRepository.save(driver);
    }

    @AfterEach
    void tearDown() {
        driverRepository.deleteAll();
    }

    @Test
    void testFindByDriverId_Success() {
        Optional<Driver> foundDriver = driverRepository.findByDriverId("D001");
        assertTrue(foundDriver.isPresent());
        assertEquals("John Doe", foundDriver.get().getDriverName());
    }

    @Test
    void testFindByDriverId_NotFound() {
        Optional<Driver> foundDriver = driverRepository.findByDriverId("D002");
        assertFalse(foundDriver.isPresent());
    }
}
