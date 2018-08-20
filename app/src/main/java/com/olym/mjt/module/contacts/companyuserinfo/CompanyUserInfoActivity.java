package com.olym.mjt.module.contacts.companyuserinfo;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.CompanyUser;
import com.olym.mjt.glide.GlideUtil;
import com.olym.mjt.module.calllog.CallItem;
import com.olym.mjt.module.contacts.contactinfo.ContactsInfoAdapter;
import com.olym.mjt.utils.AvatarHelper;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427365)
public class CompanyUserInfoActivity
        extends BaseTopbarActivity<CompanyUserInfoPresenter>
        implements ICompanyUserInfoView
{
    public static final String KEY_USER = "user";
    private static final int LOADER_ID = 1011;
    private ContactsInfoAdapter adapter;
    @ViewInject(2131230751)
    private TextView app_chat;
    @ViewInject(2131230752)
    private TextView app_sip;
    private CompanyUser companyUser;
    private ArrayList<CallItem> datas = new ArrayList();
    @ViewInject(2131231037)
    private CircleImageView iv_head;
    @ViewInject(2131231074)
    private ListView listview;
    @ViewInject(2131231082)
    private View ll_call;
    @ViewInject(2131231122)
    private View ll_sip_call;
    @ViewInject(2131231124)
    private View ll_sys;
    @ViewInject(2131231125)
    private View ll_sys_call;
    @ViewInject(2131231126)
    private View ll_sys_chat;
    private View.OnClickListener onClickListener = new CompanyUserInfoActivity.3(this);
    @ViewInject(2131231428)
    private TextView tv1;
    @ViewInject(2131231482)
    private TextView tv_name;
    @ViewInject(2131231488)
    private TextView tv_phone;
    @ViewInject(2131231551)
    private View v_sys_1;
    @ViewInject(2131231552)
    private View v_sys_2;

    public void destroy()
    {
        getSupportLoaderManager().destroyLoader(1011);
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.companyUser = ((CompanyUser)paramBundle.getSerializable("user"));
    }

    public void init()
    {
        setTitleText(getResources().getString(2131690116));
        if (ChannelUtil.currentChannel == 102)
        {
            this.app_chat.setText(getResources().getString(2131690108));
            this.app_sip.setText(getResources().getString(2131690112));
            this.ll_sys_call.setVisibility(8);
            this.ll_sys_chat.setVisibility(8);
        }
        GlideUtil.loadUserIcon(this, AvatarHelper.getAvatarUrl(this.companyUser.getTigase_id(), UserSpUtil.getInstanse().getIBCDomain(), false), this.iv_head);
        new Handler().postDelayed(new CompanyUserInfoActivity.1(this), 1000L);
        this.tv1.setText(UserSpUtil.getInstanse().getUserDomain());
        this.tv_name.setText(this.companyUser.getName());
        if (this.companyUser.getHidden() != 1) {
            this.tv_phone.setText(this.companyUser.getUser());
        }
        for (;;)
        {
            this.ll_sip_call.setOnClickListener(this.onClickListener);
            this.ll_sys_call.setOnClickListener(this.onClickListener);
            this.ll_sys_chat.setOnClickListener(this.onClickListener);
            this.adapter = new ContactsInfoAdapter(this, this.datas);
            this.listview.setAdapter(this.adapter);
            getSupportLoaderManager().initLoader(1011, null, new CompanyUserInfoActivity.2(this));
            return;
            this.v_sys_1.setVisibility(8);
            this.ll_sys.setVisibility(8);
            this.v_sys_2.setVisibility(8);
        }
    }

    protected void onResume()
    {
        super.onResume();
    }

    protected void setPresenter()
    {
        this.presenter = new CompanyUserInfoPresenter(this);
    }
}

