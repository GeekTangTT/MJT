package com.olym.mjt.database;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.lc.methodex.LogFinalUtils;
import java.lang.reflect.Constructor;
import java.sql.SQLException;

public class UnlimitDaoManager
{
    public static <D extends Dao<T, ?>, T> D createDao(ConnectionSource paramConnectionSource, DatabaseTableConfig<T> paramDatabaseTableConfig)
            throws SQLException
    {
        if (paramConnectionSource == null) {
            try
            {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            finally {}
        }
        paramConnectionSource = doCreateDao(paramConnectionSource, paramDatabaseTableConfig);
        return paramConnectionSource;
    }

    private static <D extends Dao<T, ?>, T> D doCreateDao(ConnectionSource paramConnectionSource, DatabaseTableConfig<T> paramDatabaseTableConfig)
            throws SQLException
    {
        Object localObject = (DatabaseTable)paramDatabaseTableConfig.getDataClass().getAnnotation(DatabaseTable.class);
        if ((localObject == null) || (((DatabaseTable)localObject).daoClass() == Void.class) || (((DatabaseTable)localObject).daoClass() == BaseDaoImpl.class)) {
            return null;
        }
        localObject = ((DatabaseTable)localObject).daoClass();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramConnectionSource;
        arrayOfObject[1] = paramDatabaseTableConfig;
        paramConnectionSource = findConstructor((Class)localObject, arrayOfObject);
        if (paramConnectionSource == null) {
            throw new SQLException("Could not find public constructor with ConnectionSource, DatabaseTableConfig parameters in class " + localObject);
        }
        try
        {
            paramConnectionSource = (Dao)paramConnectionSource.newInstance(arrayOfObject);
            return paramConnectionSource;
        }
        catch (Exception paramConnectionSource)
        {
            LogFinalUtils.logForException(paramConnectionSource);
            throw SqlExceptionUtil.create("Could not call the constructor in class " + localObject, paramConnectionSource);
        }
    }

    private static Constructor<?> findConstructor(Class<?> paramClass, Object[] paramArrayOfObject)
    {
        paramClass = paramClass.getConstructors();
        int n = paramClass.length;
        int i = 0;
        while (i < n)
        {
            Constructor<?> localConstructor = paramClass[i];
            Class[] arrayOfClass = localConstructor.getParameterTypes();
            if (arrayOfClass.length == paramArrayOfObject.length)
            {
                int m = 1;
                int j = 0;
                for (;;)
                {
                    int k = m;
                    if (j < arrayOfClass.length)
                    {
                        if (!arrayOfClass[j].isAssignableFrom(paramArrayOfObject[j].getClass())) {
                            k = 0;
                        }
                    }
                    else
                    {
                        if (k == 0) {
                            break;
                        }
                        return localConstructor;
                    }
                    j += 1;
                }
            }
            i += 1;
        }
        return null;
    }
}
