package com.trueman.recruitment.models;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "responsesToResume")
public class ResponseToResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Description("Пользователь, который оставил отклик на резюме")
    @ManyToOne
    @JoinColumn
    private User user;

    @Description("Резюме, на которое оставили отклик")
    @ManyToOne
    @JoinColumn
    private Resume resume;

    @Description("Дата осуществления отклика")
    @Column(name = "responseDate")
    private LocalDate responseDate;

    public void setResponseDateNow()
    {
        this.responseDate = LocalDate.now();
    }

}
