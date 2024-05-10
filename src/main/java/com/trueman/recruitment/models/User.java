package com.trueman.recruitment.models;

import com.trueman.recruitment.models.enums.Role;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Description("Имя пользователя")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Description("Электронная почта")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Description("Номер телефона")
    @Column(name = "number", unique = true, nullable = false)
    private String number;

    @Description("Пароль")
    @Column(name = "password", nullable = false)
    private String password;

    @Description("Активность пользователя")
    @Column(name = "active")
    private boolean active;

    @Description("Роль пользователя")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return active;
    }
}
