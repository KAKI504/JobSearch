package org.example.jobsearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
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

    @JsonIgnore
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<WorkExperienceInfo> workExperiences;

    @JsonIgnore
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<EducationInfo> educations;

    @JsonIgnore
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<ContactInfo> contactInfos;

    @JsonIgnore
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<RespondedApplicant> responses;
}