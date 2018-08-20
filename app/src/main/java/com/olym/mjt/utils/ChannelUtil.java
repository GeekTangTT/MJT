package com.olym.mjt.utils;

import android.content.res.Resources;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.widget.dialog.AreaCode;
import java.util.ArrayList;

public class ChannelUtil
{
    public static final int CHANNEL_ETH = 107;
    public static final int CHANNEL_GKW = 103;
    public static final int CHANNEL_HBSLT = 110;
    public static final int CHANNEL_JMT = 105;
    public static final int CHANNEL_MJT = 100;
    public static final int CHANNEL_QH1H = 106;
    public static final int CHANNEL_TYT = 109;
    public static final int CHANNEL_XGT = 101;
    public static final int CHANNEL_XJT = 108;
    public static final int CHANNEL_XYT = 102;
    public static final int CHANNEL_ZCAH = 104;
    public static final String MJTCA = "mjtca.pem";
    public static final String MJTENGINECA = "rootbase.cer";
    public static boolean action_TF;
    public static boolean action_area_code;
    public static boolean action_bracelet;
    public static boolean action_gesture;
    public static boolean action_gesture_tips;
    public static boolean action_mjt_ca;
    public static boolean action_otheruser;
    public static boolean action_show_guide;
    public static boolean action_sms;
    public static int action_upload_event;
    public static boolean action_upload_log;
    public static ArrayList<AreaCode> areaCodes = new ArrayList();
    public static AreaCode currentArea;
    public static int currentChannel;
    public static String domain;
    public static int encryMode;
    public static int fire;
    public static boolean hascolleague;
    public static int hashMode;
    public static int icon_about = 2131165580;
    public static int icon_call_logo;
    public static int icon_login = 2131165659;
    public static int icon_logo;
    public static int icon_notify = 2131165845;
    public static boolean isLoadWebView;
    public static boolean isSavaCallLog;
    public static boolean isShowNotify;
    public static boolean isUsedTEE;
    public static int showtimegap = 15;
    public static String update_url;

    static
    {
        icon_logo = 2131165822;
        icon_call_logo = 2131165596;
        domain = "";
        update_url = "https://dl.myibc.net/d/t/";
        currentChannel = 100;
        action_gesture = false;
        action_upload_log = true;
        action_gesture_tips = false;
        action_upload_event = 1;
        action_area_code = false;
        action_show_guide = false;
        action_sms = false;
        action_TF = false;
        action_bracelet = false;
        action_otheruser = false;
        action_mjt_ca = false;
        isUsedTEE = false;
        isSavaCallLog = true;
        isShowNotify = true;
        isLoadWebView = true;
        fire = 0;
        encryMode = 0;
        hashMode = 0;
        hascolleague = false;
    }

    public static void initAreaCodes()
    {
        switch (currentChannel)
        {
        }
        for (;;)
        {
            if (currentArea == null) {
                currentArea = (AreaCode)areaCodes.get(0);
            }
            return;
            areaCodes.clear();
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689668), "86"));
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689473), "852"));
            continue;
            areaCodes.clear();
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689668), "86"));
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689473), "852"));
            continue;
            areaCodes.clear();
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689668), "86"));
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689473), "852"));
            continue;
            areaCodes.clear();
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689668), "86"));
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689473), "852"));
            continue;
            areaCodes.clear();
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689668), "86"));
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689473), "852"));
            continue;
            areaCodes.clear();
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689668), "86"));
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689473), "852"));
            continue;
            areaCodes.clear();
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689668), "86"));
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689473), "852"));
            continue;
            areaCodes.clear();
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689668), "86"));
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689473), "852"));
            continue;
            areaCodes.clear();
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689473), "852"));
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689668), "86"));
            continue;
            areaCodes.clear();
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689668), "86"));
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689473), "852"));
            continue;
            areaCodes.clear();
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689668), "86"));
            areaCodes.add(new AreaCode(MjtApplication.getInstance().getResources().getString(2131689473), "852"));
        }
    }

    public static void setChannel(int paramInt)
    {
        switch (paramInt)
        {
            default:
                return;
            case 100:
                icon_login = 2131165659;
                icon_notify = 2131165845;
                icon_about = 2131165580;
                icon_logo = 2131165822;
                icon_call_logo = 2131165596;
                currentChannel = 100;
                action_gesture = true;
                action_upload_log = true;
                action_sms = false;
                action_upload_event = 4;
                action_gesture_tips = false;
                action_area_code = true;
                action_show_guide = true;
                action_mjt_ca = true;
                return;
            case 101:
                icon_login = 2131165663;
                icon_notify = 2131165843;
                icon_about = 2131165582;
                icon_logo = 2131165824;
                icon_call_logo = 2131165596;
                currentChannel = 101;
                action_upload_event = 4;
                action_sms = false;
                action_mjt_ca = true;
                return;
            case 102:
                icon_login = 2131165664;
                icon_notify = 2131165849;
                icon_about = 2131165583;
                icon_logo = 2131165825;
                icon_call_logo = 2131165601;
                domain = "lsd4";
                action_gesture = true;
                action_sms = false;
                action_upload_log = false;
                currentChannel = 102;
                action_upload_event = 1;
                action_gesture_tips = true;
                action_area_code = true;
                action_mjt_ca = true;
                hascolleague = true;
                isSavaCallLog = false;
                return;
            case 103:
                icon_login = 2131165660;
                icon_notify = 2131165846;
                icon_about = 2131165578;
                icon_logo = 2131165819;
                icon_call_logo = 2131165596;
                currentChannel = 103;
                action_upload_event = 4;
                action_sms = false;
                action_mjt_ca = true;
                return;
            case 104:
                icon_login = 2131165665;
                icon_notify = 2131165850;
                icon_about = 2131165584;
                icon_logo = 2131165826;
                icon_call_logo = 2131165596;
                currentChannel = 104;
                action_upload_event = 4;
                action_sms = false;
                action_mjt_ca = true;
                return;
            case 105:
                icon_login = 2131165661;
                icon_notify = 2131165847;
                icon_about = 2131165579;
                icon_logo = 2131165820;
                icon_call_logo = 2131165598;
                action_gesture = true;
                action_upload_log = true;
                currentChannel = 105;
                domain = "zsyjnxa";
                update_url = "https://mces.cnpc.com.cn/zsy/d/t";
                action_upload_event = 1;
                action_gesture_tips = true;
                action_area_code = false;
                action_sms = true;
                action_TF = true;
                action_bracelet = true;
                action_mjt_ca = true;
                return;
            case 106:
                icon_login = 2131165659;
                icon_notify = 2131165845;
                icon_about = 2131165580;
                icon_logo = 2131165821;
                icon_call_logo = 2131165599;
                currentChannel = 106;
                action_gesture = false;
                action_upload_log = false;
                action_upload_event = 1;
                action_gesture_tips = false;
                action_area_code = false;
                action_sms = false;
                encryMode = 1;
                hashMode = 2;
                domain = "hcitsec";
                action_mjt_ca = true;
                isUsedTEE = true;
                isLoadWebView = false;
                action_otheruser = true;
                isSavaCallLog = false;
                isShowNotify = false;
                return;
            case 107:
                icon_login = 2131165661;
                icon_notify = 2131165847;
                icon_about = 2131165579;
                icon_logo = 2131165820;
                icon_call_logo = 2131165598;
                currentChannel = 107;
                action_gesture = true;
                action_upload_log = true;
                action_sms = false;
                action_upload_event = 4;
                action_gesture_tips = false;
                action_area_code = true;
                action_mjt_ca = true;
                return;
            case 108:
                icon_login = 2131165659;
                icon_notify = 2131165845;
                icon_about = 2131165580;
                icon_logo = 2131165822;
                icon_call_logo = 2131165596;
                currentChannel = 108;
                action_gesture = true;
                action_upload_log = false;
                action_sms = false;
                action_upload_event = 4;
                action_gesture_tips = false;
                action_area_code = true;
                action_show_guide = false;
                action_mjt_ca = true;
                showtimegap = 5;
                domain = "xjt";
                return;
            case 109:
                icon_login = 2131165662;
                icon_notify = 2131165848;
                icon_about = 2131165581;
                icon_logo = 2131165823;
                icon_call_logo = 2131165600;
                currentChannel = 109;
                action_gesture = false;
                action_upload_log = false;
                action_upload_event = 1;
                action_gesture_tips = false;
                action_area_code = false;
                action_sms = false;
                encryMode = 1;
                hashMode = 2;
                domain = "qhyht";
                action_mjt_ca = true;
                isUsedTEE = true;
                return;
        }
        icon_login = 2131165659;
        icon_notify = 2131165845;
        icon_about = 2131165580;
        icon_logo = 2131165822;
        icon_call_logo = 2131165596;
        currentChannel = 110;
        domain = "slt";
        action_gesture = true;
        action_upload_log = true;
        action_sms = true;
        action_upload_event = 4;
        action_gesture_tips = false;
        action_area_code = false;
        action_show_guide = false;
        action_mjt_ca = true;
        action_otheruser = true;
        isShowNotify = true;
    }
}

