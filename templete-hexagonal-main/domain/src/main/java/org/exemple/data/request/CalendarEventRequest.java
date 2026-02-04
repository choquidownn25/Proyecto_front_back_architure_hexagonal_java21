package org.exemple.data.request;

import java.time.LocalDateTime;

public record CalendarEventRequest(
         String title,
         LocalDateTime start,
         LocalDateTime end,
         boolean allDay) {
}
