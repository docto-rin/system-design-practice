package io.github.docto_rin.tic_tac_toe;

import java.util.Optional;

/**
 * Represents a Tic Tac Toe game board.
 *
 * <p>Manages the game grid state, validates moves, checks for winners,
 * and provides display functionality. Uses an emptyCount cache for
 * efficient board fullness checking.
 *
 * <p>Supports configurable board sizes (default 3x3).
 */
public class Board {
    private final int size;
    private Symbol[][] grid;
    private int emptyCount;

    public Board() {
        this(3);
    }

    public Board(int size) {
        this.size = size;
        grid = new Symbol[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = Symbol.EMPTY;
            }
        }

        emptyCount = size * size;
    }

    public int getSize() {
        return size;
    }

    /**
     * Validates whether a coordinate is valid for placing a symbol.
     *
     * @param row the row index (0 to size-1)
     * @param col the column index (0 to size-1)
     * @return {@link MoveResult#SUCCESS} if the coordinate is valid,
     *         {@link MoveResult#OUT_OF_BOUNDS} if the position is out of bounds,
     *         {@link MoveResult#CELL_OCCUPIED} if the cell is already occupied
     */
    public MoveResult validateCoordinate(int row, int col) {
        if (!(0 <= row && row < size && 0 <= col && col < size)) {
            return MoveResult.OUT_OF_BOUNDS;
        }
        if (grid[row][col] != Symbol.EMPTY) {
            return MoveResult.CELL_OCCUPIED;
        }
        return MoveResult.SUCCESS;
    }

    /**
     * Places a symbol at the specified position on the board.
     * Assumes the coordinate has been validated.
     *
     * @param row the row index (0 to size-1)
     * @param col the column index (0 to size-1)
     * @param symbol the symbol to place (X or O)
     */
    public void putSymbol(int row, int col, Symbol symbol) {
        grid[row][col] = symbol;
        emptyCount--;
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
        for (int row = 0; row < size; row++) {
            Symbol[] rowSymbols = new Symbol[size];
            for (int col = 0; col < size; col++) {
                rowSymbols[col] = grid[row][col];
            }
            Optional<Symbol> result = checkLineWinner(rowSymbols);
            if (result.isPresent()) {
                return result;
            }
        }

        // check col
        for (int col = 0; col < size; col++) {
            Symbol[] colSymbols = new Symbol[size];
            for (int row = 0; row < size; row++) {
                colSymbols[row] = grid[row][col];
            }
            Optional<Symbol> result = checkLineWinner(colSymbols);
            if (result.isPresent()) {
                return result;
            }
        }

        // check diag (top-left to bottom-right)
        Symbol[] diag1 = new Symbol[size];
        for (int i = 0; i < size; i++) {
            diag1[i] = grid[i][i];
        }
        Optional<Symbol> result = checkLineWinner(diag1);
        if (result.isPresent()) {
            return result;
        }

        // check diag (top-right to bottom-left)
        Symbol[] diag2 = new Symbol[size];
        for (int i = 0; i < size; i++) {
            diag2[i] = grid[i][size - 1 - i];
        }
        result = checkLineWinner(diag2);
        if (result.isPresent()) {
            return result;
        }

        return Optional.empty();
    }

    private Optional<Symbol> checkLineWinner(Symbol... symbols) {
        if (symbols.length != size) {
            return Optional.empty();
        }

        Symbol firstSymbol = symbols[0];
        if (firstSymbol == Symbol.EMPTY) {
            return Optional.empty();
        }

        for (int i = 1; i < size; i++) {
            if (symbols[i] != firstSymbol) {
                return Optional.empty();
            }
        }
        return Optional.of(firstSymbol);
    }

    public Symbol getSymbol(int row, int col) {
        return grid[row][col];
    }
}
