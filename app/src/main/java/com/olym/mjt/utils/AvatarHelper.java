package com.olym.mjt.utils;

import android.text.TextUtils;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.network.client.ApiConstants;
import com.olym.mjt.utils.sharedpreferencesutils.AvatarSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;

public class AvatarHelper
{
    public static String checkDomain(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "null";
        }
        if (!TextUtils.isEmpty(str))
        {
            paramString = str;
            if (!str.equals("null")) {}
        }
        else
        {
            paramString = UserSpUtil.getInstanse().getIBCDomain();
        }
        return paramString;
    }

    public static String getAvatarUrl(String paramString1, String paramString2, boolean paramBoolean)
    {
        Object localObject2 = null;
        if (TextUtils.isEmpty(paramString2)) {
            localObject1 = "";
        }
        int i;
        do
        {
            do
            {
                do
                {
                    do
                    {
                        return (String)localObject1;
                        if (!paramString2.equals(UserSpUtil.getInstanse().getIBCDomain())) {
                            return "";
                        }
                        localObject1 = localObject2;
                    } while (TextUtils.isEmpty(paramString1));
                    i = -1;
                    try
                    {
                        int j = Integer.parseInt(paramString1.trim());
                        i = j;
                    }
                    catch (NumberFormatException localNumberFormatException)
                    {
                        for (;;)
                        {
                            LogFinalUtils.logForException(localNumberFormatException);
                            localNumberFormatException.printStackTrace();
                        }
                    }
                    localObject1 = localObject2;
                } while (i == -1);
                localObject1 = localObject2;
            } while (i == 0);
            localObject1 = AvatarSpUtil.getInstanse().getAvatar(paramString1, paramString2);
        } while ((localObject1 != null) && (!paramBoolean));
        Object localObject1 = ApiConstants.USER_HEAD_THUMB + "/" + i % 10000 + "/" + paramString1 + ".jpg?" + System.currentTimeMillis();
        AvatarSpUtil.getInstanse().setAvatar(paramString1, paramString2, (String)localObject1);
        return (String)localObject1;
    }

    public static String getAvatarUrl(String paramString, boolean paramBoolean)
    {
        if (TextUtils.isEmpty(paramString)) {}
        int i;
        do
        {
            return null;
            i = -1;
            try
            {
                int j = Integer.parseInt(paramString.trim());
                i = j;
            }
            catch (NumberFormatException localNumberFormatException)
            {
                for (;;)
                {
                    LogFinalUtils.logForException(localNumberFormatException);
                    localNumberFormatException.printStackTrace();
                }
            }
        } while ((i == -1) || (i == 0));
        i %= 10000;
        if (paramBoolean) {
            return ApiConstants.USER_HEAD_THUMB + "/" + i + "/" + paramString + ".jpg";
        }
        return ApiConstants.USER_HEAD_ORIGINAL + "/" + i + "/" + paramString + ".jpg";
    }
}

