package com.olym.mjt.database;

import android.database.Cursor;
import android.text.TextUtils;
import com.lc.methodex.LogFinalUtils;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

public class SQLiteRawUtil
{
    public static final String CHAT_MESSAGE_TABLE_PREFIX = "msg_";

    public static void createTableIfNotExist(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    {
        if (isTableExist(paramSQLiteDatabase, paramString1)) {
            return;
        }
        try
        {
            LogFinalUtils.logForNormal("----createTableIfNotExist-----" + paramString1);
            paramSQLiteDatabase.execSQL(paramString2);
            return;
        }
        catch (SQLException paramSQLiteDatabase)
        {
            LogFinalUtils.logForException(paramSQLiteDatabase);
        }
    }

    public static void dropTable(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        try
        {
            paramSQLiteDatabase.execSQL("drop table " + paramString);
            return;
        }
        catch (Exception paramSQLiteDatabase)
        {
            LogFinalUtils.logForException(paramSQLiteDatabase);
        }
    }

    public static String getCreateChatMessageTableSql(String paramString)
    {
        return "CREATE TABLE IF NOT EXISTS " + paramString + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,type INTEGER NOT NULL,timeSend INTEGER NOT NULL,packetId VARCHAR NOT NULL,timeReceive INTEGER,fromUserId VARCHAR,toUserKey VARCHAR,fromUserName VARCHAR,isMySend SMALLINT,content VARCHAR,filePath VARCHAR,location_y VARCHAR,location_x VARCHAR,imageWidth VARCHAR,imageHeight VARCHAR,isRead SMALLINT,isEncrypt SMALLINT,isUpload SMALLINT,isDownload INTEGER,isSend INTEGER,isOnDownloading INTEGER,messageState INTEGER,timeLen INTEGER , fileSize INTEGER,objectId VARCHAR,sipStatus INTEGER,sipDuration INTEGER,ibcdomain VARCHAR,ibcversion VARCHAR,fire INTEGER,needfire INTEGER,myneedfire INTEGER,isDecrypted INTEGER,roomcreatetime INTEGER,thumbnail VARCHAR)";
    }

    /* Error */
    public static java.util.List<String> getUserChatMessageTables(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        // Byte code:
        //   0: new 23	java/lang/StringBuilder
        //   3: dup
        //   4: invokespecial 24	java/lang/StringBuilder:<init>	()V
        //   7: ldc 8
        //   9: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   12: aload_1
        //   13: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   16: invokevirtual 34	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   19: astore_3
        //   20: aconst_null
        //   21: astore_2
        //   22: aconst_null
        //   23: astore_1
        //   24: aload_0
        //   25: new 23	java/lang/StringBuilder
        //   28: dup
        //   29: invokespecial 24	java/lang/StringBuilder:<init>	()V
        //   32: ldc 65
        //   34: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   37: aload_3
        //   38: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   41: ldc 67
        //   43: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   46: invokevirtual 34	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   49: aconst_null
        //   50: invokevirtual 71	net/sqlcipher/database/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Lnet/sqlcipher/Cursor;
        //   53: astore_0
        //   54: aload_0
        //   55: ifnull +110 -> 165
        //   58: aload_0
        //   59: astore_1
        //   60: aload_0
        //   61: astore_2
        //   62: new 73	java/util/ArrayList
        //   65: dup
        //   66: invokespecial 74	java/util/ArrayList:<init>	()V
        //   69: astore_3
        //   70: aload_0
        //   71: astore_1
        //   72: aload_0
        //   73: astore_2
        //   74: aload_0
        //   75: invokeinterface 80 1 0
        //   80: ifeq +71 -> 151
        //   83: aload_0
        //   84: astore_1
        //   85: aload_0
        //   86: astore_2
        //   87: aload_0
        //   88: iconst_0
        //   89: invokeinterface 84 2 0
        //   94: astore 4
        //   96: aload_0
        //   97: astore_1
        //   98: aload_0
        //   99: astore_2
        //   100: aload 4
        //   102: invokestatic 90	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   105: ifne -35 -> 70
        //   108: aload_0
        //   109: astore_1
        //   110: aload_0
        //   111: astore_2
        //   112: aload_3
        //   113: aload 4
        //   115: invokeinterface 96 2 0
        //   120: pop
        //   121: goto -51 -> 70
        //   124: astore_0
        //   125: aload_1
        //   126: astore_2
        //   127: aload_0
        //   128: invokevirtual 99	java/lang/Exception:printStackTrace	()V
        //   131: aload_1
        //   132: astore_2
        //   133: aload_0
        //   134: invokestatic 49	com/lc/methodex/LogFinalUtils:logForException	(Ljava/lang/Throwable;)V
        //   137: aload_1
        //   138: ifnull +9 -> 147
        //   141: aload_1
        //   142: invokeinterface 102 1 0
        //   147: aconst_null
        //   148: astore_1
        //   149: aload_1
        //   150: areturn
        //   151: aload_3
        //   152: astore_1
        //   153: aload_0
        //   154: ifnull -5 -> 149
        //   157: aload_0
        //   158: invokeinterface 102 1 0
        //   163: aload_3
        //   164: areturn
        //   165: aload_0
        //   166: ifnull -19 -> 147
        //   169: aload_0
        //   170: invokeinterface 102 1 0
        //   175: goto -28 -> 147
        //   178: astore_0
        //   179: aload_2
        //   180: ifnull +9 -> 189
        //   183: aload_2
        //   184: invokeinterface 102 1 0
        //   189: aload_0
        //   190: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	191	0	paramSQLiteDatabase	SQLiteDatabase
        //   0	191	1	paramString	String
        //   21	163	2	localObject1	Object
        //   19	145	3	localObject2	Object
        //   94	20	4	str	String
        // Exception table:
        //   from	to	target	type
        //   24	54	124	java/lang/Exception
        //   62	70	124	java/lang/Exception
        //   74	83	124	java/lang/Exception
        //   87	96	124	java/lang/Exception
        //   100	108	124	java/lang/Exception
        //   112	121	124	java/lang/Exception
        //   24	54	178	finally
        //   62	70	178	finally
        //   74	83	178	finally
        //   87	96	178	finally
        //   100	108	178	finally
        //   112	121	178	finally
        //   127	131	178	finally
        //   133	137	178	finally
    }

    public static boolean isTableExist(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        bool3 = false;
        bool2 = false;
        if (TextUtils.isEmpty(paramString.trim())) {
            return false;
        }
        localObject = null;
        localSQLiteDatabase = null;
        try
        {
            paramSQLiteDatabase = paramSQLiteDatabase.rawQuery("select count(*) as c from Sqlite_master  where type ='table' and name ='" + paramString.trim() + "' ", null);
            boolean bool1 = bool2;
            localSQLiteDatabase = paramSQLiteDatabase;
            localObject = paramSQLiteDatabase;
            if (paramSQLiteDatabase.moveToNext())
            {
                localSQLiteDatabase = paramSQLiteDatabase;
                localObject = paramSQLiteDatabase;
                int i = paramSQLiteDatabase.getInt(0);
                bool1 = bool2;
                if (i > 0) {
                    bool1 = true;
                }
            }
            bool2 = bool1;
            if (paramSQLiteDatabase != null)
            {
                paramSQLiteDatabase.close();
                bool2 = bool1;
            }
        }
        catch (Exception paramSQLiteDatabase)
        {
            for (;;)
            {
                localObject = localSQLiteDatabase;
                paramSQLiteDatabase.printStackTrace();
                localObject = localSQLiteDatabase;
                LogFinalUtils.logForException(paramSQLiteDatabase);
                bool2 = bool3;
                if (localSQLiteDatabase != null)
                {
                    localSQLiteDatabase.close();
                    bool2 = bool3;
                }
            }
        }
        finally
        {
            if (localObject == null) {
                break label170;
            }
            ((Cursor)localObject).close();
        }
        return bool2;
    }
}
