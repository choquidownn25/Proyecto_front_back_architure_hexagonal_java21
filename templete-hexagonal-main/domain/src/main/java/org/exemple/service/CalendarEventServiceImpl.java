package org.exemple.service;

import lombok.RequiredArgsConstructor;
import org.exemple.data.CalendarEventDTO;
import org.exemple.data.request.CalendarEventRequest;
import org.exemple.data.response.CalendarEventResponse;
import org.exemple.data.response.Message;
import org.exemple.mapper.CalendarRequestMapper;
import org.exemple.ports.api.CalendarEventServicePort;
import org.exemple.ports.spi.CalendarEventPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CalendarEventServiceImpl implements CalendarEventServicePort {

    private final CalendarEventPersistencePort calendarEventPersistencePort;

    private final ModelMapper modelMapper;
    private  CalendarRequestMapper calendarRequestMapper;

    @Override
    public CalendarEventDTO getCalendarEvent(Long id) {
        return null;
    }

    @Override
    public List<CalendarEventDTO> getCalendarEvents() {
        return List.of();
    }

    @Override
    public CalendarEventResponse saveCalendarEvent(CalendarEventRequest calendarEventRequest) {
        CalendarEventResponse calendarEventResponse = new CalendarEventResponse();
        //CalendarEventDTO calendarEventDTO = calendarRequestMapper.toRequest(calendarEventRequest);
        CalendarEventDTO calendarEventDTO =  modelMapper.map(calendarEventRequest, CalendarEventDTO.class);
        List<CalendarEventDTO> calendarEventDTOList = new ArrayList<>();
        Message message = new Message();
        CalendarEventDTO savedEvent = calendarEventPersistencePort.saveCalendarEvent(calendarEventDTO);
        if(savedEvent != null){
            message.setCode(200);
            message.setEcho("Calendar event saved successfully");
            calendarEventResponse.setMessage(message);
            calendarEventDTOList.add(savedEvent);
            calendarEventResponse.setCalendarEventDTOS(calendarEventDTOList);
            return calendarEventResponse;
        }else {
            message.setCode(500);
            message.setEcho("Error saving calendar event");
            calendarEventResponse.setCalendarEventDTOS(null);
            calendarEventResponse.setMessage(message);
        }
        return calendarEventResponse;
    }

    @Override
    public void deleteCalendarEvent(Long id) {

    }
}
