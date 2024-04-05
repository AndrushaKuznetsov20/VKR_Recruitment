package com.trueman.recruitment.dto.resume;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Ответ в виде списка резюме")
public class ListResponse {
    private List<ReadRequest> resumes;
}
