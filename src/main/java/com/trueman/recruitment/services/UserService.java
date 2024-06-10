package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.user.ListResponse;
import com.trueman.recruitment.dto.user.ReadRequest;
import com.trueman.recruitment.dto.user.UpdateRequest;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.enums.Role;
import com.trueman.recruitment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;

    public ResponseEntity<ListResponse> getAllUsers(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> users = userRepository.findAll(pageable);

        List<ReadRequest> userDTOList = new ArrayList<>();

        for (User user : users.getContent()) {
            ReadRequest userDTO = new ReadRequest();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setEmail(user.getEmail());
            userDTO.setNumber(user.getNumber());
            userDTO.setActive(user.isActive());
            userDTO.setRole(user.getRole());

            userDTOList.add(userDTO);
        }

        ListResponse userListDTO = new ListResponse();
        userListDTO.setUsers(userDTOList);

        return new ResponseEntity<>(userListDTO, HttpStatus.OK);

    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует!");
        }
        user.setActive(true);
        return save(user);
    }

    public ResponseEntity<String> update(UpdateRequest updateRequest)
    {
        User user = getCurrentUser();

        user.setUsername(updateRequest.getUsername());
        user.setEmail(updateRequest.getEmail());
        user.setNumber(updateRequest.getNumber());

        userRepository.save(user);

        return ResponseEntity.ok("Данные пользователя успешно обновлены");
    }
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден "));

    }

    public ResponseEntity<String> userBlock(Long userId)
    {
        User user = userRepository.findById(userId).orElse(null);
        user.setActive(false);
        userRepository.save(user);
        return ResponseEntity.ok("Пользователь успешно заблокирован!");
    }

    public ResponseEntity<String> userInBlock(Long userId)
    {
        User user = userRepository.findById(userId).orElse(null);
        user.setActive(true);
        userRepository.save(user);
        return ResponseEntity.ok("Пользователь успешно разблокирован!");
    }

    public ResponseEntity<String> userChangeRole(Long userId, String userRole)
    {
        User user = userRepository.findById(userId).orElse(null);

        if(userRole.equals("USER"))
        {
            user.setRole(Role.ROLE_USER);
            userRepository.save(user);
            return ResponseEntity.ok("Роль 'Пользователь' успешно назначена!");
        }
        else if(userRole.equals("EMPLOYER"))
        {
            user.setRole(Role.ROLE_EMPLOYER);
            userRepository.save(user);
            return ResponseEntity.ok("Роль 'Работодатель' успешно назначена!");
        }
        else if (userRole.equals("MODER")) {
            user.setRole(Role.ROLE_MODER);
            userRepository.save(user);
            return ResponseEntity.ok("Роль 'Модератор' успешно назначена!");
        }
        else if (userRole.equals("ADMIN")) {
            user.setRole(Role.ROLE_ADMIN);
            userRepository.save(user);
            return ResponseEntity.ok("Роль 'Администратор' успешно назначена!");
        }
        else
        {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    public ResponseEntity<User> findByUserId(Long userId)
    {
        User user = userRepository.findById(userId).orElse(null);
        return new ResponseEntity<>(user,HttpStatus.OK);
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
