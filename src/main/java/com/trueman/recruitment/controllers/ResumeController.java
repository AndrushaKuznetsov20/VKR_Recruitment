package com.trueman.recruitment.controllers;

import com.trueman.recruitment.dto.resume.CreateRequest;
import com.trueman.recruitment.dto.resume.ListResponse;
import com.trueman.recruitment.dto.resume.UpdateRequest;
import com.trueman.recruitment.models.Resume;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.services.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
@Tag(name = "Методы для работы с резюме")
public class ResumeController {

    private final ResumeService resumeService;

    @Operation(summary = "Получение списка резюме")
    @GetMapping("/list")
//    @PreAuthorize("hasRole('MODER')")
    public ResponseEntity<ListResponse> getAllResumes()
    {
        return resumeService.getAllResumes();
    }

    @Operation(summary = "Получение резюме по Id")
    @GetMapping("/myResume")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Optional<Resume>> getMyResume()
    {
        return resumeService.myResume();
    }

    @Operation(summary = "Получение списка опубликованных резюме")
    @GetMapping("/listResumeStatusOk")
    public ResponseEntity<List<Resume>> getListResumesStatusOk()
    {
        return resumeService.getListResumesStatusOk();
    }

    @Operation(summary = "Создание резюме")
    @PostMapping("/create")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createResume(@RequestBody @Valid CreateRequest createRequest, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        return resumeService.createResume(createRequest);
    }

    @Operation(summary = "Обновление резюме")
    @PutMapping("/update/{resumeId}")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateResume(@RequestBody @Valid UpdateRequest updateRequest,
                                               BindingResult bindingResult, @PathVariable("resumeId") Long resumeId)
    {
        if(bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        return resumeService.updateResume(updateRequest, resumeId);
    }

    @Operation(summary = "Удаление резюме")
    @DeleteMapping("/delete/{resumeId}")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteResume(@PathVariable("resumeId") Long resumeId)
    {
        return resumeService.deleteResume(resumeId);
    }

    @Operation(summary = "Публикация резюме")
    @PutMapping("/setStatusOk/{resumeId}")
//    @PreAuthorize("hasRole('MODER')")
    public ResponseEntity<String> setStatusOk(@PathVariable("resumeId") Long resumeId)
    {
        return resumeService.setStatusOk(resumeId);
    }

    @Operation(summary = "Блокировка резюме")
    @PutMapping("/setStatusBlock/{resumeId}")
//    @PreAuthorize("hasRole('MODER')")
    public ResponseEntity<String> setStatusBlock(@PathVariable("resumeId") Long resumeId)
    {
        return resumeService.setStatusBlock(resumeId);
    }

    @Operation(summary = "Поиск резюме по заданным параметрам")
    @GetMapping("/searchResumes")
    //    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Resume>> searchResumes(@RequestParam(required = false) String fullName,
                                                      @RequestParam(required = false) String city,
                                                      @RequestParam(required = false) String education)
    {
        return resumeService.getSearchResumes(fullName,city,education);
    }
}
