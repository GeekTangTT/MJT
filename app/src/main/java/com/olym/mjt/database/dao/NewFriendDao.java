package com.olym.mjt.database.dao;

import android.text.TextUtils;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.database.DBManager;
import com.olym.mjt.database.SQLiteHelper;
import com.olym.mjt.databean.bean.LocalContact;
import com.olym.mjt.databean.bean.NewFriendMessage;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.manager.ContactRefreshDoneListener;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.CharacterParser;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class NewFriendDao
{
    private static NewFriendDao instance = null;
    public Dao<NewFriendMessage, Integer> newFriendDao;

    private NewFriendDao()
    {
        try
        {
            this.newFriendDao = DaoManager.createDao(DBManager.getInstance().getDbHelper().getConnectionSource(), NewFriendMessage.class);
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

    public static final NewFriendDao getInstance()
    {
        if (instance == null) {}
        try
        {
            if (instance == null) {
                instance = new NewFriendDao();
            }
            return instance;
        }
        finally {}
    }

    public void ascensionNewFriend(NewFriendMessage paramNewFriendMessage, int paramInt)
    {
        createOrUpdateNewFriend(paramNewFriendMessage);
        Applog.systemOut("------ascensionNewFriend---- " + paramNewFriendMessage.getNickName());
        LocalContact localLocalContact = LocalContactDao.getInstance().getLocalContactsFromPhone(paramNewFriendMessage.getTelephone());
        if (localLocalContact == null)
        {
            localLocalContact = new LocalContact();
            localLocalContact.setNickName(paramNewFriendMessage.getNickName());
            localLocalContact.setTelephone(paramNewFriendMessage.getTelephone());
            localLocalContact.setRemarkName("");
            localLocalContact.setWholeSpell(new CharacterParser().getSellingWithPolyphone(paramNewFriendMessage.getNickName()));
            localLocalContact.setSimplespell(new CharacterParser().getSimpleSellingPolyphone(paramNewFriendMessage.getNickName()));
            LocalContactDao.getInstance().createOrUpdateContact(localLocalContact);
        }
        for (;;)
        {
            FriendDao.getInstance().createOrUpdateFriendByNewFriend(paramNewFriendMessage, paramInt);
            paramNewFriendMessage = MjtApplication.getInstance().getContactRefreshListenerList().iterator();
            while (paramNewFriendMessage.hasNext()) {
                ((ContactRefreshDoneListener)paramNewFriendMessage.next()).contactRerefeshDone();
            }
            localLocalContact.setNickName(paramNewFriendMessage.getNickName());
            if (TextUtils.isEmpty(localLocalContact.getRemarkName()))
            {
                localLocalContact.setWholeSpell(new CharacterParser().getSellingWithPolyphone(paramNewFriendMessage.getNickName()));
                localLocalContact.setSimplespell(new CharacterParser().getSimpleSellingPolyphone(paramNewFriendMessage.getNickName()));
            }
            LocalContactDao.getInstance().createOrUpdateContact(localLocalContact);
        }
    }

    public boolean createOrUpdateNewFriend(NewFriendMessage paramNewFriendMessage)
    {
        boolean bool1 = false;
        try
        {
            Object localObject = this.newFriendDao.queryBuilder().where().eq("ownerId", paramNewFriendMessage.getOwnerId()).and().eq("userId", paramNewFriendMessage.getUserId()).prepare();
            localObject = (NewFriendMessage)this.newFriendDao.queryForFirst((PreparedQuery)localObject);
            if (localObject != null) {
                paramNewFriendMessage.set_id(((NewFriendMessage)localObject).get_id());
            }
            paramNewFriendMessage = this.newFriendDao.createOrUpdate(paramNewFriendMessage);
            if (!paramNewFriendMessage.isCreated())
            {
                boolean bool2 = paramNewFriendMessage.isUpdated();
                if (!bool2) {}
            }
            else
            {
                bool1 = true;
            }
            return bool1;
        }
        catch (SQLException paramNewFriendMessage)
        {
            paramNewFriendMessage.printStackTrace();
            LogFinalUtils.logForException(paramNewFriendMessage);
        }
        return false;
    }

    public void deleteNewFriend(String paramString1, String paramString2, String paramString3)
    {
        DeleteBuilder localDeleteBuilder = this.newFriendDao.deleteBuilder();
        try
        {
            localDeleteBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("ibcdomain", paramString3);
            this.newFriendDao.delete(localDeleteBuilder.prepare());
            return;
        }
        catch (SQLException paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public List<NewFriendMessage> getAllNewFriendMsg(String paramString)
    {
        try
        {
            paramString = this.newFriendDao.queryBuilder().orderBy("timeSend", false).where().eq("ownerId", paramString).prepare();
            paramString = this.newFriendDao.query(paramString);
            return paramString;
        }
        catch (SQLException paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public int getMsgUnReadNumTotal(String paramString)
    {
        int j = 0;
        try
        {
            QueryBuilder localQueryBuilder = this.newFriendDao.queryBuilder();
            localQueryBuilder.selectRaw(new String[] { "sum(unReadNum)" });
            localQueryBuilder.where().eq("ownerId", paramString);
            paramString = this.newFriendDao.queryRaw(localQueryBuilder.prepareStatementString(), new String[0]);
            int i = j;
            if (paramString != null)
            {
                paramString = (String[])paramString.getFirstResult();
                i = j;
                if (paramString != null)
                {
                    i = j;
                    if (paramString.length > 0)
                    {
                        i = j;
                        if (paramString[0] != null) {
                            i = Integer.parseInt(paramString[0]);
                        }
                    }
                }
            }
            return i;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return 0;
    }

    @Deprecated
    public List<NewFriendMessage> getNearlyNewFriendMsg(String paramString, int paramInt1, int paramInt2)
    {
        try
        {
            paramString = this.newFriendDao.queryBuilder().orderBy("timeSend", false).limit(Long.valueOf(paramInt2)).offset(Long.valueOf(paramInt2 * paramInt1)).where().eq("ownerId", paramString).prepare();
            paramString = this.newFriendDao.query(paramString);
            return paramString;
        }
        catch (SQLException paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public NewFriendMessage getNewFriendById(String paramString1, String paramString2)
    {
        try
        {
            paramString1 = this.newFriendDao.queryBuilder().where().eq("ownerId", paramString1).and().eq("userId", paramString2).prepare();
            paramString1 = this.newFriendDao.query(paramString1);
            if ((paramString1 != null) && (paramString1.size() > 0))
            {
                paramString1 = (NewFriendMessage)paramString1.get(0);
                return paramString1;
            }
        }
        catch (SQLException paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public boolean isNewFriendRead(NewFriendMessage paramNewFriendMessage)
    {
        try
        {
            paramNewFriendMessage = this.newFriendDao.queryBuilder().where().eq("ownerId", paramNewFriendMessage.getOwnerId()).and().eq("userId", paramNewFriendMessage.getUserId()).prepare();
            paramNewFriendMessage = (NewFriendMessage)this.newFriendDao.queryForFirst(paramNewFriendMessage);
            if (paramNewFriendMessage == null) {
                return true;
            }
            boolean bool = paramNewFriendMessage.isRead();
            return bool;
        }
        catch (SQLException paramNewFriendMessage)
        {
            paramNewFriendMessage.printStackTrace();
            LogFinalUtils.logForException(paramNewFriendMessage);
        }
        return true;
    }

    public void markNewFriendRead(String paramString)
    {
        UpdateBuilder localUpdateBuilder = this.newFriendDao.updateBuilder();
        try
        {
            localUpdateBuilder.updateColumnValue("isRead", Boolean.valueOf(true));
            localUpdateBuilder.where().eq("ownerId", paramString).and().eq("isRead", Boolean.valueOf(false));
            this.newFriendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (SQLException paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
    }

    public void markUserMessageRead(String paramString)
    {
        UpdateBuilder localUpdateBuilder = this.newFriendDao.updateBuilder();
        try
        {
            Log.d("roamer", "........������������.......");
            localUpdateBuilder.updateColumnValue("unReadNum", Integer.valueOf(0));
            localUpdateBuilder.where().eq("ownerId", paramString);
            this.newFriendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
    }

    public void markUserMessageUnRead(String paramString1, String paramString2)
    {
        try
        {
            paramString1 = this.newFriendDao.queryBuilder().where().eq("ownerId", paramString1).and().eq("userId", paramString2).prepare();
            paramString1 = this.newFriendDao.query(paramString1);
            if ((paramString1 != null) && (paramString1.size() > 0))
            {
                paramString1 = (NewFriendMessage)paramString1.get(0);
                paramString1.setUnReadNum(paramString1.getUnReadNum() + 1);
                this.newFriendDao.update(paramString1);
            }
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }
}

