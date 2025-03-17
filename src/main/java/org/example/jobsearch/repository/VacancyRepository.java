package org.example.jobsearch.repository;

import org.example.jobsearch.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByAuthorId(Long authorId);
    List<Vacancy> findByIsActiveTrue();
    List<Vacancy> findByCategoryIdAndIsActiveTrue(Long categoryId);

    Page<Vacancy> findByIsActiveTrue(Pageable pageable);

    Page<Vacancy> findByNameContainingIgnoreCaseAndIsActiveTrue(String keyword, Pageable pageable);

    Page<Vacancy> findByCategoryIdAndIsActiveTrue(Long categoryId, Pageable pageable);

    Page<Vacancy> findBySalaryBetweenAndIsActiveTrue(BigDecimal minSalary, BigDecimal maxSalary, Pageable pageable);
}