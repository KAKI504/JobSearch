package org.example.jobsearch.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Long respondedApplicantId;
    private String content;
    private Long senderId;
}