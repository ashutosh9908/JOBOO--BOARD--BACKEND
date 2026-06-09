package com.RoadToSDE3.JobManagementMVP.Controllers;


import com.RoadToSDE3.JobManagementMVP.Models.DTOs.ApplicationLanePageRequestDTO;
import com.RoadToSDE3.JobManagementMVP.Models.DTOs.ApplicationLanePageResponseDTO;
import com.RoadToSDE3.JobManagementMVP.Models.DTOs.JobRequestPayloadDTO;
import com.RoadToSDE3.JobManagementMVP.Models.Enums.JobStatus;
import com.RoadToSDE3.JobManagementMVP.Services.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-application")
public class JobApplicationController {

    @Autowired
    JobApplicationService jobApplicationService;

    @GetMapping("/category-page?status={statusId}&pageSize=10&cursor={cursor}")
    public ResponseEntity<ApplicationLanePageResponseDTO> getApplicationLanePage(
            @RequestParam(name = "status")JobStatus status,
            @RequestParam(name = "pageSize",defaultValue = "10")Long pageSize,
            @RequestParam(name = "cursor" , defaultValue = "") String cursor,
            @RequestBody ApplicationLanePageRequestDTO applicationLanePageRequestDTO
            ){

        return ResponseEntity.ok(
                jobApplicationService
                        .getApplicationLanePage(status,pageSize,cursor,applicationLanePageRequestDTO.getFilters()));

    }

    @PostMapping("/save")
    public ResponseEntity<?> saveJobApplication(
            @RequestBody JobRequestPayloadDTO jobRequestPayloadDTO) throws Exception {

        jobApplicationService
                .upsertJobDetails(jobRequestPayloadDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editJobApplication(
            @RequestBody JobRequestPayloadDTO jobRequestPayloadDTO) throws Exception {

        jobApplicationService
                .upsertJobDetails(jobRequestPayloadDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/{jobId}")
    public ResponseEntity<?> deleteJobApplication(@PathVariable(name = "jobId") Long id){

        jobApplicationService.deleteJobById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/unapplied/{jobId}")
    public ResponseEntity<?> unappliedJobApplication(@PathVariable(name = "jobId") Long id){

        jobApplicationService.unApplyToAJob(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
