package ru.spbau.egorov.hw_2.engine;

import ru.spbau.egorov.hw_2.controller.MainController;

/**
 * This class implements tic tac toe game bot that always goes in random cells.
 */
public class EasyBot extends Bot {
    /**
     * Moves in random cell.
     * @param opponentType is the opponent`s cell type.
     * @return number of chosen cell.
     */
    @Override
    public int makeMove(MainController.CellType opponentType) {
        return makeRandomMove(opponentType == MainController.CellType.CROSS ? MainController.CellType.NOUGHT : MainController.CellType.CROSS);
    }
}
