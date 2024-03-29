package com.trueman.recruitment.controllers;

import com.trueman.recruitment.dto.user.ListResponse;
import com.trueman.recruitment.dto.user.UpdateRequest;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.enums.Role;
import com.trueman.recruitment.repositories.UserRepository;
import com.trueman.recruitment.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Методы для работы с пользователями")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    @Operation(summary = "Получение списка пользователей")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListResponse> getAllUsers()
    {
        ListResponse users;
        users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @Operation(summary = "Обновление данных пользователя")
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> userUpdate(@PathVariable Long userId,
                                             @RequestBody @Valid UpdateRequest updateRequest, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        userService.update(userId, updateRequest);
        return ResponseEntity.ok("Данные о пользователе успешно обновлены!");
    }
    @Operation(summary = "Блокировка пользователя")
    @PutMapping("/block/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> userBlock(@PathVariable Long userId)
    {
        User user = userRepository.findById(userId).orElse(null);
        user.setActive(false);
        userRepository.save(user);
        return ResponseEntity.ok("Пользователь успешно заблокирован!");
    }

    @Operation(summary = "Разблокировка пользователя")
    @PutMapping("/inBlock/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> userInBlock(@PathVariable Long userId)
    {
        User user = userRepository.findById(userId).orElse(null);
        user.setActive(true);
        userRepository.save(user);
        return ResponseEntity.ok("Пользователь успешно разблокирован!");
    }

    @Operation(summary = "Назначение роли пользователю")
    @PutMapping("/changeRole/{userId}/{userRole}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> userChangeRole(@PathVariable Long userId, @PathVariable String userRole)
    {
        User user = userRepository.findById(userId).orElse(null);

        if(userRole.equals("USER"))
        {
            user.setRole(Role.ROLE_USER);
            userRepository.save(user);
            return ResponseEntity.ok("Роль 'Пользователь' успешно назначена!");
        }
        else if(userRole.equals("EMPLOYER"))
        {
            user.setRole(Role.ROLE_EMPLOYER);
            userRepository.save(user);
            return ResponseEntity.ok("Роль 'Работодатель' успешно назначена!");
        }
        else if (userRole.equals("MODER")) {
            user.setRole(Role.ROLE_MODER);
            userRepository.save(user);
            return ResponseEntity.ok("Роль 'Модератор' успешно назначена!");
        }
        else if (userRole.equals("ADMIN")) {
            user.setRole(Role.ROLE_ADMIN);
            userRepository.save(user);
            return ResponseEntity.ok("Роль 'Администратор' успешно назначена!");
        }
        else
        {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }
}
