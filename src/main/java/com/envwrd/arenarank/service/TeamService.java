package com.envwrd.arenarank.service;

import com.envwrd.arenarank.domain.Player;
import com.envwrd.arenarank.domain.Team;
import com.envwrd.arenarank.dto.request.TeamRequest;
import com.envwrd.arenarank.dto.response.PlayerResponse;
import com.envwrd.arenarank.dto.response.TeamResponse;
import com.envwrd.arenarank.exception.ResourceNotFoundException;
import com.envwrd.arenarank.repository.PlayerRepository;
import com.envwrd.arenarank.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public TeamResponse create(TeamRequest request) {
        String normalizedTag = request.getTag().toUpperCase();

        if (teamRepository.existsByTag(normalizedTag)) {
            throw new IllegalArgumentException("Já existe um time cadastrado com esta tag");
        }

        Team team = Team.builder()
                .name(request.getName())
                .tag(normalizedTag)
                .build();

        Team savedTeam = teamRepository.save(team);

        return toResponse(savedTeam);
    }

    @Transactional(readOnly = true)
    public List<TeamResponse> findAll() {
        return teamRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Team::getId))
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TeamResponse findById(Long id) {
        Team team = findTeamById(id);
        return toResponse(team);
    }

    @Transactional
    public TeamResponse update(Long id, TeamRequest request) {
        Team team = findTeamById(id);
        String normalizedTag = request.getTag().toUpperCase();

        teamRepository.findByTag(normalizedTag)
                .filter(existingTeam -> !existingTeam.getId().equals(id))
                .ifPresent(existingTeam -> {
                    throw new IllegalArgumentException("Já existe outro time com esta tag");
                });

        team.setName(request.getName());
        team.setTag(normalizedTag);

        Team updatedTeam = teamRepository.save(team);

        return toResponse(updatedTeam);
    }

    @Transactional
    public void delete(Long id) {
        Team team = findTeamById(id);
        teamRepository.delete(team);
    }

    @Transactional
    public TeamResponse addPlayer(Long teamId, Long playerId) {
        Team team = findTeamById(teamId);
        Player player = findPlayerById(playerId);

        boolean playerAlreadyInTeam = team.getPlayers()
                .stream()
                .anyMatch(existingPlayer -> existingPlayer.getId().equals(playerId));

        if (playerAlreadyInTeam) {
            throw new IllegalArgumentException("Este jogador já está neste time");
        }

        team.addPlayer(player);

        Team updatedTeam = teamRepository.save(team);

        return toResponse(updatedTeam);
    }

    @Transactional
    public TeamResponse removePlayer(Long teamId, Long playerId) {
        Team team = findTeamById(teamId);
        Player player = findPlayerById(playerId);

        team.removePlayer(player);

        Team updatedTeam = teamRepository.save(team);

        return toResponse(updatedTeam);
    }

    @Transactional(readOnly = true)
    public TeamRequest getFormById(Long id) {
        Team team = findTeamById(id);

        TeamRequest request = new TeamRequest();
        request.setName(team.getName());
        request.setTag(team.getTag());

        return request;
    }


    @Transactional(readOnly = true)
    public long count() {
        return teamRepository.count();
    }

    private Team findTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Time não encontrado"));
    }

    private Player findPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrado"));
    }

    private TeamResponse toResponse(Team team) {
        List<PlayerResponse> players = team.getPlayers()
                .stream()
                .sorted(Comparator.comparing(Player::getId))
                .map(this::toPlayerResponse)
                .toList();

        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getTag(),
                players.size(),
                players,
                team.getCreatedAt()
        );
    }

    private PlayerResponse toPlayerResponse(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getNickname(),
                player.getEmail(),
                player.getRole(),
                player.getCreatedAt()
        );
    }
}