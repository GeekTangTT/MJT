package com.olym.mjt.module.settings;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.bracelet.UnPairBraceletService;
import com.olym.mjt.bracelet.event.BraceletConnectSuccessEvent;
import com.olym.mjt.bracelet.event.UnPairbraceletSuccessEvent;
import com.olym.mjt.databean.event.LogoutEvent;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.pjsip.sip.event.SecureModeEvent;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.FileUtils;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.widget.dialog.LoadingDialog;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import com.suke.widget.SwitchButton;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427434)
public class SettingsActivity
        extends BaseTopbarActivity<SettingsPresenter>
        implements ISettingsView
{
    private TipsDialog braceletConnectTipsDialog;
    private TipsDialog braceletTipsDialog;
    @ViewInject(2131230807)
    private TextView cache_size;
    private TipsDialog clearDialog;
    @ViewInject(2131230872)
    private LinearLayout clear_cache;
    private String desc = "";
    @ViewInject(2131231075)
    private LinearLayout ll_about;
    @ViewInject(2131231100)
    private View ll_message_status;
    @ViewInject(2131231130)
    private LinearLayout ll_upload_log_group;
    @ViewInject(2131231132)
    private LinearLayout ll_voip;
    private LoadingDialog loadingDialog;
    private long[] mHits = null;
    @ViewInject(2131231221)
    private LinearLayout notification_settings;
    @ViewInject(2131231318)
    private SwitchButton sb_voip;
    @ViewInject(2131231356)
    private LinearLayout send_zip;
    private TipsDialog tipsDialog;
    @ViewInject(2131231425)
    private TextView topbar_title_tv;
    @ViewInject(2131231479)
    private TextView tv_logout;
    @ViewInject(2131231524)
    private View v_about;
    @ViewInject(2131231545)
    private View v_logout1;
    @ViewInject(2131231546)
    private View v_logout2;

    private void clearCache()
    {
        String str = MjtApplication.getInstance().mAppDir;
        this.loadingDialog.show();
        FileUtils.deleteDir(str);
        str = FileUtils.getDirSize(MjtApplication.getInstance().mAppDir);
        this.cache_size.setText(str);
        this.loadingDialog.hide();
    }

    private void initBraceletConnectTipsDialog()
    {
        if (this.braceletConnectTipsDialog != null) {
            return;
        }
        this.braceletConnectTipsDialog = new TipsDialog.Build(this).setCancelable(false).setContent("������������������������,���������������������������").setDialogClickListener(new SettingsActivity.10(this)).build();
    }

    private void initBraceletTipsDialog()
    {
        if (this.braceletTipsDialog == null) {
            this.braceletTipsDialog = new TipsDialog.Build(this).setCancelable(false).setContent("������������������������?(���������������������������)").setDialogClickListener(new SettingsActivity.11(this)).build();
        }
    }

    private void setAudoClose()
    {
        if (ChannelUtil.isUsedTEE)
        {
            Applog.info("-------setAudoClose------");
            Applog.systemOut("-------setAudoClose------");
            SecureModeEvent.post(new SecureModeEvent(false));
        }
    }

    private void setAudoOpen()
    {
        if (ChannelUtil.isUsedTEE)
        {
            Applog.info("-------setAudoOpen------");
            Applog.systemOut("-------setAudoOpen------");
            SecureModeEvent.post(new SecureModeEvent(true));
        }
    }

    public void destroy()
    {
        if ((this.loadingDialog != null) && (this.loadingDialog.isShowing())) {
            this.loadingDialog.dismiss();
        }
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleBraceletConnectSuccess(BraceletConnectSuccessEvent paramBraceletConnectSuccessEvent)
    {
        if (MjtApplication.getInstance().getLastActivity() == this)
        {
            Applog.systemOut("------handleBraceletConnectSuccess----- " + paramBraceletConnectSuccessEvent.isSuccess());
            if (!paramBraceletConnectSuccessEvent.isSuccess())
            {
                this.loadingDialog.hide();
                initBraceletConnectTipsDialog();
                this.braceletConnectTipsDialog.show();
            }
        }
    }

    public void handleBundle(Bundle paramBundle) {}

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleUnPairSuccess(UnPairbraceletSuccessEvent paramUnPairbraceletSuccessEvent)
    {
        this.loadingDialog.hide();
        ToastUtils.showShortToast("������������");
        AppSpUtil.getInstanse().setBoundBraceletId("");
        AppSpUtil.getInstanse().setBoundBracelet(false);
        finish();
        LogoutEvent.post(new LogoutEvent());
        stopService(new Intent(this, UnPairBraceletService.class));
    }

    public void init()
    {
        setTitleText(getResources().getString(2131690054));
        setBackText(getResources().getString(2131689915));
        if (ChannelUtil.isUsedTEE)
        {
            this.ll_about.setVisibility(8);
            this.v_about.setVisibility(8);
            this.v_logout1.setVisibility(8);
            this.tv_logout.setVisibility(8);
            this.v_logout2.setVisibility(8);
        }
        if (ChannelUtil.action_upload_log) {
            this.ll_upload_log_group.setVisibility(0);
        }
        for (;;)
        {
            this.tipsDialog = new TipsDialog.Build(this).setContent(getResources().getString(2131689725)).setDialogClickListener(new SettingsActivity.1(this)).build();
            this.clearDialog = new TipsDialog.Build(this).setContent(getResources().getString(2131689716)).setDialogClickListener(new SettingsActivity.2(this)).build();
            String str = FileUtils.getDirSize(MjtApplication.getInstance().mAppDir);
            this.cache_size.setText(str);
            this.clear_cache.setOnClickListener(new SettingsActivity.3(this));
            this.tv_logout.setOnClickListener(new SettingsActivity.4(this));
            this.ll_about.setOnClickListener(new SettingsActivity.5(this));
            this.loadingDialog = new LoadingDialog(this);
            this.send_zip.setOnClickListener(new SettingsActivity.6(this));
            this.notification_settings.setOnClickListener(new SettingsActivity.7(this));
            this.ll_message_status.setOnClickListener(new SettingsActivity.8(this));
            this.topbar_title_tv.setOnClickListener(new SettingsActivity.9(this));
            this.ll_voip.setVisibility(8);
            this.sb_voip.setVisibility(8);
            EventBusUtil.register(this);
            return;
            this.ll_upload_log_group.setVisibility(8);
        }
    }

    protected void setPresenter()
    {
        this.presenter = new SettingsPresenter(this);
    }
}
