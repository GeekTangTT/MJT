package com.olym.mjt.database;

import android.database.Cursor;
import com.j256.ormlite.misc.JavaxPersistence;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import com.lc.methodex.LogFinalUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

public class DatabaseUtil
{
    public static final String TAG = "DatabaseUtil.java";

    private static <T> String extractTableName(Class<T> paramClass)
    {
        Object localObject = (DatabaseTable)paramClass.getAnnotation(DatabaseTable.class);
        if ((localObject != null) && (((DatabaseTable)localObject).tableName() != null) && (((DatabaseTable)localObject).tableName().length() > 0)) {
            localObject = ((DatabaseTable)localObject).tableName();
        }
        String str;
        do
        {
            return (String)localObject;
            str = JavaxPersistence.getEntityName(paramClass);
            localObject = str;
        } while (str != null);
        return paramClass.getSimpleName().toLowerCase();
    }

    /* Error */
    private static String[] getColumnNames(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
        // Byte code:
        //   0: aconst_null
        //   1: astore 8
        //   3: aconst_null
        //   4: astore 7
        //   6: aconst_null
        //   7: astore 6
        //   9: aconst_null
        //   10: astore 5
        //   12: aload 7
        //   14: astore 4
        //   16: aload_0
        //   17: new 55	java/lang/StringBuilder
        //   20: dup
        //   21: invokespecial 56	java/lang/StringBuilder:<init>	()V
        //   24: ldc 58
        //   26: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   29: aload_1
        //   30: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   33: ldc 64
        //   35: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   38: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   41: aconst_null
        //   42: invokevirtual 73	net/sqlcipher/database/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Lnet/sqlcipher/Cursor;
        //   45: astore_1
        //   46: aload 8
        //   48: astore_0
        //   49: aload_1
        //   50: ifnull +186 -> 236
        //   53: aload 7
        //   55: astore 4
        //   57: aload_1
        //   58: astore 5
        //   60: aload_1
        //   61: astore 6
        //   63: aload_1
        //   64: ldc 75
        //   66: invokeinterface 81 2 0
        //   71: istore_3
        //   72: iload_3
        //   73: iconst_m1
        //   74: if_icmpne +15 -> 89
        //   77: aload_1
        //   78: ifnull +9 -> 87
        //   81: aload_1
        //   82: invokeinterface 84 1 0
        //   87: aconst_null
        //   88: areturn
        //   89: iconst_0
        //   90: istore_2
        //   91: aload 7
        //   93: astore 4
        //   95: aload_1
        //   96: astore 5
        //   98: aload_1
        //   99: astore 6
        //   101: aload_1
        //   102: invokeinterface 87 1 0
        //   107: anewarray 32	java/lang/String
        //   110: astore 7
        //   112: aload 7
        //   114: astore 4
        //   116: aload_1
        //   117: astore 5
        //   119: aload_1
        //   120: astore 6
        //   122: aload_1
        //   123: invokeinterface 91 1 0
        //   128: pop
        //   129: aload 7
        //   131: astore 4
        //   133: aload_1
        //   134: astore 5
        //   136: aload 7
        //   138: astore_0
        //   139: aload_1
        //   140: astore 6
        //   142: aload_1
        //   143: invokeinterface 94 1 0
        //   148: ifne +88 -> 236
        //   151: aload 7
        //   153: astore 4
        //   155: aload_1
        //   156: astore 5
        //   158: aload_1
        //   159: astore 6
        //   161: aload 7
        //   163: iload_2
        //   164: aload_1
        //   165: iload_3
        //   166: invokeinterface 98 2 0
        //   171: aastore
        //   172: iload_2
        //   173: iconst_1
        //   174: iadd
        //   175: istore_2
        //   176: aload 7
        //   178: astore 4
        //   180: aload_1
        //   181: astore 5
        //   183: aload_1
        //   184: astore 6
        //   186: aload_1
        //   187: invokeinterface 101 1 0
        //   192: pop
        //   193: goto -64 -> 129
        //   196: astore_0
        //   197: aload 5
        //   199: astore 6
        //   201: aload_0
        //   202: invokevirtual 104	java/lang/Exception:printStackTrace	()V
        //   205: aload 5
        //   207: astore 6
        //   209: aload_0
        //   210: invokestatic 110	com/lc/methodex/LogFinalUtils:logForException	(Ljava/lang/Throwable;)V
        //   213: aload 4
        //   215: astore 6
        //   217: aload 5
        //   219: ifnull +14 -> 233
        //   222: aload 5
        //   224: invokeinterface 84 1 0
        //   229: aload 4
        //   231: astore 6
        //   233: aload 6
        //   235: areturn
        //   236: aload_0
        //   237: astore 6
        //   239: aload_1
        //   240: ifnull -7 -> 233
        //   243: aload_1
        //   244: invokeinterface 84 1 0
        //   249: aload_0
        //   250: astore 6
        //   252: goto -19 -> 233
        //   255: astore_0
        //   256: aload 6
        //   258: ifnull +10 -> 268
        //   261: aload 6
        //   263: invokeinterface 84 1 0
        //   268: aload_0
        //   269: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	270	0	paramSQLiteDatabase	SQLiteDatabase
        //   0	270	1	paramString	String
        //   90	86	2	i	int
        //   71	95	3	j	int
        //   14	216	4	arrayOfString1	String[]
        //   10	213	5	str	String
        //   7	255	6	localObject1	Object
        //   4	173	7	arrayOfString2	String[]
        //   1	46	8	localObject2	Object
        // Exception table:
        //   from	to	target	type
        //   16	46	196	java/lang/Exception
        //   63	72	196	java/lang/Exception
        //   101	112	196	java/lang/Exception
        //   122	129	196	java/lang/Exception
        //   142	151	196	java/lang/Exception
        //   161	172	196	java/lang/Exception
        //   186	193	196	java/lang/Exception
        //   16	46	255	finally
        //   63	72	255	finally
        //   101	112	255	finally
        //   122	129	255	finally
        //   142	151	255	finally
        //   161	172	255	finally
        //   186	193	255	finally
        //   201	205	255	finally
        //   209	213	255	finally
    }

    public static List<String> queryAllTables(SQLiteDatabase paramSQLiteDatabase)
    {
        ArrayList localArrayList = new ArrayList();
        paramSQLiteDatabase = paramSQLiteDatabase.rawQuery("SELECT name FROM SQLITE_MASTER WHERE type='table' ORDER BY name", null);
        while (paramSQLiteDatabase.moveToNext()) {
            localArrayList.add(paramSQLiteDatabase.getString(0));
        }
        paramSQLiteDatabase.close();
        return localArrayList;
    }

    public static <T> void upgradeTable(SQLiteDatabase paramSQLiteDatabase, ConnectionSource paramConnectionSource, OPERATION_TYPE paramOPERATION_TYPE)
    {
        paramConnectionSource = queryAllTables(paramSQLiteDatabase);
        if (paramConnectionSource != null)
        {
            Iterator localIterator = paramConnectionSource.iterator();
            while (localIterator.hasNext())
            {
                String str1 = (String)localIterator.next();
                if ((str1 != null) && (str1.contains("msg_")))
                {
                    paramSQLiteDatabase.beginTransaction();
                    try
                    {
                        String str2 = str1 + "_temp";
                        paramSQLiteDatabase.execSQL("ALTER TABLE " + str1 + " RENAME TO " + str2);
                        try
                        {
                            paramSQLiteDatabase.execSQL(SQLiteRawUtil.getCreateChatMessageTableSql(str1));
                            if (paramOPERATION_TYPE == OPERATION_TYPE.ADD)
                            {
                                paramConnectionSource = Arrays.toString(getColumnNames(paramSQLiteDatabase, str2)).replace("[", "").replace("]", "");
                                paramSQLiteDatabase.execSQL("INSERT INTO " + str1 + " (" + paramConnectionSource + ")  SELECT " + paramConnectionSource + " FROM " + str2);
                                paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + str2);
                                paramSQLiteDatabase.setTransactionSuccessful();
                            }
                        }
                        catch (Exception paramConnectionSource)
                        {
                            for (;;)
                            {
                                paramConnectionSource.printStackTrace();
                                LogFinalUtils.logForException(paramConnectionSource);
                            }
                        }
                    }
                    catch (Exception paramConnectionSource)
                    {
                        for (;;)
                        {
                            paramConnectionSource.printStackTrace();
                            LogFinalUtils.logForException(paramConnectionSource);
                            paramSQLiteDatabase.endTransaction();
                            break;
                            if (paramOPERATION_TYPE != OPERATION_TYPE.DELETE) {
                                break label300;
                            }
                            paramConnectionSource = Arrays.toString(getColumnNames(paramSQLiteDatabase, str1)).replace("[", "").replace("]", "");
                        }
                        label300:
                        throw new IllegalArgumentException("OPERATION_TYPE error");
                    }
                    finally
                    {
                        paramSQLiteDatabase.endTransaction();
                    }
                }
            }
        }
    }

    public static <T> void upgradeTable(SQLiteDatabase paramSQLiteDatabase, ConnectionSource paramConnectionSource, Class<T> paramClass, OPERATION_TYPE paramOPERATION_TYPE)
    {
        str1 = extractTableName(paramClass);
        paramSQLiteDatabase.beginTransaction();
        for (;;)
        {
            try
            {
                str2 = str1 + "_temp";
                paramSQLiteDatabase.execSQL("ALTER TABLE " + str1 + " RENAME TO " + str2);
            }
            catch (Exception paramConnectionSource)
            {
                String str2;
                paramConnectionSource.printStackTrace();
                LogFinalUtils.logForException(paramConnectionSource);
                return;
                if (paramOPERATION_TYPE != OPERATION_TYPE.DELETE) {
                    continue;
                }
                paramConnectionSource = Arrays.toString(getColumnNames(paramSQLiteDatabase, str1)).replace("[", "").replace("]", "");
                continue;
                throw new IllegalArgumentException("OPERATION_TYPE error");
            }
            finally
            {
                paramSQLiteDatabase.endTransaction();
            }
            try
            {
                paramSQLiteDatabase.execSQL((String)TableUtils.getCreateTableStatements(paramConnectionSource, paramClass).get(0));
                if (paramOPERATION_TYPE == OPERATION_TYPE.ADD)
                {
                    paramConnectionSource = Arrays.toString(getColumnNames(paramSQLiteDatabase, str2)).replace("[", "").replace("]", "");
                    paramSQLiteDatabase.execSQL("INSERT INTO " + str1 + " (" + paramConnectionSource + ")  SELECT " + paramConnectionSource + " FROM " + str2);
                    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + str2);
                    paramSQLiteDatabase.setTransactionSuccessful();
                    paramSQLiteDatabase.endTransaction();
                    return;
                }
            }
            catch (Exception localException)
            {
                localException.printStackTrace();
                LogFinalUtils.logForException(localException);
                TableUtils.createTable(paramConnectionSource, paramClass);
            }
        }
    }

    public static enum OPERATION_TYPE
    {
        ADD,  DELETE;

        private OPERATION_TYPE() {}
    }
}

