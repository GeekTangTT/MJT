package com.olym.mjt.module.main;

import com.olym.mjt.base.presenter.BasePresenter;
import com.olym.mjt.databean.event.UpdateUserInfoEvent;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.network.client.FastJsonBaseResponseCallback;
import com.olym.mjt.network.client.HttpsClient;
import com.olym.mjt.network.responsedata.UserInfoResponseBean;
import com.olym.mjt.utils.ValidateUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;

public class MJTMainPresenter
        extends BasePresenter
{
    private IMJTMainView imjtMainView;

    public MJTMainPresenter(IMJTMainView paramIMJTMainView)
    {
        this.imjtMainView = paramIMJTMainView;
    }

    public void destroy()
    {
        super.destroy();
    }

    public void init()
    {
        super.init();
    }

    public void updateNickName(String paramString)
    {
        if (ValidateUtil.validateNickname(paramString)) {
            HttpsClient.getInstanse().updateUserInfo(paramString, new FastJsonBaseResponseCallback()
            {
                public void onGetDataError()
                {
                    super.onGetDataError();
                }

                public void onGetDataSuccess(UserInfoResponseBean paramAnonymousUserInfoResponseBean)
                {
                    UserSpUtil.getInstanse().setLoginUser(paramAnonymousUserInfoResponseBean.getData());
                    MjtApplication.getInstance().setLoginUser(paramAnonymousUserInfoResponseBean.getData());
                    UpdateUserInfoEvent.post(new UpdateUserInfoEvent());
                }

                public void onNetworkError()
                {
                    super.onNetworkError();
                }
            });
        }
    }
}
