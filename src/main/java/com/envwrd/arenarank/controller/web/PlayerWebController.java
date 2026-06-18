package com.envwrd.arenarank.controller.web;

import com.envwrd.arenarank.domain.PlayerRole;
import com.envwrd.arenarank.dto.request.PlayerRequest;
import com.envwrd.arenarank.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/players")
public class PlayerWebController {

    private final PlayerService playerService;

    public PlayerWebController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("players", playerService.findAll());
        return "players";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("player", new PlayerRequest());
        model.addAttribute("roles", PlayerRole.values());
        model.addAttribute("formTitle", "Novo jogador");
        model.addAttribute("formAction", "/players");

        return "player-form";
    }

    @PostMapping
    public String create(
            @ModelAttribute("player") @Valid PlayerRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", PlayerRole.values());
            model.addAttribute("formTitle", "Novo jogador");
            model.addAttribute("formAction", "/players");
            return "player-form";
        }

        playerService.create(request);

        return "redirect:/players";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("player", playerService.getFormById(id));
        model.addAttribute("roles", PlayerRole.values());
        model.addAttribute("formTitle", "Editar jogador");
        model.addAttribute("formAction", "/players/" + id);

        return "player-form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("player") @Valid PlayerRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", PlayerRole.values());
            model.addAttribute("formTitle", "Editar jogador");
            model.addAttribute("formAction", "/players/" + id);
            return "player-form";
        }

        playerService.update(id, request);

        return "redirect:/players";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        playerService.delete(id);
        return "redirect:/players";
    }
}