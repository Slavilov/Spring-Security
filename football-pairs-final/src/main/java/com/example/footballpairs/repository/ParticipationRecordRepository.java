package com.example.footballpairs.repository;

import com.example.footballpairs.model.ParticipationRecord;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRecordRepository extends JpaRepository<ParticipationRecord, Long> {

    @EntityGraph(attributePaths = {"player", "match", "match.teamA", "match.teamB"})
    List<ParticipationRecord> findByMatchId(Long matchId);

    @Override
    @EntityGraph(attributePaths = {"player", "player.team", "match", "match.teamA", "match.teamB"})
    List<ParticipationRecord> findAll();
}
