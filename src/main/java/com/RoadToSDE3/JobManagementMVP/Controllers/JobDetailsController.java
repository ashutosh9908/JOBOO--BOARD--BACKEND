package com.RoadToSDE3.JobManagementMVP.Controllers;


import com.RoadToSDE3.JobManagementMVP.Models.DTOs.JobResponsePayloadDTO;
import com.RoadToSDE3.JobManagementMVP.Services.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job-details")
public class JobDetailsController {

    @Autowired
    JobApplicationService jobApplicationService;

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponsePayloadDTO> getJobDetails(@PathVariable(name = "jobId") Long id){
        return ResponseEntity.ok(jobApplicationService.getJobDetailsById(id));
    }

}
