package com.trueman.recruitment.models;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "metricsReportingHistory")
public class MetricsReportingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Description("Дата начала")
    @Column(name = "startDate")
    private LocalDate startDate;

    @Description("Дата окончания")
    @Column(name = "endDate")
    private LocalDate endDate;

    @Description("Количество вакансий")
    @Column(name = "countVacancies")
    private int countVacancies;

    @Description("Количество откликов")
    @Column(name = "countResponses")
    private int countResponses;

    @Description("Количество самоотказов")
    @Column(name = "countSelfDanial")
    private int countSelfDanial;

    @Description("Количество релевантных откликов")
    @Column(name = "countRelevantResponse")
    private int countRelevantResponse;

    @Description("Количество отказов работодателя")
    @Column(name = "countRefusalEmployer")
    private int countRefusalEmployer;

    @Description("Количество приглашений")
    @Column(name = "countInvitation")
    private int countInvitation;

    @Description("Идентификатор пользователя")
    @Column(name = "userId")
    private Long userId;
}
