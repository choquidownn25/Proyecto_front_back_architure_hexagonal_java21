package org.exemple.mapper;

import org.exemple.data.CalendarEventDTO;
import org.exemple.data.request.CalendarEventRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CalendarRequestMapper {
    CalendarRequestMapper INSTANCE =  Mappers.getMapper(CalendarRequestMapper.class);
    CalendarEventRequest toDTO(CalendarEventDTO calendarRequestDTO);
    CalendarEventDTO toRequest(CalendarEventRequest calendarEventRequest);


}
