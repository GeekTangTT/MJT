package com.olym.mjt.module.message.tools.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.olym.mjt.module.message.tools.shortcutbadger.Badger;
import com.olym.mjt.module.message.tools.shortcutbadger.ShortcutBadgeException;
import com.olym.mjt.module.message.tools.shortcutbadger.util.BroadcastHelper;
import java.util.Arrays;
import java.util.List;

@Deprecated
public class LGHomeBadger
        implements Badger
{
    private static final String INTENT_ACTION = "android.intent.action.BADGE_COUNT_UPDATE";
    private static final String INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name";
    private static final String INTENT_EXTRA_BADGE_COUNT = "badge_count";
    private static final String INTENT_EXTRA_PACKAGENAME = "badge_count_package_name";

    public void executeBadge(Context paramContext, ComponentName paramComponentName, int paramInt)
            throws ShortcutBadgeException
    {
        Intent localIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        localIntent.putExtra("badge_count", paramInt);
        localIntent.putExtra("badge_count_package_name", paramComponentName.getPackageName());
        localIntent.putExtra("badge_count_class_name", paramComponentName.getClassName());
        if (BroadcastHelper.canResolveBroadcast(paramContext, localIntent))
        {
            paramContext.sendBroadcast(localIntent);
            return;
        }
        throw new ShortcutBadgeException("unable to resolve intent: " + localIntent.toString());
    }

    public List<String> getSupportLaunchers()
    {
        return Arrays.asList(new String[] { "com.lge.launcher", "com.lge.launcher2" });
    }
}

