package org.example.jobsearch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.jobsearch.dto.VacancyDto;
import org.example.jobsearch.model.Vacancy;
import org.example.jobsearch.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vacancies")
@Tag(name = "Vacancy API", description = "API для управления вакансиями")
public class VacancyController {

    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @Operation(summary = "Создать новую вакансию", description = "Создает вакансию с указанными параметрами")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Вакансия успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    })
    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody VacancyDto vacancyDto) {
        Vacancy createdVacancy = vacancyService.createVacancy(vacancyDto);
        if (createdVacancy != null) {
            return new ResponseEntity<>(createdVacancy, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Получить вакансию по ID", description = "Возвращает вакансию с указанным ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вакансия найдена"),
            @ApiResponse(responseCode = "404", description = "Вакансия не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable Long id) {
        Optional<Vacancy> vacancy = vacancyService.getVacancyById(id);
        return vacancy.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Получить все активные вакансии", description = "Возвращает список всех активных вакансий")
    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @Operation(summary = "Получить вакансии по категории", description = "Возвращает список вакансий определенной категории")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Vacancy>> getVacanciesByCategory(@PathVariable Long categoryId) {
        List<Vacancy> vacancies = vacancyService.getVacanciesByCategory(categoryId);
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @Operation(summary = "Обновить вакансию", description = "Обновляет данные вакансии с указанным ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вакансия успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Вакансия не найдена")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable Long id, @RequestBody VacancyDto vacancyDto) {
        Vacancy updatedVacancy = vacancyService.updateVacancy(id, vacancyDto);
        if (updatedVacancy != null) {
            return new ResponseEntity<>(updatedVacancy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Удалить вакансию", description = "Удаляет вакансию с указанным ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Вакансия успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Вакансия не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        boolean deleted = vacancyService.deleteVacancy(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Поиск вакансий", description = "Ищет вакансии по ключевым словам, категории и зарплате")
    @GetMapping("/search")
    public ResponseEntity<List<Vacancy>> searchVacancies(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minSalary,
            @RequestParam(required = false) BigDecimal maxSalary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        List<Vacancy> vacancies = vacancyService.searchVacancies(keyword, categoryId, minSalary, maxSalary, page, size);
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }
}