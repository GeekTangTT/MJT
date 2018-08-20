package com.olym.mjt.glide;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GifTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.olym.mjt.module.MjtApplication;

public class GlideUtil
{
    public static void GuideClearDiskCache(Context paramContext)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                Glide.get(this.val$mContext).clearDiskCache();
            }
        }).start();
    }

    public static void GuideClearMemory(Context paramContext)
    {
        Glide.get(paramContext).clearMemory();
    }

    public static void loadImageView(Context paramContext, String paramString, ImageView paramImageView)
    {
        Glide.with(paramContext).load(paramString).into(paramImageView);
    }

    public static void loadImageViewAnim(Context paramContext, String paramString, int paramInt, ImageView paramImageView)
    {
        Glide.with(paramContext).load(paramString).animate(paramInt).into(paramImageView);
    }

    public static void loadImageViewCache(Context paramContext, String paramString, ImageView paramImageView)
    {
        Glide.with(paramContext).load(paramString).skipMemoryCache(true).into(paramImageView);
    }

    public static void loadImageViewContent(Context paramContext, String paramString, SimpleTarget<GlideDrawable> paramSimpleTarget)
    {
        Glide.with(paramContext).load(paramString).centerCrop().into(paramSimpleTarget);
    }

    public static void loadImageViewCrop(Context paramContext, String paramString, ImageView paramImageView)
    {
        Glide.with(paramContext).load(paramString).centerCrop().into(paramImageView);
    }

    public static void loadImageViewDiskCache(Context paramContext, String paramString, ImageView paramImageView)
    {
        Glide.with(paramContext).load(paramString).diskCacheStrategy(DiskCacheStrategy.ALL).into(paramImageView);
    }

    public static void loadImageViewDynamicGif(Context paramContext, String paramString, ImageView paramImageView)
    {
        Glide.with(paramContext).load(paramString).asGif().into(paramImageView);
    }

    public static void loadImageViewListener(Context paramContext, String paramString, ImageView paramImageView, RequestListener<String, GlideDrawable> paramRequestListener)
    {
        Glide.with(paramContext).load(paramString).listener(paramRequestListener).into(paramImageView);
    }

    public static void loadImageViewLoding(Context paramContext, String paramString, ImageView paramImageView, int paramInt1, int paramInt2)
    {
        Glide.with(paramContext).load(paramString).placeholder(paramInt1).error(paramInt2).into(paramImageView);
    }

    public static void loadImageViewLodingSize(Context paramContext, String paramString, int paramInt1, int paramInt2, ImageView paramImageView, int paramInt3, int paramInt4)
    {
        Glide.with(paramContext).load(paramString).override(paramInt1, paramInt2).placeholder(paramInt3).error(paramInt4).into(paramImageView);
    }

    public static void loadImageViewPriority(Context paramContext, String paramString, ImageView paramImageView)
    {
        Glide.with(paramContext).load(paramString).priority(Priority.NORMAL).into(paramImageView);
    }

    public static void loadImageViewSize(Context paramContext, String paramString, int paramInt1, int paramInt2, ImageView paramImageView)
    {
        Glide.with(paramContext).load(paramString).override(paramInt1, paramInt2).into(paramImageView);
    }

    public static void loadImageViewStaticGif(Context paramContext, String paramString, ImageView paramImageView)
    {
        Glide.with(paramContext).load(paramString).asBitmap().into(paramImageView);
    }

    public static void loadImageViewThumbnail(Context paramContext, String paramString, ImageView paramImageView)
    {
        Glide.with(paramContext).load(paramString).thumbnail(0.1F).into(paramImageView);
    }

    public static void loadUserIcon(Context paramContext, String paramString, ImageView paramImageView)
    {
        Glide.with(MjtApplication.getInstance().getApplicationContext()).load(paramString).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(2131165654).error(2131165654).dontAnimate().into(paramImageView);
    }

    public static void loadUserIconNew(Context paramContext, String paramString, SimpleTarget paramSimpleTarget)
    {
        Glide.with(MjtApplication.getInstance().getApplicationContext()).load(paramString).diskCacheStrategy(DiskCacheStrategy.ALL).error(2131165654).dontAnimate().into(paramSimpleTarget);
    }
}
