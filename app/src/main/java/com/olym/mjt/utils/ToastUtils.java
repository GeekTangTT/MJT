package com.olym.mjt.utils;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;
import com.olym.mjt.module.MjtApplication;

public class ToastUtils
{
    private static boolean isJumpWhenMore;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Toast sToast;

    private ToastUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void cancelToast()
    {
        if (sToast != null)
        {
            sToast.cancel();
            sToast = null;
        }
    }

    public static void init(boolean paramBoolean)
    {
        isJumpWhenMore = paramBoolean;
    }

    public static void showLongToast(@StringRes int paramInt)
    {
        showToast(paramInt, 1);
    }

    public static void showLongToast(@StringRes int paramInt, Object... paramVarArgs)
    {
        showToast(paramInt, 1, paramVarArgs);
    }

    public static void showLongToast(CharSequence paramCharSequence)
    {
        showToast(paramCharSequence, 1);
    }

    public static void showLongToast(String paramString, Object... paramVarArgs)
    {
        showToast(paramString, 1, paramVarArgs);
    }

    public static void showLongToastSafe(@StringRes int paramInt)
    {
        sHandler.post(new Runnable()
        {
            public void run()
            {
                ToastUtils.showToast(this.val$resId, 1);
            }
        });
    }

    public static void showLongToastSafe(@StringRes int paramInt, final Object... paramVarArgs)
    {
        sHandler.post(new Runnable()
        {
            public void run()
            {
                ToastUtils.showToast(this.val$resId, 1, paramVarArgs);
            }
        });
    }

    public static void showLongToastSafe(CharSequence paramCharSequence)
    {
        sHandler.post(new Runnable()
        {
            public void run()
            {
                ToastUtils.showToast(this.val$text, 1);
            }
        });
    }

    public static void showLongToastSafe(String paramString, final Object... paramVarArgs)
    {
        sHandler.post(new Runnable()
        {
            public void run()
            {
                ToastUtils.showToast(this.val$format, 1, paramVarArgs);
            }
        });
    }

    public static void showShortToast(@StringRes int paramInt)
    {
        showToast(paramInt, 0);
    }

    public static void showShortToast(@StringRes int paramInt, Object... paramVarArgs)
    {
        showToast(paramInt, 0, paramVarArgs);
    }

    public static void showShortToast(CharSequence paramCharSequence)
    {
        showToast(paramCharSequence, 0);
    }

    public static void showShortToast(String paramString, Object... paramVarArgs)
    {
        showToast(paramString, 0, paramVarArgs);
    }

    public static void showShortToastSafe(@StringRes int paramInt)
    {
        sHandler.post(new Runnable()
        {
            public void run()
            {
                ToastUtils.showToast(this.val$resId, 0);
            }
        });
    }

    public static void showShortToastSafe(@StringRes int paramInt, final Object... paramVarArgs)
    {
        sHandler.post(new Runnable()
        {
            public void run()
            {
                ToastUtils.showToast(this.val$resId, 0, paramVarArgs);
            }
        });
    }

    public static void showShortToastSafe(CharSequence paramCharSequence)
    {
        sHandler.post(new Runnable()
        {
            public void run()
            {
                ToastUtils.showToast(this.val$text, 0);
            }
        });
    }

    public static void showShortToastSafe(String paramString, final Object... paramVarArgs)
    {
        sHandler.post(new Runnable()
        {
            public void run()
            {
                ToastUtils.showToast(this.val$format, 0, paramVarArgs);
            }
        });
    }

    private static void showToast(@StringRes int paramInt1, int paramInt2)
    {
        showToast(MjtApplication.getInstance().getResources().getString(paramInt1), paramInt2);
    }

    private static void showToast(@StringRes int paramInt1, int paramInt2, Object... paramVarArgs)
    {
        showToast(String.format(MjtApplication.getInstance().getResources().getString(paramInt1), paramVarArgs), paramInt2);
    }

    private static void showToast(CharSequence paramCharSequence, int paramInt)
    {
        if (TextUtils.isEmpty(paramCharSequence)) {
            return;
        }
        if (isJumpWhenMore) {
            cancelToast();
        }
        if (sToast == null) {
            sToast = Toast.makeText(MjtApplication.getInstance(), paramCharSequence, paramInt);
        }
        for (;;)
        {
            sToast.show();
            return;
            sToast.setText(paramCharSequence);
            sToast.setDuration(paramInt);
        }
    }

    private static void showToast(String paramString, int paramInt, Object... paramVarArgs)
    {
        showToast(String.format(paramString, paramVarArgs), paramInt);
    }
}
