package ru.spbau.egorov.hw_2.engine;


import ru.spbau.egorov.hw_2.controller.MainController;

import java.util.Random;

/**
 * This class implements basic functions for tic tac toe game bot.
 */
public abstract class Bot {
    protected static final int GAME_FIELD_SIZE = 3;
    protected MainController.CellType[][] gameField = new MainController.CellType[GAME_FIELD_SIZE][GAME_FIELD_SIZE];

    /**
     * Makes move depending on difficulty of the bot.
     * @param opponentType is the opponent`s cell type.
     * @return number of chosen cell.
     */
    abstract public int makeMove(MainController.CellType opponentType);

    /**
     * Makes move in random empty cell.
     * @return number of chosen cell.
     */
    protected int makeRandomMove(MainController.CellType curType) {
        Random ran = new Random();
        int i = ran.nextInt(GAME_FIELD_SIZE), j = ran.nextInt(GAME_FIELD_SIZE);
        while (gameField[i][j] != MainController.CellType.EMPTY) {
            i = ran.nextInt(GAME_FIELD_SIZE);
            j = ran.nextInt(GAME_FIELD_SIZE);
        }
        gameField[i][j] = curType;
        return i * GAME_FIELD_SIZE + j;
    }

    public void setCell(MainController.CellType type, int i, int j) {
        gameField[i][j] = type;
    }

    public MainController.CellType getCell(int i, int j) {
        return gameField[i][j];
    }

    /**
     * Checks if there is a draw on the game field.
     * @return true if it has.
     */
    public boolean isDraw() {
        for (int i = 0; i < GAME_FIELD_SIZE; i++)
            for (int j = 0; j < GAME_FIELD_SIZE; j++)
                if (gameField[i][j] == MainController.CellType.EMPTY)
                    return false;
        return !(isWin(MainController.CellType.NOUGHT) || isWin(MainController.CellType.CROSS));
    }

    /**
     * Checks if certain type wins on the game field.
     * @param type is the type to check for a win condition.
     * @return true if given type has win on the game field.
     */
    public boolean isWin(MainController.CellType type) {
        boolean diagonalLTRHasWinner = true;
        boolean diagonalRTLHasWinner = true;
        for (int i = 0; i < GAME_FIELD_SIZE; i++) {
            boolean colHasWinner = true;
            boolean rowHasWinner = true;
            for (int j = 1; j < GAME_FIELD_SIZE; j++) {
                colHasWinner = colHasWinner && (gameField[j][i] == gameField[j - 1][i]);
                rowHasWinner = rowHasWinner && (gameField[i][j] == gameField[i][j - 1]);
            }
            if ((rowHasWinner && gameField[i][0] == type) || (colHasWinner && gameField[0][i] == type)) {
                return true;
            }
        }
        for (int j = 1; j < GAME_FIELD_SIZE; j++) {
            diagonalLTRHasWinner = diagonalLTRHasWinner && (gameField[j][j] == gameField[j - 1][j - 1]);
            diagonalRTLHasWinner = diagonalRTLHasWinner && (gameField[GAME_FIELD_SIZE - j - 1][j] == gameField[GAME_FIELD_SIZE - j][j - 1]);
        }
        return (diagonalLTRHasWinner && gameField[0][0] == type) || (diagonalRTLHasWinner && gameField[GAME_FIELD_SIZE - 1][0] == type);
    }

    /**
     * Fill game field with EMPTY cell types.
     */
    public void clearGameField() {
        for (int i = 0; i < GAME_FIELD_SIZE; i++)
            for (int j = 0; j < GAME_FIELD_SIZE; j++)
                gameField[i][j] = MainController.CellType.EMPTY;
    }
}
