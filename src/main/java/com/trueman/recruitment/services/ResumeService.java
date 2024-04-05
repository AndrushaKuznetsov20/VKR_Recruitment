package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.resume.CreateRequest;
import com.trueman.recruitment.dto.resume.ListResponse;
import com.trueman.recruitment.dto.resume.ReadRequest;
import com.trueman.recruitment.dto.resume.UpdateRequest;
import com.trueman.recruitment.models.Resume;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserService userService;

    public ResponseEntity<ListResponse> getAllResumes()
    {
        List<Resume> resumes = resumeRepository.findAll();
        List<ReadRequest> resumeDTOlList = new ArrayList<>();

        for(Resume resume : resumes)
        {
            ReadRequest resumeDTO = new ReadRequest();
            resumeDTO.setId(resume.getId());
            resumeDTO.setFullName(resume.getFullName());
            resumeDTO.setBirthDate(resume.getBirthDate());
            resumeDTO.setCity(resume.getCity());
            resumeDTO.setSkills(resume.getSkills());
            resumeDTO.setEducation(resume.getEducation());
            resumeDTO.setOtherInfo(resume.getOtherInfo());
            resumeDTO.setStatusResume(resume.getStatusResume());
            resumeDTO.setUser(resume.getUser());
            resumeDTOlList.add(resumeDTO);
        }

        ListResponse listResponseDTO = new ListResponse();
        listResponseDTO.setResumes(resumeDTOlList);

        return new ResponseEntity<>(listResponseDTO, HttpStatus.OK);
    }

    public ResponseEntity<Optional<Resume>> myResume()
    {
        User user = userService.getCurrentUser();
        Long userId = user.getId();

        Optional<Resume> resume = resumeRepository.findById(userId);

        return new ResponseEntity<>(resume,HttpStatus.OK);
    }

    public ResponseEntity<List<Resume>> getListResumesStatusOk()
    {
        List<Resume> resumes;
        resumes = resumeRepository.findAll();
        return new ResponseEntity<>(resumes, HttpStatus.OK);
    }

    public ResponseEntity<String> createResume(CreateRequest createRequest)
    {
        User user = userService.getCurrentUser();
        String status_resume_default = "Не модерировано!";

        var resume = Resume.builder()
                .fullName(createRequest.getFullName())
                .birthDate(createRequest.getBirthDate())
                .city(createRequest.getCity())
                .skills(createRequest.getSkills())
                .otherInfo(createRequest.getOtherInfo())
                .user(user)
                .build();

        resume.setStatusResume(status_resume_default);
        resumeRepository.save(resume);

        return ResponseEntity.ok("Резюме успешно создано и отправлено на проверку модератору!");
    }

    public ResponseEntity<String> updateResume(UpdateRequest updateRequest, Long resumeId)
    {
        Resume resume = resumeRepository.findById(resumeId).orElse(null);

        resume.setFullName(updateRequest.getFullName());
        resume.setBirthDate(updateRequest.getBirthDate());
        resume.setCity(updateRequest.getCity());
        resume.setSkills(updateRequest.getSkills());
        resume.setOtherInfo(updateRequest.getOtherInfo());

        resumeRepository.save(resume);

        return ResponseEntity.ok("Данные резюме успешно обновлены!");
    }

    public ResponseEntity<String> deleteResume(Long resumeId)
    {
        resumeRepository.deleteById(resumeId);
        return ResponseEntity.ok("Резюме успешно удалено!");
    }

    public ResponseEntity<String> setStatusOk(Long resumeId)
    {
        String status_resume = "Опубликовано!";

        Resume resume = resumeRepository.findById(resumeId).orElse(null);

        resume.setStatusResume(status_resume);
        resumeRepository.save(resume);

        return ResponseEntity.ok("Резюме успешно опубликовано!");
    }

    public ResponseEntity<String> setStatusBlock(Long resumeId)
    {
        String status_resume = "Заблокировано!";

        Resume resume = resumeRepository.findById(resumeId).orElse(null);

        resume.setStatusResume(status_resume);
        resumeRepository.save(resume);

        return ResponseEntity.ok("Резюме успешно заблокировано!");
    }
}
