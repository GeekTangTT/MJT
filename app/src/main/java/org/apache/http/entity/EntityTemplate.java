package org.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Deprecated
public class EntityTemplate
        extends AbstractHttpEntity
{
    public EntityTemplate(ContentProducer paramContentProducer)
    {
        throw new RuntimeException("Stub!");
    }

    public void consumeContent()
            throws IOException
    {
        throw new RuntimeException("Stub!");
    }

    public InputStream getContent()
    {
        throw new RuntimeException("Stub!");
    }

    public long getContentLength()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isRepeatable()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isStreaming()
    {
        throw new RuntimeException("Stub!");
    }

    public void writeTo(OutputStream paramOutputStream)
            throws IOException
    {
        throw new RuntimeException("Stub!");
    }
}

