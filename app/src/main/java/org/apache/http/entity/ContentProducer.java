package org.apache.http.entity;

import java.io.IOException;
import java.io.OutputStream;

@Deprecated
public abstract interface ContentProducer
{
    public abstract void writeTo(OutputStream paramOutputStream)
            throws IOException;
}
