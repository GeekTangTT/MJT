package com.olym.mjt.utils;

import android.text.TextUtils;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.network.responsedata.XmppLoginResponseBean;
import com.olym.mjt.network.responsedata.XmppLoginResponseBean.LoginData;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;

public class LoginHelper
{
    public static boolean prepareUser()
    {
        String str1 = UserSpUtil.getInstanse().getUserid();
        String str2 = UserSpUtil.getInstanse().getPhone();
        return (!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2));
    }

    public static boolean setLoginUser(String paramString1, String paramString2, XmppLoginResponseBean paramXmppLoginResponseBean)
    {
        paramXmppLoginResponseBean = paramXmppLoginResponseBean.getData();
        User localUser = new User();
        localUser.setTelephone(paramString1);
        localUser.setPassword(paramString2);
        localUser.setUserId(paramXmppLoginResponseBean.getUserId());
        if (TextUtils.isEmpty(paramXmppLoginResponseBean.getNickname())) {
            localUser.setNickName(paramString1);
        }
        for (;;)
        {
            MjtApplication.getInstance().setLoginUser(localUser);
            UserSpUtil.getInstanse().setUserid(paramXmppLoginResponseBean.getUserId());
            UserSpUtil.getInstanse().setLoginUser(localUser);
            UserSpUtil.getInstanse().setPhone(paramString1);
            FileSpUtils.getInstanse().setPhone(paramString1);
            return true;
            localUser.setNickName(paramXmppLoginResponseBean.getNickname());
        }
    }
}
