package com.envwrd.arenarank.repository;

import com.envwrd.arenarank.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    boolean existsByTag(String tag);

    Optional<Team> findByTag(String tag);
}