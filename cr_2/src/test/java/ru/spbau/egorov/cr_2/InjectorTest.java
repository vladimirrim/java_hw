package ru.spbau.egorov.cr_2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import task.InjectionCycleException;
import task.testClasses.ClassWithOneClassDependency;
import task.testClasses.ClassWithOneInterfaceDependency;
import task.testClasses.ClassWithoutDependencies;
import task.testClasses.InterfaceImpl;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertTrue;


public class InjectorTest {

    @Test
    public void injectorShouldInitializeClassWithoutDependencies()
            throws Exception {
        Object object = Injector.initialize("task.testClasses.ClassWithoutDependencies", Collections.emptyList());
        assertTrue(object instanceof ClassWithoutDependencies);
    }

    @Test
    public void injectorShouldInitializeClassWithOneClassDependency()
            throws Exception {
        Object object = Injector.initialize(
                "task.testClasses.ClassWithOneClassDependency",
                Collections.singletonList( Class.forName("task.testClasses.ClassWithoutDependencies"))
        );
        assertTrue(object instanceof ClassWithOneClassDependency);
        ClassWithOneClassDependency instance = (ClassWithOneClassDependency) object;
        assertTrue(instance.dependency != null);
    }

    @Test
    public void injectorShouldInitializeClassWithOneInterfaceDependency()
            throws Exception {
        Object object = Injector.initialize(
                "task.testClasses.ClassWithOneInterfaceDependency",
                Collections.singletonList(Class.forName("task.testClasses.InterfaceImpl"))
        );
        assertTrue(object instanceof ClassWithOneInterfaceDependency);
        ClassWithOneInterfaceDependency instance = (ClassWithOneInterfaceDependency) object;
        assertTrue(instance.dependency instanceof InterfaceImpl);
    }

    @Test(expected = ru.spbau.egorov.cr_2.InjectionCycleException.class)
    public void testInjectionCycleExceptionTwoClasses() throws Exception {
        ArrayList<Class<?>> a = new ArrayList<>();
        a.add(Class.forName("task.testClasses.ClassWithCycleDependenciesB"));
        a.add(Class.forName("task.testClasses.ClassWithCycleDependenciesA"));
        Injector.initialize("task.testClasses.ClassWithCycleDependenciesA",a);
    }

    @Test(expected = ru.spbau.egorov.cr_2.ImplementationNotFoundException.class)
    public void testImplementationNotFoundException() throws Exception {
        ArrayList<Class<?>> a = new ArrayList<>();
        a.add(Class.forName("task.testClasses.ClassWithoutDependencies"));
        Injector.initialize("task.testClasses.ClassWithCycleDependenciesA",a);
    }
}