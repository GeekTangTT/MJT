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
import com.olym.mjt.databean.bean.CompanyUser;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class CompanyUserDao
{
    private static CompanyUserDao instance;
    private Dao<CompanyUser, Integer> dao;
    private SQLiteHelper mHelper;

    private CompanyUserDao()
    {
        try
        {
            this.mHelper = DBManager.getInstance().getDbHelper();
            this.dao = DaoManager.createDao(this.mHelper.getConnectionSource(), CompanyUser.class);
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

    public static CompanyUserDao getInstance()
    {
        if (instance == null) {}
        try
        {
            if (instance == null) {
                instance = new CompanyUserDao();
            }
            return instance;
        }
        finally {}
    }

    public boolean createOrUpdate(CompanyUser paramCompanyUser)
    {
        boolean bool1 = false;
        try
        {
            paramCompanyUser = this.dao.createOrUpdate(paramCompanyUser);
            if (!paramCompanyUser.isCreated())
            {
                boolean bool2 = paramCompanyUser.isUpdated();
                if (!bool2) {}
            }
            else
            {
                bool1 = true;
            }
            return bool1;
        }
        catch (SQLException paramCompanyUser)
        {
            LogFinalUtils.logForException(paramCompanyUser);
        }
        return false;
    }

    public void deleteAllCompanyUsers()
    {
        Iterator localIterator = getAllCompanyUser().iterator();
        while (localIterator.hasNext()) {
            deleteCompanyUser((CompanyUser)localIterator.next());
        }
    }

    public void deleteCompanyUser(CompanyUser paramCompanyUser)
    {
        try
        {
            this.dao.delete(paramCompanyUser);
            return;
        }
        catch (SQLException paramCompanyUser)
        {
            LogFinalUtils.logForException(paramCompanyUser);
        }
    }

    public List<CompanyUser> getAllCompanyUser()
    {
        try
        {
            Object localObject = this.dao.queryBuilder().prepare();
            localObject = this.dao.query((PreparedQuery)localObject);
            return (List<CompanyUser>)localObject;
        }
        catch (SQLException localSQLException)
        {
            LogFinalUtils.logForException(localSQLException);
        }
        return null;
    }

    public List<CompanyUser> getCompanyUser(String paramString)
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

    public CompanyUser getCompanyUserFromPhone(String paramString)
    {
        try
        {
            paramString = this.dao.queryBuilder().where().in("user", (Object[])new String[] { paramString }).prepare();
            paramString = (CompanyUser)this.dao.queryForFirst(paramString);
            return paramString;
        }
        catch (SQLException paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }
}
