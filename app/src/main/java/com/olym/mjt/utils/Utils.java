package com.olym.mjt.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Utils
{
    public static void hideSoftInput(Context paramContext, View paramView)
    {
        paramContext = (InputMethodManager)paramContext.getSystemService("input_method");
        if (paramContext == null) {
            return;
        }
        paramContext.hideSoftInputFromWindow(paramView.getWindowToken(), 0);
    }

    public static void showSoftInput(EditText paramEditText)
    {
        paramEditText.setFocusable(true);
        paramEditText.setFocusableInTouchMode(true);
        paramEditText.requestFocus();
        InputMethodManager localInputMethodManager = (InputMethodManager)paramEditText.getContext().getSystemService("input_method");
        if (localInputMethodManager == null) {
            return;
        }
        localInputMethodManager.showSoftInput(paramEditText, 0);
    }
}
