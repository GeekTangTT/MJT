package com.olym.mjt.database.dao;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONArray;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.database.DBManager;
import com.olym.mjt.database.SQLiteHelper;
import com.olym.mjt.database.SQLiteRawUtil;
import com.olym.mjt.databean.bean.AttentionUser;
import com.olym.mjt.databean.bean.ChatMessage;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.LocalContact;
import com.olym.mjt.databean.bean.MucRoom;
import com.olym.mjt.databean.bean.MucRoomMember;
import com.olym.mjt.databean.bean.NewFriendMessage;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.ExistenceRoomsEvent;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.event.MessageMsgUiUpdateEvent;
import com.olym.mjt.module.message.sms.event.SmsLoadDataFromDBEvent;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.DateUtil;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.TableVersionSp;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class FriendDao
{
    private static String doingAddRoom = "doingAddRoom";
    private static FriendDao instance = null;
    public Dao<Friend, Integer> friendDao;
    private SQLiteHelper mHelper;

    private FriendDao()
    {
        try
        {
            this.mHelper = DBManager.getInstance().getDbHelper();
            this.friendDao = DaoManager.createDao(this.mHelper.getConnectionSource(), Friend.class);
            checkSystemFriend(MjtApplication.getInstance().getLoginUser().getUserId());
            return;
        }
        catch (Exception localException)
        {
            LogFinalUtils.logForException(localException);
        }
    }

    public static void close()
    {
        instance = null;
    }

    private void deletaOldFriend(String paramString, int paramInt)
    {
        TableVersionSp.getInstanse().setFriendTableVersion(paramString, paramInt);
        try
        {
            DeleteBuilder localDeleteBuilder = this.friendDao.deleteBuilder();
            localDeleteBuilder.where().eq("ownerId", paramString).and().eq("roomFlag", Integer.valueOf(0)).and().in("status", new Object[] { Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(-1) }).and().ne("version", Integer.valueOf(paramInt)).and().isNull("content");
            this.friendDao.delete(localDeleteBuilder.prepare());
            List localList = SQLiteRawUtil.getUserChatMessageTables(this.mHelper.getReadableDatabase(AppSpUtil.getInstanse().getDBPassword()), paramString);
            if ((localList != null) && (localList.size() > 0))
            {
                paramInt = 0;
                for (;;)
                {
                    if (paramInt >= localList.size()) {
                        return;
                    }
                    str3 = (String)localList.get(paramInt);
                    str4 = "msg_" + paramString;
                    i = str3.indexOf(str4);
                    if (i != -1) {
                        break;
                    }
                    paramInt += 1;
                }
            }
        }
        catch (Exception localException)
        {
            String str3;
            String str1;
            do
            {
                String str4;
                int i;
                for (;;)
                {
                    LogFinalUtils.logForException(localException);
                }
                str1 = "";
                String str2 = str3.substring(str4.length() + i, str3.length());
                localObject = str2;
                if (str2.contains("_"))
                {
                    int j = str3.lastIndexOf("_");
                    localObject = str3.substring(str4.length() + i, j);
                    str1 = str3.substring(j + 1, str3.length());
                }
            } while ((((String)localObject).equals("10002")) || (((String)localObject).equals("10004")) || (((String)localObject).equals("10001")) || (((String)localObject).equals("10000")));
            if (TextUtils.isEmpty(str1)) {}
            for (Object localObject = getFriend(paramString, (String)localObject); (localObject == null) && (SQLiteRawUtil.isTableExist(this.mHelper.getReadableDatabase(AppSpUtil.getInstanse().getDBPassword()), str3)); localObject = getFriend(paramString, (String)localObject, str1 + ".mjt.net"))
            {
                SQLiteRawUtil.dropTable(this.mHelper.getReadableDatabase(AppSpUtil.getInstanse().getDBPassword()), str3);
                ChatMessageDao.getInstance().removeTablename(str3);
                break;
            }
        }
    }

    public static final FriendDao getInstance()
    {
        if (instance == null) {}
        try
        {
            if (instance == null) {
                instance = new FriendDao();
            }
            return instance;
        }
        finally {}
    }

    public void addAttentionUsersForSip(String paramString, List<AttentionUser> paramList)
    {
        int i = TableVersionSp.getInstanse().getFriendTableVersion(paramString);
        if ((paramList != null) && (paramList.size() > 0)) {}
        try
        {
            this.friendDao.callBatchTasks(new FriendDao.1(this, paramList, paramString, i + 1));
            return;
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
    }

    public void addAttentionUsersForSms(String paramString, List<AttentionUser> paramList)
    {
        if ((paramList != null) && (paramList.size() > 0)) {}
        try
        {
            this.friendDao.callBatchTasks(new FriendDao.2(this, paramList, paramString));
            return;
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
    }

    public boolean addNewFriendInMsgTable(String paramString1, String paramString2, String paramString3)
    {
        ChatMessage localChatMessage = new ChatMessage();
        localChatMessage.setType(10);
        localChatMessage.setPacketId(UUID.randomUUID().toString().replaceAll("-", ""));
        localChatMessage.setFromUserId(paramString2);
        localChatMessage.setMessageState(1);
        localChatMessage.setTimeSend(DateUtil.sk_time_current_time());
        localChatMessage.setContent(MjtApplication.getInstance().getResources().getString(2131690270));
        localChatMessage.setMySend(false);
        localChatMessage.setIbcdomain(paramString3);
        ChatMessageDao.getInstance().saveNewSingleChatMessage(paramString1, paramString2, localChatMessage, paramString3);
        markUserMessageUnRead(paramString1, paramString2, paramString3);
        return true;
    }

    public void addRooms(String paramString, List<MucRoom> paramList)
    {
        if ((paramList != null) && (paramList.size() == 0)) {}
        int j;
        Object localObject3;
        Object localObject4;
        label419:
        do
        {
            return;
            try
            {
                DeleteBuilder localDeleteBuilder = this.friendDao.deleteBuilder();
                localDeleteBuilder.where().eq("roomFlag", Integer.valueOf(1)).and().eq("status", Integer.valueOf(2));
                this.friendDao.delete(localDeleteBuilder.prepare());
                j = TableVersionSp.getInstanse().getFriendTableVersion(paramString) + 1;
                if ((paramList != null) && (paramList.size() > 0))
                {
                    i = 0;
                    for (;;)
                    {
                        if (i >= paramList.size()) {
                            break label419;
                        }
                        localObject3 = (MucRoom)paramList.get(i);
                        if (localObject3 != null) {
                            break;
                        }
                        i += 1;
                    }
                }
            }
            catch (Exception localException1)
            {
                for (;;)
                {
                    LogFinalUtils.logForException(localException1);
                    continue;
                    Object localObject2 = ((MucRoom)localObject3).getJid();
                    localObject4 = this.friendDao.queryBuilder();
                    Object localObject1 = null;
                    try
                    {
                        ((QueryBuilder)localObject4).where().eq("ownerId", paramString).and().eq("userId", localObject2);
                        localObject2 = (Friend)this.friendDao.queryForFirst(((QueryBuilder)localObject4).prepare());
                        localObject1 = localObject2;
                    }
                    catch (SQLException localSQLException)
                    {
                        for (;;)
                        {
                            localSQLException.printStackTrace();
                            LogFinalUtils.logForException(localSQLException);
                        }
                    }
                    localObject2 = localObject1;
                    if (localObject1 == null)
                    {
                        localObject2 = new Friend();
                        ((Friend)localObject2).setOwnerId(paramString);
                        ((Friend)localObject2).setUserId(((MucRoom)localObject3).getJid());
                        ((Friend)localObject2).setTimeSend((int)((MucRoom)localObject3).getCreateTime());
                    }
                    ((Friend)localObject2).setRoomcreatetime((int)((MucRoom)localObject3).getCreateTime());
                    ((Friend)localObject2).setNickName(((MucRoom)localObject3).getName());
                    ((Friend)localObject2).setDescription(((MucRoom)localObject3).getDesc());
                    ((Friend)localObject2).setRoomFlag(1);
                    ((Friend)localObject2).setRoomId(((MucRoom)localObject3).getId());
                    ((Friend)localObject2).setRoomCreateUserId(((MucRoom)localObject3).getUserId());
                    ((Friend)localObject2).setStatus(2);
                    ((Friend)localObject2).setVersion(j);
                    localObject1 = ((MucRoom)localObject3).getMember();
                    if (localObject1 != null)
                    {
                        ((Friend)localObject2).setRoomMyNickName(((MucRoomMember)localObject1).getNickName());
                        ((Friend)localObject2).setRoomTalkTime(((MucRoomMember)localObject1).getTalkTime());
                    }
                    try
                    {
                        localObject1 = this.friendDao.createOrUpdate(localObject2);
                        localObject2 = ((Friend)localObject2).getUserId();
                        if (((Dao.CreateOrUpdateStatus)localObject1).isCreated()) {
                            ExistenceRoomsEvent.post(new ExistenceRoomsEvent((String)localObject2));
                        }
                    }
                    catch (Exception localException2)
                    {
                        LogFinalUtils.logForException(localException2);
                    }
                }
                TableVersionSp.getInstanse().setFriendTableVersion(paramString, j);
                localObject3 = SQLiteRawUtil.getUserChatMessageTables(this.mHelper.getReadableDatabase(AppSpUtil.getInstanse().getDBPassword()), paramString);
            }
        } while ((localObject3 == null) || (((List)localObject3).size() <= 0));
        int i = 0;
        label464:
        String str3;
        if (i < ((List)localObject3).size())
        {
            localObject4 = (String)((List)localObject3).get(i);
            str3 = "msg_" + paramString;
            j = ((String)localObject4).indexOf(str3);
            if (j != -1) {
                break label531;
            }
        }
        label531:
        label742:
        for (;;)
        {
            i += 1;
            break label464;
            break;
            String str1 = "";
            String str2 = ((String)localObject4).substring(str3.length() + j, ((String)localObject4).length());
            paramList = str2;
            if (str2.contains("_"))
            {
                int k = ((String)localObject4).lastIndexOf("_");
                paramList = ((String)localObject4).substring(str3.length() + j, k);
                str1 = ((String)localObject4).substring(k + 1, ((String)localObject4).length());
            }
            if ((!paramList.equals("10002")) && (!paramList.equals("10004")) && (!paramList.equals("10001")) && (!paramList.equals("10000")))
            {
                if (TextUtils.isEmpty(str1)) {}
                for (paramList = getFriend(paramString, paramList);; paramList = getFriend(paramString, paramList, str1 + ".mjt.net"))
                {
                    if ((paramList != null) || (!SQLiteRawUtil.isTableExist(this.mHelper.getReadableDatabase(AppSpUtil.getInstanse().getDBPassword()), (String)localObject4))) {
                        break label742;
                    }
                    SQLiteRawUtil.dropTable(this.mHelper.getReadableDatabase(AppSpUtil.getInstanse().getDBPassword()), (String)localObject4);
                    ChatMessageDao.getInstance().removeTablename((String)localObject4);
                    break;
                }
            }
        }
    }

    public void addRoomsInsert(String paramString, List<MucRoom> paramList)
    {
        if ((paramList != null) && (paramList.size() == 0)) {}
        while ((paramList == null) || (paramList.size() <= 0)) {
            return;
        }
        int i = 0;
        label29:
        MucRoom localMucRoom;
        if (i < paramList.size())
        {
            localMucRoom = (MucRoom)paramList.get(i);
            if (localMucRoom != null) {
                break label63;
            }
        }
        for (;;)
        {
            i += 1;
            break label29;
            break;
            label63:
            Object localObject2 = localMucRoom.getJid();
            QueryBuilder localQueryBuilder = this.friendDao.queryBuilder();
            Object localObject1 = null;
            try
            {
                localQueryBuilder.where().eq("ownerId", paramString).and().eq("userId", localObject2);
                localObject2 = (Friend)this.friendDao.queryForFirst(localQueryBuilder.prepare());
                localObject1 = localObject2;
            }
            catch (SQLException localSQLException)
            {
                for (;;)
                {
                    localSQLException.printStackTrace();
                    LogFinalUtils.logForException(localSQLException);
                }
            }
            localObject2 = localObject1;
            if (localObject1 == null)
            {
                localObject2 = new Friend();
                ((Friend)localObject2).setOwnerId(paramString);
                ((Friend)localObject2).setUserId(localMucRoom.getJid());
            }
            ((Friend)localObject2).setRoomcreatetime((int)localMucRoom.getCreateTime());
            ((Friend)localObject2).setNickName(localMucRoom.getName());
            ((Friend)localObject2).setDescription(localMucRoom.getDesc());
            ((Friend)localObject2).setRoomFlag(1);
            ((Friend)localObject2).setRoomId(localMucRoom.getId());
            ((Friend)localObject2).setRoomCreateUserId(localMucRoom.getUserId());
            ((Friend)localObject2).setStatus(2);
            ((Friend)localObject2).setTimeSend((int)localMucRoom.getCreateTime());
            localObject1 = localMucRoom.getMember();
            if (localObject1 != null)
            {
                ((Friend)localObject2).setRoomMyNickName(((MucRoomMember)localObject1).getNickName());
                ((Friend)localObject2).setRoomTalkTime(((MucRoomMember)localObject1).getTalkTime());
            }
            try
            {
                this.friendDao.createOrUpdate(localObject2);
            }
            catch (Exception localException)
            {
                LogFinalUtils.logForException(localException);
            }
        }
    }

    public void checkSystemFriend(String paramString)
    {
        try
        {
            Object localObject1 = this.friendDao.queryForEq("ownerId", paramString);
            if ((localObject1 != null) && (((List)localObject1).size() > 0)) {
                return;
            }
            localObject1 = new Friend();
            ((Friend)localObject1).setOwnerId(paramString);
            ((Friend)localObject1).setUserId("10001");
            ((Friend)localObject1).setNickName(Friend.NICKNAME_NEW_FRIEND_MESSAGE);
            ((Friend)localObject1).setStatus(8);
            Object localObject2 = this.friendDao.queryForMatching(localObject1);
            if ((localObject2 == null) || (((List)localObject2).size() <= 0))
            {
                this.friendDao.create(localObject1);
                localObject2 = new ChatMessage();
                ((ChatMessage)localObject2).setType(10);
                ((ChatMessage)localObject2).setPacketId(UUID.randomUUID().toString().replaceAll("-", ""));
                ((ChatMessage)localObject2).setFromUserId("10001");
                ((ChatMessage)localObject2).setMessageState(1);
                ((ChatMessage)localObject2).setTimeSend(DateUtil.sk_time_current_time());
                ((ChatMessage)localObject2).setContent("");
                ((ChatMessage)localObject2).setMySend(false);
                updateLastChatMessage(paramString, "10001", (ChatMessage)localObject2, true, false, false);
            }
            ((Friend)localObject1).setUserId("10000");
            ((Friend)localObject1).setNickName(Friend.NICKNAME_SYSTEM_MESSAGE);
            ((Friend)localObject1).setStatus(8);
            localObject2 = this.friendDao.queryForMatching(localObject1);
            if ((localObject2 == null) || (((List)localObject2).size() <= 0))
            {
                this.friendDao.create(localObject1);
                localObject1 = new ChatMessage();
                ((ChatMessage)localObject1).setType(10);
                ((ChatMessage)localObject1).setPacketId(UUID.randomUUID().toString().replaceAll("-", ""));
                ((ChatMessage)localObject1).setFromUserId("10000");
                ((ChatMessage)localObject1).setMessageState(1);
                ((ChatMessage)localObject1).setTimeSend(DateUtil.sk_time_current_time() + 1);
                ((ChatMessage)localObject1).setContent(MjtApplication.getInstance().getResources().getString(2131690264) + MjtApplication.getInstance().getResources().getString(2131689598));
                ((ChatMessage)localObject1).setMySend(false);
                ChatMessageDao.getInstance().saveNewMuchChatMessage(paramString, "10000", (ChatMessage)localObject1, false);
                markUserMessageUnRead(paramString, "10000");
                updateLastChatMessage(paramString, "10000", (ChatMessage)localObject1, true, false, false);
                return;
            }
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
    }

    public void cleanLastMessage(String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            localUpdateBuilder.updateColumnValue("content", paramString4);
            if (paramBoolean) {
                localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            }
            while (this.friendDao.update(localUpdateBuilder.prepare()) != -1)
            {
                MessageMsgUiUpdateEvent.post(new MessageMsgUiUpdateEvent());
                return;
                localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("domain", paramString3);
            }
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void clearAllRemark()
    {
        Iterator localIterator = getInstance().getFriends(MjtApplication.getInstance().getLoginUser().getUserId()).iterator();
        while (localIterator.hasNext())
        {
            Friend localFriend = (Friend)localIterator.next();
            localFriend.setRemarkName("");
            createOrUpdateFriend(localFriend);
        }
    }

    public boolean createOrUpdateFriend(Friend paramFriend)
    {
        boolean bool1 = false;
        try
        {
            paramFriend = this.friendDao.createOrUpdate(paramFriend);
            if (!paramFriend.isCreated())
            {
                boolean bool2 = paramFriend.isUpdated();
                if (!bool2) {}
            }
            else
            {
                bool1 = true;
            }
            return bool1;
        }
        catch (Exception paramFriend)
        {
            paramFriend.printStackTrace();
            LogFinalUtils.logForException(paramFriend);
        }
        return false;
    }

    public boolean createOrUpdateFriendByNewFriend(NewFriendMessage paramNewFriendMessage, int paramInt)
    {
        boolean bool1 = false;
        try
        {
            Object localObject2 = getFriend(paramNewFriendMessage.getOwnerId(), paramNewFriendMessage.getUserId(), paramNewFriendMessage.getIbcdomain());
            Object localObject1 = localObject2;
            if (localObject2 == null)
            {
                localObject1 = new Friend();
                ((Friend)localObject1).setOwnerId(paramNewFriendMessage.getOwnerId());
                ((Friend)localObject1).setUserId(paramNewFriendMessage.getUserId());
                ((Friend)localObject1).setNickName(paramNewFriendMessage.getNickName());
                ((Friend)localObject1).setDomain(paramNewFriendMessage.getIbcdomain());
                ((Friend)localObject1).setToTelephone(paramNewFriendMessage.getTelephone());
                ((Friend)localObject1).setTimeCreate(DateUtil.sk_time_current_time());
                ((Friend)localObject1).setCompanyId(paramNewFriendMessage.getCompanyId());
                ((Friend)localObject1).setVersion(TableVersionSp.getInstanse().getFriendTableVersion(paramNewFriendMessage.getOwnerId()));
            }
            ((Friend)localObject1).setStatus(paramInt);
            paramNewFriendMessage = LocalContactDao.getInstance().getLocalContactsFromPhone(paramNewFriendMessage.getTelephone());
            if (paramNewFriendMessage != null)
            {
                localObject2 = paramNewFriendMessage.getNickName();
                Applog.systemOut("-----name----- " + (String)localObject2);
                ((Friend)localObject1).setNickName((String)localObject2);
                if (TextUtils.isEmpty(paramNewFriendMessage.getRemarkName()))
                {
                    ((Friend)localObject1).setSimplespell(paramNewFriendMessage.getSimplespell());
                    ((Friend)localObject1).setWholeSpell(paramNewFriendMessage.getWholeSpell());
                }
                ((Friend)localObject1).setLocalId(paramNewFriendMessage.getLocalId());
            }
            Applog.systemOut("------existFriend---- " + localObject1);
            paramNewFriendMessage = this.friendDao.createOrUpdate(localObject1);
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
        catch (Exception paramNewFriendMessage)
        {
            LogFinalUtils.logForException(paramNewFriendMessage);
        }
        return false;
    }

    public void deleteAll()
    {
        Object localObject1 = getInstance().getFriends(MjtApplication.getInstance().getLoginUser().getUserId());
        if (localObject1 != null)
        {
            localObject1 = ((List)localObject1).iterator();
            while (((Iterator)localObject1).hasNext())
            {
                Object localObject2 = (Friend)((Iterator)localObject1).next();
                deleteFriend(MjtApplication.getInstance().getLoginUser().getUserId(), ((Friend)localObject2).getUserId(), ((Friend)localObject2).getDomain());
                localObject2 = LocalContactDao.getInstance().getLocalContactsFromPhone(((Friend)localObject2).getToTelephone());
                if (localObject2 != null) {
                    LocalContactDao.getInstance().deleteLocalContact((LocalContact)localObject2);
                }
            }
        }
    }

    public HashMap<String, String> deleteAllFriends()
    {
        Object localObject1 = getInstance().getFriends(MjtApplication.getInstance().getLoginUser().getUserId());
        HashMap localHashMap = new HashMap();
        if (localObject1 != null)
        {
            localObject1 = ((List)localObject1).iterator();
            while (((Iterator)localObject1).hasNext())
            {
                Object localObject2 = (Friend)((Iterator)localObject1).next();
                if ((((Friend)localObject2).getStatus() == 5) || (((Friend)localObject2).getStatus() == 2) || (((Friend)localObject2).getStatus() == -1))
                {
                    deleteFriend(MjtApplication.getInstance().getLoginUser().getUserId(), ((Friend)localObject2).getUserId(), ((Friend)localObject2).getDomain());
                    localObject2 = LocalContactDao.getInstance().getLocalContactsFromPhone(((Friend)localObject2).getToTelephone());
                    if ((localObject2 != null) && (TextUtils.isEmpty(((LocalContact)localObject2).getLocalId())))
                    {
                        String str = ((LocalContact)localObject2).getRemarkName();
                        if (!TextUtils.isEmpty(str)) {
                            localHashMap.put(((LocalContact)localObject2).getTelephone(), str);
                        }
                        LocalContactDao.getInstance().deleteLocalContact((LocalContact)localObject2);
                    }
                }
            }
        }
        return localHashMap;
    }

    public void deleteFriend(String paramString1, String paramString2)
    {
        try
        {
            DeleteBuilder localDeleteBuilder = this.friendDao.deleteBuilder();
            localDeleteBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            this.friendDao.delete(localDeleteBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void deleteFriend(String paramString1, String paramString2, String paramString3)
    {
        try
        {
            DeleteBuilder localDeleteBuilder = this.friendDao.deleteBuilder();
            localDeleteBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("domain", paramString3);
            this.friendDao.delete(localDeleteBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public List<Friend> getAllAttentions(String paramString)
    {
        try
        {
            paramString = this.friendDao.queryBuilder().where().eq("ownerId", paramString).and().eq("status", Integer.valueOf(1)).and().eq("roomFlag", Integer.valueOf(0)).and().eq("companyId", Integer.valueOf(0)).prepare();
            paramString = this.friendDao.query(paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public List<Friend> getAllBlacklists(String paramString)
    {
        try
        {
            paramString = this.friendDao.queryBuilder().where().eq("ownerId", paramString).and().eq("status", Integer.valueOf(-1)).and().eq("roomFlag", Integer.valueOf(0)).prepare();
            paramString = this.friendDao.query(paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public List<Friend> getAllContacts(String paramString)
    {
        String str = paramString;
        if (paramString == null) {}
        try
        {
            str = MjtApplication.getInstance().getLoginUser().getUserId();
            if (str != null)
            {
                paramString = this.friendDao.queryBuilder().where().eq("ownerId", str).and().in("status", new Object[] { Integer.valueOf(2), Integer.valueOf(5) }).and().eq("roomFlag", Integer.valueOf(0)).prepare();
                paramString = this.friendDao.query(paramString);
                return paramString;
            }
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public List<Friend> getAllEnterprises(String paramString)
    {
        try
        {
            paramString = this.friendDao.queryBuilder().where().eq("ownerId", paramString).and().in("status", new Object[] { Integer.valueOf(1), Integer.valueOf(2) }).and().eq("roomFlag", Integer.valueOf(0)).and().gt("companyId", Integer.valueOf(0)).prepare();
            paramString = this.friendDao.query(paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public List<Friend> getAllFriends(String paramString)
    {
        try
        {
            paramString = this.friendDao.queryBuilder().where().eq("ownerId", paramString).and().in("status", new Object[] { Integer.valueOf(2), Integer.valueOf(5) }).and().eq("roomFlag", Integer.valueOf(0)).and().eq("companyId", Integer.valueOf(0)).prepare();
            paramString = this.friendDao.query(paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public List<Friend> getAllRooms(String paramString)
    {
        try
        {
            paramString = this.friendDao.queryBuilder().orderBy("timeSend", false).where().eq("ownerId", paramString).and().eq("roomFlag", Integer.valueOf(1)).prepare();
            paramString = this.friendDao.query(paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public List<Friend> getAllRoomsMsg(String paramString)
    {
        try
        {
            paramString = this.friendDao.queryBuilder().orderBy("timeSend", false).where().eq("ownerId", paramString).and().eq("roomFlag", Integer.valueOf(1)).and().isNotNull("content").prepare();
            paramString = this.friendDao.query(paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public Friend getFriend(String paramString1, String paramString2)
    {
        if (paramString1 != null) {
            try
            {
                paramString1 = this.friendDao.queryBuilder().where().eq("ownerId", paramString1).and().eq("userId", paramString2).prepare();
                paramString1 = (Friend)this.friendDao.queryForFirst(paramString1);
                return paramString1;
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return null;
    }

    public Friend getFriend(String paramString1, String paramString2, String paramString3)
    {
        if (paramString1 != null) {
            try
            {
                paramString1 = this.friendDao.queryBuilder().where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("domain", paramString3).prepare();
                paramString1 = (Friend)this.friendDao.queryForFirst(paramString1);
                return paramString1;
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return null;
    }

    public Friend getFriendFromPhone(String paramString1, String paramString2)
    {
        if (paramString1 != null) {
            try
            {
                paramString1 = this.friendDao.queryBuilder().where().eq("ownerId", paramString1).and().eq("toTelephone", paramString2).prepare();
                paramString1 = (Friend)this.friendDao.queryForFirst(paramString1);
                return paramString1;
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return null;
    }

    public Friend getFriendFromPhone(String paramString1, String paramString2, String paramString3)
    {
        if (paramString1 != null) {
            try
            {
                paramString1 = this.friendDao.queryBuilder().where().eq("ownerId", paramString1).and().eq("toTelephone", paramString2).and().eq("domain", paramString3).prepare();
                paramString1 = (Friend)this.friendDao.queryForFirst(paramString1);
                return paramString1;
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return null;
    }

    public String getFriendId(String paramString1, String paramString2)
    {
        QueryBuilder localQueryBuilder = this.friendDao.queryBuilder();
        try
        {
            localQueryBuilder.selectColumns(new String[] { "userId" });
            localQueryBuilder.where().eq("ownerId", paramString1).and().eq("toTelephone", paramString2);
            paramString1 = this.friendDao.queryRaw(localQueryBuilder.prepareStatementString(), new String[0]);
            if (paramString1 != null)
            {
                paramString1 = (String[])paramString1.getFirstResult();
                if ((paramString1 != null) && (paramString1.length > 0))
                {
                    paramString1 = paramString1[0];
                    return paramString1;
                }
            }
        }
        catch (NumberFormatException paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
            return null;
        }
        catch (Exception paramString1)
        {
            for (;;)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
    }

    public int getFriendStatus(String paramString1, String paramString2, String paramString3)
    {
        int j = 0;
        QueryBuilder localQueryBuilder = this.friendDao.queryBuilder();
        try
        {
            localQueryBuilder.selectColumns(new String[] { "status" });
            localQueryBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("domain", paramString3);
            paramString1 = this.friendDao.queryRaw(localQueryBuilder.prepareStatementString(), new String[0]);
            int i = j;
            if (paramString1 != null)
            {
                paramString1 = (String[])paramString1.getFirstResult();
                i = j;
                if (paramString1 != null)
                {
                    i = j;
                    if (paramString1.length > 0) {
                        i = Integer.parseInt(paramString1[0]);
                    }
                }
            }
            return i;
        }
        catch (NumberFormatException paramString1)
        {
            LogFinalUtils.logForException(paramString1);
            return 0;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
        return 0;
    }

    public List<Friend> getFriends(String paramString)
    {
        if (paramString != null) {
            try
            {
                paramString = this.friendDao.queryBuilder().where().eq("ownerId", paramString).and().in("status", new Object[] { Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(-1), Integer.valueOf(5) }).and().eq("roomFlag", Integer.valueOf(0)).and().eq("companyId", Integer.valueOf(0)).and().ne("nickName", "10000").prepare();
                paramString = this.friendDao.query(paramString);
                return paramString;
            }
            catch (Exception paramString)
            {
                LogFinalUtils.logForException(paramString);
            }
        }
        return null;
    }

    public List<Friend> getFriensdFromPhone(String paramString1, String paramString2)
    {
        if (paramString1 != null) {
            try
            {
                paramString1 = this.friendDao.queryBuilder().where().eq("ownerId", paramString1).and().eq("toTelephone", paramString2).prepare();
                paramString1 = this.friendDao.query(paramString1);
                return paramString1;
            }
            catch (Exception paramString1)
            {
                paramString1.printStackTrace();
                LogFinalUtils.logForException(paramString1);
            }
        }
        return null;
    }

    public int getLastSmsTimeSend(String paramString1, String paramString2)
    {
        int j = 0;
        QueryBuilder localQueryBuilder = this.friendDao.queryBuilder();
        localQueryBuilder.selectRaw(new String[] { "timeSend" });
        try
        {
            localQueryBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            paramString1 = this.friendDao.queryRaw(localQueryBuilder.prepareStatementString(), new String[0]);
            int i = j;
            if (paramString1 != null)
            {
                paramString1 = (String[])paramString1.getFirstResult();
                i = j;
                if (paramString1 != null)
                {
                    i = j;
                    if (paramString1.length > 0)
                    {
                        i = j;
                        if (paramString1[0] != null) {
                            i = Integer.parseInt(paramString1[0]);
                        }
                    }
                }
            }
            return i;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return 0;
    }

    public int getLastTimeSend(String paramString1, String paramString2)
    {
        int j = 0;
        QueryBuilder localQueryBuilder = this.friendDao.queryBuilder();
        localQueryBuilder.selectRaw(new String[] { "roomLastTime" });
        try
        {
            localQueryBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            paramString1 = this.friendDao.queryRaw(localQueryBuilder.prepareStatementString(), new String[0]);
            int i = j;
            if (paramString1 != null)
            {
                paramString1 = (String[])paramString1.getFirstResult();
                i = j;
                if (paramString1 != null)
                {
                    i = j;
                    if (paramString1.length > 0)
                    {
                        i = j;
                        if (paramString1[0] != null) {
                            i = Integer.parseInt(paramString1[0]);
                        }
                    }
                }
            }
            return i;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return 0;
    }

    public int getMsgUnReadNumTotal(String paramString)
    {
        int j = 0;
        try
        {
            QueryBuilder localQueryBuilder = this.friendDao.queryBuilder();
            localQueryBuilder.selectRaw(new String[] { "sum(unReadNum)" });
            localQueryBuilder.where().eq("ownerId", paramString).and().ge("status", Integer.valueOf(2)).and().lt("status", Integer.valueOf(8));
            paramString = this.friendDao.queryRaw(localQueryBuilder.prepareStatementString(), new String[0]);
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

    public Friend getMucFriendByRoomId(String paramString)
    {
        try
        {
            paramString = this.friendDao.queryBuilder().where().eq("userId", paramString).prepare();
            paramString = (Friend)this.friendDao.queryForFirst(paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public List<Friend> getNearlyFriendMsg(String paramString)
    {
        try
        {
            paramString = this.friendDao.queryBuilder().orderBy("timeSend", false).where().eq("ownerId", paramString).and().in("status", new Object[] { Integer.valueOf(5), Integer.valueOf(2), Integer.valueOf(0) }).and().le("status", Integer.valueOf(8)).and().isNotNull("content").and().eq("roomFlag", Integer.valueOf(0)).or().eq("roomFlag", Integer.valueOf(1)).prepare();
            paramString = this.friendDao.query(paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    @Deprecated
    public List<Friend> getNearlyFriendMsg(String paramString, int paramInt1, int paramInt2)
    {
        try
        {
            paramString = this.friendDao.queryBuilder().orderBy("timeSend", false).limit(Long.valueOf(paramInt2)).offset(Long.valueOf(paramInt2 * paramInt1)).where().eq("ownerId", paramString).and().isNotNull("content").and().ge("status", Integer.valueOf(2)).and().le("status", Integer.valueOf(8)).prepare();
            paramString = this.friendDao.query(paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public List<Friend> getNearlyFriendMsgWithoutSystem(String paramString)
    {
        try
        {
            paramString = this.friendDao.queryBuilder().orderBy("timeSend", false).where().eq("ownerId", paramString).and().in("status", new Object[] { Integer.valueOf(2), Integer.valueOf(5) }).and().lt("status", Integer.valueOf(8)).and().isNotNull("content").and().eq("roomFlag", Integer.valueOf(0)).or().eq("roomFlag", Integer.valueOf(1)).prepare();
            paramString = this.friendDao.query(paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public List<Friend> getNearlyFriendSms(String paramString)
    {
        try
        {
            paramString = this.friendDao.queryBuilder().orderBy("timeSend", false).where().eq("ownerId", paramString).and().isNotNull("content").and().eq("roomFlag", Integer.valueOf(3)).prepare();
            paramString = this.friendDao.query(paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
        return null;
    }

    public int getNewFriendUnReadNumTotal(String paramString)
    {
        int j = 0;
        try
        {
            QueryBuilder localQueryBuilder = this.friendDao.queryBuilder();
            localQueryBuilder.selectRaw(new String[] { "sum(unReadNum)" });
            localQueryBuilder.where().eq("ownerId", paramString).and().eq("userId", "10001");
            paramString = this.friendDao.queryRaw(localQueryBuilder.prepareStatementString(), new String[0]);
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

    public String getRemarkName(String paramString1, String paramString2)
    {
        QueryBuilder localQueryBuilder = this.friendDao.queryBuilder();
        localQueryBuilder.selectRaw(new String[] { "remarkName" });
        try
        {
            localQueryBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            paramString1 = this.friendDao.queryRaw(localQueryBuilder.prepareStatementString(), new String[0]);
            if (paramString1 != null)
            {
                paramString1 = (String[])paramString1.getFirstResult();
                if ((paramString1 != null) && (paramString1.length > 0))
                {
                    paramString1 = paramString1[0];
                    return paramString1;
                }
            }
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return "";
    }

    public int getRoomCreateTime(String paramString1, String paramString2)
    {
        int j = 0;
        QueryBuilder localQueryBuilder = this.friendDao.queryBuilder();
        localQueryBuilder.selectRaw(new String[] { "roomcreatetime" });
        try
        {
            localQueryBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            paramString1 = this.friendDao.queryRaw(localQueryBuilder.prepareStatementString(), new String[0]);
            int i = j;
            if (paramString1 != null)
            {
                paramString1 = (String[])paramString1.getFirstResult();
                i = j;
                if (paramString1 != null)
                {
                    i = j;
                    if (paramString1.length > 0)
                    {
                        i = j;
                        if (paramString1[0] != null) {
                            i = Integer.parseInt(paramString1[0]);
                        }
                    }
                }
            }
            return i;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
        return 0;
    }

    public int getSmsUnReadNumTotal(String paramString)
    {
        int j = 0;
        try
        {
            QueryBuilder localQueryBuilder = this.friendDao.queryBuilder();
            localQueryBuilder.selectRaw(new String[] { "sum(unReadNum)" });
            localQueryBuilder.where().eq("ownerId", paramString).and().eq("roomFlag", Integer.valueOf(3));
            paramString = this.friendDao.queryRaw(localQueryBuilder.prepareStatementString(), new String[0]);
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

    public void markUserMessageRead(String paramString1, String paramString2)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            Log.d("roamer", "........������������.......");
            localUpdateBuilder.updateColumnValue("unReadNum", Integer.valueOf(0));
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void markUserMessageRead(String paramString1, String paramString2, String paramString3)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            Log.d("roamer", "........������������.......");
            localUpdateBuilder.updateColumnValue("unReadNum", Integer.valueOf(0));
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("domain", paramString3);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void markUserMessageUnRead(String paramString1, String paramString2)
    {
        try
        {
            paramString1 = this.friendDao.queryBuilder().where().eq("ownerId", paramString1).and().eq("userId", paramString2).prepare();
            paramString1 = this.friendDao.query(paramString1);
            if ((paramString1 != null) && (paramString1.size() > 0))
            {
                paramString1 = (Friend)paramString1.get(0);
                paramString1.setUnReadNum(paramString1.getUnReadNum() + 1);
                this.friendDao.update(paramString1);
            }
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void markUserMessageUnRead(String paramString1, String paramString2, String paramString3)
    {
        try
        {
            paramString1 = this.friendDao.queryBuilder().where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("domain", paramString3).prepare();
            paramString1 = this.friendDao.query(paramString1);
            if ((paramString1 != null) && (paramString1.size() > 0))
            {
                paramString1 = (Friend)paramString1.get(0);
                paramString1.setUnReadNum(paramString1.getUnReadNum() + 1);
                this.friendDao.update(paramString1);
            }
            return;
        }
        catch (SQLException paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public List<Friend> queryAllContacts(String paramString1, String paramString2, int paramInt)
    {
        String str = paramString1;
        if (paramString1 == null) {}
        try
        {
            str = MjtApplication.getInstance().getLoginUser().getUserId();
            if (str != null)
            {
                paramString1 = this.friendDao.queryBuilder().orderBy("timeSend", false).limit(Long.valueOf(3L)).where().eq("ownerId", str).and().eq("roomFlag", Integer.valueOf(paramInt)).and().like("nickName", "%" + paramString2 + "%").or().like("remarkName", "%" + paramString2 + "%").or().like("toTelephone", "%" + paramString2 + "%").prepare();
                paramString1 = this.friendDao.query(paramString1);
                return paramString1;
            }
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<Friend> queryAllContactsWithoutLimit(String paramString1, String paramString2, int paramInt)
    {
        String str = paramString1;
        if (paramString1 == null) {}
        try
        {
            str = MjtApplication.getInstance().getLoginUser().getUserId();
            if (str != null)
            {
                paramString1 = this.friendDao.queryBuilder().orderBy("timeSend", false).where().eq("ownerId", str).and().eq("roomFlag", Integer.valueOf(paramInt)).and().like("nickName", "%" + paramString2 + "%").or().like("remarkName", "%" + paramString2 + "%").or().like("toTelephone", "%" + paramString2 + "%").prepare();
                paramString1 = this.friendDao.query(paramString1);
                return paramString1;
            }
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<Friend> queryAllRooms(String paramString1, String paramString2, int paramInt)
    {
        String str = paramString1;
        if (paramString1 == null) {}
        try
        {
            str = MjtApplication.getInstance().getLoginUser().getUserId();
            if (str != null)
            {
                paramString1 = this.friendDao.queryBuilder().limit(Long.valueOf(3L)).where().eq("ownerId", str).and().eq("roomFlag", Integer.valueOf(paramInt)).and().like("nickName", "%" + paramString2 + "%").prepare();
                paramString1 = this.friendDao.query(paramString1);
                return paramString1;
            }
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public List<Friend> queryAllRoomsWithoutLimit(String paramString1, String paramString2, int paramInt)
    {
        String str = paramString1;
        if (paramString1 == null) {}
        try
        {
            str = MjtApplication.getInstance().getLoginUser().getUserId();
            if (str != null)
            {
                paramString1 = this.friendDao.queryBuilder().where().eq("ownerId", str).and().eq("roomFlag", Integer.valueOf(paramInt)).and().like("nickName", "%" + paramString2 + "%").prepare();
                paramString1 = this.friendDao.query(paramString1);
                return paramString1;
            }
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
        return null;
    }

    public void resetFriendMessage(String paramString1, String paramString2)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            localUpdateBuilder.updateColumnValue("unReadNum", Integer.valueOf(0));
            localUpdateBuilder.updateColumnValue("content", null);
            localUpdateBuilder.updateColumnValue("timeSend", Integer.valueOf(0));
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void setRemarkName(String paramString1, String paramString2, String paramString3)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            localUpdateBuilder.updateColumnValue("remarkName", paramString3);
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void setRemarkName(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            localUpdateBuilder.updateColumnValue("remarkName", paramString3);
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("domain", paramString4);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateFriend(String paramString, Friend paramFriend)
    {
        paramFriend.setVersion(TableVersionSp.getInstanse().getFriendTableVersion(paramString) + 1);
        try
        {
            this.friendDao.createOrUpdate(paramFriend);
            return;
        }
        catch (Exception paramString)
        {
            LogFinalUtils.logForException(paramString);
        }
    }

    public void updateFriendDomain(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            localUpdateBuilder.updateColumnValue("domain", paramString3);
            localUpdateBuilder.updateColumnValue("domainVersion", paramString4);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateFriendStatus(String paramString1, String paramString2, int paramInt)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            localUpdateBuilder.updateColumnValue("status", Integer.valueOf(paramInt));
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateFriendStatus(String paramString1, String paramString2, int paramInt, String paramString3)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            localUpdateBuilder.updateColumnValue("status", Integer.valueOf(paramInt));
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("domain", paramString3);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateLastChatMessage(String paramString1, String paramString2, ChatMessage paramChatMessage, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
        Object localObject2 = "";
        for (;;)
        {
            int m;
            Object localObject1;
            int k;
            try
            {
                m = paramChatMessage.getType();
                int i;
                if (m == 1)
                {
                    localObject1 = paramChatMessage.getFilePath();
                    if (!TextUtils.isEmpty((CharSequence)localObject1))
                    {
                        localObject1 = JSONArray.parseArray((String)localObject1);
                        j = 0;
                        i = 0;
                        if (i < ((JSONArray)localObject1).size())
                        {
                            localObject2 = ((JSONArray)localObject1).get(i);
                            k = j;
                            if (localObject2 == null) {
                                break label864;
                            }
                            k = j;
                            if (!localObject2.toString().equals(MjtApplication.getInstance().getLoginUser().getUserId())) {
                                break label864;
                            }
                            k = 1;
                            break label864;
                        }
                        if (j != 0)
                        {
                            localObject1 = MjtApplication.getInstance().getResources().getString(2131690058) + " " + paramChatMessage.getContent();
                            if (paramChatMessage.getIsDecrypted() == 1) {
                                localObject1 = MjtApplication.getInstance().getResources().getString(2131690000);
                            }
                            localObject2 = localObject1;
                            if (TextUtils.isEmpty((CharSequence)localObject1)) {
                                localObject2 = "";
                            }
                            localObject1 = localObject2;
                            if (!((String)localObject2).contains("'")) {
                                break label877;
                            }
                            localObject1 = ((String)localObject2).replace("'", "''");
                            break label877;
                            localObject2 = this.friendDao.updateBuilder();
                        }
                    }
                }
                try
                {
                    ((UpdateBuilder)localObject2).updateColumnValue("content", localObject1);
                    ((UpdateBuilder)localObject2).updateColumnValue("type", Integer.valueOf(m));
                    ((UpdateBuilder)localObject2).updateColumnValue("domain", paramChatMessage.getIbcdomain());
                    if (!paramBoolean3) {
                        ((UpdateBuilder)localObject2).updateColumnValue("timeSend", Integer.valueOf(paramChatMessage.getTimeSend()));
                    }
                    if ((!paramBoolean3) && (!paramChatMessage.isMySend())) {
                        ((UpdateBuilder)localObject2).updateColumnValue("roomLastTime", Integer.valueOf(paramChatMessage.getTimeSend()));
                    }
                    if (!paramBoolean1) {
                        continue;
                    }
                    ((UpdateBuilder)localObject2).where().eq("ownerId", paramString1).and().eq("userId", paramString2);
                    this.friendDao.update(((UpdateBuilder)localObject2).prepare());
                }
                catch (Exception paramString1)
                {
                    LogFinalUtils.logForException(paramString1);
                    continue;
                }
                return;
                localObject1 = paramChatMessage.getContent();
                continue;
                localObject1 = paramChatMessage.getContent();
                continue;
                if (m == 2)
                {
                    localObject1 = MjtApplication.getInstance().getResources().getString(2131689910);
                    continue;
                }
                if (m == 3)
                {
                    localObject1 = MjtApplication.getInstance().getResources().getString(2131689912);
                    continue;
                }
                if (m == 4)
                {
                    localObject1 = MjtApplication.getInstance().getResources().getString(2131689909);
                    continue;
                }
                if (m == 5)
                {
                    localObject1 = MjtApplication.getInstance().getResources().getString(2131689908);
                    continue;
                }
                if (m == 6)
                {
                    localObject1 = MjtApplication.getInstance().getResources().getString(2131689911);
                    continue;
                }
                if (m == 9)
                {
                    localObject1 = MjtApplication.getInstance().getResources().getString(2131689907);
                    continue;
                }
                if (m == 10)
                {
                    localObject1 = paramChatMessage.getContent();
                    continue;
                }
                if (m == 503)
                {
                    localObject1 = localObject2;
                    if (paramChatMessage.isMySend()) {
                        continue;
                    }
                    localObject1 = MjtApplication.getInstance().getResources().getString(2131690057);
                    continue;
                }
                if (m == 500)
                {
                    localObject1 = localObject2;
                    if (paramChatMessage.isMySend()) {
                        continue;
                    }
                    if (TextUtils.isEmpty(paramChatMessage.getContent()))
                    {
                        localObject1 = MjtApplication.getInstance().getResources().getString(2131690060);
                        continue;
                    }
                    localObject1 = paramChatMessage.getContent();
                    continue;
                }
                if (m == 501)
                {
                    localObject1 = localObject2;
                    if (paramChatMessage.isMySend()) {
                        continue;
                    }
                    localObject1 = MjtApplication.getInstance().getResources().getString(2131690273);
                    continue;
                }
                if (m == 508)
                {
                    localObject1 = localObject2;
                    if (paramChatMessage.isMySend()) {
                        continue;
                    }
                    localObject1 = MjtApplication.getInstance().getResources().getString(2131690271);
                    continue;
                }
                if (m == 502)
                {
                    localObject1 = localObject2;
                    if (paramChatMessage.isMySend()) {
                        continue;
                    }
                    localObject1 = localObject2;
                    if (TextUtils.isEmpty(paramChatMessage.getContent())) {
                        continue;
                    }
                    localObject1 = paramChatMessage.getContent();
                    continue;
                }
                if (m != 506) {
                    break label889;
                }
                localObject1 = MjtApplication.getInstance().getResources().getString(2131690272);
                continue;
                if (paramChatMessage.isMySend())
                {
                    localObject1 = MjtApplication.getInstance().getResources().getString(2131690275);
                    continue;
                }
                localObject1 = MjtApplication.getInstance().getResources().getString(2131690274);
                continue;
                localObject1 = paramChatMessage.getContent();
                continue;
                ((UpdateBuilder)localObject2).where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("domain", paramChatMessage.getIbcdomain());
                continue;
                i += 1;
            }
            finally {}
            label864:
            int j = k;
            continue;
            label877:
            if (paramBoolean2)
            {
                localObject1 = "";
                continue;
                label889:
                if ((m != 12) && (m != 16) && (m != 17) && (m != 13) && (m != 15)) {
                    if (m != 14) {}
                }
            }
        }
    }

    public void updateLastMessage(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, boolean paramBoolean1, boolean paramBoolean2)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        if (paramInt == 2) {}
        for (;;)
        {
            try
            {
                str = MjtApplication.getInstance().getResources().getString(2131689910);
                localUpdateBuilder.updateColumnValue("content", str);
                if (paramBoolean1)
                {
                    localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
                    if ((this.friendDao.update(localUpdateBuilder.prepare()) != -1) && (paramBoolean2))
                    {
                        MessageMsgUiUpdateEvent.post(new MessageMsgUiUpdateEvent());
                        return;
                        if (paramInt == 3)
                        {
                            str = MjtApplication.getInstance().getResources().getString(2131689912);
                            continue;
                        }
                        if (paramInt == 4)
                        {
                            str = MjtApplication.getInstance().getResources().getString(2131689909);
                            continue;
                        }
                        if (paramInt == 5)
                        {
                            str = MjtApplication.getInstance().getResources().getString(2131689908);
                            continue;
                        }
                        if (paramInt == 6)
                        {
                            str = MjtApplication.getInstance().getResources().getString(2131689911);
                            continue;
                        }
                        if (paramInt != 9) {
                            break label260;
                        }
                        str = MjtApplication.getInstance().getResources().getString(2131689907);
                    }
                }
                else
                {
                    localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("domain", paramString3);
                    continue;
                }
                return;
            }
            catch (Exception paramString1)
            {
                LogFinalUtils.logForException(paramString1);
            }
            label260:
            String str = paramString4;
            if (paramInt == 10) {
                str = paramString4;
            }
        }
    }

    public void updateLastSms(String paramString1, String paramString2, ChatMessage paramChatMessage, String paramString3, boolean paramBoolean)
    {
        try
        {
            if (getFriend(paramString1, paramString2) == null)
            {
                localObject = new Friend();
                ((Friend)localObject).setOwnerId(paramString1);
                ((Friend)localObject).setUserId(paramString2);
                ((Friend)localObject).setRoomFlag(3);
                ((Friend)localObject).setToTelephone(paramString2);
                ((Friend)localObject).setNickName(paramString2);
                ((Friend)localObject).setRemarkName(paramString3);
                ((Friend)localObject).setTimeCreate(DateUtil.sk_time_current_time());
                ((Friend)localObject).setVersion(TableVersionSp.getInstanse().getFriendTableVersion(paramString1));
                this.friendDao.createOrUpdate(localObject);
            }
            localObject = paramChatMessage.getContent();
            paramString3 = (String)localObject;
            if (paramChatMessage.isMySend())
            {
                paramString3 = (String)localObject;
                if (paramChatMessage.getFire() == 1) {
                    paramString3 = MjtApplication.getInstance().getResources().getString(2131690050);
                }
            }
            localObject = paramString3;
            if (!paramChatMessage.isMySend())
            {
                localObject = paramString3;
                if (paramChatMessage.getFire() == 1) {
                    localObject = MjtApplication.getInstance().getResources().getString(2131690006);
                }
            }
            if (paramBoolean) {
                localObject = "";
            }
            paramString3 = (String)localObject;
            if (((String)localObject).contains("'")) {
                paramString3 = ((String)localObject).replace("'", "''");
            }
            localObject = this.friendDao.updateBuilder();
        }
        catch (Exception paramString3)
        {
            for (;;)
            {
                try
                {
                    Object localObject;
                    ((UpdateBuilder)localObject).updateColumnValue("content", paramString3);
                    ((UpdateBuilder)localObject).updateColumnValue("type", Integer.valueOf(paramChatMessage.getType()));
                    ((UpdateBuilder)localObject).updateColumnValue("timeSend", Integer.valueOf(paramChatMessage.getTimeSend()));
                    ((UpdateBuilder)localObject).where().eq("ownerId", paramString1).and().eq("userId", paramString2);
                    if (this.friendDao.update(((UpdateBuilder)localObject).prepare()) != -1) {
                        SmsLoadDataFromDBEvent.post(new SmsLoadDataFromDBEvent());
                    }
                    return;
                }
                catch (Exception paramString1)
                {
                    LogFinalUtils.logForException(paramString1);
                }
                paramString3 = paramString3;
                LogFinalUtils.logForException(paramString3);
            }
        }
    }

    public void updateMucFriendRoomName(String paramString1, String paramString2)
    {
        try
        {
            UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
            localUpdateBuilder.updateColumnValue("nickName", paramString2).where().eq("userId", paramString1);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateNickName(String paramString1, String paramString2, String paramString3)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            localUpdateBuilder.updateColumnValue("roomMyNickName", paramString3);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updatePrivacy(String paramString1, String paramString2, String paramString3)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            localUpdateBuilder.updateColumnValue("privacy", paramString3);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateUserNickName(String paramString1, String paramString2, String paramString3)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2);
            localUpdateBuilder.updateColumnValue("nickName", paramString3);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }

    public void updateUserNickName(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        UpdateBuilder localUpdateBuilder = this.friendDao.updateBuilder();
        try
        {
            localUpdateBuilder.where().eq("ownerId", paramString1).and().eq("userId", paramString2).and().eq("domain", paramString4);
            localUpdateBuilder.updateColumnValue("nickName", paramString3);
            this.friendDao.update(localUpdateBuilder.prepare());
            return;
        }
        catch (Exception paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
    }
}

