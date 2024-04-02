package com.trueman.recruitment.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "sender")
    private Long sender;

    @Column(name = "receiver")
    private Long receiver;

    @Column(name = "date")
    private LocalDateTime currentDateTime;

    public void setCurrentDate()
    {
        this.currentDateTime = LocalDateTime.now();
    }
}
