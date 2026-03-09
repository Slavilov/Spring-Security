package com.example.footballpairs.config;

import com.example.footballpairs.model.Team;
import com.example.footballpairs.repository.TeamRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTeamConverter implements Converter<String, Team> {

    private final TeamRepository teamRepository;

    public StringToTeamConverter(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        Long id = Long.parseLong(source);
        return teamRepository.findById(id).orElse(null);
    }
}
