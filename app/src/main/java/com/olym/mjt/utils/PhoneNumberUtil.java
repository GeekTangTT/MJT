package com.olym.mjt.utils;

import android.telephony.PhoneNumberUtils;

public class PhoneNumberUtil
{
    public static String parse(String paramString)
    {
        String str = PhoneNumberUtils.stripSeparators(paramString);
        if (str.startsWith("+86")) {
            paramString = str.substring(3, str.length());
        }
        do
        {
            return paramString;
            if (str.startsWith("0086")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+852")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("0852")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+853")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("0853")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+886")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("0886")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+1")) {
                return str.substring(2, str.length());
            }
            if (str.startsWith("0001")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+44")) {
                return str.substring(3, str.length());
            }
            if (str.startsWith("0044")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+33")) {
                return str.substring(3, str.length());
            }
            if (str.startsWith("0033")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+49")) {
                return str.substring(3, str.length());
            }
            if (str.startsWith("0049")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+65")) {
                return str.substring(3, str.length());
            }
            if (str.startsWith("0065")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+66")) {
                return str.substring(3, str.length());
            }
            if (str.startsWith("0066")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+60")) {
                return str.substring(3, str.length());
            }
            if (str.startsWith("0060")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+62")) {
                return str.substring(3, str.length());
            }
            if (str.startsWith("0062")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+81")) {
                return str.substring(3, str.length());
            }
            if (str.startsWith("0081")) {
                return str.substring(4, str.length());
            }
            if (str.startsWith("+82")) {
                return str.substring(3, str.length());
            }
            paramString = str;
        } while (!str.startsWith("0082"));
        return str.substring(4, str.length());
    }
}
