package com.trueman.recruitment.controllers;

import com.trueman.recruitment.models.ResponseToResume;
import com.trueman.recruitment.models.Resume;
import com.trueman.recruitment.services.ResponseToResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/responseToResume")
@RequiredArgsConstructor
@Tag(name = "Методы для работы с откликами на резюме")
public class ResponseToResumeController {

    private final ResponseToResumeService responseToResumeService;


    @Operation(summary = "Метод получения списка резюме на которые определённый пользователь оставил отклики")
    @GetMapping("/listMyResponsesToResume/{pageNo}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<List<Resume>> listMyResponsesToResume(@PathVariable("pageNo") int pageNo)
    {
        int pageSize = 8;
        return responseToResumeService.listResponseUser(pageNo, pageSize);
    }

    @Operation(summary = "Метод создания отклика на резюме")
    @PostMapping("/create/{resumeId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> createResponseToResume(@PathVariable("resumeId") Long resumeId)
    {
        return responseToResumeService.createResponseToResume(resumeId);
    }

    @Operation(summary = "Метод удаления отклика на резюме")
    @DeleteMapping("/delete/{resumeId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<String> deleteResponseToResume(@PathVariable("resumeId") Long resumeId)
    {
        return responseToResumeService.deleteResponseResume(resumeId);
    }

}
