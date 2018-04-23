package ru.spbau.egorov.hw_1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ThreadPoolImplTest {
    @Test
    public void fiveThreadsApplyTwoTimes() throws LightExecutionException {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(5);
        LightFuture<Integer> task = pool.addTask(() -> 2 * 2);
        assertThat(task.get(), is(4));
        LightFuture<Integer> task1 = pool.addTask(() -> 2 * 3);
        LightFuture<Integer> task2 = task1.thenApply(i -> i + 1);
        LightFuture<Integer> task3 = task1.thenApply(i -> i + 2);
        assertThat(task1.get(), is(6));
        assertThat(task2.get(), is(7));
        assertThat(task3.get(), is(8));
    }

    @Test
    public void fourThreadsApplyThousandTimes() throws LightExecutionException {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        ArrayList<LightFuture<Integer>> tasks = new ArrayList<>();
        tasks.add(pool.addTask(() -> 0));
        for (int i = 1; i < 1000; i++)
            tasks.add(tasks.get(i - 1).thenApply(k -> k + 1));
        for (int i = 0; i < 1000; i++)
            assertThat(tasks.get(i).get(), is(i));
    }

    @Test
    public void fourThreadsAddTaskException() {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        LightFuture<Integer> task = pool.addTask(() -> {
            throw new RuntimeException();
        });
        assertThrows(LightExecutionException.class, task::get);
    }

    @Test
    public void fourThreadsThenApplyException() {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        LightFuture<Integer> task = pool.addTask(() -> 1);
        LightFuture<Integer> task1 = task.thenApply(i -> {
            throw new RuntimeException();
        });
        assertThrows(LightExecutionException.class, task1::get);
    }

    @Test
    public void fourThreadsThenApplyCheckOrder() throws LightExecutionException {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        ArrayList<LightFuture<Integer>> tasks = new ArrayList<>();
        StringBuilder res = new StringBuilder();
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            ans.append(i);
        }
        tasks.add(pool.addTask(() -> {
            res.append(0);
            return 0;
        }));
        for (int i = 1; i < 4; i++)
            tasks.add(tasks.get(i - 1).thenApply(k -> {
                res.append(k + 1);
                return k + 1;
            }));
        assertThat(tasks.get(3).get(), is(3));
        assertThat(res.toString(), is(ans.toString()));
    }

    @Test
    public void fourThreadsShutdown() {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        pool.shutdown();
        LightFuture<Integer> task = pool.addTask(() -> 2 * 2);
        assertThat(task.isReady(), is(false));
    }
}