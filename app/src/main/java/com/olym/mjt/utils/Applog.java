package com.olym.mjt.utils;

import android.os.Environment;
import android.os.Process;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Applog
{
    private static Applog INSTANCE = null;
    private static final long MAX_FILE_SIZE = 26214400L;
    private File currentFile;
    private int currentIndex;
    private Writer mWriter;
    private SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");

    private Applog()
    {
        try
        {
            this.currentIndex = AppSpUtil.getInstanse().getWriteLogIndex();
            configLog(this.currentIndex);
            clearOldLogFiles();
            return;
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
    }

    private static void changeLogFile(int paramInt)
    {
        if (paramInt == 1) {}
        for (paramInt = 2;; paramInt = 1) {
            try
            {
                FileUtils.deleteFile(new File(getInstance().getFilePath(paramInt)));
                getInstance().configLog(paramInt);
                return;
            }
            catch (IOException localIOException)
            {
                localIOException.printStackTrace();
            }
        }
    }

    private void clearOldLogFiles()
    {
        if ("mounted".equals(Environment.getExternalStorageState())) {}
        for (String str = MjtApplication.getInstance().getExternalFilesDir(null).getParent();; str = MjtApplication.getInstance().getFilesDir().getParent())
        {
            FileUtils.deleteDir(new File(str + "/log"));
            return;
        }
    }

    private void configLog(int paramInt)
            throws IOException
    {
        AppSpUtil.getInstanse().setWriteLogIndex(paramInt);
        this.currentIndex = paramInt;
        this.currentFile = new File(getFilePath(paramInt));
        if (!this.currentFile.exists()) {
            this.currentFile.createNewFile();
        }
        try
        {
            if (this.mWriter != null) {
                this.mWriter.close();
            }
            this.mWriter = new BufferedWriter(new FileWriter(this.currentFile, true), 2048);
            if (this.mWriter == null) {
                return;
            }
            this.mWriter.write("===================" + this.sdf.format(new Date()) + "===================");
            this.mWriter.write("\r\n");
            this.mWriter.flush();
            return;
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
    }

    private String getFilePath(int paramInt)
    {
        String str = MjtApplication.getInstance().mLogDir;
        if (paramInt == 1) {
            return str + File.separator + "Applog1.txt";
        }
        return str + File.separator + "Applog2.txt";
    }

    public static Applog getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new Applog();
        }
        return INSTANCE;
    }

    public static void info(String paramString)
    {
        try
        {
            if (needChangeLogFile()) {
                changeLogFile(getInstance().currentIndex);
            }
            int i = Process.myPid();
            int j = Process.myTid();
            getInstance().writemsg(i + "-" + j + " " + paramString);
            return;
        }
        finally {}
    }

    public static void info_importance(String paramString) {}

    private static boolean needChangeLogFile()
    {
        return FileUtils.getFileLength(getInstance().currentFile) >= 26214400L;
    }

    public static void systemOut(String paramString) {}

    private void writemsg(String paramString)
    {
        try
        {
            if (this.mWriter == null) {
                return;
            }
            Object localObject = new Date();
            localObject = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)localObject);
            this.mWriter.write((String)localObject + "-");
            this.mWriter.write(paramString);
            this.mWriter.write("\r\n");
            this.mWriter.flush();
            return;
        }
        catch (IOException paramString)
        {
            paramString.printStackTrace();
        }
    }
}

