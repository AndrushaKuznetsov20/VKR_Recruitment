package com.trueman.recruitment.controllers;

import com.trueman.recruitment.dto.vacancy.CreateRequest;
import com.trueman.recruitment.dto.vacancy.ReadRequest;
import com.trueman.recruitment.dto.vacancy.ListResponse;
import com.trueman.recruitment.dto.vacancy.UpdateRequest;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.VacancyRepository;
import com.trueman.recruitment.services.UserService;
import com.trueman.recruitment.services.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final VacancyRepository vacancyRepository;
    private final UserService userService;

    @Operation(summary = "Получение списка вакансий")
    @GetMapping("/list")
    @PreAuthorize("hasRole('MODER')")
    public ResponseEntity<ListResponse> getAllVacancies()
    {
        ListResponse vacancies;
        vacancies = vacancyService.getAllVacancies();
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }
    @Operation(summary = "Получение списка вакансий определённого пользователя")
    @GetMapping("/listMyVacancies")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<List<Vacancy>> listMyVacancies()
    {
        List<Vacancy> vacancies;
        User user = userService.getCurrentUser();
        vacancies = vacancyRepository.findByUserId(user.getId());
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @Operation(summary = "Получение списка опубликованных вакансий")
    @GetMapping("/listVacanciesStatusOk")
    @PreAuthorize("hasRole('EMPLOYER','USER')")
    public ResponseEntity<List<Vacancy>> listVacanciesStatusOk()
    {
        List<Vacancy> vacancies;
        vacancies = vacancyRepository.findAll();
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

    @Operation(summary = "Публикация вакансии")
    @PutMapping("/setStatusOk/{vacancyId}")
    @PreAuthorize("hasRole('MODER')")
    public ResponseEntity<String> setStatusOk(@PathVariable("vacancyId") Long vacancyId) {

        String status_vacancy = "Опубликовано!";

        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
        vacancy.setStatus_vacancy(status_vacancy);

        vacancyRepository.save(vacancy);
        return ResponseEntity.ok("Вакансия успешно опубликована!");
    }

    @Operation(summary = "Блокировка вакансии")
    @PutMapping("/setStatusBlock/{vacancyId}")
    @PreAuthorize("hasRole('MODER')")
    public ResponseEntity<String> setStatusBlock(@PathVariable("vacancyId") Long vacancyId) {

        String status_vacancy = "Заблокировано!";

        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
        vacancy.setStatus_vacancy(status_vacancy);

        vacancyRepository.save(vacancy);
        return ResponseEntity.ok("Вакансия успешно заблокирована!");
    }
}
