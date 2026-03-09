package com.example.footballpairs.controller;

import com.example.footballpairs.model.FootballMatch;
import com.example.footballpairs.service.FootballMatchService;
import com.example.footballpairs.service.TeamService;
import com.example.footballpairs.util.DateParserUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/matches")
public class FootballMatchController {

    private final FootballMatchService footballMatchService;
    private final TeamService teamService;

    public FootballMatchController(FootballMatchService footballMatchService, TeamService teamService) {
        this.footballMatchService = footballMatchService;
        this.teamService = teamService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("matches", footballMatchService.getAll());
        model.addAttribute("teams", teamService.getAll());
        if (!model.containsAttribute("footballMatch")) {
            model.addAttribute("footballMatch", new FootballMatch());
        }
        model.addAttribute("formAction", "/matches");
        model.addAttribute("dateValue", "");
        return "matches/index";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("footballMatch") FootballMatch footballMatch,
                         BindingResult bindingResult,
                         @RequestParam String rawDate,
                         Model model) {
        return saveMatch(null, footballMatch, bindingResult, rawDate, model);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        FootballMatch footballMatch = footballMatchService.getById(id);
        model.addAttribute("matches", footballMatchService.getAll());
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("footballMatch", footballMatch);
        model.addAttribute("formAction", "/matches/update/" + id);
        model.addAttribute("dateValue", footballMatch.getDate());
        return "matches/index";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("footballMatch") FootballMatch footballMatch,
                         BindingResult bindingResult,
                         @RequestParam String rawDate,
                         Model model) {
        return saveMatch(id, footballMatch, bindingResult, rawDate, model);
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        footballMatchService.deleteById(id);
        return "redirect:/matches";
    }

    private String saveMatch(Long id,
                             FootballMatch footballMatch,
                             BindingResult bindingResult,
                             String rawDate,
                             Model model) {
        try {
            LocalDate parsedDate = DateParserUtil.parseDate(rawDate);
            footballMatch.setDate(parsedDate);
        } catch (Exception exception) {
            bindingResult.rejectValue("date", "invalid.date", "Invalid date format.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("matches", footballMatchService.getAll());
            model.addAttribute("teams", teamService.getAll());
            model.addAttribute("formAction", id == null ? "/matches" : "/matches/update/" + id);
            model.addAttribute("dateValue", rawDate);
            return "matches/index";
        }

        if (id != null) {
            footballMatch.setId(id);
        }

        footballMatchService.save(footballMatch);
        return "redirect:/matches";
    }
}
