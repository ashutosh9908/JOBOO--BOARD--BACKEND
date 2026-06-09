package com.RoadToSDE3.JobManagementMVP.Services;


import com.RoadToSDE3.JobManagementMVP.Exceptions.IllegalRequestPayloadException;
import com.RoadToSDE3.JobManagementMVP.Exceptions.ResourceNotFoundException;
import com.RoadToSDE3.JobManagementMVP.Models.DTOs.ApplicationFilterDTO;
import com.RoadToSDE3.JobManagementMVP.Models.DTOs.ApplicationLanePageResponseDTO;
import com.RoadToSDE3.JobManagementMVP.Models.DTOs.JobRequestPayloadDTO;
import com.RoadToSDE3.JobManagementMVP.Models.DTOs.JobResponsePayloadDTO;
import com.RoadToSDE3.JobManagementMVP.Models.Entities.Job;
import com.RoadToSDE3.JobManagementMVP.Models.Enums.JobStatus;
import com.RoadToSDE3.JobManagementMVP.Repositories.JobRepository;
import com.RoadToSDE3.JobManagementMVP.Utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class JobApplicationService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    CommonUtils commonUtils;

    public JobResponsePayloadDTO convertJobToJobResponsePayloadDTO(Job job){
        List<String> labels = new ArrayList<>();
        if(Objects.equals(job.getByReferral(), true))
            labels.add("BY REFERRAL");

        if(Objects.equals(job.getForReferral(), true))
            labels.add("FOR REFERRAL");
        return JobResponsePayloadDTO
                .builder()
                .id(job.getId())
                .jobId(job.getJobId())
                .jobDescription(job.getJobDescription())
                .jobName(job.getJobName())
                .company(job.getCompany())
                .location(job.getLocation())
                .hrDetails(Objects.equals(job.getHrDetails(),null)?"":job.getHrDetails())
                .appliedDate(job.getAppliedDate())
                .entryDate(job.getEntryDate())
                .postingDate(job.getPostingDate())
                .lastDate(job.getLastDate())
                .lastUpdate(job.getLastUpdate())
                .Labels(labels)
                .byReferral(Objects.equals(job.getByReferral(), true))
                .forReferral(Objects.equals(job.getForReferral(), true))
                .isApplied(job.getIsApplied())
                .minExperience(job.getMinExperience())
                .status(job.getStatus())
                .applyLink(job.getApplyLink()) //links
                .resumeLatex(job.getResumeLatex())
                .resumePdf(job.getResumePdf())
                .build();
    }
    // get  job by job id-  1
    public JobResponsePayloadDTO getJobDetailsById(Long id){
        Job job = jobRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Job with ID : "+id+" was not found"));

        return convertJobToJobResponsePayloadDTO(job);
    }



    //  save job details - 3-4
    // if payload has id, check if its a valid job or not
    public void upsertJobDetails(JobRequestPayloadDTO jobRequestPayloadDTO) throws Exception {

        Job job = null;
        if(!Objects.equals(jobRequestPayloadDTO.getId(),null)) {
            job = jobRepository.findById(jobRequestPayloadDTO.getId()).orElseThrow(() ->
                    new ResourceNotFoundException("Job with ID : " + jobRequestPayloadDTO.getId() + "not found"));

            job = commonUtils.updateJobFromJobRequest(jobRequestPayloadDTO,job);
        }
        else
            job = commonUtils.createJobFromJobRequest(jobRequestPayloadDTO);

        jobRepository.save(job);


    }



    // delete job by job id; 5
    public void deleteJobById(Long id) {

        jobRepository.deleteById(id);
    }


    // unapply a job by jobid 6

    public void unApplyToAJob(Long id){
        Job job = jobRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cannot Unapply a job which doesn't exist"));

        if(!Objects.equals(job.getIsApplied(),true))
            throw new IllegalRequestPayloadException("Cannot Unapply a job which is not currently applied to");

        job.setIsApplied(false);
        jobRepository.save(job);
    }

    // get page via cursor - 2
    // steps??
    // we will get a filter payload

    public ApplicationLanePageResponseDTO getApplicationLanePage(JobStatus jobStatus, Long pagesize, String cursor, ApplicationFilterDTO applicationFilterDTO){


        List<String> companies =(applicationFilterDTO.getCompanies().isEmpty())
                ? null
                : applicationFilterDTO.getCompanies();
        List<String> referrals = (applicationFilterDTO.getReferral().isEmpty())
                ? null
                : applicationFilterDTO.getReferral();
        Long minExperience = Long.parseLong(applicationFilterDTO.getMinExperience());

        Sort sort = Sort.by(
                (applicationFilterDTO.getSortDir().equalsIgnoreCase("asc"))
                        ? Sort.Direction.ASC : Sort.Direction.DESC,
                applicationFilterDTO.getSortBy()
        );

        Pageable pageable = PageRequest.of(0, Math.toIntExact(pagesize),sort);
        List<Job> jobs = new ArrayList<>();
        if(cursor.isBlank()) {



            LocalDate cursorTime=null;
            if(applicationFilterDTO.getSortBy().equalsIgnoreCase("entryDate"))
                jobs = jobRepository.findGreaterCursorByEntryDate(jobStatus,cursorTime,companies,referrals, minExperience, pageable);

            if(applicationFilterDTO.getSortBy().equalsIgnoreCase("appliedDate"))
                jobs = jobRepository.findGreaterCursorByAppliedDate(jobStatus,cursorTime,companies,referrals, minExperience, pageable);

            if(applicationFilterDTO.getSortBy().equalsIgnoreCase("lastUpdate"))
                jobs = jobRepository.findGreaterCursorByLastUpdate(jobStatus,cursorTime,companies,referrals, minExperience, pageable);
        }
        else{
            // -  we decide up or down

            Map<String, String> mp = new HashMap<>();
            mp = CommonUtils.decode(cursor);

            LocalDate cursorDate = LocalDate.parse(mp.get("timestamp"));
            String pageDir = mp.get("direction");

            if(applicationFilterDTO.getSortDir().equalsIgnoreCase("asc"))
            {

                if(pageDir.equalsIgnoreCase("NEXT"))
                {

                    // NEXT greater
                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("entryDate"))
                        jobs = jobRepository.findGreaterCursorByEntryDate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);

                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("appliedDate"))
                        jobs = jobRepository.findGreaterCursorByAppliedDate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);

                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("lastUpdate"))
                        jobs = jobRepository.findGreaterCursorByLastUpdate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);
                }else{

                    // PREV smaller
                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("entryDate"))
                        jobs = jobRepository.findSmallerCursorByEntryDate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);

                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("appliedDate"))
                        jobs = jobRepository.findSmallerCursorByAppliedDate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);

                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("lastUpdate"))
                        jobs = jobRepository.findSmallerCursorByLastUpdate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);
                }

            }else{


                if(pageDir.equalsIgnoreCase("PREV"))
                {

                    // PREV greater
                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("entryDate"))
                        jobs = jobRepository.findGreaterCursorByEntryDate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);

                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("appliedDate"))
                        jobs = jobRepository.findGreaterCursorByAppliedDate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);

                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("lastUpdate"))
                        jobs = jobRepository.findGreaterCursorByLastUpdate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);
                }else{

                    // NEXT smaller
                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("entryDate"))
                        jobs = jobRepository.findSmallerCursorByEntryDate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);

                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("appliedDate"))
                        jobs = jobRepository.findSmallerCursorByAppliedDate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);

                    if(applicationFilterDTO.getSortBy().equalsIgnoreCase("lastUpdate"))
                        jobs = jobRepository.findSmallerCursorByLastUpdate(jobStatus,cursorDate,companies,referrals, minExperience, pageable);
                }

            }


        }

        // so we now have jobs:

        // next cursor
        Map<String,String> nextMap = new HashMap<>();
        nextMap.put("direction","NEXT");
        // previous cursor
        Map<String,String> prevMap = new HashMap<>();
        prevMap.put("direction","PREV");

        Job lastJob = jobs.getLast();
        Job firstJob = jobs.getFirst();

        if(applicationFilterDTO.getSortBy().equalsIgnoreCase("entryDate"))
        {
            
            nextMap.put("timestamp",lastJob.getEntryDate().toString());
            prevMap.put("timestamp",firstJob.getEntryDate().toString());
        }

        if(applicationFilterDTO.getSortBy().equalsIgnoreCase("appliedDate"))
        {
            nextMap.put("timestamp",lastJob.getAppliedDate().toString());
            prevMap.put("timestamp",firstJob.getAppliedDate().toString());
        }

        if(applicationFilterDTO.getSortBy().equalsIgnoreCase("lastUpdate"))
        {
            nextMap.put("timestamp",lastJob.getLastUpdate().toString());
            prevMap.put("timestamp",firstJob.getLastUpdate().toString());
        }

        String nextCursor = CommonUtils.encode(nextMap);
        String prevCursor = CommonUtils.encode(prevMap);
        List<JobResponsePayloadDTO> jobList = new ArrayList<>();
        for(Job job : jobs)
            jobList.add(convertJobToJobResponsePayloadDTO(job));

        return ApplicationLanePageResponseDTO
                .builder()
                .jobs(jobList)
                .nextCursor(nextCursor)
                .prevCursor(prevCursor)
                .build();
    }
}
