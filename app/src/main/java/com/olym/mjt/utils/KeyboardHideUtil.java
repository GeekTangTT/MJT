package com.olym.mjt.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ScrollView;

public class KeyboardHideUtil
{
    private KeyboardHideUtil(final Activity paramActivity)
    {
        ViewGroup localViewGroup = (ViewGroup)paramActivity.findViewById(16908290);
        localViewGroup.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
            {
                KeyboardHideUtil.this.dispatchTouchEvent(paramActivity, paramAnonymousMotionEvent);
                return false;
            }
        });
        getScrollView(localViewGroup, paramActivity);
    }

    private void getScrollView(ViewGroup paramViewGroup, final Activity paramActivity)
    {
        if (paramViewGroup == null) {
            return;
        }
        int j = paramViewGroup.getChildCount();
        int i = 0;
        label13:
        View localView;
        if (i < j)
        {
            localView = paramViewGroup.getChildAt(i);
            if (!(localView instanceof ScrollView)) {
                break label58;
            }
            ((ScrollView)localView).setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
                {
                    KeyboardHideUtil.this.dispatchTouchEvent(paramActivity, paramAnonymousMotionEvent);
                    return false;
                }
            });
        }
        for (;;)
        {
            i += 1;
            break label13;
            break;
            label58:
            if ((localView instanceof AbsListView)) {
                ((AbsListView)localView).setOnTouchListener(new View.OnTouchListener()
                {
                    public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
                    {
                        KeyboardHideUtil.this.dispatchTouchEvent(paramActivity, paramAnonymousMotionEvent);
                        return false;
                    }
                });
            } else if ((localView instanceof ViewGroup)) {
                getScrollView((ViewGroup)localView, paramActivity);
            }
        }
    }

    public static void hideKeyboard(Context paramContext, IBinder paramIBinder)
    {
        if (paramIBinder != null) {
            ((InputMethodManager)paramContext.getSystemService("input_method")).hideSoftInputFromWindow(paramIBinder, 2);
        }
    }

    public static void hideKeyboard(Context paramContext, View paramView)
    {
        InputMethodManager localInputMethodManager = (InputMethodManager)paramContext.getSystemService("input_method");
        paramContext = ((Activity)paramContext).getCurrentFocus();
        if ((localInputMethodManager.isActive(paramView)) && (paramContext != null) && (paramContext.getId() == paramView.getId())) {
            localInputMethodManager.toggleSoftInput(0, 2);
        }
    }

    private void hideSoftInput(Activity paramActivity, IBinder paramIBinder)
    {
        if (paramIBinder != null) {
            ((InputMethodManager)paramActivity.getSystemService("input_method")).hideSoftInputFromWindow(paramIBinder, 2);
        }
    }

    public static void init(Activity paramActivity)
    {
        new KeyboardHideUtil(paramActivity);
    }

    private boolean isShouldHideInput(View paramView, MotionEvent paramMotionEvent)
    {
        if ((paramView instanceof EditText))
        {
            Rect localRect = new Rect();
            paramView.getHitRect(localRect);
            if (localRect.contains((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())) {
                return false;
            }
        }
        return true;
    }

    public boolean dispatchTouchEvent(Activity paramActivity, MotionEvent paramMotionEvent)
    {
        if (paramMotionEvent.getAction() == 0)
        {
            View localView = paramActivity.getCurrentFocus();
            if ((localView != null) && (isShouldHideInput(localView, paramMotionEvent)))
            {
                hideSoftInput(paramActivity, localView.getWindowToken());
                localView.clearFocus();
            }
        }
        return false;
    }
}
