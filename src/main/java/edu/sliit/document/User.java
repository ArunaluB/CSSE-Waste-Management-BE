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
@Document(collation = "User_Details")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String UserId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String username;
    private String location;
    private String status;
    private Number points ;

}
