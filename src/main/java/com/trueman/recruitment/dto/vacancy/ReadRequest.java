package com.trueman.recruitment.dto.vacancy;

import com.trueman.recruitment.models.User;
import lombok.Data;

import java.util.List;

@Data
public class ReadRequest {
    private Long id;
    private String name_vacancy;
    private String description_vacancy;
    private String conditions_and_requirements;
    private Integer wage;
    private String schedule;
    private String status_vacancy;
    private User user;
}
