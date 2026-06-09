package com.RoadToSDE3.JobManagementMVP.Utils;

import com.RoadToSDE3.JobManagementMVP.Exceptions.EncodeDecodeException;
import com.RoadToSDE3.JobManagementMVP.Exceptions.IllegalRequestPayloadException;
import com.RoadToSDE3.JobManagementMVP.Models.DTOs.JobRequestPayloadDTO;
import com.RoadToSDE3.JobManagementMVP.Models.Entities.Job;
import com.RoadToSDE3.JobManagementMVP.Models.Enums.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Objects;
import java.util.Map;
import java.util.Base64;


@Component
public class CommonUtils {


    private static final ObjectMapper OBJECT_MAPPER =  new ObjectMapper();;
    public String capitalizeEachWord(String input) {
        if (input == null || input.isBlank()) {
            return input;
        }

        String[] words = input.trim().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result
                    .append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }

        return result.toString().trim();
    }

    public Boolean matchesJobStatusEnum(String status){

        for(JobStatus jobStatus:JobStatus.values())
            if(status.equalsIgnoreCase(jobStatus.name()))
                return true;

        return false;
    }


    public Job createJobFromJobRequest(JobRequestPayloadDTO jobRequestPayloadDTO) throws RuntimeException, Exception{


        Job.JobBuilder jobBuilder = Job.builder();

        //jobname
        if(Objects.equals(jobRequestPayloadDTO.getJobName(),null))
            jobBuilder.jobName("General Job");
        else
            jobBuilder.jobName(capitalizeEachWord(jobRequestPayloadDTO.getJobName()));

        // jobId
        if(Objects.equals(jobRequestPayloadDTO.getJobId(),null))
            jobBuilder.jobId("XXX 000 XXX");
        else
            jobBuilder.jobId(capitalizeEachWord(jobRequestPayloadDTO.getJobId()));

        // company
        if(Objects.equals(jobRequestPayloadDTO.getCompany(),null))
            jobBuilder.company("General Company");
        else
            jobBuilder.company(capitalizeEachWord(jobRequestPayloadDTO.getCompany()));

        // Location
        if(Objects.equals(jobRequestPayloadDTO.getLocation(),null))
            jobBuilder.location("General Location");
        else
            jobBuilder.location(capitalizeEachWord(jobRequestPayloadDTO.getLocation()));

        // hrdetails
        if(Objects.equals(jobRequestPayloadDTO.getHrDetails(),null))
            jobBuilder.hrDetails("");
        else
            jobBuilder.hrDetails(jobRequestPayloadDTO.getHrDetails());

        // minExperience
        if(Objects.equals(jobRequestPayloadDTO.getMinExperience(),null))
            jobBuilder.minExperience((long)5);
        else
            jobBuilder.minExperience(jobRequestPayloadDTO.getMinExperience());

        // posting date
        LocalDate postingDate;
        if(Objects.equals(jobRequestPayloadDTO.getPostingDate(),null))
        {
            jobBuilder.postingDate(LocalDate.now());
            postingDate = LocalDate.now();
        }
        else {
            jobBuilder.postingDate(jobRequestPayloadDTO.getPostingDate());
            postingDate = jobRequestPayloadDTO.getPostingDate();
        }

        // entry Date
        jobBuilder.entryDate(LocalDate.now());

        //last date
        if(Objects.equals(jobRequestPayloadDTO.getLastDate(),null))
            jobBuilder.lastDate(LocalDate.now().plusDays(7));
        else
            if(!jobRequestPayloadDTO.getLastDate().isBefore(postingDate))
                jobBuilder.lastDate(jobRequestPayloadDTO.getLastDate());
            else
                throw new IllegalRequestPayloadException("Last Date cannot be greater than Posting date");

        // LastUpdate
        jobBuilder.lastUpdate(LocalDate.now());


        //applied Date
        if(Objects.equals(jobRequestPayloadDTO.getAppliedDate(),null))
            jobBuilder.appliedDate(LocalDate.now());
        else
            if(!jobRequestPayloadDTO.getAppliedDate().isBefore(postingDate))
                jobBuilder.jobName(jobRequestPayloadDTO.getHrDetails());
            else
                throw new IllegalRequestPayloadException("Date of application cannot be before Date of Posting");



        // jobDescript

        if(Objects.equals(jobRequestPayloadDTO.getJobDescription(),null))
            jobBuilder.jobDescription("");
        else
            jobBuilder.jobDescription(jobRequestPayloadDTO.getJobDescription());


        // byRefferral
        if(Objects.equals(jobRequestPayloadDTO.getByReferral(),null))
            jobBuilder.byReferral(false);
        else
            jobBuilder.byReferral(jobRequestPayloadDTO.getByReferral());

        // for Refferral
        if(Objects.equals(jobRequestPayloadDTO.getForReferral(),null))
            jobBuilder.forReferral(false);
        else
            jobBuilder.forReferral(jobRequestPayloadDTO.getForReferral());


        // isApplied
        jobBuilder.isApplied(true);
        ;
        // pdf link
        if(Objects.equals(jobRequestPayloadDTO.getResumePdf(),null))
            jobBuilder.resumePdf("");
        else
            jobBuilder.resumePdf(jobRequestPayloadDTO.getResumePdf().trim());

        // tex link
        if(Objects.equals(jobRequestPayloadDTO.getResumeLatex(),null))
            jobBuilder.resumeLatex("");
        else
            jobBuilder.resumeLatex(jobRequestPayloadDTO.getResumeLatex().trim());

        // apply link
        if(Objects.equals(jobRequestPayloadDTO.getApplyLink(),null))
            jobBuilder.applyLink("");
        else
            jobBuilder.applyLink(jobRequestPayloadDTO.getApplyLink().trim());
        return jobBuilder.build();

    }

    public Job updateJobFromJobRequest(JobRequestPayloadDTO jobRequestPayloadDTO, Job job) throws RuntimeException, Exception{




        //jobname
        if(!Objects.equals(jobRequestPayloadDTO.getJobName(),null))
            job.setJobName(capitalizeEachWord(jobRequestPayloadDTO.getJobName()));

        // jobId
        if(!Objects.equals(jobRequestPayloadDTO.getJobId(),null))
            job.setJobId(capitalizeEachWord(jobRequestPayloadDTO.getJobId()));

        // company
        if(!Objects.equals(jobRequestPayloadDTO.getCompany(),null))
            job.setCompany(capitalizeEachWord(jobRequestPayloadDTO.getCompany()));

        // Location
        if(!Objects.equals(jobRequestPayloadDTO.getLocation(),null))
            job.setLocation(capitalizeEachWord(jobRequestPayloadDTO.getLocation()));

        // hrdetails
        if(!Objects.equals(jobRequestPayloadDTO.getHrDetails(),null))
            job.setHrDetails(jobRequestPayloadDTO.getHrDetails());

        // minExperience
        if(!Objects.equals(jobRequestPayloadDTO.getMinExperience(),null))
            job.setMinExperience(jobRequestPayloadDTO.getMinExperience());

        // posting date
        LocalDate postingDate;
        if(!Objects.equals(jobRequestPayloadDTO.getPostingDate(),null))
        {
            if(jobRequestPayloadDTO.getPostingDate().isAfter(job.getPostingDate()))
                throw new IllegalRequestPayloadException("New Posting cannot be after the current Posting date");
            job.setPostingDate(jobRequestPayloadDTO.getPostingDate());
        }


//        // entry Date
//        jobBuilder.entryDate(LocalDate.now());

        //last date
        if(!Objects.equals(jobRequestPayloadDTO.getLastDate(),null))
        {
            if(jobRequestPayloadDTO.getLastDate().isBefore(job.getPostingDate()))
                throw new IllegalRequestPayloadException("New Last Date cannot be after the current Posting date");
            job.setLastDate(jobRequestPayloadDTO.getLastDate());
        }

        // LastUpdate
        job.setLastUpdate(LocalDate.now());


        //applied Date
        if(!Objects.equals(jobRequestPayloadDTO.getAppliedDate(),null))
        {
            if(jobRequestPayloadDTO.getAppliedDate().isBefore(job.getPostingDate()))
                throw new IllegalRequestPayloadException("New Applied Date cannot be after the current Posting date");
            job.setAppliedDate(jobRequestPayloadDTO.getAppliedDate());
        }

        // jobDescript
        if(!Objects.equals(jobRequestPayloadDTO.getJobDescription(),null))
            job.setJobDescription(jobRequestPayloadDTO.getJobDescription());


        // byRefferral
        if(!Objects.equals(jobRequestPayloadDTO.getByReferral(),null))
            job.setByReferral(jobRequestPayloadDTO.getByReferral());

        // for Refferral
        if(!Objects.equals(jobRequestPayloadDTO.getForReferral(),null))
            job.setForReferral(jobRequestPayloadDTO.getForReferral());


        // isApplied
        job.setIsApplied(true);
        ;
        // pdf link
        if(!Objects.equals(jobRequestPayloadDTO.getResumePdf(),null))
            job.setResumePdf(jobRequestPayloadDTO.getResumePdf().trim());

        // tex link
        if(!Objects.equals(jobRequestPayloadDTO.getResumeLatex(),null))
            job.setResumeLatex(jobRequestPayloadDTO.getResumeLatex().trim());

        // apply link
        if(!Objects.equals(jobRequestPayloadDTO.getApplyLink(),null))
            job.setApplyLink(jobRequestPayloadDTO.getApplyLink().trim());
        return job;

    }


    public static String encode(Map<String, String> data) {
        try {
            String json = CommonUtils.OBJECT_MAPPER.writeValueAsString(data);

            return Base64.getEncoder()
                    .encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new EncodeDecodeException("Failed to encode");
        }
    }

    public static Map<String, String> decode(String encoded) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encoded);

            String json = new String(decodedBytes, StandardCharsets.UTF_8);

            return CommonUtils.OBJECT_MAPPER.readValue(
                    json,
                    new TypeReference<Map<String, String>>() {}
            );
        } catch (Exception e) {
            throw new EncodeDecodeException("Failed to decode");
        }
    }
}
