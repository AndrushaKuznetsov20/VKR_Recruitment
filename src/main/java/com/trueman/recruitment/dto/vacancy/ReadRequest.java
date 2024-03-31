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
    private String status_vacancy;
    private User user;
    private List<User> userList;
}