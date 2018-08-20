package com.olym.mjt.databean.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CompanyGroup
{
    @DatabaseField(generatedId=true)
    private int _id;
    @DatabaseField
    private String id;
    @DatabaseField
    private int is_parent;
    @DatabaseField
    private String name;
    @DatabaseField
    private String parent_group_id;
    @DatabaseField
    private int people_num;
    @DatabaseField
    private int type;

    public String getId()
    {
        return this.id;
    }

    public int getIs_parent()
    {
        return this.is_parent;
    }

    public String getName()
    {
        return this.name;
    }

    public String getParent_group_id()
    {
        return this.parent_group_id;
    }

    public int getPeople_num()
    {
        return this.people_num;
    }

    public int get_id()
    {
        return this._id;
    }

    public void setId(String paramString)
    {
        this.id = paramString;
    }

    public void setIs_parent(int paramInt)
    {
        this.is_parent = paramInt;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }

    public void setParent_group_id(String paramString)
    {
        this.parent_group_id = paramString;
    }

    public void setPeople_num(int paramInt)
    {
        this.people_num = paramInt;
    }

    public void set_id(int paramInt)
    {
        this._id = paramInt;
    }

    public String toString()
    {
        return "CompanyGroup{_id=" + this._id + ", name='" + this.name + '\'' + ", id='" + this.id + '\'' + ", is_parent=" + this.is_parent + ", people_num=" + this.people_num + ", parent_group_id='" + this.parent_group_id + '\'' + ", type=" + this.type + '}';
    }
}
