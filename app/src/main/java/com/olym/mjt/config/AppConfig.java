package com.olym.mjt.config;

import android.text.TextUtils;
import com.olym.mjt.network.client.ApiConstants;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;

public class AppConfig
{
    public static String CONFIG_IBC_SERVER_URL;
    public static String CONFIG_KEY_SERVER_URL;
    public static final String CONFIG_LOGIN = "nisc_voip";
    public static final String DEFUAL_DOMAIN = "olym";
    public static String DOWNLOAD_FILE_URL = "https://" + SERVER_IP + SERVER_PORT + "/dl";
    public static final String IBC_PORT = ":443";
    public static final String IBC_URL = "u.sm9.net.cn";
    public static String MY_URL;
    public static final int PJSIP_NOT_ENCRYPT_PORT = 11532;
    public static final int PJSIP_PORT = 11533;
    public static String SERVER_IP;
    public static String SERVER_PORT;
    public static String SERVER_URL = "";
    public static final String SIP_PASS_ALG = "SM9_SM3";
    public static final String SIP_PASS_TAG = "retr_sip_auth_key";
    public static String SIP_SERVER_URL;
    public static final String SIP_SUB_URL = "/api/sipact.php";
    public static final String SIP_SUB_URL_V2 = "/api/sipactV2.php";
    public static String UPLOAD_FILE_URL;
    public static final String XMPP_LOGIN_CONFIG = "nisc_voip";
    public static final int XMPP_PORT = 5222;

    static
    {
        SERVER_PORT = ":443";
        SIP_SERVER_URL = "https://" + SERVER_URL + SERVER_PORT;
        SERVER_IP = "";
        MY_URL = SERVER_IP + ":" + 11533;
        CONFIG_KEY_SERVER_URL = "https://" + SERVER_IP + SERVER_PORT;
        CONFIG_IBC_SERVER_URL = "https://" + SERVER_URL;
        UPLOAD_FILE_URL = "https://" + SERVER_IP + SERVER_PORT + "/ul";
    }

    public static void clear() {}

    public static boolean init()
    {
        String str1 = UserSpUtil.getInstanse().getUserIP();
        if (TextUtils.isEmpty(str1)) {
            return false;
        }
        String str2 = UserSpUtil.getInstanse().getUserUrl();
        String str3 = UserSpUtil.getInstanse().getUserPort();
        Applog.info("----AppConfig---init---- " + str2 + " " + str3);
        Applog.systemOut("----AppConfig---init---- " + str2 + " " + str3);
        SERVER_IP = str1;
        SERVER_URL = str2;
        SERVER_PORT = str3;
        SIP_SERVER_URL = "https://" + str2 + str3;
        MY_URL = str1 + ":" + UserSpUtil.getInstanse().getKeyPjsipPort();
        CONFIG_KEY_SERVER_URL = "https://" + SERVER_IP + str3;
        UPLOAD_FILE_URL = "https://" + SERVER_IP + str3 + "/ul";
        DOWNLOAD_FILE_URL = "https://" + SERVER_IP + str3 + "/dl";
        ApiConstants.init();
        return true;
    }
}
