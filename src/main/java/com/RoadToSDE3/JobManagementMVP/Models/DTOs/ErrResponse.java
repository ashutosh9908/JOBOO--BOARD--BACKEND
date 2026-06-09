package com.RoadToSDE3.JobManagementMVP.Models.DTOs;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ErrResponse {

    String message;
    LocalDate time;
    HttpStatus httpStatus;
}
