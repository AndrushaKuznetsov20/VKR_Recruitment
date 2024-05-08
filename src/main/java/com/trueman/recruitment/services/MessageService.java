package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.message.SendMessage;
import com.trueman.recruitment.dto.vacancy.CreateRequest;
import com.trueman.recruitment.models.Message;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.MessageRepository;
import com.trueman.recruitment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public ResponseEntity<String> sendMessage(SendMessage sendMessage, Long senderId, Long receiverId)
    {
        var message = Message.builder()
                .content(sendMessage.getContent())
                .sender(senderId)
                .receiver(receiverId)
                .build();

        message.setCurrentDate();
        messageRepository.save(message);

        return ResponseEntity.ok("Сообщение успешно отправлено!");
    }
    public ResponseEntity<List<Message>> outputMessages(Long senderId, Long receiverId)
    {
        List<Message> messages;
        messages = messageRepository.findBySenderIdAndReceiverIdOrderByCurrentDateTimeDesc(senderId, receiverId);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    public ResponseEntity<List<User>> listChats()
    {
        User user = userService.getCurrentUser();
        List<Long> usersChats = messageRepository.findUserChats(user.getId());

        List<User> users = new ArrayList<>();

        for (Long userId : usersChats)
        {
            User findUser = userRepository.findByUserId(userId);
            users.add(findUser);
        }
        return ResponseEntity.ok(users);
    }
}
