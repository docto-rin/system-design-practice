package io.github.docto_rin.tic_tac_toe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void testPlayerCreation() {
        Player player = new Player("Alice", Symbol.X);
        assertEquals("Alice", player.getName());
        assertEquals(Symbol.X, player.getSymbol());
    }
}
