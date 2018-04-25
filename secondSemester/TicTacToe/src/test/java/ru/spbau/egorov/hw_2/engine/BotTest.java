package ru.spbau.egorov.hw_2.engine;

import org.junit.jupiter.api.Test;
import ru.spbau.egorov.hw_2.controller.MainController;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {
    @Test
    void isWinSecondHorizontalLine() {
        EasyBot bot = new EasyBot();
        bot.clearGameField();
        for (int i = 0; i < 3; i++)
            bot.setCell(MainController.CellType.CROSS, 1, i);
        assertTrue(bot.isWin(MainController.CellType.CROSS));
    }

    @Test
    void isWinSecondVerticalLine() {
        EasyBot bot = new EasyBot();
        bot.clearGameField();
        for (int i = 0; i < 3; i++)
            bot.setCell(MainController.CellType.CROSS, i, 1);
        assertTrue(bot.isWin(MainController.CellType.CROSS));
    }

    @Test
    void isWinDiagonalLTR() {
        EasyBot bot = new EasyBot();
        bot.clearGameField();
        for (int i = 0; i < 3; i++)
            bot.setCell(MainController.CellType.CROSS, i, i);
        assertTrue(bot.isWin(MainController.CellType.CROSS));
    }

    @Test
    void isWinDiagonalRTL() {
        EasyBot bot = new EasyBot();
        bot.clearGameField();
        for (int i = 0; i < 3; i++)
            bot.setCell(MainController.CellType.CROSS, i, 2 - i);
        assertTrue(bot.isWin(MainController.CellType.CROSS));
    }


    @Test
    void clearGameField() {
        EasyBot bot = new EasyBot();
        for (int i = 0; i < 3; i++)
            bot.setCell(MainController.CellType.CROSS, i, 2 - i);
        bot.clearGameField();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                assertEquals(MainController.CellType.EMPTY, bot.getCell(i, j));
    }

}