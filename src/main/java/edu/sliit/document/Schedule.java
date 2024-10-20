package edu.sliit.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Schedules")
public class Schedule {
    @Id
    private String id;
    private String scheduleId;
    private List<String> smartBins;  // List of bin IDs
    private String driverId;
    private String time;
    private String route;
}
