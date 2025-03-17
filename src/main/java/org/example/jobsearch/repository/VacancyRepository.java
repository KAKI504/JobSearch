package org.example.jobsearch.repository;

import org.example.jobsearch.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByAuthorId(Long authorId);
    List<Vacancy> findByIsActiveTrue();
    List<Vacancy> findByCategoryIdAndIsActiveTrue(Long categoryId);
}