package com.olym.mjt.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.EditText;
import com.zhy.autolayout.utils.AutoUtils;

public class CodeView
        extends EditText
{
    public static final int SHOW_TYPE_PASSWORD = 2;
    public static final int SHOW_TYPE_WORD = 1;
    private float board = 4.0F;
    private int borderColor;
    private float borderWidth;
    private String code;
    private int codeColor;
    private int dividerColor;
    private float dividerWidth;
    private Paint gridPaint;
    private float gridWidth = 66.0F;
    private int length;
    private Listener listener;
    private Paint paint;
    private float pointRadius;
    private int showType;
    private float spaceWidth;
    private float textSize;

    public CodeView(Context paramContext)
    {
        super(paramContext);
        init(null);
    }

    public CodeView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        init(paramAttributeSet);
    }

    private void drawGrid(Canvas paramCanvas)
    {
        int i = 0;
        while (i < this.length)
        {
            float f = this.board + i * (this.gridWidth + this.spaceWidth + 2.0F * this.board);
            paramCanvas.drawRect(f, this.board, f + this.gridWidth, this.gridWidth, this.gridPaint);
            i += 1;
        }
    }

    private void drawPoint(Canvas paramCanvas)
    {
        if (this.pointRadius > 0.0F)
        {
            this.paint.setColor(this.codeColor);
            int i = 0;
            while (i < this.code.length())
            {
                float f1 = this.board;
                float f2 = i;
                float f3 = this.gridWidth;
                float f4 = this.spaceWidth;
                float f5 = this.board;
                paramCanvas.drawCircle(this.gridWidth / 2.0F + (f1 + f2 * (f3 + f4 + f5 * 2.0F)), getHeight() / 2, this.pointRadius, this.paint);
                i += 1;
            }
        }
    }

    private void drawValue(Canvas paramCanvas)
    {
        if (this.pointRadius > 0.0F)
        {
            this.paint.setColor(this.codeColor);
            this.paint.setTextSize(this.textSize);
            int i = 0;
            while (i < this.code.length())
            {
                float f1 = this.board;
                float f2 = i;
                float f3 = this.gridWidth;
                float f4 = this.spaceWidth;
                float f5 = this.board;
                paramCanvas.drawText(this.code.charAt(i) + "", this.gridWidth / 2.0F + (f1 + f2 * (f3 + f4 + f5 * 2.0F)), getTextBaseLine(0.0F, getHeight(), this.paint), this.paint);
                i += 1;
            }
        }
    }

    private float getTextBaseLine(float paramFloat1, float paramFloat2, Paint paramPaint)
    {
        paramPaint = paramPaint.getFontMetrics();
        return (paramFloat1 + paramFloat2 - paramPaint.bottom - paramPaint.top) / 2.0F;
    }

    private void init(AttributeSet paramAttributeSet)
    {
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setTextAlign(Paint.Align.CENTER);
        this.gridPaint = new Paint();
        this.gridPaint.setStyle(Paint.Style.STROKE);
        this.gridPaint.setStrokeWidth(this.board);
        this.gridPaint.setColor(Color.parseColor("#008EFF"));
        this.length = 6;
        this.borderColor = Color.parseColor("#E1E1E1");
        this.borderWidth = 1.0F;
        this.dividerColor = Color.parseColor("#E1E1E1");
        this.dividerWidth = 1.0F;
        this.code = "";
        this.codeColor = Color.parseColor("#000000");
        this.pointRadius = 16.0F;
        this.showType = 2;
        this.textSize = 0.0F;
        this.gridWidth = AutoUtils.getPercentWidthSizeBigger(62);
    }

    public void delete()
    {
        if (this.code.length() > 0)
        {
            this.code = this.code.substring(0, this.code.length() - 1);
            if (this.listener != null) {
                this.listener.onValueChanged(this.code);
            }
            invalidate();
        }
    }

    public int getBorderColor()
    {
        return this.borderColor;
    }

    public float getBorderWidth()
    {
        return this.borderWidth;
    }

    public String getCode()
    {
        return this.code;
    }

    public int getCodeColor()
    {
        return this.codeColor;
    }

    public int getDividerColor()
    {
        return this.dividerColor;
    }

    public float getDividerWidth()
    {
        return this.dividerWidth;
    }

    public int getLength()
    {
        return this.length;
    }

    public float getPointRadius()
    {
        return this.pointRadius;
    }

    public int getShowType()
    {
        return this.showType;
    }

    public float getTextSize()
    {
        return this.textSize;
    }

    public void input(String paramString)
    {
        if (this.code.length() < this.length)
        {
            this.code += paramString;
            if (this.listener != null)
            {
                this.listener.onValueChanged(this.code);
                if (this.code.length() == this.length) {
                    this.listener.onComplete(this.code);
                }
            }
            invalidate();
        }
    }

    protected void onDraw(Canvas paramCanvas)
    {
        super.onDraw(paramCanvas);
        drawGrid(paramCanvas);
        switch (this.showType)
        {
            default:
                drawValue(paramCanvas);
                return;
        }
        drawPoint(paramCanvas);
    }

    protected void onMeasure(int paramInt1, int paramInt2)
    {
        super.onMeasure(paramInt1, paramInt2);
        float f = getMeasuredWidth();
        if (this.textSize == 0.0F) {
            this.textSize = (this.gridWidth / 2.0F);
        }
        this.spaceWidth = (0.2F * (f - 6.0F * (this.gridWidth + this.board * 2.0F)));
        setMeasuredDimension((int)f, (int)(this.gridWidth + this.board * 2.0F));
    }

    public void setBorderColor(int paramInt)
    {
        this.borderColor = paramInt;
        invalidate();
    }

    public void setBorderWidth(float paramFloat)
    {
        this.borderWidth = paramFloat;
        invalidate();
    }

    public void setCode(String paramString)
    {
        this.code = paramString;
        invalidate();
    }

    public void setCodeColor(int paramInt)
    {
        this.codeColor = paramInt;
        invalidate();
    }

    public void setDividerColor(int paramInt)
    {
        this.dividerColor = paramInt;
        invalidate();
    }

    public void setDividerWidth(float paramFloat)
    {
        this.dividerWidth = paramFloat;
        invalidate();
    }

    public void setLength(int paramInt)
    {
        this.length = paramInt;
        invalidate();
    }

    public void setListener(Listener paramListener)
    {
        this.listener = paramListener;
    }

    public void setPointRadius(float paramFloat)
    {
        this.pointRadius = paramFloat;
        invalidate();
    }

    public void setShowType(int paramInt)
    {
        this.showType = paramInt;
        invalidate();
    }

    public void setTextSize(float paramFloat)
    {
        this.textSize = paramFloat;
        invalidate();
    }

    public static abstract interface Listener
    {
        public abstract void onComplete(String paramString);

        public abstract void onValueChanged(String paramString);
    }
}
