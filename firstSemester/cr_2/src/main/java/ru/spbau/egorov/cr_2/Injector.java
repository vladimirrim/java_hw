package ru.spbau.egorov.cr_2;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * This class implements dependency injection.
 */
public class Injector {
    private static HashMap<String, Boolean> used = new HashMap<>();
    private static HashMap<String, Object> inited = new HashMap<>();

    public static Object initialize(@NotNull String rootClassName, @NotNull List<Class<?>> classes)
            throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
            InstantiationException, InjectionCycleException, ImplementationNotFoundException, AmbiguousImplementationException {

        if (used.getOrDefault(rootClassName, false)) {
            if (inited.getOrDefault(rootClassName, null) == null) {
                throw new InjectionCycleException();
            } else {
                return inited.get(rootClassName);
            }
        }

        used.put(rootClassName, true);

        Class<?> rootClass = Class.forName(rootClassName);
        Constructor cons = rootClass.getDeclaredConstructors()[0];
        Type[] params = cons.getGenericParameterTypes();
        ArrayList<Object> initParams = new ArrayList<>();
        for (Type t : params) {
            Class<?> depClass = Class.forName(t.getTypeName());
            if (depClass.isInterface()) {
                int cnt = 0;
                for (Class c : classes) {
                    if (Arrays.asList(c.getInterfaces()).contains(depClass) && !Modifier.isAbstract(c.getModifiers())) {
                        cnt++;
                    }
                }
                if (cnt == 0)
                    throw new ImplementationNotFoundException();

                if (cnt > 1)
                    throw new AmbiguousImplementationException();

                for (Class c : classes) {
                    if (Arrays.asList(c.getInterfaces()).contains(depClass)) {
                        initParams.add(initialize(c.getCanonicalName(), classes));
                        break;
                    }
                }


            } else if (Modifier.isAbstract(depClass.getModifiers())) {

                int cnt = 0;
                for (Class c : classes) {
                    if (depClass.isAssignableFrom(c) && !Modifier.isAbstract(c.getModifiers())) {
                        cnt++;
                    }
                }
                if (cnt == 0)
                    throw new ImplementationNotFoundException();

                if (cnt > 1)
                    throw new AmbiguousImplementationException();


                for (Class c : classes) {
                    if (depClass.isAssignableFrom(c) && !Modifier.isAbstract(c.getModifiers())) {
                        initParams.add(initialize(c.getCanonicalName(), classes));
                        break;
                    }
                }

            } else {
                if (classes.contains(depClass))
                    initParams.add(initialize(t.getTypeName(), classes));
                else
                    throw new ImplementationNotFoundException();

            }
        }

        cons.setAccessible(true);
        Object root = cons.newInstance(initParams.toArray());
        inited.put(rootClassName, root);
        return root;
    }

    public static void clear(){
        used.clear();
        inited.clear();
    }
}
