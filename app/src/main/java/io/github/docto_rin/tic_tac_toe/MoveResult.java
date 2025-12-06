package io.github.docto_rin.tic_tac_toe;

/**
 * Represents the result of attempting to make a move on the board.
 */
public enum MoveResult {
    /**
     * The move was successful.
     */
    SUCCESS("") {
        @Override
        public String getErrorMessage(String input) {
            return message;
        }
    },

    /**
     * The move failed because the coordinates are out of bounds.
     */
    OUT_OF_BOUNDS("Out of bounds") {
        @Override
        public String getErrorMessage(String input) {
            return message;
        }
    },

    /**
     * The move failed because the cell is already occupied.
     * Includes the input coordinate in the error message.
     */
    CELL_OCCUPIED("The cell (%s) is already occupied") {
        @Override
        public String getErrorMessage(String input) {
            return String.format(message, input);
        }
    };

    protected final String message;

    MoveResult(String message) {
        this.message = message;
    }

    /**
     * Returns an appropriate error message for this result.
     * Each enum value provides its own implementation.
     *
     * @param input the user's input coordinate string
     * @return the error message, or empty string for SUCCESS
     */
    public abstract String getErrorMessage(String input);
}
