package edu.sliit.document;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collation = "Bin_Details")
public class Bin {

    @Id
    private String id;
    @Indexed(unique = true)
    private Number BinId;
    private String UserId ;
    private String BinType;
    private String Capacity;
    private String Location ;

}
