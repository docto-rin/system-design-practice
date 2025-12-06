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
    void testValidateCoordinateSucceedsWhenCellIsEmpty() {
        Board board = new Board();
        MoveResult moveResult = board.validateCoordinate(0, 0);
        assertEquals(MoveResult.SUCCESS, moveResult);
    }

    @Test
    void testValidateCoordinateFailsWhenCellIsOccupied() {
        Board board = new Board();
        board.putSymbol(0, 0, Symbol.X);
        MoveResult moveResult = board.validateCoordinate(0, 0);
        assertEquals(MoveResult.CELL_OCCUPIED, moveResult);
        assertEquals(Symbol.X, board.getSymbol(0, 0));
    }

    @Test
    void testValidateCoordinateOutOfBounds() {
        Board board = new Board();
        assertEquals(MoveResult.OUT_OF_BOUNDS, board.validateCoordinate(-1, 0));
        assertEquals(MoveResult.OUT_OF_BOUNDS, board.validateCoordinate(0, -1));
        assertEquals(MoveResult.OUT_OF_BOUNDS, board.validateCoordinate(3, 0));
        assertEquals(MoveResult.OUT_OF_BOUNDS, board.validateCoordinate(0, 3));
    }

    @Test
    void testIsFull() {
        Board board = new Board();
        assertFalse(board.isFull());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.putSymbol(i, j, Symbol.X);
            }
        }
        assertTrue(board.isFull());
    }

    @Test
    void testCheckWinnerRow() {
        Board board1 = new Board();
        board1.putSymbol(0, 0, Symbol.X);
        board1.putSymbol(0, 1, Symbol.X);
        board1.putSymbol(0, 2, Symbol.X);
        assertEquals(Optional.of(Symbol.X), board1.checkWinner());

        Board board2 = new Board();
        board2.putSymbol(1, 0, Symbol.O);
        board2.putSymbol(1, 1, Symbol.O);
        board2.putSymbol(1, 2, Symbol.O);
        assertEquals(Optional.of(Symbol.O), board2.checkWinner());

        Board board3 = new Board();
        board3.putSymbol(2, 0, Symbol.X);
        board3.putSymbol(2, 1, Symbol.X);
        board3.putSymbol(2, 2, Symbol.X);
        assertEquals(Optional.of(Symbol.X), board3.checkWinner());
    }

    @Test
    void testCheckWinnerColumn() {
        Board board1 = new Board();
        board1.putSymbol(0, 0, Symbol.X);
        board1.putSymbol(1, 0, Symbol.X);
        board1.putSymbol(2, 0, Symbol.X);
        assertEquals(Optional.of(Symbol.X), board1.checkWinner());

        Board board2 = new Board();
        board2.putSymbol(0, 1, Symbol.O);
        board2.putSymbol(1, 1, Symbol.O);
        board2.putSymbol(2, 1, Symbol.O);
        assertEquals(Optional.of(Symbol.O), board2.checkWinner());

        Board board3 = new Board();
        board3.putSymbol(0, 2, Symbol.X);
        board3.putSymbol(1, 2, Symbol.X);
        board3.putSymbol(2, 2, Symbol.X);
        assertEquals(Optional.of(Symbol.X), board3.checkWinner());
    }

    @Test
    void testCheckWinnerDiagonal() {
        // Top-left to bottom-right
        Board board1 = new Board();
        board1.putSymbol(0, 0, Symbol.X);
        board1.putSymbol(1, 1, Symbol.X);
        board1.putSymbol(2, 2, Symbol.X);
        assertEquals(Optional.of(Symbol.X), board1.checkWinner());

        // Top-right to bottom-left
        Board board2 = new Board();
        board2.putSymbol(0, 2, Symbol.O);
        board2.putSymbol(1, 1, Symbol.O);
        board2.putSymbol(2, 0, Symbol.O);
        assertEquals(Optional.of(Symbol.O), board2.checkWinner());
    }

    @Test
    void testCheckWinnerNoWinner() {
        Board board = new Board();
        board.putSymbol(0, 0, Symbol.X);
        board.putSymbol(0, 1, Symbol.O);
        board.putSymbol(0, 2, Symbol.X);
        board.putSymbol(1, 0, Symbol.O);
        board.putSymbol(1, 1, Symbol.X);
        board.putSymbol(1, 2, Symbol.O);
        board.putSymbol(2, 0, Symbol.O);
        board.putSymbol(2, 1, Symbol.X);
        board.putSymbol(2, 2, Symbol.O);
        assertEquals(Optional.empty(), board.checkWinner());
    }
}
