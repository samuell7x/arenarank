package com.envwrd.arenarank.service;

import com.envwrd.arenarank.domain.Player;
import com.envwrd.arenarank.dto.request.PlayerRequest;
import com.envwrd.arenarank.dto.response.PlayerResponse;
import com.envwrd.arenarank.exception.ResourceNotFoundException;
import com.envwrd.arenarank.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public PlayerResponse create(PlayerRequest request) {
        if (playerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Já existe um jogador cadastrado com este email");
        }

        Player player = Player.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .role(request.getRole())
                .build();

        Player savedPlayer = playerRepository.save(player);

        return toResponse(savedPlayer);
    }

    @Transactional(readOnly = true)
    public List<PlayerResponse> findAll() {
        return playerRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Player::getId))
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlayerResponse findById(Long id) {
        Player player = findEntityById(id);
        return toResponse(player);
    }

    @Transactional(readOnly = true)
    public PlayerRequest getFormById(Long id) {
        Player player = findEntityById(id);

        PlayerRequest request = new PlayerRequest();
        request.setNickname(player.getNickname());
        request.setEmail(player.getEmail());
        request.setRole(player.getRole());

        return request;
    }

    @Transactional
    public PlayerResponse update(Long id, PlayerRequest request) {
        Player player = findEntityById(id);

        playerRepository.findByEmail(request.getEmail())
                .filter(existingPlayer -> !existingPlayer.getId().equals(id))
                .ifPresent(existingPlayer -> {
                    throw new IllegalArgumentException("Já existe outro jogador com este email");
                });

        player.setNickname(request.getNickname());
        player.setEmail(request.getEmail());
        player.setRole(request.getRole());

        Player updatedPlayer = playerRepository.save(player);

        return toResponse(updatedPlayer);
    }

    @Transactional
    public void delete(Long id) {
        Player player = findEntityById(id);
        playerRepository.delete(player);
    }

    @Transactional(readOnly = true)
    public long count() {
        return playerRepository.count();
    }

    private Player findEntityById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrado"));
    }

    private PlayerResponse toResponse(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getNickname(),
                player.getEmail(),
                player.getRole(),
                player.getCreatedAt()
        );
    }
}