package org.example.jobsearch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.jobsearch.dto.MessageDto;
import org.example.jobsearch.model.Message;
import org.example.jobsearch.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Message API", description = "API для обмена сообщениями между работодателями и соискателями")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Отправить сообщение", description = "Отправляет сообщение в чате отклика")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Сообщение успешно отправлено"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "404", description = "Отклик или пользователь не найден")
    })
    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDto messageDto) {
        try {
            Message message = messageService.createMessage(messageDto);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Получить сообщения чата", description = "Возвращает историю сообщений для указанного отклика")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение сообщений"),
            @ApiResponse(responseCode = "404", description = "Отклик не найден")
    })
    @GetMapping("/response/{responseId}")
    public ResponseEntity<List<Message>> getMessagesByResponse(@PathVariable Long responseId) {
        List<Message> messages = messageService.getMessagesByResponse(responseId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}