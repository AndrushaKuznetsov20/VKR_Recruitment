package com.trueman.recruitment.controllers;

import com.trueman.recruitment.dto.user.ListResponse;
import com.trueman.recruitment.dto.user.UpdateRequest;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @Operation(summary = "Получение списка пользователей")
    @GetMapping("/list/{pageNo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListResponse> getAllUsers(@PathVariable("pageNo") int pageNo)
    {
        int pageSize = 8;
        return userService.getAllUsers(pageNo, pageSize);
    }

    @Operation(summary = "Обновление данных пользователя")
    @PutMapping("/update")
    public ResponseEntity<String> userUpdate(@RequestBody @Valid UpdateRequest updateRequest, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        return userService.update(updateRequest);
    }

    @Operation(summary = "Блокировка пользователя")
    @PutMapping("/block/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> userBlock(@PathVariable("userId") Long userId)
    {
        return userService.userBlock(userId);
    }

    @Operation(summary = "Разблокировка пользователя")
    @PutMapping("/inBlock/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> userInBlock(@PathVariable("userId") Long userId)
    {
        return userService.userInBlock(userId);
    }

    @Operation(summary = "Назначение роли пользователю")
    @PutMapping("/changeRole/{userId}/{userRole}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> userChangeRole(@PathVariable("userId") Long userId, @PathVariable("userRole") String userRole)
    {
        return userService.userChangeRole(userId, userRole);
    }

    @Operation(summary = "Поиск пользователя по ID")
    @GetMapping("/findByUser/{userId}")
    public ResponseEntity<User> findByUser(@PathVariable("userId") Long userId)
    {
        return userService.findByUserId(userId);
    }
}
