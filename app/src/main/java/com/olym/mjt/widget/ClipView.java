package com.olym.mjt.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

public class ClipView
        extends View
{
    private static final int LAYER_FLAGS = 31;
    private Paint borderPaint = new Paint();
    private int clipBorderWidth = 2;
    int height;
    private Paint paint = new Paint();
    private float radiusWidthRatio = 0.22222222F;
    int width;
    private Xfermode xfermode;

    public ClipView(Context paramContext)
    {
        this(paramContext, null);
    }

    public ClipView(Context paramContext, AttributeSet paramAttributeSet)
    {
        this(paramContext, paramAttributeSet, 0);
    }

    public ClipView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        this.paint.setAntiAlias(true);
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setColor(-1);
        this.borderPaint.setStrokeWidth(this.clipBorderWidth);
        this.borderPaint.setAntiAlias(true);
        this.xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    public Rect getClipRect()
    {
        Rect localRect = new Rect();
        localRect.left = ((int)(this.width / 2 - this.width * this.radiusWidthRatio));
        localRect.right = ((int)(this.width / 2 + this.width * this.radiusWidthRatio));
        localRect.top = ((int)(this.height / 2 - this.width * this.radiusWidthRatio));
        localRect.bottom = ((int)(this.height / 2 + this.width * this.radiusWidthRatio));
        return localRect;
    }

    protected void onDraw(Canvas paramCanvas)
    {
        super.onDraw(paramCanvas);
        this.width = getWidth();
        this.height = getHeight();
        paramCanvas.saveLayer(0.0F, 0.0F, this.width, this.height, null, 31);
        paramCanvas.drawColor(Color.parseColor("#a8000000"));
        this.paint.setXfermode(this.xfermode);
        paramCanvas.drawCircle(this.width / 2, this.height / 2, this.width * this.radiusWidthRatio, this.paint);
        paramCanvas.drawCircle(this.width / 2, this.height / 2, this.width * this.radiusWidthRatio + this.clipBorderWidth, this.borderPaint);
        paramCanvas.restore();
    }
}
