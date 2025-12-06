package io.github.docto_rin.tic_tac_toe;

import io.github.docto_rin.tic_tac_toe.view.BoardView;

public class Game {
    private Board board;
    private Player[] players;
    private int currentPlayerIndex;
    private BoardView boardView;

    public Game(Player player1, Player player2, BoardView boardView) {
        board = new Board();
        players = new Player[]{player1, player2};
        currentPlayerIndex = 0;
        this.boardView = boardView;
    }

    public void play() {
        while (true) {
            boardView.display(board);
            Player player = players[currentPlayerIndex];

            int[] coordinate = boardView.getNextMove(board, player);
            board.putSymbol(coordinate[0], coordinate[1], player.getSymbol());

            if (board.checkWinner().isPresent()) {
                boardView.displayWinner(board, player);
                break;
            }

            if (board.isFull()) {
                boardView.displayDraw(board);
                break;
            }

            currentPlayerIndex ^= 1;
        }
    }
}