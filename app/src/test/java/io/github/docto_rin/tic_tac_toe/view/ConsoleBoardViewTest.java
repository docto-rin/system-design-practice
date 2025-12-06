package io.github.docto_rin.tic_tac_toe.view;

import io.github.docto_rin.tic_tac_toe.Board;
import io.github.docto_rin.tic_tac_toe.MoveResult;
import io.github.docto_rin.tic_tac_toe.Player;
import io.github.docto_rin.tic_tac_toe.Symbol;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

class ConsoleBoardViewTest {
    @Test
    void testDisplayShowsBoardState() {
        Board board = new Board();
        board.putSymbol(0, 0, Symbol.X);
        board.putSymbol(0, 1, Symbol.O);
        board.putSymbol(1, 1, Symbol.X);
        board.putSymbol(2, 2, Symbol.O);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        Scanner scanner = new Scanner(System.in);

        ConsoleBoardView view = new ConsoleBoardView(scanner, printer);
        view.display(board);

        String display = out.toString();
        String[] lines = display.split("\\r?\\n");

        assertEquals("   a   b   c ", lines[0]);
        assertEquals("1  X | O |   ", lines[1]);
        assertEquals("  -----------", lines[2]);
        assertEquals("2    | X |   ", lines[3]);
        assertEquals("  -----------", lines[4]);
        assertEquals("3    |   | O ", lines[5]);
    }

    @Test
    void testDisplayContainsSymbols() {
        Board board = new Board();
        board.putSymbol(0, 0, Symbol.X);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        Scanner scanner = new Scanner(System.in);

        ConsoleBoardView view = new ConsoleBoardView(scanner, printer);
        view.display(board);

        String display = out.toString();
        assertNotNull(display);
        assertTrue(display.contains("X"));
    }

    @Test
    void testGetNextMoveWithValidInput() {
        String input = "1a\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Board board = new Board();
        Player player = new Player("Alice", Symbol.X);

        ConsoleBoardView view = new ConsoleBoardView(scanner, printer);
        int[] pos = view.getNextMove(board, player);

        assertArrayEquals(new int[]{0, 0}, pos);
        String output = out.toString();
        assertTrue(output.contains("It's Alice (X)'s turn next."));
    }

    @Test
    void testGetNextMoveRejectsInvalidInput() {
        String input = "99\n2b\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Board board = new Board();
        Player player = new Player("Alice", Symbol.X);

        ConsoleBoardView view = new ConsoleBoardView(scanner, printer);
        int[] pos = view.getNextMove(board, player);

        assertArrayEquals(new int[]{1, 1}, pos);
        String output = out.toString();
        // "99" has invalid format (second char is not a letter)
        assertTrue(output.contains("Invalid input format. Try again."));
    }

    @Test
    void testGetNextMoveRejectsOccupiedCell() {
        String input = "1a\n1b\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Board board = new Board();
        board.putSymbol(0, 0, Symbol.X);
        Player player = new Player("Bob", Symbol.O);

        ConsoleBoardView view = new ConsoleBoardView(scanner, printer);
        int[] pos = view.getNextMove(board, player);

        assertArrayEquals(new int[]{0, 1}, pos);
        String output = out.toString();
        assertTrue(output.contains("The cell (1a) is already occupied. Try again."));
    }

    @Test
    void testDisplayWinner() {
        Board board = new Board();
        board.putSymbol(0, 0, Symbol.X);
        board.putSymbol(0, 1, Symbol.X);
        board.putSymbol(0, 2, Symbol.X);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        Scanner scanner = new Scanner(System.in);

        ConsoleBoardView view = new ConsoleBoardView(scanner, printer);
        Player winner = new Player("Alice", Symbol.X);
        view.displayWinner(board, winner);

        String output = out.toString();
        assertTrue(output.contains("Game! The winner is Alice (X)."));
        assertTrue(output.contains("X"));
    }

    @Test
    void testDisplayDraw() {
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

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        Scanner scanner = new Scanner(System.in);

        ConsoleBoardView view = new ConsoleBoardView(scanner, printer);
        view.displayDraw(board);

        String output = out.toString();
        assertTrue(output.contains("The board is full. It's a draw."));
    }
}
