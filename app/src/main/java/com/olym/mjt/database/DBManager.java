package com.olym.mjt.database;

import android.content.Context;
import com.j256.ormlite.sqlcipher.android.apptools.OpenHelperManager;
import com.olym.mjt.config.AppConfig;
import com.olym.mjt.database.dao.ChatMessageDao;
import com.olym.mjt.database.dao.CompanyGroupDao;
import com.olym.mjt.database.dao.CompanyUserDao;
import com.olym.mjt.database.dao.FriendDao;
import com.olym.mjt.database.dao.LocalContactDao;
import com.olym.mjt.database.dao.NewFriendDao;
import com.olym.mjt.database.dao.UserDao;
import com.olym.mjt.database.dao.VideoFileDao;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.utils.FileSpUtils;
import java.util.UUID;
import net.sqlcipher.database.SQLiteDatabase;

public class DBManager
{
    private static DBManager instance;
    private SQLiteHelper dbHelper;

    private DBManager() {}

    private DBManager(Context paramContext)
    {
        SQLiteDatabase.loadLibs(paramContext);
        FileSpUtils.getInstanse().getKeyFileReandomNumber();
        SQLiteHelper.resetDBName();
        FriendDao.close();
        ChatMessageDao.close();
        LocalContactDao.close();
        NewFriendDao.close();
        UserDao.close();
        VideoFileDao.close();
        CompanyGroupDao.close();
        CompanyUserDao.close();
        OpenHelperManager.setHelper(null);
        this.dbHelper = ((SQLiteHelper)OpenHelperManager.getHelper(MjtApplication.getInstance(), SQLiteHelper.class));
    }

    public static DBManager getInstance()
    {
        if (instance == null)
        {
            instance = new DBManager(MjtApplication.getInstance());
            if (AppConfig.init()) {
                EngineUtils.getInstance().initSecurityEngine(MjtApplication.getInstance());
            }
        }
        return instance;
    }

    public static String getUUID()
    {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    public static void init(Context paramContext)
    {
        instance = new DBManager(paramContext);
    }

    public void closeDatabase()
    {
        FriendDao.close();
        ChatMessageDao.close();
        LocalContactDao.close();
        NewFriendDao.close();
        UserDao.close();
        VideoFileDao.close();
        CompanyGroupDao.close();
        CompanyUserDao.close();
        OpenHelperManager.releaseHelper();
        OpenHelperManager.setHelper(null);
        this.dbHelper = null;
        instance = null;
    }

    public SQLiteHelper getDbHelper()
    {
        return this.dbHelper;
    }
}
