package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.user.ListResponse;
import com.trueman.recruitment.dto.user.ReadRequest;
import com.trueman.recruitment.dto.user.UpdateRequest;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import com.trueman.recruitment.models.enums.Role;
import com.trueman.recruitment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ListResponse getAllUsers() {
        List<User> users = repository.findAll();
        List<ReadRequest> userDTOList = new ArrayList<>();

        for (User user : users) {
            ReadRequest userDTO = new ReadRequest();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setEmail(user.getEmail());
            userDTO.setActive(user.isActive());
            userDTO.setRole(user.getRole());

            userDTOList.add(userDTO);
        }

        ListResponse userListDTO = new ListResponse();
        userListDTO.setUsers(userDTOList);

        return userListDTO;

    }

    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует !");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует !");
        }
        user.setActive(true);
        return save(user);
    }

    public User update(Long userId, UpdateRequest updateRequest)
    {
        User user = repository.findById(userId).orElse(null);

        user.setUsername(updateRequest.getUsername());
        user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        user.setEmail(updateRequest.getEmail());

        return repository.save(user);
    }
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден !"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
    @Deprecated
    public void getUser() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_USER);
        save(user);
    }
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
    @Deprecated
    public void getModer() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_MODER);
        save(user);
    }
    @Deprecated
    public void getEmployer() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_EMPLOYER);
        save(user);
    }
}
