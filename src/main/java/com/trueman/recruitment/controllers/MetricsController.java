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
    @GetMapping("/countVacancy/{startDateTime}/{endDateTime}")
//    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countVacancy(@DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("startDateTime") LocalDateTime startDateTime,
                                                @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("endDateTime") LocalDateTime endDateTime)
    {
        return metricsService.getCountVacancy(startDateTime, endDateTime);
    }

    @Operation(summary = "Расчёт общего количества откликов за определённый период времени")
    @GetMapping("/countAllResponses/{startDateTime}/{endDateTime}")
//    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countAllResponses(@DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("startDateTime") LocalDateTime startDateTime,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("endDateTime") LocalDateTime endDateTime)
    {
        return metricsService.getAllResponses(startDateTime, endDateTime);
    }

    @Operation(summary = "Расчёт количества откликов на вакансию за определённый период времени")
    @GetMapping("/countResponses/{startDateTime}/{endDateTime}/{vacancyId}")
//    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countResponses(@DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("startDateTime") LocalDateTime startDateTime,
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("endDateTime") LocalDateTime endDateTime,
                                                  @PathVariable("vacancyId") Long vacancyId)
    {
        return metricsService.getResponses(startDateTime, endDateTime, vacancyId);
    }

    @Operation(summary = "Расчёт количества самоотказов")
    @GetMapping("/countSelfDanial/{startDateTime}/{endDateTime}/{vacancyId}")
//    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countSelfDanial(@DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("startDateTime") LocalDateTime startDateTime,
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("endDateTime") LocalDateTime endDateTime,
                                                   @PathVariable("vacancyId") Long vacancyId)
    {
        return metricsService.getCountSelfDanial(startDateTime, endDateTime, vacancyId);
    }

    @Operation(summary = "Расчёт количества отказов работодателя")
    @GetMapping("/countRefusalEmployer/{startDateTime}/{endDateTime}/{vacancyId}")
//    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countRefusalEmployer(@DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("startDateTime") LocalDateTime startDateTime,
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("endDateTime") LocalDateTime endDateTime,
                                                        @PathVariable("vacancyId") Long vacancyId)
    {
        return metricsService.getCountRefusalEmployer(startDateTime, endDateTime, vacancyId);
    }

    @Operation(summary = "Расчёт количества релевантных откликов")
    @GetMapping("/countRelevantResponses/{startDateTime}/{endDateTime}/{vacancyId}")
//    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countRelevantResponses(@DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("startDateTime") LocalDateTime startDateTime,
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("endDateTime") LocalDateTime endDateTime,
                                                          @PathVariable("vacancyId") Long vacancyId)
    {
        return metricsService.getCountRelevantResponses(startDateTime, endDateTime, vacancyId);
    }

    @Operation(summary = "Расчёт количества приглашений")
    @GetMapping("/countInvitation/{startDateTime}/{endDateTime}/{vacancyId}")
//    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Integer> countInvitation(@DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("startDateTime") LocalDateTime startDateTime,
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss") @PathVariable("endDateTime") LocalDateTime endDateTime,
                                                   @PathVariable("vacancyId") Long vacancyId)
    {
        return metricsService.getСountInvitation(startDateTime, endDateTime, vacancyId);
    }

}
