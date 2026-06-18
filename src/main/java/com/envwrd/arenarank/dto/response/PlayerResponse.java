package com.envwrd.arenarank.dto.response;

import com.envwrd.arenarank.domain.PlayerRole;

import java.time.LocalDateTime;

public record PlayerResponse(
        Long id,
        String nickname,
        String email,
        PlayerRole role,
        LocalDateTime createdAt
) {
}