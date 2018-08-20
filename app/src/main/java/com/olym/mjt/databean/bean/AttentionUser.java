package com.olym.mjt.databean.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;

public class AttentionUser
        implements Serializable
{
    private int blacklist;
    private int companyId;
    private int createTime;
    private String domain;
    private int groupId;
    private String groupName;
    private String localId;
    private int modifyTime;
    private String remarkName;
    private String simplespell;
    private int status;
    @JSONField(name="toNickname")
    private String toNickName;
    private String toTelephone;
    private String toUserId;
    private String userId;
    private String version;
    private String wholeSpell;

    public int getBlacklist()
    {
        return this.blacklist;
    }

    public int getCompanyId()
    {
        return this.companyId;
    }

    public int getCreateTime()
    {
        return this.createTime;
    }

    public String getDomain()
    {
        return this.domain;
    }

    public int getGroupId()
    {
        return this.groupId;
    }

    public String getGroupName()
    {
        return this.groupName;
    }

    public String getLocalId()
    {
        return this.localId;
    }

    public int getModifyTime()
    {
        return this.modifyTime;
    }

    public String getRemarkName()
    {
        return this.remarkName;
    }

    public String getSimplespell()
    {
        return this.simplespell;
    }

    public int getStatus()
    {
        return this.status;
    }

    public String getToNickName()
    {
        return this.toNickName;
    }

    public String getToTelephone()
    {
        return this.toTelephone;
    }

    public String getToUserId()
    {
        return this.toUserId;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public String getVersion()
    {
        return this.version;
    }

    public String getWholeSpell()
    {
        return this.wholeSpell;
    }

    public void setBlacklist(int paramInt)
    {
        this.blacklist = paramInt;
    }

    public void setCompanyId(int paramInt)
    {
        this.companyId = paramInt;
    }

    public void setCreateTime(int paramInt)
    {
        this.createTime = paramInt;
    }

    public void setDomain(String paramString)
    {
        this.domain = paramString;
    }

    public void setGroupId(int paramInt)
    {
        this.groupId = paramInt;
    }

    public void setGroupName(String paramString)
    {
        this.groupName = paramString;
    }

    public void setLocalId(String paramString)
    {
        this.localId = paramString;
    }

    public void setModifyTime(int paramInt)
    {
        this.modifyTime = paramInt;
    }

    public void setRemarkName(String paramString)
    {
        this.remarkName = paramString;
    }

    public void setSimplespell(String paramString)
    {
        this.simplespell = paramString;
    }

    public void setStatus(int paramInt)
    {
        this.status = paramInt;
    }

    public void setToNickName(String paramString)
    {
        this.toNickName = paramString;
    }

    public void setToTelephone(String paramString)
    {
        this.toTelephone = paramString;
    }

    public void setToUserId(String paramString)
    {
        this.toUserId = paramString;
    }

    public void setUserId(String paramString)
    {
        this.userId = paramString;
    }

    public void setVersion(String paramString)
    {
        this.version = paramString;
    }

    public void setWholeSpell(String paramString)
    {
        this.wholeSpell = paramString;
    }

    public String toString()
    {
        return "AttentionUser{blacklist=" + this.blacklist + ", createTime=" + this.createTime + ", groupId=" + this.groupId + ", groupName='" + this.groupName + '\'' + ", modifyTime=" + this.modifyTime + ", status=" + this.status + ", toUserId='" + this.toUserId + '\'' + ", userId='" + this.userId + '\'' + ", toTelephone='" + this.toTelephone + '\'' + ", companyId=" + this.companyId + ", localId='" + this.localId + '\'' + ", toNickName='" + this.toNickName + '\'' + ", remarkName='" + this.remarkName + '\'' + ", wholeSpell='" + this.wholeSpell + '\'' + ", simplespell='" + this.simplespell + '\'' + ", domain='" + this.domain + '\'' + ", version='" + this.version + '\'' + '}';
    }
}

