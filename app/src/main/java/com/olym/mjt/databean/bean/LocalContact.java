package com.olym.mjt.databean.bean;

import android.text.TextUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class LocalContact
{
    private boolean Checked;
    @DatabaseField(generatedId=true)
    private int _id;
    @DatabaseField
    private int issecret = 0;
    @DatabaseField(canBeNull=true)
    private String localId;
    @DatabaseField(canBeNull=true)
    private String nickName;
    @DatabaseField(canBeNull=true)
    private String remarkName;
    @DatabaseField
    private String simplespell;
    @DatabaseField
    private String telephone;
    @DatabaseField
    private int version;
    @DatabaseField
    private String wholeSpell;

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

    public String getRemarkName()
    {
        return this.remarkName;
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

    public String getTelephone()
    {
        return this.telephone;
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

    public void setRemarkName(String paramString)
    {
        this.remarkName = paramString;
    }

    public void setSimplespell(String paramString)
    {
        this.simplespell = paramString;
    }

    public void setTelephone(String paramString)
    {
        this.telephone = paramString;
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
        return "LocalContact{_id=" + this._id + ", localId='" + this.localId + '\'' + ", nickName='" + this.nickName + '\'' + ", remarkName='" + this.remarkName + '\'' + ", version=" + this.version + ", telephone='" + this.telephone + '\'' + ", issecret=" + this.issecret + ", wholeSpell='" + this.wholeSpell + '\'' + ", simplespell='" + this.simplespell + '\'' + '}';
    }
}
