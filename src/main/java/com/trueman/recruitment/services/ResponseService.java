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

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;
    private final ResponseRepository responseRepository;

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
    public ResponseEntity<String> createResponse(Long userId, Long vacancyId)
    {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        Response existingResponse = responseRepository.findByUserIdAndVacancyId(userId, vacancyId);

        if(existingResponse != null)
        {
            return ResponseEntity.ok("Вы уже оставили свой отклик на данную вакансию!");
        }

        Response response = new Response();

        response.setUser(user);
        response.setVacancy(vacancy);
        response.setCurrentDateTime();

        responseRepository.save(response);

        return ResponseEntity.ok("Отклик успешно оставлен!");
    }

    public ResponseEntity<String> deleteResponse(Long userId, Long vacancyId)
    {
        Response response = responseRepository.findByUserIdAndVacancyId(userId, vacancyId);
        responseRepository.delete(response);
        return ResponseEntity.ok("Отклик успешно удалён!");
    }
}
