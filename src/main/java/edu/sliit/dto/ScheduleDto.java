package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private String scheduleId;
    private List<String> smartBins;
    private String driverId;
    private String time;
    private String route;
}
