package com.trueman.recruitment.services;

import com.trueman.recruitment.models.MetricsReportingHistory;
import com.trueman.recruitment.repositories.MetricsReportingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricsReportingHistoryService {

    private final MetricsReportingHistoryRepository metricsReportingHistoryRepository;

    public ResponseEntity<String> createMetricsReporting(MetricsReportingHistory metricsReportingHistory)
    {
        metricsReportingHistoryRepository.save(metricsReportingHistory);

        return ResponseEntity.ok("Данные успешно сохранены! Вы сможете посмотреть их в истории отчётов");
    }
}
