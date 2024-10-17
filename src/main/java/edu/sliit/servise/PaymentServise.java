package edu.sliit.servise;


import edu.sliit.dto.GetPaymentDto;
import edu.sliit.dto.PaymentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentServise {
    ResponseEntity<String> addPayment(PaymentDto dto);
    List<GetPaymentDto> getAllPayment (String userid);
    String getNextPaymentDate(String userid);
}
