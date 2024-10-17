package edu.sliit.servise;

import edu.sliit.dto.CollecterDto;
import edu.sliit.dto.GetPaymentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CollecterServise {
    ResponseEntity<String> addCollecter(CollecterDto dto);
    List<GetPaymentDto>getAllCollection (String userid);
}
