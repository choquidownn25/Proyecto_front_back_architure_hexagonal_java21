package org.exemple.ports.spi;

import org.exemple.data.CalendarEventDTO;

import java.util.List;

public interface CalendarEventPersistencePort {

    CalendarEventDTO getCalendarEvent(Long id);
    List<CalendarEventDTO> getCalendarEvents();
    CalendarEventDTO saveCalendarEvent(CalendarEventDTO calendarEventDTO);
    void deleteCalendarEvent(Long id);

}
