package org.example.jobsearch.service;

import org.example.jobsearch.dto.ResumeDto;
import org.example.jobsearch.model.Category;
import org.example.jobsearch.model.Resume;
import org.example.jobsearch.model.User;
import org.example.jobsearch.repository.CategoryRepository;
import org.example.jobsearch.repository.ResumeRepository;
import org.example.jobsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository,
                         CategoryRepository categoryRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Resume createResume(ResumeDto resumeDto) {
        Optional<User> optionalUser = userRepository.findById(resumeDto.getApplicantId());
        Optional<Category> optionalCategory = categoryRepository.findById(resumeDto.getCategoryId());

        if (optionalUser.isPresent() && optionalCategory.isPresent()) {
            Resume resume = new Resume();
            resume.setName(resumeDto.getName());
            resume.setApplicant(optionalUser.get());
            resume.setCategory(optionalCategory.get());
            resume.setSalary(resumeDto.getSalary());
            resume.setIsActive(resumeDto.getIsActive());
            resume.setCreatedDate(LocalDateTime.now());
            resume.setUpdateTime(LocalDateTime.now());

            return resumeRepository.save(resume);
        }

        return null;
    }

    public Optional<Resume> getResumeById(Long id) {
        return resumeRepository.findById(id);
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findByIsActiveTrue();
    }

    public List<Resume> getResumesByCategory(Long categoryId) {
        return resumeRepository.findByCategoryIdAndIsActiveTrue(categoryId);
    }

    public Resume updateResume(Long id, ResumeDto resumeDto) {
        Optional<Resume> optionalResume = resumeRepository.findById(id);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();

            Optional<Category> optionalCategory = categoryRepository.findById(resumeDto.getCategoryId());
            if (optionalCategory.isPresent()) {
                resume.setName(resumeDto.getName());
                resume.setCategory(optionalCategory.get());
                resume.setSalary(resumeDto.getSalary());
                resume.setIsActive(resumeDto.getIsActive());
                resume.setUpdateTime(LocalDateTime.now());

                return resumeRepository.save(resume);
            }
        }
        return null;
    }

    public boolean deleteResume(Long id) {
        Optional<Resume> optionalResume = resumeRepository.findById(id);
        if (optionalResume.isPresent()) {
            resumeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}