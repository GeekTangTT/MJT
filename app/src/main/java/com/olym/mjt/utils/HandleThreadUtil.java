package com.olym.mjt.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.io.PrintStream;
import java.util.HashMap;

public class HandleThreadUtil
{
    private static final long DELAY_QUIT_TIME = 45000L;
    private static HashMap<String, Handler> handlerHashMap = new HashMap();

    private static Handler getHandler(String paramString)
    {
        if (handlerHashMap.get(paramString) == null) {}
        synchronized (handlerHashMap)
        {
            if (handlerHashMap.get(paramString) == null)
            {
                System.out.println("------handle���������----- " + paramString);
                Object localObject = new HandlerThread(paramString);
                ((HandlerThread)localObject).start();
                localObject = new Handler(((HandlerThread)localObject).getLooper());
                handlerHashMap.put(paramString, localObject);
            }
            return (Handler)handlerHashMap.get(paramString);
        }
    }

    public static void post(String paramString, HandlerRunable paramHandlerRunable)
    {
        getHandler(paramString).post(paramHandlerRunable);
    }

    public static abstract class HandlerRunable
            implements Runnable
    {
        private String name;

        public HandlerRunable(String paramString)
        {
            this.name = paramString;
        }

        private void lastDo()
        {
            HandleThreadUtil.getHandler(this.name).postDelayed(new Runnable()
            {
                public void run()
                {
                    synchronized (HandleThreadUtil.handlerHashMap)
                    {
                        Handler localHandler = (Handler)HandleThreadUtil.handlerHashMap.get(HandleThreadUtil.HandlerRunable.this.name);
                        HandleThreadUtil.handlerHashMap.remove(HandleThreadUtil.HandlerRunable.this.name);
                        localHandler.removeCallbacksAndMessages(null);
                        localHandler.getLooper().quit();
                        System.out.println("------handle���������----- " + HandleThreadUtil.HandlerRunable.this.name + " " + HandleThreadUtil.handlerHashMap.get(HandleThreadUtil.HandlerRunable.this.name));
                        return;
                    }
                }
            }, 45000L);
        }

        private void preDo()
        {
            HandleThreadUtil.getHandler(this.name).removeCallbacksAndMessages(null);
        }

        public abstract void doRun();

        public void run()
        {
            preDo();
            try
            {
                doRun();
                lastDo();
                return;
            }
            catch (Exception localException)
            {
                localException = localException;
                lastDo();
                return;
            }
            finally
            {
                localObject = finally;
                lastDo();
                throw ((Throwable)localObject);
            }
        }
    }
}
