package com.olym.mjt.module.user.ibcdomain;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ActionSheetDialog
        extends BottomBaseDialog
{
    private BaseAdapter adapter;
    private ArrayList<DialogMenuItem> contents = new ArrayList();
    private float cornerRadius_DP = 5.0F;
    private int dividerColor = Color.parseColor("#D7D7D9");
    private float dividerHeight_DP = 0.8F;
    private boolean isTitleShow = true;
    private float itemHeight_DP = 48.0F;
    private int itemPressColor = Color.parseColor("#ffcccccc");
    private int itemTextColor = Color.parseColor("#44A2FF");
    private float itemTextSize_SP = 17.5F;
    private LayoutAnimationController lac;
    private ListView lv;
    private int lvBgColor = Color.parseColor("#ddffffff");
    private OnOperItemClickL onOperItemClickL;
    private String title;
    private int titleBgColor = Color.parseColor("#ddffffff");
    private float titleHeight = 48.0F;
    private int titleTextColor = Color.parseColor("#8F8F8F");
    private float titleTextSize_SP = 17.5F;
    private TextView tv_cancel;
    private TextView tv_title;
    private View v_line_title;

    public ActionSheetDialog(Context paramContext, BaseAdapter paramBaseAdapter, View paramView)
    {
        super(paramContext, paramView);
        this.adapter = paramBaseAdapter;
        init();
    }

    public ActionSheetDialog(Context paramContext, ArrayList<DialogMenuItem> paramArrayList, View paramView)
    {
        super(paramContext, paramView);
        this.contents.addAll(paramArrayList);
        init();
    }

    public ActionSheetDialog(Context paramContext, String[] paramArrayOfString, View paramView)
    {
        super(paramContext, paramView);
        this.contents = new ArrayList();
        int j = paramArrayOfString.length;
        int i = 0;
        while (i < j)
        {
            paramView = new DialogMenuItem(paramArrayOfString[i], 0);
            this.contents.add(paramView);
            i += 1;
        }
        this.title = paramContext.getResources().getString(2131690105);
        init();
    }

    private void init()
    {
        widthScale(0.95F);
        TranslateAnimation localTranslateAnimation = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 6.0F, 1, 0.0F);
        localTranslateAnimation.setInterpolator(new DecelerateInterpolator());
        localTranslateAnimation.setDuration(350L);
        localTranslateAnimation.setStartOffset(150L);
        this.lac = new LayoutAnimationController(localTranslateAnimation, 0.12F);
        this.lac.setInterpolator(new DecelerateInterpolator());
    }

    public ActionSheetDialog cornerRadius(float paramFloat)
    {
        this.cornerRadius_DP = paramFloat;
        return this;
    }

    public ActionSheetDialog dividerColor(int paramInt)
    {
        this.dividerColor = paramInt;
        return this;
    }

    public ActionSheetDialog dividerHeight(float paramFloat)
    {
        this.dividerHeight_DP = paramFloat;
        return this;
    }

    public ActionSheetDialog isTitleShow(boolean paramBoolean)
    {
        this.isTitleShow = paramBoolean;
        return this;
    }

    public ActionSheetDialog itemHeight(float paramFloat)
    {
        this.itemHeight_DP = paramFloat;
        return this;
    }

    public ActionSheetDialog itemPressColor(int paramInt)
    {
        this.itemPressColor = paramInt;
        return this;
    }

    public ActionSheetDialog itemTextColor(int paramInt)
    {
        this.itemTextColor = paramInt;
        return this;
    }

    public ActionSheetDialog itemTextSize(float paramFloat)
    {
        this.itemTextSize_SP = paramFloat;
        return this;
    }

    public ActionSheetDialog layoutAnimation(LayoutAnimationController paramLayoutAnimationController)
    {
        this.lac = paramLayoutAnimationController;
        return this;
    }

    public ActionSheetDialog lvBgColor(int paramInt)
    {
        this.lvBgColor = paramInt;
        return this;
    }

    public View onCreateView()
    {
        LinearLayout localLinearLayout = new LinearLayout(this.context);
        localLinearLayout.setOrientation(1);
        localLinearLayout.setBackgroundColor(0);
        this.tv_title = new TextView(this.context);
        this.tv_title.setGravity(17);
        this.tv_title.setPadding(dp2px(10.0F), dp2px(5.0F), dp2px(10.0F), dp2px(5.0F));
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
        localLayoutParams.topMargin = dp2px(20.0F);
        localLinearLayout.addView(this.tv_title, localLayoutParams);
        this.v_line_title = new View(this.context);
        localLinearLayout.addView(this.v_line_title);
        this.lv = new ListView(this.context);
        this.lv.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 1.0F));
        this.lv.setCacheColorHint(0);
        this.lv.setFadingEdgeLength(0);
        this.lv.setVerticalScrollBarEnabled(false);
        this.lv.setSelector(new ColorDrawable(0));
        localLinearLayout.addView(this.lv);
        this.tv_cancel = new TextView(this.context);
        this.tv_cancel.setGravity(17);
        localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
        localLayoutParams.topMargin = dp2px(3.0F);
        localLayoutParams.bottomMargin = dp2px(7.0F);
        this.tv_cancel.setLayoutParams(localLayoutParams);
        localLinearLayout.addView(this.tv_cancel);
        return localLinearLayout;
    }

    public void setOnOperItemClickL(OnOperItemClickL paramOnOperItemClickL)
    {
        this.onOperItemClickL = paramOnOperItemClickL;
    }

    public void setUiBeforShow()
    {
        float f = dp2px(this.cornerRadius_DP);
        this.tv_title.setHeight(dp2px(this.titleHeight));
        this.tv_title.setBackgroundDrawable(CornerUtils.cornerDrawable(this.titleBgColor, new float[] { f, f, f, f, 0.0F, 0.0F, 0.0F, 0.0F }));
        this.tv_title.setText(this.title);
        this.tv_title.setTextSize(2, this.titleTextSize_SP);
        this.tv_title.setTextColor(this.titleTextColor);
        Object localObject = this.tv_title;
        int i;
        if (this.isTitleShow)
        {
            i = 0;
            ((TextView)localObject).setVisibility(i);
            this.v_line_title.setLayoutParams(new LinearLayout.LayoutParams(-1, dp2px(this.dividerHeight_DP)));
            this.v_line_title.setBackgroundColor(this.dividerColor);
            localObject = this.v_line_title;
            if (!this.isTitleShow) {
                break label432;
            }
            i = 0;
            label178:
            ((View)localObject).setVisibility(i);
            this.tv_cancel.setHeight(dp2px(this.itemHeight_DP));
            this.tv_cancel.setText(this.context.getResources().getString(2131689644));
            this.tv_cancel.setTextSize(2, this.itemTextSize_SP);
            this.tv_cancel.setTextColor(this.itemTextColor);
            this.tv_cancel.setBackgroundDrawable(CornerUtils.listItemSelector(f, this.lvBgColor, this.itemPressColor, 1, 0));
            this.tv_cancel.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View paramAnonymousView)
                {
                    ActionSheetDialog.this.dismiss();
                }
            });
            this.lv.setDivider(new ColorDrawable(this.dividerColor));
            this.lv.setDividerHeight(dp2px(this.dividerHeight_DP));
            if (!this.isTitleShow) {
                break label438;
            }
            this.lv.setBackgroundDrawable(CornerUtils.cornerDrawable(this.lvBgColor, new float[] { 0.0F, 0.0F, 0.0F, 0.0F, f, f, f, f }));
        }
        for (;;)
        {
            if (this.adapter == null) {
                this.adapter = new ListDialogAdapter();
            }
            this.lv.setAdapter(this.adapter);
            this.lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
                {
                    if (ActionSheetDialog.this.onOperItemClickL != null) {
                        ActionSheetDialog.this.onOperItemClickL.onOperItemClick(paramAnonymousAdapterView, paramAnonymousView, paramAnonymousInt, paramAnonymousLong);
                    }
                }
            });
            this.lv.setLayoutAnimation(this.lac);
            return;
            i = 8;
            break;
            label432:
            i = 8;
            break label178;
            label438:
            this.lv.setBackgroundDrawable(CornerUtils.cornerDrawable(this.lvBgColor, f));
        }
    }

    public ActionSheetDialog title(String paramString)
    {
        this.title = paramString;
        return this;
    }

    public ActionSheetDialog titleBgColor(int paramInt)
    {
        this.titleBgColor = paramInt;
        return this;
    }

    public ActionSheetDialog titleHeight(float paramFloat)
    {
        this.titleHeight = paramFloat;
        return this;
    }

    public ActionSheetDialog titleTextColor(int paramInt)
    {
        this.titleTextColor = paramInt;
        return this;
    }

    public ActionSheetDialog titleTextSize_SP(float paramFloat)
    {
        this.titleTextSize_SP = paramFloat;
        return this;
    }

    class ListDialogAdapter
            extends BaseAdapter
    {
        ListDialogAdapter() {}

        public int getCount()
        {
            return ActionSheetDialog.this.contents.size();
        }

        public Object getItem(int paramInt)
        {
            return null;
        }

        public long getItemId(int paramInt)
        {
            return paramInt;
        }

        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
        {
            boolean bool = true;
            int i = 0;
            paramView = (DialogMenuItem)ActionSheetDialog.this.contents.get(paramInt);
            paramViewGroup = new LinearLayout(ActionSheetDialog.this.context);
            paramViewGroup.setOrientation(0);
            paramViewGroup.setGravity(16);
            ImageView localImageView = new ImageView(ActionSheetDialog.this.context);
            localImageView.setPadding(0, 0, ActionSheetDialog.this.dp2px(15.0F), 0);
            paramViewGroup.addView(localImageView);
            TextView localTextView = new TextView(ActionSheetDialog.this.context);
            localTextView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            localTextView.setSingleLine(true);
            localTextView.setGravity(17);
            localTextView.setTextColor(ActionSheetDialog.this.itemTextColor);
            localTextView.setTextSize(2, ActionSheetDialog.this.itemTextSize_SP);
            localTextView.setHeight(ActionSheetDialog.this.dp2px(ActionSheetDialog.this.itemHeight_DP));
            paramViewGroup.addView(localTextView);
            float f = ActionSheetDialog.this.dp2px(ActionSheetDialog.this.cornerRadius_DP);
            if (ActionSheetDialog.this.isTitleShow)
            {
                int j = ActionSheetDialog.this.itemPressColor;
                if (paramInt == ActionSheetDialog.this.contents.size() - 1) {
                    paramViewGroup.setBackgroundDrawable(CornerUtils.listItemSelector(f, 0, j, bool));
                }
            }
            for (;;)
            {
                localImageView.setImageResource(paramView.resId);
                localTextView.setText(paramView.operName);
                paramInt = i;
                if (paramView.resId == 0) {
                    paramInt = 8;
                }
                localImageView.setVisibility(paramInt);
                return paramViewGroup;
                bool = false;
                break;
                paramViewGroup.setBackgroundDrawable(CornerUtils.listItemSelector(f, 0, ActionSheetDialog.this.itemPressColor, ActionSheetDialog.this.contents.size(), paramInt));
            }
        }
    }
}
