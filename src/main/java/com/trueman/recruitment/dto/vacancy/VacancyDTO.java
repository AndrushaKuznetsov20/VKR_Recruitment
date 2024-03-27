package com.trueman.recruitment.dto.vacancy;

import com.trueman.recruitment.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VacancyDTO {
    private Long id;
    private String name_vacancy;
    private String description_vacancy;
    private String conditions_and_requirements;
    private String status_vacancy;
    private User user;
}
