package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;
import java.io.File;

public class DownLoadApkEvent
{
    public static final int TYPE_APK_ERROR = 3;
    public static final int TYPE_ERROR = 1;
    public static final int TYPE_PROGRESS = 2;
    public static final int TYPE_SUCCESS = 0;
    private File file;
    private float progress;
    private int type;

    public DownLoadApkEvent(int paramInt, float paramFloat, File paramFile)
    {
        this.type = paramInt;
        this.progress = paramFloat;
        this.file = paramFile;
    }

    public static void post(DownLoadApkEvent paramDownLoadApkEvent)
    {
        EventBusUtil.post(paramDownLoadApkEvent);
    }

    public File getFile()
    {
        return this.file;
    }

    public float getProgress()
    {
        return this.progress;
    }

    public int getType()
    {
        return this.type;
    }

    public void setFile(File paramFile)
    {
        this.file = paramFile;
    }

    public void setProgress(float paramFloat)
    {
        this.progress = paramFloat;
    }

    public void setType(int paramInt)
    {
        this.type = paramInt;
    }
}
