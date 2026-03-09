package com.example.footballpairs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "participation_records")
public class ParticipationRecord {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private FootballMatch match;

    @NotNull
    private Integer fromMinute;

    @NotNull
    private Integer toMinute;

    public ParticipationRecord() {
    }

    public ParticipationRecord(Long id, Player player, FootballMatch match, Integer fromMinute, Integer toMinute) {
        this.id = id;
        this.player = player;
        this.match = match;
        this.fromMinute = fromMinute;
        this.toMinute = toMinute;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public FootballMatch getMatch() {
        return match;
    }

    public void setMatch(FootballMatch match) {
        this.match = match;
    }

    public Integer getFromMinute() {
        return fromMinute;
    }

    public void setFromMinute(Integer fromMinute) {
        this.fromMinute = fromMinute;
    }

    public Integer getToMinute() {
        return toMinute;
    }

    public void setToMinute(Integer toMinute) {
        this.toMinute = toMinute;
    }
}
