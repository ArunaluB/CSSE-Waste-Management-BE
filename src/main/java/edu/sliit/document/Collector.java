package edu.sliit.document;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collation = "Collector_Details")
public class Collector {
    @Id
    private String id;
    @Indexed(unique=true)
    private Number CollectorId;
    private String userId ;
    private String binId;
    private String binType;
    private String DriverId;
    private String DriverName;
    private Date   CollectionDate;
}
