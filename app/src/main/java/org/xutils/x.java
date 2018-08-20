package org.xutils;

import android.app.Application;
import javax.net.ssl.HttpsURLConnection;
import org.xutils.common.TaskController;
import org.xutils.common.task.TaskControllerImpl;
import org.xutils.db.DbManagerImpl;
import org.xutils.http.HttpManagerImpl;
import org.xutils.image.ImageManagerImpl;
import org.xutils.view.ViewInjectorImpl;

public final class x
{
    public static Application app()
    {
        if (Ext.app == null) {
            throw new RuntimeException("please invoke x.Ext.init(app) on Application#onCreate()");
        }
        return Ext.app;
    }

    public static DbManager getDb(DbManager.DaoConfig paramDaoConfig)
    {
        return DbManagerImpl.getInstance(paramDaoConfig);
    }

    public static HttpManager http()
    {
        if (Ext.httpManager == null) {
            HttpManagerImpl.registerInstance();
        }
        return Ext.httpManager;
    }

    public static ImageManager image()
    {
        if (Ext.imageManager == null) {
            ImageManagerImpl.registerInstance();
        }
        return Ext.imageManager;
    }

    public static boolean isDebug()
    {
        return Ext.debug;
    }

    public static TaskController task()
    {
        return Ext.taskController;
    }

    public static ViewInjector view()
    {
        if (Ext.viewInjector == null) {
            ViewInjectorImpl.registerInstance();
        }
        return Ext.viewInjector;
    }

    public static class Ext
    {
        private static Application app;
        private static boolean debug;
        private static HttpManager httpManager;
        private static ImageManager imageManager;
        private static TaskController taskController;
        private static ViewInjector viewInjector;

        static
        {
            TaskControllerImpl.registerInstance();
            HttpsURLConnection.setDefaultHostnameVerifier(new x.Ext.1());
        }

        public static void init(Application paramApplication)
        {
            if (app == null) {
                app = paramApplication;
            }
        }

        public static void setDebug(boolean paramBoolean)
        {
            debug = paramBoolean;
        }

        public static void setHttpManager(HttpManager paramHttpManager)
        {
            httpManager = paramHttpManager;
        }

        public static void setImageManager(ImageManager paramImageManager)
        {
            imageManager = paramImageManager;
        }

        public static void setTaskController(TaskController paramTaskController)
        {
            if (taskController == null) {
                taskController = paramTaskController;
            }
        }

        public static void setViewInjector(ViewInjector paramViewInjector)
        {
            viewInjector = paramViewInjector;
        }
    }
}

