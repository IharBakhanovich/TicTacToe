package com.bakhanovich.tictactoe;

import com.bakhanovich.tictactoe.exception.IllegalMoveException;
import com.bakhanovich.tictactoe.model.Player;
import com.bakhanovich.tictactoe.service.PlayBoard;

import com.bakhanovich.tictactoe.service.impl.PlayBoardImpl;
import com.bakhanovich.tictactoe.utility.Constants;
import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TictactoeApplicationTests {

	@Mock
	private PlayBoard playBoard;
	@Mock
	private Scanner scanner;

	@InjectMocks
	private TictactoeApplication tictactoeApplication;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		tictactoeApplication = new TictactoeApplication(playBoard, scanner);
	}

	@Test
	public void testWaitForPressEnter() {
		// given
		TictactoeApplication tictactoeApplication = new TictactoeApplication(null, scanner);

		// when then
		assertDoesNotThrow(() -> tictactoeApplication.waitForPressEnter(scanner, "Press Enter to continue..."));
	}

	@Test
	public void testWaitForPressEnter_void() throws Exception {
		// given

		// when then
		SystemLambda.withTextFromSystemIn("\n").execute(() -> {

			java.lang.reflect.Method method = TictactoeApplication.class.getDeclaredMethod("waitForPressEnter", Scanner.class, String.class);
			method.setAccessible(true);
			assertDoesNotThrow(() -> method.invoke(tictactoeApplication, scanner, "Press Enter to continue..."));
		});
	}

	@Test
	void testRun_NewQuit_void() throws Exception {
		// given
		when(scanner.nextLine()).thenReturn("\n","new", "quit");
		doNothing().when(playBoard).clearBoard();
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(1)).clearBoard();
		verify(playBoard, times(1)).setCurrentPlayer(Player.PLAYER2);
		verify(playBoard, times(1)).setFirstPlayer(Player.PLAYER2);
		verify(scanner, times(3)).nextLine();  // to simulate "Press Enter", "new", and "quit"
	}

	@Test
	void testRun_NewQuitPlayer2_void() throws Exception {
		// given
		when(scanner.nextLine()).thenReturn("\n","new", "quit");
		doNothing().when(playBoard).clearBoard();
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER2);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER2);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(1)).clearBoard();
		verify(playBoard, times(1)).setCurrentPlayer(Player.PLAYER1);
		verify(playBoard, times(1)).setFirstPlayer(Player.PLAYER1);
		verify(scanner, times(3)).nextLine();  // to simulate "Press Enter", "new", and "quit"
	}

	@Test
	void testRun_NQ_void() throws Exception {
		// given
		when(scanner.nextLine()).thenReturn("\n","n", "q");
		doNothing().when(playBoard).clearBoard();
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(1)).clearBoard();
		verify(playBoard, times(1)).setCurrentPlayer(Player.PLAYER2);
		verify(playBoard, times(1)).setFirstPlayer(Player.PLAYER2);
		verify(scanner, times(3)).nextLine();  // to simulate "Press Enter", "new", and "quit"
	}

	@Test
	void testRun_StatsQuit_void() throws Exception {
		// given
		when(scanner.nextLine()).thenReturn("\n","stats", "quit");
		doNothing().when(playBoard).clearBoard();
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(6)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
		verify(playBoard, times(1)).getStatistics();
	}

	@Test
	void testRun_SQ_void() throws Exception {
		when(scanner.nextLine()).thenReturn("\n","s", "q");

		doNothing().when(playBoard).clearBoard();
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		tictactoeApplication.run();

		verify(playBoard, times(6)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
		verify(playBoard, times(1)).getStatistics();
	}

	@Test
	public void testPrintQuit() throws Exception {
		when(scanner.nextLine()).thenReturn("\n","p", "q");

		doNothing().when(playBoard).clearBoard();
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		tictactoeApplication.run();

		verify(playBoard, times(6)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@Test
	public void testRun_PQ_void() throws Exception {
		// given
		when(scanner.nextLine()).thenReturn("\n","print", "quit");
		doNothing().when(playBoard).clearBoard();
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(6)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@Test
	public void testRun_HelpQuit_void() throws Exception {
		// given
		when(scanner.nextLine()).thenReturn("\n","help", "quit");
		doNothing().when(playBoard).clearBoard();
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(6)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@Test
	public void testRun_HQ_void() throws Exception {
		// given
		when(scanner.nextLine()).thenReturn("\n","h", "q");
		doNothing().when(playBoard).clearBoard();
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(6)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@Test
	public void testRun_MoveQuit_NullByMove_void() throws Exception {
		// given
		when(scanner.nextLine()).thenReturn("\n","move 2 2", "quit");
		when(playBoard.move(2,2)).thenReturn(playBoard);
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(8)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@Test
	public void testRun_MoveQuit_void() throws Exception {
		// given
		PlayBoard playBoardImpl = new PlayBoardImpl();
		when(playBoard.move(anyInt(), anyInt())).thenReturn(playBoardImpl);

		when(scanner.nextLine()).thenReturn("\n","move 2 2", "quit");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
//		verify(playBoard, times(1)).clearBoard();
		verify(playBoard, times(1)).move(1, 1);
		verify(playBoard, times(9)).getCurrentPlayer();
		verify(playBoard, times(3)).isGameOver();
	}

	@Test
	public void testRun_MoveThrowsIllegalArgumentExceptionQuit_void() throws Exception {
		// given
		PlayBoard playBoardImpl = new PlayBoardImpl();
		when(playBoard.move(anyInt(), anyInt())).thenThrow(IllegalArgumentException.class);

		when(scanner.nextLine()).thenReturn("\n","move 2 2", "quit");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
//		verify(playBoard, times(1)).clearBoard();
		verify(playBoard, times(1)).move(1, 1);
		verify(playBoard, times(8)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@Test
	public void testRun_MoveThrowsIllegalMoveExceptionQuit_void() throws Exception {
		// given
		when(playBoard.move(anyInt(), anyInt())).thenThrow(new IllegalMoveException(Constants.MOVE_NOT_ALLOWED));

		when(scanner.nextLine()).thenReturn("\n","move 2 2", "quit");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
//		verify(playBoard, times(1)).clearBoard();
		verify(playBoard, times(1)).move(1, 1);
		verify(playBoard, times(8)).getCurrentPlayer();
		verify(playBoard, times(2)).isGameOver();
	}

	@Test
	public void testRun_MoveThrowsIllegalMoveException2Quit_void() throws Exception {
		// given
		when(playBoard.move(anyInt(), anyInt())).thenThrow(new IllegalMoveException(Constants.getGameOver()));

		when(scanner.nextLine()).thenReturn("\n","move 2 2", "quit");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
//		verify(playBoard, times(1)).clearBoard();
		verify(playBoard, times(1)).move(1, 1);
		verify(playBoard, times(8)).getCurrentPlayer();
		verify(playBoard, times(2)).isGameOver();
	}

	@Test
	public void testRun_MQ_void() throws Exception {
		// given
		PlayBoard playBoardImpl = new PlayBoardImpl();
		when(playBoard.move(anyInt(), anyInt())).thenReturn(playBoardImpl);

		when(scanner.nextLine()).thenReturn("\n","m 2 2", "q");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(1)).move(1, 1);
		verify(playBoard, times(9)).getCurrentPlayer();
		verify(playBoard, times(3)).isGameOver();
	}

	@Test
	public void testRun_MQ_CoordinateNotValid_void() throws Exception {
		// given
		PlayBoard playBoardImpl = new PlayBoardImpl();
		when(playBoard.move(anyInt(), anyInt())).thenReturn(playBoardImpl);

		when(scanner.nextLine()).thenReturn("\n","m a 2", "q");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(5)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@Test
	public void testRun_MQ_LessThan3MoreThan3Tokens_void() throws Exception {
		// given
		PlayBoard playBoardImpl = new PlayBoardImpl();
		when(playBoard.move(anyInt(), anyInt())).thenReturn(playBoardImpl);

		when(scanner.nextLine()).thenReturn("\n","m a", "q");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(5)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@Test
	public void testRun_MQ_NegativCoordinate_void() throws Exception {
		// given
		PlayBoard playBoardImpl = new PlayBoardImpl();
		when(playBoard.move(anyInt(), anyInt())).thenReturn(playBoardImpl);

		when(scanner.nextLine()).thenReturn("\n","m -1 -1", "q");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(9)).getCurrentPlayer();
		verify(playBoard, times(3)).isGameOver();
	}

	@Test
	public void testRun_MQ_NumberFormatException_void() throws Exception {
		// given
		PlayBoard playBoardImpl = new PlayBoardImpl();
		when(playBoard.move(anyInt(), anyInt())).thenReturn(playBoardImpl);

		when(scanner.nextLine()).thenReturn("\n","m 123.23 -1", "q");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(5)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@ParameterizedTest
	@CsvSource(value={"PLAYER1, PLAYER2", "PLAYER2, PLAYER1"})
	public void testRun_MQGameOverWinnerPlayer1_void(Player winner, Player nextFirstPlayer) throws Exception {
		// given
		PlayBoard playBoardImpl = new PlayBoardImpl();
		when(playBoard.move(anyInt(), anyInt())).thenReturn(playBoardImpl);

		when(scanner.nextLine()).thenReturn("\n","m 2 2", "q");
		when(playBoard.isGameOver()).thenReturn(true);
		when(playBoard.getWinner()).thenReturn(winner);
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(1)).clearBoard();
		verify(playBoard, times(1)).getWinner();
		verify(playBoard, times(3)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
		verify(playBoard, times(1)).setFirstPlayer(nextFirstPlayer);
	}

	@Test
	public void testRun_MQGameOverTie_void() throws Exception {
		// given
		PlayBoard playBoardImpl = new PlayBoardImpl();
		when(playBoard.move(anyInt(), anyInt())).thenReturn(playBoardImpl);

		when(scanner.nextLine()).thenReturn("\n","m 2 2", "q");
		when(playBoard.isGameOver()).thenReturn(true);
		when(playBoard.getWinner()).thenReturn(null);
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(1)).clearBoard();
		verify(playBoard, times(1)).getWinner();
		verify(playBoard, times(3)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
		verify(playBoard, times(1)).setFirstPlayer(Player.PLAYER2);
	}

	@Test
	public void testRun_EmptyQuit_void() throws Exception {
		// given
		PlayBoard playBoardImpl = new PlayBoardImpl();
		when(playBoard.move(anyInt(), anyInt())).thenReturn(playBoardImpl);

		when(scanner.nextLine()).thenReturn("\n","  ", "q");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(3)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@Test
	public void testRun_WrongCommand_void() throws Exception {
		// given
		when(scanner.nextLine()).thenReturn("\n","x", "q");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
//		verify(playBoard, times(1)).clearBoard();
		verify(playBoard, times(5)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@Test
	void testRun_WrongCommandOfOneByNew_void() throws Exception {
		// given
		when(scanner.nextLine()).thenReturn("\n","n 1", "q");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(5)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@ParameterizedTest
	@CsvSource(value={"h 2","p 0"})
	void testRun_WrongCommandOfOneByHelp_void(String command1) throws Exception {
		// given
		when(scanner.nextLine()).thenReturn("\n","h 1", "q");
		when(playBoard.getFirstPlayer()).thenReturn(Player.PLAYER1);
		when(playBoard.getCurrentPlayer()).thenReturn(Player.PLAYER1);
		doNothing().when(playBoard).setCurrentPlayer(any(Player.class));
		doNothing().when(playBoard).setFirstPlayer(any(Player.class));

		// when
		tictactoeApplication.run();

		// then
		verify(playBoard, times(6)).getCurrentPlayer();
		verify(playBoard, times(1)).isGameOver();
	}

	@Test
	void testConstructor_void() throws Exception {
		// given

		// when
		TictactoeApplication tictactoeApplication1 = new TictactoeApplication(playBoard);

		// then
		assertNotNull(tictactoeApplication1);
	}
}
