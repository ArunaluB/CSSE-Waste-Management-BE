package edu.sliit.controller;


import edu.sliit.dto.CollecterDto;
import edu.sliit.dto.GetPaymentDto;
import edu.sliit.servise.CollecterServise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/collecter")
@RequiredArgsConstructor
public class CollecterController {

    private final CollecterServise collecterServise;

    @PostMapping("addcollecter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addCollecter (@RequestBody CollecterDto dto ) {
        log.info("User Registration for [{}]", dto);
        return collecterServise.addCollecter(dto);
    }

    @GetMapping("getcollecterdetails")
    public List<GetPaymentDto> getAllDetails (String userid){
        return collecterServise.getAllCollection(userid);
    }
}
