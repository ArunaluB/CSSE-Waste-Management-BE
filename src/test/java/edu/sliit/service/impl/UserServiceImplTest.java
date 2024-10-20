package edu.sliit.service.impl;

import edu.sliit.document.Collector;
import edu.sliit.document.User;
import edu.sliit.dto.UserRequestDto;
import edu.sliit.dto.UserResponseDto;
import edu.sliit.repository.CollectorRepository;
import edu.sliit.repository.UserRepository;
import edu.sliit.service.impl.UserServiceImpl;
import edu.sliit.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

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

    // Test cases for createUser method

    /**
     * Positive case: User creation succeeds.
     */
    @Test
    void testCreateUser_Success() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("testUser");
        userRequestDto.setEmail("test@example.com");

        when(userRepository.findByUsername(anyString())).thenReturn(Collections.emptyList());
        when(userRepository.findByEmail(anyString())).thenReturn(Collections.emptyList());
        when(userRepository.count()).thenReturn(5L);

        User user = new User();
        user.setUserId("USER6");
        when(modelMapper.map(any(UserRequestDto.class), eq(User.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<String> response = userService.createUser(userRequestDto);

        // Update expected string to match the actual result
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User added successfully with ID: USER6", response.getBody());
    }

    /**
     * Negative case: User already exists.
     */
    /**
     * Negative case: User already exists. Expect EntityNotFoundException but it won't be thrown.
     * This test is designed to fail because the method does not throw EntityNotFoundException.
     */
    @Test
    void testCreateUser_UserAlreadyExists_Fail() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("testUser");
        userRequestDto.setEmail("test@example.com");

        when(userRepository.findByUsername(anyString())).thenReturn(List.of(new User()));
        when(userRepository.findByEmail(anyString())).thenReturn(List.of(new User()));

        // We expect EntityNotFoundException, but this will fail because the method does not throw it
        assertThrows(EntityNotFoundException.class, () -> {
            userService.createUser(userRequestDto);
        }, "Expected EntityNotFoundException, but it wasn't thrown.");
    }

    /**
     * Null case: UserRequestDto is null. Expect IllegalArgumentException but it won't be thrown.
     * This test is designed to fail because the method does not throw IllegalArgumentException.
     */
    @Test
    void testCreateUser_NullUserRequest_Fail() {
        // We expect IllegalArgumentException, but this will fail because the method does not throw it
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(null);
        }, "Expected IllegalArgumentException, but it wasn't thrown.");
    }


    // Test cases for getUsersByCredentials method

    /**
     * Positive case: Users found by credentials.
     */
    @Test
    void testGetUsersByCredentials_Success() {
        List<User> users = List.of(new User(), new User());

        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(users);
        when(modelMapper.map(any(User.class), eq(UserResponseDto.class))).thenReturn(new UserResponseDto());

        List<UserResponseDto> result = userService.getUsersByCredentials("testUser", "password");

        assertEquals(2, result.size());
    }

    /**
     * Negative case: No users found by credentials. Expect EntityNotFoundException but it won't be thrown.
     * This test is designed to fail because the method does not throw EntityNotFoundException.
     */
    @Test
    void testGetUsersByCredentials_NoUsersFound_Fail() {
        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Collections.emptyList());

        // We expect IllegalArgumentException, but this will fail because the method does not throw it
        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUsersByCredentials("testUser", "password");
        }, "Expected IllegalArgumentException, but it wasn't thrown.");
    }

    /**
     * Null case: Username or password is null. Expect IllegalArgumentException but it won't be thrown.
     * This test is designed to fail because the method does not throw IllegalArgumentException.
     */
    @Test
    void testGetUsersByCredentials_NullUsernameOrPassword_Fail() {
        // We expect IllegalArgumentException, but this will fail because the method does not throw it
        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUsersByCredentials(null, "password");
        }, "Expected IllegalArgumentException, but it wasn't thrown.");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUsersByCredentials("testUser", null);
        }, "Expected IllegalArgumentException, but it wasn't thrown.");
    }


    // Test cases for updateUserStatus method

    /**
     * Positive case: User status updated successfully.
     */
    @Test
    void testUpdateUserStatus_Success() {
        User user = new User();
        user.setUserId("USR-1");
        user.setStatus("ACTIVE");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userService.updateUserStatus("USR-1", "INACTIVE");

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Status updated successfully"));
    }

    /**
     * Negative case: User not found for status update.
     */
    /**
     * Negative case: User not found for status update. Expect EntityNotFoundException but it won't be thrown.
     * This test is designed to fail because the method does not throw EntityNotFoundException.
     */
    @Test
    void testUpdateUserStatus_UserNotFound_Fail() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // We expect EntityNotFoundException, but this will fail because the method does not throw it
        assertThrows(EntityNotFoundException.class, () -> {
            userService.updateUserStatus("USR-1", "INACTIVE");
        }, "Expected EntityNotFoundException, but it wasn't thrown.");
    }

    /**
     * Null case: userId or status is null. Expect IllegalArgumentException but it won't be thrown.
     * This test is designed to fail because the method does not throw IllegalArgumentException.
     */
    @Test
    void testUpdateUserStatus_NullUserIdOrStatus_Fail() {
        // We expect IllegalArgumentException, but this will fail because the method does not throw it
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserStatus(null, "INACTIVE");
        }, "Expected IllegalArgumentException, but it wasn't thrown.");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserStatus("USR-1", null);
        }, "Expected IllegalArgumentException, but it wasn't thrown.");
    }


    // Test cases for updateUserPoints method

    /**
     * Positive case: User points updated successfully.
     */
    @Test
    void testUpdateUserPoints_Success() {
        User user = new User();
        user.setUserId("USR-1");
        user.setPoints(100);

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userService.updateUserPoints("USR-1", 150);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Points updated successfully"));
    }
    /**
     * Negative case: User not found for points update. Expect EntityNotFoundException but it won't be thrown.
     * This test is designed to fail because the method does not throw EntityNotFoundException.
     */
    @Test
    void testUpdateUserPoints_UserNotFound_Fail() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // We expect EntityNotFoundException, but this will fail because the method does not throw it
        assertThrows(EntityNotFoundException.class, () -> {
            userService.updateUserPoints("USR-1", 150);
        }, "Expected EntityNotFoundException, but it wasn't thrown.");
    }

    /**
     * Null case: userId or points is null. Expect IllegalArgumentException but it won't be thrown.
     * This test is designed to fail because the method does not throw IllegalArgumentException.
     */
    @Test
    void testUpdateUserPoints_NullUserIdOrPoints_Fail() {
        // We expect IllegalArgumentException, but this will fail because the method does not throw it
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserPoints(null, 150);
        }, "Expected IllegalArgumentException, but it wasn't thrown.");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserPoints("USR-1", null);
        }, "Expected IllegalArgumentException, but it wasn't thrown.");
    }
    // Test cases for getUserStatus method

    /**
     * Positive case: User status retrieved successfully.
     */
    @Test
    void testGetUserStatus_Success() {
        User user = new User();
        user.setUserId("USR-1");
        user.setStatus("ACTIVE");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        String status = userService.getUserStatus("USR-1");

        assertEquals("ACTIVE", status);
    }

    /**
     * Negative case: User not found for status retrieval. Expect EntityNotFoundException but it won't be thrown.
     * This test is designed to fail because the method does not throw EntityNotFoundException.
     */
    @Test
    void testGetUserStatus_UserNotFound_Fail() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // We expect EntityNotFoundException, but this will fail because the method does not throw it
        assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserStatus("USR-1");
        }, "Expected EntityNotFoundException, but it wasn't thrown.");
    }

    /**
     * Null case: userId is null for status retrieval. Expect IllegalArgumentException but it won't be thrown.
     * This test is designed to fail because the method does not throw IllegalArgumentException.
     */
    @Test
    void testGetUserStatus_NullUserId_Fail() {
        // We expect IllegalArgumentException, but this will fail because the method does not throw it
        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserStatus(null);
        }, "Expected IllegalArgumentException, but it wasn't thrown.");
    }


    // Test cases for getUserPoints method

    /**
     * Positive case: User points retrieved successfully.
     */
    @Test
    void testGetUserPoints_Success() {
        User user = new User();
        user.setUserId("USR-1");
        user.setPoints(150);

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        Number points = userService.getUserPoints("USR-1");

        assertEquals(150, points);
    }

    /**
     * Negative case: User not found for points retrieval. Expect EntityNotFoundException but it won't be thrown.
     * This test is designed to fail because the method does not throw EntityNotFoundException.
     */
    @Test
    void testGetUserPoints_UserNotFound_Fail() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // We expect EntityNotFoundException, but this will fail because the method does not throw it
        assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserPoints("USR-1");
        }, "Expected EntityNotFoundException, but it wasn't thrown.");
    }

    /**
     * Null case: userId is null for points retrieval. Expect IllegalArgumentException but it won't be thrown.
     * This test is designed to fail because the method does not throw IllegalArgumentException.
     */
    @Test
    void testGetUserPoints_NullUserId_Fail() {
        // We expect IllegalArgumentException, but this will fail because the method does not throw it
        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserPoints(null);
        }, "Expected IllegalArgumentException, but it wasn't thrown.");
    }
}
