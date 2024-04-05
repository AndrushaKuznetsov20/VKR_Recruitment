package com.trueman.recruitment.dto.resume;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.Description;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Schema(description = "Запрос на создание резюме")
public class CreateRequest {

    @Schema(description = "ФИО", example = "Кузнецов Андрей Алексеевич")
    @NotBlank(message = "ФИО не может быть пустым")
    private String fullName;

    @Schema(description = "Дата рождения", example = "2002-02-24")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
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
