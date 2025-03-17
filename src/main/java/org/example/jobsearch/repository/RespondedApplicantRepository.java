package org.example.jobsearch.repository;

import org.example.jobsearch.model.RespondedApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespondedApplicantRepository extends JpaRepository<RespondedApplicant, Long> {
    List<RespondedApplicant> findByVacancyId(Long vacancyId);
    List<RespondedApplicant> findByResumeId(Long resumeId);
}