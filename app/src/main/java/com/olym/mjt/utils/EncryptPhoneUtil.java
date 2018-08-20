package com.olym.mjt.utils;

import android.content.ContentResolver;
import android.provider.Settings.System;
import com.olym.mjt.module.MjtApplication;

public class EncryptPhoneUtil
{
    public static boolean getPCMMode()
    {
        return Settings.System.getInt(MjtApplication.getInstance().getContentResolver(), "security_mode_enabled", 0) == 1;
    }

    public static void setPCMMode(boolean paramBoolean)
    {
        ContentResolver localContentResolver = MjtApplication.getInstance().getContentResolver();
        if (paramBoolean) {}
        for (int i = 1;; i = 0)
        {
            Settings.System.putInt(localContentResolver, "security_mode_enabled", i);
            return;
        }
    }

    public static void setSipMode(boolean paramBoolean)
    {
        ContentResolver localContentResolver = MjtApplication.getInstance().getContentResolver();
        if (paramBoolean) {}
        for (int i = 1;; i = 0)
        {
            Settings.System.putInt(localContentResolver, "sip_audio_call_enabled", i);
            return;
        }
    }
}
