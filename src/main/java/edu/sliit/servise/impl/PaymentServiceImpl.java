package edu.sliit.servise.impl;

import edu.sliit.document.Payment;
import edu.sliit.dto.GetPaymentDto;
import edu.sliit.dto.PaymentDto;
import edu.sliit.repository.PaymentRepository;
import edu.sliit.repository.UserRepository;
import edu.sliit.servise.PaymentServise;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentServise {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public ResponseEntity<String> addPayment(PaymentDto dto) {

        userRepository.findById(dto.getUserId()).ifPresent(user -> {
            user.setStatus("Active");
            userRepository.save(user);
        });

        Date paymentDate = dto.getPaymentDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paymentDate);
        calendar.add(Calendar.MONTH, 1);
        Date nextPaymentDate = calendar.getTime();

        long paymentCount = paymentRepository.count();
        Payment paymentEntity = modelMapper.map(dto, Payment.class);
        paymentEntity.setPaymentId(paymentCount+1);
        paymentEntity.setNextPaymentDate(nextPaymentDate);
        paymentRepository.save(paymentEntity);
        return ResponseEntity.ok("Payment added successfully with ID: " +paymentEntity.getPaymentId());
    }

    @Override
    public List<GetPaymentDto> getAllPayment(String userid) {
        List<Payment> payments = paymentRepository.findByUserId(userid);
        return payments.stream()
                .map(payment -> modelMapper.map(payment, GetPaymentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getNextPaymentDate(String userid) {
        Optional<Payment> PaymentsOptional = paymentRepository.findById(userid);
        return String.valueOf(PaymentsOptional.map(Payment::getNextPaymentDate));
    }
}

