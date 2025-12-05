package io.github.docto_rin.tic_tac_toe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

public class GameTest {
    @Test
    void testParseCoordinate() {
        Game game = new Game(
            new Player("Alice", Symbol.O),
            new Player("Bob", Symbol.X)
        );
        Optional<int[]> coord1 = game.parseCoordinate("1a");
        assertTrue(coord1.isPresent());
        assertArrayEquals(new int[]{0, 0}, coord1.get());

        Optional<int[]> coord2 = game.parseCoordinate("3b");
        assertTrue(coord2.isPresent());
        assertArrayEquals(new int[]{2, 1}, coord2.get());

        Optional<int[]> coord3 = game.parseCoordinate("4a");
        assertTrue(coord3.isEmpty());

        Optional<int[]> coord4 = game.parseCoordinate("");
        assertTrue(coord4.isEmpty());
    }
}
