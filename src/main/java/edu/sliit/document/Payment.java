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
@Document(collation = "Payment_Details")
public class Payment {
    @Id
    private String id;
    @Indexed(unique=true)
    private Long PaymentId;
    private String UserId;
    private Number PaymentAmount;
    private Date PaymentDate;
    private Date NextPaymentDate;
}
