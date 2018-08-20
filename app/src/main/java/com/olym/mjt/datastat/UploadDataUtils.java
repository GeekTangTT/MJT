package com.olym.mjt.datastat;

import com.olym.mjt.network.client.HttpsClient;
import com.olym.mjt.utils.CachedThreadPoolUtils;
import com.olym.mjt.utils.ChannelUtil;

public class UploadDataUtils
{
    public static void uploadCallEvent(String paramString1, final String paramString2, final int paramInt1, final String paramString3, final boolean paramBoolean, final int paramInt2)
    {
        if (ChannelUtil.action_upload_event >= 4) {
            CachedThreadPoolUtils.getInstance().execute(new Runnable()
            {
                public void run()
                {
                    HttpsClient.getInstanse().uploadEvent(UploadDataFactory.getCallEventData(this.val$phone, paramString2, paramInt1, paramString3, paramBoolean, paramInt2));
                }
            });
        }
    }

    public static void uploadConflictEvent(String paramString1, final String paramString2)
    {
        if (ChannelUtil.action_upload_event >= 2) {
            CachedThreadPoolUtils.getInstance().execute(new Runnable()
            {
                public void run()
                {
                    HttpsClient.getInstanse().uploadEvent(UploadDataFactory.getConflictEventData(this.val$phone, paramString2));
                }
            });
        }
    }

    public static void uploadCrashEvent(String paramString)
    {
        if (ChannelUtil.action_upload_event >= 2) {
            CachedThreadPoolUtils.getInstance().execute(new Runnable()
            {
                public void run()
                {
                    HttpsClient.getInstanse().uploadEvent(UploadDataFactory.getCrashEventData(this.val$errorText));
                }
            });
        }
    }

    public static void uploadInCallEvent(String paramString1, final String paramString2, final int paramInt, final String paramString3, final boolean paramBoolean)
    {
        if (ChannelUtil.action_upload_event >= 4) {
            CachedThreadPoolUtils.getInstance().execute(new Runnable()
            {
                public void run()
                {
                    HttpsClient.getInstanse().uploadEvent(UploadDataFactory.getInCallEventData(this.val$phone, paramString2, paramInt, paramString3, paramBoolean));
                }
            });
        }
    }

    public static void uploadOfflineEvent()
    {
        if (ChannelUtil.action_upload_event >= 2) {
            CachedThreadPoolUtils.getInstance().execute(new Runnable()
            {
                public void run()
                {
                    HttpsClient.getInstanse().uploadEvent(UploadDataFactory.getOfflineEventData());
                }
            });
        }
    }

    public static void uploadOnlineEvent()
    {
        if (ChannelUtil.action_upload_event >= 2) {
            CachedThreadPoolUtils.getInstance().execute(new Runnable()
            {
                public void run()
                {
                    HttpsClient.getInstanse().uploadEvent(UploadDataFactory.getOnlineEventData());
                }
            });
        }
    }
}
