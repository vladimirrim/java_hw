package ru.spbau.egorov.hw_1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class MultiThreadLazyTest {
    @Test
    public void getNullSupplier() {
        Supplier<Integer> sup = mock(Supplier.class);
        when(sup.get()).thenReturn(null);
        Lazy<Integer> lazy = LazyFactory.createMultiThreadLazy(sup);
        for (int i = 0; i < 100; i++)
            assertNull(lazy.get());
        verify(sup, times(1)).get();
    }

    @Test
    public void getThousandTimes() {
        Supplier<Integer> sup = mock(Supplier.class);
        when(sup.get()).thenReturn(1);
        Lazy<Integer> lazy = LazyFactory.createMultiThreadLazy(sup);
        for (int i = 0; i < 1000; i++)
            assertEquals((Integer) 1, lazy.get());
        verify(sup, times(1)).get();
    }

    @Test
    public void getRaceCondition() throws InterruptedException {
        Supplier<Integer> sup = mock(Supplier.class);
        when(sup.get()).thenReturn(null);
        Lazy<Integer> lazy = LazyFactory.createMultiThreadLazy(sup);
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            threads.add(new Thread(() -> assertNull(lazy.get())));
        for (int i = 0; i < 10; i++)
            threads.get(i).start();
        for (int i = 0; i < 10; i++)
            threads.get(i).join();
        verify(sup, times(1)).get();
    }

}