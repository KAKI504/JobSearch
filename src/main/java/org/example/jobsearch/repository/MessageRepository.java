package org.example.jobsearch.repository;

import org.example.jobsearch.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRespondedApplicantIdOrderByTimestampAsc(Long respondedApplicantId);
}