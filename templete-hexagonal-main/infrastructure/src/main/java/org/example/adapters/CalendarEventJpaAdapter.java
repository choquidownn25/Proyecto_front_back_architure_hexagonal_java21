package org.example.adapters;

import lombok.RequiredArgsConstructor;
import org.example.entity.CalendarEvent;
import org.example.mappers.CalendarMapper;
import org.example.repository.CalendarEventRepository;
import org.exemple.data.CalendarEventDTO;
import org.exemple.ports.spi.CalendarEventPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarEventJpaAdapter implements CalendarEventPersistencePort {
    @Autowired
    private  CalendarEventRepository calendarEventRepository;
    @Autowired
    private CalendarMapper calendarMapper;
    @Override
    public CalendarEventDTO getCalendarEvent(Long id) {
        return calendarEventRepository.findById(Math.toIntExact(id))
                .map(calendarMapper::toEntity)
                .orElseThrow(null);

    }

    @Override
    public List<CalendarEventDTO> getCalendarEvents() {

        return calendarMapper.toDTOList( calendarEventRepository.findAll());
    }

    @Override
    public CalendarEventDTO saveCalendarEvent(CalendarEventDTO calendarEventDTO) {
        CalendarEvent calendarEvent = calendarMapper.toDTO(calendarEventDTO);
        CalendarEvent savedEvent = calendarEventRepository.saveAndFlush(calendarEvent);
        return calendarMapper.toEntity(savedEvent);

    }

    @Override
    public void deleteCalendarEvent(Long id) {
        calendarEventRepository.deleteById(Math.toIntExact(id));
    }
}
