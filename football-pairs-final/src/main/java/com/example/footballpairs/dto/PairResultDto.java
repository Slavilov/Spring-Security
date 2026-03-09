package com.example.footballpairs.dto;

import java.util.ArrayList;
import java.util.List;

public class PairResultDto {

    private Long player1Id;
    private String player1Name;
    private Long player2Id;
    private String player2Name;
    private int totalSharedMinutes;
    private List<MatchOverlapDto> matchDetails = new ArrayList<>();

    public PairResultDto() {
    }

    public PairResultDto(Long player1Id, String player1Name, Long player2Id, String player2Name,
                         int totalSharedMinutes, List<MatchOverlapDto> matchDetails) {
        this.player1Id = player1Id;
        this.player1Name = player1Name;
        this.player2Id = player2Id;
        this.player2Name = player2Name;
        this.totalSharedMinutes = totalSharedMinutes;
        this.matchDetails = matchDetails;
    }

    public Long getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(Long player1Id) {
        this.player1Id = player1Id;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public Long getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(Long player2Id) {
        this.player2Id = player2Id;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public int getTotalSharedMinutes() {
        return totalSharedMinutes;
    }

    public void setTotalSharedMinutes(int totalSharedMinutes) {
        this.totalSharedMinutes = totalSharedMinutes;
    }

    public List<MatchOverlapDto> getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(List<MatchOverlapDto> matchDetails) {
        this.matchDetails = matchDetails;
    }
}
