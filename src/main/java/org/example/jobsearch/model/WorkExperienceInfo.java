package org.example.jobsearch.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "work_experience_info")
public class WorkExperienceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private Integer years;

    @Column(name = "company_name")
    private String companyName;

    private String position;

    private String responsibilities;
}