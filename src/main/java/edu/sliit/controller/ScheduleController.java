package edu.sliit.controller;

import edu.sliit.dto.ScheduleDto;
import edu.sliit.servise.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("schedule")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addSchedule(@RequestBody ScheduleDto dto) {
        log.info("Adding new Schedule for [{}]", dto);
        return scheduleService.addSchedule(dto);
    }

    @GetMapping("/getSchedules")
    public List<ScheduleDto> getSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/getSchedule")
    public ScheduleDto getSchedule(@RequestParam String scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    @PutMapping("/update/{scheduleId}") // Update mapping to include path variable
    public ResponseEntity<String> updateSchedule(@PathVariable String scheduleId, @RequestBody ScheduleDto dto) {
        dto.setScheduleId(scheduleId); // Assuming your ScheduleDto has a method to set ID
        return scheduleService.updateSchedule(dto);
    }

    @DeleteMapping("/delete/{scheduleId}") // Change to use a path variable
    public ResponseEntity<String> deleteSchedule(@PathVariable String scheduleId) {
        log.info("Deleting Schedule with ID [{}]", scheduleId);
        return scheduleService.deleteSchedule(scheduleId);
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllSchedules() {
        log.info("Deleting all schedules");
        return scheduleService.deleteAllSchedules();
    }
}