package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;

@Deprecated
public class BasicEofSensorWatcher
        implements EofSensorWatcher
{
    protected boolean attemptReuse;
    protected ManagedClientConnection managedConn;

    public BasicEofSensorWatcher(ManagedClientConnection paramManagedClientConnection, boolean paramBoolean)
    {
        throw new RuntimeException("Stub!");
    }

    public boolean eofDetected(InputStream paramInputStream)
            throws IOException
    {
        throw new RuntimeException("Stub!");
    }

    public boolean streamAbort(InputStream paramInputStream)
            throws IOException
    {
        throw new RuntimeException("Stub!");
    }

    public boolean streamClosed(InputStream paramInputStream)
            throws IOException
    {
        throw new RuntimeException("Stub!");
    }
}
