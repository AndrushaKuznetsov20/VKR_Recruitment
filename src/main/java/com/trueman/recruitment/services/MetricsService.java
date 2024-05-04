package com.trueman.recruitment.services;

import com.trueman.recruitment.models.Response;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.ResponseRepository;
import com.trueman.recruitment.repositories.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetricsService {
    private final VacancyRepository vacancyRepository;
    private final UserService userService;
    private final ResponseRepository responseRepository;

    public ResponseEntity<Integer> getCountVacancy(LocalDate startDate, LocalDate endDate)
    {
        User user = userService.getCurrentUser();
        Long userId = user.getId();

        int count = vacancyRepository.countVacancyInDateTimeRange(startDate, endDate, userId);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getVacancyResponseCount(LocalDate startDate, LocalDate endDate, Long vacancyId)
    {
        int count = responseRepository.countResponses(startDate, endDate, vacancyId);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getAllResponses(LocalDate startDate, LocalDate endDate)
    {
        int count = 0;

        User user = userService.getCurrentUser();
        List<Vacancy> vacancyList = vacancyRepository.findByUserId(user.getId());

        for(Vacancy vacancy : vacancyList) {
            count += responseRepository.countResponses(startDate, endDate, vacancy.getId());
        }

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getVacancySelfDanialCount(LocalDate startDate, LocalDate endDate, Long vacancyId)
    {
        int count = responseRepository.countSelfDanial(startDate, endDate, vacancyId);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getAllCountSelfDanial(LocalDate startDate, LocalDate endDate)
    {
        int count = 0;

        User user = userService.getCurrentUser();
        List<Vacancy> vacancyList = vacancyRepository.findByUserId(user.getId());

        for(Vacancy vacancy : vacancyList) {
            count += responseRepository.countSelfDanial(startDate, endDate, vacancy.getId());
        }

        return ResponseEntity.ok(count);
    }


    public ResponseEntity<Integer> getCountRefusalEmployer(LocalDate startDate, LocalDate endDate, Long vacancyId)
    {
        int count = responseRepository.countRefusalEmployer(startDate, endDate, vacancyId);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getAllRefusalEmployer(LocalDate startDate, LocalDate endDate)
    {
        int count = 0;

        User user = userService.getCurrentUser();
        List<Vacancy> vacancyList = vacancyRepository.findByUserId(user.getId());

        for(Vacancy vacancy : vacancyList) {
            count += responseRepository.countRefusalEmployer(startDate, endDate, vacancy.getId());
        }

        return ResponseEntity.ok(count);
    }


    public ResponseEntity<Integer> getCountRelevantResponses(LocalDate startDate, LocalDate endDate, Long vacancyId)
    {
        int count = responseRepository.countRelevantResponses(startDate, endDate, vacancyId);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getAllRelevantResponse(LocalDate startDate, LocalDate endDate)
    {
        int count = 0;

        User user = userService.getCurrentUser();
        List<Vacancy> vacancyList = vacancyRepository.findByUserId(user.getId());

        for(Vacancy vacancy : vacancyList) {
            count += responseRepository.countRelevantResponses(startDate, endDate, vacancy.getId());
        }

        return ResponseEntity.ok(count);
    }


    public ResponseEntity<Integer> getСountInvitation(LocalDate startDate, LocalDate endDate, Long vacancyId)
    {
        int count = responseRepository.сountInvitation(startDate, endDate, vacancyId);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getAllInvitation(LocalDate startDate, LocalDate endDate)
    {
        int count = 0;

        User user = userService.getCurrentUser();
        List<Vacancy> vacancyList = vacancyRepository.findByUserId(user.getId());

        for(Vacancy vacancy : vacancyList) {
        count += responseRepository.сountInvitation(startDate, endDate, vacancy.getId());
        }

        return ResponseEntity.ok(count);
    }
}
