package org.apache.http.auth.params;

import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

@Deprecated
public class AuthParamBean
        extends HttpAbstractParamBean
{
    public AuthParamBean(HttpParams paramHttpParams)
    {
        super((HttpParams)null);
        throw new RuntimeException("Stub!");
    }

    public void setCredentialCharset(String paramString)
    {
        throw new RuntimeException("Stub!");
    }
}

