package com.RoadToSDE3.JobManagementMVP.Repositories;

import com.RoadToSDE3.JobManagementMVP.Models.Entities.Job;
import com.RoadToSDE3.JobManagementMVP.Models.Enums.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

//    @Query("""
//        SELECT j
//        FROM Job j
//        WHERE j.jobStatus = :jobStatus
//          AND j.id > :id
//          AND j.company IN :companies
//          AND j.referral IN :referrals
//          AND j.minExperience >= :minExp
//        """)

    //@Query("SELECT j FROM Job j WHERE j.status = :jobStatus AND j.id>:id AND j.company IN :companies AND j.forReferral IN :referrals AND ")

    @Query("""
    SELECT j
    FROM Job j
    WHERE (:jobStatus IS NULL OR j.jobStatus = :jobStatus)
      AND (:lastUpdate IS NULL OR j.lastUpdate > :lastUpdate)
      AND (:companies IS NULL OR j.company IN :companies)
      AND (:referrals IS NULL OR j.referral IN :referrals)
      AND (:minExp IS NULL OR j.minExperience >= :minExp)
    """)
    List<Job> findGreaterCursorByLastUpdate(
            @Param("jobStatus") JobStatus jobStatus,
            @Param("lastUpdate") LocalDate lastUpdate,
            @Param("companies") List<String> companies,
            @Param("referrals") List<String> referrals,
            @Param("minExp") Long minExp,
            Pageable pageable);

    @Query("""
    SELECT j
    FROM Job j
    WHERE (:jobStatus IS NULL OR j.jobStatus = :jobStatus)
      AND (:lastUpdate IS NULL OR j.lastUpdate < :lastUpdate)
      AND (:companies IS NULL OR j.company IN :companies)
      AND (:referrals IS NULL OR j.referral IN :referrals)
      AND (:minExp IS NULL OR j.minExperience >= :minExp)
    """)
    List<Job> findSmallerCursorByLastUpdate(
            @Param("jobStatus") JobStatus jobStatus,
            @Param("lastUpdate") LocalDate lastUpdate,
            @Param("companies") List<String> companies,
            @Param("referrals") List<String> referrals,
            @Param("minExp") Long minExp,
            Pageable pageable);


    // ------------------------------------------
    @Query("""
    SELECT j
    FROM Job j
    WHERE (:jobStatus IS NULL OR j.jobStatus = :jobStatus)
      AND (:lastUpdate IS NULL OR j.entryDate > :entryDate)
      AND (:companies IS NULL OR j.company IN :companies)
      AND (:referrals IS NULL OR j.referral IN :referrals)
      AND (:minExp IS NULL OR j.minExperience >= :minExp)
    """)
    List<Job> findGreaterCursorByEntryDate(
            @Param("jobStatus") JobStatus jobStatus,
            @Param("lastUpdate") LocalDate entryDate,
            @Param("companies") List<String> companies,
            @Param("referrals") List<String> referrals,
            @Param("minExp") Long minExp,
            Pageable pageable);

    @Query("""
    SELECT j
    FROM Job j
    WHERE (:jobStatus IS NULL OR j.jobStatus = :jobStatus)
      AND (:lastUpdate IS NULL OR j.entryDate < :entryDate)
      AND (:companies IS NULL OR j.company IN :companies)
      AND (:referrals IS NULL OR j.referral IN :referrals)
      AND (:minExp IS NULL OR j.minExperience >= :minExp)
    """)
    List<Job> findSmallerCursorByEntryDate(
            @Param("jobStatus") JobStatus jobStatus,
            @Param("lastUpdate") LocalDate entryDate,
            @Param("companies") List<String> companies,
            @Param("referrals") List<String> referrals,
            @Param("minExp") Long minExp,
            Pageable pageable);



    // --------------------------------
    @Query("""
    SELECT j
    FROM Job j
    WHERE (:jobStatus IS NULL OR j.jobStatus = :jobStatus)
      AND (:lastUpdate IS NULL OR j.appliedDate > :appliedDate)
      AND (:companies IS NULL OR j.company IN :companies)
      AND (:referrals IS NULL OR j.referral IN :referrals)
      AND (:minExp IS NULL OR j.minExperience >= :minExp)
    """)
    List<Job> findGreaterCursorByAppliedDate(
            @Param("jobStatus") JobStatus jobStatus,
            @Param("lastUpdate") LocalDate appliedDate,
            @Param("companies") List<String> companies,
            @Param("referrals") List<String> referrals,
            @Param("minExp") Long minExp,
            Pageable pageable);

    @Query("""
    SELECT j
    FROM Job j
    WHERE (:jobStatus IS NULL OR j.jobStatus = :jobStatus)
      AND (:lastUpdate IS NULL OR j.appliedDate < :appliedDate)
      AND (:companies IS NULL OR j.company IN :companies)
      AND (:referrals IS NULL OR j.referral IN :referrals)
      AND (:minExp IS NULL OR j.minExperience >= :minExp)
    """)
    List<Job> findSmallerCursorByAppliedDate(
            @Param("jobStatus") JobStatus jobStatus,
            @Param("lastUpdate") LocalDate appliedDate,
            @Param("companies") List<String> companies,
            @Param("referrals") List<String> referrals,
            @Param("minExp") Long minExp,
            Pageable pageable);
}
