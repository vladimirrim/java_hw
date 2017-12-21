package ru.spbau.egorov.hw_11;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static org.junit.jupiter.api.Assertions.*;

public class ReflectorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void printClassOnlyFields() throws ClassNotFoundException, FileNotFoundException, MalformedURLException {
        Class<?> oldClass = Class.forName("Fields");
        Reflector.printStructure(oldClass);
        File f = new File("." + File.separator + "Fields.java");

        URL url = f.toURL();

        URL[] urls = new URL[]{url};

        ClassLoader cl = new URLClassLoader(urls);
        Class<?> newClass = cl.loadClass("Fields");

        Reflector.diffClasses(oldClass, newClass);
        assertEquals("Unique fields, Fields:\nUnique fields, Fields:\nUnique methods, Fields:\nUnique methods, Fields:\n"
                , outContent.toString());
        f.delete();
    }

    @Test
    public void printClassFieldsAndMethods() throws ClassNotFoundException, FileNotFoundException, MalformedURLException {
        Class<?> oldClass = Class.forName("FieldsAndMethods");
        Reflector.printStructure(oldClass);
        File f = new File("." + File.separator + "FieldsAndMethods.java");

        URL url = f.toURL();

        URL[] urls = new URL[]{url};

        ClassLoader cl = new URLClassLoader(urls);
        Class<?> newClass = cl.loadClass("FieldsAndMethods");

        Reflector.diffClasses(oldClass, newClass);
        assertEquals("Unique fields, FieldsAndMethods:\nUnique fields, FieldsAndMethods:\nUnique methods, FieldsAndMethods:\nUnique methods, FieldsAndMethods:\n"
                , outContent.toString());
        f.delete();
    }

    @Test
    public void printClassFieldsAndMethodsAndClasses() throws ClassNotFoundException, FileNotFoundException, MalformedURLException {
        Class<?> oldClass = Class.forName("FieldsAndMethodsAndClasses");
        Reflector.printStructure(oldClass);
        File f = new File("." + File.separator + "FieldsAndMethodsAndClasses.java");

        URL url = f.toURL();

        URL[] urls = new URL[]{url};

        ClassLoader cl = new URLClassLoader(urls);
        Class<?> newClass = cl.loadClass("FieldsAndMethodsAndClasses");

        Reflector.diffClasses(oldClass, newClass);
        assertEquals("Unique fields, FieldsAndMethodsAndClasses:\nUnique fields, FieldsAndMethodsAndClasses:\nUnique methods, FieldsAndMethodsAndClasses:\nUnique methods, FieldsAndMethodsAndClasses:\n"
                , outContent.toString());
        f.delete();
    }

}