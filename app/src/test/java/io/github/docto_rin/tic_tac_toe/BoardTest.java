package io.github.docto_rin.tic_tac_toe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

class BoardTest {
    @Test
    void testBoardIsInitializedWithEmptyCells() {
        Board board = new Board();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(Symbol.EMPTY, board.getSymbol(i, j));
            }
        }
    }

    @Test
    void testMakeMoveSucceedsWhenCellIsEmpty() {
        Board board = new Board();
        boolean moveResult = board.makeMove(0, 0, Symbol.X);
        assertTrue(moveResult);
        assertEquals(Symbol.X, board.getSymbol(0, 0));
    }

    @Test
    void testMakeMoveFailsWhenCellIsOccupied() {
        Board board = new Board();
        board.makeMove(0, 0, Symbol.X);
        boolean moveResult = board.makeMove(0, 0, Symbol.O);
        assertFalse(moveResult);
        assertEquals(Symbol.X, board.getSymbol(0, 0));
    }

    @Test
    void testMakeMoveOutOfBounds() {
        Board board = new Board();
        assertFalse(board.makeMove(-1, 0, Symbol.X));
        assertFalse(board.makeMove(0, -1, Symbol.X));
        assertFalse(board.makeMove(3, 0, Symbol.X));
        assertFalse(board.makeMove(0, 3, Symbol.X));
    }

    @Test
    void testIsFull() {
        Board board = new Board();
        assertFalse(board.isFull());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.makeMove(i, j, Symbol.X);
            }
        }
        assertTrue(board.isFull());
    }

    @Test
    void testCheckWinnerRow() {
        Board board1 = new Board();
        board1.makeMove(0, 0, Symbol.X);
        board1.makeMove(0, 1, Symbol.X);
        board1.makeMove(0, 2, Symbol.X);
        assertEquals(Optional.of(Symbol.X), board1.checkWinner());

        Board board2 = new Board();
        board2.makeMove(1, 0, Symbol.O);
        board2.makeMove(1, 1, Symbol.O);
        board2.makeMove(1, 2, Symbol.O);
        assertEquals(Optional.of(Symbol.O), board2.checkWinner());

        Board board3 = new Board();
        board3.makeMove(2, 0, Symbol.X);
        board3.makeMove(2, 1, Symbol.X);
        board3.makeMove(2, 2, Symbol.X);
        assertEquals(Optional.of(Symbol.X), board3.checkWinner());
    }

    @Test
    void testCheckWinnerColumn() {
        Board board1 = new Board();
        board1.makeMove(0, 0, Symbol.X);
        board1.makeMove(1, 0, Symbol.X);
        board1.makeMove(2, 0, Symbol.X);
        assertEquals(Optional.of(Symbol.X), board1.checkWinner());

        Board board2 = new Board();
        board2.makeMove(0, 1, Symbol.O);
        board2.makeMove(1, 1, Symbol.O);
        board2.makeMove(2, 1, Symbol.O);
        assertEquals(Optional.of(Symbol.O), board2.checkWinner());

        Board board3 = new Board();
        board3.makeMove(0, 2, Symbol.X);
        board3.makeMove(1, 2, Symbol.X);
        board3.makeMove(2, 2, Symbol.X);
        assertEquals(Optional.of(Symbol.X), board3.checkWinner());
    }

    @Test
    void testCheckWinnerDiagonal() {
        // Top-left to bottom-right
        Board board1 = new Board();
        board1.makeMove(0, 0, Symbol.X);
        board1.makeMove(1, 1, Symbol.X);
        board1.makeMove(2, 2, Symbol.X);
        assertEquals(Optional.of(Symbol.X), board1.checkWinner());

        // Top-right to bottom-left
        Board board2 = new Board();
        board2.makeMove(0, 2, Symbol.O);
        board2.makeMove(1, 1, Symbol.O);
        board2.makeMove(2, 0, Symbol.O);
        assertEquals(Optional.of(Symbol.O), board2.checkWinner());
    }

    @Test
    void testCheckWinnerNoWinner() {
        Board board = new Board();
        board.makeMove(0, 0, Symbol.X);
        board.makeMove(0, 1, Symbol.O);
        board.makeMove(0, 2, Symbol.X);
        board.makeMove(1, 0, Symbol.O);
        board.makeMove(1, 1, Symbol.X);
        board.makeMove(1, 2, Symbol.O);
        board.makeMove(2, 0, Symbol.O);
        board.makeMove(2, 1, Symbol.X);
        board.makeMove(2, 2, Symbol.O);
        assertEquals(Optional.empty(), board.checkWinner());
    }

    @Test
    void testGetDisplayString() {
        Board board = new Board();
        board.makeMove(0, 0, Symbol.X);
        board.makeMove(0, 1, Symbol.O);
        board.makeMove(1, 1, Symbol.X);
        board.makeMove(2, 2, Symbol.O);

        String display = board.getDisplayString();
        String[] lines = display.split("\\r?\\n");

        assertEquals("   a   b   c ", lines[0]);
        assertEquals("1  X | O |   ", lines[1]);
        assertEquals("  -----------", lines[2]);
        assertEquals("2    | X |   ", lines[3]);
        assertEquals("  -----------", lines[4]);
        assertEquals("3    |   | O ", lines[5]);
    }

    @Test
    void testGetDisplayStringContainsSymbol() {
        Board board = new Board();
        board.makeMove(0, 0, Symbol.X);

        String display = board.getDisplayString();
        assertNotNull(display);
        assertTrue(display.contains("X"));
    }

    @Test
    void testParseCoordinate() {
        Board board = new Board();

        Optional<int[]> coord1 = board.parseCoordinate("1a");
        assertTrue(coord1.isPresent());
        assertArrayEquals(new int[]{0, 0}, coord1.get());

        Optional<int[]> coord2 = board.parseCoordinate("3b");
        assertTrue(coord2.isPresent());
        assertArrayEquals(new int[]{2, 1}, coord2.get());

        Optional<int[]> coord3 = board.parseCoordinate("4a");
        assertTrue(coord3.isEmpty());

        Optional<int[]> coord4 = board.parseCoordinate("");
        assertTrue(coord4.isEmpty());
    }
}
