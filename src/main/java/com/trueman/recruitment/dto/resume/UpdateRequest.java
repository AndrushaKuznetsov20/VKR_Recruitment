package com.trueman.recruitment.dto.resume;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Запрос на обновление резюме")
public class UpdateRequest {

    @Schema(description = "ФИО", example = "Кузнецов Андрей Алексеевич")
    @NotBlank(message = "ФИО не может быть пустым")
    private String fullName;

    @Schema(description = "Дата рождения", example = "2002-24-02")
    @NotBlank(message = "Дата рождения не может быть пустым")
    private LocalDate birthDate;

    @Schema(description = "Город", example = "Владимир")
    @NotBlank(message = "Наименование города не может быть пустым")
    private String city;

    @Schema(description = "Навыки", example = "Разработка клиент-серверных приложений")
    @Size(min = 10, max = 500, message = "Описание навыков должно содержать от 10 до 500 символов")
    @NotBlank(message = "Описание навыков не должно быть пустым")
    private String skills;

    @Schema(description = "Образование", example = "ВлГУ-Информационные системы и технологии")
    @NotBlank(message = "Описание образования не должно быть пустым")
    private String education;

    @Schema(description = "Другая информация", example = "Дополнительная информация")
    private String otherInfo;
}
