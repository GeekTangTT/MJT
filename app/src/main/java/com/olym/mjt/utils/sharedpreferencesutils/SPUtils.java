package com.olym.mjt.utils.sharedpreferencesutils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.Nullable;
import com.olym.mjt.module.MjtApplication;
import java.util.Map;
import java.util.Set;

public class SPUtils
{
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    public SPUtils(String paramString)
    {
        this.sp = MjtApplication.getInstance().getSharedPreferences(paramString, 0);
        this.editor = this.sp.edit();
        this.editor.apply();
    }

    public void clear()
    {
        this.editor.clear().apply();
    }

    public boolean contains(String paramString)
    {
        return this.sp.contains(paramString);
    }

    public Map<String, ?> getAll()
    {
        return this.sp.getAll();
    }

    public boolean getBoolean(String paramString)
    {
        return getBoolean(paramString, false);
    }

    public boolean getBoolean(String paramString, boolean paramBoolean)
    {
        return this.sp.getBoolean(paramString, paramBoolean);
    }

    public float getFloat(String paramString)
    {
        return getFloat(paramString, -1.0F);
    }

    public float getFloat(String paramString, float paramFloat)
    {
        return this.sp.getFloat(paramString, paramFloat);
    }

    public int getInt(String paramString)
    {
        return getInt(paramString, -1);
    }

    public int getInt(String paramString, int paramInt)
    {
        return this.sp.getInt(paramString, paramInt);
    }

    public long getLong(String paramString)
    {
        return getLong(paramString, -1L);
    }

    public long getLong(String paramString, long paramLong)
    {
        return this.sp.getLong(paramString, paramLong);
    }

    public String getString(String paramString)
    {
        return getString(paramString, null);
    }

    public String getString(String paramString1, String paramString2)
    {
        return this.sp.getString(paramString1, paramString2);
    }

    public Set<String> getStringSet(String paramString)
    {
        return getStringSet(paramString, null);
    }

    public Set<String> getStringSet(String paramString, @Nullable Set<String> paramSet)
    {
        return this.sp.getStringSet(paramString, paramSet);
    }

    public void put(String paramString, float paramFloat)
    {
        this.editor.putFloat(paramString, paramFloat).apply();
    }

    public void put(String paramString, int paramInt)
    {
        this.editor.putInt(paramString, paramInt).apply();
    }

    public void put(String paramString, long paramLong)
    {
        this.editor.putLong(paramString, paramLong).apply();
    }

    public void put(String paramString1, @Nullable String paramString2)
    {
        this.editor.putString(paramString1, paramString2).apply();
    }

    public void put(String paramString, @Nullable Set<String> paramSet)
    {
        this.editor.putStringSet(paramString, paramSet).apply();
    }

    public void put(String paramString, boolean paramBoolean)
    {
        this.editor.putBoolean(paramString, paramBoolean).apply();
    }

    public void remove(String paramString)
    {
        this.editor.remove(paramString).apply();
    }
}

