package org.example.configuration;

import org.example.adapters.CalendarEventJpaAdapter;
import org.example.repository.CalendarEventRepository;
import org.exemple.data.CalendarEventDTO;
import org.exemple.data.request.CalendarEventRequest;
import org.exemple.mapper.CalendarRequestMapper;
import org.exemple.ports.api.CalendarEventServicePort;
import org.exemple.ports.spi.CalendarEventPersistencePort;
import org.exemple.service.CalendarEventServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalendarEventConfig {

        @Bean
        public ModelMapper modelMapper() {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration()
                    .setFieldMatchingEnabled(true)
                    .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
            return mapper;
        }

    @Bean
    public CalendarEventPersistencePort calendarEventPersistencePort() {
        return new CalendarEventJpaAdapter();
    }
    @Bean
    public CalendarEventServicePort calendarEventServicePort() {
        return new CalendarEventServiceImpl(calendarEventPersistencePort(), modelMapper());
    }

}
