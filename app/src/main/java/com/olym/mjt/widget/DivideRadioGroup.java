package com.olym.mjt.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DivideRadioGroup
        extends LinearLayout
{
    private int mCheckedId = -1;
    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private PassThroughHierarchyChangeListener mPassThroughListener;
    private boolean mProtectFromCheckedChange = false;

    public DivideRadioGroup(Context paramContext)
    {
        super(paramContext);
        setOrientation(1);
        init();
    }

    public DivideRadioGroup(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        init();
    }

    private void init()
    {
        this.mChildOnCheckedChangeListener = new CheckedStateTracker(null);
        this.mPassThroughListener = new PassThroughHierarchyChangeListener(null);
        super.setOnHierarchyChangeListener(this.mPassThroughListener);
    }

    private void setCheckedId(int paramInt)
    {
        this.mCheckedId = paramInt;
        if (this.mOnCheckedChangeListener != null) {
            this.mOnCheckedChangeListener.onCheckedChanged(this, this.mCheckedId);
        }
    }

    private void setCheckedStateForView(int paramInt, boolean paramBoolean)
    {
        View localView = findViewById(paramInt);
        if ((localView != null) && ((localView instanceof RadioButton))) {
            ((RadioButton)localView).setChecked(paramBoolean);
        }
    }

    public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
    {
        RadioButton localRadioButton;
        if ((paramView instanceof RadioButton))
        {
            localRadioButton = (RadioButton)paramView;
            if (localRadioButton.isChecked())
            {
                this.mProtectFromCheckedChange = true;
                if (this.mCheckedId != -1) {
                    setCheckedStateForView(this.mCheckedId, false);
                }
                this.mProtectFromCheckedChange = false;
                setCheckedId(localRadioButton.getId());
            }
        }
        for (;;)
        {
            super.addView(paramView, paramInt, paramLayoutParams);
            return;
            if ((paramView instanceof ViewGroup))
            {
                localRadioButton = findRadioButton((ViewGroup)paramView);
                if ((localRadioButton != null) && (localRadioButton.isChecked()))
                {
                    this.mProtectFromCheckedChange = true;
                    if (this.mCheckedId != -1) {
                        setCheckedStateForView(this.mCheckedId, false);
                    }
                    this.mProtectFromCheckedChange = false;
                    setCheckedId(localRadioButton.getId());
                }
            }
        }
    }

    public void check(int paramInt)
    {
        if ((paramInt != -1) && (paramInt == this.mCheckedId)) {
            return;
        }
        if (this.mCheckedId != -1) {
            setCheckedStateForView(this.mCheckedId, false);
        }
        if (paramInt != -1) {
            setCheckedStateForView(paramInt, true);
        }
        setCheckedId(paramInt);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
        return paramLayoutParams instanceof LayoutParams;
    }

    public void clearCheck()
    {
        check(-1);
    }

    public RadioButton findRadioButton(ViewGroup paramViewGroup)
    {
        Object localObject1 = null;
        int j = paramViewGroup.getChildCount();
        int i = 0;
        if (i < j)
        {
            Object localObject2;
            if ((paramViewGroup.getChildAt(i) instanceof RadioButton)) {
                localObject2 = (RadioButton)paramViewGroup.getChildAt(i);
            }
            for (;;)
            {
                i += 1;
                localObject1 = localObject2;
                break;
                localObject2 = localObject1;
                if ((paramViewGroup.getChildAt(i) instanceof ViewGroup))
                {
                    findRadioButton((ViewGroup)paramViewGroup.getChildAt(i));
                    localObject2 = localObject1;
                }
            }
        }
        return (RadioButton)localObject1;
    }

    protected LinearLayout.LayoutParams generateDefaultLayoutParams()
    {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
    {
        return new LayoutParams(getContext(), paramAttributeSet);
    }

    public int getCheckedRadioButtonId()
    {
        return this.mCheckedId;
    }

    protected void onFinishInflate()
    {
        super.onFinishInflate();
        if (this.mCheckedId != -1)
        {
            this.mProtectFromCheckedChange = true;
            setCheckedStateForView(this.mCheckedId, true);
            this.mProtectFromCheckedChange = false;
            setCheckedId(this.mCheckedId);
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
    {
        super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
        paramAccessibilityEvent.setClassName(RadioGroup.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
    {
        super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
        paramAccessibilityNodeInfo.setClassName(RadioGroup.class.getName());
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener paramOnCheckedChangeListener)
    {
        this.mOnCheckedChangeListener = paramOnCheckedChangeListener;
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener paramOnHierarchyChangeListener)
    {
        PassThroughHierarchyChangeListener.access$202(this.mPassThroughListener, paramOnHierarchyChangeListener);
    }

    private class CheckedStateTracker
            implements CompoundButton.OnCheckedChangeListener
    {
        private CheckedStateTracker() {}

        public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
        {
            if (DivideRadioGroup.this.mProtectFromCheckedChange) {
                return;
            }
            DivideRadioGroup.access$302(DivideRadioGroup.this, true);
            if (DivideRadioGroup.this.mCheckedId != -1) {
                DivideRadioGroup.this.setCheckedStateForView(DivideRadioGroup.this.mCheckedId, false);
            }
            DivideRadioGroup.access$302(DivideRadioGroup.this, false);
            int i = paramCompoundButton.getId();
            DivideRadioGroup.this.setCheckedId(i);
        }
    }

    public static class LayoutParams
            extends LinearLayout.LayoutParams
    {
        public LayoutParams(int paramInt1, int paramInt2)
        {
            super(paramInt2);
        }

        public LayoutParams(int paramInt1, int paramInt2, float paramFloat)
        {
            super(paramInt2, paramFloat);
        }

        public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
        {
            super(paramAttributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
        {
            super();
        }

        public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
        {
            super();
        }

        protected void setBaseAttributes(TypedArray paramTypedArray, int paramInt1, int paramInt2)
        {
            if (paramTypedArray.hasValue(paramInt1)) {}
            for (this.width = paramTypedArray.getLayoutDimension(paramInt1, "layout_width"); paramTypedArray.hasValue(paramInt2); this.width = -2)
            {
                this.height = paramTypedArray.getLayoutDimension(paramInt2, "layout_height");
                return;
            }
            this.height = -2;
        }
    }

    public static abstract interface OnCheckedChangeListener
    {
        public abstract void onCheckedChanged(DivideRadioGroup paramDivideRadioGroup, int paramInt);
    }

    private class PassThroughHierarchyChangeListener
            implements ViewGroup.OnHierarchyChangeListener
    {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {}

        public void onChildViewAdded(View paramView1, View paramView2)
        {
            if ((paramView1 == DivideRadioGroup.this) && ((paramView2 instanceof RadioButton)))
            {
                if (paramView2.getId() == -1) {
                    paramView2.setId(paramView2.hashCode());
                }
                ((RadioButton)paramView2).setOnCheckedChangeListener(DivideRadioGroup.this.mChildOnCheckedChangeListener);
            }
            for (;;)
            {
                if (this.mOnHierarchyChangeListener != null) {
                    this.mOnHierarchyChangeListener.onChildViewAdded(paramView1, paramView2);
                }
                return;
                if ((paramView1 == DivideRadioGroup.this) && ((paramView2 instanceof ViewGroup)))
                {
                    RadioButton localRadioButton = DivideRadioGroup.this.findRadioButton((ViewGroup)paramView2);
                    if (localRadioButton != null)
                    {
                        if (localRadioButton.getId() == -1) {
                            localRadioButton.setId(localRadioButton.hashCode());
                        }
                        localRadioButton.setOnCheckedChangeListener(DivideRadioGroup.this.mChildOnCheckedChangeListener);
                    }
                }
            }
        }

        public void onChildViewRemoved(View paramView1, View paramView2)
        {
            if ((paramView1 == DivideRadioGroup.this) && ((paramView2 instanceof RadioButton))) {
                ((RadioButton)paramView2).setOnCheckedChangeListener(null);
            }
            for (;;)
            {
                if (this.mOnHierarchyChangeListener != null) {
                    this.mOnHierarchyChangeListener.onChildViewRemoved(paramView1, paramView2);
                }
                return;
                if ((paramView1 == DivideRadioGroup.this) && ((paramView2 instanceof ViewGroup)))
                {
                    RadioButton localRadioButton = DivideRadioGroup.this.findRadioButton((ViewGroup)paramView2);
                    if (localRadioButton != null) {
                        localRadioButton.setOnCheckedChangeListener(null);
                    }
                }
            }
        }
    }
}
