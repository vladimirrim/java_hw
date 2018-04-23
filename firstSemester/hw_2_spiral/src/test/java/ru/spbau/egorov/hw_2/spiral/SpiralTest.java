package ru.spbau.egorov.hw_2.spiral;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SpiralTest {

    @Test
    void createSpiral() {
        Spiral sp = new Spiral(11);
        assertNotNull(sp);
    }

    @Test
    void printElementsOneElement() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Spiral sp = new Spiral(1);
        sp.set(0, 0, 8);
        sp.printElements();
        assertEquals("8 ", out.toString());
    }

    @Test
    void printElementsNineElements() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Spiral sp = new Spiral(3);
        int val = 1;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                sp.set(j, i, val++);

        sp.printElements();
        assertEquals("5 4 7 8 9 6 3 2 1 ", out.toString());
    }

    @Test
    void sortColsCopyColsElevenCols() {
        Spiral sp = new Spiral(11);
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 11; j++)
                sp.set(i, j, 11 - i);

        sp.sortCols();
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 11; j++)
                assertEquals(i + 1, sp.get(i, j));
    }

    @Test
    void sortColsReverseOrderElevenCols() {
        Spiral sp = new Spiral(11);
        for (int i = 0; i < 11; i++)
            sp.set(i, 0, 11 - i);

        sp.sortCols();
        for (int i = 0; i < 11; i++)
            assertEquals(i + 1, sp.get(i, 0));
    }

    @Test
    void sortColsRandomElementsThirteenCols() {
        Random rd = new Random();
        Spiral sp = new Spiral(13);
        for (int i = 0; i < 13; i++) {
            sp.set(i, 0, rd.nextInt());
        }

        sp.sortCols();

        for (int i = 1; i < 13; i++) {
            assertEquals(true, sp.get(i - 1, 0) <= sp.get(i, 0));
        }
    }

}