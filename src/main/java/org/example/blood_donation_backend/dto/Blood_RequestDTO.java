package org.example.blood_donation_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Blood_RequestDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String bloodType;
    private String district;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate requestDate;
    private String hospitalName;

}
