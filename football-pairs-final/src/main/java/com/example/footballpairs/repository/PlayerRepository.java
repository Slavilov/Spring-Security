package com.example.footballpairs.repository;

import com.example.footballpairs.model.Player;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Player> findAll();
}
