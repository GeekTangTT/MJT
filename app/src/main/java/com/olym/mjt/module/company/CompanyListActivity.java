package com.olym.mjt.module.company;

import android.os.Bundle;
import android.widget.ExpandableListView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.database.dao.CompanyGroupDao;
import com.olym.mjt.database.dao.CompanyUserDao;
import com.olym.mjt.databean.bean.CompanyGroup;
import com.olym.mjt.databean.bean.CompanyUser;
import com.olym.mjt.databean.bean.ServiceInfo;
import com.olym.mjt.databean.event.ClickCompanyGroupEvent;
import com.olym.mjt.databean.event.ClickCompanyTitleEvent;
import com.olym.mjt.network.client.HttpsClient;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427366)
public class CompanyListActivity
        extends BaseTopbarActivity<CompanyListPresenter>
        implements ICompanyListView
{
    private CompanyExListAdapter adapter;
    private ArrayList<ArrayList<CompanyGroup>> companyGroups;
    private ArrayList<ArrayList<CompanyUser>> companyUsers;
    @ViewInject(2131231074)
    private ExpandableListView listview;
    private ArrayList<ArrayList<CompanyGroup>> titles;

    public void destroy()
    {
        EventBusUtil.unregister(this);
    }

    public void getData(int paramInt, String paramString)
    {
        List localList1 = CompanyGroupDao.getInstance().getCompanyGroup(paramString);
        List localList2 = CompanyUserDao.getInstance().getCompanyUser(paramString);
        if ((localList1.isEmpty()) && (localList2.isEmpty())) {
            HttpsClient.getInstanse().getCompanyInfo(paramString, new CompanyListActivity.1(this, localList1, localList2, paramString, paramInt));
        }
        for (;;)
        {
            return;
            if (paramInt != -1) {
                break;
            }
            int i = localList1.size();
            paramInt = 0;
            while (paramInt < i)
            {
                paramString = new ArrayList();
                paramString.add(localList1.get(paramInt));
                this.titles.add(paramString);
                paramString = new ArrayList();
                this.companyGroups.add(paramString);
                paramString = new ArrayList();
                this.companyUsers.add(paramString);
                paramInt += 1;
            }
            this.adapter = new CompanyExListAdapter(this, this.titles, this.companyGroups, this.companyUsers);
            this.listview.setAdapter(this.adapter);
            paramInt = 0;
            while (paramInt < i)
            {
                getData(paramInt, ((CompanyGroup)localList1.get(paramInt)).getId());
                this.listview.expandGroup(paramInt);
                paramInt += 1;
            }
        }
        ((ArrayList)this.companyGroups.get(paramInt)).clear();
        ((ArrayList)this.companyGroups.get(paramInt)).addAll(localList1);
        ((ArrayList)this.companyUsers.get(paramInt)).clear();
        ((ArrayList)this.companyUsers.get(paramInt)).addAll(localList2);
        this.adapter.notifyDataSetChanged();
    }

    public void handleBundle(Bundle paramBundle) {}

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleClickCompanyGroup(ClickCompanyGroupEvent paramClickCompanyGroupEvent)
    {
        CompanyGroup localCompanyGroup = paramClickCompanyGroupEvent.getCompanyGroup();
        int i = paramClickCompanyGroupEvent.getGroupIndex();
        ((ArrayList)this.titles.get(i)).add(localCompanyGroup);
        this.adapter.notifyDataSetChanged();
        getData(i, localCompanyGroup.getId());
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleClickCompanyTitle(ClickCompanyTitleEvent paramClickCompanyTitleEvent)
    {
        CompanyGroup localCompanyGroup = paramClickCompanyTitleEvent.getCompanyGroup();
        int j = paramClickCompanyTitleEvent.getGroupIndex();
        int k = ((ArrayList)this.titles.get(j)).indexOf(localCompanyGroup);
        int i = ((ArrayList)this.titles.get(j)).size();
        if ((k >= 0) && (k != i - 1))
        {
            while (k != i - 1)
            {
                ((ArrayList)this.titles.get(j)).remove(i - 1);
                i = ((ArrayList)this.titles.get(j)).size();
            }
            this.adapter.notifyDataSetChanged();
            getData(j, localCompanyGroup.getId());
        }
    }

    public void init()
    {
        EventBusUtil.register(this);
        setTitleText(2131689679);
        this.titles = new ArrayList();
        this.companyGroups = new ArrayList();
        this.companyUsers = new ArrayList();
        int i = UserSpUtil.getInstanse().getLastCompany();
        Applog.systemOut("------last----- " + i);
        ServiceInfo localServiceInfo = UserSpUtil.getInstanse().getServiceInfo();
        Applog.systemOut("------serviceInfo----- " + localServiceInfo.getCreated_at());
        if (localServiceInfo.getCreated_at() > i)
        {
            CompanyGroupDao.getInstance().deleteAllCompanyGroup();
            CompanyUserDao.getInstance().deleteAllCompanyUsers();
            UserSpUtil.getInstanse().setLastCompany(localServiceInfo.getCreated_at());
        }
        getData(-1, "0");
    }

    protected void setPresenter()
    {
        this.presenter = new CompanyListPresenter(this);
    }
}

