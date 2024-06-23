package com.bakhanovich.tictactoe.utility;

import com.bakhanovich.tictactoe.exception.IllegalMoveException;

/**
 * Utility class. Contains constants, which are used in the application
 */
public class Constants {
    /**
     * to use by the {@link IllegalArgumentException}
     * and by the {@link IllegalMoveException}
     */
    public static final String MOVE_NOT_ALLOWED = "This cell is already filled";

    /** to catch {@link IllegalMoveException} */
    private final static String GAME_OVER = "Game over";

    /**
     * Returns the {@link Constants} GAME_OVER
     *
     * @return the value of the {@link Constants} GAME_OVER
     */
    public static String getGameOver() {
        return GAME_OVER;
    }

    /**
     * the caller should be prevented from constructing objects of this class
     * by declaring this private constructor
     */
    private Constants() {

        // this prevent even the native class from call in this ctor as well
        throw new AssertionError();
    }
}
