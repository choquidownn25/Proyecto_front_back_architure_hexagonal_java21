package org.exemple.ports.api;

import org.exemple.data.CalendarEventDTO;
import org.exemple.data.request.CalendarEventRequest;
import org.exemple.data.response.CalendarEventResponse;

import java.util.List;

public interface CalendarEventServicePort {

    CalendarEventDTO getCalendarEvent(Long id);
    List<CalendarEventDTO> getCalendarEvents();
    CalendarEventResponse saveCalendarEvent(CalendarEventRequest calendarEventDTO);
    void deleteCalendarEvent(Long id);

}
