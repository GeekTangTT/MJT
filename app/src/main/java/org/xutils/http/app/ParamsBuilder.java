package org.xutils.http.app;

import javax.net.ssl.SSLSocketFactory;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

public abstract interface ParamsBuilder
{
    public abstract String buildCacheKey(RequestParams paramRequestParams, String[] paramArrayOfString);

    public abstract void buildParams(RequestParams paramRequestParams);

    public abstract void buildSign(RequestParams paramRequestParams, String[] paramArrayOfString);

    public abstract String buildUri(HttpRequest paramHttpRequest);

    public abstract SSLSocketFactory getSSLSocketFactory();
}
