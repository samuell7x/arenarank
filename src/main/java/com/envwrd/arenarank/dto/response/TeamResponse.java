package com.envwrd.arenarank.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record TeamResponse(
        Long id,
        String name,
        String tag,
        int totalPlayers,
        List<PlayerResponse> players,
        LocalDateTime createdAt
) {
}