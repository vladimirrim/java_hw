package ru.spbau.egorov.hw_4.maybe;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MaybeTest {


    @Test
    void justInteger() throws ValueNotPresentException {
        Maybe<Integer> ma = Maybe.just(8);
        assertEquals(Integer.valueOf(8), ma.get());
    }

    @Test
    void justString() throws ValueNotPresentException {
        Maybe<String> ma = Maybe.just("asasgjgdfjgh");
        assertEquals("asasgjgdfjgh", ma.get());
    }

    @Test
    void nothingException() {
        Throwable e = assertThrows(ValueNotPresentException.class, () -> {
            Maybe.nothing().get();
        });
    }

    @Test
    void isPresentNothing() {
        assertFalse(Maybe.nothing().isPresent());
    }

    @Test
    void isPresentInteger() {
        assertTrue(Maybe.just(8).isPresent());
    }

    @Test
    void mapSquareInteger() throws ValueNotPresentException {
        assertEquals(Integer.valueOf(64), Maybe.just(8).map(x -> x * x).get());
    }

    @Test
    void mapNothing() throws ValueNotPresentException {
        assertFalse(Maybe.nothing().map(x -> "jo").isPresent());
    }

    @Test
    void readAndWriteFromFile() throws IOException, ValueNotPresentException {
        ArrayList<Maybe<Integer>> inputLines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("testInput.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    inputLines.add(Maybe.just(Integer.parseInt(line)));
                } catch (NumberFormatException e) {
                    inputLines.add(Maybe.nothing());
                }
            }
        }

        ArrayList<Maybe<Integer>> squares = new ArrayList<>();
        for (Maybe<Integer> elem : inputLines) {
            squares.add(elem.map(x -> x * x));
        }

        try (FileWriter fw = new FileWriter("testOutput.txt")) {
            for (Maybe<Integer> elem : squares) {
                if (!elem.isPresent()) {
                    fw.write("null\n");
                } else {
                    fw.write(elem.get() + "\n");
                }
            }
        }

        ArrayList<Maybe<Integer>> outputLines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("testOutput.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    outputLines.add(Maybe.just(Integer.parseInt(line)));
                } catch (NumberFormatException e) {
                    outputLines.add(Maybe.nothing());
                }
            }
        }

        assertEquals(squares.size(), outputLines.size());
        for (int i = 0; i < squares.size(); i++) {
            assertEquals(squares.get(i).isPresent(), outputLines.get(i).isPresent());
            if (squares.get(i).isPresent())
                assertEquals(squares.get(i).get(), outputLines.get(i).get());
        }
    }

}