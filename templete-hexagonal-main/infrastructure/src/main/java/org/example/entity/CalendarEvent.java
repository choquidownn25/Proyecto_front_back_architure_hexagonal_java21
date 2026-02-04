package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "event")
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "start", nullable = true)
    private LocalDateTime start;
    @Column(name = "end", nullable = true)
    private LocalDateTime end;
    @Column(name = "allDay", nullable = false)
    private boolean allDay;
}
