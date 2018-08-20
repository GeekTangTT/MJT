package com.olym.mjt.database.dao;

import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.sqlcipher.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.database.DBManager;
import com.olym.mjt.database.SQLiteHelper;
import com.olym.mjt.databean.bean.Company;
import com.olym.mjt.databean.bean.User;
import java.sql.SQLException;

public class UserDao
{
    private static UserDao instance = null;
    public Dao<Company, Integer> companyDao;
    public Dao<User, String> userDao;

    private UserDao()
    {
        try
        {
            SQLiteHelper localSQLiteHelper = DBManager.getInstance().getDbHelper();
            this.userDao = DaoManager.createDao(localSQLiteHelper.getConnectionSource(), User.class);
            this.companyDao = DaoManager.createDao(localSQLiteHelper.getConnectionSource(), Company.class);
            return;
        }
        catch (SQLException localSQLException)
        {
            localSQLException.printStackTrace();
            LogFinalUtils.logForException(localSQLException);
        }
    }

    public static void close()
    {
        instance = null;
    }

    public static final UserDao getInstance()
    {
        if (instance == null) {}
        try
        {
            if (instance == null) {
                instance = new UserDao();
            }
            return instance;
        }
        finally {}
    }

    public User getUserByUserId(String paramString)
    {
        try
        {
            paramString = (User)this.userDao.queryForId(paramString);
            return paramString;
        }
        catch (SQLException paramString)
        {
            LogFinalUtils.logForException(paramString);
            paramString.printStackTrace();
            return null;
        }
        catch (NullPointerException paramString)
        {
            for (;;)
            {
                paramString.printStackTrace();
                LogFinalUtils.logForException(paramString);
            }
        }
    }

    public boolean saveUserLogin(User paramUser)
    {
        try
        {
            if ((User)this.userDao.queryForId(paramUser.getUserId()) != null)
            {
                UpdateBuilder localUpdateBuilder = this.userDao.updateBuilder();
                localUpdateBuilder.updateColumnValue("telephone", paramUser.getTelephone());
                localUpdateBuilder.updateColumnValue("password", paramUser.getPassword());
                localUpdateBuilder.where().idEq(paramUser.getUserId());
                this.userDao.update(localUpdateBuilder.prepare());
            }
            for (;;)
            {
                this.userDao.refresh(paramUser);
                return true;
                this.userDao.create(paramUser);
            }
            return false;
        }
        catch (SQLException paramUser)
        {
            paramUser.printStackTrace();
            LogFinalUtils.logForException(paramUser);
            return false;
        }
        catch (NullPointerException paramUser)
        {
            paramUser.printStackTrace();
            LogFinalUtils.logForException(paramUser);
        }
    }

    public void updateAreaId(String paramString, int paramInt)
    {
        updateColumnValue(paramString, "areaId", paramInt + "");
    }

    public void updateBirthday(String paramString1, String paramString2)
    {
        updateColumnValue(paramString1, "birthday", paramString2);
    }

    public boolean updateByUser(User paramUser)
    {
        if (paramUser == null) {}
        for (;;)
        {
            return false;
            try
            {
                User localUser = (User)this.userDao.queryForId(paramUser.getUserId());
                if (localUser != null)
                {
                    paramUser.setPassword(localUser.getPassword());
                    if (paramUser.getCompany() != null) {
                        this.companyDao.createOrUpdate(paramUser.getCompany());
                    }
                    this.userDao.update(paramUser);
                    return true;
                }
            }
            catch (SQLException paramUser)
            {
                LogFinalUtils.logForException(paramUser);
                paramUser.printStackTrace();
                return false;
            }
            catch (NullPointerException paramUser)
            {
                paramUser.printStackTrace();
                LogFinalUtils.logForException(paramUser);
            }
        }
        return false;
    }

    public void updateCityId(String paramString, int paramInt)
    {
        updateColumnValue(paramString, "cityId", paramInt + "");
    }

    public void updateColumnValue(String paramString1, String paramString2, String paramString3)
    {
        UpdateBuilder localUpdateBuilder = this.userDao.updateBuilder();
        try
        {
            localUpdateBuilder.updateColumnValue(paramString2, paramString3);
            localUpdateBuilder.where().idEq(paramString1);
            this.userDao.update(localUpdateBuilder.prepare());
            paramString1 = (User)this.userDao.queryForId(paramString1);
            Log.d("roamer", "user.sex:" + paramString1.getSex());
            return;
        }
        catch (SQLException paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateCountryId(String paramString, int paramInt)
    {
        updateColumnValue(paramString, "countryId", paramInt + "");
    }

    public void updateDescription(String paramString1, String paramString2)
    {
        updateColumnValue(paramString1, "description", paramString2);
    }

    public void updateNickName(String paramString1, String paramString2)
    {
        updateColumnValue(paramString1, "nickName", paramString2);
    }

    public void updateProvinceId(String paramString, int paramInt)
    {
        updateColumnValue(paramString, "provinceId", paramInt + "");
    }

    public void updateSex(String paramString1, String paramString2)
    {
        updateColumnValue(paramString1, "sex", paramString2);
    }

    public void updateUnLineTime(String paramString, long paramLong)
    {
        updateColumnValue(paramString, "offlineTime", paramLong + "");
    }

    public void updateUserCompany(Company paramCompany)
    {
        if (paramCompany != null) {}
        try
        {
            this.companyDao.createOrUpdate(paramCompany);
            return;
        }
        catch (SQLException paramCompany)
        {
            paramCompany.printStackTrace();
            LogFinalUtils.logForException(paramCompany);
        }
    }
}
