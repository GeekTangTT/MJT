package com.olym.mjt.databean.event;

import android.app.PendingIntent;
import android.content.ContentValues;
import com.olym.mjt.pjsip.sip.api.SipCallSession;
import com.olym.mjt.utils.EventBusUtil;

public class ShowNotificationEvent
{
    public static final int TYPE_DELETE = 10;
    public static final int TYPE_REMOVE_SIP_CONTENT = 9;
    public static final int TYPE_REMOVE_XMPP_CONTENT = 2;
    public static final int TYPE_UPDATE_SIP_CALLING = 5;
    public static final int TYPE_UPDATE_SIP_CONFIRM = 7;
    public static final int TYPE_UPDATE_SIP_INCOMING = 6;
    public static final int TYPE_UPDATE_SIP_MISS = 8;
    public static final int TYPE_UPDATE_SIP_STATUS = 4;
    public static final int TYPE_UPDATE_XMPP_CONTENT = 1;
    public static final int TYPE_UPDATE_XMPP_STATUE = 3;
    private String content;
    private ContentValues contentValues;
    private boolean isOnline;
    private PendingIntent pendingIntent;
    private SipCallSession sipCallSession;
    private int type;
    private int xmppStatus;

    public ShowNotificationEvent(int paramInt)
    {
        this.type = paramInt;
    }

    public ShowNotificationEvent(int paramInt1, int paramInt2)
    {
        this.type = paramInt1;
        this.xmppStatus = paramInt2;
    }

    public ShowNotificationEvent(int paramInt, PendingIntent paramPendingIntent, String paramString)
    {
        this.type = paramInt;
        this.pendingIntent = paramPendingIntent;
        this.content = paramString;
    }

    public ShowNotificationEvent(int paramInt, ContentValues paramContentValues)
    {
        this.type = paramInt;
        this.contentValues = paramContentValues;
    }

    public ShowNotificationEvent(int paramInt, SipCallSession paramSipCallSession)
    {
        this.type = paramInt;
        this.sipCallSession = paramSipCallSession;
    }

    public ShowNotificationEvent(int paramInt, boolean paramBoolean)
    {
        this.type = paramInt;
        this.isOnline = paramBoolean;
    }

    public static void post(ShowNotificationEvent paramShowNotificationEvent)
    {
        EventBusUtil.post(paramShowNotificationEvent);
    }

    public String getContent()
    {
        return this.content;
    }

    public ContentValues getContentValues()
    {
        return this.contentValues;
    }

    public PendingIntent getPendingIntent()
    {
        return this.pendingIntent;
    }

    public SipCallSession getSipCallSession()
    {
        return this.sipCallSession;
    }

    public int getType()
    {
        return this.type;
    }

    public int getXmppStatus()
    {
        return this.xmppStatus;
    }

    public boolean isOnline()
    {
        return this.isOnline;
    }
}
