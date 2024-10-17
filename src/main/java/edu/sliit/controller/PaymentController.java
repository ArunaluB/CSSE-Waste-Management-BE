package edu.sliit.controller;

import edu.sliit.dto.GetPaymentDto;
import edu.sliit.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.sliit.servise.PaymentServise;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/Payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServise paymentServise;

    @PostMapping("addpayment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addPayment (@RequestBody PaymentDto dto ) {
        log.info("User Registration for [{}]", dto);
        return paymentServise.addPayment(dto);
    }

    @GetMapping("getdetails")
    public List<GetPaymentDto> getAllDetails (String userid){
        return paymentServise.getAllPayment(userid);
    }

    @GetMapping("nextPayment")
    public String getNextPaymentDate (String userid){
        return paymentServise.getNextPaymentDate(userid);
    }

}

