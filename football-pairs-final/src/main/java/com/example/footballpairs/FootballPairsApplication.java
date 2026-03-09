package com.example.footballpairs;

import com.example.footballpairs.service.CsvImportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FootballPairsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootballPairsApplication.class, args);
    }

    @Bean
    CommandLineRunner seedDataOnStartup(
            CsvImportService csvImportService,
            @Value("${app.csv.import-on-startup:true}") boolean importOnStartup) {
        return args -> {
            if (importOnStartup) {
                csvImportService.importAllIfDatabaseEmpty();
            }
        };
    }
}
