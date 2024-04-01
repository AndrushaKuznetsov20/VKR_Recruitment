package com.trueman.recruitment.controllers;

import com.trueman.recruitment.dto.message.SendMessage;
import com.trueman.recruitment.models.Message;
import com.trueman.recruitment.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Tag(name = "Система чата")
public class MessageController {
    private final MessageService messageService;

    @Operation(summary = "Вывод истории чата двух пользователей")
    @GetMapping("/outputMessages/{senderId}/{receiverId}")
    public ResponseEntity<List<Message>> outputMessages(@PathVariable("senderId") Long senderId, @PathVariable("receiverId") Long receiverId)
    {
        return messageService.outputMessages(senderId, receiverId);
    }

    @Operation(summary = "Отправка сообщения")
    @PostMapping("/sendMessage/{senderId}/{receiverId}")
    public ResponseEntity<String> sendMessage(@RequestBody @Valid SendMessage sendMessage, BindingResult bindingResult, @PathVariable("senderId") Long senderId, @PathVariable("receiverId") Long receiverId)
    {
        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        return messageService.sendMessage(sendMessage, senderId, receiverId);
    }
}
