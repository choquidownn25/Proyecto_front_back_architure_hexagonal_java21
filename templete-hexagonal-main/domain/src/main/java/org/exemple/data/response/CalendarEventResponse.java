package org.exemple.data.response;

import lombok.*;
import org.exemple.data.CalendarEventDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CalendarEventResponse {
    List<CalendarEventDTO> calendarEventDTOS;
    private Message message;
    private Integer code;
}
