package ru.spbau.egorov.hw_1;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class implements task executor which uses several threads for parallel evaluation.
 *
 * @param <T> is the type of evaluation result.
 */
public class ThreadPoolImpl<T> {
    private final Queue<LightFutureImpl> workQueue = new LinkedList<>();
    private final ArrayList<Thread> threads = new ArrayList<>();

    /**
     * Creates pool with given amount of threads.
     *
     * @param numberOfThreads is the number of threads that are running in pool.
     */
    public ThreadPoolImpl(int numberOfThreads) {
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Thread(() -> {
                while (!Thread.interrupted()) {
                    LightFutureImpl nextTask;
                    synchronized (workQueue) {
                        nextTask = workQueue.poll();
                    }
                    if (nextTask != null) {
                        try {
                            nextTask.value = nextTask.sup.get();
                        } catch (RuntimeException e) {
                            nextTask.exception = new LightExecutionException(e);
                        }
                        nextTask.isReady = true;
                    }
                }
            }));
            threads.get(i).start();
        }
    }

    /**
     * Adds task to the thread pool.
     *
     * @param sup is the supplier which do the evaluation.
     * @return LightFuture which represents task.
     */
    public LightFuture<T> addTask(Supplier<T> sup) {
        LightFutureImpl future = new LightFutureImpl(sup);
        workQueue.add(future);
        return future;

    }

    /**
     * Stop all threads in pool through interrupt.
     */
    public void shutdown() {
        for (Thread thread : threads)
            thread.interrupt();
    }

    private class LightFutureImpl implements LightFuture<T> {
        private volatile boolean isReady;
        private Supplier<T> sup;
        private volatile T value;
        private volatile LightExecutionException exception;

        private LightFutureImpl(@NotNull Supplier<T> sup) {
            this.sup = sup;
        }

        @Override
        public boolean isReady() {
            return isReady;
        }

        @Override
        public T get() throws LightExecutionException {
            while (!isReady) {
                Thread.yield();
            }
            if (exception != null)
                throw exception;
            return value;
        }

        @Override
        public LightFuture<T> thenApply(Function<T, T> fun) {
            return addTask(() -> {
                try {
                    return fun.apply(LightFutureImpl.this.get());
                } catch (LightExecutionException e) {
                    e.printStackTrace();
                    return null;
                }
            });
        }
    }
}
