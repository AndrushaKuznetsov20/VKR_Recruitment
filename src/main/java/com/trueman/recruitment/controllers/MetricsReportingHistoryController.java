package com.trueman.recruitment.controllers;

import com.trueman.recruitment.models.MetricsReportingHistory;
import com.trueman.recruitment.services.MetricsReportingHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/metricsReportingHistory")
@RequiredArgsConstructor
@Tag(name = "Сохранение истории расчёта метрик за разные периоды времени")
public class MetricsReportingHistoryController {

    private final MetricsReportingHistoryService metricsReportingHistoryService;

    @Operation(summary = "Вывод списка отчётов")
    @GetMapping("/getAllMetricsReportingHistory")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<List<MetricsReportingHistory>> getAllMetricsReportingHistory()
    {
        return metricsReportingHistoryService.getAllMetricsReportingHistory();
    }

    @Operation(summary = "Создание отчёта")
    @PostMapping("/create")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> createMetricsReporting(@RequestBody MetricsReportingHistory metricsReportingHistory)
    {
        return metricsReportingHistoryService.createMetricsReporting(metricsReportingHistory);
    }

    @Operation(summary = "Удаление отчёта")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> deleteMetricsReporting(@PathVariable("id") Long id)
    {
        return metricsReportingHistoryService.deleteMetricsReporting(id);
    }
}
