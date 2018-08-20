package com.olym.mjt.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.DisplayMetrics;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AppKeyInfoUtil
{
    public static String getPublicKeyInfo(Context paramContext)
    {
        try
        {
            paramContext = CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 64).signatures[0].toByteArray())).getPublicKey().toString();
            return paramContext;
        }
        catch (Throwable paramContext)
        {
            paramContext.printStackTrace();
        }
        return null;
    }

    public static String getSign(Context paramContext)
    {
        Iterator localIterator = paramContext.getPackageManager().getInstalledPackages(64).iterator();
        while (localIterator.hasNext())
        {
            PackageInfo localPackageInfo = (PackageInfo)localIterator.next();
            if (localPackageInfo.packageName.equals(paramContext.getPackageName())) {
                return localPackageInfo.signatures[0].toCharsString();
            }
        }
        return null;
    }

    public static String getSignaturesFromApk(File paramFile)
    {
        try
        {
            paramFile = new JarFile(paramFile);
            paramFile = loadCertificates(paramFile, paramFile.getJarEntry("AndroidManifest.xml"), new byte['���']);
            if ((paramFile != null) && (paramFile.length > 0))
            {
                paramFile = paramFile[0].getPublicKey().toString();
                return paramFile;
            }
        }
        catch (Exception paramFile) {}
        return null;
    }

    private static Certificate[] loadCertificates(JarFile paramJarFile, JarEntry paramJarEntry, byte[] paramArrayOfByte)
    {
        Object localObject = null;
        try
        {
            paramJarFile = paramJarFile.getInputStream(paramJarEntry);
            while (paramJarFile.read(paramArrayOfByte, 0, paramArrayOfByte.length) != -1) {}
            paramJarFile.close();
            paramJarFile = (JarFile)localObject;
            if (paramJarEntry != null) {
                paramJarFile = paramJarEntry.getCertificates();
            }
            return paramJarFile;
        }
        catch (IOException paramJarFile) {}
        return null;
    }

    public static String showUninstallAPKSignatures(String paramString)
    {
        try
        {
            Class localClass = Class.forName("android.content.pm.PackageParser");
            Object localObject = localClass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { paramString });
            DisplayMetrics localDisplayMetrics = new DisplayMetrics();
            localDisplayMetrics.setToDefaults();
            paramString = localClass.getDeclaredMethod("parsePackage", new Class[] { File.class, String.class, DisplayMetrics.class, Integer.TYPE }).invoke(localObject, new Object[] { new File(paramString), paramString, localDisplayMetrics, Integer.valueOf(64) });
            localClass.getDeclaredMethod("collectCertificates", new Class[] { paramString.getClass(), Integer.TYPE }).invoke(localObject, new Object[] { paramString, Integer.valueOf(64) });
            paramString = ((Signature[])(Signature[])paramString.getClass().getDeclaredField("mSignatures").get(paramString))[0].toCharsString();
            return paramString;
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
        }
        return null;
    }

    private static String toCharsString(byte[] paramArrayOfByte)
    {
        int k = paramArrayOfByte.length;
        char[] arrayOfChar = new char[k * 2];
        int i = 0;
        if (i < k)
        {
            int m = paramArrayOfByte[i];
            int j = m >> 4 & 0xF;
            if (j >= 10)
            {
                j = j + 97 - 10;
                label44:
                arrayOfChar[(i * 2)] = ((char)j);
                j = m & 0xF;
                if (j < 10) {
                    break label97;
                }
                j = j + 97 - 10;
            }
            for (;;)
            {
                arrayOfChar[(i * 2 + 1)] = ((char)j);
                i += 1;
                break;
                j += 48;
                break label44;
                label97:
                j += 48;
            }
        }
        return new String(arrayOfChar);
    }
}

