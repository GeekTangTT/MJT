package com.olym.mjt.module.calllog.calllogdetails;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.olym.mjt.module.calllog.CallItem.ExtraItem;
import com.olym.mjt.utils.DateUtil;
import java.util.ArrayList;

public class CallLogDetailsAdapter
        extends BaseAdapter
{
    private Context context;
    private ArrayList<CallItem.ExtraItem> datas;

    public CallLogDetailsAdapter(Context paramContext, ArrayList<CallItem.ExtraItem> paramArrayList)
    {
        this.context = paramContext;
        this.datas = paramArrayList;
    }

    private String handleDuration(long paramLong)
    {
        if (paramLong == 0L) {
            return "";
        }
        if (paramLong < 60L) {
            return paramLong + this.context.getResources().getString(2131690032);
        }
        long l1 = paramLong / 60L;
        paramLong %= 60L;
        if (l1 < 60L) {
            return l1 + this.context.getResources().getString(2131689920) + " " + paramLong + this.context.getResources().getString(2131690032);
        }
        long l2 = l1 / 60L;
        return l2 + this.context.getResources().getString(2131689838) + " " + l1 % 60L + this.context.getResources().getString(2131689920) + " " + paramLong + this.context.getResources().getString(2131690032);
    }

    public int getCount()
    {
        return this.datas.size();
    }

    public Object getItem(int paramInt)
    {
        return this.datas.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        paramView = LayoutInflater.from(this.context).inflate(2131427515, null);
        paramViewGroup = (TextView)paramView.findViewById(2131231449);
        TextView localTextView1 = (TextView)paramView.findViewById(2131231511);
        TextView localTextView2 = (TextView)paramView.findViewById(2131231459);
        CallItem.ExtraItem localExtraItem = (CallItem.ExtraItem)this.datas.get(paramInt);
        paramViewGroup.setText(DateUtil.getTimeForHHMM(localExtraItem.getData()));
        paramInt = localExtraItem.getType();
        long l = localExtraItem.getDuration();
        switch (paramInt)
        {
        }
        for (;;)
        {
            localTextView2.setText(handleDuration(l));
            return paramView;
            if (l == 0L)
            {
                localTextView1.setText(this.context.getResources().getString(2131689646));
            }
            else
            {
                localTextView1.setText(this.context.getResources().getString(2131689844));
                continue;
                localTextView1.setText(this.context.getResources().getString(2131689921));
                continue;
                if (l == 0L) {
                    localTextView1.setText(this.context.getResources().getString(2131689646));
                } else {
                    localTextView1.setText(this.context.getResources().getString(2131689967));
                }
            }
        }
    }
}
