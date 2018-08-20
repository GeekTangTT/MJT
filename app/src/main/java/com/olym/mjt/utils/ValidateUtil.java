package com.olym.mjt.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil
{
    public static boolean isExChar(String paramString)
    {
        return Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~���@#���%������&*������������+|{}������������������������������������]").matcher(paramString).find();
    }

    public static boolean isGroupbanEditChar(String paramString)
    {
        return Pattern.compile("[`~''~]").matcher(paramString).find();
    }

    public static boolean isIP(String paramString)
    {
        if ((paramString.length() < 7) || (paramString.length() > 15) || ("".equals(paramString))) {
            return false;
        }
        return Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}").matcher(paramString).find();
    }

    public static boolean validateCode(String paramString)
    {
        return (!paramString.equals("")) && (paramString.length() == 6);
    }

    public static boolean validateNickname(String paramString)
    {
        return !paramString.equals("");
    }

    public static boolean validatePhone(String paramString)
    {
        return true;
    }

    public static boolean validatePwd(String paramString)
    {
        return paramString.length() >= 6;
    }

    public static boolean validateRemarkname(String paramString)
    {
        return !paramString.equals("");
    }
}
