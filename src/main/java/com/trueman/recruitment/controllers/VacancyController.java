package com.trueman.recruitment.controllers;

import com.trueman.recruitment.dto.vacancy.CreateRequest;
import com.trueman.recruitment.dto.vacancy.ListResponse;
import com.trueman.recruitment.dto.vacancy.UpdateRequest;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.services.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/vacancy")
@RequiredArgsConstructor
@Tag(name = "Методы для работы с вакансиями")
public class VacancyController {
    private final VacancyService vacancyService;

    @Operation(summary = "Получение списка вакансий")
    @GetMapping("/list/{pageNo}")
    @PreAuthorize("hasRole('MODER')")
    public ResponseEntity<ListResponse> getAllVacancies(@PathVariable(value = "pageNo") int pageNo)
    {
        int pageSize = 8;
        return vacancyService.getAllVacancies(pageNo, pageSize);
    }

    @Operation(summary = "Получение списка вакансий определённого пользователя")
    @GetMapping("/listMyVacancies/{pageNo}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<ListResponse> getListMyVacancies(@PathVariable(value = "pageNo") int pageNo)
    {
        int pageSize = 8;
        return vacancyService.listMyVacancies(pageNo, pageSize);
    }

    @Operation(summary = "Получение списка опубликованных вакансий")
    @GetMapping("/listVacanciesStatusOk/{pageNo}")
//    @PreAuthorize("hasRole('EMPLOYER','USER')")
    public ResponseEntity<ListResponse> getListVacanciesStatusOk(@PathVariable(value = "pageNo") int pageNo)
    {
        int pageSize = 8;
        return vacancyService.listVacanciesStatusOk(pageNo, pageSize);
    }

    @Operation(summary = "Создание вакансии")
    @PostMapping("/create")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> createVacancy(@RequestBody @Valid CreateRequest request, BindingResult bindingResult)
    {
        Integer wage = request.getWage();
        if(wage == null && wage <= 0)
        {
            return ResponseEntity.ok("Заработная плата должна быть целым числом больше нуля");
        }
        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        return vacancyService.createVacancy(request);
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

        return vacancyService.updateVacancy(vacancyId, updateRequest);
    }

    @Operation(summary = "Удаление вакансии")
    @DeleteMapping("/delete/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> deleteVacancy(@PathVariable("vacancyId") Long vacancyId)
    {
        return vacancyService.deleteVacancy(vacancyId);
    }

    @Operation(summary = "Публикация вакансии")
    @PutMapping("/setStatusOk/{vacancyId}")
    @PreAuthorize("hasRole('MODER')")
    public ResponseEntity<String> setStatusOk(@PathVariable("vacancyId") Long vacancyId)
    {
        return vacancyService.setStatusOk(vacancyId);
    }

    @Operation(summary = "Блокировка вакансии")
    @PutMapping("/setStatusBlock/{vacancyId}")
    @PreAuthorize("hasRole('MODER')")
    public ResponseEntity<String> setStatusBlock(@PathVariable("vacancyId") Long vacancyId)
    {
        return vacancyService.setStatusBlock(vacancyId);
    }

    @Operation(summary = "Поиск вакансий по заданным критериям")
    @GetMapping("/searchVacancies")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Vacancy>> searchVacancy(@RequestParam(required = false) String nameVacancy,
                                                       @RequestParam(required = false) String description,
                                                       @RequestParam(required = false) String schedule,
                                                       @RequestParam(required = false) String conditions_and_requirements,
                                                       @RequestParam(required = false) Integer minWage,
                                                       @RequestParam(required = false) Integer maxWage)
    {
        return vacancyService.getSearchVacancies(nameVacancy,description,schedule,conditions_and_requirements,minWage,maxWage);
    }
}
