package edu.sliit.service.impl;

import edu.sliit.document.Payment;
import edu.sliit.document.User;
import edu.sliit.dto.GetPaymentDto;
import edu.sliit.dto.PaymentDto;
import edu.sliit.repository.PaymentRepository;
import edu.sliit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases for createPayment method

    /**
     * Positive case: Payment creation succeeds.
     */
    @Test
    void testCreatePayment_Success() {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setUserId("123");
        paymentDto.setPaymentDate(new Date());

        User user = new User();
        user.setUserId("123");
        user.setStatus("ACTIVE");

        when(userRepository.findByUserId(anyString())).thenReturn(user);
        when(paymentRepository.count()).thenReturn(5L);

        Payment payment = new Payment();
        payment.setPaymentId("PAY-6");
        payment.setNextPaymentDate(new Date());
        when(modelMapper.map(any(PaymentDto.class), eq(Payment.class))).thenReturn(payment);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        ResponseEntity<String> response = paymentService.createPayment(paymentDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Payment added successfully. PAY-6", response.getBody());
    }
    @Test
    void testCreatePayment_UserNotFound_Fail() {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setUserId("123");
        paymentDto.setPaymentDate(new Date());

        when(userRepository.findByUserId(anyString())).thenReturn(null);

        // This test is designed to fail because the method does not throw EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            paymentService.createPayment(paymentDto);
        }, "Expected EntityNotFoundException, but it wasn't thrown.");
    }

    /**
     * Null case: UserId is null in payment creation. This should fail because we expect IllegalArgumentException.
     */
    @Test
    void testCreatePayment_NullUserId_Fail() {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setUserId(null);  // UserId is null
        paymentDto.setPaymentDate(new Date());

        // This test is designed to fail because the method does not throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.createPayment(paymentDto);
        }, "Expected IllegalArgumentException, but it wasn't thrown.");
    }

    // Test cases for getAllPaymentsByUserId method

    /**
     * Positive case: Fetching payments by userId succeeds.
     */
    @Test
    void testGetAllPaymentsByUserId_Success() {
        List<Payment> paymentList = Arrays.asList(new Payment(), new Payment());

        when(paymentRepository.findByUserId(anyString())).thenReturn(paymentList);
        when(modelMapper.map(any(Payment.class), eq(GetPaymentDto.class))).thenReturn(new GetPaymentDto());

        List<GetPaymentDto> result = paymentService.getAllPaymentsByUserId("123");

        assertEquals(2, result.size());
    }

    /**
     * Negative case: No payments found for userId. This should fail because we expect an EntityNotFoundException.
     */
    @Test
    void testGetAllPaymentsByUserId_NoPaymentsFound_Fail() {
        when(paymentRepository.findByUserId(anyString())).thenReturn(Collections.emptyList());

        // This test is designed to fail because we expect an exception, but the method returns an empty list
        assertThrows(EntityNotFoundException.class, () -> {
            paymentService.getAllPaymentsByUserId("123");
        }, "Expected EntityNotFoundException, but it wasn't thrown.");
    }

    /**
     * Null case: userId is null for fetching payments. This should fail because we expect IllegalArgumentException.
     */
    @Test
    void testGetAllPaymentsByUserId_NullUserId_Fail() {
        // This test is designed to fail because the method does not throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.getAllPaymentsByUserId(null);
        }, "Expected IllegalArgumentException, but it wasn't thrown.");
    }

    // Test cases for getNextPaymentDueDate method

    /**
     * Positive case: Fetching next payment due date succeeds.
     */
    @Test
    void testGetNextPaymentDueDate_Success() {
        Payment payment = new Payment();
        payment.setNextPaymentDate(new Date());

        List<Payment> paymentList = Collections.singletonList(payment);

        when(paymentRepository.findByUserId(anyString())).thenReturn(paymentList);

        String nextPaymentDate = paymentService.getNextPaymentDueDate("123");

        assertNotNull(nextPaymentDate);
    }

    /**
     * Negative case: No payments found for next payment due date.
     */
    /**
     * Negative case: No payments found for next payment due date. This should fail because we expect an EntityNotFoundException.
     */
    @Test
    void testGetNextPaymentDueDate_NoPaymentsFound_Fail() {
        when(paymentRepository.findByUserId(anyString())).thenReturn(Collections.emptyList());

        // This test is designed to fail because we expect an exception, but the method might return an empty list
        assertThrows(EntityNotFoundException.class, () -> {
            paymentService.getNextPaymentDueDate("123");
        }, "Expected EntityNotFoundException, but it wasn't thrown.");
    }

    /**
     * Null case: userId is null for next payment due date. This should fail because we expect IllegalArgumentException.
     */
    @Test
    void testGetNextPaymentDueDate_NullUserId_Fail() {
        // This test is designed to fail because the method does not throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.getNextPaymentDueDate(null);
        }, "Expected IllegalArgumentException, but it wasn't thrown.");
    }
}
