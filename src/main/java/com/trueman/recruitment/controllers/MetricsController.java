package com.trueman.recruitment.controllers;

import com.trueman.recruitment.models.Message;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.repositories.VacancyRepository;
import com.trueman.recruitment.services.MetricsService;
import com.trueman.recruitment.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
@Tag(name = "Расчёт метрик")
public class MetricsController {

    private final VacancyRepository vacancyRepository;
    private final UserService userService;
    private final MetricsService metricsService;

    @Operation(summary = "Расчёт количества созданных вакансий за определённый период времени")
    @GetMapping("/countVacancy/{startDate}/{endDate}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countVacancy(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate)
    {
        return metricsService.getCountVacancy(startDate, endDate);
    }

    @Operation(summary = "Расчёт количества откликов на определённую вакансию")
    @GetMapping("/vacancyResponseCount/{startDate}/{endDate}/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> vacancyResponseCount(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate,
                                                        @PathVariable("vacancyId") Long vacancyId)
    {
        return metricsService.getVacancyResponseCount(startDate, endDate, vacancyId);
    }

    @Operation(summary = "Расчёт общего количества откликов за определённый период времени")
    @GetMapping("/countAllResponses/{startDate}/{endDate}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countAllResponses(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate)
    {

        return metricsService.getAllResponses(startDate, endDate);
    }

    @Operation(summary = "Расчёт количества самоотказов на определённую вакансию")
    @GetMapping("/vacancySelfDanialCount/{startDate}/{endDate}/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> vacancySelfDanialCount(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate,
                                                          @PathVariable("vacancyId") Long vacancyId)
    {
        return metricsService.getVacancySelfDanialCount(startDate, endDate, vacancyId);
    }

    @Operation(summary = "Расчёт общего количества самоотказов за определённый период времени")
    @GetMapping("/countAllSelfDanial/{startDate}/{endDate}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countAllSelfDanial(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate)
    {
        return metricsService.getAllCountSelfDanial(startDate, endDate);
    }

    @Operation(summary = "Расчёт количества отказов работодателя на определённую вакансию")
    @GetMapping("/vacancyRefusalEmployerCount/{startDate}/{endDate}/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> vacancyRefusalEmployerCount(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate,
                                                               @PathVariable("vacancyId") Long vacancyId)
    {
        return metricsService.getCountRefusalEmployer(startDate, endDate, vacancyId);
    }

    @Operation(summary = "Расчёт общего количества отказов работодателя за определённый период времени")
    @GetMapping("/countAllRefusalEmployer/{startDate}/{endDate}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countAllRefusalEmployer(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate)
    {
        return metricsService.getAllRefusalEmployer(startDate, endDate);
    }


    @Operation(summary = "Расчёт количества релевантных откликов на определённую вакансию")
    @GetMapping("/vacancyRelevantResponsesCount/{startDate}/{endDate}/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> vacancyRelevantResponsesCount(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate,
                                                                 @PathVariable("vacancyId") Long vacancyId)
    {
        return metricsService.getCountRelevantResponses(startDate, endDate, vacancyId);
    }

    @Operation(summary = "Расчёт общего количества релевантных откликов за определённый период")
    @GetMapping("/countAllRelevantResponses/{startDate}/{endDate}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countAllRelevantResponses(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                                             @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate)
    {
        return metricsService.getAllRelevantResponse(startDate, endDate);
    }

    @Operation(summary = "Расчёт количества приглашений на определённую вакансию")
    @GetMapping("/countInvitation/{startDate}/{endDate}/{vacancyId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> vacancyInvitationCount(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate,
                                                          @PathVariable("vacancyId") Long vacancyId)
    {
        return metricsService.getСountInvitation(startDate, endDate, vacancyId);
    }

    @Operation(summary = "Расчёт количества приглашений за определённый период времени")
    @GetMapping("/countAllInvitation/{startDate}/{endDate}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countAllInvitation(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                                      @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate)
    {
        return metricsService.getAllInvitation(startDate, endDate);
    }
}
