package com.RoadToSDE3.JobManagementMVP.Models.DTOs;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
/*
where status and id>cursor and company in comapnies and forrefferal in [referral] and minExp>=minExp
order by {} {}
offset 0
limit pagesize

cursor if emtpty - 0

 */
public class ApplicationFilterDTO {

    List<String> companies;
    String minExperience;
    List<String> referral;
    String sortBy;
    String sortDir;

}
