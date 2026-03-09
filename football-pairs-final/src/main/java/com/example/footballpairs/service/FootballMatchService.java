package com.example.footballpairs.service;

import com.example.footballpairs.model.FootballMatch;
import com.example.footballpairs.repository.FootballMatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootballMatchService {

    private final FootballMatchRepository footballMatchRepository;

    public FootballMatchService(FootballMatchRepository footballMatchRepository) {
        this.footballMatchRepository = footballMatchRepository;
    }

    public List<FootballMatch> getAll() {
        return footballMatchRepository.findAll();
    }

    public FootballMatch getById(Long id) {
        return footballMatchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Match not found: " + id));
    }

    public FootballMatch save(FootballMatch footballMatch) {
        return footballMatchRepository.save(footballMatch);
    }

    public void deleteById(Long id) {
        footballMatchRepository.deleteById(id);
    }

    public long count() {
        return footballMatchRepository.count();
    }
}
