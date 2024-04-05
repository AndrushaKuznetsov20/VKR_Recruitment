package com.trueman.recruitment.models;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Description("ФИО")
    @Column(name = "fullName")
    private String fullName;

    @Description("Дата рождения")
    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Description("Город")
    @Column(name = "city")
    private String city;

    @Description("Навыки")
    @Column(name = "skills")
    private String skills;

    @Description("Образование")
    @Column(name = "education")
    private String education;

    @Description("Другая информация")
    @Column(name = "otherInfo")
    private String otherInfo;

    @Description("Статус резюме - (модерировано или заблокировано)")
    @Column(name = "statusResume")
    private String statusResume;

    @Description("Пользователь")
    @OneToOne
    @JoinColumn
    private User user;
}
