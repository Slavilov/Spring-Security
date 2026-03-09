package com.example.footballpairs.dto;

public class MatchOverlapDto {

    private Long matchId;
    private String matchTitle;
    private int sharedMinutes;

    public MatchOverlapDto() {
    }

    public MatchOverlapDto(Long matchId, String matchTitle, int sharedMinutes) {
        this.matchId = matchId;
        this.matchTitle = matchTitle;
        this.sharedMinutes = sharedMinutes;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public int getSharedMinutes() {
        return sharedMinutes;
    }

    public void setSharedMinutes(int sharedMinutes) {
        this.sharedMinutes = sharedMinutes;
    }
}
