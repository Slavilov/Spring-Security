package com.example.footballpairs.controller;

import com.example.footballpairs.service.PairAnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnalysisController {

    private final PairAnalysisService pairAnalysisService;

    public AnalysisController(PairAnalysisService pairAnalysisService) {
        this.pairAnalysisService = pairAnalysisService;
    }

    @GetMapping("/analysis")
    public String analysis(Model model) {
        model.addAttribute("topPair", pairAnalysisService.getTopPair());
        model.addAttribute("results", pairAnalysisService.getAllPairsSortedBySharedMinutes());
        return "analysis/index";
    }
}
