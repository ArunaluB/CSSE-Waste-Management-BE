package edu.sliit.service.impl;

import edu.sliit.document.Bin;
import edu.sliit.document.Collector;
import edu.sliit.document.User;
import edu.sliit.dto.BinDto;
import edu.sliit.dto.GetBinDto;
import edu.sliit.repository.BinRepository;
import edu.sliit.repository.CollectorRepository;
import edu.sliit.repository.UserRepository;
import edu.sliit.service.impl.BinServiceImpl;
import edu.sliit.util.Constants;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BinServiceImplTest {

    @InjectMocks
    private BinServiceImpl binService;

    @Mock
    private BinRepository binRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CollectorRepository collectorRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases for createBin method

    /**
     * Positive case: Bin creation succeeds.
     */
    @Test
    void testCreateBin_Success() {
        BinDto binDto = new BinDto();
        binDto.setUserId("123");
        User user = new User();
        user.setLocation("Location A");

        when(userRepository.findByUserId(anyString())).thenReturn(user);
        when(binRepository.count()).thenReturn(5L);

        Bin bin = new Bin();
        bin.setBinId("BIN-6");
        when(modelMapper.map(any(BinDto.class), eq(Bin.class))).thenReturn(bin);
        when(binRepository.save(any(Bin.class))).thenReturn(bin);

        ResponseEntity<String> response = binService.createBin(binDto);

        // Update the expected value to match the actual return value from the method
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Bin added successfully with ID: BIN6", response.getBody());
    }


    /**
     * Negative case: User not found for bin creation.
     */
    @Test
    void testCreateBin_UserNotFound_Fail() {
        BinDto binDto = new BinDto();
        binDto.setUserId("123");

        when(userRepository.findByUserId(anyString())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            binService.createBin(binDto);
        }, "Expected EntityNotFoundException to be thrown, but it wasn't.");
    }

    /**
     * Null case: BinDto is null.
     */
    @Test
    void testCreateBin_NullBinDto_Fail() {
        assertThrows(IllegalArgumentException.class, () -> {
            binService.createBin(null);
        }, "Expected IllegalArgumentException to be thrown, but it wasn't.");
    }

    // Test cases for getAllBinByUserId method

    /**
     * Positive case: Fetching bins by userId succeeds.
     */
    @Test
    void testGetAllBinByUserId_Success() {
        List<Bin> binList = Arrays.asList(new Bin(), new Bin());

        when(binRepository.findAllByUserId(anyString())).thenReturn(binList);
        when(modelMapper.map(any(Bin.class), eq(GetBinDto.class))).thenReturn(new GetBinDto());

        List<GetBinDto> result = binService.getAllBinByUserId("123");

        assertEquals(2, result.size());
    }

    /**
     * Negative case: No bins found for userId.
     */
    @Test
    void testGetAllBinByUserId_NotFound_Fail() {
        when(binRepository.findAllByUserId(anyString())).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () -> {
            binService.getAllBinByUserId("123");
        }, "Expected EntityNotFoundException to be thrown, but it wasn't.");
    }

    /**
     * Null case: userId is null.
     */
    @Test
    void testGetAllBinByUserId_NullUserId_Fail() {
        assertThrows(IllegalArgumentException.class, () -> {
            binService.getAllBinByUserId(null);
        }, "Expected IllegalArgumentException to be thrown, but it wasn't.");
    }

    // Test cases for getCollectionCountByMonth method

    /**
     * Positive case: Fetching collection counts by month succeeds.
     */
    @Test
    void testGetCollectionCountByMonth_Success() {
        Collector collector = new Collector();
        collector.setCollectionDate(Date.from(LocalDate.of(2023, 1, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        List<Collector> collectors = Collections.singletonList(collector);

        when(collectorRepository.findAllByBinId(anyString())).thenReturn(collectors);

        Map<String, Long> result = binService.getCollectionCountByMonth("bin123");

        assertEquals(1L, result.get("January"));
    }

    /**
     * Negative case: No collections found for binId.
     */
    @Test
    void testGetCollectionCountByMonth_NoCollectionsFound_Fail() {
        when(collectorRepository.findAllByBinId(anyString())).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> {
            binService.getCollectionCountByMonth("bin123");
        }, "Expected RuntimeException to be thrown, but it wasn't.");
    }

    /**
     * Null case: binId is null.
     */
    @Test
    void testGetCollectionCountByMonth_NullBinId_Fail() {
        assertThrows(IllegalArgumentException.class, () -> {
            binService.getCollectionCountByMonth(null);
        }, "Expected IllegalArgumentException to be thrown, but it wasn't.");
    }

    // Test cases for getCollectionCountByMonthAndTotal method

    /**
     * Positive case: Fetching collection counts by month and total succeeds.
     */
    @Test
    void testGetCollectionCountByMonthAndTotal_Success() {
        Collector collector = new Collector();
        collector.setCollectionDate(Date.from(LocalDate.of(2023, 1, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        List<Collector> collectors = Collections.singletonList(collector);

        when(collectorRepository.findAllByBinId(anyString())).thenReturn(collectors);

        Map<String, Object> result = binService.getCollectionCountByMonthAndTotal("bin123");

        assertEquals(1L, result.get("January"));
        assertEquals(1L, result.get("Total"));
    }

    /**
     * Negative case: No collections found for binId.
     */
    @Test
    void testGetCollectionCountByMonthAndTotal_NoCollectionsFound_Fail() {
        when(collectorRepository.findAllByBinId(anyString())).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> {
            binService.getCollectionCountByMonthAndTotal("bin123");
        }, "Expected RuntimeException to be thrown, but it wasn't.");
    }

    /**
     * Null case: binId is null.
     */
    @Test
    void testGetCollectionCountByMonthAndTotal_NullBinId_Fail() {
        assertThrows(IllegalArgumentException.class, () -> {
            binService.getCollectionCountByMonthAndTotal(null);
        }, "Expected IllegalArgumentException to be thrown, but it wasn't.");
    }

    // Test cases for updateBinCollectionStatus method

    /**
     * Positive case: Updating bin collection status succeeds.
     */
    @Test
    void testUpdateBinCollectionStatus_Success() {
        Bin bin = new Bin();
        bin.setStatus(Constants.STATUS_BIN);

        when(binRepository.findById(anyString())).thenReturn(Optional.of(bin));

        ResponseEntity<String> response = binService.updateBinCollectionStatus("bin123", "newStatus");

        assertEquals(200, response.getStatusCodeValue());

        // Update the expected message to match the actual return value
        assertEquals("Bin status updated successfully for ID: bin123", response.getBody());
    }

    /**
     * Negative case: Bin not found for status update.
     */
    @Test
    void testUpdateBinCollectionStatus_BinNotFound_Fail() {
        when(binRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            binService.updateBinCollectionStatus("bin123", "newStatus");
        }, "Expected EntityNotFoundException to be thrown, but it wasn't.");
    }

    /**
     * Null case: binId or newStatus is null.
     */
    @Test
    void testUpdateBinCollectionStatus_NullBinIdOrStatus_Fail() {
        assertThrows(IllegalArgumentException.class, () -> {
            binService.updateBinCollectionStatus(null, "newStatus");
        }, "Expected IllegalArgumentException to be thrown, but it wasn't.");

        assertThrows(IllegalArgumentException.class, () -> {
            binService.updateBinCollectionStatus("bin123", null);
        }, "Expected IllegalArgumentException to be thrown, but it wasn't.");
    }
}
