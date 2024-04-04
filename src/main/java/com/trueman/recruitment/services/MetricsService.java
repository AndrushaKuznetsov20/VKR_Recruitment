package com.trueman.recruitment.services;

import com.trueman.recruitment.models.User;
import com.trueman.recruitment.repositories.ResponseRepository;
import com.trueman.recruitment.repositories.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MetricsService {
    private final VacancyRepository vacancyRepository;
    private final UserService userService;
    private final ResponseRepository responseRepository;

    public ResponseEntity<Integer> getCountVacancy(LocalDateTime startDateTime, LocalDateTime endDateTime)
    {
        User user = userService.getCurrentUser();
        Long userId = user.getId();

        int count = vacancyRepository.countVacancyInDateTimeRange(startDateTime, endDateTime, userId);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getResponses(LocalDateTime startDateTime, LocalDateTime endDateTime, Long vacancyId)
    {
        int count = responseRepository.countResponses(startDateTime, endDateTime, vacancyId);

        return ResponseEntity.ok(count);
    }
}
