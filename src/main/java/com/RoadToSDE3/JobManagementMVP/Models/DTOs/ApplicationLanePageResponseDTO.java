package com.RoadToSDE3.JobManagementMVP.Models.DTOs;


import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ApplicationLanePageResponseDTO {

    List<JobResponsePayloadDTO> jobs;
    String nextCursor;
    String prevCursor;
}
