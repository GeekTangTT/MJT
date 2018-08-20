package org.apache.http.cookie;

@Deprecated
public abstract interface SetCookie2
        extends SetCookie
{
    public abstract void setCommentURL(String paramString);

    public abstract void setDiscard(boolean paramBoolean);

    public abstract void setPorts(int[] paramArrayOfInt);
}

