package ru.spbau.egorov.hw_2.spiral;

import java.util.Random;


/**
 * This class contains matrix and can print it in spiral order and sort it`s cols by first elements.
 */
public class Spiral {
    private int matrix[][];
    private static Random generator = new Random();

    /**
     * @param n only odd.
     */
    public Spiral(int n) {
        matrix = new int[n][];
        for (int i = 0; i < n; i++)
            matrix[i] = new int[n];
    }

    /**
     * Print elements of the matrix in spiral order starting in the middle.
     */
    public void printElements() {
        int x = matrix[0].length / 2;
        int y = matrix[0].length / 2;
        int direction = -1;
        for (int i = 1; i <= matrix[0].length; i++) {
            int cnt = i;
            while (cnt != 0) {
                cnt--;
                System.out.print(matrix[x][y] + " ");
                x += direction;
            }
            cnt = i;
            while (cnt != 0 && cnt != matrix[0].length) {
                cnt--;
                System.out.print(matrix[x][y] + " ");
                y -= direction;
            }
            direction *= -1;
        }
    }

    /**
     * Sort cols by their first elements using quick sort.
     */
    public void sortCols() {
        quickSort(0, matrix[0].length - 1);
    }

    public int get(int i, int j) {
        return matrix[i][j];
    }

    public void set(int i, int j, int value) {
        matrix[i][j] = value;
    }

    private void quickSort(int l, int r) {
        if (l >= r)
            return;

        int i = l, j = r;
        int x = matrix[l + generator.nextInt((r - l) / 2 + 1)][0];

        while (i <= j) {
            while (matrix[i][0] < x) {
                i++;
            }
            while (x < matrix[j][0]) {
                j--;
            }
            if (i <= j) {
                swapCols(i++, j--);
            }
        }

        quickSort(l, j);
        quickSort(i, r);
    }

    private void swapCols(int i, int j) {
        int tmp[] = new int[matrix[i].length];

        System.arraycopy(matrix[i], 0, tmp, 0, matrix[i].length);
        System.arraycopy(matrix[j], 0, matrix[i], 0, matrix[i].length);
        System.arraycopy(tmp, 0, matrix[j], 0, matrix[i].length);
    }
}
