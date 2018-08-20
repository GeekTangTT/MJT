package com.olym.mjt.module.settings.aboutus;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.DeviceInfoUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427354)
public class AboutUsActivity
        extends BaseTopbarActivity<AboutUsPresenter>
        implements IAboutUsView
{
    @ViewInject(2131231039)
    private ImageView iv_icon;
    private long[] mHits = null;
    @ViewInject(2131231429)
    private TextView tv_1;
    @ViewInject(2131231430)
    private TextView tv_2;
    @ViewInject(2131231442)
    private TextView tv_content;

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(getResources().getString(2131689578));
        this.tv_content.setText(getResources().getString(2131689598) + " " + DeviceInfoUtil.getVersionName(this));
        if ((ChannelUtil.currentChannel == 100) && (DeviceInfoUtil.isZh(this)))
        {
            this.tv_2.setVisibility(0);
            this.tv_1.setVisibility(0);
        }
        for (;;)
        {
            this.iv_icon.setImageResource(ChannelUtil.icon_about);
            return;
            this.tv_1.setVisibility(8);
            this.tv_2.setVisibility(8);
        }
    }

    protected void setPresenter()
    {
        this.presenter = new AboutUsPresenter(this);
    }
}

