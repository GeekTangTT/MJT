package com.olym.mjt.databean.event;

import com.olym.mjt.databean.bean.CompanyGroup;
import com.olym.mjt.utils.EventBusUtil;

public class ClickCompanyTitleEvent
{
    private CompanyGroup companyGroup;
    private int groupIndex;

    public ClickCompanyTitleEvent(int paramInt, CompanyGroup paramCompanyGroup)
    {
        this.groupIndex = paramInt;
        this.companyGroup = paramCompanyGroup;
    }

    public static void post(ClickCompanyTitleEvent paramClickCompanyTitleEvent)
    {
        EventBusUtil.post(paramClickCompanyTitleEvent);
    }

    public CompanyGroup getCompanyGroup()
    {
        return this.companyGroup;
    }

    public int getGroupIndex()
    {
        return this.groupIndex;
    }
}
