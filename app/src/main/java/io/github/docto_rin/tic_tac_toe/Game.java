package io.github.docto_rin.tic_tac_toe;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Optional;

public class Game {
    private Board board;
    private Player[] players;
    private int currentPlayerIndex;

    public Game(Player player1, Player player2) {
        board = new Board();
        players = new Player[]{player1, player2};
        currentPlayerIndex = 0;
    }

    public void play(Scanner scanner, PrintStream output) {

        while (true) {
            output.print(board.getDisplayString());
            Player player = players[currentPlayerIndex];
            output.printf("It's %s (%s)'s turn next.%n",
                player.getName(),
                player.getSymbol()
            );

            output.println("Enter coordinate:");
            String inputStr;
            Optional<int[]> coordinate;
            do {
                inputStr = scanner.nextLine();

                coordinate = board.parseCoordinate(inputStr);
                if (coordinate.isEmpty()) {
                    output.println("Invalid input. Try again.");
                    continue;
                }

                boolean moveResult = board.makeMove(
                    coordinate.get()[0], coordinate.get()[1], player.getSymbol()
                );
                if (!moveResult) {
                    output.printf("The cell (%s) is already occupied. Try again.%n", inputStr);
                    continue;
                }

                break;
            } while (true);

            if (board.checkWinner().isPresent()) {
                output.print(board.getDisplayString());
                output.printf("Game! The winner is %s (%s).%n",
                    player.getName(),
                    player.getSymbol()
                );
                break;
            }

            if (board.isFull()) {
                output.print(board.getDisplayString());
                output.println("The board is full. It's a draw.");
                break;
            }

            currentPlayerIndex ^= 1;
        }
    }
}