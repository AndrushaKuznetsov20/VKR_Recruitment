package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.JwtAuthenticationResponse;
import com.trueman.recruitment.dto.SignUpRequest;
import com.trueman.recruitment.dto.vacancy.CreateVacancyRequest;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.models.enums.Role;
import com.trueman.recruitment.repositories.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final UserService userService;
    private final VacancyRepository vacancyRepository;


    public Vacancy createVacancy(CreateVacancyRequest request)
    {
        User user = userService.getCurrentUser();
        String status_vacancy_default = "Не модерировано!";

        var vacancy = Vacancy.builder()
                .name_vacancy(request.getName_vacancy())
                .description_vacancy(request.getDescription_vacancy())
                .conditions_and_requirements(request.getConditions_and_requirements())
                .status_vacancy(status_vacancy_default)
                .user(user)
                .build();

        return vacancyRepository.save(vacancy);
    }
}
