package org.example.jobsearch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String avatar;

    @Column(name = "account_type")
    private String accountType;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<Resume> resumes;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Vacancy> vacancies;
}