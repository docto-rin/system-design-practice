package io.github.docto_rin.tic_tac_toe;

import io.github.docto_rin.tic_tac_toe.view.BoardView;
import io.github.docto_rin.tic_tac_toe.view.ConsoleBoardView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Running solution for: Tic Tac Toe");

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter player 1 name (Symbol: X): ");
            String player1Name = scanner.nextLine();

            System.out.print("Enter player 2 name (Symbol: O): ");
            String player2Name = scanner.nextLine();

            BoardView boardView = new ConsoleBoardView(scanner);
            Game game = new Game(
                new Player(player1Name, Symbol.X),
                new Player(player2Name, Symbol.O),
                boardView
            );
            game.play();
        }
    }
}
