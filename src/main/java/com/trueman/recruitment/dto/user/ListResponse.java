package com.trueman.recruitment.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Ответ в виде списка пользователей")
public class ListResponse {
    private List<ReadRequest> users;
}
