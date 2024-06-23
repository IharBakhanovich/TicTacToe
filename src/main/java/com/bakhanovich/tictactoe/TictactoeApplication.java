package com.bakhanovich.tictactoe;

import com.bakhanovich.tictactoe.utility.Constants;
import com.bakhanovich.tictactoe.exception.IllegalMoveException;
import com.bakhanovich.tictactoe.model.Player;
import com.bakhanovich.tictactoe.model.Point;
import com.bakhanovich.tictactoe.service.PlayBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

/**
 * The main Class of the Application.
 */
@SpringBootApplication
public class TictactoeApplication implements CommandLineRunner {
    private final static String PLAYER1_WON = "Player1 has won!";
    private final static String PLAYER2_WON = "Player2 has won!";
    private final static String TIE = "Tie game!";
    private final static String WRONG_COMMAND =
            "Error! You entered the wrong command";
    private final static String TRY_AGAIN =
            "Please try again";
    private final static String NOTE_COMM_PRINT =
            "Command 'print' displays the table of the game."
                    + "\n"
                    + "              To display the table of the game"
                    + " you should write this command WITHOUT parameters."
                    + "\n" + "              Examples: 'print' or 'p'";
    private final static String NOTE_COMM_HELP =
            "Command 'help' displays"
                    + " instructions for using the program and"
                    + " interface." + "\n"
                    + "              To displays"
                    + " instructions write this command "
                    + " WITHOUT parameters." + "\n"
                    + "              Examples: 'help' or 'h'";
    private final static String NOTE_COMM_NEW =
            "Command 'new' creates a new game."
                    + " Existing field will be deleted. " + "\n"
                    + "              For creating a new game you should write"
                    + " this command WITHOUT parameters." + "\n"
                    + "              Example: 'new' or 'n'";
    private final static String NOTE_COMM_QUIT =
            "To quit the program enter the command 'quit' or 'q'." + "\n"
                    + "              Examples: 'quit' or 'q'";
    private final static String NOTE_COMM_MOVE =
            "Command 'move' make a move in the game,"
                    + " if the move is legal."
                    + "\n" + "              To make a move in the game"
                    + " write the command 'move' or 'm' with 2 parameters, "
                    + "\n" + "                which should be integer from 1 to 3."
                    + "\n" + "              Examples: 'move 1 2' or 'm 1 2'. "
                    + "\n" + "              Keep in mind that:" + "\n"
                    + "                  - 'move' is equivalent to 'm';" + "\n"
                    + "                  - parameters must consists of"
                    + " integers from 1 to 3.";
    private final static String NOTE_COMM_STATS =
            "Command 'stats' prints out amount of wins for both players."
                    + "\n"
                    + "              To show amount of players wins, please"
                    + " write the command 'switch' or 's' WITHOUT parameters."
                    + "\n" + "              Examples: 'stats' or 's'.";
    private final static String NOTE =
            "It is the game TicTacToe. There are 2 Players: Player1 amd Player2."
                    + " Both players make moves. Is is possible only those"
                    + " move, which are placed into an empty area of the playboard"
                    + " Win the player, which tiles marks in a row (up, down,\n"
                    + " across, or diagonally). \n"
                    + "\n" + "The following commands are availible in the game:"
                    + "\n"
                    + "- print:  " + NOTE_COMM_PRINT + "\n"
                    + "- help:   " + NOTE_COMM_HELP + "\n"
                    + "- new:    " + NOTE_COMM_NEW + "\n"
                    + "- quit:   " + NOTE_COMM_QUIT + "\n"
                    + "- move:   " + NOTE_COMM_MOVE + "\n"
                    + "- stats:   " + NOTE_COMM_STATS + "\n"
                    + "The program is controlled by using expressions,"
                    + " which consists of parts: command integer1 integer2"
                    + "\n"
                    + "The parts of the expression are separated by space."
                    + "\n" + "Let's try to play." + "\n"
                    + "WELCOM TO THE 'TICTACTOE'";
    private final static String ILLEGAL_MOVE = "Error! Invalid move at ";
    private final static String TRY_ANOTHER_POSITIONS =
            "Error! The position is wrong. Please try entering numbers"
                    + " which are not less than 1 and not more than 3";
    private final static String WRONG_POSITION1 =
            "Error! The position is wrong. It does not consist of numbers";
    private final static String NOTE_POSITIONS =
            "The positions (the first and the second parameter of"
                    + " the command) must consist of integers";
    private final static String WRONG_POSITION2 =
            "Error! The position is wrong. The value does not match integer";
    private static final String PRESS_ENTER_TO_RUN_THE_NEXT_COMMAND = "Press Enter to run the next command...";

    private final PlayBoard playBoard;
    private final Scanner scanner;

    /**
     * Constructor for the {@link TictactoeApplication}
     * @param playBoard
     *         {@link PlayBoard}
     */
    @Autowired
    public TictactoeApplication(PlayBoard playBoard) {
        this.playBoard = playBoard;
        this.scanner = new Scanner(System.in).useDelimiter("\\s+");
    }

    /**
     * Constructor for the {@link TictactoeApplication}
     * @param playBoard
     *         {@link PlayBoard}
     * @param scanner
     *         {@link Scanner}
     */
    public TictactoeApplication(PlayBoard playBoard, Scanner scanner) {
        this.playBoard = playBoard;
        this.scanner = scanner;
    }

    /**
     * The runner of the application.
     *
     * @param args
     *         an arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(TictactoeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String quit = "";
        waitForPressEnter(scanner, NOTE);
        while (!quit.equals("quit")) {
            gameOver(playBoard, scanner);
            String exit = "";
            String[] tokens; // array for save inputs from user by words
            prompt(playBoard.getCurrentPlayer(), playBoard);
            while (!exit.equals("exit")) { // condit to exit human activity
                tokens = scanUserInput(scanner, playBoard.getCurrentPlayer(), playBoard);

                //catches the wrong name of the command
                tokens = validateCommand(tokens);
                switch (tokens[0]) {

                    case ("new"):
                        processNewCommand(tokens);
                        break;
                    case ("help"):
                        processHelpCommand(tokens);
                        break;
                    case ("move"):
                        if (checkCommOfThree(tokens, NOTE_COMM_MOVE, playBoard.getCurrentPlayer(), scanner, playBoard) == null) {
                            break;
                        } else {
                            int positionI = checkCommOfThree(tokens,
                                    NOTE_COMM_MOVE, playBoard.getCurrentPlayer(), scanner, playBoard).getPositionI();
                            int positionJ = checkCommOfThree(tokens,
                                    NOTE_COMM_MOVE, playBoard.getCurrentPlayer(), scanner, playBoard).getPositionJ();
                            try {
                                PlayBoard boardAfterMove = (PlayBoard) playBoard.move(
                                        positionI - 1, positionJ - 1);

                                if (boardAfterMove == null) {
                                    System.out.println(ILLEGAL_MOVE + "("
                                            + positionI + ","
                                            + positionJ + ")");
                                    prompt(playBoard.getCurrentPlayer(), playBoard);
                                    waitForPressEnter(scanner, PRESS_ENTER_TO_RUN_THE_NEXT_COMMAND);
                                    break;
                                } else {
                                    playBoard.setCurrentPlayer(playBoard.getCurrentPlayer() == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1);
                                    if (!playBoard.isGameOver()) {
                                        System.out.println(playBoard.toString());
                                        waitForPressEnter(scanner, PRESS_ENTER_TO_RUN_THE_NEXT_COMMAND);
                                    }
                                    exit = "exit";
                                }
                            } catch (IllegalMoveException exception1) {

                                if (exception1.getMessage().equals(
                                        Constants.MOVE_NOT_ALLOWED)) {
                                    System.out.println("This cell is already filled. Please try another one");
                                    exit = "exit";
                                    waitForPressEnter(scanner, PRESS_ENTER_TO_RUN_THE_NEXT_COMMAND);
                                    break;
                                } else if (exception1.getMessage().
                                        equals(Constants.getGameOver())) {
                                    exit = "exit";
                                    waitForPressEnter(scanner, PRESS_ENTER_TO_RUN_THE_NEXT_COMMAND);
                                    break;
                                }
                            } catch (IllegalArgumentException exception2) {
                                System.out.println(TRY_ANOTHER_POSITIONS);
                                waitForPressEnter(scanner, PRESS_ENTER_TO_RUN_THE_NEXT_COMMAND);
                                prompt(playBoard.getCurrentPlayer(), playBoard);
                            }
                            break;
                        }
                    case ("stats"):
                        processStatsCommand(tokens);
                        break;
                    case ("print"):
                        processPrintCommand(tokens);
                        break;
                    case ("quit"):
                        if (!checkCommOfOne(tokens, NOTE_COMM_PRINT, playBoard.getCurrentPlayer(), scanner, playBoard)) {
                            break;
                        } else {
                            exit = "exit";
                            quit = "quit";
                        }
                    default:
                        break;
                }
            }
        }
    }

    private void processPrintCommand(String[] tokens) {
        if (!checkCommOfOne(tokens, NOTE_COMM_PRINT, playBoard.getCurrentPlayer(), scanner, playBoard)) {
            return;
        } else {
            System.out.println(playBoard.toString());
            waitForPressEnter(scanner, PRESS_ENTER_TO_RUN_THE_NEXT_COMMAND);
            prompt(playBoard.getCurrentPlayer(), playBoard);
            return;
        }
    }

    private void processStatsCommand(String[] tokens) {
        if (!checkCommOfOne(tokens, NOTE_COMM_STATS, playBoard.getCurrentPlayer(), scanner, playBoard)) {
            return;
        } else {
            playBoard.getStatistics();
            waitForPressEnter(scanner, PRESS_ENTER_TO_RUN_THE_NEXT_COMMAND);
            prompt(playBoard.getCurrentPlayer(), playBoard);
            return;
        }
    }

    private void processHelpCommand(String[] tokens) {
        if (!checkCommOfOne(tokens, NOTE_COMM_HELP, playBoard.getCurrentPlayer(), scanner, playBoard)) {
            prompt(playBoard.getCurrentPlayer(), playBoard);
            return;
        }
        System.out.println(NOTE);
        waitForPressEnter(scanner, PRESS_ENTER_TO_RUN_THE_NEXT_COMMAND);
        prompt(playBoard.getCurrentPlayer(), playBoard);
        return;
    }

    private void processNewCommand(String[] tokens) {
        if (!checkCommOfOne(tokens, NOTE_COMM_NEW, playBoard.getCurrentPlayer(), scanner, playBoard)) {
            return;
        }
        playBoard.clearBoard();

        if (playBoard.getFirstPlayer() == Player.PLAYER1) {
            playBoard.setCurrentPlayer(Player.PLAYER2);
            playBoard.setFirstPlayer(Player.PLAYER2);
            prompt(playBoard.getCurrentPlayer(), playBoard);
            return;
        } else {
            playBoard.setCurrentPlayer(Player.PLAYER1);
            playBoard.setFirstPlayer(Player.PLAYER1);
            prompt(playBoard.getCurrentPlayer(), playBoard);
            return;
        }
    }

    private String[] validateCommand(String[] tokens) {
        while (!tokens[0].equalsIgnoreCase("new")
                && !tokens[0].equalsIgnoreCase("move")
                && !tokens[0].equalsIgnoreCase("stats")
                && !tokens[0].equalsIgnoreCase("print")
                && !tokens[0].equalsIgnoreCase("help")
                && !tokens[0].equalsIgnoreCase("quit")) {
            error(WRONG_COMMAND, NOTE, TRY_AGAIN, playBoard.getCurrentPlayer(), scanner, playBoard);
            tokens = scanUserInput(scanner, playBoard.getCurrentPlayer(), playBoard);

        }
        return tokens;
    }

    static void waitForPressEnter(Scanner scanner, String message) {
        System.out.println(message);
        scanner.nextLine();
        clearConsole();
    }

    ;

    /**
     * Makes the command 'new' of the game
     *
     * @param game
     *         is the current game, that is to check, whether the conditions
     *         of the end of the game met.
     * @param scanner
     *         is the {@link Scanner}
     */
    private static void gameOver(PlayBoard game, Scanner scanner) {
        if (game.isGameOver()) {
            System.out.println(game.toString());
            Player winner = game.getWinner();
            if (winner == Player.PLAYER1) {
                System.out.println(PLAYER1_WON);
            } else if (winner == Player.PLAYER2) {
                System.out.println(PLAYER2_WON);
            } else if (winner == null) {
                System.out.println(TIE);
            }
            game.clearBoard();
            // set new first Player
            if (winner == Player.PLAYER1) {
                game.setFirstPlayer(Player.PLAYER2);
            } else if (winner == Player.PLAYER2) {
                game.setFirstPlayer(Player.PLAYER1);
            } else {
                game.setFirstPlayer(game.getFirstPlayer() == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1);
            }
            waitForPressEnter(scanner, PRESS_ENTER_TO_RUN_THE_NEXT_COMMAND);

        }
    }

    /**
     * Prints prompt
     *
     * @param player
     *         is the {@link Player}, which is on the game
     * @param playBoard
     *         is the current {@link PlayBoard}
     */
    private static void prompt(Player player, PlayBoard playBoard) {
        System.out.println(playBoard);
        System.out.printf("%s, please enter a next command > ", player.toString());
    }

    /**
     * Takes from a user input and transform it to String array of tokens.
     *
     * @param scanner
     *         is the scanner, that receives input from a user.
     * @param player
     *         is the {@link Player}, which is on the game
     * @param playBoard
     *         is the current {@link PlayBoard}
     * @return String array, which consists parts of the user input as Strings.
     */
    private static String[] scanUserInput(Scanner scanner, Player player, PlayBoard playBoard) {
        String readScanner;
        readScanner = scanner.nextLine();
        while (readScanner.trim().equalsIgnoreCase("")) { // catches IOoB
            clearConsole();
            prompt(player, playBoard);
            readScanner = scanner.nextLine();
        }
        return translateInputInArray(readScanner.trim());
    }

    /**
     * Saves user input by words in the array and returns this array
     *
     * @param userInput is the text entered by user
     * @return array, array which comprises separately words entered by the user
     */
    private static String[] translateInputInArray(String userInput) {
        String delimeter = "\\s+";

        //to save users inputs by words
        String[] userInputToTokens = userInput.split(delimeter);

        // translates an expression to lowercase
        for (int i = 0; i < userInputToTokens.length; i++) {
            userInputToTokens[i] = userInputToTokens[i].toLowerCase();
        }
        userInputToTokens[0] = acceptOneLetterCommand(userInputToTokens[0]);
        return userInputToTokens;
    }

    /**
     * Implements the possibility of using the first letter as
     * the whole command. The one-letter input, that accepted by the program
     * is replaced by a full command. In case that the input does not match
     * the shortened version of the command, the input remains unchanged.
     *
     * @param string
     *         consists the input from user.
     * @return String contains either the full version of the abbreviated
     *         command entered by the user or the entered command unchanged
     */
    private static String acceptOneLetterCommand(String string) {
        String newCommand = string;
        switch (string) {

            case "n":
                newCommand = "new";
                break;
            case "h":
                newCommand = "help";
                break;
            case "s":
                newCommand = "stats";
                break;
            case "m":
                newCommand = "move";
                break;
            case "p":
                newCommand = "print";
                break;
            case "q":
                newCommand = "quit";
                break;
            default:
                return newCommand;
        }
        return newCommand;
    }

    /**
     * Returns error message, which corresponds to the type of the error and
     * the prompt of the system.
     *
     * @param message1
     *         corresponds the type of the error.
     * @param message2
     *         is the information, that helps to the user to take
     *         the right action in subsequent communications with the system.
     * @param message3
     *         is the information, what the user should do next.
     * @param player
     *         the current player of the game
     * @param scanner
     *         the Scanner
     * @param playBoard
     *         the game board
     */
    private static void error(String message1, String message2,
                              String message3, Player player, Scanner scanner, PlayBoard playBoard) {
        System.out.println(message1 + "\n" + "\n" + message2 + "\n" + "\n"
                + message3 + "\n");
        waitForPressEnter(scanner, PRESS_ENTER_TO_RUN_THE_NEXT_COMMAND);
        prompt(player, playBoard);
    }

    /**
     * Checks the content of the command, that has not parameters.
     *
     * @param tokens
     *         is the array, that elements contains the user command.
     * @param note
     *         the output text, that belongs to the command being processed.
     * @param player
     *         the current player of the game
     * @param scanner
     *         the scanner
     * @param playBoard
     *         the game board
     *
     * @return true if command consists only of the name and false otherwise.
     */
    private static boolean checkCommOfOne(String[] tokens, String note, Player player, Scanner scanner, PlayBoard playBoard) {

        if (tokens.length != 1) {
            error(WRONG_COMMAND, note, TRY_AGAIN, player, scanner, playBoard);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks whether the value {@param string} a number.
     *
     * @param string
     *         is the value, that is the second parameter of the command.
     * @return true if the {@param string} a number and false otherwise
     */
    private static boolean isCoordinateValid(String string) {
        return string.matches("\\d+")
                || string.matches("-\\d+");
    }

    /**
     * checks the content of the command, that parameters are coordinates
     * @param tokens
     *         is the array, that elements contains the parts of the user command
     * @param note
     *         the output text, that belongs to the command being processed
     * @param player
     *         the player
     * @param scanner
     *         the {@link Scanner}
     * @param playBoard
     *         the game board
     *
     * @return null if command parameters (positions) are wrong and
     *         {@link Point}, if positions were entered correctly
     */
    private static Point checkCommOfThree(String[] tokens, String note, Player player, Scanner scanner, PlayBoard playBoard) {
        int positionI;
        int positionJ;

        if (tokens.length != 3) {
            error(WRONG_COMMAND, note, TRY_AGAIN, player, scanner, playBoard);
            return null;
        } else if (!isCoordinateValid(tokens[1])
                || !isCoordinateValid(tokens[2])) {
            error(WRONG_POSITION1, NOTE_POSITIONS, TRY_AGAIN, player, scanner, playBoard);
            return null;
        } else {
            positionI = parseValue(tokens[1]);
            positionJ = parseValue(tokens[2]);
            // catches NumberFormatException
            if (parseValue(tokens[1]) == Integer.MIN_VALUE
                    || (parseValue(tokens[2]) == Integer.MIN_VALUE)) {
                error(WRONG_POSITION2, NOTE_POSITIONS,
                        TRY_ANOTHER_POSITIONS, player, scanner, playBoard);
                return null;
            } else {
                return Point.builder().positionI(positionI).positionJ(positionJ).build();
            }
        }
    }

    /**
     * Parses second parameter of user input from String to int.
     * Catches NumberFormatException and checks whether
     * the input number negative.
     *
     * @param token
     *         is the text entered by user.
     * @return the value if this value was entered by the user correctly,
     *         or Integer.MIN_VALUE otherwise.
     */
    private static int parseValue(String token) {
        int value;
        try {

            if (token.toCharArray()[0] == '-') {
                value = Integer.parseInt(token.substring(1));
                value = value * -1;
            } else {
                value = Integer.parseInt(token);
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return Integer.MIN_VALUE;
        }
        return value;
    }

    /**
     * NOTE: ClearConsole works only in ordinary terminals. It does not work in IDEs terminals.
     */
    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            System.err.println("Error clearing console: " + ex.getMessage());
        }
    }
}
