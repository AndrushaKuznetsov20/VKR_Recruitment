package com.trueman.recruitment.services;

import com.trueman.recruitment.models.Response;
import com.trueman.recruitment.models.Resume;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.ResponseRepository;
import com.trueman.recruitment.repositories.ResponseToResumeRepository;
import com.trueman.recruitment.repositories.ResumeRepository;
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
    private final ResponseToResumeRepository responseToResumeRepository;

    public ResponseEntity<Integer> getCountVacancy(LocalDate startDate, LocalDate endDate)
    {
        User user = userService.getCurrentUser();
        Long userId = user.getId();

        int count = vacancyRepository.countVacancyInDateTimeRange(startDate, endDate, userId);

        return ResponseEntity.ok(count);
    }

    public ResponseEntity<Integer> getVacancyResponseCount(Long vacancyId)
    {
        int count = responseRepository.vacancyResponseCount(vacancyId);

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

    public ResponseEntity<Integer> getVacancySelfDanialCount(Long vacancyId)
    {
        int count = responseRepository.vacancySelfDanialCount(vacancyId);

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


    public ResponseEntity<Integer> getCountRefusalEmployer(Long vacancyId)
    {
        int count = responseRepository.vacancyRefusalEmployerCount(vacancyId);

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


    public ResponseEntity<Integer> getCountRelevantResponses(Long vacancyId)
    {
        int count = responseRepository.vacancyRelevantResponsesCount(vacancyId);

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


    public ResponseEntity<Integer> getСountInvitation(Long vacancyId)
    {
        int count = responseRepository.vacancyInvitationCount(vacancyId);

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

    public ResponseEntity<Integer> getAllCountFoundResume(LocalDate startDate, LocalDate endDate)
    {

        User user = userService.getCurrentUser();

        int count = responseToResumeRepository.countAllFoundResume(startDate, endDate, user.getId());

        return ResponseEntity.ok(count);
    }
}
