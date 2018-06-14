package ru.spbau.egorov.hw_5;

import ru.spbau.egorov.hw_5.annotations.*;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents test invoker.
 */
public class Main {
    /**
     * This function takes path to the class and it`s name and invokes all tests in it, which are marked by @Test annotation.
     *
     * @param args are the path and the class name.
     * @throws ClassNotFoundException    if class doesn`t exist.
     * @throws IllegalAccessException    if there was trouble with method accessibility.
     * @throws InstantiationException    if class don`t have constructor without parameters.
     * @throws InvocationTargetException if there was a trouble invoking method.
     * @throws MalformedURLException     if there was a trouble loading class.
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, MalformedURLException {
        Path path = Paths.get(args[0]);
        URL url = path.toUri().toURL();
        URL[] urls = new URL[]{url};
        ClassLoader cl = new URLClassLoader(urls);
        Class clazz = cl.loadClass(args[1]);

        ArrayList<Method> tests = new ArrayList<>();
        ArrayList<Test> testAnnotations = new ArrayList<>();
        ArrayList<Method> beforeClassMethods = new ArrayList<>();
        ArrayList<Method> afterClassMethods = new ArrayList<>();
        ArrayList<Method> beforeMethods = new ArrayList<>();
        ArrayList<Method> afterMethods = new ArrayList<>();
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> Arrays.stream(method.getDeclaredAnnotations()).forEach(annotation -> {
            if (annotation instanceof Test) {
                tests.add(method);
                testAnnotations.add((Test) annotation);
            }
            if (annotation instanceof Before) {
                beforeMethods.add(method);
            }
            if (annotation instanceof After) {
                afterMethods.add(method);
            }
            if (annotation instanceof BeforeClass) {
                beforeClassMethods.add(method);
            }
            if (annotation instanceof AfterClass) {
                afterClassMethods.add(method);
            }
        }));

        for (Method method : beforeClassMethods) {
            method.setAccessible(true);
            method.invoke(clazz.newInstance());
        }

        for (Method before : beforeMethods)
            before.setAccessible(true);

        for (Method after : afterMethods)
            after.setAccessible(true);

        int succeeded = 0, failed = 0, ignored = 0;
        for (int i = 0; i < tests.size(); i++) {
            Method test = tests.get(i);
            Test testAnnotation = testAnnotations.get(i);
            if (!testAnnotation.ignored().equals("NOT_IGNORED")) {
                ignored++;
                System.out.println("Test " + test.getName() + " was ignored.Reason:");
                System.out.println(testAnnotation.ignored());
                continue;
            }
            for (Method before : beforeMethods)
                before.invoke(clazz.newInstance());
            long startTime = System.currentTimeMillis();
            try {
                test.setAccessible(true);
                test.invoke(clazz.newInstance());
            } catch (Throwable e) {
                long endTime = System.currentTimeMillis();
                if (e.getCause().getClass().equals(testAnnotation.expected())) {
                    System.out.println("Test " + test.getName() + ": SUCCESS. TIME:");
                    System.out.println((double) (endTime - startTime) / 1000 + "seconds");
                    succeeded++;
                } else {
                    System.out.println("Test " + test.getName() + ": FAIL. TIME:");
                    System.out.println((double) (endTime - startTime) / 1000 + "seconds");
                    System.out.println("Expected exception: " + (testAnnotation.expected().equals(void.class) ? "No exceptions" : testAnnotation.expected().getName()) +
                            " But was: " + e.getClass().getName());
                    failed++;

                }
                for (Method after : afterMethods)
                    after.invoke(clazz.newInstance());
                continue;
            }
            long endTime = System.currentTimeMillis();
            if (testAnnotation.expected().equals(void.class)) {
                System.out.println("Test " + test.getName() + ": SUCCESS. TIME:");
                System.out.println((double) (endTime - startTime) / 1000 + "seconds");
                succeeded++;
            } else {
                System.out.println("Test " + test.getName() + ": FAIL. TIME:");
                System.out.println((double) (endTime - startTime) / 1000 + "seconds");
                System.out.println("Expected exception: " + testAnnotation.expected().getName() +
                        " But was: No exceptions");
                failed++;
            }
            for (Method after : afterMethods) {
                after.invoke(clazz.newInstance());
            }
        }

        System.out.println("Total number of tests: " + tests.size());
        System.out.println("Failed: " + failed);
        System.out.println("Succeeded: " + succeeded);
        System.out.println("Ignored: " + ignored);

        for (Method method : afterClassMethods) {
            method.setAccessible(true);
            method.invoke(clazz.newInstance());
        }
    }
}
