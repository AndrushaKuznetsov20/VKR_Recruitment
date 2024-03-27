package com.trueman.recruitment.dto.vacancy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Список вакансий")
public class VacancyListDTO {
    private List<VacancyDTO> vacancies;
}
