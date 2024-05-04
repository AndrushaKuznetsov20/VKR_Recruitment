package com.trueman.recruitment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "responses")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Description("Вакансия на которую пользователь оставил отклик")
    @ManyToOne
    @JoinColumn
    private Vacancy vacancy;

    @Description("Пользователь, который оставил отклик на данную вакансию")
    @ManyToOne
    @JoinColumn
    private User user;

    @Description("Дата осуществления отклика")
    @Column(name = "currentDateTime")
    private LocalDate currentDate;

    @Description("Статус отклика")
    @Column(name = "statusResponse")
    private String statusResponse;

    public void setCurrentDateTime()
    {
        this.currentDate = LocalDate.now();
    }
}
