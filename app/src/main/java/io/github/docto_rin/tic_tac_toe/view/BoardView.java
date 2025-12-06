package io.github.docto_rin.tic_tac_toe.view;

import io.github.docto_rin.tic_tac_toe.Board;
import io.github.docto_rin.tic_tac_toe.Player;

/**
 * Interface for displaying the game board and handling user input.
 *
 * <p>This abstraction allows different implementations (console, GUI, web)
 * to be used interchangeably while keeping the game logic independent of
 * I/O details.
 */
public interface BoardView {
    /**
     * Displays the current state of the board.
     *
     * @param board the board to display
     */
    void display(Board board);

    /**
     * Prompts the current player for their next move and validates the input.
     *
     * <p>This method handles all user interaction including:
     * <ul>
     *   <li>Displaying whose turn it is</li>
     *   <li>Prompting for input</li>
     *   <li>Parsing and validating coordinates</li>
     *   <li>Checking if the position is available</li>
     *   <li>Re-prompting on invalid input</li>
     * </ul>
     *
     * @param board the current board state
     * @param currentPlayer the player whose turn it is
     * @return an array containing the validated row and column indices [row, col]
     */
    int[] getNextMove(Board board, Player currentPlayer);

    /**
     * Displays the final board and announces the winner.
     *
     * @param board the final board state
     * @param winner the winning player
     */
    void displayWinner(Board board, Player winner);

    /**
     * Displays the final board and announces a draw.
     *
     * @param board the final board state
     */
    void displayDraw(Board board);
}
