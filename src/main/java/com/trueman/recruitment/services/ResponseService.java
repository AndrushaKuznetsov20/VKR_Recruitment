package com.trueman.recruitment.services;

import com.trueman.recruitment.models.Response;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.ResponseRepository;
import com.trueman.recruitment.repositories.UserRepository;
import com.trueman.recruitment.repositories.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final VacancyRepository vacancyRepository;
    private final ResponseRepository responseRepository;
    private final UserService userService;

    public ResponseEntity<List<User>> listUsers(Long vacancyId)
    {
        List<Response> responses = responseRepository.findByVacancyId(vacancyId);
        List<User> userList = new ArrayList<>();

        for(Response response : responses)
        {
            userList.add(response.getUser());
        }

        return new ResponseEntity<>(userList,HttpStatus.OK);
    }

    public ResponseEntity<List<Vacancy>> listVacancy(Long userId)
    {
        List<Response> responses = responseRepository.findByUserId(userId);

        List<Vacancy> vacancyList = new ArrayList<>();

        for(Response response : responses)
        {
            vacancyList.add(response.getVacancy());
        }

        return new ResponseEntity<>(vacancyList,HttpStatus.OK);
    }

    public ResponseEntity<String> createResponse(Long vacancyId)
    {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
        User user = userService.getCurrentUser();
        Long userId = user.getId();

        Response existingResponse = responseRepository.findByUserIdAndVacancyId(userId, vacancyId);

        if(existingResponse != null)
        {
            return ResponseEntity.ok("Вы уже оставили свой отклик на данную вакансию!");
        }

        Response response = new Response();

        response.setUser(user);
        response.setVacancy(vacancy);
        response.setCurrentDateTime();
        response.setStatusResponse("Не обработан!");

        responseRepository.save(response);

        return ResponseEntity.ok("Отклик успешно оставлен!");
    }

    public ResponseEntity<String> deleteResponse(Long userId, Long vacancyId)
    {
        Response response = responseRepository.findByUserIdAndVacancyId(userId, vacancyId);
        responseRepository.delete(response);
        return ResponseEntity.ok("Отклик успешно удалён!");
    }

    public ResponseEntity<String> setStatusResponseSelfDenial(Long userId, Long vacancyId)
    {
        Response response = responseRepository.findByUserIdAndVacancyId(userId, vacancyId);
        response.setStatusResponse("Самоотказ!");
        responseRepository.save(response);
        return ResponseEntity.ok("Статус отклика сменён на 'Самоотказ'");
    }
}
