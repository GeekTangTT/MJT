package com.olym.mjt.utils;

import android.os.Process;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class XZip
{
    public static List<File> GetFileList(String paramString, boolean paramBoolean1, boolean paramBoolean2)
            throws Exception
    {
        Log.v("XZip", "GetFileList(String)");
        ArrayList localArrayList = new ArrayList();
        paramString = new ZipInputStream(new FileInputStream(paramString));
        for (;;)
        {
            Object localObject = paramString.getNextEntry();
            if (localObject == null) {
                break;
            }
            String str = ((ZipEntry)localObject).getName();
            if (((ZipEntry)localObject).isDirectory())
            {
                localObject = new File(str.substring(0, str.length() - 1));
                if (paramBoolean1) {
                    localArrayList.add(localObject);
                }
            }
            else
            {
                localObject = new File(str);
                if (paramBoolean2) {
                    localArrayList.add(localObject);
                }
            }
        }
        paramString.close();
        return localArrayList;
    }

    public static void UnZipFolder(String paramString1, String paramString2)
            throws Exception
    {
        paramString1 = new ZipInputStream(new FileInputStream(paramString1));
        for (;;)
        {
            Object localObject1 = paramString1.getNextEntry();
            if (localObject1 == null) {
                break;
            }
            Object localObject2 = ((ZipEntry)localObject1).getName();
            if (((ZipEntry)localObject1).isDirectory())
            {
                localObject1 = ((String)localObject2).substring(0, ((String)localObject2).length() - 1);
                new File(paramString2 + File.separator + (String)localObject1).mkdirs();
            }
            else
            {
                localObject1 = new File(paramString2 + File.separator + (String)localObject2);
                ((File)localObject1).createNewFile();
                localObject1 = new FileOutputStream((File)localObject1);
                localObject2 = new byte['��'];
                for (;;)
                {
                    int i = paramString1.read((byte[])localObject2);
                    if (i == -1) {
                        break;
                    }
                    ((FileOutputStream)localObject1).write((byte[])localObject2, 0, i);
                    ((FileOutputStream)localObject1).flush();
                }
                ((FileOutputStream)localObject1).close();
            }
        }
        paramString1.close();
    }

    public static InputStream UpZip(String paramString1, String paramString2)
            throws Exception
    {
        Log.v("XZip", "UpZip(String, String)");
        paramString1 = new ZipFile(paramString1);
        return paramString1.getInputStream(paramString1.getEntry(paramString2));
    }

    private static void ZipFiles(String paramString1, String paramString2, ZipOutputStream paramZipOutputStream)
            throws Exception
    {
        int i = 0;
        if (paramZipOutputStream == null) {}
        for (;;)
        {
            return;
            Object localObject1 = new File(paramString1 + paramString2);
            if (((File)localObject1).isFile())
            {
                paramString1 = new ZipEntry(paramString2);
                paramString2 = new FileInputStream((File)localObject1);
                paramZipOutputStream.putNextEntry(paramString1);
                paramString1 = new byte['���'];
                for (;;)
                {
                    i = paramString2.read(paramString1);
                    if (i == -1) {
                        break;
                    }
                    paramZipOutputStream.write(paramString1, 0, i);
                }
                paramZipOutputStream.closeEntry();
                return;
            }
            localObject1 = ((File)localObject1).list();
            if (localObject1 != null)
            {
                ArrayList localArrayList = new ArrayList();
                String str = Process.myPid() + "";
                int j = localObject1.length;
                while (i < j)
                {
                    Object localObject2 = localObject1[i];
                    if (((String)localObject2).contains(str)) {
                        localArrayList.add(localObject2);
                    }
                    i += 1;
                }
                if (localObject1.length <= 0)
                {
                    paramZipOutputStream.putNextEntry(new ZipEntry(paramString2 + File.separator));
                    paramZipOutputStream.closeEntry();
                    return;
                }
                i = 0;
                while (i < localObject1.length)
                {
                    ZipFiles(paramString1, paramString2 + File.separator + localObject1[i], paramZipOutputStream);
                    i += 1;
                }
            }
        }
    }

    public static void ZipFolder(String paramString1, String paramString2)
            throws Exception
    {
        paramString2 = new ZipOutputStream(new FileOutputStream(paramString2));
        paramString1 = new File(paramString1);
        new ArrayList();
        ZipFiles(paramString1.getParent() + File.separator, paramString1.getName(), paramString2);
        paramString2.finish();
        paramString2.close();
    }

    private static void getFile(String paramString1, String paramString2, List<File> paramList)
    {
        Object localObject = new File(paramString1 + paramString2);
        if (((File)localObject).isFile()) {
            paramList.add(localObject);
        }
        for (;;)
        {
            return;
            localObject = ((File)localObject).list();
            if ((localObject != null) && (localObject.length > 0))
            {
                int i = 0;
                while (i < localObject.length)
                {
                    getFile(paramString1, paramString2 + File.separator + localObject[i], paramList);
                    i += 1;
                }
            }
        }
    }

    public void finalize()
            throws Throwable
    {}
}
