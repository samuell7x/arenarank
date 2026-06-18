package com.envwrd.arenarank.dto.response;

import com.envwrd.arenarank.domain.TournamentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TournamentResponse(
        Long id,
        String name,
        TournamentStatus status,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime createdAt
) {
}