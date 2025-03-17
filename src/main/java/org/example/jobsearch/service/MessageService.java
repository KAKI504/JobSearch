package org.example.jobsearch.service;

import org.example.jobsearch.dto.MessageDto;
import org.example.jobsearch.model.Message;
import org.example.jobsearch.model.RespondedApplicant;
import org.example.jobsearch.model.User;
import org.example.jobsearch.repository.MessageRepository;
import org.example.jobsearch.repository.RespondedApplicantRepository;
import org.example.jobsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RespondedApplicantRepository respondedApplicantRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository,
                          RespondedApplicantRepository respondedApplicantRepository,
                          UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.respondedApplicantRepository = respondedApplicantRepository;
        this.userRepository = userRepository;
    }

    public Message createMessage(MessageDto messageDto) {
        Optional<RespondedApplicant> responseOpt = respondedApplicantRepository.findById(messageDto.getRespondedApplicantId());
        Optional<User> senderOpt = userRepository.findById(messageDto.getSenderId());

        if (responseOpt.isPresent() && senderOpt.isPresent()) {
            Message message = new Message();
            message.setRespondedApplicant(responseOpt.get());
            message.setContent(messageDto.getContent());
            message.setTimestamp(LocalDateTime.now());


            return messageRepository.save(message);
        }
        throw new RuntimeException("Отклик или пользователь не найден");
    }

    public List<Message> getMessagesByResponse(Long responseId) {
        return messageRepository.findByRespondedApplicantIdOrderByTimestampAsc(responseId);
    }
}