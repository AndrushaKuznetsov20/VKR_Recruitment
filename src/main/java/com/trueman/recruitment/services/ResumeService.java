package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.resume.CreateRequest;
import com.trueman.recruitment.dto.resume.ListResponse;
import com.trueman.recruitment.dto.resume.ReadRequest;
import com.trueman.recruitment.dto.resume.UpdateRequest;
import com.trueman.recruitment.models.Resume;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.repositories.ResumeRepository;
import com.trueman.recruitment.specification.ResumeSpecification;
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
import java.util.Optional;
import java.util.OptionalInt;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserService userService;

    public ResponseEntity<ListResponse> getAllResumes(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Resume> resumes = resumeRepository.findAll(pageable);

        List<ReadRequest> resumeDTOlList = new ArrayList<>();

        for(Resume resume : resumes.getContent())
        {
            ReadRequest resumeDTO = new ReadRequest();
            resumeDTO.setId(resume.getId());
            resumeDTO.setFullName(resume.getFullName());
            resumeDTO.setBirthDate(resume.getBirthDate());
            resumeDTO.setAge(resume.getAge());
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

    public ResponseEntity<?> myResume()
    {
        User user = userService.getCurrentUser();
        Long userId = user.getId();

        Optional<Resume> resume = resumeRepository.findByUserId(userId);

        if(resume == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(resume,HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getResumeById(Long id)
    {
        Optional<Resume> resume = resumeRepository.findByUserId(id);

        if(resume.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(resume,HttpStatus.OK);
        }
    }

    public ResponseEntity<ListResponse> getListResumesStatusOk(int pageNo, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Resume> resumes = resumeRepository.findAllStatusOk(pageable);

        List<ReadRequest> resumeDTOlList = new ArrayList<>();

        for(Resume resume : resumes.getContent())
        {
            ReadRequest resumeDTO = new ReadRequest();
            resumeDTO.setId(resume.getId());
            resumeDTO.setFullName(resume.getFullName());
            resumeDTO.setBirthDate(resume.getBirthDate());
            resumeDTO.setAge(resume.getAge());
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

    public ResponseEntity<String> createResume(CreateRequest createRequest)
    {
        User user = userService.getCurrentUser();
        String status_resume_default = "Не модерировано!";

        var resume = Resume.builder()
                .fullName(createRequest.getFullName())
                .birthDate(createRequest.getBirthDate())
                .city(createRequest.getCity())
                .skills(createRequest.getSkills())
                .education(createRequest.getEducation())
                .otherInfo(createRequest.getOtherInfo())
                .user(user)
                .build();

        resume.setStatusResume(status_resume_default);
        resume.calculationAge();

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
        resume.setEducation(updateRequest.getEducation());
        resume.setOtherInfo(updateRequest.getOtherInfo());

        resume.calculationAge();
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

    public ResponseEntity<List<Resume>> getSearchResumes(String fullName, String city, String skills, String education, Integer minAge, Integer maxAge)
    {
        Specification<Resume> specification = Specification.where(null);

        if(fullName != null && !fullName.isEmpty())
        {
            specification = specification.and(ResumeSpecification.findByFullName(fullName));
        }

        if(city != null && !city.isEmpty())
        {
            specification = specification.and(ResumeSpecification.findByCity(city));
        }

        if(skills != null && !skills.isEmpty())
        {
            specification = specification.and(ResumeSpecification.findBySkills(skills));
        }

        if(education != null && !education.isEmpty())
        {
            specification = specification.and(ResumeSpecification.findByEducation(education));
        }

        if(minAge != null && maxAge != null)
        {
            specification = specification.and(ResumeSpecification.findByAge(minAge, maxAge));
        }

        List<Resume> resumes = resumeRepository.findAll(specification);

        return new ResponseEntity<>(resumes, HttpStatus.OK);
    }
}
