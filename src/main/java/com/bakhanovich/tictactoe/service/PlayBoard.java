package com.bakhanovich.tictactoe.service;


import com.bakhanovich.tictactoe.exception.IllegalMoveException;
import com.bakhanovich.tictactoe.model.Player;

/**
 * Interface for a TicTacToe game.
 */
public interface PlayBoard {
    /**
     * The number of rows and columns of the game grid. Originally 3.
     */
    int SIZE = 3;

    /**
     * Gets the player who should start or already has started the game.
     *
     * @return The player who makes the initial move.
     */
    Player getFirstPlayer();

    /**
     * Gets the player who should start or already has started the game.
     *
     * @return The player who makes the initial move.
     */
    Player getCurrentPlayer();

    /**
     * Executes a move. This method does not change the state of this
     * instance, which is treated here as immutable. Instead, a new board/game
     * is returned, which is a copy of {@code this} with the move executed.
     *
     * @param row The slot's row number where a tile of the move should
     *        be placed on.
     * @param col The slot's column number where a tile of the move
     *        should be placed on.
     * @return A new board with the move executed. If the move is not valid,
     *         e.g., the defined slot was occupied then {@code null} will be returned.
     * @throws IllegalMoveException If the game is already over.
     * @throws IllegalArgumentException If the provided parameters are invalid,
     *         e.g., the defined slot is not on the grid.
     */
    PlayBoard move(int row, int col) throws IllegalMoveException,
            IllegalArgumentException;

    /**
     * Checks if the game is over. Either one of the players has won
     * or there is a tie, i.e., no player can perform a move anymore.
     *
     * @return {@code true} if and only if the game is over.
     */
    boolean isGameOver();

    /**
     * Checks if the game state is won. Should only be called if
     * {@link #isGameOver()} returns {@code true}.
     *
     * @return The winner or nobody in case of a tie.
     */
    Player getWinner();

    /**
     * Gets the string representation of this board as row x column matrix. Each
     * slot is represented by one the three chars ' ', 'X', or 'O'. ' ' means
     * that the slot currently contains no tile. 'X' means that it contains a
     * tile of the human player. 'O' means that it contains a machine tile. In
     * contrast to the rows, the columns are whitespace separated.
     *
     * @return The string representation of the current Reversi game.
     */
    @Override
    String toString();

    /**
     * Gets the opportunity to clear the {@link PlayBoard}.
     */
    void clearBoard();

    /**
     * Provides an opportunity to set the current player of the game.
     * @param player
     *         the {@link Player}
     */
    void setCurrentPlayer(Player player);

    /**
     * Provides an opportunity to set the {@link Player}, who made a first move in the game.
     * @param player
     *         the {@link Player}
     */
    void setFirstPlayer(Player player);

    /**
     * Provides an opportunity to set the {@link Player}, who won the game.
     * @param player
     *         the {@link Player}
     */
    void setWinner(Player player);

    /**
     * Provides an opportunity to output statistics about the winners.
     */
    void getStatistics();
}
