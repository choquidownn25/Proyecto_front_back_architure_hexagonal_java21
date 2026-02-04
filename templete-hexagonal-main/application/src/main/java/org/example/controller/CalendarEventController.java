package org.example.controller;

import org.exemple.data.request.CalendarEventRequest;
import org.exemple.data.response.CalendarEventResponse;
import org.exemple.ports.api.CalendarEventServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/calendar-event")
public class CalendarEventController {
    private final CalendarEventServicePort calendarEventServicePort;

    public CalendarEventController(CalendarEventServicePort calendarEventServicePort) {
        this.calendarEventServicePort = calendarEventServicePort;
    }
    @PostMapping
    public ResponseEntity<CalendarEventResponse> create(@RequestBody CalendarEventRequest request) {
      CalendarEventResponse calendarEventResponse= calendarEventServicePort.saveCalendarEvent(request);
      if(calendarEventResponse != null){
          return  ResponseEntity.ok(calendarEventResponse);
      }else {
          return ResponseEntity.badRequest().build();
      }

    }
}
