package com.olym.mjt.glide;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import java.io.InputStream;

public class SimpleGlideModule
        implements GlideModule
{
    public void applyOptions(Context paramContext, GlideBuilder paramGlideBuilder)
    {
        paramGlideBuilder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        paramContext = new MemorySizeCalculator(paramContext);
        int j = paramContext.getMemoryCacheSize();
        int i = paramContext.getBitmapPoolSize();
        j = (int)(j * 0.8D);
        i = (int)(i * 0.8D);
        paramGlideBuilder.setMemoryCache(new LruResourceCache(j));
        paramGlideBuilder.setBitmapPool(new LruBitmapPool(i));
    }

    public void registerComponents(Context paramContext, Glide paramGlide)
    {
        paramGlide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
