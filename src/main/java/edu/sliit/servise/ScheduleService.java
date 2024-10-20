package edu.sliit.servise;

import edu.sliit.dto.ScheduleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ScheduleService {
    ResponseEntity<String> addSchedule(ScheduleDto dto);
    List<ScheduleDto> getAllSchedules();
    ScheduleDto getScheduleById(String scheduleId);
    ResponseEntity<String> updateSchedule(ScheduleDto dto);
    ResponseEntity<String> deleteSchedule(String scheduleId);
    ResponseEntity<String> deleteAllSchedules();
}
