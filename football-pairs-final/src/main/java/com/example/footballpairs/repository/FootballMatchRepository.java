package com.example.footballpairs.repository;

import com.example.footballpairs.model.FootballMatch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {

    @Override
    @EntityGraph(attributePaths = {"teamA", "teamB"})
    List<FootballMatch> findAll();
}
