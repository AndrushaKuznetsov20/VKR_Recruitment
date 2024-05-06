package com.trueman.recruitment.services;

import com.trueman.recruitment.models.MetricsReportingHistory;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.repositories.MetricsReportingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetricsReportingHistoryService {

    private final MetricsReportingHistoryRepository metricsReportingHistoryRepository;
    private final UserService userService;
    public ResponseEntity<List<MetricsReportingHistory>> getAllMetricsReportingHistory()
    {
        User user = userService.getCurrentUser();
        List<MetricsReportingHistory> metricsReportingHistoryList = metricsReportingHistoryRepository.findByUserId(user.getId());

        return new ResponseEntity<>(metricsReportingHistoryList, HttpStatus.OK);
    }

    public ResponseEntity<String> createMetricsReporting(MetricsReportingHistory metricsReportingHistory)
    {
        metricsReportingHistoryRepository.save(metricsReportingHistory);

        return ResponseEntity.ok("Данные успешно сохранены! Вы сможете посмотреть их в истории отчётов");
    }
}
