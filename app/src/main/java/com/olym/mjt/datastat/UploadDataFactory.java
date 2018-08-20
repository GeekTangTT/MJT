package com.olym.mjt.datastat;

import android.os.Build;
import android.os.Build.VERSION;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.DeviceInfoUtil;
import com.olym.mjt.utils.IpUtils;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;

public class UploadDataFactory
{
    private static final int EVENT_MODULE_SIP = 1;
    private static final int EVENT_MODULE_XMPP = 2;
    private static final int EVENT_TYPE_CALL = 3;
    private static final int EVENT_TYPE_CONFLICT = 6;
    private static final int EVENT_TYPE_CRASH = 5;
    private static final int EVENT_TYPE_INCALL = 4;
    private static final int EVENT_TYPE_OFFLINE = 2;
    private static final int EVENT_TYPE_ONLINE = 1;
    private static final int OP_CODE_FAIL = 0;
    private static final int OP_CODE_SUCCESS = 1;

    private static void fillDefaultData(UploadData paramUploadData)
    {
        paramUploadData.setEvent_module(1);
        paramUploadData.setUser_id(UserSpUtil.getInstanse().getPhone());
        paramUploadData.setUser_domain(UserSpUtil.getInstanse().getIBCDomain());
        if (IpUtils.isWifi()) {}
        for (int i = 1;; i = 2)
        {
            paramUploadData.setUser_net(i);
            paramUploadData.setUser_dev_type(Build.MODEL);
            paramUploadData.setUser_dev_os("android" + Build.VERSION.SDK_INT);
            paramUploadData.setUser_app_versio(DeviceInfoUtil.getVersionName(MjtApplication.getInstance()) + "_" + ChannelUtil.currentChannel);
            paramUploadData.setOp_code(1);
            paramUploadData.setPhone_type(2);
            return;
        }
    }

    public static UploadData getCallEventData(String paramString1, String paramString2, int paramInt1, String paramString3, boolean paramBoolean, int paramInt2)
    {
        UploadData localUploadData = new UploadData();
        fillDefaultData(localUploadData);
        localUploadData.setEvent_type(3);
        localUploadData.setPeer_id(paramString1);
        localUploadData.setPeer_domain(paramString2);
        localUploadData.setDuration(paramInt1);
        localUploadData.setError_text(paramString3);
        if (paramBoolean) {}
        for (paramInt1 = 1;; paramInt1 = 0)
        {
            localUploadData.setOp_code(paramInt1);
            localUploadData.setRing_time(paramInt2);
            return localUploadData;
        }
    }

    public static UploadData getConflictEventData(String paramString1, String paramString2)
    {
        UploadData localUploadData = new UploadData();
        fillDefaultData(localUploadData);
        localUploadData.setEvent_type(6);
        localUploadData.setEvent_module(2);
        localUploadData.setUser_id(paramString1);
        localUploadData.setUser_domain(paramString2);
        return localUploadData;
    }

    public static UploadData getCrashEventData(String paramString)
    {
        UploadData localUploadData = new UploadData();
        fillDefaultData(localUploadData);
        localUploadData.setEvent_type(5);
        localUploadData.setError_text(paramString);
        return localUploadData;
    }

    public static UploadData getInCallEventData(String paramString1, String paramString2, int paramInt, String paramString3, boolean paramBoolean)
    {
        UploadData localUploadData = new UploadData();
        fillDefaultData(localUploadData);
        localUploadData.setEvent_type(4);
        localUploadData.setPeer_id(paramString1);
        localUploadData.setPeer_domain(paramString2);
        localUploadData.setDuration(paramInt);
        localUploadData.setError_text(paramString3);
        if (paramBoolean) {}
        for (paramInt = 1;; paramInt = 0)
        {
            localUploadData.setOp_code(paramInt);
            return localUploadData;
        }
    }

    public static UploadData getOfflineEventData()
    {
        UploadData localUploadData = new UploadData();
        fillDefaultData(localUploadData);
        localUploadData.setEvent_type(2);
        return localUploadData;
    }

    public static UploadData getOnlineEventData()
    {
        UploadData localUploadData = new UploadData();
        fillDefaultData(localUploadData);
        localUploadData.setEvent_type(1);
        return localUploadData;
    }
}
