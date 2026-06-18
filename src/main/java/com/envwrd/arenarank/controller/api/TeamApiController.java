package com.envwrd.arenarank.controller.api;

import com.envwrd.arenarank.dto.request.TeamRequest;
import com.envwrd.arenarank.dto.response.TeamResponse;
import com.envwrd.arenarank.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamApiController {

    private final TeamService teamService;

    public TeamApiController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponse create(@RequestBody @Valid TeamRequest request) {
        return teamService.create(request);
    }

    @GetMapping
    public List<TeamResponse> findAll() {
        return teamService.findAll();
    }

    @GetMapping("/{id}")
    public TeamResponse findById(@PathVariable Long id) {
        return teamService.findById(id);
    }

    @PutMapping("/{id}")
    public TeamResponse update(
            @PathVariable Long id,
            @RequestBody @Valid TeamRequest request
    ) {
        return teamService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        teamService.delete(id);
    }

    @PostMapping("/{teamId}/players/{playerId}")
    public TeamResponse addPlayer(
            @PathVariable Long teamId,
            @PathVariable Long playerId
    ) {
        return teamService.addPlayer(teamId, playerId);
    }

    @DeleteMapping("/{teamId}/players/{playerId}")
    public TeamResponse removePlayer(
            @PathVariable Long teamId,
            @PathVariable Long playerId
    ) {
        return teamService.removePlayer(teamId, playerId);
    }
}