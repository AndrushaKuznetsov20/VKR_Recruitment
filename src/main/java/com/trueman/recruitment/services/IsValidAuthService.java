package com.trueman.recruitment.services;

import com.trueman.recruitment.dto.auth.JwtAuthenticationResponse;
import com.trueman.recruitment.dto.auth.SignInRequest;
import com.trueman.recruitment.dto.auth.SignUpRequest;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsValidAuthService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public ResponseEntity<?> isValidLogin(SignInRequest signInRequest)
    {
        User existingUser = userRepository.findByNameIsValidAuth(signInRequest.getUsername());

        if(existingUser != null)
        {
            if(existingUser.isActive())
            {
                JwtAuthenticationResponse response = authenticationService.signIn(signInRequest);
                return ResponseEntity.ok(response);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.LOCKED);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    public ResponseEntity<?> isValidRegister(SignUpRequest signUpRequest, String selectedRole)
    {
        if(!userRepository.existsByUsername(signUpRequest.getUsername()))
        {
            if(!userRepository.existsByEmail(signUpRequest.getEmail()))
            {
                authenticationService.signUp(signUpRequest, selectedRole);
                return ResponseEntity.ok("Регистрация прошла успешно!");
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
