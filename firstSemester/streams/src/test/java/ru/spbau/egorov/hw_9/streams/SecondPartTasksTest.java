package ru.spbau.egorov.hw_9.streams;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Math.abs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        answer.add("The first line1");
        for (int i = 0; i < 10; i++) {
            File file = File.createTempFile("abcde" + i, "txt");
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println("The first line" + i);
            writer.println("The second line");
            writer.close();
            Scanner s = new Scanner(file);
            lines.add(s.nextLine());
            lines.add(s.nextLine());
            s.close();
        }
        assertEquals(answer, SecondPartTasks.findQuotes(lines, "1"));
    }

    @Test
    public void testPiDividedBy4() {
        assertTrue(abs(Math.PI / 4 - SecondPartTasks.piDividedBy4()) < 0.01);
    }

    @Test
    public void testFindPrinter() {
        HashMap<String, List<String>> c = new HashMap<>();
        ArrayList<String> jo1 = new ArrayList<>();
        jo1.add("jojojojo");
        c.put("Jo", jo1);
        ArrayList<String> jo2 = new ArrayList<>();
        jo2.add("jojoj");
        jo2.add("jojoj");
        c.put("jo", jo2);
        ArrayList<String> jo3 = new ArrayList<>();
        jo3.add("joj");
        jo3.add("joj");
        jo3.add("jojoj");
        c.put("jojo", jo3);
        assertEquals("jojo", SecondPartTasks.findPrinter(c));
    }

    @Test
    public void testCalculateGlobalOrder() {
        ArrayList<Map<String, Integer>> l = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Map<String, Integer> m = new HashMap<>();
            m.put("jo", i);
            m.put("jojo", i);
            l.add(m);
        }
        Map<String, Integer> m = new HashMap<>();
        m.put("jo", 55);
        m.put("jojo", 55);
        assertEquals(m, SecondPartTasks.calculateGlobalOrder(l));
    }
}