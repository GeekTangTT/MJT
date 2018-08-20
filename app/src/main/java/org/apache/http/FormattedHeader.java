package org.apache.http;

import org.apache.http.util.CharArrayBuffer;

@Deprecated
public abstract interface FormattedHeader
        extends Header
{
    public abstract CharArrayBuffer getBuffer();

    public abstract int getValuePos();
}

