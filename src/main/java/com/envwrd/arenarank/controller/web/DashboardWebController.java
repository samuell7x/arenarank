package com.envwrd.arenarank.controller.web;

import com.envwrd.arenarank.service.PlayerService;
import com.envwrd.arenarank.service.TeamService;
import com.envwrd.arenarank.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardWebController {

    private final PlayerService playerService;
    private final TeamService teamService;
    private final TournamentService tournamentService;

    public DashboardWebController(
            PlayerService playerService,
            TeamService teamService,
            TournamentService tournamentService
    ) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.tournamentService = tournamentService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalPlayers", playerService.count());
        model.addAttribute("totalTeams", teamService.count());
        model.addAttribute("totalTournaments", tournamentService.count());

        return "dashboard";
    }
}