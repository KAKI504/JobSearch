package org.example.jobsearch.service;

import org.example.jobsearch.dto.VacancyDto;
import org.example.jobsearch.model.Category;
import org.example.jobsearch.model.User;
import org.example.jobsearch.model.Vacancy;
import org.example.jobsearch.repository.CategoryRepository;
import org.example.jobsearch.repository.UserRepository;
import org.example.jobsearch.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public VacancyService(VacancyRepository vacancyRepository, UserRepository userRepository,
                          CategoryRepository categoryRepository) {
        this.vacancyRepository = vacancyRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Vacancy createVacancy(VacancyDto vacancyDto) {
        Optional<User> optionalUser = userRepository.findById(vacancyDto.getAuthorId());
        Optional<Category> optionalCategory = categoryRepository.findById(vacancyDto.getCategoryId());

        if (optionalUser.isPresent() && optionalCategory.isPresent()) {
            Vacancy vacancy = new Vacancy();
            vacancy.setName(vacancyDto.getName());
            vacancy.setDescription(vacancyDto.getDescription());
            vacancy.setAuthor(optionalUser.get());
            vacancy.setCategory(optionalCategory.get());
            vacancy.setSalary(vacancyDto.getSalary());
            vacancy.setExpFrom(vacancyDto.getExpFrom());
            vacancy.setExpTo(vacancyDto.getExpTo());
            vacancy.setIsActive(vacancyDto.getIsActive());
            vacancy.setCreatedDate(LocalDateTime.now());
            vacancy.setUpdateTime(LocalDateTime.now());

            return vacancyRepository.save(vacancy);
        }

        return null;
    }

    public Optional<Vacancy> getVacancyById(Long id) {
        return vacancyRepository.findById(id);
    }

    public List<Vacancy> getAllVacancies() {
        return vacancyRepository.findByIsActiveTrue();
    }

    public List<Vacancy> getVacanciesByCategory(Long categoryId) {
        return vacancyRepository.findByCategoryIdAndIsActiveTrue(categoryId);
    }

    public Vacancy updateVacancy(Long id, VacancyDto vacancyDto) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
        if (optionalVacancy.isPresent()) {
            Vacancy vacancy = optionalVacancy.get();

            Optional<Category> optionalCategory = categoryRepository.findById(vacancyDto.getCategoryId());
            if (optionalCategory.isPresent()) {
                vacancy.setName(vacancyDto.getName());
                vacancy.setDescription(vacancyDto.getDescription());
                vacancy.setCategory(optionalCategory.get());
                vacancy.setSalary(vacancyDto.getSalary());
                vacancy.setExpFrom(vacancyDto.getExpFrom());
                vacancy.setExpTo(vacancyDto.getExpTo());
                vacancy.setIsActive(vacancyDto.getIsActive());
                vacancy.setUpdateTime(LocalDateTime.now());

                return vacancyRepository.save(vacancy);
            }
        }
        return null;
    }

    public boolean deleteVacancy(Long id) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
        if (optionalVacancy.isPresent()) {
            vacancyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Vacancy> searchVacancies(String keyword, Long categoryId,
                                         BigDecimal minSalary, BigDecimal maxSalary,
                                         int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime").descending());

        if (keyword != null && !keyword.isEmpty()) {
            return vacancyRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(keyword, pageable).getContent();
        } else if (categoryId != null) {
            return vacancyRepository.findByCategoryIdAndIsActiveTrue(categoryId, pageable).getContent();
        } else if (minSalary != null && maxSalary != null) {
            return vacancyRepository.findBySalaryBetweenAndIsActiveTrue(minSalary, maxSalary, pageable).getContent();
        } else {
            return vacancyRepository.findByIsActiveTrue(pageable).getContent();
        }
    }
}