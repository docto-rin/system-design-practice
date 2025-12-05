package io.github.docto_rin.tic_tac_toe;

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

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.display();
            Player player = players[currentPlayerIndex];
            System.out.printf("It's %s (%s)'s turn next.%n",
                player.getName(),
                player.getSymbol()
            );
            
            System.out.println("Enter coordinate:");
            String input;
            Optional<int[]> coordinate;
            do {
                input = scanner.nextLine();
                
                coordinate = parseCoordinate(input);
                if (coordinate.isEmpty()) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }

                boolean moveResult = board.makeMove(
                    coordinate.get()[0], coordinate.get()[1], player.getSymbol()
                );
                if (!moveResult) {
                    System.out.printf("The cell (%s) is already occupied. Try again.%n", input);
                    continue;
                }

                break;
            } while (true);
            
            if (board.checkWinner().isPresent()) {
                board.display();
                System.out.printf("Game! The winner is %s (%s).%n",
                    player.getName(),
                    player.getSymbol()
                );
                break;
            }

            if (board.isFull()) {
                board.display();
                System.out.println("The board is full. It's a draw.");
                break;
            }

            currentPlayerIndex = 1 - currentPlayerIndex;
        }

        scanner.close();
    }

    Optional<int[]> parseCoordinate(String input) {
        if (input.length() != 2) {
            return Optional.empty();
        }

        char rowChar = input.charAt(0);
        char colChar = input.charAt(1);
        
        int rowIndex = rowChar - '1';
        int colIndex = colChar - 'a';

        if (rowIndex < 0 || rowIndex >= 3 || colIndex < 0 || colIndex >= 3) {
            return Optional.empty();
        }

        return Optional.of(new int[]{rowIndex, colIndex});
    }
}