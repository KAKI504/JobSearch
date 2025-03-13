package org.example.jobsearch.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "responded_applicants")
    private RespondedApplicant respondedApplicant;

    private String content;

    private LocalDateTime timestamp;
}