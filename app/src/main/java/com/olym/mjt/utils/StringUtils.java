package com.olym.mjt.utils;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils
{
    static Pattern companyNamePat = Pattern.compile("^[���-���_a-zA-Z0-9_]{3,50}$");
    private static final Pattern email_pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    static Pattern nickNamePat;
    static Pattern phoneNumberPat = Pattern.compile("^((13[0-9])|(147)|(15[0-3,5-9])|(17[0,6-8])|(18[0-9]))\\d{8}$");
    static Pattern searchNickNamePat;

    static
    {
        nickNamePat = Pattern.compile("^[���-���_a-zA-Z0-9_]{2,15}$");
        searchNickNamePat = Pattern.compile("^[���-���_a-zA-Z0-9_]*$");
    }

    public static CharSequence editTextHtmlErrorTip(Context paramContext, int paramInt)
    {
        return Html.fromHtml("<font color='red'>" + paramContext.getString(paramInt) + "</font>");
    }

    public static CharSequence editTextHtmlErrorTip(Context paramContext, String paramString)
    {
        return Html.fromHtml("<font color='red'>" + paramString + "</font>");
    }

    public static boolean isCompanyName(String paramString)
    {
        if (TextUtils.isEmpty(paramString)) {
            return false;
        }
        return companyNamePat.matcher(paramString).matches();
    }

    public static boolean isEmail(String paramString)
    {
        if ((paramString == null) || (paramString.trim().length() == 0)) {
            return false;
        }
        return email_pattern.matcher(paramString).matches();
    }

    public static boolean isMobileNumber(String paramString)
    {
        return phoneNumberPat.matcher(paramString).matches();
    }

    public static boolean isNickName(String paramString)
    {
        if (TextUtils.isEmpty(paramString)) {
            return false;
        }
        return nickNamePat.matcher(paramString).matches();
    }

    public static boolean isSearchNickName(String paramString)
    {
        if (paramString == null) {
            return false;
        }
        return searchNickNamePat.matcher(paramString).matches();
    }

    public static String replaceSpecialChar(String paramString)
    {
        if ((paramString != null) && (paramString.length() > 0)) {
            return paramString.replaceAll("&#39;", "���").replaceAll("&#039;", "���").replaceAll("&nbsp;", " ").replaceAll("\r\n", "\n").replaceAll("\n", "\r\n");
        }
        return "";
    }

    public static boolean strEquals(String paramString1, String paramString2)
    {
        if (paramString1 == paramString2) {}
        label62:
        label65:
        for (;;)
        {
            return true;
            int i;
            if ((paramString1 == null) || (paramString1.trim().length() == 0))
            {
                i = 1;
                if ((paramString2 != null) && (paramString2.trim().length() != 0)) {
                    break label62;
                }
            }
            for (int j = 1;; j = 0)
            {
                if ((i != 0) && (j != 0)) {
                    break label65;
                }
                if (paramString1 == null) {
                    break label67;
                }
                return paramString1.equals(paramString2);
                i = 0;
                break;
            }
        }
        label67:
        if (paramString2 != null) {
            return paramString2.equals(paramString1);
        }
        return false;
    }
}
