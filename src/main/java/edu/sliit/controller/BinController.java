package edu.sliit.controller;

import edu.sliit.dto.BinDto;
import edu.sliit.dto.GetBinDto;
import edu.sliit.servise.BinServise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/Bin")
@RequiredArgsConstructor
public class BinController {

    private final BinServise binServise;


    @PostMapping("addbin")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addBin (@RequestBody BinDto dto ) {
        log.info("User Registration for [{}]", dto);
        return binServise.addCollecter(dto);
    }

    @GetMapping("getbindetails")
    public List<GetBinDto> getAllDetails (String userid){
        return binServise.getAllCollection(userid);
    }

}
