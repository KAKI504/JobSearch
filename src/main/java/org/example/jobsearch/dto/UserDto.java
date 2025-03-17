package org.example.jobsearch.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String password;
    private String phoneNumber;
    private String accountType;
}