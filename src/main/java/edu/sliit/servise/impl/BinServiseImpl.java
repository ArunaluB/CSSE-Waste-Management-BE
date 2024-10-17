package edu.sliit.servise.impl;

import edu.sliit.dto.BinDto;
import edu.sliit.dto.GetBinDto;
import edu.sliit.servise.BinServise;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinServiseImpl implements BinServise {
    @Override
    public ResponseEntity<String> addCollecter(BinDto dto) {
        return null;
    }

    @Override
    public List<GetBinDto> getAllCollection(String userid) {
        return null;
    }
}
