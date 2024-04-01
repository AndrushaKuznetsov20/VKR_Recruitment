package com.trueman.recruitment.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на отправку сообщения")
public class SendMessage
{
    @Schema(description = "Текст сообщения", example = "Привет!")
    @NotBlank(message = "Сообщение не может быть пустым")
    private String content;
}
