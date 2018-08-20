package com.olym.mjt.glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpStreamFetcher
        implements DataFetcher<InputStream>
{
    private final OkHttpClient client;
    private ResponseBody responseBody;
    private InputStream stream;
    private final GlideUrl url;

    public OkHttpStreamFetcher(OkHttpClient paramOkHttpClient, GlideUrl paramGlideUrl)
    {
        this.client = paramOkHttpClient;
        this.url = paramGlideUrl;
    }

    public void cancel() {}

    public void cleanup()
    {
        if (this.stream != null) {}
        try
        {
            this.stream.close();
            if (this.responseBody != null) {
                this.responseBody.close();
            }
            return;
        }
        catch (IOException localIOException)
        {
            for (;;) {}
        }
    }

    public String getId()
    {
        return this.url.getCacheKey();
    }

    public InputStream loadData(Priority paramPriority)
            throws Exception
    {
        paramPriority = new Request.Builder().url(this.url.toStringUrl());
        Iterator localIterator = this.url.getHeaders().entrySet().iterator();
        while (localIterator.hasNext())
        {
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            paramPriority.addHeader((String)localEntry.getKey(), (String)localEntry.getValue());
        }
        paramPriority = paramPriority.build();
        paramPriority = this.client.newCall(paramPriority).execute();
        this.responseBody = paramPriority.body();
        if (!paramPriority.isSuccessful()) {
            throw new IOException("Request failed with code: " + paramPriority.code());
        }
        long l = this.responseBody.contentLength();
        this.stream = ContentLengthInputStream.obtain(this.responseBody.byteStream(), l);
        return this.stream;
    }
}
