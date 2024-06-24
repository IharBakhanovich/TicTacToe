package com.bakhanovich.tictactoe.model;

import com.bakhanovich.tictactoe.service.PlayBoard;
import lombok.Builder;
import lombok.Getter;

/**
 * A {@link Tile} in the {@link PlayBoard}
 */
@Getter
@Builder
public class Tile {
    private Player player;
    private Sign sign;
}
