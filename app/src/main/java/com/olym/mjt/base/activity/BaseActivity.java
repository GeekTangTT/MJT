package com.olym.mjt.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.main.MJTMainActivity;
import com.olym.mjt.module.user.login.LoginActivity;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.LoginHelper;
import org.xutils.ViewInjector;
import org.xutils.x;

public abstract class BaseActivity
        extends ForbidScreenShotActivity
        implements IBaseView
{
    private int enterAnim = 2130771998;
    private int exitAnim = 2130771999;

    protected void handleSavedInstanceState(Bundle paramBundle) {}

    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(this.enterAnim, this.exitAnim);
    }

    protected void onCreate(@Nullable Bundle paramBundle)
    {
        Applog.systemOut("-------appStatus------ " + MjtApplication.getInstance().isAppNormal);
        super.onCreate(paramBundle);
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            handleBundle(getIntent().getExtras());
        }
        x.view().inject(this);
        handleSavedInstanceState(paramBundle);
        init();
        if (!MjtApplication.getInstance().isAppNormal)
        {
            if (LoginHelper.prepareUser()) {
                startActivity(new Intent(this, MJTMainActivity.class));
            }
        }
        else {
            return;
        }
        startActivity(new Intent(this, LoginActivity.class));
    }

    protected void onDestroy()
    {
        super.onDestroy();
        destroy();
    }

    public void setAnim(int paramInt1, int paramInt2)
    {
        this.enterAnim = paramInt1;
        this.exitAnim = paramInt2;
    }
}

