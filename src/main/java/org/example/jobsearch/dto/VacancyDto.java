package org.example.jobsearch.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class VacancyDto {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private BigDecimal salary;
    private Integer expFrom;
    private Integer expTo;
    private Boolean isActive;
    private Long authorId;
}