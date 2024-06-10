package com.trueman.recruitment.models;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

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

    @Description("Текст сообщения")
    @Column(name = "content")
    private String content;

    @Description("Отправитель")
    @Column(name = "sender")
    private Long sender;

    @Description("Получатель")
    @Column(name = "receiver")
    private Long receiver;

    @Description("Дата отправки сообщения")
    @Column(name = "date")
    private LocalDateTime currentDateTime;

    public void setCurrentDate()
    {
        this.currentDateTime = LocalDateTime.now();
    }
}
