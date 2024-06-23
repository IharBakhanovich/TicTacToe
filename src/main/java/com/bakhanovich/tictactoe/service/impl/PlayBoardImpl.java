package com.bakhanovich.tictactoe.service.impl;

import com.bakhanovich.tictactoe.utility.Constants;
import com.bakhanovich.tictactoe.exception.IllegalMoveException;
import com.bakhanovich.tictactoe.model.Player;
import com.bakhanovich.tictactoe.model.Sign;
import com.bakhanovich.tictactoe.model.Stats;
import com.bakhanovich.tictactoe.model.Tile;
import com.bakhanovich.tictactoe.service.PlayBoard;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * Implements a {@link PlayBoard} of the Game. A Board contains
 * the current {@link Player} of the game, Board of {@link Tile}s,
 * array of {@link Tile} on the board,
 * the parameter {@link Player} firstPlayer, that shows the player,
 * that made the first move of the game
 */
@Component
@Getter
public class PlayBoardImpl implements PlayBoard {
    private final static String INVALID_PARAMETERS = "The value of the row or"
            + "the value of the column of your move are invalid." + "\n"
            + " Values of the row and the value of the column"
            + " must be from 1 to 3";
    //    private final static int SIZE = 3;
    private Player currentPlayer;
    private Tile[][] currentBoard;
    private Player firstPlayer;
    private Player winner;
    private Stats stats;

    /**
     * Constructs a new start {@link PlayBoardImpl}.
     */
    public PlayBoardImpl() {
        this.currentPlayer = Player.PLAYER1;
        this.currentBoard = new Tile[SIZE][SIZE];
        this.firstPlayer = Player.PLAYER1;
        this.stats = Stats.builder().firstPlayerWins(0).secondPlayerWins(0).build();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public PlayBoard move(int row, int col) throws IllegalMoveException, IllegalArgumentException {
        if ((row < 0 || row > 2) || (col < 0 || col > 2)) {
            throw new IllegalArgumentException(Constants.MOVE_NOT_ALLOWED);
        }
        if (currentBoard[row][col] != null) {
            throw new IllegalMoveException(Constants.MOVE_NOT_ALLOWED);
        }
        Sign currentPlayerSign = currentPlayer.equals(firstPlayer) ? Sign.X : Sign.O;
        currentBoard[row][col] = Tile.builder().player(currentPlayer).sign(currentPlayerSign).build();

        // check possible end of the game
        if (isGameOver()) {
            Player winner = isThereWinningCombination();
            setWinner(winner);

            // organize statistics
            if (winner != null) {
                if (winner.equals(Player.PLAYER1)) {
                    stats.incrementFirstPlayerWins();
                } else {
                    stats.incrementSecondPlayerWins();
                }
            }

        }
        return this;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        // TODO make a US (not a scope of this feature): there are combinations, by which there is no sense to make a move, because the positions is 100% going to be tie. Also the US is to calculate such positions and to stop the game and announce the tie.
        return (isTheBoardFilled() || isThereWinningCombination() != null);
    }

    /**
     * Checks is there a winning position and if it is so, returns the winner.
     *
     * @return the {@link Player} who won the game or null if there isn't found any winning combination.
     */
    private Player isThereWinningCombination() {

        // Check rows and columns for each player
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (currentBoard[i][0] != null && currentBoard[i][1] != null && currentBoard[i][2] != null) {
                if (currentBoard[i][0].getPlayer() == currentBoard[i][1].getPlayer() &&
                        currentBoard[i][1].getPlayer() == currentBoard[i][2].getPlayer()) {
                    return currentBoard[i][0].getPlayer();
                }
            }
            // Check columns
            if (currentBoard[0][i] != null && currentBoard[1][i] != null && currentBoard[2][i] != null) {
                if (currentBoard[0][i].getPlayer() == currentBoard[1][i].getPlayer() &&
                        currentBoard[1][i].getPlayer() == currentBoard[2][i].getPlayer()) {
                    return currentBoard[0][i].getPlayer();
                }
            }
        }

        // Check diagonals
        if (currentBoard[0][0] != null && currentBoard[1][1] != null && currentBoard[2][2] != null) {
            if (currentBoard[0][0].getPlayer() == currentBoard[1][1].getPlayer() &&
                    currentBoard[1][1].getPlayer() == currentBoard[2][2].getPlayer()) {
                return currentBoard[0][0].getPlayer();
            }
        }
        if (currentBoard[0][2] != null && currentBoard[1][1] != null && currentBoard[2][0] != null) {
            if (currentBoard[0][2].getPlayer() == currentBoard[1][1].getPlayer() &&
                    currentBoard[1][1].getPlayer() == currentBoard[2][0].getPlayer()) {
                return currentBoard[0][2].getPlayer();
            }
        }

        // No winning combination found
        return null;
    }

    /**
     * Checks whether the board is filled.
     *
     * @return true if the board is filled
     */
    private boolean isTheBoardFilled() {
        // Check if the currentBoard itself is null
        if (currentBoard == null) {
            return true;
        }

        // Flatten the 2D array to a 1D stream and check if any element is null
        return Arrays.stream(currentBoard)
                .flatMap(Arrays::stream)
                .allMatch(Objects::nonNull);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Player getWinner() {
        return isThereWinningCombination();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void clearBoard() {
        this.currentBoard = new Tile[SIZE][SIZE];
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void setFirstPlayer(Player player) {
        firstPlayer = player;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void setWinner(Player player) {
        winner = player;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void getStatistics() {
        System.out.printf(
                "%s has won %s times. \n%s has won %s times.%n",
                Player.PLAYER1.toString(), stats.getFirstPlayerWins(),
                Player.PLAYER2.toString(), stats.getSecondPlayerWins()
        );

    }

    //    /**
//     *{@inheritDoc}
//     */
//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("\n");
//        for (int i = 0; i < currentBoard.length; i++) {
//            for (int j = 0; j < currentBoard[i].length; j++) {
//
//                if (currentBoard[i][j] == null) {
//                    if (j<2) {
//                        builder.append(" . |");
//                    } else {
//                        builder.append(" . ");
//                    }
//
//                } else if (currentBoard[i][j].getPlayer() == Player.PLAYER1) {
//                    if (firstPlayer.equals(Player.PLAYER1)) {
//                        if (j<2) {
//                            builder.append(" X |");
//                        } else {
//                            builder.append(" X ");
//                        }
//
//                    } else {
//                        if (j<2) {
//                            builder.append(" O |");
//                        } else {
//                            builder.append(" O ");
//                        }
//                    }
//
//                } else if (currentBoard[i][j].getPlayer() == Player.PLAYER2) {
//                    if (firstPlayer.equals(Player.PLAYER2)) {
//                        if (j<2) {
//                            builder.append(" X |");
//                        } else {
//                            builder.append(" X ");
//                        }
//                    } else {
//                        if (j<2) {
//                            builder.append(" O |");
//                        } else {
//                            builder.append(" O ");
//                        }
//                    }
//                }
//
//                if (j < currentBoard[i].length - 1) {
//                    builder.append(" ");
//                }
//            }
//            builder.append("\n");
//
//            if (i < 2) {
//                builder.append("-------------");
//                builder.append("\n");
//            }
//        }
//        return builder.toString();
//    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\n");

        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                builder.append(formatCell(i, j));
                if (j < currentBoard[i].length - 1) {
                    builder.append(" ");
                }
            }
            builder.append("\n");
            if (i < 2) {
                builder.append("-------------\n");
            }
        }
        return builder.toString();
    }

    private String formatCell(int i, int j) {
        if (currentBoard[i][j] == null) {
            return j < 2 ? "   |" : "   ";
        }

        Player player = currentBoard[i][j].getPlayer();
        boolean isFirstPlayer = firstPlayer.equals(player);
        String symbol = (isFirstPlayer ? " X " : " O ");

        return j < 2 ? symbol + "|" : symbol;
    }
}
