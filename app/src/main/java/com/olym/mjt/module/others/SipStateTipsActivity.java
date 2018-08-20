package com.olym.mjt.module.others;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.olym.mjt.base.activity.ForbidScreenShotActivity;
import com.olym.mjt.utils.StatusBarUtil;
import org.xutils.ViewInjector;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(2131427556)
public class SipStateTipsActivity
        extends ForbidScreenShotActivity
{
    @ViewInject(2131231454)
    private TextView tv_dialog_content;
    @ViewInject(2131231456)
    private TextView tv_dialog_right_button;
    @ViewInject(2131231562)
    private View view_root;

    public void finish()
    {
        super.finish();
        overridePendingTransition(0, 0);
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        StatusBarUtil.setStatusBar(this);
        x.view().inject(this);
        this.tv_dialog_right_button.setOnClickListener(new SipStateTipsActivity.1(this));
        this.view_root.setOnClickListener(new SipStateTipsActivity.2(this));
    }
}
