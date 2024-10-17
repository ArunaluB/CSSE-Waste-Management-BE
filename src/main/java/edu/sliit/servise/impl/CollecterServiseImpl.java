package edu.sliit.servise.impl;

import edu.sliit.dto.CollecterDto;
import edu.sliit.dto.GetPaymentDto;
import edu.sliit.servise.CollecterServise;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollecterServiseImpl implements CollecterServise {
    @Override
    public ResponseEntity<String> addCollecter(CollecterDto dto) {
        return null;
    }

    @Override
    public List<GetPaymentDto> getAllCollection(String userid) {
        return null;
    }
}
