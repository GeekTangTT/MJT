package com.olym.mjt.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadPoolUtils
{
    public static final int CachedThread = 1;
    public static final int FixedThread = 0;
    public static final int SingleThread = 2;
    private ExecutorService exec;
    private ScheduledExecutorService scheduleExec;

    private ThreadPoolUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public ThreadPoolUtils(int paramInt1, int paramInt2)
    {
        this.scheduleExec = Executors.newScheduledThreadPool(paramInt2);
        switch (paramInt1)
        {
            default:
                return;
            case 0:
                this.exec = Executors.newFixedThreadPool(paramInt2);
                return;
            case 2:
                this.exec = Executors.newSingleThreadExecutor();
                return;
        }
        this.exec = Executors.newCachedThreadPool();
    }

    public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit)
            throws InterruptedException
    {
        return this.exec.awaitTermination(paramLong, paramTimeUnit);
    }

    public void execute(Runnable paramRunnable)
    {
        this.exec.execute(paramRunnable);
    }

    public void execute(List<Runnable> paramList)
    {
        paramList = paramList.iterator();
        while (paramList.hasNext())
        {
            Runnable localRunnable = (Runnable)paramList.next();
            this.exec.execute(localRunnable);
        }
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection)
            throws InterruptedException
    {
        return this.exec.invokeAll(paramCollection);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection, long paramLong, TimeUnit paramTimeUnit)
            throws InterruptedException
    {
        return this.exec.invokeAll(paramCollection, paramLong, paramTimeUnit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> paramCollection)
            throws InterruptedException, ExecutionException
    {
        return (T)this.exec.invokeAny(paramCollection);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> paramCollection, long paramLong, TimeUnit paramTimeUnit)
            throws InterruptedException, ExecutionException, TimeoutException
    {
        return (T)this.exec.invokeAny(paramCollection, paramLong, paramTimeUnit);
    }

    public boolean isShutDown()
    {
        return this.exec.isShutdown();
    }

    public boolean isTerminated()
    {
        return this.exec.isTerminated();
    }

    public ScheduledFuture<?> schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
    {
        return this.scheduleExec.schedule(paramRunnable, paramLong, paramTimeUnit);
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> paramCallable, long paramLong, TimeUnit paramTimeUnit)
    {
        return this.scheduleExec.schedule(paramCallable, paramLong, paramTimeUnit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
    {
        return this.scheduleExec.scheduleWithFixedDelay(paramRunnable, paramLong1, paramLong2, paramTimeUnit);
    }

    public ScheduledFuture<?> scheduleWithFixedRate(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
    {
        return this.scheduleExec.scheduleAtFixedRate(paramRunnable, paramLong1, paramLong2, paramTimeUnit);
    }

    public void shutDown()
    {
        this.exec.shutdown();
    }

    public List<Runnable> shutDownNow()
    {
        return this.exec.shutdownNow();
    }

    public Future<?> submit(Runnable paramRunnable)
    {
        return this.exec.submit(paramRunnable);
    }

    public <T> Future<T> submit(Runnable paramRunnable, T paramT)
    {
        return this.exec.submit(paramRunnable, paramT);
    }

    public <T> Future<T> submit(Callable<T> paramCallable)
    {
        return this.exec.submit(paramCallable);
    }
}
