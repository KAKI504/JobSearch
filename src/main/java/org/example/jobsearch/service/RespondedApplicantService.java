package org.example.jobsearch.service;

import org.example.jobsearch.dto.ResponseDto;
import org.example.jobsearch.model.Resume;
import org.example.jobsearch.model.RespondedApplicant;
import org.example.jobsearch.model.Vacancy;
import org.example.jobsearch.repository.ResumeRepository;
import org.example.jobsearch.repository.RespondedApplicantRepository;
import org.example.jobsearch.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RespondedApplicantService {

    private final RespondedApplicantRepository respondedApplicantRepository;
    private final ResumeRepository resumeRepository;
    private final VacancyRepository vacancyRepository;

    @Autowired
    public RespondedApplicantService(RespondedApplicantRepository respondedApplicantRepository,
                                     ResumeRepository resumeRepository,
                                     VacancyRepository vacancyRepository) {
        this.respondedApplicantRepository = respondedApplicantRepository;
        this.resumeRepository = resumeRepository;
        this.vacancyRepository = vacancyRepository;
    }

    public RespondedApplicant createResponse(ResponseDto responseDto) {
        Optional<Resume> optionalResume = resumeRepository.findById(responseDto.getResumeId());
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(responseDto.getVacancyId());

        if (optionalResume.isPresent() && optionalVacancy.isPresent()) {
            RespondedApplicant respondedApplicant = new RespondedApplicant();
            respondedApplicant.setResume(optionalResume.get());
            respondedApplicant.setVacancy(optionalVacancy.get());
            respondedApplicant.setConfirmation(false);

            return respondedApplicantRepository.save(respondedApplicant);
        }

        return null;
    }

    public List<RespondedApplicant> getResponsesByVacancy(Long vacancyId) {
        return respondedApplicantRepository.findByVacancyId(vacancyId);
    }

    public List<RespondedApplicant> getResponsesByApplicant(Long resumeId) {
        return respondedApplicantRepository.findByResumeId(resumeId);
    }
    public List<RespondedApplicant> getAllResponses() {
        return respondedApplicantRepository.findAll();
    }
}