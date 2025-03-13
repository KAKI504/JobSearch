package org.example.jobsearch.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private User applicant;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private BigDecimal salary;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<WorkExperienceInfo> workExperiences;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<EducationInfo> educations;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<ContactInfo> contactInfos;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<RespondedApplicant> responses;
}