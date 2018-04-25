package ru.spbau.egorov.hw_2.engine;

import org.junit.jupiter.api.Test;
import ru.spbau.egorov.hw_2.controller.MainController;

import static org.junit.jupiter.api.Assertions.*;
import static ru.spbau.egorov.hw_2.controller.MainController.CellType.NOUGHT;


class EasyBotTest {
    @Test
    void makeMoveOneEmptyCell() {
        EasyBot bot = new EasyBot();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                bot.setCell(MainController.CellType.CROSS, i, j);
        bot.setCell(MainController.CellType.EMPTY, 2, 2);
        bot.makeMove(MainController.CellType.CROSS);
        assertEquals(NOUGHT, bot.getCell(2, 2));
    }

    @Test
    void makeMoveNineMoves() {
        EasyBot bot = new EasyBot();
        bot.clearGameField();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                bot.makeMove(MainController.CellType.CROSS);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                assertEquals(NOUGHT, bot.getCell(i, j));
    }

}