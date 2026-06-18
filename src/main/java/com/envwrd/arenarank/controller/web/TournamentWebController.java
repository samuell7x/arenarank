package com.envwrd.arenarank.controller.web;

import com.envwrd.arenarank.dto.request.TournamentRequest;
import com.envwrd.arenarank.service.TournamentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tournaments")
public class TournamentWebController {

    private final TournamentService tournamentService;

    public TournamentWebController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tournaments", tournamentService.findAll());
        return "tournaments";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("tournament", new TournamentRequest());
        model.addAttribute("formTitle", "Novo torneio");
        model.addAttribute("formAction", "/tournaments");

        return "tournament-form";
    }

    @PostMapping
    public String create(
            @ModelAttribute("tournament") @Valid TournamentRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Novo torneio");
            model.addAttribute("formAction", "/tournaments");
            return "tournament-form";
        }

        tournamentService.create(request);

        return "redirect:/tournaments";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("tournament", tournamentService.getFormById(id));
        model.addAttribute("formTitle", "Editar torneio");
        model.addAttribute("formAction", "/tournaments/" + id);

        return "tournament-form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("tournament") @Valid TournamentRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Editar torneio");
            model.addAttribute("formAction", "/tournaments/" + id);
            return "tournament-form";
        }

        tournamentService.update(id, request);

        return "redirect:/tournaments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        tournamentService.delete(id);
        return "redirect:/tournaments";
    }

    @PostMapping("/{id}/start")
    public String start(@PathVariable Long id) {
        tournamentService.start(id);
        return "redirect:/tournaments";
    }

    @PostMapping("/{id}/finish")
    public String finish(@PathVariable Long id) {
        tournamentService.finish(id);
        return "redirect:/tournaments";
    }
}