package com.trueman.recruitment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_vacancy", unique = true, nullable = false)
    private String name_vacancy;

    @Column(name = "description_vacancy", nullable = false)
    private String description_vacancy;

    @Column(name = "conditions_and_requirements", nullable = false)
    private String conditions_and_requirements;

    @Column(name = "status_vacancy", nullable = false)
    private String status_vacancy;

    @Description("Пользователь, создавший данную вакансию")
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Description("Список пользователей, которые оставили отклики на данную вакансию")
    @JsonIgnore
    @ManyToMany
    private List<User> userList;

}
