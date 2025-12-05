package io.github.docto_rin.tic_tac_toe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class GameTest {
    @Test
    void testPlayerXWinsRowVictory() {
        // X wins first row: X:1a, O:2a, X:1b, O:2b, X:1c
        String input = "1a\n2a\n1b\n2b\n1c\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Game game = new Game(
            new Player("Alice", Symbol.X),
            new Player("Bob", Symbol.O)
        );
        game.play(scanner, printer);

        String output = out.toString();
        assertTrue(output.contains("Game! The winner is Alice (X)."));
    }

    @Test
    void testPlayerOWinsColumnVictory() {
        // O wins column b: X:1a, O:1b, X:2a, O:2b, X:3c, O:3b
        String input = "1a\n1b\n2a\n2b\n3c\n3b\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Game game = new Game(
            new Player("Alice", Symbol.X),
            new Player("Bob", Symbol.O)
        );
        game.play(scanner, printer);

        String output = out.toString();
        assertTrue(output.contains("Game! The winner is Bob (O)."));
    }

    @Test
    void testPlayerXWinsDiagonalVictory() {
        // X wins diagonal: X:1a, O:1b, X:2b, O:2a, X:3c
        String input = "1a\n1b\n2b\n2a\n3c\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Game game = new Game(
            new Player("Alice", Symbol.X),
            new Player("Bob", Symbol.O)
        );
        game.play(scanner, printer);

        String output = out.toString();
        assertTrue(output.contains("Game! The winner is Alice (X)."));
    }

    @Test
    void testDrawGame() {
        // Draw: X|O|X, O|X|O, O|X|X
        String input = "1a\n1b\n1c\n2b\n2a\n2c\n3b\n3a\n3c\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Game game = new Game(
            new Player("Alice", Symbol.X),
            new Player("Bob", Symbol.O)
        );
        game.play(scanner, printer);

        String output = out.toString();
        assertTrue(output.contains("The board is full. It's a draw."));
    }

    @Test
    void testInvalidInputRejected() {
        // Invalid input "99" followed by valid game
        String input = "99\n1a\n1b\n2a\n2b\n3a\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Game game = new Game(
            new Player("Alice", Symbol.X),
            new Player("Bob", Symbol.O)
        );
        game.play(scanner, printer);

        String output = out.toString();
        assertTrue(output.contains("Invalid input. Try again."));
        assertTrue(output.contains("Game! The winner is Alice (X)."));
    }

    @Test
    void testOccupiedCellRejected() {
        // X takes 1a, O tries 1a (rejected), O takes 1b
        String input = "1a\n1a\n1b\n2a\n2b\n3a\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Game game = new Game(
            new Player("Alice", Symbol.X),
            new Player("Bob", Symbol.O)
        );
        game.play(scanner, printer);

        String output = out.toString();
        assertTrue(output.contains("The cell (1a) is already occupied. Try again."));
        assertTrue(output.contains("Game! The winner is Alice (X)."));
    }

    @Test
    void testMultipleInvalidInputsHandled() {
        // Multiple invalid inputs before valid moves
        String input = "99\nzz\n\n1a\n1b\n2a\n2b\n3a\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Game game = new Game(
            new Player("Alice", Symbol.X),
            new Player("Bob", Symbol.O)
        );
        game.play(scanner, printer);

        String output = out.toString();
        // Should have 3 "Invalid input" messages
        int count = 0;
        int index = 0;
        while ((index = output.indexOf("Invalid input", index)) != -1) {
            count++;
            index += "Invalid input".length();
        }
        assertTrue(count >= 3);
        assertTrue(output.contains("Game! The winner is Alice (X)."));
    }

    @Test
    void testPlayerTurnAlternates() {
        // Verify turns alternate between players
        String input = "1a\n2a\n1b\n2b\n1c\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Game game = new Game(
            new Player("Alice", Symbol.X),
            new Player("Bob", Symbol.O)
        );
        game.play(scanner, printer);

        String output = out.toString();
        assertTrue(output.contains("It's Alice (X)'s turn next."));
        assertTrue(output.contains("It's Bob (O)'s turn next."));
    }

    @Test
    void testBoardDisplayAfterEachMove() {
        // Verify board is displayed multiple times (complete game)
        String input = "1a\n2a\n1b\n2b\n1c\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Game game = new Game(
            new Player("Alice", Symbol.X),
            new Player("Bob", Symbol.O)
        );
        game.play(scanner, printer);

        String output = out.toString();
        // Board header should appear multiple times (at least for each turn + final)
        int count = 0;
        int index = 0;
        while ((index = output.indexOf("a   b   c", index)) != -1) {
            count++;
            index += "a   b   c".length();
        }
        assertTrue(count >= 5); // At least 5 turns + final display
    }

    @Test
    void testWinnerAnnouncementFormat() {
        // Verify exact winner announcement format
        String input = "1a\n2a\n1b\n2b\n1c\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);

        Game game = new Game(
            new Player("Alice", Symbol.X),
            new Player("Bob", Symbol.O)
        );
        game.play(scanner, printer);

        String output = out.toString();
        assertTrue(output.contains("Game! The winner is Alice (X)."));
        // Verify it doesn't contain "Bob wins"
        assertFalse(output.contains("Game! The winner is Bob"));
    }
}
