package com.trueman.recruitment.controllers;

import com.trueman.recruitment.dto.vacancy.CreateVacancyRequest;
import com.trueman.recruitment.dto.vacancy.VacancyDTO;
import com.trueman.recruitment.dto.vacancy.VacancyListDTO;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.services.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/vacancy")
@RequiredArgsConstructor
@Tag(name = "Методы для работы с вакансиями")
public class VacancyController {

    private final VacancyService vacancyService;

    @Operation(summary = "Получение списка вакансий")
    @GetMapping("/listVacancies")
    public ResponseEntity<VacancyListDTO> getAllVacancies()
    {
        VacancyListDTO vacancies;
        vacancies = vacancyService.getAllVacancies();
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @Operation(summary = "Создание вакансии")
    @PostMapping("/createVacancy")
    public ResponseEntity<String> createVacancy(@RequestBody @Valid CreateVacancyRequest request)
    {
        vacancyService.createVacancy(request);
        return ResponseEntity.ok("Вакансия успешно создана !");
    }
    @Operation(summary = "Удаление вакансии")
    @DeleteMapping("/delete/{vacancyId}")
    public ResponseEntity<String> deleteVacancy(@PathVariable("vacancyId") Long vacancyId) {
        vacancyService.deleteVacancy(vacancyId);
        return ResponseEntity.ok("Вакансия успешно удалена !");
    }
}
