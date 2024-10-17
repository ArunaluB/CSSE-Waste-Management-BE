package edu.sliit.servise.impl;

import edu.sliit.dto.GetPaymentDto;
import edu.sliit.dto.PaymentDto;
import edu.sliit.servise.PaymentServise;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiseImpl implements PaymentServise {
    @Override
    public ResponseEntity<String> addPayment(PaymentDto dto) {
        return null;
    }

    @Override
    public List<GetPaymentDto> getAllPayment(String userid) {
        return null;
    }

    @Override
    public String getNextPaymentDate(String userid) {
        return null;
    }
}
