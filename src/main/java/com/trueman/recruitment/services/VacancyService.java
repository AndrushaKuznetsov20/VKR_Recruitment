package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.vacancy.CreateRequest;
import com.trueman.recruitment.dto.vacancy.ReadRequest;
import com.trueman.recruitment.dto.vacancy.ListResponse;
import com.trueman.recruitment.dto.vacancy.UpdateRequest;
import com.trueman.recruitment.models.Resume;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.ResponseRepository;
import com.trueman.recruitment.repositories.VacancyRepository;
import com.trueman.recruitment.specification.ResumeSpecification;
import com.trueman.recruitment.specification.VacancySpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    private final ResponseRepository responseRepository;

    public ResponseEntity<ListResponse> getAllVacancies(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Vacancy> vacancies = vacancyRepository.findAll(pageable);
        List<ReadRequest> vacancyDTOList = new ArrayList<>();

        for (Vacancy vacancy : vacancies.getContent())
        {
            ReadRequest vacancyDTO = new ReadRequest();
            vacancyDTO.setId(vacancy.getId());
            vacancyDTO.setName_vacancy(vacancy.getName_vacancy());
            vacancyDTO.setDescription_vacancy(vacancy.getDescription_vacancy());
            vacancyDTO.setConditions_and_requirements(vacancy.getConditions_and_requirements());
            vacancyDTO.setWage(vacancy.getWage());
            vacancyDTO.setSchedule(vacancy.getSchedule());
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

    public ResponseEntity<ListResponse> listVacanciesStatusOk(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Vacancy> vacancies = vacancyRepository.findAllStatusOk(pageable);

        List<ReadRequest> vacancyDTOList = new ArrayList<>();

        for (Vacancy vacancy : vacancies.getContent())
        {
            ReadRequest vacancyDTO = new ReadRequest();
            vacancyDTO.setId(vacancy.getId());
            vacancyDTO.setName_vacancy(vacancy.getName_vacancy());
            vacancyDTO.setDescription_vacancy(vacancy.getDescription_vacancy());
            vacancyDTO.setConditions_and_requirements(vacancy.getConditions_and_requirements());
            vacancyDTO.setWage(vacancy.getWage());
            vacancyDTO.setSchedule(vacancy.getSchedule());
            vacancyDTO.setStatus_vacancy(vacancy.getStatus_vacancy());
            vacancyDTO.setUser(vacancy.getUser());
            vacancyDTOList.add(vacancyDTO);
        }

        ListResponse vacancyListDTO = new ListResponse();
        vacancyListDTO.setVacancies(vacancyDTOList);

        return new ResponseEntity<>(vacancyListDTO, HttpStatus.OK);
    }

    public ResponseEntity<String> createVacancy(CreateRequest request)
    {
        User user = userService.getCurrentUser();
        String status_vacancy_default = "Не модерирована!";

        var vacancy = Vacancy.builder()
                .name_vacancy(request.getName_vacancy())
                .description_vacancy(request.getDescription_vacancy())
                .conditions_and_requirements(request.getConditions_and_requirements())
                .wage(request.getWage())
                .schedule(request.getSchedule())
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
        vacancy.setWage(updateRequest.getWage());
        vacancy.setSchedule(updateRequest.getSchedule());

        vacancyRepository.save(vacancy);

        return ResponseEntity.ok("Данные о вакансии успешно обновлены !");
    }

    public ResponseEntity<String> setStatusOk(Long vacancyId)
    {
        String status_vacancy = "Опубликована!";

        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
        vacancy.setStatus_vacancy(status_vacancy);

        vacancyRepository.save(vacancy);

        return ResponseEntity.ok("Вакансия успешно опубликована!");
    }

    public ResponseEntity<String> setStatusBlock(Long vacancyId)
    {
        String status_vacancy = "Заблокирована!";

        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
        vacancy.setStatus_vacancy(status_vacancy);

        vacancyRepository.save(vacancy);

        return ResponseEntity.ok("Вакансия успешно заблокирована!");
    }

    @Transactional
    public ResponseEntity<String> deleteVacancy(Long vacancyId)
    {
        responseRepository.deleteByVacancyId(vacancyId);
        vacancyRepository.deleteById(vacancyId);
        return ResponseEntity.ok("Вакансия успешно удалена!");
    }

    public ResponseEntity<List<Vacancy>> getSearchVacancies(String nameVacancy, String description, String schedule,String conditions_and_requirements,
                                                            Integer minWage, Integer maxWage)
    {
        Specification<Vacancy> specification = Specification.where(null);

        if(nameVacancy!= null && !nameVacancy.isEmpty())
        {
            specification = specification.and(VacancySpecification.findByName(nameVacancy));
        }

        if(description != null && !description.isEmpty())
        {
            specification = specification.and(VacancySpecification.findByDescription(description));
        }

        if(schedule != null && !schedule.isEmpty())
        {
            specification = specification.and(VacancySpecification.findBySchedule(schedule));
        }

        if(conditions_and_requirements != null && !conditions_and_requirements.isEmpty())
        {
            specification = specification.and(VacancySpecification.findByConditionsAndRequirements(conditions_and_requirements));
        }

        if(minWage != null && maxWage != null)
        {
            specification = specification.and(VacancySpecification.findByWage(minWage, maxWage));
        }

        List<Vacancy> vacancies = vacancyRepository.findAll(specification);

        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }
}
