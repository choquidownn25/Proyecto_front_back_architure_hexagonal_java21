package org.exemple.data;


import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CalendarEventDTO {

    private Integer id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private String color;
    private boolean allDay;
}
