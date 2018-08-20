package com.olym.mjt.glide;

import android.content.Context;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import java.io.InputStream;
import okhttp3.OkHttpClient;

public class OkHttpUrlLoader
        implements ModelLoader<GlideUrl, InputStream>
{
    private final OkHttpClient client;

    public OkHttpUrlLoader(OkHttpClient paramOkHttpClient)
    {
        this.client = paramOkHttpClient;
    }

    public DataFetcher<InputStream> getResourceFetcher(GlideUrl paramGlideUrl, int paramInt1, int paramInt2)
    {
        return new OkHttpStreamFetcher(this.client, paramGlideUrl);
    }

    public static class Factory
            implements ModelLoaderFactory<GlideUrl, InputStream>
    {
        private static volatile OkHttpClient internalClient;
        private OkHttpClient client;

        public Factory()
        {
            this(getInternalClient());
        }

        public Factory(OkHttpClient paramOkHttpClient)
        {
            this.client = paramOkHttpClient;
        }

        private static OkHttpClient getInternalClient()
        {
            if (internalClient == null) {}
            try
            {
                if (internalClient == null) {
                    internalClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
                }
                return internalClient;
            }
            finally {}
        }

        public ModelLoader<GlideUrl, InputStream> build(Context paramContext, GenericLoaderFactory paramGenericLoaderFactory)
        {
            return new OkHttpUrlLoader(this.client);
        }

        public void teardown() {}
    }
}
