package org.apache.http.client;

import java.io.IOException;
import org.apache.http.protocol.HttpContext;

@Deprecated
public abstract interface HttpRequestRetryHandler
{
    public abstract boolean retryRequest(IOException paramIOException, int paramInt, HttpContext paramHttpContext);
}
