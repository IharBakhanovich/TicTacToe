package com.bakhanovich.tictactoe.model;

import lombok.Builder;
import lombok.Data;

/**
 * A {@link Stats} encapsulates the pair of Integer, which corresponds amount of winning games by both players.
 */
@Data
@Builder
public class Stats {
    private int firstPlayerWins;
    private int secondPlayerWins;

    /**
     * Increment the firstPlayerWins by 1.
     */
    public void incrementFirstPlayerWins() {
        firstPlayerWins++;
    }

    /**
     * Increment the secondPlayerWins by 1.
     */
    public void incrementSecondPlayerWins() {
        secondPlayerWins++;
    }
}
