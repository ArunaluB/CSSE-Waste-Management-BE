package edu.sliit.servise.impl;

import edu.sliit.document.Bin;
import edu.sliit.dto.BinDto;
import edu.sliit.dto.GetBinDto;
import edu.sliit.repository.BinRepository;
import edu.sliit.servise.BinServise;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BinServiseImpl implements BinServise {

    private final BinRepository binRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<String> addBin(BinDto dto) {
        long BinCount = binRepository.count();
        Bin BinEntity = modelMapper.map(dto,Bin.class);
        BinEntity.setBinId(BinCount + 1);
        binRepository.save(BinEntity);
        return ResponseEntity.ok("Collection added successfully with ID: " +BinEntity.getBinId());
    }

    @Override
    public List<GetBinDto> getAllBin(String userid) {
        List<Bin> BinList = binRepository.findAllByUserId(userid);
        return BinList.stream()
                .map(collector -> modelMapper.map(collector, GetBinDto.class))
                .collect(Collectors.toList());
    }
}
