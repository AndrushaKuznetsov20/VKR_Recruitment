package com.trueman.recruitment.controllers;

import com.trueman.recruitment.dto.SignUpRequest;
import com.trueman.recruitment.dto.vacancy.CreateVacancyRequest;
import com.trueman.recruitment.services.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/vacancy")
@RequiredArgsConstructor
@Tag(name = "Методы для работы с вакансиями")
public class VacancyController {

    private final VacancyService vacancyService;

    @Operation(summary = "Создание вакансии")
    @PostMapping("/createVacancy")
    public ResponseEntity<String> createVacancy(@RequestBody @Valid CreateVacancyRequest request)
    {
        vacancyService.createVacancy(request);
        return ResponseEntity.ok("Вакансия успешно создана !");
    }
}
