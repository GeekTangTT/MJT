package org.apache.http;

import org.apache.http.protocol.HttpContext;

@Deprecated
public abstract interface ConnectionReuseStrategy
{
    public abstract boolean keepAlive(HttpResponse paramHttpResponse, HttpContext paramHttpContext);
}

