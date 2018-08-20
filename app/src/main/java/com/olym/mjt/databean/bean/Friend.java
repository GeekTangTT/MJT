package com.olym.mjt.databean.bean;

import android.content.res.Resources;
import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.olym.mjt.module.MjtApplication;
import java.io.Serializable;

@DatabaseTable
public class Friend
        implements Serializable
{
    public static final String ID_BLOG_MESSAGE = "10002";
    public static final String ID_INTERVIEW_MESSAGE = "10004";
    public static final String ID_LOCAL_CONTACT = "10006";
    public static final String ID_MUC_ROOM = "10005";
    public static final String ID_MUC_ROOM_EX = "pushuser007";
    public static final String ID_NEW_FRIEND_MESSAGE = "10001";
    public static final String ID_SYSTEM_MESSAGE = "10000";
    public static final String NICKNAME_NEW_FRIEND_MESSAGE = MjtApplication.getInstance().getResources().getString(2131689943);
    public static final String NICKNAME_SYSTEM_MESSAGE = MjtApplication.getInstance().getResources().getString(2131690102);
    public static final int STATUS_ATTENTION = 1;
    public static final int STATUS_BLACKLIST = -1;
    public static final int STATUS_COLLEAGUE = 5;
    public static final int STATUS_FRIEND = 2;
    public static final int STATUS_NO_SHOW_SYSTEM = 9;
    public static final int STATUS_SELF = 9999;
    public static final int STATUS_SYSTEM = 8;
    public static final int STATUS_UNKNOW = 0;
    public static final int STATUS_WILL_BE_FRIEND = 3;
    private static final long serialVersionUID = -6859528031175998594L;
    private boolean Checked;
    @DatabaseField(generatedId=true)
    private int _id;
    @DatabaseField(defaultValue="0")
    private int companyId;
    @DatabaseField
    private String content;
    @DatabaseField
    private String description;
    @DatabaseField(defaultValue="null")
    private String domain;
    @DatabaseField
    private String domainVersion;
    @DatabaseField
    private int issecret = 0;
    @DatabaseField
    private String localId;
    @JSONField(name="nickname")
    @DatabaseField(canBeNull=false)
    private String nickName;
    @DatabaseField(canBeNull=false)
    private String ownerId;
    @DatabaseField
    private String privacy;
    @DatabaseField
    private String remarkName;
    @DatabaseField
    private String roomCreateUserId;
    @DatabaseField(defaultValue="0")
    private int roomFlag;
    @DatabaseField
    private String roomId;
    @DatabaseField
    private int roomLastTime;
    @DatabaseField
    private String roomMyNickName;
    @DatabaseField
    private int roomTalkTime;
    @DatabaseField
    private int roomcreatetime;
    @DatabaseField
    private String simplespell;
    @DatabaseField
    private int status;
    @DatabaseField
    private int timeCreate;
    @DatabaseField
    private int timeSend;
    @DatabaseField
    private String toTelephone;
    @DatabaseField
    private int type;
    @DatabaseField(defaultValue="0")
    private int unReadNum;
    @DatabaseField(canBeNull=false)
    private String userId;
    @DatabaseField
    private int version;
    @DatabaseField
    private String wholeSpell;

    public int getCompanyId()
    {
        return this.companyId;
    }

    public String getContent()
    {
        return this.content;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getDomain()
    {
        return this.domain;
    }

    public String getDomainVersion()
    {
        return this.domainVersion;
    }

    public int getIssecret()
    {
        return this.issecret;
    }

    public String getLocalId()
    {
        return this.localId;
    }

    public String getNickName()
    {
        return this.nickName;
    }

    public String getOwnerId()
    {
        return this.ownerId;
    }

    public String getPrivacy()
    {
        return this.privacy;
    }

    public String getRemarkName()
    {
        return this.remarkName;
    }

    public String getRoomCreateUserId()
    {
        return this.roomCreateUserId;
    }

    public int getRoomFlag()
    {
        return this.roomFlag;
    }

    public String getRoomId()
    {
        return this.roomId;
    }

    public int getRoomLastTime()
    {
        return this.roomLastTime;
    }

    public String getRoomMyNickName()
    {
        return this.roomMyNickName;
    }

    public int getRoomTalkTime()
    {
        return this.roomTalkTime;
    }

    public int getRoomcreatetime()
    {
        return this.roomcreatetime;
    }

    public String getShowName()
    {
        if (!TextUtils.isEmpty(this.remarkName)) {
            return this.remarkName.trim();
        }
        if (!TextUtils.isEmpty(this.nickName)) {
            return this.nickName.trim();
        }
        return "";
    }

    public String getSimplespell()
    {
        return this.simplespell;
    }

    public int getStatus()
    {
        return this.status;
    }

    public int getTimeCreate()
    {
        return this.timeCreate;
    }

    public int getTimeSend()
    {
        return this.timeSend;
    }

    public String getToTelephone()
    {
        return this.toTelephone;
    }

    public int getType()
    {
        return this.type;
    }

    public int getUnReadNum()
    {
        return this.unReadNum;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public int getVersion()
    {
        return this.version;
    }

    public String getWholeSpell()
    {
        return this.wholeSpell;
    }

    public int get_id()
    {
        return this._id;
    }

    public boolean isChecked()
    {
        return this.Checked;
    }

    public void setChecked(boolean paramBoolean)
    {
        this.Checked = paramBoolean;
    }

    public void setCompanyId(int paramInt)
    {
        this.companyId = paramInt;
    }

    public void setContent(String paramString)
    {
        this.content = paramString;
    }

    public void setDescription(String paramString)
    {
        this.description = paramString;
    }

    public void setDomain(String paramString)
    {
        this.domain = paramString;
    }

    public void setDomainVersion(String paramString)
    {
        this.domainVersion = paramString;
    }

    public void setIssecret(int paramInt)
    {
        this.issecret = paramInt;
    }

    public void setLocalId(String paramString)
    {
        this.localId = paramString;
    }

    public void setNickName(String paramString)
    {
        this.nickName = paramString;
    }

    public void setOwnerId(String paramString)
    {
        this.ownerId = paramString;
    }

    public void setPrivacy(String paramString)
    {
        this.privacy = paramString;
    }

    public void setRemarkName(String paramString)
    {
        this.remarkName = paramString;
    }

    public void setRoomCreateUserId(String paramString)
    {
        this.roomCreateUserId = paramString;
    }

    public void setRoomFlag(int paramInt)
    {
        this.roomFlag = paramInt;
    }

    public void setRoomId(String paramString)
    {
        this.roomId = paramString;
    }

    public void setRoomLastTime(int paramInt)
    {
        this.roomLastTime = paramInt;
    }

    public void setRoomMyNickName(String paramString)
    {
        this.roomMyNickName = paramString;
    }

    public void setRoomTalkTime(int paramInt)
    {
        this.roomTalkTime = paramInt;
    }

    public void setRoomcreatetime(int paramInt)
    {
        this.roomcreatetime = paramInt;
    }

    public void setSimplespell(String paramString)
    {
        this.simplespell = paramString;
    }

    public void setStatus(int paramInt)
    {
        this.status = paramInt;
    }

    public void setTimeCreate(int paramInt)
    {
        this.timeCreate = paramInt;
    }

    public void setTimeSend(int paramInt)
    {
        this.timeSend = paramInt;
    }

    public void setToTelephone(String paramString)
    {
        this.toTelephone = paramString;
    }

    public void setType(int paramInt)
    {
        this.type = paramInt;
    }

    public void setUnReadNum(int paramInt)
    {
        this.unReadNum = paramInt;
    }

    public void setUserId(String paramString)
    {
        this.userId = paramString;
    }

    public void setVersion(int paramInt)
    {
        this.version = paramInt;
    }

    public void setWholeSpell(String paramString)
    {
        this.wholeSpell = paramString;
    }

    public void set_id(int paramInt)
    {
        this._id = paramInt;
    }

    public String toString()
    {
        return "Friend{_id=" + this._id + ", ownerId='" + this.ownerId + '\'' + ", userId='" + this.userId + '\'' + ", domain='" + this.domain + '\'' + ", domainVersion='" + this.domainVersion + '\'' + ", nickName='" + this.nickName + '\'' + ", description='" + this.description + '\'' + ", timeCreate=" + this.timeCreate + ", unReadNum=" + this.unReadNum + ", content='" + this.content + '\'' + ", type=" + this.type + ", timeSend=" + this.timeSend + ", roomFlag=" + this.roomFlag + ", companyId=" + this.companyId + ", status=" + this.status + ", privacy='" + this.privacy + '\'' + ", remarkName='" + this.remarkName + '\'' + ", version=" + this.version + ", roomId='" + this.roomId + '\'' + ", roomCreateUserId='" + this.roomCreateUserId + '\'' + ", roomMyNickName='" + this.roomMyNickName + '\'' + ", roomTalkTime=" + this.roomTalkTime + ", toTelephone='" + this.toTelephone + '\'' + ", localId='" + this.localId + '\'' + ", issecret=" + this.issecret + ", wholeSpell='" + this.wholeSpell + '\'' + ", simplespell='" + this.simplespell + '\'' + ", Checked=" + this.Checked + '}';
    }
}

