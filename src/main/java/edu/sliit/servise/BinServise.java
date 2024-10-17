package edu.sliit.servise;

import edu.sliit.dto.BinDto;
import edu.sliit.dto.GetBinDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BinServise {
    ResponseEntity<String> addCollecter(BinDto dto);
    List<GetBinDto> getAllCollection (String userid);
}
