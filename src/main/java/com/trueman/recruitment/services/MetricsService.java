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

    public ResponseEntity<Integer> getAllResponses(LocalDateTime startDateTime, LocalDateTime endDateTime)
    {
        int count = responseRepository.findAll(startDateTime, endDateTime);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getCountSelfDanial(LocalDateTime startDateTime, LocalDateTime endDateTime, Long vacancyId)
    {
        int count = responseRepository.countSelfDanial(startDateTime, endDateTime, vacancyId);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getCountRefusalEmployer(LocalDateTime startDateTime, LocalDateTime endDateTime, Long vacancyId)
    {
        int count = responseRepository.countRefusalEmployer(startDateTime, endDateTime, vacancyId);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getCountRelevantResponses(LocalDateTime startDateTime, LocalDateTime endDateTime, Long vacancyId)
    {
        int count = responseRepository.countRelevantResponses(startDateTime, endDateTime, vacancyId);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getСountInvitation(LocalDateTime startDateTime, LocalDateTime endDateTime, Long vacancyId)
    {
        int count = responseRepository.сountInvitation(startDateTime, endDateTime, vacancyId);

        return ResponseEntity.ok(count);
    }
}
