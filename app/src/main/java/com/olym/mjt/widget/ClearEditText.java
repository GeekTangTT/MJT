package com.olym.mjt.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

public class ClearEditText
        extends EditText
        implements TextWatcher, View.OnFocusChangeListener
{
    private boolean hasFoucs;
    private Drawable mClearDrawable;

    public ClearEditText(Context paramContext)
    {
        this(paramContext, null);
    }

    public ClearEditText(Context paramContext, AttributeSet paramAttributeSet)
    {
        this(paramContext, paramAttributeSet, 16842862);
    }

    public ClearEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void init()
    {
        this.mClearDrawable = getCompoundDrawables()[2];
        if (this.mClearDrawable == null) {
            this.mClearDrawable = getResources().getDrawable(2131165710);
        }
        this.mClearDrawable.setBounds(0, 0, this.mClearDrawable.getIntrinsicWidth(), this.mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    public void afterTextChanged(Editable paramEditable) {}

    public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}

    public void onFocusChange(View paramView, boolean paramBoolean)
    {
        boolean bool = false;
        this.hasFoucs = paramBoolean;
        if (paramBoolean)
        {
            paramBoolean = bool;
            if (getText().length() > 0) {
                paramBoolean = true;
            }
            setClearIconVisible(paramBoolean);
            return;
        }
        setClearIconVisible(false);
    }

    public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
        if (this.hasFoucs) {
            if (paramCharSequence.length() <= 0) {
                break label26;
            }
        }
        label26:
        for (boolean bool = true;; bool = false)
        {
            setClearIconVisible(bool);
            return;
        }
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
        int i = 1;
        if ((paramMotionEvent.getAction() == 1) && (getCompoundDrawables()[2] != null)) {
            if ((paramMotionEvent.getX() <= getWidth() - getTotalPaddingRight()) || (paramMotionEvent.getX() >= getWidth() - getPaddingRight())) {
                break label71;
            }
        }
        for (;;)
        {
            if (i != 0) {
                setText("");
            }
            return super.onTouchEvent(paramMotionEvent);
            label71:
            i = 0;
        }
    }

    protected void setClearIconVisible(boolean paramBoolean)
    {
        if (paramBoolean) {}
        for (Drawable localDrawable = this.mClearDrawable;; localDrawable = null)
        {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], localDrawable, getCompoundDrawables()[3]);
            return;
        }
    }
}
