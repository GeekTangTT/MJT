package com.olym.mjt.widget.applock;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.olym.mjt.module.applock.gesture.GestureActivity;
import com.olym.mjt.utils.ViewTransferUtil;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LockPatternUtils
{
    public static final int FAILED_ATTEMPTS_BEFORE_TIMEOUT = 5;
    public static final long FAILED_ATTEMPT_TIMEOUT_MS = 30000L;
    private static final String LOCK_PATTERN_FILE = "gesture.key";
    public static final int MIN_LOCK_PATTERN_SIZE = 4;
    public static final int MIN_PATTERN_REGISTER_FAIL = 4;

    public static boolean checkPattern(List<LockPatternView.Cell> paramList)
    {
        return patternToString(paramList).equals(AppSpUtil.getInstanse().getApplockGesture());
    }

    public static void clearLockPattern()
    {
        AppSpUtil.getInstanse().setApplockGesture("");
        AppSpUtil.getInstanse().setApplockGestureEnable(false);
        AppSpUtil.getInstanse().setGestureErrorCount(0);
    }

    public static void clearOtherLockPattern()
    {
        AppSpUtil.getInstanse().setOtherApplockGesture("");
        AppSpUtil.getInstanse().setApplockGesture("");
        AppSpUtil.getInstanse().setApplockGestureEnable(false);
        AppSpUtil.getInstanse().setGestureErrorCount(0);
    }

    public static void gotoCheckView(Activity paramActivity)
    {
        if (AppSpUtil.getInstanse().getApplockGestureenable())
        {
            Bundle localBundle = new Bundle();
            localBundle.putInt("type", 3);
            ViewTransferUtil.transferGestureActivity(paramActivity, localBundle);
        }
    }

    public static void gotoCheckViewForMainShare(Activity paramActivity, Intent paramIntent)
    {
        if (AppSpUtil.getInstanse().getApplockGestureenable())
        {
            Bundle localBundle = new Bundle();
            localBundle.putInt("extra_data", 1);
            localBundle.putInt("type", 3);
            paramIntent.setComponent(new ComponentName(paramActivity, GestureActivity.class));
            paramIntent.putExtras(localBundle);
            paramActivity.startActivity(paramIntent);
        }
    }

    public static void gotoCheckViewForShare(Activity paramActivity, Intent paramIntent)
    {
        if (AppSpUtil.getInstanse().getApplockGestureenable())
        {
            Bundle localBundle = new Bundle();
            localBundle.putInt("type", 3);
            paramIntent.setComponent(new ComponentName(paramActivity, GestureActivity.class));
            paramIntent.putExtras(localBundle);
            paramActivity.startActivity(paramIntent);
        }
    }

    public static void gotoCheckViewFromNoActivity(Context paramContext)
    {
        if (AppSpUtil.getInstanse().getApplockGestureenable())
        {
            Bundle localBundle = new Bundle();
            localBundle.putInt("type", 3);
            Intent localIntent = new Intent(paramContext, GestureActivity.class);
            localIntent.setFlags(805306368);
            localIntent.putExtras(localBundle);
            paramContext.startActivity(localIntent);
        }
    }

    public static void gotoCloseCheckView(Activity paramActivity)
    {
        if (AppSpUtil.getInstanse().getApplockGestureenable())
        {
            Bundle localBundle = new Bundle();
            localBundle.putInt("type", 5);
            ViewTransferUtil.transferGestureActivity(paramActivity, localBundle);
        }
    }

    public static void gotoMainCheckView(Activity paramActivity)
    {
        if (AppSpUtil.getInstanse().getApplockGestureenable())
        {
            Bundle localBundle = new Bundle();
            localBundle.putInt("extra_data", 1);
            localBundle.putInt("type", 3);
            ViewTransferUtil.transferGestureActivity(paramActivity, localBundle);
        }
    }

    public static void gotoMainSetView(Activity paramActivity)
    {
        Bundle localBundle = new Bundle();
        localBundle.putInt("extra_data", 1);
        localBundle.putInt("type", 1);
        ViewTransferUtil.transferGestureActivity(paramActivity, localBundle);
    }

    public static void gotoSetview(Activity paramActivity)
    {
        Bundle localBundle = new Bundle();
        localBundle.putInt("type", 1);
        ViewTransferUtil.transferGestureActivity(paramActivity, localBundle);
    }

    private static byte[] patternToHash(List<LockPatternView.Cell> paramList)
    {
        if (paramList == null) {
            return null;
        }
        int j = paramList.size();
        byte[] arrayOfByte = new byte[j];
        int i = 0;
        while (i < j)
        {
            LockPatternView.Cell localCell = (LockPatternView.Cell)paramList.get(i);
            arrayOfByte[i] = ((byte)(localCell.getRow() * 3 + localCell.getColumn()));
            i += 1;
        }
        try
        {
            paramList = MessageDigest.getInstance("SHA-1").digest(arrayOfByte);
            return paramList;
        }
        catch (NoSuchAlgorithmException paramList) {}
        return arrayOfByte;
    }

    public static String patternToString(List<LockPatternView.Cell> paramList)
    {
        if (paramList == null) {
            return "";
        }
        int j = paramList.size();
        StringBuilder localStringBuilder = new StringBuilder();
        int i = 0;
        while (i < j)
        {
            LockPatternView.Cell localCell = (LockPatternView.Cell)paramList.get(i);
            localStringBuilder.append(localCell.getRow() * 3 + localCell.getColumn());
            i += 1;
        }
        return localStringBuilder.toString();
    }

    public static void saveLockPattern(List<LockPatternView.Cell> paramList)
    {
        paramList = patternToString(paramList);
        AppSpUtil.getInstanse().setApplockGesture(paramList);
    }

    public static List<LockPatternView.Cell> stringToPattern(String paramString)
    {
        ArrayList localArrayList = new ArrayList();
        paramString = paramString.getBytes();
        int i = 0;
        while (i < paramString.length)
        {
            int j = paramString[i];
            localArrayList.add(LockPatternView.Cell.of(j / 3, j % 3));
            i += 1;
        }
        return localArrayList;
    }
}

