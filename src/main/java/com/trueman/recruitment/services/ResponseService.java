package com.trueman.recruitment.services;

import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.UserRepository;
import com.trueman.recruitment.repositories.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;

    public void createResponse(Long userId, Long vacancyId)
    {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        vacancy.getUserList().add(user);
        user.getListVacancy().add(vacancy);

        vacancyRepository.save(vacancy);
        userRepository.save(user);
    }
}
