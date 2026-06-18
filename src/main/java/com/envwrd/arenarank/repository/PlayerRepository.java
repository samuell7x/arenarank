package com.envwrd.arenarank.repository;

import com.envwrd.arenarank.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    boolean existsByEmail(String email);

    Optional<Player> findByEmail(String email);
}