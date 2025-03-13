package org.example.jobsearch.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contacts_info")
public class ContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ContactType type;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private String value;
}