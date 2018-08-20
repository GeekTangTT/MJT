package com.olym.mjt.module.gallery.clipphoto;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.module.gallery.PhotoUtils;
import com.olym.mjt.utils.BitmapUtil;
import com.olym.mjt.widget.ClipView;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427363)
public class ClipPhotoActivity
        extends BaseTopbarActivity<ClipPhotoPresenter>
        implements IClipPhotoView
{
    private static final int DRAG = 1;
    public static final String KEY_DATA = "data";
    private static final int NONE = 0;
    private static final int ZOOM = 2;
    private int action;
    private Bitmap bitmap;
    @ViewInject(2131231030)
    private ClipView iv_clip;
    @ViewInject(2131231058)
    private ImageView iv_src;
    private File mTakePhotoFile;
    private Uri mTakePhotoUri;
    private Matrix matrix = new Matrix();
    private PointF mid = new PointF();
    private int mode = 0;
    private float oldDist = 1.0F;
    private String path;
    private Matrix savedMatrix = new Matrix();
    private PointF start = new PointF();
    @ViewInject(2131231486)
    private TextView tv_ok;

    private void generateUriAndReturn()
    {
        Bitmap localBitmap = getZoomedCropBitmap();
        if (localBitmap == null) {
            return;
        }
        File localFile = new File(PhotoUtils.getTempFilePath(this), "cliped_" + System.currentTimeMillis() + ".jpg");
        Object localObject4 = Uri.fromFile(localFile);
        Object localObject3;
        Object localObject1;
        if (localObject4 != null)
        {
            localObject3 = null;
            localObject1 = null;
        }
        for (;;)
        {
            try
            {
                localObject4 = getContentResolver().openOutputStream((Uri)localObject4);
                if (localObject4 != null)
                {
                    localObject1 = localObject4;
                    localObject3 = localObject4;
                    localBitmap.compress(Bitmap.CompressFormat.JPEG, 90, (OutputStream)localObject4);
                }
            }
            catch (IOException localIOException4)
            {
                localObject3 = localIOException1;
                LogFinalUtils.logForException(localIOException4);
                if (localIOException1 == null) {
                    continue;
                }
                try
                {
                    localIOException1.close();
                }
                catch (IOException localIOException2)
                {
                    LogFinalUtils.logForException(localIOException2);
                    localIOException2.printStackTrace();
                }
                continue;
            }
            finally
            {
                if (localObject3 == null) {
                    break label184;
                }
            }
            try
            {
                ((OutputStream)localObject4).close();
                localObject1 = new Intent();
                ((Intent)localObject1).putExtra("result", localFile.getAbsolutePath());
                setResult(-1, (Intent)localObject1);
                finish();
                return;
            }
            catch (IOException localIOException1)
            {
                LogFinalUtils.logForException(localIOException1);
                localIOException1.printStackTrace();
            }
        }
        try
        {
            ((OutputStream)localObject3).close();
            label184:
            throw ((Throwable)localObject2);
        }
        catch (IOException localIOException3)
        {
            for (;;)
            {
                LogFinalUtils.logForException(localIOException3);
                localIOException3.printStackTrace();
            }
        }
    }

    private Bitmap getZoomedCropBitmap()
    {
        this.iv_src.setDrawingCacheEnabled(true);
        this.iv_src.buildDrawingCache();
        Object localObject2 = this.iv_clip.getClipRect();
        Object localObject1 = null;
        Object localObject3 = null;
        try
        {
            localObject2 = Bitmap.createBitmap(this.iv_src.getDrawingCache(), ((Rect)localObject2).left, ((Rect)localObject2).top, ((Rect)localObject2).width(), ((Rect)localObject2).height());
            localObject1 = localObject2;
            Bitmap localBitmap = BitmapUtil.zoomBitmap((Bitmap)localObject2, 200, 200);
            localObject3 = localBitmap;
            localObject1 = localObject2;
        }
        catch (Exception localException)
        {
            for (;;)
            {
                LogFinalUtils.logForException(localException);
                localException.printStackTrace();
            }
        }
        if (localObject1 != null) {
            ((Bitmap)localObject1).recycle();
        }
        this.iv_src.destroyDrawingCache();
        return (Bitmap)localObject3;
    }

    private void initSrcPic()
    {
        this.bitmap = BitmapUtil.decodeSampledBitmap(this.path, 1080, 1920);
        if (this.bitmap == null) {
            return;
        }
        float f3;
        if (this.bitmap.getWidth() > this.bitmap.getHeight())
        {
            float f2 = this.iv_src.getWidth() / this.bitmap.getWidth();
            f3 = this.iv_clip.getClipRect().height() / this.bitmap.getHeight();
            f1 = f2;
            if (f2 >= f3) {}
        }
        for (float f1 = f3;; f1 = this.iv_src.getWidth() / 2.0F / this.bitmap.getWidth())
        {
            this.matrix.postScale(f1, f1);
            int i = this.iv_src.getWidth() / 2;
            int j = this.iv_src.getHeight() / 2;
            int k = (int)(this.bitmap.getWidth() * f1 / 2.0F);
            int m = (int)(this.bitmap.getHeight() * f1 / 2.0F);
            this.matrix.postTranslate(i - k, j - m);
            this.iv_src.setScaleType(ImageView.ScaleType.MATRIX);
            this.iv_src.setImageMatrix(this.matrix);
            this.iv_src.setImageBitmap(this.bitmap);
            return;
        }
    }

    private void midPoint(PointF paramPointF, MotionEvent paramMotionEvent)
    {
        float f1 = paramMotionEvent.getX(0);
        float f2 = paramMotionEvent.getX(1);
        float f3 = paramMotionEvent.getY(0);
        float f4 = paramMotionEvent.getY(1);
        paramPointF.set((f1 + f2) / 2.0F, (f3 + f4) / 2.0F);
    }

    private float spacing(MotionEvent paramMotionEvent)
    {
        float f1 = paramMotionEvent.getX(0) - paramMotionEvent.getX(1);
        float f2 = paramMotionEvent.getY(0) - paramMotionEvent.getY(1);
        return (float)Math.sqrt(f1 * f1 + f2 * f2);
    }

    private void takePhoto()
    {
        this.mTakePhotoFile = new File(PhotoUtils.getTempFilePath(this), "taked_" + System.currentTimeMillis() + ".jpg");
        this.mTakePhotoUri = Uri.fromFile(this.mTakePhotoFile);
        Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        localIntent.putExtra("output", this.mTakePhotoUri);
        startActivityForResult(localIntent, 1000);
    }

    public void destroy()
    {
        if (this.bitmap != null)
        {
            this.bitmap.recycle();
            this.bitmap = null;
        }
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.path = paramBundle.getString("data", null);
        this.action = paramBundle.getInt("action", 0);
    }

    public void init()
    {
        if (this.action == 0)
        {
            setTitleText(getResources().getString(2131689758));
            this.tv_ok.setOnClickListener(new ClipPhotoActivity.1(this));
            this.iv_clip.getViewTreeObserver().addOnGlobalLayoutListener(new ClipPhotoActivity.2(this));
            this.iv_src.setOnTouchListener(new ClipPhotoActivity.3(this));
            return;
        }
        takePhoto();
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if ((paramInt1 == 1000) && (paramInt2 == -1))
        {
            if (this.mTakePhotoFile.exists())
            {
                if (this.action == 1)
                {
                    paramIntent = new Intent();
                    paramIntent.putExtra("result", this.mTakePhotoFile.getAbsolutePath());
                    setResult(-1, paramIntent);
                    finish();
                }
                while (this.action != 2) {
                    return;
                }
                this.action = 0;
                this.path = this.mTakePhotoFile.getAbsolutePath();
                init();
                return;
            }
            finish();
            return;
        }
        finish();
    }

    protected void setPresenter()
    {
        this.presenter = new ClipPhotoPresenter(this);
    }
}
