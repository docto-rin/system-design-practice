package io.github.docto_rin.tic_tac_toe.view;

import io.github.docto_rin.tic_tac_toe.Board;
import io.github.docto_rin.tic_tac_toe.MoveResult;
import io.github.docto_rin.tic_tac_toe.Player;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

/**
 * Console-based implementation of BoardView.
 *
 * <p>Displays the board in a text grid format and prompts for moves
 * via standard input/output.
 */
public class ConsoleBoardView implements BoardView {
    private final Scanner scanner;
    private final PrintStream output;

    public ConsoleBoardView(Scanner scanner) {
        this(scanner, System.out);
    }

    public ConsoleBoardView(Scanner scanner, PrintStream output) {
        this.scanner = scanner;
        this.output = output;
    }

    @Override
    public void display(Board board) {
        output.print(buildDisplayString(board));
    }

    @Override
    public int[] getNextMove(Board board, Player currentPlayer) {
        output.printf(
                "It's %s (%s)'s turn next.%n",
                currentPlayer.getName(),
                currentPlayer.getSymbol()
        );
        output.println("Enter coordinate (example: 1a):");

        while (true) {
            String input = scanner.nextLine();
            Optional<int[]> parsedCoordinate = parseCoordinate(input);

            if (parsedCoordinate.isEmpty()) {
                output.println("Invalid input format. Try again.");
                continue;
            }

            int[] coordinate = parsedCoordinate.get();
            MoveResult validation = board.validateCoordinate(coordinate[0], coordinate[1]);

            if (validation == MoveResult.SUCCESS) {
                return coordinate;
            }

            output.println(validation.getErrorMessage(input) + ". Try again.");
        }
    }

    @Override
    public void displayWinner(Board board, Player winner) {
        display(board);
        output.printf("Game! The winner is %s (%s).%n",
                winner.getName(),
                winner.getSymbol()
        );
    }

    @Override
    public void displayDraw(Board board) {
        display(board);
        output.println("The board is full. It's a draw.");
    }

    private String buildDisplayString(Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(buildColumnHeader(board));
        for (int row = 0; row < board.getSize(); row++) {
            stringBuilder.append(buildRow(board, row));
            if (row < board.getSize() - 1) {
                stringBuilder.append(buildSeparator());
            }
        }
        return stringBuilder.toString();
    }

    private String buildColumnHeader(Board board) {
        StringBuilder stringBuilder = new StringBuilder("   ");
        for (int col = 0; col < board.getSize(); col++) {
            stringBuilder.append((char)('a' + col));
            if (col < board.getSize() - 1) {
                stringBuilder.append("   ");
            }
        }
        return stringBuilder.append(" \n").toString();
    }

    private String buildRow(Board board, int row) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(row + 1).append("  ");
        for (int col = 0; col < board.getSize(); col++) {
            stringBuilder.append(board.getSymbol(row, col));
            if (col < board.getSize() - 1) {
                stringBuilder.append(" | ");
            }
        }
        return stringBuilder.append(" \n").toString();
    }

    private String buildSeparator() {
        return "  -----------\n";
    }

    private Optional<int[]> parseCoordinate(String input) {
        if (input.length() != 2) {
            return Optional.empty();
        }

        char rowChar = input.charAt(0);
        char colChar = input.charAt(1);

        // Basic format check: first char should be a digit, second should be a letter
        if (!Character.isDigit(rowChar) || !Character.isLetter(colChar)) {
            return Optional.empty();
        }

        int rowIndex = rowChar - '1';
        int colIndex = Character.toLowerCase(colChar) - 'a';

        return Optional.of(new int[]{rowIndex, colIndex});
    }
}
