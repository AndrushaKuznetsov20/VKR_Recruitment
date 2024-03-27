package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.vacancy.CreateVacancyRequest;
import com.trueman.recruitment.dto.vacancy.VacancyDTO;
import com.trueman.recruitment.dto.vacancy.VacancyListDTO;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final UserService userService;
    private final VacancyRepository vacancyRepository;

    public VacancyListDTO getAllVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAll();
        List<VacancyDTO> vacancyDTOList = new ArrayList<>();

        for (Vacancy vacancy : vacancies) {
            VacancyDTO vacancyDTO = new VacancyDTO();
            vacancyDTO.setId(vacancy.getId());
            vacancyDTO.setName_vacancy(vacancy.getName_vacancy());
            vacancyDTO.setDescription_vacancy(vacancy.getDescription_vacancy());
            vacancyDTO.setStatus_vacancy(vacancy.getConditions_and_requirements());
            vacancyDTO.setUser(vacancy.getUser());
            vacancyDTOList.add(vacancyDTO);
        }

        VacancyListDTO vacancyListDTO = new VacancyListDTO();
        vacancyListDTO.setVacancies(vacancyDTOList);

        return vacancyListDTO;

    }
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
    public void deleteVacancy(Long vacancyId)
    {
        vacancyRepository.deleteById(vacancyId);
    }
}
