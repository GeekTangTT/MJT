package com.olym.mjt.utils.sharedpreferencesutils;

import android.util.Base64;
import com.alibaba.fastjson.JSON;
import com.olym.mjt.databean.bean.UserDomainBean;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.network.client.FastJsonObjectCallback;
import com.olym.mjt.network.client.HttpsClient;
import com.olym.mjt.network.responsedata.SipResponseBean;
import com.olym.mjt.utils.Applog;
import java.util.ArrayList;
import okhttp3.Call;

public class IBCSpUtil
{
    private static final String NAME = "ibc";
    private static volatile IBCSpUtil instanse;
    private SPUtils spUtils = new SPUtils("ibc");

    public static IBCSpUtil getInstanse()
    {
        if (instanse == null) {}
        try
        {
            if (instanse == null) {
                instanse = new IBCSpUtil();
            }
            return instanse;
        }
        finally {}
    }

    public void clearKey()
    {
        this.spUtils.clear();
    }

    public void getDomainsFromUser(final String paramString, final OnGetDomainsDoneListener paramOnGetDomainsDoneListener)
    {
        Applog.systemOut("-----getDomainsFromUser----- ");
        Applog.info("-----getDomainsFromUser----- ");
        Applog.info_importance("----phone--- " + paramString);
        Object localObject = this.spUtils.getString(paramString, null);
        if (localObject != null)
        {
            Applog.systemOut("----������������ domains----");
            Applog.info("----������������ domains----");
            localObject = (ArrayList)JSON.parseArray(EngineUtils.getInstance().decryptCms(UserSpUtil.getInstanse().getPhone(), (String)localObject), UserDomainBean.class);
            if (localObject == null)
            {
                setUserDomains(paramString, null);
                paramOnGetDomainsDoneListener.onGetDomainsFail();
                return;
            }
            paramOnGetDomainsDoneListener.onGetDomainsSuccess((ArrayList)localObject);
            return;
        }
        Applog.systemOut("----������������ domains----");
        Applog.info("----������������ domains----");
        HttpsClient.getInstanse().getUserDomain(paramString, new FastJsonObjectCallback()
        {
            public void onError(Call paramAnonymousCall, Exception paramAnonymousException, int paramAnonymousInt)
            {
                Applog.info("----������������ domains--onError--");
                paramOnGetDomainsDoneListener.onGetDomainsFail();
            }

            public void onResponse(SipResponseBean paramAnonymousSipResponseBean, int paramAnonymousInt)
            {
                if (paramAnonymousSipResponseBean.getCode() == 0)
                {
                    paramAnonymousSipResponseBean = paramAnonymousSipResponseBean.getData();
                    IBCSpUtil.this.setUserDomains(paramString, paramAnonymousSipResponseBean);
                    IBCSpUtil.this.getDomainsFromUser(paramString, paramOnGetDomainsDoneListener);
                    return;
                }
                paramOnGetDomainsDoneListener.onGetDomainsFail();
                Applog.info("----������������ domains--response.getCode() != 0----");
            }
        });
    }

    public String getIBCFromDomain(String paramString1, String paramString2)
    {
        String str = this.spUtils.getString(paramString1, null);
        if (str != null) {
            return new String(Base64.decode(str, 0));
        }
        try
        {
            str = HttpsClient.getInstanse().getIBCFromDomain(paramString1, paramString2);
            if (str != null)
            {
                setDomainIBC(paramString1, str);
                return getIBCFromDomain(paramString1, paramString2);
            }
        }
        catch (Exception paramString1)
        {
            paramString1.printStackTrace();
            return "";
        }
        return "";
    }

    public void setDomainIBC(String paramString1, String paramString2)
    {
        this.spUtils.put(paramString1, paramString2);
    }

    public void setUserDomains(String paramString1, String paramString2)
    {
        this.spUtils.put(paramString1, paramString2);
    }

    public static abstract interface OnGetDomainsDoneListener
    {
        public abstract void onGetDomainsFail();

        public abstract void onGetDomainsSuccess(ArrayList<UserDomainBean> paramArrayList);
    }
}
