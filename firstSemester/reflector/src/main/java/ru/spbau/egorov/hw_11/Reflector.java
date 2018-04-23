package ru.spbau.egorov.hw_11;


import com.google.common.base.Defaults;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class prints structure of class and shows difference in methods and fields of two classes.
 */
public class Reflector {

    private static String paramNames = "z";

    /**
     * Prints structure of the class.
     *
     * @param c is given class.
     */
    public static void printStructure(@NotNull Class<?> c) {
        File f = new File("." + File.separator + c.getSimpleName() + ".java");
        try (PrintWriter s = new PrintWriter(f)) {
            printClass(c, s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printClass(Class<?> c, PrintWriter s) {
        s.print(c.toGenericString() + " extends " + c.getGenericSuperclass().getTypeName());
        if (c.getGenericInterfaces().length != 0)
            s.print(Arrays.stream(c.getGenericInterfaces()).map(Type::getTypeName).
                    collect(Collectors.joining(", ", " implements ", " ")));
        s.print("{\n");

        Arrays.stream(c.getDeclaredFields()).forEach(f -> {
            s.print(Modifier.toString(f.getModifiers()) + " " +
                    f.getGenericType().getTypeName() + " " + f.getName() + " ");
            if (Modifier.isFinal(f.getModifiers()))
                s.print("=" + Defaults.defaultValue(f.getType()));
            s.println(";");
        });

        Arrays.stream(c.getDeclaredConstructors()).forEach(m -> {
            s.print(Modifier.toString(m.getModifiers()) + " " + m.getName() + " ");

            s.print(Arrays.stream(m.getGenericParameterTypes()).map(f -> {
                        paramNames += "z";
                        return f.getTypeName() + " " + paramNames;
                    }
            ).
                    collect(Collectors.joining(", ", "( ", " ) ")));

            if (m.getGenericExceptionTypes().length != 0)
                s.print(" throws " +
                        Arrays.stream(m.getGenericExceptionTypes()).map(Type::getTypeName).collect(Collectors.joining(", ")));
            paramNames = "z";
            s.println("{\n}");
        });

        Arrays.stream(c.getDeclaredMethods()).forEach(m -> {
            s.print(Modifier.toString(m.getModifiers()) + " " + m.getGenericReturnType().getTypeName() + " " + m.getName() + " ");

            s.print(Arrays.stream(m.getGenericParameterTypes()).map(f -> {
                        paramNames += "z";
                        return f.getTypeName() + " " + paramNames;
                    }
            ).
                    collect(Collectors.joining(", ", "( ", " ) ")));

            if (m.getGenericExceptionTypes().length != 0)
                s.print(" throws " +
                        Arrays.stream(m.getGenericExceptionTypes()).map(Type::getTypeName).collect(Collectors.joining(", ")));
            paramNames = "z";

            s.print("{\nreturn ");
            if (!m.getGenericReturnType().getTypeName().equals("void"))
                s.print(Defaults.defaultValue(m.getReturnType()));
            s.println(";\n}");
        });


        Arrays.stream(c.getDeclaredClasses()).forEach(m -> printClass(m, s));
        s.print("}\n");
    }


    /**
     * Shows difference in methods and fields of two classes.
     *
     * @param a is the first class.
     * @param b is the second class.
     */
    public static void diffClasses(@NotNull Class<?> a, @NotNull Class<?> b) {

        System.out.print("Unique fields, " + a.getSimpleName() + ":\n");
        for (Field t : a.getDeclaredFields()) {
            if (!Arrays.asList(b.getDeclaredFields()).contains(t))
                System.out.println(t.toGenericString());
        }

        System.out.print("Unique fields, " + b.getSimpleName() + ":\n");
        for (Field t : b.getDeclaredFields()) {
            if (!Arrays.asList(a.getDeclaredFields()).contains(t))
                System.out.println(t.toGenericString());
        }

        System.out.print("Unique methods, " + a.getSimpleName() + ":\n");
        for (Method t : a.getDeclaredMethods()) {
            if (!Arrays.asList(b.getDeclaredMethods()).contains(t))
                System.out.println(t.toGenericString());
        }

        System.out.print("Unique methods, " + b.getSimpleName() + ":\n");
        for (Method t : b.getDeclaredMethods()) {
            if (!Arrays.asList(a.getDeclaredMethods()).contains(t))
                System.out.println(t.toGenericString());
        }

    }
}

