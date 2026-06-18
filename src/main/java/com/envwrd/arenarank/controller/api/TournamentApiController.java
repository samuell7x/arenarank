package com.envwrd.arenarank.controller.api;

import com.envwrd.arenarank.dto.request.TournamentRequest;
import com.envwrd.arenarank.dto.response.TournamentResponse;
import com.envwrd.arenarank.service.TournamentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentApiController {

    private final TournamentService tournamentService;

    public TournamentApiController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TournamentResponse create(@RequestBody @Valid TournamentRequest request) {
        return tournamentService.create(request);
    }

    @GetMapping
    public List<TournamentResponse> findAll() {
        return tournamentService.findAll();
    }

    @GetMapping("/{id}")
    public TournamentResponse findById(@PathVariable Long id) {
        return tournamentService.findById(id);
    }

    @PutMapping("/{id}")
    public TournamentResponse update(
            @PathVariable Long id,
            @RequestBody @Valid TournamentRequest request
    ) {
        return tournamentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tournamentService.delete(id);
    }

    @PatchMapping("/{id}/start")
    public TournamentResponse start(@PathVariable Long id) {
        return tournamentService.start(id);
    }

    @PatchMapping("/{id}/finish")
    public TournamentResponse finish(@PathVariable Long id) {
        return tournamentService.finish(id);
    }
}