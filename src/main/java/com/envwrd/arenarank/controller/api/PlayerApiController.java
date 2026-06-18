package com.envwrd.arenarank.controller.api;

import com.envwrd.arenarank.dto.request.PlayerRequest;
import com.envwrd.arenarank.dto.response.PlayerResponse;
import com.envwrd.arenarank.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerApiController {

    private final PlayerService playerService;

    public PlayerApiController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerResponse create(@RequestBody @Valid PlayerRequest request) {
        return playerService.create(request);
    }

    @GetMapping
    public List<PlayerResponse> findAll() {
        return playerService.findAll();
    }

    @GetMapping("/{id}")
    public PlayerResponse findById(@PathVariable Long id) {
        return playerService.findById(id);
    }

    @PutMapping("/{id}")
    public PlayerResponse update(
            @PathVariable Long id,
            @RequestBody @Valid PlayerRequest request
    ) {
        return playerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        playerService.delete(id);
    }
}