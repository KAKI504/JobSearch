package org.example.jobsearch.repository;

import org.example.jobsearch.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByApplicantId(Long applicantId);
    List<Resume> findByIsActiveTrue();
    List<Resume> findByCategoryIdAndIsActiveTrue(Long categoryId);
}