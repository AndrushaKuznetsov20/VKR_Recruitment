package com.trueman.recruitment.models;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

import java.time.LocalDate;

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

    @Description("Наименование вакансии")
    @Column(name = "name_vacancy", nullable = false)
    private String name_vacancy;

    @Description("Описание вакансии")
    @Column(name = "description_vacancy", nullable = false)
    private String description_vacancy;

    @Description("Условия и требования")
    @Column(name = "conditions_and_requirements", nullable = false)
    private String conditions_and_requirements;

    @Description("Заработная плата")
    @Column(name = "wage")
    private Integer wage;

    @Description("График")
    @Column(name = "schedule")
    private String schedule;

    @Description("Статус вакансии")
    @Column(name = "status_vacancy", nullable = false)
    private String status_vacancy;

    @Description("Дата создания вакансии")
    @Column(name = "createDateTime")
    private LocalDate createDateTime;

    @Description("Пользователь, создавший данную вакансию")
    @ManyToOne
    @JoinColumn
    private User user;

    public void setCreateDateTime()
    {
        this.createDateTime = LocalDate.now();
    }
}
