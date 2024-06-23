package com.bakhanovich.tictactoe.exception;

import com.bakhanovich.tictactoe.service.PlayBoard;

/**
 * A private {@link Exception} in the {@link PlayBoard}
 */
public class IllegalMoveException extends Exception{
    /**
     * Constructs a new {@link IllegalMoveException}.
     *
     * @param message
     *         is the {@link String}, that incapsulate this Exception.
     */
    public IllegalMoveException(String message) {
        super(message);
    }
}
