package ru.spbau.egorov.cr_2.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class implements logic for the Pairs game.
 */
public class GameEngine {
    private final static int MAX_GAME_FIELD_SIZE = 16;
    private final int[][] cellNumbers;
    private final boolean[][] isActive;
    private final int gameFieldSize;

    /**
     * Creates game field of given size.
     * @param size is the given size.
     * @throws InvalidGameFieldSizeException if the size is invalid.
     */
    public GameEngine(int size) throws InvalidGameFieldSizeException {
        if (!checkGameFieldSize(size))
            throw new InvalidGameFieldSizeException();

        gameFieldSize = size;
        isActive = new boolean[size][size];
        cellNumbers = new int[size][size];

        List<Integer> randomPairs = new ArrayList<>();
        for (int i = 0; i < size * size / 2; i++) {
            randomPairs.add(i);
            randomPairs.add(i);
        }
        Iterator<Integer> it = randomPairs.iterator();

        Collections.shuffle(randomPairs);

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                isActive[i][j] = true;
                cellNumbers[i][j] = it.next();
            }
    }

    public void setInactive(int row, int col) {
        isActive[row][col] = false;
    }

    public void setActive(int row, int col) {
        isActive[row][col] = true;
    }

    /**
     * Checks if the game has ended.
     * @return true if it has ended.
     */
    public boolean isEnded() {
        for (int i = 0; i < gameFieldSize; i++)
            for (int j = 0; j < gameFieldSize; j++)
                if (isActive[i][j])
                    return false;
        return true;
    }

    public int getCellNumber(int row, int col) {
        return cellNumbers[row][col];
    }

    private boolean checkGameFieldSize(int size) {
        return size > 0 && size <= MAX_GAME_FIELD_SIZE && size % 2 == 0;
    }
}
