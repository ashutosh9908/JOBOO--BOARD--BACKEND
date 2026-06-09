package com.RoadToSDE3.JobManagementMVP.Models.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApplicationLanePageRequestDTO {
    String statusId;
    String Cursor;
    Long pageSize;
    ApplicationFilterDTO filters;
}
