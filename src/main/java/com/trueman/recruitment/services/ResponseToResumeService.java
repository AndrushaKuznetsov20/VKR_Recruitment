package com.trueman.recruitment.services;

import com.trueman.recruitment.models.ResponseToResume;
import com.trueman.recruitment.models.Resume;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.repositories.ResponseToResumeRepository;
import com.trueman.recruitment.repositories.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResponseToResumeService {
    private final ResponseToResumeRepository responseToResumeRepository;
    private final ResumeRepository resumeRepository;
    private final UserService userService;

    public ResponseEntity<List<Resume>> listResponseUser(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        User user = userService.getCurrentUser();

        Page<ResponseToResume> responseToResumeList = responseToResumeRepository.findByUserId(user.getId(), pageable);

        List<Resume> resumes = new ArrayList<>();

        for(ResponseToResume responseToResume : responseToResumeList.getContent())
        {
            resumes.add(responseToResume.getResume());
        }
        return new ResponseEntity<>(resumes, HttpStatus.OK);
    }

    public ResponseEntity<String> createResponseToResume(Long resumeId)
    {
        User user = userService.getCurrentUser();
        Resume resume = resumeRepository.findById(resumeId).orElse(null);

        ResponseToResume existingResponseToResume = responseToResumeRepository.findByUserIdAndResumeId(user.getId(), resume.getId());

        if(existingResponseToResume != null)
        {
            return ResponseEntity.ok("Вы уже оставляли отклик на данное резюме!");
        }

        ResponseToResume responseToResume = new ResponseToResume();

        responseToResume.setUser(user);
        responseToResume.setResume(resume);
        responseToResume.setResponseDateNow();


        responseToResumeRepository.save(responseToResume);

        return ResponseEntity.ok("Отклик успешно оставлен!");
    }

    public ResponseEntity<String> deleteResponseResume(Long resumeId)
    {
        User user = userService.getCurrentUser();
        ResponseToResume responseToResume = responseToResumeRepository.findByUserIdAndResumeId(user.getId(), resumeId);

        responseToResumeRepository.delete(responseToResume);

        return ResponseEntity.ok("Отклики успешно удалён");
    }
}
