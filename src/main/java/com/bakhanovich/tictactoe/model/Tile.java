package com.bakhanovich.tictactoe.model;

import com.bakhanovich.tictactoe.service.PlayBoard;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * A {@link Tile} in the {@link PlayBoard}
 */
//@RequiredArgsConstructor
@Builder
@Data
public class Tile {
    private Player player;
    private Sign sign;
}
