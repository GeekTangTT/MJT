package com.olym.mjt.database.dao;

import android.text.TextUtils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.database.DBManager;
import com.olym.mjt.database.SQLiteHelper;
import com.olym.mjt.databean.bean.AttentionUser;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.LocalContact;
import com.olym.mjt.utils.CharacterParser;
import com.olym.mjt.utils.sharedpreferencesutils.TableVersionSp;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

public class LocalContactDao
{
    private static LocalContactDao instance = null;
    public Dao<LocalContact, Integer> contactDao;
    private String insertUpdateLock = "insertUpdateLock";
    private SQLiteHelper mHelper;

    private LocalContactDao()
    {
        try
        {
            this.mHelper = DBManager.getInstance().getDbHelper();
            this.contactDao = DaoManager.createDao(this.mHelper.getConnectionSource(), LocalContact.class);
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

    public static final LocalContactDao getInstance()
    {
        if (instance == null) {}
        try
        {
            if (instance == null) {
                instance = new LocalContactDao();
            }
            return instance;
        }
        finally {}
    }

    public void addLocalContact(final AttentionUser paramAttentionUser)
    {
        final int i = TableVersionSp.getInstanse().getFriendTableVersion("100000000") + 1;
        try
        {
            this.contactDao.callBatchTasks(new Callable()
            {
                public Void call()
                        throws Exception
                {
                    if (paramAttentionUser == null) {}
                    while (LocalContactDao.this.getLocalContactsFromPhone(paramAttentionUser.getToTelephone()) != null) {
                        return null;
                    }
                    LocalContact localLocalContact = new LocalContact();
                    String str1 = paramAttentionUser.getToNickName();
                    localLocalContact.setNickName(str1);
                    String str2 = paramAttentionUser.getRemarkName();
                    if (TextUtils.isEmpty(str2))
                    {
                        localLocalContact.setWholeSpell(new CharacterParser().getSellingWithPolyphone(str1));
                        localLocalContact.setSimplespell(new CharacterParser().getSimpleSellingPolyphone(str1));
                        localLocalContact.setRemarkName("");
                    }
                    for (;;)
                    {
                        localLocalContact.setTelephone(paramAttentionUser.getToTelephone());
                        localLocalContact.setVersion(i);
                        localLocalContact.setIssecret(0);
                        try
                        {
                            LocalContactDao.this.contactDao.createOrUpdate(localLocalContact);
                            return null;
                        }
                        catch (SQLException localSQLException)
                        {
                            LogFinalUtils.logForException(localSQLException);
                            localSQLException.printStackTrace();
                            return null;
                        }
                        localSQLException.setWholeSpell(new CharacterParser().getSellingWithPolyphone(str2));
                        localSQLException.setSimplespell(new CharacterParser().getSimpleSellingPolyphone(str2));
                        localSQLException.setRemarkName(str2);
                    }
                }
            });
            TableVersionSp.getInstanse().setFriendTableVersion("100000000", i);
            return;
        }
        catch (Exception paramAttentionUser)
        {
            for (;;)
            {
                paramAttentionUser.printStackTrace();
                LogFinalUtils.logForException(paramAttentionUser);
            }
        }
    }

    public void addLocalContacts(final List<LocalContact> paramList)
    {
        final int i = TableVersionSp.getInstanse().getFriendTableVersion("100000000") + 1;
        try
        {
            this.contactDao.callBatchTasks(new Callable()
            {
                public Void call()
                        throws Exception
                {
                    if ((paramList != null) && (paramList.size() > 0))
                    {
                        int i = 0;
                        if (i < paramList.size())
                        {
                            LocalContact localLocalContact1 = (LocalContact)paramList.get(i);
                            if (localLocalContact1 == null) {}
                            for (;;)
                            {
                                i += 1;
                                break;
                                LocalContact localLocalContact2 = LocalContactDao.this.getLocalContactsFromPhone(localLocalContact1.getTelephone());
                                if (localLocalContact2 == null)
                                {
                                    try
                                    {
                                        localLocalContact1.setVersion(i);
                                        LocalContactDao.this.contactDao.createOrUpdate(localLocalContact1);
                                    }
                                    catch (SQLException localSQLException1)
                                    {
                                        LogFinalUtils.logForException(localSQLException1);
                                    }
                                }
                                else
                                {
                                    if (localLocalContact2.getLocalId() == null) {
                                        localLocalContact2.setLocalId(localSQLException1.getLocalId());
                                    }
                                    if (TextUtils.isEmpty(localLocalContact2.getRemarkName()))
                                    {
                                        localLocalContact2.setRemarkName(localSQLException1.getRemarkName());
                                        localLocalContact2.setWholeSpell(new CharacterParser().getSellingWithPolyphone(localLocalContact2.getRemarkName()));
                                        localLocalContact2.setSimplespell(new CharacterParser().getSimpleSellingPolyphone(localLocalContact2.getRemarkName()));
                                    }
                                    try
                                    {
                                        localLocalContact2.setVersion(i);
                                        LocalContactDao.this.contactDao.createOrUpdate(localLocalContact2);
                                    }
                                    catch (SQLException localSQLException2)
                                    {
                                        LogFinalUtils.logForException(localSQLException2);
                                    }
                                }
                            }
                        }
                    }
                    return null;
                }
            });
            TableVersionSp.getInstanse().setFriendTableVersion("100000000", i);
            return;
        }
        catch (Exception paramList)
        {
            for (;;)
            {
                LogFinalUtils.logForException(paramList);
            }
        }
    }

    public boolean createOrUpdateContact(LocalContact paramLocalContact)
    {
        boolean bool1 = false;
        try
        {
            paramLocalContact = this.contactDao.createOrUpdate(paramLocalContact);
            if (!paramLocalContact.isCreated())
            {
                boolean bool2 = paramLocalContact.isUpdated();
                if (!bool2) {}
            }
            else
            {
                bool1 = true;
            }
            return bool1;
        }
        catch (SQLException paramLocalContact)
        {
            paramLocalContact.printStackTrace();
            LogFinalUtils.logForException(paramLocalContact);
        }
        return false;
    }

    public void deleteAll()
    {
        Iterator localIterator = getAllLocalContact().iterator();
        while (localIterator.hasNext()) {
            deleteLocalContact((LocalContact)localIterator.next());
        }
    }

    public void deleteLocalContact(LocalContact paramLocalContact)
    {
        try
        {
            this.contactDao.delete(paramLocalContact);
            return;
        }
        catch (SQLException paramLocalContact)
        {
            paramLocalContact.printStackTrace();
            LogFinalUtils.logForException(paramLocalContact);
        }
    }

    public boolean exitLocalContact(String paramString)
    {
        try
        {
            paramString = this.contactDao.queryBuilder().where().in("telephone", (Object[])new String[] { paramString }).prepare();
            paramString = (LocalContact)this.contactDao.queryForFirst(paramString);
            if (paramString != null) {
                return true;
            }
        }
        catch (SQLException paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
        return false;
    }

    public List<LocalContact> getAllLocalContact()
    {
        try
        {
            Object localObject = this.contactDao.queryBuilder().prepare();
            localObject = this.contactDao.query((PreparedQuery)localObject);
            return (List<LocalContact>)localObject;
        }
        catch (SQLException localSQLException)
        {
            localSQLException.printStackTrace();
            LogFinalUtils.logForException(localSQLException);
        }
        return null;
    }

    public LocalContact getLocalContactsFromPhone(String paramString)
    {
        try
        {
            paramString = this.contactDao.queryBuilder().where().in("telephone", (Object[])new String[] { paramString }).prepare();
            paramString = (LocalContact)this.contactDao.queryForFirst(paramString);
            return paramString;
        }
        catch (SQLException paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public String getLocalRemarkName(String paramString)
    {
        try
        {
            Object localObject = this.contactDao.queryBuilder().where().in("telephone", (Object[])new String[] { paramString }).prepare();
            LocalContact localLocalContact = (LocalContact)this.contactDao.queryForFirst((PreparedQuery)localObject);
            localObject = paramString;
            if (localLocalContact != null) {
                localObject = localLocalContact.getRemarkName();
            }
            return (String)localObject;
        }
        catch (SQLException localSQLException)
        {
            localSQLException.printStackTrace();
            LogFinalUtils.logForException(localSQLException);
        }
        return paramString;
    }

    public boolean queryLocalContact(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = FriendDao.getInstance().getFriend(paramString1, paramString2, paramString3);
        if (paramString1 != null)
        {
            paramString1 = paramString1.getToTelephone();
            try
            {
                paramString1 = this.contactDao.queryBuilder().where().in("telephone", new Object[] { paramString1 }).prepare();
                paramString1 = (LocalContact)this.contactDao.queryForFirst(paramString1);
                if (paramString1 != null) {
                    return true;
                }
            }
            catch (SQLException paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return false;
    }

    public void updateLocalContact(String paramString, int paramInt)
    {
        UpdateBuilder localUpdateBuilder = this.contactDao.updateBuilder();
        try
        {
            localUpdateBuilder.updateColumnValue("issecret", Integer.valueOf(paramInt));
            localUpdateBuilder.where().eq("localId", paramString);
            this.contactDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (SQLException paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
    }
}
