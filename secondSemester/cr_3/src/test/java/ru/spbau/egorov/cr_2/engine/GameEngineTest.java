package ru.spbau.egorov.cr_2.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    @Test
    void createGameEngineCorrectGameFieldSize() throws InvalidGameFieldSizeException {
        GameEngine engine = new GameEngine(8);
        assertNotNull(engine);
    }

    @Test
    void createGameEngineInvalidGameFieldSize() {
        assertThrows(InvalidGameFieldSizeException.class, () -> new GameEngine(7));
        assertThrows(InvalidGameFieldSizeException.class, () -> new GameEngine(-2));
        assertThrows(InvalidGameFieldSizeException.class, () -> new GameEngine(18));
    }

    @Test
    void isEnded() throws InvalidGameFieldSizeException {
        GameEngine engine = new GameEngine(16);
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
                engine.setInactive(i, j);
        assertTrue(engine.isEnded());
    }

    @Test
    void checkAllNumbersPresent() throws InvalidGameFieldSizeException {
        GameEngine engine = new GameEngine(16);
        int[] count = new int[16 * 8];
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
                count[engine.getCellNumber(i, j)]++;
        for (int i = 0; i < 16 * 8; i++)
            assertEquals(2, count[i]);
    }

}