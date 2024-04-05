package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.vacancy.CreateRequest;
import com.trueman.recruitment.dto.vacancy.ReadRequest;
import com.trueman.recruitment.dto.vacancy.ListResponse;
import com.trueman.recruitment.dto.vacancy.UpdateRequest;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final UserService userService;
    private final VacancyRepository vacancyRepository;

    public ResponseEntity<ListResponse> getAllVacancies() {
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
            vacancyDTOList.add(vacancyDTO);
        }

        ListResponse vacancyListDTO = new ListResponse();
        vacancyListDTO.setVacancies(vacancyDTOList);

        return new ResponseEntity<>(vacancyListDTO, HttpStatus.OK);

    }

    public ResponseEntity<List<Vacancy>> listMyVacancies()
    {
        List<Vacancy> vacancies;
        User user = userService.getCurrentUser();
        vacancies = vacancyRepository.findByUserId(user.getId());
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    public ResponseEntity<List<Vacancy>> listVacanciesStatusOk()
    {
        List<Vacancy> vacancies;
        vacancies = vacancyRepository.findAll();
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    public ResponseEntity<String> createVacancy(CreateRequest request)
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
        vacancy.setCreateDateTime();
        vacancyRepository.save(vacancy);

        return ResponseEntity.ok("Вакансия успешно создана и отправлена на проверку модератору!");
    }

    public ResponseEntity<String> updateVacancy(Long vacancyId, UpdateRequest updateRequest)
    {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);

        vacancy.setName_vacancy(updateRequest.getName_vacancy());
        vacancy.setDescription_vacancy(updateRequest.getDescription_vacancy());
        vacancy.setConditions_and_requirements(updateRequest.getConditions_and_requirements());

        vacancyRepository.save(vacancy);

        return ResponseEntity.ok("Данные о вакансии успешно обновлены !");
    }

    public ResponseEntity<String> setStatusOk(Long vacancyId)
    {
        String status_vacancy = "Опубликовано!";

        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
        vacancy.setStatus_vacancy(status_vacancy);

        vacancyRepository.save(vacancy);

        return ResponseEntity.ok("Вакансия успешно опубликована!");
    }

    public ResponseEntity<String> setStatusBlock(Long vacancyId)
    {
        String status_vacancy = "Заблокировано!";

        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
        vacancy.setStatus_vacancy(status_vacancy);

        vacancyRepository.save(vacancy);

        return ResponseEntity.ok("Вакансия успешно заблокирована!");
    }

    public ResponseEntity<String> deleteVacancy(Long vacancyId)
    {
        vacancyRepository.deleteById(vacancyId);
        return ResponseEntity.ok("Вакансия успешно удалена!");
    }

}
