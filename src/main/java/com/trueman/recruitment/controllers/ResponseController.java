package com.trueman.recruitment.controllers;

import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.VacancyRepository;
import com.trueman.recruitment.services.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/response")
@RequiredArgsConstructor
@Tag(name = "Методы для работы с откликами")
public class ResponseController {

    private final ResponseService responseService;
    private final VacancyRepository vacancyRepository;

    @Operation(summary = "Метод получения списка пользователей, которые откликнулись на определённую вакансию")
    @GetMapping("/listUsers/{vacancyId}")
//    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<List<User>> listUsers(@PathVariable("vacancyId") Long vacancyId)
    {
        return responseService.listUsers(vacancyId);
    }

    @Operation(summary = "Метод создания отклика")
    @PostMapping("/create/{userId}/{vacancyId}")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createResponse(@PathVariable("userId") Long userId, @PathVariable("vacancyId") Long vacancyId)
    {
        return responseService.createResponse(userId, vacancyId);
    }

    @Operation(summary = "Метод удаления отклика")
    @DeleteMapping("/delete/{userId}/{vacancyId}")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteResponse(@PathVariable("userId") Long userId, @PathVariable("vacancyId") Long vacancyId)
    {
        return responseService.deleteResponse(userId, vacancyId);
    }

}
