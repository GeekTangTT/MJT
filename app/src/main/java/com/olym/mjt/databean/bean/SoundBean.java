package com.olym.mjt.databean.bean;

public class SoundBean
{
    private boolean isSelected = false;
    private String title;
    private String uri;

    public SoundBean() {}

    public SoundBean(String paramString1, String paramString2)
    {
        this.title = paramString1;
        this.uri = paramString2;
    }

    public SoundBean(String paramString1, String paramString2, boolean paramBoolean)
    {
        this.title = paramString1;
        this.uri = paramString2;
        this.isSelected = paramBoolean;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getUri()
    {
        return this.uri;
    }

    public boolean isSame(SoundBean paramSoundBean)
    {
        return paramSoundBean.getUri().equals(this.uri);
    }

    public boolean isSelected()
    {
        return this.isSelected;
    }

    public void setSelected(boolean paramBoolean)
    {
        this.isSelected = paramBoolean;
    }

    public void setTitle(String paramString)
    {
        this.title = paramString;
    }

    public void setUri(String paramString)
    {
        this.uri = paramString;
    }

    public String toString()
    {
        return "SoundBean{title='" + this.title + '\'' + ", uri='" + this.uri + '\'' + ", isSelected=" + this.isSelected + '}';
    }
}
