package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.auth.JwtAuthenticationResponse;
import com.trueman.recruitment.dto.auth.SignInRequest;
import com.trueman.recruitment.dto.auth.SignUpRequest;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoderService passwordEncoderService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request, String selectedRole) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoderService.passwordEncoder().encode(request.getPassword()))
                .number(request.getNumber())
//                .role(Role.ROLE_USER)
                .build();

        if(selectedRole.equals("USER"))
        {
            user.setRole(Role.ROLE_USER);
        }
        else{
            user.setRole(Role.ROLE_EMPLOYER);
        }

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
