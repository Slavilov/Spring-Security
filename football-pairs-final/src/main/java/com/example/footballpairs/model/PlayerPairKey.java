package com.example.footballpairs.model;

import java.util.Objects;

public class PlayerPairKey {

    private final Long player1Id;
    private final Long player2Id;

    public PlayerPairKey(Long first, Long second) {
        if (first <= second) {
            this.player1Id = first;
            this.player2Id = second;
        } else {
            this.player1Id = second;
            this.player2Id = first;
        }
    }

    public Long getPlayer1Id() {
        return player1Id;
    }

    public Long getPlayer2Id() {
        return player2Id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PlayerPairKey other)) {
            return false;
        }
        return Objects.equals(player1Id, other.player1Id)
                && Objects.equals(player2Id, other.player2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1Id, player2Id);
    }
}
