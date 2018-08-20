package com.olym.mjt.utils.sharedpreferencesutils;

import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings.System;
import com.alibaba.fastjson.JSON;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.databean.bean.SoundBean;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.FileIOUtils;
import com.olym.mjt.utils.FileUtils;
import com.olym.mjt.utils.cipher.CryptUtils;
import java.io.File;

public class AppSpUtil
{
    private static final String KEY_ACTION_BRACELET = "action_bracelet";
    private static final String KEY_ACTION_TF = "action_tf";
    private static final String KEY_APPLOCK_GESTURE = "applock_gesture";
    private static final String KEY_APPLOCK_GESTURE_ENABLE = "applock_gesture_enable";
    public static final String KEY_BACKGROUND_TIME = "key_background_time";
    private static final String KEY_BOUND_BRACELET = "bound_bracelet";
    private static final String KEY_BOUND_BRACELET_ID = "bound_bracelet_id";
    private static final String KEY_CHATMESSAGE_FIRE_DELETE = "chatmessage_fire_delete";
    private static final String KEY_CLEAR_DATAS = "clear_datas_413";
    private static final String KEY_DB_PASSWORD = "password_db";
    private static final String KEY_DEVICE_ID = "device_id";
    private static final String KEY_FIRST_START = "first_start";
    private static final String KEY_GESTURE_ERROR_COUNT = "gesture_error_count";
    private static final String KEY_GOOGLE_TOKEN = "key_google_token";
    private static final String KEY_INIT_CONTACTS = "init_contacts";
    private static final String KEY_INIT_ROOM = "init_room";
    private static final String KEY_MESSAGE_STATUS_REACHED = "message_status_reached";
    private static final String KEY_MESSAGE_STATUS_READ = "message_status_read";
    private static final String KEY_MESSAGE_STATUS_SENT = "message_status_sent";
    private static final String KEY_NOTIFICATION_SOUND = "notificaiton_sound";
    private static final String KEY_RING_TONE = "ring_tone";
    private static final String KEY_SELF_DISTRUCT = "key_self_distruct";
    private static final String KEY_SHOW_KEEPLIVE = "show_keeplive";
    private static final String KEY_SOUND_ENABLE = "sound_enable";
    public static final String KEY_TEST_MODE = "key_test_mode";
    private static final String KEY_VERSION_CHEKC_DATE = "version_check_date";
    private static final String KEY_VIBRATE_ENABLE = "vibrate_enable";
    private static final String KEY_VOIP_OPEN = "voip_open";
    private static final String KEY_WRITE_LOG_INDEX = "write_log_index";
    public static final int MAX_ERROR_COUNT = 5;
    private static final String NAME = "app";
    private static volatile AppSpUtil instanse;
    private SPUtils spUtils = new SPUtils("app");

    private String getDBPasswordKeyName()
    {
        return UserSpUtil.getInstanse().getPhone() + "_" + UserSpUtil.getInstanse().getUserDomain() + "_" + "password_db";
    }

    public static AppSpUtil getInstanse()
    {
        if (instanse == null) {}
        try
        {
            if (instanse == null) {
                instanse = new AppSpUtil();
            }
            return instanse;
        }
        finally {}
    }

    public boolean getActionBracelet()
    {
        boolean bool = false;
        if (ChannelUtil.currentChannel == 105) {
            bool = this.spUtils.getBoolean("action_bracelet", false);
        }
        return bool;
    }

    public boolean getActionTF()
    {
        boolean bool = false;
        if (ChannelUtil.currentChannel == 105) {
            bool = this.spUtils.getBoolean("action_tf", false);
        }
        return bool;
    }

    public String getApplockGesture()
    {
        Object localObject1 = new File(MjtApplication.getInstance().mGesturePassOldName);
        Object localObject2 = new File(MjtApplication.getInstance().mGesturePassNewName);
        if ((!((File)localObject1).exists()) || (!((File)localObject2).exists())) {
            localObject1 = "";
        }
        for (;;)
        {
            return (String)localObject1;
            try
            {
                if (!EngineUtils.getInstance().verifySignFile(MjtApplication.getInstance().mGesturePassOldName, MjtApplication.getInstance().mGesturePassNewName, "").contains(UserSpUtil.getInstanse().getPhone())) {
                    return "";
                }
                localObject2 = FileIOUtils.readFile2String(new File(MjtApplication.getInstance().mGesturePassOldName));
                localObject1 = localObject2;
                if (!((String)localObject2).equals(""))
                {
                    localObject1 = CryptUtils.nativeDecrypt(MjtApplication.getInstance(), (String)localObject2);
                    localObject1 = EngineUtils.getInstance().decryptCms(UserSpUtil.getInstanse().getPhone(), (String)localObject1);
                    return (String)localObject1;
                }
            }
            catch (Exception localException)
            {
                LogFinalUtils.logForException(localException);
            }
        }
        return "";
    }

    public boolean getApplockGestureenable()
    {
        return this.spUtils.getBoolean("applock_gesture_enable", false);
    }

    public long getBackGroundTime()
    {
        return this.spUtils.getLong("key_background_time", 0L);
    }

    public boolean getBoundBracelet()
    {
        return this.spUtils.getBoolean("bound_bracelet", false);
    }

    public String getBoundBraceletId()
    {
        return this.spUtils.getString("bound_bracelet_id", "");
    }

    public long getChatMessageFireDate()
    {
        return this.spUtils.getLong("chatmessage_fire_delete", 0L);
    }

    public boolean getClearDatas()
    {
        return this.spUtils.getBoolean("clear_datas_413", true);
    }

    public String getDBPassword()
    {
        String str1 = this.spUtils.getString(getDBPasswordKeyName(), "");
        if (!str1.equals("")) {
            return CryptUtils.decryptRandomPass(UserSpUtil.getInstanse().getPhone(), str1);
        }
        str1 = CryptUtils.getStringRandom(32);
        String str2 = CryptUtils.encryptRandomPass(UserSpUtil.getInstanse().getPhone(), str1);
        this.spUtils.put(getDBPasswordKeyName(), str2);
        return str1;
    }

    public SoundBean getDefaultNotificationSound()
    {
        SoundBean localSoundBean = new SoundBean();
        Uri localUri = RingtoneManager.getDefaultUri(2);
        localSoundBean.setTitle(MjtApplication.getInstance().getResources().getString(2131690101));
        localSoundBean.setUri(localUri.toString());
        return localSoundBean;
    }

    public SoundBean getDefaultRingTone()
    {
        SoundBean localSoundBean = new SoundBean();
        localSoundBean.setTitle(MjtApplication.getInstance().getResources().getString(2131690101));
        localSoundBean.setUri(Settings.System.DEFAULT_RINGTONE_URI.toString());
        return localSoundBean;
    }

    public String getDeviceId()
    {
        return this.spUtils.getString("device_id", null);
    }

    public boolean getFirstStart()
    {
        return this.spUtils.getBoolean("first_start", true);
    }

    public int getGestureErrorCount()
    {
        return this.spUtils.getInt("gesture_error_count", 0);
    }

    public String getGoogleToken()
    {
        return this.spUtils.getString("key_google_token", "");
    }

    public boolean getInitContacts()
    {
        return this.spUtils.getBoolean("init_contacts", false);
    }

    public boolean getInitRoom()
    {
        return this.spUtils.getBoolean("init_room", false);
    }

    public boolean getKeepLive()
    {
        return this.spUtils.getBoolean("show_keeplive", true);
    }

    public boolean getMessageStatusReached()
    {
        return this.spUtils.getBoolean("message_status_reached", true);
    }

    public boolean getMessageStatusRead()
    {
        return this.spUtils.getBoolean("message_status_read", true);
    }

    public boolean getMessageStatusSent()
    {
        return this.spUtils.getBoolean("message_status_sent", true);
    }

    public SoundBean getNotificationSound()
    {
        Object localObject = this.spUtils.getString("notificaiton_sound", null);
        if (localObject != null) {
            try
            {
                localObject = (SoundBean)JSON.parseObject((String)localObject, SoundBean.class);
                return (SoundBean)localObject;
            }
            catch (Exception localException)
            {
                for (;;)
                {
                    localSoundBean = getDefaultNotificationSound();
                    setNotificationSound(localSoundBean);
                }
            }
        }
        SoundBean localSoundBean = getDefaultNotificationSound();
        setNotificationSound(localSoundBean);
        return localSoundBean;
    }

    public String getOtherApplockGesture()
    {
        Object localObject1 = new File(MjtApplication.getInstance().mOtherGesturePassOldName);
        Object localObject2 = new File(MjtApplication.getInstance().mOtherGesturePassNewName);
        if ((!((File)localObject1).exists()) || (!((File)localObject2).exists())) {
            localObject1 = "";
        }
        for (;;)
        {
            return (String)localObject1;
            try
            {
                if (!EngineUtils.getInstance().verifySignFile(MjtApplication.getInstance().mOtherGesturePassOldName, MjtApplication.getInstance().mOtherGesturePassNewName, "").contains(UserSpUtil.getInstanse().getPhone())) {
                    return "";
                }
                localObject2 = FileIOUtils.readFile2String(new File(MjtApplication.getInstance().mOtherGesturePassOldName));
                localObject1 = localObject2;
                if (!((String)localObject2).equals(""))
                {
                    localObject1 = CryptUtils.nativeDecrypt(MjtApplication.getInstance(), (String)localObject2);
                    localObject1 = EngineUtils.getInstance().decryptCms(UserSpUtil.getInstanse().getPhone(), (String)localObject1);
                    return (String)localObject1;
                }
            }
            catch (Exception localException)
            {
                LogFinalUtils.logForException(localException);
            }
        }
        return "";
    }

    public SoundBean getRingTone()
    {
        Object localObject = this.spUtils.getString("ring_tone", null);
        if (localObject != null) {
            try
            {
                localObject = (SoundBean)JSON.parseObject((String)localObject, SoundBean.class);
                return (SoundBean)localObject;
            }
            catch (Exception localException)
            {
                for (;;)
                {
                    localSoundBean = getDefaultRingTone();
                    setRingTone(localSoundBean);
                }
            }
        }
        SoundBean localSoundBean = getDefaultRingTone();
        setRingTone(localSoundBean);
        return localSoundBean;
    }

    public boolean getSelfDestruct()
    {
        return this.spUtils.getBoolean("key_self_distruct", false);
    }

    public boolean getSoundEnable()
    {
        return this.spUtils.getBoolean("sound_enable", true);
    }

    public boolean getTestMode()
    {
        return this.spUtils.getBoolean("key_test_mode", false);
    }

    public long getVersionCheckData()
    {
        return this.spUtils.getLong("version_check_date", 0L);
    }

    public boolean getVibrateEnable()
    {
        return this.spUtils.getBoolean("vibrate_enable", true);
    }

    public boolean getVoipOpen()
    {
        return this.spUtils.getBoolean("voip_open", false);
    }

    public int getWriteLogIndex()
    {
        return this.spUtils.getInt("write_log_index", 1);
    }

    public void setActionBraceletOn()
    {
        this.spUtils.put("action_bracelet", true);
        ChannelUtil.action_bracelet = true;
        this.spUtils.put("action_tf", false);
        ChannelUtil.action_TF = false;
    }

    public void setActionTFOn()
    {
        this.spUtils.put("action_tf", true);
        ChannelUtil.action_TF = true;
        this.spUtils.put("action_bracelet", false);
        ChannelUtil.action_bracelet = false;
    }

    public void setApplockGesture(String paramString)
    {
        if (!paramString.equals("")) {
            try
            {
                paramString = EngineUtils.getInstance().cryptCms(UserSpUtil.getInstanse().getPhone(), UserSpUtil.getInstanse().getPhone(), paramString);
                paramString = CryptUtils.nativeEncrypt(MjtApplication.getInstance(), paramString);
                FileIOUtils.writeFileFromString(new File(MjtApplication.getInstance().mGesturePassOldName), paramString);
                EngineUtils.getInstance().setEngineStringAttribute(2, MjtApplication.getInstance().getLoginUser().getTelephone());
                EngineUtils.getInstance().signFile(MjtApplication.getInstance().mGesturePassOldName, 0, MjtApplication.getInstance().mGesturePassNewName);
                return;
            }
            catch (Exception paramString)
            {
                LogFinalUtils.logForException(paramString);
                return;
            }
        }
        FileUtils.deleteFile(new File(MjtApplication.getInstance().mGesturePassOldName));
        FileUtils.deleteFile(new File(MjtApplication.getInstance().mGesturePassNewName));
    }

    public void setApplockGestureEnable(boolean paramBoolean)
    {
        this.spUtils.put("applock_gesture_enable", paramBoolean);
    }

    public void setBackgroundTime(long paramLong)
    {
        this.spUtils.put("key_background_time", paramLong);
    }

    public void setBoundBracelet(boolean paramBoolean)
    {
        this.spUtils.put("bound_bracelet", paramBoolean);
    }

    public void setBoundBraceletId(String paramString)
    {
        this.spUtils.put("bound_bracelet_id", paramString);
    }

    public void setChatMessageFireDate(long paramLong)
    {
        this.spUtils.put("chatmessage_fire_delete", paramLong);
    }

    public void setClearDatas(boolean paramBoolean)
    {
        this.spUtils.put("clear_datas_413", paramBoolean);
    }

    public void setDeviceId(String paramString)
    {
        this.spUtils.put("device_id", paramString);
    }

    public void setFirstStart(boolean paramBoolean)
    {
        this.spUtils.put("first_start", paramBoolean);
    }

    public void setGestureErrorCount(int paramInt)
    {
        this.spUtils.put("gesture_error_count", paramInt);
    }

    public void setGoogleToken(String paramString)
    {
        this.spUtils.put("key_google_token", paramString);
    }

    public void setInitContacts(boolean paramBoolean)
    {
        this.spUtils.put("init_contacts", paramBoolean);
    }

    public void setInitRoom(boolean paramBoolean)
    {
        this.spUtils.put("init_room", paramBoolean);
    }

    public void setKeepLive(boolean paramBoolean)
    {
        this.spUtils.put("show_keeplive", paramBoolean);
    }

    public void setMessageStatusReached(boolean paramBoolean)
    {
        this.spUtils.put("message_status_reached", paramBoolean);
    }

    public void setMessageStatusRead(boolean paramBoolean)
    {
        this.spUtils.put("message_status_read", paramBoolean);
    }

    public void setMessageStatusSent(boolean paramBoolean)
    {
        this.spUtils.put("message_status_sent", paramBoolean);
    }

    public void setNotificationSound(SoundBean paramSoundBean)
    {
        this.spUtils.put("notificaiton_sound", JSON.toJSONString(paramSoundBean));
    }

    public void setOtherApplockGesture(String paramString)
    {
        if (!paramString.equals("")) {
            try
            {
                paramString = EngineUtils.getInstance().cryptCms(UserSpUtil.getInstanse().getPhone(), UserSpUtil.getInstanse().getPhone(), paramString);
                paramString = CryptUtils.nativeEncrypt(MjtApplication.getInstance(), paramString);
                FileIOUtils.writeFileFromString(new File(MjtApplication.getInstance().mOtherGesturePassOldName), paramString);
                EngineUtils.getInstance().setEngineStringAttribute(2, MjtApplication.getInstance().getLoginUser().getTelephone());
                EngineUtils.getInstance().signFile(MjtApplication.getInstance().mOtherGesturePassOldName, 0, MjtApplication.getInstance().mOtherGesturePassNewName);
                return;
            }
            catch (Exception paramString)
            {
                LogFinalUtils.logForException(paramString);
                return;
            }
        }
        FileUtils.deleteFile(new File(MjtApplication.getInstance().mOtherGesturePassOldName));
        FileUtils.deleteFile(new File(MjtApplication.getInstance().mOtherGesturePassNewName));
    }

    public void setRingTone(SoundBean paramSoundBean)
    {
        this.spUtils.put("ring_tone", JSON.toJSONString(paramSoundBean));
    }

    public void setSelfDestruct(boolean paramBoolean)
    {
        this.spUtils.put("key_self_distruct", paramBoolean);
    }

    public void setSoundEnable(boolean paramBoolean)
    {
        this.spUtils.put("sound_enable", paramBoolean);
    }

    public void setTestMode(boolean paramBoolean)
    {
        this.spUtils.put("key_test_mode", paramBoolean);
    }

    public void setVersionCheckDate(long paramLong)
    {
        this.spUtils.put("version_check_date", paramLong);
    }

    public void setVibrateEnable(boolean paramBoolean)
    {
        this.spUtils.put("vibrate_enable", paramBoolean);
    }

    public void setVoipOpen(boolean paramBoolean)
    {
        this.spUtils.put("voip_open", paramBoolean);
    }

    public void setWriteLogIndex(int paramInt)
    {
        this.spUtils.put("write_log_index", paramInt);
    }
}
