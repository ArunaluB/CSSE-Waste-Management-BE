package edu.sliit.servise.impl;

import edu.sliit.document.Collector;
import edu.sliit.dto.CollecterDto;
import edu.sliit.dto.GetPaymentDto;
import edu.sliit.repository.CollecterRepository;
import edu.sliit.servise.CollecterServise;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollecterServiseImpl implements CollecterServise {

    private final CollecterRepository collecterRepository;
    private final ModelMapper modelMapper;
    @Override
    public ResponseEntity<String> addCollecter(CollecterDto dto) {
        long CollectorCount = collecterRepository.count();
        Collector CollectedEntity =modelMapper.map(dto, Collector.class);
        CollectedEntity.setCollectorId(CollectorCount + 1);
        collecterRepository.save(CollectedEntity);
        return ResponseEntity.ok("Collection added successfully with ID: " +CollectedEntity.getCollectorId());
    }

    @Override
    public List<GetPaymentDto> getAllCollection(String userid) {
        List<Collector> collectorList = collecterRepository.findAllByUserId(userid);
        return collectorList.stream()
                .map(collector -> modelMapper.map(collector, GetPaymentDto.class))
                .collect(Collectors.toList());
    }
}
