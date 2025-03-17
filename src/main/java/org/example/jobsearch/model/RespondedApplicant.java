package org.example.jobsearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "responded_applicants")
public class RespondedApplicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    private Boolean confirmation;

    @JsonIgnore
    @OneToMany(mappedBy = "respondedApplicant", cascade = CascadeType.ALL)
    private List<Message> messages;
}