package com.envwrd.arenarank.service;

import com.envwrd.arenarank.domain.Tournament;
import com.envwrd.arenarank.dto.request.TournamentRequest;
import com.envwrd.arenarank.dto.response.TournamentResponse;
import com.envwrd.arenarank.exception.ResourceNotFoundException;
import com.envwrd.arenarank.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Transactional
    public TournamentResponse create(TournamentRequest request) {
        validateDates(request);

        if (tournamentRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Já existe um torneio cadastrado com este nome");
        }

        Tournament tournament = Tournament.builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        Tournament savedTournament = tournamentRepository.save(tournament);

        return toResponse(savedTournament);
    }

    @Transactional(readOnly = true)
    public List<TournamentResponse> findAll() {
        return tournamentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Tournament::getId))
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TournamentResponse findById(Long id) {
        Tournament tournament = findEntityById(id);
        return toResponse(tournament);
    }

    @Transactional
    public TournamentResponse update(Long id, TournamentRequest request) {
        validateDates(request);

        Tournament tournament = findEntityById(id);

        if (tournament.getStatus().name().equals("FINISHED")) {
            throw new IllegalArgumentException("Não é possível editar um torneio finalizado");
        }

        tournament.setName(request.getName());
        tournament.setStartDate(request.getStartDate());
        tournament.setEndDate(request.getEndDate());

        Tournament updatedTournament = tournamentRepository.save(tournament);

        return toResponse(updatedTournament);
    }

    @Transactional
    public void delete(Long id) {
        Tournament tournament = findEntityById(id);

        if (tournament.getStatus().name().equals("IN_PROGRESS")) {
            throw new IllegalArgumentException("Não é possível excluir um torneio em andamento");
        }

        tournamentRepository.delete(tournament);
    }

    @Transactional
    public TournamentResponse start(Long id) {
        Tournament tournament = findEntityById(id);
        tournament.start();

        return toResponse(tournamentRepository.save(tournament));
    }

    @Transactional
    public TournamentResponse finish(Long id) {
        Tournament tournament = findEntityById(id);
        tournament.finish();

        return toResponse(tournamentRepository.save(tournament));
    }

    @Transactional(readOnly = true)
    public TournamentRequest getFormById(Long id) {
        Tournament tournament = findEntityById(id);

        TournamentRequest request = new TournamentRequest();
        request.setName(tournament.getName());
        request.setStartDate(tournament.getStartDate());
        request.setEndDate(tournament.getEndDate());

        return request;
    }

    @Transactional(readOnly = true)
    public long count() {
        return tournamentRepository.count();
    }

    private void validateDates(TournamentRequest request) {
        if (request.getStartDate() != null && request.getEndDate() != null
                && request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("A data de término não pode ser antes da data de início");
        }
    }

    private Tournament findEntityById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Torneio não encontrado"));
    }

    private TournamentResponse toResponse(Tournament tournament) {
        return new TournamentResponse(
                tournament.getId(),
                tournament.getName(),
                tournament.getStatus(),
                tournament.getStartDate(),
                tournament.getEndDate(),
                tournament.getCreatedAt()
        );
    }
}