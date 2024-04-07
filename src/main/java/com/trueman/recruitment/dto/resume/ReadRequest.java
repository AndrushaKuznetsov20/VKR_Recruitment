package com.trueman.recruitment.dto.resume;

import com.trueman.recruitment.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReadRequest {
    private Long id;

    private String fullName;

    private LocalDate birthDate;

    private Integer age;

    private String city;

    private String skills;

    private String education;

    private String otherInfo;

    private String statusResume;

    private User user;
}
