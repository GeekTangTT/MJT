package com.olym.mjt.database.dao;

import android.content.res.Resources;
import com.olym.mjt.databean.bean.AttentionUser;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.event.CardUIUpdateEvent;
import com.olym.mjt.module.message.event.MessageMsgUiUpdateEvent;
import com.olym.mjt.module.message.event.MsgNumRefreshEvent;
import com.olym.mjt.module.message.event.MsgNumUpdateEvent;
import com.olym.mjt.utils.DateUtil;
import com.olym.mjt.utils.sharedpreferencesutils.TableVersionSp;

public class FriendHelper
{
    public static void addBlacklistExtraOperation(String paramString1, String paramString2, String paramString3)
    {
        MessageMsgUiUpdateEvent.post(new MessageMsgUiUpdateEvent());
        MsgNumUpdateEvent.post(new MsgNumUpdateEvent(-1, -1));
        MsgNumRefreshEvent.post(new MsgNumRefreshEvent(paramString2, paramString3));
    }

    public static void addFriendExtraOperation(String paramString1, String paramString2, String paramString3)
    {
        FriendDao.getInstance().addNewFriendInMsgTable(paramString1, paramString2, paramString3);
        MessageMsgUiUpdateEvent.post(new MessageMsgUiUpdateEvent());
        MsgNumUpdateEvent.post(new MsgNumUpdateEvent(1, 1));
        MsgNumRefreshEvent.post(new MsgNumRefreshEvent(paramString2, paramString3));
    }

    public static void beAddBlacklist(String paramString1, String paramString2, String paramString3)
    {
        if (FriendDao.getInstance().getFriend(paramString1, paramString2, paramString3) == null) {}
    }

    public static void beAddFriendExtraOperation(String paramString1, String paramString2, String paramString3)
    {
        FriendDao.getInstance().addNewFriendInMsgTable(paramString1, paramString2, paramString3);
        MessageMsgUiUpdateEvent.post(new MessageMsgUiUpdateEvent());
        MsgNumUpdateEvent.post(new MsgNumUpdateEvent(1, 1));
        MsgNumRefreshEvent.post(new MsgNumRefreshEvent(paramString2, paramString3));
        CardUIUpdateEvent.post(new CardUIUpdateEvent());
    }

    public static void beDeleteAllNewFriend(String paramString1, String paramString2, String paramString3)
    {
        if (LocalContactDao.getInstance().queryLocalContact(paramString1, paramString2, paramString3)) {
            removeFriend(paramString1, paramString2, paramString3);
        }
        for (;;)
        {
            MsgNumUpdateEvent.post(new MsgNumUpdateEvent(-1, -1));
            MsgNumRefreshEvent.post(new MsgNumRefreshEvent(paramString2, paramString3));
            NewFriendDao.getInstance().deleteNewFriend(paramString1, paramString2, paramString3);
            MessageMsgUiUpdateEvent.post(new MessageMsgUiUpdateEvent());
            CardUIUpdateEvent.post(new CardUIUpdateEvent());
            return;
            removeAttentionOrFriend(paramString1, paramString2, paramString3);
        }
    }

    public static void beDeleteSeeNewFriend(String paramString1, String paramString2, String paramString3)
    {
        Friend localFriend = FriendDao.getInstance().getFriend(paramString1, paramString2, paramString3);
        if ((localFriend != null) && (localFriend.getStatus() == 2))
        {
            localFriend.setStatus(1);
            localFriend.setContent("");
            FriendDao.getInstance().createOrUpdateFriend(localFriend);
            ChatMessageDao.getInstance().deleteSingleMessageTable(paramString1, paramString2, paramString3);
            MessageMsgUiUpdateEvent.post(new MessageMsgUiUpdateEvent());
            MsgNumUpdateEvent.post(new MsgNumUpdateEvent(-1, -1));
            MsgNumRefreshEvent.post(new MsgNumRefreshEvent(paramString2, paramString3));
            CardUIUpdateEvent.post(new CardUIUpdateEvent());
        }
    }

    public static void removeAttentionOrFriend(String paramString1, String paramString2, String paramString3)
    {
        FriendDao.getInstance().deleteFriend(paramString1, paramString2, paramString3);
        ChatMessageDao.getInstance().deleteSingleMessageTable(paramString1, paramString2, paramString3);
        MessageMsgUiUpdateEvent.post(new MessageMsgUiUpdateEvent());
        MsgNumUpdateEvent.post(new MsgNumUpdateEvent(-1, -1));
        MsgNumRefreshEvent.post(new MsgNumRefreshEvent(paramString2, paramString3));
    }

    public static void removeFriend(String paramString1, String paramString2, String paramString3)
    {
        FriendDao.getInstance().updateFriendStatus(paramString1, paramString2, 3, paramString3);
        NewFriendDao.getInstance().deleteNewFriend(paramString1, paramString2, paramString3);
        ChatMessageDao.getInstance().deleteSingleMessageTable(paramString1, paramString2, paramString3);
    }

    public static boolean updateFriendRelationship(String paramString1, String paramString2, AttentionUser paramAttentionUser)
    {
        Friend localFriend = FriendDao.getInstance().getFriend(paramString1, paramString2);
        if ((paramAttentionUser == null) || ((paramAttentionUser.getStatus() != 1) && (paramAttentionUser.getStatus() != 2)))
        {
            if (localFriend == null) {}
            return false;
        }
        label118:
        int i;
        if (localFriend == null)
        {
            localFriend = new Friend();
            localFriend.setTimeCreate(paramAttentionUser.getCreateTime());
            localFriend.setTimeSend(DateUtil.sk_time_current_time());
            localFriend.setOwnerId(paramAttentionUser.getUserId());
            localFriend.setUserId(paramAttentionUser.getToUserId());
            if (paramAttentionUser.getToNickName() != null)
            {
                localFriend.setNickName(paramAttentionUser.getToNickName());
                if (paramAttentionUser.getRemarkName() == null) {
                    break label198;
                }
                localFriend.setRemarkName(paramAttentionUser.getRemarkName());
                localFriend.setRoomFlag(0);
                localFriend.setCompanyId(paramAttentionUser.getCompanyId());
                if (paramAttentionUser.getBlacklist() != 0) {
                    break label217;
                }
                i = paramAttentionUser.getStatus();
                label145:
                localFriend.setStatus(i);
                localFriend.setVersion(TableVersionSp.getInstanse().getFriendTableVersion(paramString1));
                FriendDao.getInstance().createOrUpdateFriend(localFriend);
                if (i != -1) {
                    break label222;
                }
            }
            for (;;)
            {
                return true;
                localFriend.setNickName(MjtApplication.getInstance().getResources().getString(2131690253));
                break;
                label198:
                localFriend.setRemarkName(MjtApplication.getInstance().getResources().getString(2131690253));
                break label118;
                label217:
                i = -1;
                break label145;
                label222:
                if ((i != 1) && (i == 2)) {
                    addFriendExtraOperation(paramString1, paramString2, localFriend.getDomain());
                }
            }
        }
        if (paramAttentionUser.getBlacklist() == 0)
        {
            i = paramAttentionUser.getStatus();
            label257:
            if (i == localFriend.getStatus()) {
                break label299;
            }
            FriendDao.getInstance().updateFriendStatus(paramString1, paramString2, i, localFriend.getDomain());
            if (i != -1) {
                break label313;
            }
            if (localFriend.getStatus() != 1) {
                break label301;
            }
        }
        for (;;)
        {
            return true;
            i = -1;
            break label257;
            label299:
            break;
            label301:
            if (localFriend.getStatus() == 2)
            {
                continue;
                label313:
                if (i == 1)
                {
                    if (localFriend.getStatus() == -1) {
                        addBlacklistExtraOperation(paramString1, paramString2, localFriend.getDomain());
                    } else if (localFriend.getStatus() == 2) {
                        addFriendExtraOperation(paramString1, paramString2, localFriend.getDomain());
                    }
                }
                else if (i == 2) {
                    if (localFriend.getStatus() == -1)
                    {
                        addBlacklistExtraOperation(paramString1, paramString2, localFriend.getDomain());
                    }
                    else if (localFriend.getStatus() == 1)
                    {
                        ChatMessageDao.getInstance().deleteSingleMessageTable(paramString1, paramString2, localFriend.getDomain());
                        MessageMsgUiUpdateEvent.post(new MessageMsgUiUpdateEvent());
                        MsgNumUpdateEvent.post(new MsgNumUpdateEvent(-1, -1));
                        MsgNumRefreshEvent.post(new MsgNumRefreshEvent(paramString2, localFriend.getDomain()));
                    }
                }
            }
        }
    }
}
