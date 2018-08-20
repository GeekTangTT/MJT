package com.olym.mjt.utils.sharedpreferencesutils;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.databean.bean.ServiceInfo;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.ExceptionLogoutEvent;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.FileUtils;
import com.olym.mjt.utils.cipher.CryptUtils;
import java.io.File;

public class UserSpUtil
{
    private static final String KEY_ACCESSTOKEN = "accesstoken";
    private static final String KEY_ANDROIDID = "android_id";
    private static final String KEY_BIND_STATUS = "bind_status";
    private static final String KEY_BLUETOOTH = "bluetooth";
    private static final String KEY_CALL_MISSEDE_NUM = "call_missed_num";
    private static final String KEY_COUNTRY_CODE = "country_code";
    private static final String KEY_DISPLAYVERSION = "display_version";
    private static final String KEY_DOMAIN = "domain";
    private static final String KEY_DisplayMetrics = "display_metrics";
    private static final String KEY_FILE_REANDOM_NUMBER = "file_random_number";
    private static final String KEY_FIRE_CHAT_IMAGE_PRE = "chat_fire_image_pre";
    private static final String KEY_FIRE_CHAT_PRE = "chat_fire_pre";
    private static final String KEY_IBC_DOMAIN = "ibc_domain";
    private static final String KEY_IP = "ip";
    private static final String KEY_IP_CHECK_DATE = "ip_check_date";
    private static final String KEY_LAST_COMPANY = "key_last_company";
    private static final String KEY_LOGIN_USER = "login_user";
    private static final String KEY_MACADDRESS = "macaddress";
    private static final String KEY_NEW_LOGIN = "new_login";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PJSIP_PORT = "pjsip_port";
    private static final String KEY_PJSUA_ID = "pjsua_id";
    private static final String KEY_PORT = "port";
    private static final String KEY_RANDOM_NUMBER = "random_number";
    private static final String KEY_SERVICE_INFO = "service_info";
    private static final String KEY_SERVICE_INFO_DATE = "service_infp_date";
    private static final String KEY_URL = "url";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_VIP = "vip";
    private static final String KEY_XMPP_PORT = "xmpp_port";
    private static final String NAME = "user";
    private static volatile UserSpUtil instanse;
    private SPUtils spUtils = new SPUtils("user");

    public static UserSpUtil getInstanse()
    {
        if (instanse == null) {}
        try
        {
            if (instanse == null) {
                instanse = new UserSpUtil();
            }
            return instanse;
        }
        finally {}
    }

    public void clearKey()
    {
        this.spUtils.clear();
    }

    public void deletePriKey()
    {
        String str2 = MjtApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath();
        String str1 = str2;
        if (str2 != null) {
            str1 = str2.substring(0, str2.lastIndexOf("/"));
        }
        FileUtils.deleteDir(str1 + "/PrivateFiles/");
        FileUtils.deleteDir(str1 + "/CommonFiles/");
    }

    public String getAccessToken()
    {
        String str = this.spUtils.getString("accesstoken");
        if (str != null) {
            return EngineUtils.getInstance().decryptCms(getPhone(), str);
        }
        return "";
    }

    public String getAndroidId()
    {
        return this.spUtils.getString("android_id", "");
    }

    public String getBluetooth()
    {
        return this.spUtils.getString("bluetooth", "");
    }

    public int getCallMissedNum()
    {
        return this.spUtils.getInt("call_missed_num", 0);
    }

    public boolean getChatImagePre()
    {
        return this.spUtils.getBoolean("chat_fire_image_pre");
    }

    public boolean getChatPre()
    {
        return this.spUtils.getBoolean("chat_fire_pre");
    }

    public String getDisplayMetrics()
    {
        return this.spUtils.getString("display_metrics", "");
    }

    public String getIBCDomain()
    {
        return this.spUtils.getString("ibc_domain", "olym");
    }

    public long getIpCheckDate()
    {
        return this.spUtils.getLong("ip_check_date", 0L);
    }

    public int getKeyBindStatus()
    {
        return this.spUtils.getInt("bind_status", 0);
    }

    public String getKeyCountryCode()
    {
        return this.spUtils.getString("country_code", "+86");
    }

    public String getKeyDisplayversion()
    {
        return this.spUtils.getString("display_version", "");
    }

    public int getKeyPjsipPort()
    {
        return this.spUtils.getInt("pjsip_port", 11533);
    }

    public int getKeyXmppPort()
    {
        return this.spUtils.getInt("xmpp_port", 5222);
    }

    public int getLastCompany()
    {
        return this.spUtils.getInt("key_last_company", 0);
    }

    public User getLoginUser()
    {
        Object localObject = this.spUtils.getString("login_user", null);
        User localUser = null;
        Applog.info_importance("-----getLoginUser----- " + (String)localObject);
        if (localObject != null) {}
        try
        {
            localUser = (User)JSON.parseObject((String)localObject, User.class);
            localObject = localUser;
            if (localUser == null)
            {
                localObject = new User();
                ((User)localObject).setUserId("1");
            }
            return (User)localObject;
        }
        catch (Exception localException)
        {
            for (;;)
            {
                localUser = new User();
                localUser.setUserId("1");
                ExceptionLogoutEvent.post(new ExceptionLogoutEvent("key_login_error"));
                LogFinalUtils.logForException(localException);
            }
        }
    }

    public String getMacAddress()
    {
        return this.spUtils.getString("macaddress", "");
    }

    public boolean getNewLogin()
    {
        return this.spUtils.getBoolean("new_login", false);
    }

    public String getNickName()
    {
        return this.spUtils.getString("nickname", "");
    }

    public String getPassword()
    {
        String str = this.spUtils.getString("password");
        if (str != null) {
            return EngineUtils.getInstance().decryptCms(getPhone(), str);
        }
        return "";
    }

    public String getPhone()
    {
        return this.spUtils.getString("phone", "");
    }

    public int getPjsuaId()
    {
        return this.spUtils.getInt("pjsua_id", -1);
    }

    public String getRandomNumber()
    {
        String str1 = this.spUtils.getString("random_number");
        if (str1 != null) {
            try
            {
                str1 = CryptUtils.decryptPass(MjtApplication.getInstance(), str1, "");
                return str1;
            }
            catch (Exception localException)
            {
                for (;;)
                {
                    LogFinalUtils.logForException(localException);
                    String str2 = "";
                }
            }
        }
        return "";
    }

    public ServiceInfo getServiceInfo()
    {
        String str = this.spUtils.getString("service_info", null);
        if (str == null) {
            return null;
        }
        return (ServiceInfo)JSON.parseObject(str, ServiceInfo.class);
    }

    public long getServiceInfoDate()
    {
        return this.spUtils.getLong("service_infp_date", 0L);
    }

    public String getUserDomain()
    {
        return this.spUtils.getString("domain", "olym");
    }

    public String getUserIP()
    {
        return this.spUtils.getString("ip", "");
    }

    public String getUserPort()
    {
        return this.spUtils.getString("port");
    }

    public String getUserUrl()
    {
        return this.spUtils.getString("url");
    }

    public String getUserid()
    {
        return this.spUtils.getString("user_id", "");
    }

    public int getVip()
    {
        return this.spUtils.getInt("vip", 0);
    }

    public void setAccessToken(String paramString)
    {
        this.spUtils.put("accesstoken", paramString);
    }

    public void setAndroidId(String paramString)
    {
        this.spUtils.put("android_id", paramString);
    }

    public void setBluetooth(String paramString)
    {
        this.spUtils.put("bluetooth", paramString);
    }

    public void setCallMissedNumAdd()
    {
        int i = this.spUtils.getInt("call_missed_num", 0);
        this.spUtils.put("call_missed_num", i + 1);
    }

    public void setCallMissedNumClear()
    {
        this.spUtils.put("call_missed_num", 0);
    }

    public void setChatImagePre(boolean paramBoolean)
    {
        this.spUtils.put("chat_fire_image_pre", paramBoolean);
    }

    public void setChatPre(boolean paramBoolean)
    {
        this.spUtils.put("chat_fire_pre", paramBoolean);
    }

    public void setDisplayMetrics(String paramString)
    {
        this.spUtils.put("display_metrics", paramString);
    }

    public void setIBCDomain(String paramString)
    {
        this.spUtils.put("ibc_domain", paramString);
    }

    public void setIpCheckDate(long paramLong)
    {
        this.spUtils.put("ip_check_date", paramLong);
    }

    public void setKeyBindStatus(int paramInt)
    {
        this.spUtils.put("bind_status", paramInt);
    }

    public void setKeyCountryCode(String paramString)
    {
        this.spUtils.put("country_code", paramString);
    }

    public void setKeyDisplayversion(String paramString)
    {
        this.spUtils.put("display_version", paramString);
    }

    public void setKeyPjsipPort(int paramInt)
    {
        this.spUtils.put("pjsip_port", paramInt);
    }

    public void setKeyXmppPort(int paramInt)
    {
        this.spUtils.put("xmpp_port", paramInt);
    }

    public void setLastCompany(int paramInt)
    {
        this.spUtils.put("key_last_company", paramInt);
    }

    public void setLoginUser(User paramUser)
    {
        User localUser = getLoginUser();
        if ((localUser != null) && (!localUser.isErrorUser())) {
            paramUser.setPassword(localUser.getPassword());
        }
        this.spUtils.put("login_user", JSON.toJSONString(paramUser));
    }

    public void setMacAddress(String paramString)
    {
        this.spUtils.put("macaddress", paramString);
    }

    public void setNewLogin(boolean paramBoolean)
    {
        this.spUtils.put("new_login", paramBoolean);
    }

    public void setNickName(String paramString)
    {
        this.spUtils.put("nickname", paramString);
    }

    public void setPassword(String paramString)
    {
        this.spUtils.put("password", paramString);
    }

    public void setPhone(String paramString)
    {
        this.spUtils.put("phone", paramString);
    }

    public void setPjsuaId(int paramInt)
    {
        this.spUtils.put("pjsua_id", paramInt);
    }

    public void setRandomNumber(String paramString)
    {
        this.spUtils.put("random_number", paramString);
    }

    public void setServiceInfo(ServiceInfo paramServiceInfo)
    {
        this.spUtils.put("service_info", JSON.toJSONString(paramServiceInfo));
    }

    public void setServiceInfoDate(long paramLong)
    {
        this.spUtils.put("service_infp_date", paramLong);
    }

    public void setUserDomian(String paramString)
    {
        this.spUtils.put("domain", paramString);
    }

    public void setUserIP(String paramString)
    {
        this.spUtils.put("ip", paramString);
    }

    public void setUserPort(String paramString)
    {
        this.spUtils.put("port", paramString);
    }

    public void setUserUrl(String paramString)
    {
        this.spUtils.put("url", paramString);
    }

    public void setUserid(String paramString)
    {
        this.spUtils.put("user_id", paramString);
    }

    public void setVip(int paramInt)
    {
        this.spUtils.put("vip", paramInt);
    }
}
