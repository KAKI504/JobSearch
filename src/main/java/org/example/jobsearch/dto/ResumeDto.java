package org.example.jobsearch.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ResumeDto {
    private Long id;
    private Long applicantId;
    private String name;
    private Long categoryId;
    private BigDecimal salary;
    private Boolean isActive;
}