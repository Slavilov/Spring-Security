package com.example.footballpairs.controller;

import com.example.footballpairs.model.Player;
import com.example.footballpairs.service.PlayerService;
import com.example.footballpairs.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;

    public PlayerController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("players", playerService.getAll());
        model.addAttribute("teams", teamService.getAll());
        if (!model.containsAttribute("player")) {
            model.addAttribute("player", new Player());
        }
        model.addAttribute("formAction", "/players");
        return "players/index";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("player") Player player,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("players", playerService.getAll());
            model.addAttribute("teams", teamService.getAll());
            model.addAttribute("formAction", "/players");
            return "players/index";
        }

        playerService.save(player);
        return "redirect:/players";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("players", playerService.getAll());
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("player", playerService.getById(id));
        model.addAttribute("formAction", "/players/update/" + id);
        return "players/index";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("player") Player player,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("players", playerService.getAll());
            model.addAttribute("teams", teamService.getAll());
            model.addAttribute("formAction", "/players/update/" + id);
            return "players/index";
        }

        player.setId(id);
        playerService.save(player);
        return "redirect:/players";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        playerService.deleteById(id);
        return "redirect:/players";
    }
}
