package ru.spbau.egorov.hw_2.engine;

import org.junit.jupiter.api.Test;
import ru.spbau.egorov.hw_2.controller.MainController;

import static org.junit.jupiter.api.Assertions.*;
import static ru.spbau.egorov.hw_2.controller.MainController.CellType.NOUGHT;

class HardBotTest {
    @Test
    void makeMoveSecondHorizontalLineLoss() {
        HardBot bot = new HardBot();
        bot.clearGameField();
        for (int i = 0; i < 2; i++)
            bot.setCell(MainController.CellType.CROSS, 1, i);
        bot.makeMove(MainController.CellType.CROSS);
        assertEquals(NOUGHT, bot.getCell(1, 2));
    }

    @Test
    void makeMoveRTLDiagonalLoss() {
        HardBot bot = new HardBot();
        bot.clearGameField();
        for (int i = 0; i < 2; i++)
            bot.setCell(MainController.CellType.CROSS, i, 2 - i);
        bot.makeMove(MainController.CellType.CROSS);
        assertEquals(NOUGHT, bot.getCell(2, 0));
    }

    @Test
    void makeMoveLTRDiagonalLoss() {
        HardBot bot = new HardBot();
        bot.clearGameField();
        for (int i = 0; i < 2; i++)
            bot.setCell(MainController.CellType.CROSS, i, i);
        bot.makeMove(MainController.CellType.CROSS);
        assertEquals(NOUGHT, bot.getCell(2, 2));
    }

    @Test
    void makeMoveFirstHorizontalLineWin() {
        HardBot bot = new HardBot();
        bot.clearGameField();
        for (int i = 0; i < 2; i++)
            bot.setCell(MainController.CellType.CROSS, 0, i);
        bot.makeMove(MainController.CellType.NOUGHT);
        assertEquals(MainController.CellType.CROSS, bot.getCell(0, 2));
    }
}