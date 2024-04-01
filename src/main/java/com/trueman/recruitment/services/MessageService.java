package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.message.SendMessage;
import com.trueman.recruitment.dto.vacancy.CreateRequest;
import com.trueman.recruitment.models.Message;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    public ResponseEntity<String> sendMessage(SendMessage sendMessage, Long senderId, Long receiverId)
    {
        var message = Message.builder()
                .content(sendMessage.getContent())
                .sender(senderId)
                .receiver(receiverId)
                .build();
        messageRepository.save(message);
        return ResponseEntity.ok("Сообщение успешно отправлено!");
    }
    public ResponseEntity<List<Message>> outputMessages(Long senderId, Long receiverId)
    {
        List<Message> messages;
        messages = messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
