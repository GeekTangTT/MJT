package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class ExceptionLogoutEvent
{
    public static final String KEY_ACCOUNT_DISABLED = "key_account_disabled";
    public static final String KEY_KEY_DELETE = "key_key_delete";
    public static final String KEY_LOGIN_ERROR = "key_login_error";
    public static final String KEY_SELF_DESTRUCT = "key_self_destruct";
    public static final String KEY_TFCARD_ERROR = "key_tfcard_error";
    public static final String KEY_TOKEN_NULL = "key_token_null";
    private String key;

    public ExceptionLogoutEvent(String paramString)
    {
        this.key = paramString;
    }

    public static void post(ExceptionLogoutEvent paramExceptionLogoutEvent)
    {
        EventBusUtil.post(paramExceptionLogoutEvent);
    }

    public String getKey()
    {
        return this.key;
    }
}

