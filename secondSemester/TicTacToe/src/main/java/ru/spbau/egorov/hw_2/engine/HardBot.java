package ru.spbau.egorov.hw_2.engine;


import ru.spbau.egorov.hw_2.controller.MainController;

/**
 * This class implements tic tac toe game bot that always win if there is a one turn victory on the game field,
 * blocks random line for opponent if he has one turn victory and moves in random cells otherwise.
 */
public class HardBot extends Bot {
    /**
     * Wins one turn victory, blocks one turn loses on random line, moves in random cell otherwise.
     * @param opponentType is the opponent`s cell type.
     * @return number of chosen cell.
     */
    @Override
    public int makeMove(MainController.CellType opponentType) {
        MainController.CellType myType = opponentType == MainController.CellType.NOUGHT ? MainController.CellType.CROSS : MainController.CellType.NOUGHT;
        int res = checkWin(myType, myType);
        if (res != -1)
            return res;
        res = checkWin(opponentType, myType);
        if (res != -1)
            return res;
        return makeRandomMove(opponentType);
    }


    private int checkWin(MainController.CellType type, MainController.CellType myType) {
        int res = checkLine(true, type, myType);
        if (res != -1)
            return res;
        res = checkLine(false, type, myType);
        if (res != -1)
            return res;
        res = checkDiagonals(false, type, myType);
        if (res != -1)
            return res;
        return checkDiagonals(true, type, myType);

    }

    private int checkLine(boolean isRow, MainController.CellType type, MainController.CellType myType) {
        int cntOfOpponentCells;
        int x = 0, y = 0;
        boolean skip;

        for (int i = 0; i < GAME_FIELD_SIZE; i++) {
            skip = false;
            cntOfOpponentCells = 0;
            for (int j = 0; j < GAME_FIELD_SIZE; j++) {
                if ((isRow ? gameField[i][j] : gameField[j][i]) == type)
                    cntOfOpponentCells++;
                else if ((isRow ? gameField[i][j] : gameField[j][i]) == MainController.CellType.EMPTY) {
                    x = isRow ? i : j;
                    y = isRow ? j : i;
                } else
                    skip = true;
            }
            if (!skip && cntOfOpponentCells == GAME_FIELD_SIZE - 1) {
                gameField[x][y] = myType;
                return GAME_FIELD_SIZE * x + y;
            }
        }
        return -1;
    }

    private int checkDiagonals(boolean LTR, MainController.CellType type, MainController.CellType myType) {
        int cntOfOpponentCells = 0;
        int x = 0, y = 0;
        boolean skip = false;

        for (int i = 0; i < GAME_FIELD_SIZE; i++) {
            if ((LTR ? gameField[i][i] : gameField[i][GAME_FIELD_SIZE - 1 - i]) == type)
                cntOfOpponentCells++;
            else if ((LTR ? gameField[i][i] : gameField[i][GAME_FIELD_SIZE - 1 - i]) == MainController.CellType.EMPTY) {
                x = i;
                y = LTR ? i : (GAME_FIELD_SIZE - 1 - i);
            } else
                skip = true;
        }
        if (!skip && cntOfOpponentCells == GAME_FIELD_SIZE - 1) {
            gameField[x][y] = myType;
            return GAME_FIELD_SIZE * x + y;
        }
        return -1;
    }
}
