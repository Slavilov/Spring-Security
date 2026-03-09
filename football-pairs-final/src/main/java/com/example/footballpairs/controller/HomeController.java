package com.example.footballpairs.controller;

import com.example.footballpairs.service.CsvImportService;
import com.example.footballpairs.service.FootballMatchService;
import com.example.footballpairs.service.PlayerService;
import com.example.footballpairs.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final TeamService teamService;
    private final PlayerService playerService;
    private final FootballMatchService footballMatchService;
    private final CsvImportService csvImportService;

    public HomeController(TeamService teamService,
                          PlayerService playerService,
                          FootballMatchService footballMatchService,
                          CsvImportService csvImportService) {
        this.teamService = teamService;
        this.playerService = playerService;
        this.footballMatchService = footballMatchService;
        this.csvImportService = csvImportService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("teamCount", teamService.count());
        model.addAttribute("playerCount", playerService.count());
        model.addAttribute("matchCount", footballMatchService.count());
        return "index";
    }

    @GetMapping("/import")
    public String importData() throws Exception {
        csvImportService.importAll();
        return "redirect:/";
    }
}
