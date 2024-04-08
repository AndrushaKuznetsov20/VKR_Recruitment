package com.trueman.recruitment.config;

import com.trueman.recruitment.filter.JwtAuthenticationFilter;
import com.trueman.recruitment.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers("/vacancy/list").permitAll()
                        .requestMatchers("/vacancy/create").hasRole("EMPLOYER")
                        .requestMatchers("/vacancy/update").hasRole("EMPLOYER")
                        .requestMatchers("/vacancy/delete").hasRole("EMPLOYER")
                        .requestMatchers("/vacancy/listMyVacancies").permitAll()
                        .requestMatchers("/vacancy/listVacanciesStatusOk").hasRole("EMPLOYER, USER")
                        .requestMatchers("/vacancy/setStatusOk").hasRole("MODER")
                        .requestMatchers("/vacancy/setStatusBlock").hasRole("MODER")
                        .requestMatchers("/vacancy/searchVacancies").hasRole("USER")

                        .requestMatchers("/user/list").hasRole("ADMIN")
                        .requestMatchers("/user/block").hasRole("ADMIN")
                        .requestMatchers("/user/inBlock").hasRole("ADMIN")
                        .requestMatchers("/user/changeRole").hasRole("ADMIN")
                        .requestMatchers("/user/update").permitAll()

                        .requestMatchers("/response/create").hasRole("USER")
                        .requestMatchers("/response/listUsers").hasRole("EMPLOYER")
                        .requestMatchers("/response/listVacancy").hasRole("USER")
                        .requestMatchers("/response/delete").hasRole("USER")
                        .requestMatchers("/response/setStatusSelfDenial").hasRole("EMPLOYER")
                        .requestMatchers("/response/setStatusRefusalEmployer").hasRole("EMPLOYER")
                        .requestMatchers("/response/setStatusRelevant").hasRole("EMPLOYER")
                        .requestMatchers("/response/setStatusInvitation").hasRole("EMPLOYER")

                        .requestMatchers("/resume/list").permitAll()
                        .requestMatchers("/resume/myResume").permitAll()
                        .requestMatchers("/resume/listResumeStatusOk").permitAll()
                        .requestMatchers("/resume/create").permitAll()
                        .requestMatchers("/resume/update").permitAll()
                        .requestMatchers("/resume/delete").permitAll()
                        .requestMatchers("/resume/setStatusOk").permitAll()
                        .requestMatchers("/resume/setStatusBlock").permitAll()

                        .requestMatchers("/metrics/**").hasRole("EMPLOYER")

                        .requestMatchers("/message/**").permitAll()

                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
