package com.trueman.recruitment.services;

import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.UserRepository;
import com.trueman.recruitment.repositories.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseService {
//    private final VacancyRepository vacancyRepository;
//    private final UserRepository userRepository;
//
//    public ResponseEntity<String> createResponse(Long userId, Long vacancyId)
//    {
//        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
//        User user = userRepository.findById(userId).orElse(null);
//
//        if(vacancy.getUserList().contains(user))
//        {
//            return ResponseEntity.ok("Вы уже оставляли отклик на данную вакансию!");
//        }
//
//        vacancy.getUserList().add(user);
//        user.getListVacancy().add(vacancy);
//
//        vacancyRepository.save(vacancy);
//        userRepository.save(user);
//
//        return ResponseEntity.ok("Отклик успешно оставлен!");
//    }
//    public ResponseEntity<String> deleteResponse(Long userId, Long vacancyId)
//    {
//        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
//        User user = userRepository.findById(userId).orElse(null);
//
//        vacancy.getUserList().remove(user);
//        user.getListVacancy().remove(vacancy);
//
//        vacancyRepository.save(vacancy);
//        userRepository.save(user);
//
//        return ResponseEntity.ok("Отклик успешно удалён!");
//    }
}
