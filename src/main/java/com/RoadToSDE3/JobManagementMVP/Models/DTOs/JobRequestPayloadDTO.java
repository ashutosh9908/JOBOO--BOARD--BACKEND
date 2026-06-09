package com.RoadToSDE3.JobManagementMVP.Models.DTOs;

import com.RoadToSDE3.JobManagementMVP.Models.Enums.JobStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JobRequestPayloadDTO {

        Long id;

        String jobId;
        String jobName;
        String company;
        String jobDescription;
        String location;
        String hrDetails;
        Long minExperience;


        LocalDate entryDate;
        LocalDate postingDate;
        LocalDate lastDate;
        LocalDate appliedDate;
        LocalDate lastUpdate;

        JobStatus status;

        Boolean byReferral;
        Boolean forReferral;
        Boolean isApplied;

        String applyLink;
        String resumePdf;
        String resumeLatex;

}
