package com.trueman.recruitment.controllers;

import com.trueman.recruitment.dto.vacancy.CreateRequest;
import com.trueman.recruitment.dto.vacancy.ReadRequest;
import com.trueman.recruitment.dto.vacancy.ListResponse;
import com.trueman.recruitment.dto.vacancy.UpdateRequest;
import com.trueman.recruitment.services.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/vacancy")
@RequiredArgsConstructor
@Tag(name = "Методы для работы с вакансиями")
public class VacancyController {

    private final VacancyService vacancyService;

    @Operation(summary = "Получение списка вакансий")
    @GetMapping("/list")
    public ResponseEntity<ListResponse> getAllVacancies()
    {
        ListResponse vacancies;
        vacancies = vacancyService.getAllVacancies();
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @Operation(summary = "Создание вакансии")
    @PostMapping("/create")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> createVacancy(@RequestBody @Valid CreateRequest request, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        vacancyService.createVacancy(request);
        return ResponseEntity.ok("Вакансия успешно создана !");
    }

    @Operation(summary = "Обновление данных о вакансии")
    @PutMapping("/update/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> updateVacancy(@PathVariable("vacancyId") Long vacancyId,
                                                @RequestBody @Valid UpdateRequest updateRequest, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        vacancyService.updateVacancy(vacancyId, updateRequest);
        return ResponseEntity.ok("Данные о вакансии успешно обновлены !");
    }

    @Operation(summary = "Удаление вакансии")
    @DeleteMapping("/delete/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> deleteVacancy(@PathVariable("vacancyId") Long vacancyId) {
        vacancyService.deleteVacancy(vacancyId);
        return ResponseEntity.ok("Вакансия успешно удалена !");
    }

}
