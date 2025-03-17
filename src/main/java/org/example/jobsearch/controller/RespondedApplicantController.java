package org.example.jobsearch.controller;

import org.example.jobsearch.dto.ResponseDto;
import org.example.jobsearch.model.RespondedApplicant;
import org.example.jobsearch.service.RespondedApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responses")
public class RespondedApplicantController {

    private final RespondedApplicantService respondedApplicantService;

    @Autowired
    public RespondedApplicantController(RespondedApplicantService respondedApplicantService) {
        this.respondedApplicantService = respondedApplicantService;
    }

    @PostMapping
    public ResponseEntity<RespondedApplicant> createResponse(@RequestBody ResponseDto responseDto) {
        RespondedApplicant respondedApplicant = respondedApplicantService.createResponse(responseDto);
        if (respondedApplicant != null) {
            return new ResponseEntity<>(respondedApplicant, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/vacancy/{vacancyId}")
    public ResponseEntity<List<RespondedApplicant>> getResponsesByVacancy(@PathVariable Long vacancyId) {
        List<RespondedApplicant> responses = respondedApplicantService.getResponsesByVacancy(vacancyId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/resume/{resumeId}")
    public ResponseEntity<List<RespondedApplicant>> getResponsesByResume(@PathVariable Long resumeId) {
        List<RespondedApplicant> responses = respondedApplicantService.getResponsesByApplicant(resumeId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RespondedApplicant>> getAllResponses() {
        List<RespondedApplicant> responses = respondedApplicantService.getAllResponses();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}