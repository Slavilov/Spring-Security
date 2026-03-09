package com.example.footballpairs.repository;

import com.example.footballpairs.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
