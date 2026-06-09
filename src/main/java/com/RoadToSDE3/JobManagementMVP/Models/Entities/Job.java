package com.RoadToSDE3.JobManagementMVP.Models.Entities;

import com.RoadToSDE3.JobManagementMVP.Models.Enums.JobStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "JOB_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String jobId;
    String jobName;
    String company;
    String jobDescription;
    String location;
    String hrDetails;
    Long minExperience;

    @CreationTimestamp
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
