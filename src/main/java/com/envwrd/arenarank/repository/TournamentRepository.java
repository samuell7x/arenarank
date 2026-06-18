package com.envwrd.arenarank.repository;

import com.envwrd.arenarank.domain.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    boolean existsByNameIgnoreCase(String name);
}