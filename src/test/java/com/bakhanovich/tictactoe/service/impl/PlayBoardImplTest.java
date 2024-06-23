package com.bakhanovich.tictactoe.service.impl;

import com.bakhanovich.tictactoe.exception.IllegalMoveException;
import com.bakhanovich.tictactoe.model.Player;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;

class PlayBoardImplTest {
    PlayBoardImpl playBoard;

    @BeforeEach
    void setUp(){
        playBoard = new PlayBoardImpl();
    }
    @Test
    void getFirstPlayer_ifGetAfterInit_ReturnsPlayer1(){
        // given

        // when
        Player firstPlayer = playBoard.getFirstPlayer();
        // then
        assertEquals(Player.PLAYER1, firstPlayer);
    }

    @Test
    void getCurrentPlayer_ifGetAfterInit_ReturnsPlayer1(){
        // given

        // when
        Player currentPlayer = playBoard.getCurrentPlayer();
        // then
        assertEquals(Player.PLAYER1, currentPlayer);
    }

    @Test
    void getWinner_ifGetAfterInit_ReturnsNull(){
        // given

        // when
        Player winner = playBoard.getWinner();
        // then
        assertNull(winner);
    }

    @Test
    void isGameOver_ifBoardNotFilled_ReturnsFalse() throws IllegalMoveException {
        // given

        // when
        boolean isGameOver = playBoard.isGameOver();
        // then
        assertFalse(isGameOver);
    }

    @Test
    void isGameOver_ifBoardFilled_ReturnsTrue() throws IllegalMoveException {
        // given
        fillThePlayBoard();

        // when
        boolean isGameOver = playBoard.isGameOver();
        // then
        assertTrue(isGameOver);
    }

    @ParameterizedTest
    @CsvSource(value={"1","2","0"})
    void isGameOver_ifWinningCombinationVertical_ReturnsTrue(int col) throws IllegalMoveException {
        // given
        playBoard.move(0, col);
        playBoard.move(1, col);
        playBoard.move(2, col);

        // when
        boolean isGameOver = playBoard.isGameOver();
        // then
        assertTrue(isGameOver);
    }

    @ParameterizedTest
    @CsvSource(value={"1","2","0"})
    void isGameOver_ifWinningCombinationhorisontal_ReturnsTrue(int row) throws IllegalMoveException {
        // given
        playBoard.move(row,0);
        playBoard.move(row, 1);
        playBoard.move(row,2);

        // when
        boolean isGameOver = playBoard.isGameOver();
        // then
        assertTrue(isGameOver);
    }

    @ParameterizedTest
    @CsvSource(value={"1","2","0"})
    void isGameOver_ifWinningCombinationVerticalcurrentIsPlayer2_ReturnsTrue(int col) throws IllegalMoveException {
        // given
        playBoard.setCurrentPlayer(Player.PLAYER2);
        playBoard.move(0, col);
        playBoard.move(1, col);
        playBoard.move(2, col);

        // when
        boolean isGameOver = playBoard.isGameOver();
        // then
        assertTrue(isGameOver);
    }

    @ParameterizedTest
    @CsvSource(value={"1","2","0"})
    void isGameOver_ifWinningCombinationhorisontalCurrentIsPlayer2_ReturnsTrue(int row) throws IllegalMoveException {
        // given
        playBoard.setCurrentPlayer(Player.PLAYER2);
        playBoard.move(row,0);
        playBoard.move(row, 1);
        playBoard.move(row,2);

        // when
        boolean isGameOver = playBoard.isGameOver();
        // then
        assertTrue(isGameOver);
        assertEquals(playBoard.getWinner(), Player.PLAYER2);
    }

    @ParameterizedTest
    @CsvSource(value={"1","2","0"})
    void isGameOver_ifWinningCombinationhorisontalCurrentIsPlayer1_ReturnsTrue(int row) throws IllegalMoveException {
        // given
        playBoard.move(row,0);
        playBoard.move(row, 1);
        playBoard.move(row,2);

        // when
        boolean isGameOver = playBoard.isGameOver();
        // then
        assertTrue(isGameOver);
        assertEquals(playBoard.getWinner(), Player.PLAYER1);
    }

    @Test
    void isGameOver_ifTieGame_ReturnsTrue() throws IllegalMoveException {
        // given
        playBoard.move(1,1);
        playBoard.setCurrentPlayer(Player.PLAYER2);
        playBoard.move(0, 1);
        playBoard.setCurrentPlayer(Player.PLAYER1);
        playBoard.move(0,0);
        playBoard.setCurrentPlayer(Player.PLAYER2);
        playBoard.move(2,2);
        playBoard.setCurrentPlayer(Player.PLAYER1);
        playBoard.move(0,2);
        playBoard.setCurrentPlayer(Player.PLAYER2);
        playBoard.move(2,0);
        playBoard.setCurrentPlayer(Player.PLAYER1);
        playBoard.move(2,1);
        playBoard.setCurrentPlayer(Player.PLAYER2);
        playBoard.move(1,2);
        playBoard.setCurrentPlayer(Player.PLAYER1);
        playBoard.move(1,0);


        // when
        boolean isGameOver = playBoard.isGameOver();
        // then
        assertTrue(isGameOver);
        assertNull(playBoard.getWinner());
    }


    @Test
    void isGameOver_ifWinningCombinationDiagonal1_ReturnsTrue() throws IllegalMoveException {
        // given
        playBoard.move(0, 0);
        playBoard.move(1, 1);
        playBoard.move(2, 2);

        // when
        boolean isGameOver = playBoard.isGameOver();
        // then
        assertTrue(isGameOver);
    }

    @Test
    void isGameOver_ifWinningCombinationDiagonal2_ReturnsTrue() throws IllegalMoveException {
        // given
        playBoard.move(0, 2);
        playBoard.move(1, 1);
        playBoard.move(2, 0);

        // when
        boolean isGameOver = playBoard.isGameOver();
        // then
        assertTrue(isGameOver);
    }

    @Test
    @SneakyThrows
    void isGameOver_boardIsNull_ReturnsFalse() throws IllegalMoveException {
        // given
        Field currentBoardField = playBoard.getClass().getDeclaredField("currentBoard");
        currentBoardField.setAccessible(true);
        currentBoardField.set(playBoard, null);

        // when
        boolean isGameOver = playBoard.isGameOver();
        // then
        assertTrue(isGameOver);
    }

    @Test
    void getStatistics_atTheBeginningOfTheGame_BothPlayersHave0() throws IllegalMoveException {
        // given

        // when then
        assertEquals(0, playBoard.getStats().getFirstPlayerWins());
        assertEquals(0, playBoard.getStats().getSecondPlayerWins());
    }

    @ParameterizedTest
    @CsvSource(value={"-1, 0","3, 0","0, -1", "0, 3"})
    void move_ifOutOfBound_Throws(int row, int col) throws IllegalMoveException {
        // given

        // when then
        assertThrows(IllegalArgumentException.class,
                ()->{playBoard.move(row, col);});
    }

    @Test
    void move_ifCurrentCellIsNotNull_Throws() throws IllegalMoveException {
        // given
        playBoard.move(0, 2);

        // when then
        assertThrows(IllegalMoveException.class,
                ()->{playBoard.move(0, 2);});
    }

    @Test
    void clearBoard_ifBoaerWasFilled_BoardIsClear() throws IllegalMoveException {
        // given
        playBoard.move(0, 2);
        boolean allCellsClearBefore = Arrays.stream(playBoard.getCurrentBoard())
                .flatMap(Arrays::stream)
                .allMatch(Objects::nonNull);
        assertFalse(allCellsClearBefore);

        // when
        playBoard.clearBoard();
        boolean allCellsClearAfter = Arrays.stream(playBoard.getCurrentBoard())
                .flatMap(Arrays::stream)
                .noneMatch(Objects::nonNull);

        // then
        assertTrue(allCellsClearAfter);
    }

    @Test
    void setFirstPlayer_ifWasPlayer1_NowPlayer2() throws IllegalMoveException {
        // given
        assertEquals(Player.PLAYER1, playBoard.getFirstPlayer());

        // when
        playBoard.setFirstPlayer(Player.PLAYER2);

        // then
        assertEquals(Player.PLAYER2, playBoard.getFirstPlayer());
    }

    @Test
    @SneakyThrows
    void getStatictics_ifStartTheGame_void() throws IllegalMoveException {
        // given
        assertEquals(0, playBoard.getStats().getFirstPlayerWins());
        assertEquals(0, playBoard.getStats().getSecondPlayerWins());

        // when
        // Capture the system output
        String systemOut = tapSystemOut(() -> {
            playBoard.getStatistics();
        });

        // Define the expected output
        String expectedOutput = String.format(
                "%s has won %s times. \n%s has won %s times.%n",
                Player.PLAYER1.toString(), playBoard.getStats().getFirstPlayerWins(),
                Player.PLAYER2.toString(), playBoard.getStats().getSecondPlayerWins()
        );

        // then
        assertEquals(expectedOutput, systemOut);
    }

    @Test
    void toString_ifStartTheGame_void() throws IllegalMoveException {
        // given
        String expectedOutput = "\n" +
                "   |    |    \n" +
                "-------------\n" +
                "   |    |    \n" +
                "-------------\n" +
                "   |    |    \n";

        // when
        String toString = playBoard.toString();

        // then
        assertEquals(expectedOutput, toString);
    }

    @Test
    void toString_afterTwoMoves_void() throws IllegalMoveException {
        // given
        playBoard.move(1, 1);
        playBoard.setCurrentPlayer(Player.PLAYER2);
        playBoard.move(0, 0);
        String expectedOutput = "\n" +
                " O |    |    \n" +
                "-------------\n" +
                "   |  X |    \n" +
                "-------------\n" +
                "   |    |    \n";

        // when
        String toString = playBoard.toString();

        // then
        assertEquals(expectedOutput, toString);
    }

    @Test
    void getFirstPlayer_afterGameStart_Player1() throws IllegalMoveException {
        // given

        // when
        Player expectedFirstPlayer = playBoard.getFirstPlayer();

        // then
        assertEquals(Player.PLAYER1, expectedFirstPlayer);
    }


    private void fillThePlayBoard() throws IllegalMoveException {
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                playBoard.move(i, j);
            }
        }
    }
}
