package com.olym.mjt.databean.bean;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.utils.DateUtil;
import java.io.Serializable;
import java.util.UUID;

@DatabaseTable
public class NewFriendMessage
        extends XmppMessage
        implements Cloneable, Serializable
{
    private static final long serialVersionUID = -4231369003725583507L;
    @DatabaseField(generatedId=true)
    private int _id;
    @DatabaseField
    private int companyId;
    @DatabaseField
    private String content;
    private int fromCompanyId;
    private String fromUserId;
    private String fromUserName;
    @DatabaseField
    private String ibcdomain;
    @DatabaseField
    private boolean isRead;
    @DatabaseField
    private String nickName;
    @DatabaseField(canBeNull=false)
    private String ownerId;
    @DatabaseField(canBeNull=false)
    private String telephone;
    @DatabaseField(defaultValue="0")
    private int unReadNum;
    @DatabaseField(canBeNull=false)
    private String userId;

    public NewFriendMessage() {}

    public NewFriendMessage(String paramString)
    {
        parserJsonData(paramString);
    }

    public static NewFriendMessage createWillSendMessage(User paramUser, int paramInt, String paramString, Friend paramFriend)
    {
        return createWillSendMessage(paramUser, paramInt, paramString, paramFriend.getUserId() + "@" + paramFriend.getDomain(), paramFriend.getNickName(), paramFriend.getCompanyId());
    }

    public static NewFriendMessage createWillSendMessage(User paramUser, int paramInt, String paramString, NewFriendMessage paramNewFriendMessage)
    {
        return createWillSendMessage(paramUser, paramInt, paramString, paramNewFriendMessage.getUserId() + "@" + paramNewFriendMessage.getIbcdomain(), paramNewFriendMessage.getNickName(), paramNewFriendMessage.getCompanyId());
    }

    public static NewFriendMessage createWillSendMessage(User paramUser1, int paramInt, String paramString, User paramUser2)
    {
        return createWillSendMessage(paramUser1, paramInt, paramString, paramUser2.getUserId() + "@" + paramUser2.getDomain(), paramUser2.getNickName(), paramUser2.getCompanyId());
    }

    public static NewFriendMessage createWillSendMessage(User paramUser, int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2)
    {
        String str = UUID.randomUUID().toString().replace("-", "");
        NewFriendMessage localNewFriendMessage = new NewFriendMessage();
        localNewFriendMessage.setPacketId(str);
        localNewFriendMessage.setFromUserId(paramUser.getUserId());
        localNewFriendMessage.setFromUserName(paramUser.getNickName());
        localNewFriendMessage.setFromCompanyId(paramUser.getCompanyId());
        localNewFriendMessage.setTimeSend(DateUtil.sk_time_current_time());
        localNewFriendMessage.setType(paramInt1);
        localNewFriendMessage.setContent(paramString1);
        localNewFriendMessage.setTelephone(paramUser.getTelephone());
        localNewFriendMessage.setOwnerId(paramUser.getUserId());
        localNewFriendMessage.setUserId(paramString2);
        localNewFriendMessage.setNickName(paramString3);
        localNewFriendMessage.setCompanyId(paramInt2);
        localNewFriendMessage.setRead(true);
        localNewFriendMessage.setMySend(true);
        return localNewFriendMessage;
    }

    private void parserJsonData(String paramString)
    {
        try
        {
            paramString = JSON.parseObject(paramString);
            this.userId = getStringValueFromJSONObject(paramString, "fromUserId");
            this.nickName = getStringValueFromJSONObject(paramString, "fromUserName");
            this.companyId = getIntValueFromJSONObject(paramString, "fromCompanyId");
            this.type = getIntValueFromJSONObject(paramString, "type");
            this.timeSend = getIntValueFromJSONObject(paramString, "timeSend");
            this.content = getStringValueFromJSONObject(paramString, "content");
            this.telephone = getStringValueFromJSONObject(paramString, "telephone");
            this.ibcdomain = getStringValueFromJSONObject(paramString, "ibcdomain");
            this.unReadNum = getIntValueFromJSONObject(paramString, "unReadNum");
            return;
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
            LogFinalUtils.logForException(paramString);
        }
    }

    public NewFriendMessage clone()
    {
        try
        {
            NewFriendMessage localNewFriendMessage = (NewFriendMessage)super.clone();
            return localNewFriendMessage;
        }
        catch (CloneNotSupportedException localCloneNotSupportedException)
        {
            localCloneNotSupportedException.printStackTrace();
            LogFinalUtils.logForException(localCloneNotSupportedException);
        }
        return null;
    }

    public int getCompanyId()
    {
        return this.companyId;
    }

    public String getContent()
    {
        return this.content;
    }

    public int getFromCompanyId()
    {
        return this.fromCompanyId;
    }

    public String getFromUserId()
    {
        return this.fromUserId;
    }

    public String getFromUserName()
    {
        return this.fromUserName;
    }

    public String getIbcdomain()
    {
        return this.ibcdomain;
    }

    public String getNickName()
    {
        return this.nickName;
    }

    public String getOwnerId()
    {
        return this.ownerId;
    }

    public String getTelephone()
    {
        return this.telephone;
    }

    public int getUnReadNum()
    {
        return this.unReadNum;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public int get_id()
    {
        return this._id;
    }

    public boolean isRead()
    {
        return this.isRead;
    }

    public void setCompanyId(int paramInt)
    {
        this.companyId = paramInt;
    }

    public void setContent(String paramString)
    {
        this.content = paramString;
    }

    public void setFromCompanyId(int paramInt)
    {
        this.fromCompanyId = paramInt;
    }

    public void setFromUserId(String paramString)
    {
        this.fromUserId = paramString;
    }

    public void setFromUserName(String paramString)
    {
        this.fromUserName = paramString;
    }

    public void setIbcdomain(String paramString)
    {
        this.ibcdomain = paramString;
    }

    public void setNickName(String paramString)
    {
        this.nickName = paramString;
    }

    public void setOwnerId(String paramString)
    {
        this.ownerId = paramString;
    }

    public void setRead(boolean paramBoolean)
    {
        this.isRead = paramBoolean;
    }

    public void setTelephone(String paramString)
    {
        this.telephone = paramString;
    }

    public void setUnReadNum(int paramInt)
    {
        this.unReadNum = paramInt;
    }

    public void setUserId(String paramString)
    {
        this.userId = paramString;
    }

    public void set_id(int paramInt)
    {
        this._id = paramInt;
    }

    public String toJsonString()
    {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("fromUserName", this.fromUserName);
        localJSONObject.put("type", Integer.valueOf(this.type));
        localJSONObject.put("timeSend", Integer.valueOf(this.timeSend));
        localJSONObject.put("messageId", this.packetId);
        localJSONObject.put("telephone", this.telephone);
        localJSONObject.put("ibcdomain", this.ibcdomain);
        localJSONObject.put("unReadNum", Integer.valueOf(this.unReadNum));
        if (!TextUtils.isEmpty(this.content)) {
            localJSONObject.put("content", this.content);
        }
        return localJSONObject.toString();
    }

    public String toString()
    {
        return "NewFriendMessage{_id=" + this._id + ", ownerId='" + this.ownerId + '\'' + ", userId='" + this.userId + '\'' + ", telephone='" + this.telephone + '\'' + ", nickName='" + this.nickName + '\'' + ", content='" + this.content + '\'' + ", isRead=" + this.isRead + ", companyId=" + this.companyId + ", fromUserId='" + this.fromUserId + '\'' + ", fromUserName='" + this.fromUserName + '\'' + ", fromCompanyId=" + this.fromCompanyId + '}';
    }
}

