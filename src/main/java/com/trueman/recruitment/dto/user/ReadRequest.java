package com.trueman.recruitment.dto.user;

import com.trueman.recruitment.models.enums.Role;
import lombok.Data;

@Data
public class ReadRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean active;
    private Role role;
}
