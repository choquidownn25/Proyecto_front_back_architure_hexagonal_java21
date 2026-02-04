package org.example.mappers;

import org.example.entity.CalendarEvent;
import org.exemple.data.CalendarEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarMapper {

    CalendarEventDTO toEntity(CalendarEvent calendarEvent);
    CalendarEvent toDTO(CalendarEventDTO calendarEventDTO);
    List<CalendarEventDTO> toDTOList(List<CalendarEvent> calendarEvents);
    List<CalendarEvent> toEntityList(List<CalendarEventDTO> calendarEventDTOS);
}