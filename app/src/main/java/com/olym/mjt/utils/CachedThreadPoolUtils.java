package com.olym.mjt.utils;

public class CachedThreadPoolUtils
{
    private static final int CORE_SIZE = 8;
    private static volatile CachedThreadPoolUtils instance;
    private ThreadPoolUtils threadPoolUtils = new ThreadPoolUtils(1, 8);

    public static CachedThreadPoolUtils getInstance()
    {
        if (instance == null) {}
        try
        {
            if (instance == null) {
                instance = new CachedThreadPoolUtils();
            }
            return instance;
        }
        finally {}
    }

    public void execute(Runnable paramRunnable)
    {
        this.threadPoolUtils.execute(paramRunnable);
    }

    public void shutdown()
    {
        this.threadPoolUtils.shutDown();
    }
}
