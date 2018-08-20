package com.olym.mjt.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.database.DBManager;
import com.olym.mjt.database.SQLiteHelper;
import com.olym.mjt.databean.bean.CompanyGroup;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class CompanyGroupDao
{
    private static CompanyGroupDao instance;
    private Dao<CompanyGroup, Integer> dao;
    private SQLiteHelper mHelper;

    private CompanyGroupDao()
    {
        try
        {
            this.mHelper = DBManager.getInstance().getDbHelper();
            this.dao = DaoManager.createDao(this.mHelper.getConnectionSource(), CompanyGroup.class);
            return;
        }
        catch (SQLException localSQLException)
        {
            LogFinalUtils.logForException(localSQLException);
        }
    }

    public static void close()
    {
        instance = null;
    }

    public static final CompanyGroupDao getInstance()
    {
        if (instance == null) {}
        try
        {
            if (instance == null) {
                instance = new CompanyGroupDao();
            }
            return instance;
        }
        finally {}
    }

    public boolean createOrUpdate(CompanyGroup paramCompanyGroup)
    {
        boolean bool1 = false;
        try
        {
            paramCompanyGroup = this.dao.createOrUpdate(paramCompanyGroup);
            if (!paramCompanyGroup.isCreated())
            {
                boolean bool2 = paramCompanyGroup.isUpdated();
                if (!bool2) {}
            }
            else
            {
                bool1 = true;
            }
            return bool1;
        }
        catch (SQLException paramCompanyGroup)
        {
            LogFinalUtils.logForException(paramCompanyGroup);
        }
        return false;
    }

    public void deleteAllCompanyGroup()
    {
        Iterator localIterator = getAllCompanyGroup().iterator();
        while (localIterator.hasNext()) {
            deleteCompanyGroup((CompanyGroup)localIterator.next());
        }
    }

    public void deleteCompanyGroup(CompanyGroup paramCompanyGroup)
    {
        try
        {
            this.dao.delete(paramCompanyGroup);
            return;
        }
        catch (SQLException paramCompanyGroup)
        {
            LogFinalUtils.logForException(paramCompanyGroup);
        }
    }

    public List<CompanyGroup> getAllCompanyGroup()
    {
        try
        {
            Object localObject = this.dao.queryBuilder().prepare();
            localObject = this.dao.query((PreparedQuery)localObject);
            return (List<CompanyGroup>)localObject;
        }
        catch (SQLException localSQLException)
        {
            LogFinalUtils.logForException(localSQLException);
        }
        return null;
    }

    public List<CompanyGroup> getCompanyGroup(String paramString)
    {
        try
        {
            paramString = this.dao.queryBuilder().where().in("parent_group_id", (Object[])new String[] { paramString }).prepare();
            paramString = this.dao.query(paramString);
            return paramString;
        }
        catch (SQLException paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }
}
