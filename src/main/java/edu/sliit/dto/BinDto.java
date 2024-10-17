package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinDto {

    @NotNull
    private String UserId ;
    private String BinType;
    private String Capacity;
    private String Location ;
}
