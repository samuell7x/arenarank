package com.envwrd.arenarank.controller.web;

import com.envwrd.arenarank.dto.request.TeamRequest;
import com.envwrd.arenarank.service.PlayerService;
import com.envwrd.arenarank.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teams")
public class TeamWebController {

    private final TeamService teamService;
    private final PlayerService playerService;

    public TeamWebController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("teams", teamService.findAll());
        return "teams";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("team", new TeamRequest());
        model.addAttribute("formTitle", "Novo time");
        model.addAttribute("formAction", "/teams");

        return "team-form";
    }

    @PostMapping
    public String create(
            @ModelAttribute("team") @Valid TeamRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Novo time");
            model.addAttribute("formAction", "/teams");
            return "team-form";
        }

        teamService.create(request);

        return "redirect:/teams";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("team", teamService.findById(id));
        model.addAttribute("players", playerService.findAll());

        return "team-details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("team", teamService.getFormById(id));
        model.addAttribute("formTitle", "Editar time");
        model.addAttribute("formAction", "/teams/" + id);

        return "team-form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("team") @Valid TeamRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Editar time");
            model.addAttribute("formAction", "/teams/" + id);
            return "team-form";
        }

        teamService.update(id, request);

        return "redirect:/teams";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        teamService.delete(id);
        return "redirect:/teams";
    }

    @PostMapping("/{teamId}/players")
    public String addPlayer(
            @PathVariable Long teamId,
            @RequestParam Long playerId
    ) {
        teamService.addPlayer(teamId, playerId);
        return "redirect:/teams/" + teamId;
    }

    @PostMapping("/{teamId}/players/{playerId}/remove")
    public String removePlayer(
            @PathVariable Long teamId,
            @PathVariable Long playerId
    ) {
        teamService.removePlayer(teamId, playerId);
        return "redirect:/teams/" + teamId;
    }
}