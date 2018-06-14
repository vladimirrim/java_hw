package ru.spbau.egorov.hw_5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    private ByteArrayOutputStream outContent;
    private final String sep = System.getProperty("line.separator");

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    void BeforeClassAndAfterClass() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, MalformedURLException {
        Main.main(new String[]{"src/test/resources", "test_classes.BeforeClassAndAfterClass"});
        assertTrue(outContent.toString().contains("Succeeded: 1"));
        assertTrue(outContent.toString().contains("Before classTest"));
        assertTrue(outContent.toString().contains(sep + "After class"));
    }

    @Test
    void ExpectedException() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, MalformedURLException {
        Main.main(new String[]{"src/test/resources", "test_classes.ExpectedException"});
        assertTrue(outContent.toString().contains("Succeeded: 1"));
    }

    @Test
    void UnexpectedException() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, MalformedURLException {
        Main.main(new String[]{"src/test/resources", "test_classes.UnexpectedException"});
        assertTrue(outContent.toString().contains("Failed: 1"));
    }

    @Test
    void TwoTestsNoExceptions() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, MalformedURLException {
        Main.main(new String[]{"src/test/resources", "test_classes.TwoTestsNoExceptions"});
        assertTrue(outContent.toString().contains("Succeeded: 2"));
        assertTrue(outContent.toString().contains("Test1"));
        assertTrue(outContent.toString().contains("Test2"));
    }

    @Test
    void TwoBeforeTwoAfterTwoBeforeClassTwoAfterClass() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, MalformedURLException {
        Main.main(new String[]{"src/test/resources", "test_classes.TwoBeforeTwoAfterTwoBeforeClassTwoAfterClass"});
        assertTrue(outContent.toString().contains("Succeeded: 1"));
        assertTrue(outContent.toString().contains("BeforeClassBeforeClassBeforeBeforeTest"));
        assertTrue(outContent.toString().contains(sep + "AfterClassAfterClass"));
        assertTrue(outContent.toString().contains(sep + "AfterAfter"));
    }

    @Test
    void TwoIgnoredTests() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, MalformedURLException {
        Main.main(new String[]{"src/test/resources", "test_classes.TwoIgnoredTests"});
        assertTrue(outContent.toString().contains("Ignored: 2"));
        assertTrue(outContent.toString().contains(sep + "JoJo"));
        assertTrue(outContent.toString().contains(sep + "jojo"));
        assertFalse(outContent.toString().contains("Test1"));
        assertFalse(outContent.toString().contains("Test2"));
    }

    @Test
    void MethodWithTwoAnnotations() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, MalformedURLException {
        Main.main(new String[]{"src/test/resources", "test_classes.MethodWithTwoAnnotations"});
        assertTrue(outContent.toString().contains("Before and AfterTotal"));
        assertTrue(outContent.toString().contains(sep + "Before and After"));
    }

}