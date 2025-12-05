package io.github.docto_rin.tic_tac_toe;

import java.util.Optional;

/**
 * Represents a 3x3 Tic Tac Toe game board.
 *
 * <p>Manages the game grid state, validates moves, checks for winners,
 * and provides display functionality. Uses an emptyCount cache for
 * efficient board fullness checking.
 */
public class Board {
    private static final int BOARD_SIZE = 3;
    private Symbol[][] grid;
    private int emptyCount;

    public Board() {
        grid = new Symbol[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                grid[i][j] = Symbol.EMPTY;
            }
        }

        emptyCount = BOARD_SIZE * BOARD_SIZE;
    }

    /**
     * Places a symbol at the specified position on the board.
     *
     * @param row the row index (0-2)
     * @param col the column index (0-2)
     * @param symbol the symbol to place (X or O)
     * @return {@code true} if the move was successful, {@code false} if the
     *         position is out of bounds or already occupied
     */
    public boolean makeMove(int row, int col, Symbol symbol) {
        if (!(0 <= row && row < BOARD_SIZE && 0 <= col && col < BOARD_SIZE)) {
            return false;
        }
        if (grid[row][col] == Symbol.EMPTY) {
            grid[row][col] = symbol;
            emptyCount--;
            return true;
        }
        return false;
    }

    /**
     * Checks if the board is full (all cells are occupied).
     *
     * @return {@code true} if all cells are occupied, {@code false} otherwise
     */
    public boolean isFull() {
        return emptyCount == 0;
    }

    /**
     * Checks for a winner by examining all rows, columns, and diagonals.
     *
     * @return an {@link Optional} containing the winning symbol (X or O) if found,
     *         or {@link Optional#empty()} if there is no winner
     */
    public Optional<Symbol> checkWinner() {
        // check row
        for (int row = 0; row < BOARD_SIZE; row++) {
            Symbol[] rowSymbols = new Symbol[BOARD_SIZE];
            for (int col = 0; col < BOARD_SIZE; col++) {
                rowSymbols[col] = grid[row][col];
            }
            Optional<Symbol> result = checkLine(rowSymbols);
            if (result.isPresent()) {
                return result;
            }
        }

        // check col
        for (int col = 0; col < BOARD_SIZE; col++) {
            Symbol[] colSymbols = new Symbol[BOARD_SIZE];
            for (int row = 0; row < BOARD_SIZE; row++) {
                colSymbols[row] = grid[row][col];
            }
            Optional<Symbol> result = checkLine(colSymbols);
            if (result.isPresent()) {
                return result;
            }
        }

        // check diag (top-left to bottom-right)
        Symbol[] diag1 = new Symbol[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            diag1[i] = grid[i][i];
        }
        Optional<Symbol> result = checkLine(diag1);
        if (result.isPresent()) {
            return result;
        }

        // check diag (top-right to bottom-left)
        Symbol[] diag2 = new Symbol[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            diag2[i] = grid[i][BOARD_SIZE - 1 - i];
        }
        result = checkLine(diag2);
        if (result.isPresent()) {
            return result;
        }

        return Optional.empty();
    }

    private Optional<Symbol> checkLine(Symbol... symbols) {
        if (symbols.length != BOARD_SIZE) {
            return Optional.empty();
        }

        Symbol firstSymbol = symbols[0];
        if (firstSymbol == Symbol.EMPTY) {
            return Optional.empty();
        }

        for (int i = 1; i < BOARD_SIZE; i++) {
            if (symbols[i] != firstSymbol) {
                return Optional.empty();
            }
        }
        return Optional.of(firstSymbol);
    }

    public String getDisplayString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(buildColumnHeader());
        for (int row = 0; row < BOARD_SIZE; row++) {
            stringBuilder.append(buildRow(row));
            if (row < BOARD_SIZE - 1) {
                stringBuilder.append(buildSeparator());
            }
        }
        return stringBuilder.toString();
    }

    private String buildColumnHeader() {
        StringBuilder stringBuilder = new StringBuilder("   ");
        for (int col = 0; col < BOARD_SIZE; col++) {
            stringBuilder.append((char)('a' + col));
            if (col < BOARD_SIZE - 1) {
                stringBuilder.append("   ");
            }
        }
        return stringBuilder.append(" \n").toString();
    }

    private String buildRow(int row) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(row + 1).append("  ");
        for (int col = 0; col < BOARD_SIZE; col++) {
            stringBuilder.append(grid[row][col]);
            if (col < BOARD_SIZE - 1) {
                stringBuilder.append(" | ");
            }
        }
        return stringBuilder.append(" \n").toString();
    }

    private String buildSeparator() {
        return "  -----------\n";
    }

    /**
     * Parses user input coordinate string to board indices.
     *
     * @param input the coordinate string (e.g., "2c" for row 2, column c)
     * @return an {@link Optional} containing the row and column indices [row, col]
     *         if valid, or {@link Optional#empty()} if invalid
     */
    public Optional<int[]> parseCoordinate(String input) {
        if (input.length() != 2) {
            return Optional.empty();
        }

        char rowChar = input.charAt(0);
        char colChar = input.charAt(1);

        int rowIndex = rowChar - '1';
        int colIndex = colChar - 'a';

        if (rowIndex < 0 || rowIndex >= BOARD_SIZE || colIndex < 0 || colIndex >= BOARD_SIZE) {
            return Optional.empty();
        }

        return Optional.of(new int[]{rowIndex, colIndex});
    }

    Symbol getSymbol(int row, int col) {
        return grid[row][col];
    }
}
