package org.example.jobsearch.controller;

import org.example.jobsearch.dto.VacancyDto;
import org.example.jobsearch.model.Vacancy;
import org.example.jobsearch.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody VacancyDto vacancyDto) {
        Vacancy createdVacancy = vacancyService.createVacancy(vacancyDto);
        if (createdVacancy != null) {
            return new ResponseEntity<>(createdVacancy, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable Long id) {
        Optional<Vacancy> vacancy = vacancyService.getVacancyById(id);
        return vacancy.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Vacancy>> getVacanciesByCategory(@PathVariable Long categoryId) {
        List<Vacancy> vacancies = vacancyService.getVacanciesByCategory(categoryId);
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable Long id, @RequestBody VacancyDto vacancyDto) {
        Vacancy updatedVacancy = vacancyService.updateVacancy(id, vacancyDto);
        if (updatedVacancy != null) {
            return new ResponseEntity<>(updatedVacancy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        boolean deleted = vacancyService.deleteVacancy(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}