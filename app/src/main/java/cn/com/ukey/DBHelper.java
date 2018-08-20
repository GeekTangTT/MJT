package cn.com.ukey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper
        extends SQLiteOpenHelper
{
    private String CREATE_LOG_TBL = " create table  LogTbl(_id integer primary key autoincrement, timeStr text, plain text, sessionKey text, iv text, cipher text) ";
    private String DB_NAME = "smlog.db";
    private String LOG_TBL_NAME = "LogTbl";
    private SQLiteDatabase db;

    public DBHelper(Context paramContext, String paramString1, String paramString2, String paramString3)
    {
        super(paramContext, paramString1, null, 2);
        this.DB_NAME = paramString1;
        this.LOG_TBL_NAME = paramString2;
        this.CREATE_LOG_TBL = paramString3;
    }

    public void close()
    {
        if (this.db != null) {
            this.db.close();
        }
    }

    public void del(int paramInt)
    {
        if (this.db == null) {
            this.db = getWritableDatabase();
        }
        this.db.delete(this.LOG_TBL_NAME, "_id=?", new String[] { String.valueOf(paramInt) });
    }

    public void insert(ContentValues paramContentValues)
    {
        SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
        localSQLiteDatabase.insert(this.LOG_TBL_NAME, null, paramContentValues);
        localSQLiteDatabase.close();
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
        this.db = paramSQLiteDatabase;
        paramSQLiteDatabase.execSQL(this.CREATE_LOG_TBL);
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}

    public Cursor query()
    {
        return getWritableDatabase().query(this.LOG_TBL_NAME, null, null, null, null, null, null);
    }
}

