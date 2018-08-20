package com.olym.mjt.module.settings.keeplivesettings;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.utils.RomUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427389)
public class KeepLiveSettingsActivity
        extends BaseTopbarActivity<KeepLiveSettingsPresenter>
        implements IKeepLiveSettingsView
{
    private JumpUtils jumpUtils;
    private View.OnClickListener onClickListener = new KeepLiveSettingsActivity.1(this);
    @ViewInject(2131231277)
    private View rl_4g;
    @ViewInject(2131231280)
    private View rl_battery;
    @ViewInject(2131231281)
    private View rl_boot;
    @ViewInject(2131231283)
    private View rl_miui_settings;
    @ViewInject(2131231284)
    private View rl_protect;
    @ViewInject(2131231442)
    private TextView tv_content;
    @ViewInject(2131231445)
    private TextView tv_content_3;

    private void showFloatView(int paramInt)
    {
        new Handler().postDelayed(new KeepLiveSettingsActivity.2(this, paramInt), 200L);
    }

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(getResources().getString(2131690120));
        setBackText(getResources().getString(2131689915));
        this.jumpUtils = new JumpUtils();
        String str = getResources().getString(2131689598);
        SpannableString localSpannableString = new SpannableString(getResources().getString(2131689874) + str + getResources().getString(2131689875));
        int i = localSpannableString.toString().indexOf(str);
        localSpannableString.setSpan(new ForegroundColorSpan(getResources().getColor(2131034208)), i, str.length() + i, 33);
        this.tv_content.setText(localSpannableString);
        this.rl_boot.setOnClickListener(this.onClickListener);
        this.rl_battery.setOnClickListener(this.onClickListener);
        this.rl_protect.setOnClickListener(this.onClickListener);
        this.rl_4g.setOnClickListener(this.onClickListener);
        this.rl_miui_settings.setOnClickListener(this.onClickListener);
        if (RomUtil.isLockedClear()) {
            this.tv_content_3.setText(getResources().getString(2131689677));
        }
        if (RomUtil.isGotoLockedShow())
        {
            this.rl_miui_settings.setVisibility(0);
            return;
        }
        this.rl_miui_settings.setVisibility(8);
    }

    protected void setPresenter()
    {
        this.presenter = new KeepLiveSettingsPresenter(this);
    }
}

