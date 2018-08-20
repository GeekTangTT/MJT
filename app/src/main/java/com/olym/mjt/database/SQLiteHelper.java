package com.olym.mjt.database;

import android.content.Context;
import android.os.Build.VERSION;
import com.j256.ormlite.sqlcipher.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.databean.bean.AuthCode;
import com.olym.mjt.databean.bean.CircleMessage;
import com.olym.mjt.databean.bean.Company;
import com.olym.mjt.databean.bean.CompanyGroup;
import com.olym.mjt.databean.bean.CompanyUser;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.LocalContact;
import com.olym.mjt.databean.bean.MyPhoto;
import com.olym.mjt.databean.bean.NewFriendMessage;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.bean.VideoFile;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import java.sql.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

public class SQLiteHelper
        extends OrmLiteSqliteOpenHelper
{
    private static final String DATABASE_NAME = "shiku.db";
    private static final int DATABASE_VERSION = 19;
    private static String NEW_DB_NAME = UserSpUtil.getInstanse().getPhone() + "_" + UserSpUtil.getInstanse().getUserDomain() + "_" + "shiku.db";

    public SQLiteHelper(Context paramContext)
    {
        super(paramContext, NEW_DB_NAME, AppSpUtil.getInstanse().getDBPassword(), null, 19);
    }

    public static void resetDBName()
    {
        NEW_DB_NAME = UserSpUtil.getInstanse().getPhone() + "_" + UserSpUtil.getInstanse().getUserDomain() + "_" + "shiku.db";
    }

    private void update19(SQLiteDatabase paramSQLiteDatabase, ConnectionSource paramConnectionSource)
    {
        try
        {
            TableUtils.createTableIfNotExists(paramConnectionSource, CompanyGroup.class);
            TableUtils.createTableIfNotExists(paramConnectionSource, CompanyUser.class);
            return;
        }
        catch (SQLException paramSQLiteDatabase)
        {
            LogFinalUtils.logForException(paramSQLiteDatabase);
        }
    }

    private void version2Update(ConnectionSource paramConnectionSource)
    {
        try
        {
            TableUtils.createTableIfNotExists(paramConnectionSource, Company.class);
            TableUtils.createTableIfNotExists(paramConnectionSource, User.class);
            TableUtils.createTableIfNotExists(paramConnectionSource, Friend.class);
            TableUtils.createTableIfNotExists(paramConnectionSource, LocalContact.class);
            TableUtils.createTableIfNotExists(paramConnectionSource, NewFriendMessage.class);
            TableUtils.createTableIfNotExists(paramConnectionSource, VideoFile.class);
            TableUtils.createTableIfNotExists(paramConnectionSource, AuthCode.class);
            TableUtils.createTableIfNotExists(paramConnectionSource, MyPhoto.class);
            TableUtils.createTableIfNotExists(paramConnectionSource, CircleMessage.class);
            TableUtils.createTableIfNotExists(paramConnectionSource, CompanyGroup.class);
            TableUtils.createTableIfNotExists(paramConnectionSource, CompanyUser.class);
            return;
        }
        catch (SQLException paramConnectionSource)
        {
            LogFinalUtils.logForException(paramConnectionSource);
        }
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase, ConnectionSource paramConnectionSource)
    {
        version2Update(paramConnectionSource);
        if (Build.VERSION.SDK_INT >= 11)
        {
            paramSQLiteDatabase.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS friend_fts USING fts4(_id INTEGER PRIMARY KEY AUTOINCREMENT, packetId,roomFlag,userId, friendId,telephone, content,nickName,ibcdomain,timesend,indexNum,filepath)");
            return;
        }
        paramSQLiteDatabase.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS friend_fts USING fts3(_id INTEGER PRIMARY KEY AUTOINCREMENT, packetId,roomFlag,userId, friendId,telephone, content,nickName,ibcdomain,timesend,indexNum,filepath)");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, ConnectionSource paramConnectionSource, int paramInt1, int paramInt2)
    {
        if (paramInt1 < 3)
        {
            DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, DatabaseUtil.OPERATION_TYPE.ADD);
            DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, Friend.class, DatabaseUtil.OPERATION_TYPE.ADD);
            DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, LocalContact.class, DatabaseUtil.OPERATION_TYPE.ADD);
            DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, NewFriendMessage.class, DatabaseUtil.OPERATION_TYPE.ADD);
            update19(paramSQLiteDatabase, paramConnectionSource);
        }
        for (;;)
        {
            onCreate(paramSQLiteDatabase, this.connectionSource);
            return;
            if (paramInt1 < 11)
            {
                DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, DatabaseUtil.OPERATION_TYPE.ADD);
                DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, Friend.class, DatabaseUtil.OPERATION_TYPE.ADD);
                DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, NewFriendMessage.class, DatabaseUtil.OPERATION_TYPE.ADD);
                update19(paramSQLiteDatabase, paramConnectionSource);
            }
            else if (paramInt1 < 12)
            {
                DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, DatabaseUtil.OPERATION_TYPE.ADD);
                DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, NewFriendMessage.class, DatabaseUtil.OPERATION_TYPE.ADD);
                DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, Friend.class, DatabaseUtil.OPERATION_TYPE.ADD);
                update19(paramSQLiteDatabase, paramConnectionSource);
            }
            else if (paramInt1 < 13)
            {
                DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, DatabaseUtil.OPERATION_TYPE.ADD);
                DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, NewFriendMessage.class, DatabaseUtil.OPERATION_TYPE.ADD);
                DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, Friend.class, DatabaseUtil.OPERATION_TYPE.ADD);
                update19(paramSQLiteDatabase, paramConnectionSource);
            }
            else if (paramInt1 < 17)
            {
                DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, DatabaseUtil.OPERATION_TYPE.ADD);
                DatabaseUtil.upgradeTable(paramSQLiteDatabase, this.connectionSource, Friend.class, DatabaseUtil.OPERATION_TYPE.ADD);
                update19(paramSQLiteDatabase, paramConnectionSource);
            }
            else if (paramInt1 < 19)
            {
                update19(paramSQLiteDatabase, paramConnectionSource);
            }
        }
    }
}
