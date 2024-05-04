package com.trueman.recruitment.controllers;

import com.trueman.recruitment.models.Response;
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

    @Operation(summary = "Метод получения списка откликов на определённую вакансию")
    @GetMapping("/listResponse/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<List<Response>> listResponse(@PathVariable("vacancyId") Long vacancyId)
    {
        return responseService.listResponse(vacancyId);
    }
    @Operation(summary = "Метод получения списка пользователей, которые откликнулись на определённую вакансию")
    @GetMapping("/listUsers/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<List<User>> listUsers(@PathVariable("vacancyId") Long vacancyId)
    {
        return responseService.listUsers(vacancyId);
    }

    @Operation(summary = "Получение списка вакансий на которые определённый пользователь оставил отклики")
    @GetMapping("/listVacancy/{pageNo}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Vacancy>> listVacancy(@PathVariable("pageNo") int pageNo)
    {
        int pageSize = 8;
        return responseService.listVacancy(pageNo, pageSize);
    }

    @Operation(summary = "Метод создания отклика")
    @PostMapping("/create/{vacancyId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createResponse(@PathVariable("vacancyId") Long vacancyId)
    {
        return responseService.createResponse(vacancyId);
    }

    @Operation(summary = "Метод удаления отклика")
    @DeleteMapping("/delete/{vacancyId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteResponse(@PathVariable("vacancyId") Long vacancyId)
    {
        return responseService.deleteResponse(vacancyId);
    }

    @Operation(summary = "Метод установки статуса 'Самоотказ'")
    @PutMapping("/setStatusSelfDenial/{userId}/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> setStatusResponseSelfDenial(@PathVariable("userId") Long userId, @PathVariable("vacancyId") Long vacancyId)
    {
        return responseService.setStatusResponseSelfDenial(userId, vacancyId);
    }

    @Operation(summary = "Метод установки статуса 'Отказ работодателя'")
    @PutMapping("/setStatusRefusalEmployer/{userId}/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> setStatusResponseRefusalEmployer(@PathVariable("userId") Long userId, @PathVariable("vacancyId") Long vacancyId)
    {
        return responseService.setStatusResponseRefusalEmployer(userId, vacancyId);
    }

    @Operation(summary = "Метод установки статуса 'Релевантный отклик'")
    @PutMapping("/setStatusRelevant/{userId}/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> setStatusResponseRelevant(@PathVariable("userId") Long userId, @PathVariable("vacancyId") Long vacancyId)
    {
        return responseService.setStatusResponseRelevant(userId, vacancyId);
    }

    @Operation(summary = "Метод установки статуса 'Кандидат приглашён'")
    @PutMapping("/setStatusInvitation/{userId}/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> setStatusResponseInvitation(@PathVariable("userId") Long userId, @PathVariable("vacancyId") Long vacancyId)
    {
        return responseService.setStatusResponseInvitation(userId, vacancyId);
    }
}
