package com.olym.mjt.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Dialpad
        extends FrameLayout
        implements View.OnClickListener
{
    @SuppressLint({"UseSparseArrays"})
    private static final Map<Integer, int[]> DIGITS_BTNS = new HashMap();
    private static final SparseArray<String> DIGITS_NAMES;
    private static final String THIS_FILE;
    private OnDialKeyListener onDialKeyListener;

    static
    {
        DIGITS_BTNS.put(Integer.valueOf(2131230794), new int[] { 0, 7 });
        DIGITS_BTNS.put(Integer.valueOf(2131230795), new int[] { 1, 8 });
        DIGITS_BTNS.put(Integer.valueOf(2131230796), new int[] { 2, 9 });
        DIGITS_BTNS.put(Integer.valueOf(2131230797), new int[] { 3, 10 });
        DIGITS_BTNS.put(Integer.valueOf(2131230798), new int[] { 4, 11 });
        DIGITS_BTNS.put(Integer.valueOf(2131230799), new int[] { 5, 12 });
        DIGITS_BTNS.put(Integer.valueOf(2131230800), new int[] { 6, 13 });
        DIGITS_BTNS.put(Integer.valueOf(2131230801), new int[] { 7, 14 });
        DIGITS_BTNS.put(Integer.valueOf(2131230802), new int[] { 8, 15 });
        DIGITS_BTNS.put(Integer.valueOf(2131230803), new int[] { 9, 16 });
        DIGITS_BTNS.put(Integer.valueOf(2131230805), new int[] { 11, 18 });
        DIGITS_BTNS.put(Integer.valueOf(2131230806), new int[] { 10, 17 });
        DIGITS_NAMES = new SparseArray();
        THIS_FILE = null;
        DIGITS_NAMES.put(2131230794, "0");
        DIGITS_NAMES.put(2131230795, "1");
        DIGITS_NAMES.put(2131230796, "2");
        DIGITS_NAMES.put(2131230797, "3");
        DIGITS_NAMES.put(2131230798, "4");
        DIGITS_NAMES.put(2131230799, "5");
        DIGITS_NAMES.put(2131230800, "6");
        DIGITS_NAMES.put(2131230801, "7");
        DIGITS_NAMES.put(2131230802, "8");
        DIGITS_NAMES.put(2131230803, "9");
        DIGITS_NAMES.put(2131230805, "pound");
        DIGITS_NAMES.put(2131230806, "star");
    }

    public Dialpad(Context paramContext)
    {
        super(paramContext);
        initLayout(paramContext);
    }

    public Dialpad(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        initLayout(paramContext);
    }

    private void dispatchDialKeyEvent(int paramInt)
    {
        if ((this.onDialKeyListener != null) && (DIGITS_BTNS.containsKey(Integer.valueOf(paramInt))))
        {
            int[] arrayOfInt = (int[])DIGITS_BTNS.get(Integer.valueOf(paramInt));
            this.onDialKeyListener.onTrigger(arrayOfInt[1], arrayOfInt[0]);
        }
    }

    private void initLayout(Context paramContext)
    {
        LayoutInflater.from(paramContext).inflate(2131427496, this, true);
    }

    public void onClick(View paramView)
    {
        dispatchDialKeyEvent(paramView.getId());
    }

    protected void onFinishInflate()
    {
        super.onFinishInflate();
        Iterator localIterator = DIGITS_BTNS.keySet().iterator();
        while (localIterator.hasNext())
        {
            RelativeLayout localRelativeLayout = (RelativeLayout)findViewById(((Integer)localIterator.next()).intValue());
            if (localRelativeLayout != null) {
                localRelativeLayout.setOnClickListener(this);
            }
        }
    }

    public void setOnDialKeyListener(OnDialKeyListener paramOnDialKeyListener)
    {
        this.onDialKeyListener = paramOnDialKeyListener;
    }

    public static abstract interface OnDialKeyListener
    {
        public abstract void onTrigger(int paramInt1, int paramInt2);
    }
}
