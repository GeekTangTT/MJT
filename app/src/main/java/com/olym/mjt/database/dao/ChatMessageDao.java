package com.olym.mjt.database.dao;

import android.text.TextUtils;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.sqlcipher.android.DatabaseTableConfigUtil;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.database.DBManager;
import com.olym.mjt.database.SQLiteHelper;
import com.olym.mjt.database.SQLiteRawUtil;
import com.olym.mjt.database.UnlimitDaoManager;
import com.olym.mjt.databean.bean.ChatMessage;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.DateUtil;
import com.olym.mjt.utils.TimeUtils;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ChatMessageDao
{
    private static ChatMessageDao instance = null;
    private String currentMessageId = "";
    ReentrantLock lock = new ReentrantLock();
    private ConcurrentMap<String, Dao<ChatMessage, Integer>> mDaoMap = new ConcurrentHashMap();
    private SQLiteHelper mHelper = DBManager.getInstance().getDbHelper();
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

    public static void close()
    {
        instance = null;
    }

    public static final ChatMessageDao getInstance()
    {
        if (instance == null) {}
        try
        {
            if (instance == null) {
                instance = new ChatMessageDao();
            }
            return instance;
        }
        finally {}
    }

    public boolean IsNewSmsMessage(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2.replace("+", "_"));
        if (paramString1 == null) {}
        for (;;)
        {
            return false;
            paramString2 = paramString1.queryBuilder();
            try
            {
                paramString2.where().eq("packetId", paramString3).and().isNull("needfire");
                paramString1 = paramString1.query(paramString2.prepare());
                if (paramString1 != null)
                {
                    int i = paramString1.size();
                    if (i > 0) {}
                }
                else
                {
                    return true;
                }
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
            }
        }
        return false;
    }

    public void deleteMuchMessage(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
    {
        try
        {
            paramString1 = getDao(paramString1, paramString2).deleteBuilder();
            paramString1.where().gt("timeSend", Integer.valueOf(paramInt1)).and().lt("timeSend", Integer.valueOf(paramInt2)).and().eq("fromUserId", paramString3);
            paramString1.delete();
            return;
        }
        catch (SQLException paramString1)
        {
            paramString1.printStackTrace();
        }
    }

    public boolean deleteMuchMessage(String paramString1, String paramString2, ChatMessage paramChatMessage)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {}
        for (;;)
        {
            return false;
            try
            {
                List localList = paramString1.queryForEq("packetId", paramChatMessage.getPacketId());
                if ((localList != null) && (localList.size() > 0))
                {
                    paramString1.delete(paramChatMessage);
                    SearchDao.getInstance().deleteMuchMsg(paramString2, paramChatMessage.getPacketId());
                    return true;
                }
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return false;
    }

    public void deleteMuchMessageTable(String paramString1, String paramString2)
    {
        paramString1 = "msg_" + paramString1 + paramString2;
        if (this.mDaoMap.containsKey(paramString1)) {
            this.mDaoMap.remove(paramString1);
        }
        if (SQLiteRawUtil.isTableExist(this.mHelper.getWritableDatabase(AppSpUtil.getInstanse().getDBPassword()), paramString1)) {
            SQLiteRawUtil.dropTable(this.mHelper.getWritableDatabase(AppSpUtil.getInstanse().getDBPassword()), paramString1);
        }
        SearchDao.getInstance().deleteTableMsg(paramString2);
    }

    public boolean deleteMuchMsg(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {}
        for (;;)
        {
            return false;
            try
            {
                List localList = paramString1.queryForEq("packetId", paramString3);
                if ((localList != null) && (localList.size() > 0))
                {
                    paramString1.delete(localList.get(0));
                    SearchDao.getInstance().deleteMuchMsg(paramString2, paramString3);
                    return true;
                }
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return false;
    }

    public boolean deleteSingleChatMessage(String paramString1, String paramString2, ChatMessage paramChatMessage, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {}
        for (;;)
        {
            return false;
            try
            {
                List localList = paramString1.queryForEq("packetId", paramChatMessage.getPacketId());
                if ((localList != null) && (localList.size() > 0))
                {
                    paramString1.delete(paramChatMessage);
                    SearchDao.getInstance().deleteSingleMsg(paramString2, paramString3, paramChatMessage.getPacketId());
                    return true;
                }
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return false;
    }

    public void deleteSingleMessage(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
    {
        try
        {
            paramString1 = getDao(paramString1, paramString2, paramString3).deleteBuilder();
            paramString1.where().gt("timeSend", Integer.valueOf(paramInt1)).and().lt("timeSend", Integer.valueOf(paramInt2));
            paramString1.delete();
            return;
        }
        catch (SQLException paramString1)
        {
            paramString1.printStackTrace();
        }
    }

    public void deleteSingleMessageTable(String paramString1, String paramString2, String paramString3)
    {
        String str = paramString3;
        if (!TextUtils.isEmpty(paramString3)) {
            str = paramString3.split("[.]")[0];
        }
        paramString1 = "msg_" + paramString1 + paramString2 + "_" + str;
        if (this.mDaoMap.containsKey(paramString1)) {
            this.mDaoMap.remove(paramString1);
        }
        if (SQLiteRawUtil.isTableExist(this.mHelper.getWritableDatabase(AppSpUtil.getInstanse().getDBPassword()), paramString1)) {
            SQLiteRawUtil.dropTable(this.mHelper.getWritableDatabase(AppSpUtil.getInstanse().getDBPassword()), paramString1);
        }
        SearchDao.getInstance().deleteTableMsg(paramString2);
    }

    public boolean deleteSingleMsg(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        paramString1 = getDao(paramString1, paramString2, paramString4);
        if (paramString1 == null) {}
        for (;;)
        {
            return false;
            try
            {
                List localList = paramString1.queryForEq("packetId", paramString3);
                if ((localList != null) && (localList.size() > 0))
                {
                    paramString1.delete(localList.get(0));
                    SearchDao.getInstance().deleteSingleMsg(paramString2, paramString4, paramString3);
                    return true;
                }
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return false;
    }

    public List<ChatMessage> getAllFIleMuchChatMessage(String paramString1, String paramString2, int paramInt)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().eq("type", Integer.valueOf(paramInt));
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getAllFIleSingleChatMessage(String paramString1, String paramString2, String paramString3, int paramInt)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().eq("type", Integer.valueOf(paramInt));
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getAllMuchChatMessages(String paramString1, String paramString2)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getAllPhotosMuchChatMessage(String paramString1, String paramString2)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().eq("type", Integer.valueOf(2)).or().eq("type", Integer.valueOf(6));
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getAllPhotosSingleChatMessage(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().eq("type", Integer.valueOf(2)).or().eq("type", Integer.valueOf(6));
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getAllSingleChatMessages(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getAllSmsChatMessage(String paramString1, String paramString2, int paramInt)
    {
        paramString1 = getDao(paramString1, paramString2.replace("+", "_"));
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        if (paramInt != 0) {}
        try
        {
            paramString2.where().lt("timeSend", Integer.valueOf(paramInt));
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public Dao<ChatMessage, Integer> getDao(String paramString1, String paramString2)
    {
        if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2))) {}
        do
        {
            return null;
            str = "msg_" + paramString1 + paramString2;
        } while ((this.mDaoMap == null) || (str == null));
        if (this.mDaoMap.containsKey(str)) {
            return (Dao)this.mDaoMap.get(str);
        }
        paramString2 = null;
        paramString1 = paramString2;
        try
        {
            DatabaseTableConfig localDatabaseTableConfig = DatabaseTableConfigUtil.fromClass(this.mHelper.getConnectionSource(), ChatMessage.class);
            paramString1 = paramString2;
            localDatabaseTableConfig.setTableName(str);
            paramString1 = paramString2;
            SQLiteRawUtil.createTableIfNotExist(this.mHelper.getWritableDatabase(AppSpUtil.getInstanse().getDBPassword()), str, SQLiteRawUtil.getCreateChatMessageTableSql(str));
            paramString1 = paramString2;
            paramString2 = UnlimitDaoManager.createDao(this.mHelper.getConnectionSource(), localDatabaseTableConfig);
            paramString1 = paramString2;
            Applog.systemOut("----change room name===create table success======tableName==" + str);
            paramString1 = paramString2;
            Applog.info("---change room name===create table success======tableName==" + str);
            paramString1 = paramString2;
        }
        catch (Exception paramString2)
        {
            for (;;)
            {
                paramString2.printStackTrace();
                LogFinalUtils.logForException(paramString2);
                Applog.systemOut("---change room name===create table exception===" + paramString2.getMessage() + "===tableName==" + str);
                Applog.info("---change room name===create table exception===" + paramString2.getMessage() + "===tableName==" + str);
            }
        }
        if (paramString1 != null) {
            this.mDaoMap.put(str, paramString1);
        }
        return paramString1;
    }

    public Dao<ChatMessage, Integer> getDao(String paramString1, String paramString2, String paramString3)
    {
        String str = paramString3;
        if (paramString3 == null) {
            str = "null";
        }
        if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2))) {
            return null;
        }
        if (!str.equals("null"))
        {
            paramString3 = str;
            if (!TextUtils.isEmpty(str)) {}
        }
        else
        {
            paramString3 = UserSpUtil.getInstanse().getIBCDomain();
        }
        paramString3 = paramString3.split("[.]")[0];
        paramString2 = "msg_" + paramString1 + paramString2 + "_" + paramString3;
        if (this.mDaoMap.containsKey(paramString2)) {
            return (Dao)this.mDaoMap.get(paramString2);
        }
        try
        {
            paramString1 = DatabaseTableConfigUtil.fromClass(this.mHelper.getConnectionSource(), ChatMessage.class);
            paramString1.setTableName(paramString2);
            SQLiteRawUtil.createTableIfNotExist(this.mHelper.getWritableDatabase(AppSpUtil.getInstanse().getDBPassword()), paramString2, SQLiteRawUtil.getCreateChatMessageTableSql(paramString2));
            paramString1 = UnlimitDaoManager.createDao(this.mHelper.getConnectionSource(), paramString1);
            if (paramString1 != null) {
                this.mDaoMap.put(paramString2, paramString1);
            }
            Applog.info("checkDomain9:,after create table put Daomap");
            return paramString1;
        }
        catch (Exception paramString1)
        {
            for (;;)
            {
                paramString1.printStackTrace();
                Applog.info("checkDomain9:,create table error:" + paramString1.getMessage());
                LogFinalUtils.logForException(paramString1);
                paramString1 = null;
            }
        }
    }

    public List<ChatMessage> getMuchChatMessages(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        if (paramInt1 != 0) {}
        for (;;)
        {
            try
            {
                paramString2.where().le("timeSend", Integer.valueOf(paramInt1)).and().eq("needfire", Integer.valueOf(0));
                paramString2.orderBy("timeSend", false);
                paramString2.limit(Long.valueOf(paramInt3));
                paramString2.offset(paramInt2);
                paramString2.distinct();
                return paramString1.query(paramString2.prepare());
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
            paramString2.where().isNotNull("content");
        }
        return null;
    }

    public List<ChatMessage> getMuchChatMessages(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().gt("timeSend", Integer.valueOf(paramInt1)).and().le("timeSend", Integer.valueOf(paramInt2)).and().eq("fromUserId", paramString3);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getMuchFireChatMessages(String paramString1, String paramString2, int paramInt)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        if (paramInt != 0) {}
        try
        {
            paramString2.where().gt("timeSend", Integer.valueOf(paramInt));
            paramString2.where().eq("needfire", Integer.valueOf(1));
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getMuchMessage(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            if (!paramString3.equals("")) {
                paramString2.where().eq("packetId", paramString3);
            }
            paramString2.orderBy("_id", false);
            paramString2.limit(Long.valueOf(1L));
            paramString2.offset(Long.valueOf(0L));
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public String getMuchNickName(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2);
        try
        {
            paramString1 = ((ChatMessage)paramString1.queryForFirst(paramString1.queryBuilder().where().eq("fromUserId", paramString3).prepare())).getFromUserName();
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getSingleChatMessages(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        if (paramInt1 != 0) {}
        try
        {
            paramString2.where().le("timeSend", Integer.valueOf(paramInt1));
            paramString2.orderBy("timeSend", false);
            paramString2.limit(Long.valueOf(paramInt3));
            paramString2.offset(paramInt2);
            paramString2.distinct();
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getSingleChatMessages(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().gt("timeSend", Integer.valueOf(paramInt1)).and().le("timeSend", Integer.valueOf(paramInt2)).and().eq("fromUserId", paramString4);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getSingleMessage(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            if (!paramString4.equals("")) {
                paramString2.where().eq("packetId", paramString4);
            }
            paramString2.orderBy("_id", false);
            paramString2.limit(Long.valueOf(1L));
            paramString2.offset(Long.valueOf(0L));
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public String getSingleNickName(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        paramString1 = getDao(paramString1, paramString2, paramString4);
        try
        {
            paramString1 = ((ChatMessage)paramString1.queryForFirst(paramString1.queryBuilder().where().eq("fromUserId", paramString3).prepare())).getFromUserName();
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> getSmsChatMessages(String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        paramString1 = getDao(paramString1, paramString2.replace("+", "_"));
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        if (paramInt1 != 0) {}
        for (;;)
        {
            try
            {
                paramString2.where().lt("timeSend", Integer.valueOf(paramInt1)).and().eq("needfire", Integer.valueOf(0));
                paramString2.orderBy("timeSend", false);
                paramString2.limit(Long.valueOf(paramInt2));
                paramString2.offset(Long.valueOf(0L));
                paramString2.distinct();
                return paramString1.query(paramString2.prepare());
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
            paramString2.where().isNotNull("content");
        }
        return null;
    }

    public int isFireSmsMessage(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2);
        try
        {
            int i = ((ChatMessage)paramString1.queryForFirst(paramString1.queryBuilder().where().eq("packetId", paramString3).prepare())).getNeedfire();
            return i;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return 0;
    }

    public boolean isMuchMessageUpload(String paramString1, String paramString2, int paramInt)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {}
        for (;;)
        {
            return false;
            try
            {
                paramString1 = (ChatMessage)paramString1.queryForFirst(paramString1.queryBuilder().where().idEq(Integer.valueOf(paramInt)).prepare());
                if (paramString1 != null)
                {
                    boolean bool = paramString1.isUpload();
                    return bool;
                }
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return false;
    }

    public boolean isSingleMessageUpload(String paramString1, String paramString2, String paramString3, int paramInt)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {}
        for (;;)
        {
            return false;
            try
            {
                paramString1 = (ChatMessage)paramString1.queryForFirst(paramString1.queryBuilder().where().idEq(Integer.valueOf(paramInt)).prepare());
                if (paramString1 != null)
                {
                    boolean bool = paramString1.isUpload();
                    return bool;
                }
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return false;
    }

    public List<ChatMessage> queryAllContentMuchChatMessages(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().like("content", "%" + paramString3 + "%").and().eq("type", Integer.valueOf(1));
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> queryAllContentSingleChatMessages(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().like("content", "%" + paramString4 + "%").and().eq("type", Integer.valueOf(1));
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> queryAllFilePathMuchChatMessages(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().like("filePath", "%" + paramString3 + "%").and().eq("type", Integer.valueOf(9));
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> queryAllFilePathSingleChatMessages(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().like("filePath", "%" + paramString4 + "%").and().eq("type", Integer.valueOf(9));
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> queryMuchChatMessages(String paramString1, String paramString2, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().like("content", "%" + paramString3 + "%");
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<ChatMessage> querySingleChatMessages(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {
            return null;
        }
        paramString2 = paramString1.queryBuilder();
        try
        {
            paramString2.where().like("content", "%" + paramString4 + "%");
            paramString2.orderBy("timeSend", false);
            paramString1 = paramString1.query(paramString2.prepare());
            return paramString1;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public void removeTablename(String paramString)
    {
        if (this.mDaoMap.containsKey(paramString)) {
            this.mDaoMap.remove(paramString);
        }
    }

    public boolean saveNewMuchChatMessage(String paramString1, String paramString2, ChatMessage paramChatMessage, boolean paramBoolean)
    {
        Object localObject = getDao(paramString1, paramString2);
        if (localObject == null) {
            return false;
        }
        try
        {
            List localList = ((Dao)localObject).queryForEq("packetId", paramChatMessage.getPacketId());
            if ((localList != null) && (localList.size() > 0)) {
                return false;
            }
            ((Dao)localObject).create(paramChatMessage);
            Applog.info("----saveNewMuchChatMessage create message" + paramChatMessage.getPacketId() + "===frindId==" + paramString2);
            Applog.systemOut("----saveNewMuchChatMessage create message" + paramChatMessage.getPacketId() + "===frindId==" + paramString2);
            if ((paramChatMessage.getFire() == 0) && (paramChatMessage.getType() == 1))
            {
                localObject = FriendDao.getInstance().getFriend(paramString1, paramString2);
                SearchDao.getInstance().insertQueryInfo(paramChatMessage.getPacketId(), 2, 1, paramString2, ((Friend)localObject).getShowName(), paramChatMessage.getContent(), ((Friend)localObject).getShowName(), "", paramChatMessage.getTimeSend(), paramChatMessage.getFilePath());
            }
            boolean bool = this.lock.tryLock(500L, TimeUnit.MILLISECONDS);
            if (bool) {
                try
                {
                    int i = FriendDao.getInstance().getLastTimeSend(paramString1, paramString2);
                    if ((paramChatMessage.getTimeSend() > i) || (paramChatMessage.isMySend())) {
                        FriendDao.getInstance().updateLastChatMessage(paramString1, paramString2, paramChatMessage, true, false, paramBoolean);
                    }
                }
                finally
                {
                    this.lock.unlock();
                }
            }
            Applog.info("saveNewMuchChatMessage getLock Failed");
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
            Applog.systemOut("===change room name===saveNewMuchChatMessage=" + paramString1.getMessage());
            Applog.info("----change room name===saveNewMuchChatMessage=" + paramString1.getMessage());
            return false;
        }
        return true;
    }

    public boolean saveNewMuchChatMessageInTransaction(final String paramString1, final String paramString2, final ChatMessage paramChatMessage, final boolean paramBoolean)
    {
        final Dao localDao = getDao(paramString1, paramString2);
        if (localDao == null) {
            return false;
        }
        TransactionManager localTransactionManager = new TransactionManager(this.mHelper.getConnectionSource());
        paramString1 = new Callable()
        {
            public Boolean call()
                    throws Exception
            {
                Object localObject = localDao.queryForEq("packetId", paramChatMessage.getPacketId());
                if ((localObject != null) && (((List)localObject).size() > 0)) {
                    return Boolean.valueOf(false);
                }
                localDao.create(paramChatMessage);
                Applog.systemOut("===change room name===saveNewMuchChatMessage create message" + paramChatMessage.getContent() + "===frindId==" + paramString2);
                if ((paramChatMessage.getFire() == 0) && (paramChatMessage.getType() == 1))
                {
                    localObject = FriendDao.getInstance().getFriend(paramString1, paramString2);
                    SearchDao.getInstance().insertQueryInfo(paramChatMessage.getPacketId(), 2, 1, paramString2, ((Friend)localObject).getShowName(), paramChatMessage.getContent(), ((Friend)localObject).getShowName(), "", paramChatMessage.getTimeSend(), paramChatMessage.getFilePath());
                }
                int i = FriendDao.getInstance().getLastTimeSend(paramString1, paramString2);
                if (paramChatMessage.getTimeSend() > i) {
                    FriendDao.getInstance().updateLastChatMessage(paramString1, paramString2, paramChatMessage, true, false, paramBoolean);
                }
                return Boolean.valueOf(true);
            }
        };
        try
        {
            paramBoolean = ((Boolean)localTransactionManager.callInTransaction(paramString1)).booleanValue();
            return paramBoolean;
        }
        catch (SQLException paramString1)
        {
            for (;;)
            {
                paramBoolean = false;
                paramString1.printStackTrace();
            }
        }
    }

    public boolean saveNewSingleChatMessage(String paramString1, String paramString2, ChatMessage paramChatMessage, String paramString3)
    {
        Object localObject = getDao(paramString1, paramString2, paramString3);
        if (localObject == null)
        {
            Applog.systemOut("saveNewSingleChatMessage dao is null");
            return false;
        }
        try
        {
            List localList = ((Dao)localObject).queryForEq("packetId", paramChatMessage.getPacketId());
            if ((localList != null) && (localList.size() > 0))
            {
                Applog.systemOut("saveNewSingleChatMessage query chatSingleMessage");
                return false;
            }
            ((Dao)localObject).create(paramChatMessage);
            Applog.info("saveNewSingleChatMessage create message" + paramChatMessage.getPacketId() + "===frindId==" + paramString2);
            if ((paramChatMessage.getFire() == 0) && (paramChatMessage.getType() == 1))
            {
                localObject = FriendDao.getInstance().getFriend(paramString1, paramString2, paramString3);
                SearchDao.getInstance().insertQueryInfo(paramChatMessage.getPacketId(), 2, 0, paramString2, ((Friend)localObject).getToTelephone(), paramChatMessage.getContent(), ((Friend)localObject).getShowName(), paramString3, paramChatMessage.getTimeSend(), paramChatMessage.getFilePath());
            }
            FriendDao.getInstance().updateLastChatMessage(paramString1, paramString2, paramChatMessage, false, false, false);
            Applog.systemOut("saveNewSingleChatMessage query chatSingleMessage finish");
            return true;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            Applog.systemOut("saveNewSingleChatMessage:" + paramString1.getMessage());
            LogFinalUtils.logForException(paramString1);
        }
        return false;
    }

    public boolean saveNewSmsMessage(String paramString1, String paramString2, ChatMessage paramChatMessage, String paramString3)
    {
        paramString2 = paramString2.replace("+", "_");
        Dao localDao = getDao(paramString1, paramString2);
        if (localDao == null) {
            return false;
        }
        try
        {
            List localList = localDao.queryForEq("packetId", paramChatMessage.getPacketId());
            if ((localList != null) && (localList.size() > 0)) {
                return false;
            }
            localDao.create(paramChatMessage);
            paramString2 = paramString2.replace("_", "+");
            int i = FriendDao.getInstance().getLastSmsTimeSend(paramString1, paramString2);
            if (paramChatMessage.getTimeSend() > i) {
                FriendDao.getInstance().updateLastSms(paramString1, paramString2, paramChatMessage, paramString3, false);
            }
            return true;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return false;
    }

    public void updateMuchMessageContent(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("content", paramString3);
            paramString2.updateColumnValue("isDecrypted", Integer.valueOf(paramInt2));
            paramString2.where().idEq(Integer.valueOf(paramInt1));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateMuchMessageDownloadState(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("isDownload", Integer.valueOf(paramInt2));
            paramString2.updateColumnValue("isOnDownloading", Integer.valueOf(paramInt3));
            paramString2.updateColumnValue("filePath", paramString3);
            paramString2.where().idEq(Integer.valueOf(paramInt1));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateMuchMessageFilePath(String paramString1, String paramString2, int paramInt, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("filePath", paramString3);
            paramString2.where().idEq(Integer.valueOf(paramInt));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateMuchMessageRead(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3, String paramString4, String paramString5)
    {
        Dao localDao = getDao(paramString1, paramString2);
        if (localDao == null)
        {
            Log.e("wzw", "������������");
            return;
        }
        UpdateBuilder localUpdateBuilder = localDao.updateBuilder();
        try
        {
            Log.e("wzw", "������������msg_" + paramString1 + paramString2);
            Log.e("wzw", "������������" + paramString3);
            localUpdateBuilder.updateColumnValue("isSend", Integer.valueOf(paramInt1));
            localUpdateBuilder.updateColumnValue("needfire", Integer.valueOf(paramInt2));
            localUpdateBuilder.updateColumnValue("myneedfire", Integer.valueOf(paramInt3));
            localUpdateBuilder.updateColumnValue("content", paramString4);
            localUpdateBuilder.updateColumnValue("filePath", paramString5);
            localUpdateBuilder.where().eq("packetId", paramString3);
            localDao.update(localUpdateBuilder.prepare());
            Log.e("wzw", "������������");
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateMuchMessageReadState(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("isRead", Boolean.valueOf(paramBoolean));
            paramString2.where().idEq(Integer.valueOf(paramInt));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateMuchMessageSendState(String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("messageState", Integer.valueOf(paramInt2));
            paramString2.updateColumnValue("timeReceive", Integer.valueOf(DateUtil.sk_time_current_time()));
            paramString2.where().idEq(Integer.valueOf(paramInt1));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateMuchMessageType(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {}
        for (;;)
        {
            return;
            int i = 0;
            while (i < 10)
            {
                try
                {
                    paramString2 = paramString1.queryForEq("packetId", paramString3);
                    if ((paramString2 != null) && (paramString2.size() > 0))
                    {
                        paramString2 = paramString1.updateBuilder();
                        try
                        {
                            paramString2.updateColumnValue("content", paramString4);
                            if (paramInt2 != 0) {
                                paramString2.updateColumnValue("timeSend", Integer.valueOf(paramInt2));
                            }
                            paramString2.updateColumnValue("type", Integer.valueOf(paramInt1));
                            paramString2.updateColumnValue("thumbnail", "");
                            paramString2.updateColumnValue("filePath", "");
                            paramString2.where().eq("packetId", paramString3);
                            paramString1.update(paramString2.prepare());
                            return;
                        }
                        catch (Exception paramString1)
                        {
                            LogFinalUtils.logForException(paramString1);
                            return;
                        }
                    }
                    i += 1;
                }
                catch (SQLException paramString1)
                {
                    LogFinalUtils.logForException(paramString1);
                    return;
                }
                if (i == 10)
                {
                    paramString2 = new ChatMessage();
                    paramString2.setType(paramInt1);
                    paramString2.setPacketId(paramString3);
                    paramString2.setContent(paramString4);
                    paramString2.setTimeSend(TimeUtils.sk_time_current_time());
                    paramString1.create(paramString2);
                }
                try
                {
                    Thread.sleep(200L);
                }
                catch (InterruptedException paramString2)
                {
                    paramString2.printStackTrace();
                }
            }
        }
    }

    public void updateMuchMessageUploadState(String paramString1, String paramString2, int paramInt, boolean paramBoolean, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2);
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("isUpload", Boolean.valueOf(paramBoolean));
            paramString2.updateColumnValue("content", paramString3);
            paramString2.where().idEq(Integer.valueOf(paramInt));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateMuchNickName(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        paramString1 = getDao(paramString1, paramString2);
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.where().eq("fromUserId", paramString3);
            paramString2.updateColumnValue("fromUserName", paramString4);
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateNickName(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        paramString1 = getDao(paramString1, paramString2);
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.where().eq("fromUserId", paramString3);
            paramString2.updateColumnValue("fromUserName", paramString4);
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
        }
    }

    public void updateSingleMessageContent(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, int paramInt2)
    {
        paramString1 = getDao(paramString1, paramString2, paramString4);
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("content", paramString3);
            paramString2.updateColumnValue("isDecrypted", Integer.valueOf(paramInt2));
            paramString2.where().idEq(Integer.valueOf(paramInt1));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateSingleMessageDownloadState(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, String paramString3, String paramString4)
    {
        paramString1 = getDao(paramString1, paramString2, paramString4);
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("isDownload", Integer.valueOf(paramInt2));
            paramString2.updateColumnValue("isOnDownloading", Integer.valueOf(paramInt3));
            paramString2.updateColumnValue("filePath", paramString3);
            paramString2.where().idEq(Integer.valueOf(paramInt1));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateSingleMessageFilePath(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
    {
        paramString1 = getDao(paramString1, paramString2, paramString4);
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("filePath", paramString3);
            paramString2.where().idEq(Integer.valueOf(paramInt));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateSingleMessageRead(String paramString1, String paramString2, String paramString3, int paramInt1, String paramString4, int paramInt2, int paramInt3)
    {
        paramString4 = getDao(paramString1, paramString2, paramString4);
        if (paramString4 == null)
        {
            Log.e("wzw", "������������");
            return;
        }
        UpdateBuilder localUpdateBuilder = paramString4.updateBuilder();
        try
        {
            Log.e("wzw", "������������msg_" + paramString1 + paramString2);
            Log.e("wzw", "������������" + paramString3);
            localUpdateBuilder.updateColumnValue("isSend", Integer.valueOf(paramInt1));
            localUpdateBuilder.updateColumnValue("needfire", Integer.valueOf(paramInt2));
            localUpdateBuilder.updateColumnValue("myneedfire", Integer.valueOf(paramInt3));
            localUpdateBuilder.where().eq("packetId", paramString3);
            paramString4.update(localUpdateBuilder.prepare());
            Log.e("wzw", "������������");
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateSingleMessageReadState(String paramString1, String paramString2, int paramInt, boolean paramBoolean, String paramString3)
    {
        paramString1 = getDao(paramString1, paramString2, paramString3);
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("isRead", Boolean.valueOf(paramBoolean));
            paramString2.where().idEq(Integer.valueOf(paramInt));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateSingleMessageSendState(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3)
    {
        if (paramString3 == null) {}
        do
        {
            return;
            paramString1 = getDao(paramString1, paramString2, paramString3);
        } while (paramString1 == null);
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("messageState", Integer.valueOf(paramInt2));
            paramString2.updateColumnValue("timeReceive", Integer.valueOf(DateUtil.sk_time_current_time()));
            paramString2.where().idEq(Integer.valueOf(paramInt1));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateSingleMessageType(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt2)
    {
        paramString1 = getDao(paramString1, paramString2, paramString5);
        if (paramString1 == null) {
            return;
        }
        try
        {
            paramString5 = paramString1.queryForEq("packetId", paramString3);
            if ((paramString5 != null) && (paramString5.size() > 0))
            {
                paramString2 = paramString1.updateBuilder();
                try
                {
                    paramString2.updateColumnValue("content", paramString4);
                    if (paramInt2 != 0) {
                        paramString2.updateColumnValue("timeSend", Integer.valueOf(paramInt2));
                    }
                    paramString2.updateColumnValue("type", Integer.valueOf(paramInt1));
                    paramString2.updateColumnValue("thumbnail", "");
                    paramString2.updateColumnValue("filePath", "");
                    paramString2.where().eq("packetId", paramString3);
                    paramString1.update(paramString2.prepare());
                    return;
                }
                catch (Exception paramString1)
                {
                    paramString1.printStackTrace();
                    LogFinalUtils.logForException(paramString1);
                    return;
                }
            }
            paramString5 = new ChatMessage();
        }
        catch (SQLException paramString1)
        {
            paramString1.printStackTrace();
            return;
        }
        paramString5.setType(paramInt1);
        paramString5.setFromUserId(paramString2);
        paramString5.setPacketId(paramString3);
        paramString5.setContent(paramString4);
        paramString5.setTimeSend(TimeUtils.sk_time_current_time());
        paramString1.create(paramString5);
    }

    public void updateSingleMessageUploadState(String paramString1, String paramString2, int paramInt, boolean paramBoolean, String paramString3, String paramString4)
    {
        paramString1 = getDao(paramString1, paramString2, paramString4);
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("isUpload", Boolean.valueOf(paramBoolean));
            paramString2.updateColumnValue("content", paramString3);
            paramString2.where().idEq(Integer.valueOf(paramInt));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateSingleNickName(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    {
        paramString1 = getDao(paramString1, paramString2, paramString5);
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.where().eq("fromUserId", paramString3);
            paramString2.updateColumnValue("fromUserName", paramString4);
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateSmsBurnState(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4, String paramString5)
    {
        paramString1 = getDao(paramString1, paramString2.replace("+", "_"));
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("needfire", Integer.valueOf(paramInt1));
            paramString2.updateColumnValue("myneedfire", Integer.valueOf(paramInt2));
            paramString2.updateColumnValue("content", paramString4);
            paramString2.updateColumnValue("filePath", paramString5);
            paramString2.where().eq("packetId", paramString3);
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateSmsSendState(String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        paramString1 = getDao(paramString1, paramString2.replace("+", "_"));
        if (paramString1 == null) {
            return;
        }
        paramString2 = paramString1.updateBuilder();
        try
        {
            paramString2.updateColumnValue("messageState", Integer.valueOf(paramInt2));
            paramString2.updateColumnValue("timeReceive", Integer.valueOf(DateUtil.sk_time_current_time()));
            paramString2.where().idEq(Integer.valueOf(paramInt1));
            paramString1.update(paramString2.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }
}
