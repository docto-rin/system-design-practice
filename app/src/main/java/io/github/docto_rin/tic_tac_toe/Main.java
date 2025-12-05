package io.github.docto_rin.tic_tac_toe;

public class Main {
    public static void main(String[] args) {
        System.out.println("Running solution for: Tic Tac Toe");

        Game game = new Game(
            new Player("Alice", Symbol.O),
            new Player("Bob", Symbol.X)
        );
        game.play();
    }
}
