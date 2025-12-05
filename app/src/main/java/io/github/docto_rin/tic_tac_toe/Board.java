package io.github.docto_rin.tic_tac_toe;

import java.util.Optional;

public class Board {
    private Symbol[][] grid;
    private int emptyCount;

    public Board() {
        grid = new Symbol[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = Symbol.EMPTY;
            }
        }

        emptyCount = 3 * 3;
    }

    Symbol getSymbol(int row, int col) {
        return grid[row][col];
    }

    public boolean makeMove(int row, int col, Symbol symbol) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false;
        }
        if (grid[row][col] == Symbol.EMPTY) {
            grid[row][col] = symbol;
            emptyCount--;
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return emptyCount == 0;
    }

    public Optional<Symbol> checkWinner() {
        // check row
        for (int row = 0; row < 3; row++) {
            Optional<Symbol> result = checkLine(
                grid[row][0], grid[row][1], grid[row][2]
            );
            if (result.isPresent()) {
                return result;
            }
        }

        // check col
        for (int col = 0; col < 3; col++) {
            Optional<Symbol> result = checkLine(
                grid[0][col], grid[1][col], grid[2][col]
            );
            if (result.isPresent()) {
                return result;
            }
        }

        // check diag
        Optional<Symbol> result = checkLine(
            grid[0][0], grid[1][1], grid[2][2]
        );
        if (result.isPresent()) {
            return result;
        }
        result = checkLine(
            grid[0][2], grid[1][1], grid[2][0]
        );
        if (result.isPresent()) {
            return result;
        }

        return Optional.empty();
    }

    private Optional<Symbol> checkLine(Symbol... symbols) {
        if (symbols.length != 3) {
            return Optional.empty();
        }

        Symbol firstSymbol = symbols[0];
        if (firstSymbol == Symbol.EMPTY) {
            return Optional.empty();
        }

        for (int i = 1; i < 3; i++) {
            if (symbols[i] != firstSymbol) {
                return Optional.empty();
            }
        }
        return Optional.of(firstSymbol);
    }

    public void display() {
        System.out.print(getDisplayString());
    }

    String getDisplayString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("   a   b   c \n");

        for (int row = 0; row < 3; row++) {
            stringBuilder.append(String.format("%d  %s | %s | %s %n",
                row + 1,
                grid[row][0],
                grid[row][1],
                grid[row][2]
            ));
            if (row < 2) {
                stringBuilder.append("  -----------\n");
            }
        }

        return stringBuilder.toString();
    }    
}
