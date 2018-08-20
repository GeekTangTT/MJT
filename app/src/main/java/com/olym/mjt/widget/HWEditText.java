package com.olym.mjt.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;
import com.olym.mjt.R.styleable;
import java.lang.reflect.Field;

public class HWEditText
        extends EditText
{
    public HWEditText(Context paramContext)
    {
        this(paramContext, null);
    }

    public HWEditText(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        modifyCursorDrawable(paramContext, paramAttributeSet);
    }

    public HWEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        modifyCursorDrawable(paramContext, paramAttributeSet);
    }

    private void modifyCursorDrawable(Context paramContext, AttributeSet paramAttributeSet)
    {
        paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.HWEditText);
        int i = paramContext.getResourceId(0, 0);
        if (i != 0) {}
        try
        {
            paramAttributeSet = TextView.class.getDeclaredField("mCursorDrawableRes");
            paramAttributeSet.setAccessible(true);
            paramAttributeSet.set(this, Integer.valueOf(i));
            paramContext.recycle();
            return;
        }
        catch (IllegalAccessException paramAttributeSet)
        {
            for (;;)
            {
                paramAttributeSet.printStackTrace();
            }
        }
        catch (NoSuchFieldException paramAttributeSet)
        {
            for (;;)
            {
                paramAttributeSet.printStackTrace();
            }
        }
    }
}
