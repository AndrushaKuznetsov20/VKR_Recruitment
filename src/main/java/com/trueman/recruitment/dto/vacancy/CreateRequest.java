package com.trueman.recruitment.dto.vacancy;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание вакансии")
public class CreateRequest {

    @Schema(description = "Наименование вакансии", example = "Программист")
    @Size(min = 5, max = 30, message = "Наименование вакансии должно содержать от 5 до 30 символов")
    @NotBlank(message = "Наименование вакансии не может быть пустым")
    private String name_vacancy;

    @Schema(description = "Описание вакансии")
    @Size(min = 30, max = 300, message = "Описание вакансии должно содержать от 30 до 300 символов")
    @NotBlank(message = "Описание вакансии не может быть пустым")
    private String description_vacancy;

    @Schema(description = "Условия и требования к вакансии")
    @Size(min = 30, max = 300, message = "Условия и требования к вакансии должно содержать от 30 до 300 символов")
    @NotBlank(message = "Условия и требования к вакансии не могут быть пустыми")
    private String conditions_and_requirements;

}
