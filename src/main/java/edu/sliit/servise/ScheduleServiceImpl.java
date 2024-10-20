package edu.sliit.servise;

import edu.sliit.document.Schedule;
import edu.sliit.dto.ScheduleDto;
import edu.sliit.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ResponseEntity<String> addSchedule(ScheduleDto dto) {
        Schedule newSchedule = new Schedule(null, dto.getScheduleId(), dto.getSmartBins(), dto.getDriverId(), dto.getTime(), dto.getRoute());
        scheduleRepository.save(newSchedule);
        return new ResponseEntity<>("Schedule added successfully", HttpStatus.CREATED);
    }

    @Override
    public List<ScheduleDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleDto(schedule.getScheduleId(), schedule.getSmartBins(), schedule.getDriverId(), schedule.getTime(), schedule.getRoute()))
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDto getScheduleById(String scheduleId) {
        Optional<Schedule> schedule = scheduleRepository.findByScheduleId(scheduleId);
        return schedule.map(s -> new ScheduleDto(s.getScheduleId(), s.getSmartBins(), s.getDriverId(), s.getTime(), s.getRoute()))
                .orElse(null);
    }

    @Override
    public ResponseEntity<String> updateSchedule(ScheduleDto dto) {
        Optional<Schedule> existingSchedule = scheduleRepository.findByScheduleId(dto.getScheduleId());
        if (existingSchedule.isPresent()) {
            Schedule updatedSchedule = existingSchedule.get();
            updatedSchedule.setSmartBins(dto.getSmartBins());
            updatedSchedule.setDriverId(dto.getDriverId());
            updatedSchedule.setTime(dto.getTime());
            updatedSchedule.setRoute(dto.getRoute());
            scheduleRepository.save(updatedSchedule);
            return new ResponseEntity<>("Schedule updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> deleteSchedule(String scheduleId) {
        Optional<Schedule> existingSchedule = scheduleRepository.findByScheduleId(scheduleId);
        if (existingSchedule.isPresent()) {
            scheduleRepository.delete(existingSchedule.get());
            return new ResponseEntity<>("Schedule deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<String> deleteAllSchedules() {
        try {
            scheduleRepository.deleteAll(); // Deletes all entries from the schedule table
            return ResponseEntity.ok("All schedules deleted successfully.");
        } catch (Exception e) {
//            log.error("Error occurred while deleting schedules: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete schedules.");
        }
    }
}
