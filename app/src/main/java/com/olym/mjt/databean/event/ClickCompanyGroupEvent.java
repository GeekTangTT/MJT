package com.olym.mjt.databean.event;

import com.olym.mjt.databean.bean.CompanyGroup;
import com.olym.mjt.utils.EventBusUtil;

public class ClickCompanyGroupEvent
{
    private CompanyGroup companyGroup;
    private int groupIndex;

    public ClickCompanyGroupEvent(int paramInt, CompanyGroup paramCompanyGroup)
    {
        this.groupIndex = paramInt;
        this.companyGroup = paramCompanyGroup;
    }

    public static void post(ClickCompanyGroupEvent paramClickCompanyGroupEvent)
    {
        EventBusUtil.post(paramClickCompanyGroupEvent);
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
