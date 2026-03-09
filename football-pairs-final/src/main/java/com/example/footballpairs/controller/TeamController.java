package com.example.footballpairs.controller;

import com.example.footballpairs.model.Team;
import com.example.footballpairs.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("teams", teamService.getAll());
        if (!model.containsAttribute("team")) {
            model.addAttribute("team", new Team());
        }
        model.addAttribute("formAction", "/teams");
        return "teams/index";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("team") Team team,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.getAll());
            model.addAttribute("formAction", "/teams");
            return "teams/index";
        }

        teamService.save(team);
        return "redirect:/teams";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("team", teamService.getById(id));
        model.addAttribute("formAction", "/teams/update/" + id);
        return "teams/index";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("team") Team team,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.getAll());
            model.addAttribute("formAction", "/teams/update/" + id);
            return "teams/index";
        }

        team.setId(id);
        teamService.save(team);
        return "redirect:/teams";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        teamService.deleteById(id);
        return "redirect:/teams";
    }
}
