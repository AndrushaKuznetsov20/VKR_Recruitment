package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.vacancy.CreateRequest;
import com.trueman.recruitment.dto.vacancy.ReadRequest;
import com.trueman.recruitment.dto.vacancy.ListResponse;
import com.trueman.recruitment.dto.vacancy.UpdateRequest;
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

    public ListResponse getAllVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAll();
        List<ReadRequest> vacancyDTOList = new ArrayList<>();

        for (Vacancy vacancy : vacancies) {
            ReadRequest vacancyDTO = new ReadRequest();
            vacancyDTO.setId(vacancy.getId());
            vacancyDTO.setName_vacancy(vacancy.getName_vacancy());
            vacancyDTO.setDescription_vacancy(vacancy.getDescription_vacancy());
            vacancyDTO.setConditions_and_requirements(vacancy.getConditions_and_requirements());
            vacancyDTO.setStatus_vacancy(vacancy.getStatus_vacancy());
            vacancyDTO.setUser(vacancy.getUser());
            vacancyDTO.setUserList(vacancy.getUserList());
            vacancyDTOList.add(vacancyDTO);
        }

        ListResponse vacancyListDTO = new ListResponse();
        vacancyListDTO.setVacancies(vacancyDTOList);

        return vacancyListDTO;

    }
    public Vacancy createVacancy(CreateRequest request)
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

    public Vacancy updateVacancy(Long vacancyId, UpdateRequest updateRequest)
    {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);

        vacancy.setName_vacancy(updateRequest.getName_vacancy());
        vacancy.setDescription_vacancy(updateRequest.getDescription_vacancy());
        vacancy.setConditions_and_requirements(updateRequest.getConditions_and_requirements());

        return vacancyRepository.save(vacancy);
    }
    public void deleteVacancy(Long vacancyId)
    {
        vacancyRepository.deleteById(vacancyId);
    }
}
