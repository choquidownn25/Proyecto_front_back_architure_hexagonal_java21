package org.example.configuration;

import org.exemple.ports.api.LoadprocesarCsv;
import org.exemple.utils.load.FileCsv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadCsvConfig {
    @Bean
    public LoadprocesarCsv loadprocesarCsv() {
        return new FileCsv();
    }
}
