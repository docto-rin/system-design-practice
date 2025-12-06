package io.github.docto_rin.tic_tac_toe;

public enum Symbol {
    X,
    O,
    EMPTY;

    @Override
    public String toString() {
        return switch (this) {
            case X -> "X";
            case O -> "O";
            case EMPTY -> " ";
        };
    }
}
