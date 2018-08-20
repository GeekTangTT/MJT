package com.olym.mjt.module.settings.serviceinfo;

import android.os.Bundle;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.ServiceInfo;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427433)
public class ServiceInfoInfoActivity
        extends BaseTopbarActivity<ServiceInfoPresenter>
        implements IServiceInfoView
{
    @ViewInject(2131231427)
    private TextView tv;

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(2131690126);
        ServiceInfo localServiceInfo = UserSpUtil.getInstanse().getServiceInfo();
        if (localServiceInfo != null) {
            this.tv.setText(localServiceInfo.toString());
        }
    }

    protected void setPresenter()
    {
        this.presenter = new ServiceInfoPresenter(this);
    }
}
