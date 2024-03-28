package com.trueman.recruitment.controllers;

import com.trueman.recruitment.dto.user.ListResponse;
import com.trueman.recruitment.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Методы для работы с пользователями")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получение списка пользователей")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListResponse> getAllUsers()
    {
        ListResponse users;
        users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
