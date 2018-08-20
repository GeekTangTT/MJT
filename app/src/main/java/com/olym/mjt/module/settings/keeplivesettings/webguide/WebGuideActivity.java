package com.olym.mjt.module.settings.keeplivesettings.webguide;

import android.content.res.Resources;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.utils.RomUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427442)
public class WebGuideActivity
        extends BaseTopbarActivity<WebGuidePresenter>
        implements IWebGuideView
{
    private static final String url_all = "https://myibc.net/d/t/help.html";
    private static final String url_lenovo = "https://myibc.net/d/t/help.html?model=lenovo";
    private static final String url_mi = "https://myibc.net/d/t/help.html?model=mi";
    private static final String url_oneplus = "https://myibc.net/d/t/help.html?model=oneplus";
    private static final String url_oppo = "https://myibc.net/d/t/help.html?model=oppo";
    private static final String url_sanxin = "https://myibc.net/d/t/help.html?model=sanxin";
    private static final String url_vivo = "https://myibc.net/d/t/help.html?model=vivo";
    @ViewInject(2131231571)
    private WebView webview;

    public void destroy() {}

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        setTitleText(getResources().getString(2131690120));
        WebSettings localWebSettings = this.webview.getSettings();
        localWebSettings.setUseWideViewPort(true);
        localWebSettings.setLoadWithOverviewMode(true);
        localWebSettings.setSupportZoom(false);
        localWebSettings.setBuiltInZoomControls(true);
        localWebSettings.setDisplayZoomControls(false);
        localWebSettings.setCacheMode(1);
        localWebSettings.setAllowFileAccess(false);
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        localWebSettings.setLoadsImagesAutomatically(true);
        localWebSettings.setDefaultTextEncodingName("utf-8");
        if (RomUtil.isVivo())
        {
            this.webview.loadUrl("https://myibc.net/d/t/help.html?model=vivo");
            return;
        }
        if (RomUtil.isOppo())
        {
            this.webview.loadUrl("https://myibc.net/d/t/help.html?model=oppo");
            return;
        }
        if (RomUtil.isSamsung())
        {
            this.webview.loadUrl("https://myibc.net/d/t/help.html?model=sanxin");
            return;
        }
        if (RomUtil.isMiui())
        {
            this.webview.loadUrl("https://myibc.net/d/t/help.html?model=mi");
            return;
        }
        this.webview.loadUrl("https://myibc.net/d/t/help.html");
    }

    protected void setPresenter()
    {
        this.presenter = new WebGuidePresenter(this);
    }
}

