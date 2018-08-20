package com.olym.mjt.utils;

import android.os.Build;
import android.text.TextUtils;

public class RomUtil
{
    private static final String KEY_VERSION_EMUI = "ro.build.version.emui";
    private static final String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
    private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";
    private static final String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final String KEY_VERSION_VIVO = "ro.vivo.os.version";
    public static final String ROM_EMUI = "EMUI";
    public static final String ROM_FLYME = "FLYME";
    public static final String ROM_MIUI = "MIUI";
    public static final String ROM_OPPO = "OPPO";
    public static final String ROM_QIKU = "QIKU";
    public static final String ROM_SMARTISAN = "SMARTISAN";
    public static final String ROM_VIVO = "VIVO";
    private static final String TAG = "Rom";
    private static String sName;
    private static String sVersion;

    public static boolean check(String paramString)
    {
        if (sName != null) {
            return sName.equals(paramString);
        }
        String str = getProp("ro.miui.ui.version.name");
        sVersion = str;
        if (!TextUtils.isEmpty(str)) {
            sName = "MIUI";
        }
        for (;;)
        {
            return sName.equals(paramString);
            str = getProp("ro.build.version.emui");
            sVersion = str;
            if (!TextUtils.isEmpty(str))
            {
                sName = "EMUI";
            }
            else
            {
                str = getProp("ro.build.version.opporom");
                sVersion = str;
                if (!TextUtils.isEmpty(str))
                {
                    sName = "OPPO";
                }
                else
                {
                    str = getProp("ro.vivo.os.version");
                    sVersion = str;
                    if (!TextUtils.isEmpty(str))
                    {
                        sName = "VIVO";
                    }
                    else
                    {
                        str = getProp("ro.smartisan.version");
                        sVersion = str;
                        if (!TextUtils.isEmpty(str))
                        {
                            sName = "SMARTISAN";
                        }
                        else
                        {
                            sVersion = Build.DISPLAY;
                            if (sVersion.toUpperCase().contains("FLYME"))
                            {
                                sName = "FLYME";
                            }
                            else
                            {
                                sVersion = "unknown";
                                sName = Build.MANUFACTURER.toUpperCase();
                            }
                        }
                    }
                }
            }
        }
    }

    private static String getManufacturer()
    {
        if (Build.MANUFACTURER == null) {
            return "";
        }
        return Build.MANUFACTURER.trim();
    }

    public static String getName()
    {
        if (sName == null) {
            check("");
        }
        return sName;
    }

    /* Error */
    public static String getProp(String paramString)
    {
        // Byte code:
        //   0: aconst_null
        //   1: astore_1
        //   2: aconst_null
        //   3: astore_3
        //   4: new 103	java/io/BufferedReader
        //   7: dup
        //   8: new 105	java/io/InputStreamReader
        //   11: dup
        //   12: invokestatic 111	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
        //   15: new 113	java/lang/StringBuilder
        //   18: dup
        //   19: invokespecial 114	java/lang/StringBuilder:<init>	()V
        //   22: ldc 116
        //   24: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   27: aload_0
        //   28: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   31: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   34: invokevirtual 127	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
        //   37: invokevirtual 133	java/lang/Process:getInputStream	()Ljava/io/InputStream;
        //   40: invokespecial 136	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
        //   43: sipush 1024
        //   46: invokespecial 139	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
        //   49: astore_2
        //   50: aload_2
        //   51: invokevirtual 142	java/io/BufferedReader:readLine	()Ljava/lang/String;
        //   54: astore_1
        //   55: aload_2
        //   56: invokevirtual 145	java/io/BufferedReader:close	()V
        //   59: aload_2
        //   60: ifnull +7 -> 67
        //   63: aload_2
        //   64: invokevirtual 145	java/io/BufferedReader:close	()V
        //   67: aload_1
        //   68: astore_0
        //   69: aload_0
        //   70: areturn
        //   71: astore_0
        //   72: aload_0
        //   73: invokevirtual 148	java/io/IOException:printStackTrace	()V
        //   76: goto -9 -> 67
        //   79: astore_1
        //   80: aload_3
        //   81: astore_2
        //   82: aload_1
        //   83: astore_3
        //   84: aload_2
        //   85: astore_1
        //   86: ldc 44
        //   88: new 113	java/lang/StringBuilder
        //   91: dup
        //   92: invokespecial 114	java/lang/StringBuilder:<init>	()V
        //   95: ldc -106
        //   97: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   100: aload_0
        //   101: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   104: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   107: aload_3
        //   108: invokestatic 156	com/olym/mjt/utils/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
        //   111: aconst_null
        //   112: astore_0
        //   113: aload_2
        //   114: ifnull -45 -> 69
        //   117: aload_2
        //   118: invokevirtual 145	java/io/BufferedReader:close	()V
        //   121: aconst_null
        //   122: areturn
        //   123: astore_0
        //   124: aload_0
        //   125: invokevirtual 148	java/io/IOException:printStackTrace	()V
        //   128: aconst_null
        //   129: areturn
        //   130: astore_0
        //   131: aload_1
        //   132: ifnull +7 -> 139
        //   135: aload_1
        //   136: invokevirtual 145	java/io/BufferedReader:close	()V
        //   139: aload_0
        //   140: athrow
        //   141: astore_1
        //   142: aload_1
        //   143: invokevirtual 148	java/io/IOException:printStackTrace	()V
        //   146: goto -7 -> 139
        //   149: astore_0
        //   150: aload_2
        //   151: astore_1
        //   152: goto -21 -> 131
        //   155: astore_3
        //   156: goto -72 -> 84
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	159	0	paramString	String
        //   1	67	1	str	String
        //   79	4	1	localIOException1	java.io.IOException
        //   85	51	1	localObject1	Object
        //   141	2	1	localIOException2	java.io.IOException
        //   151	1	1	localObject2	Object
        //   49	102	2	localObject3	Object
        //   3	105	3	localIOException3	java.io.IOException
        //   155	1	3	localIOException4	java.io.IOException
        // Exception table:
        //   from	to	target	type
        //   63	67	71	java/io/IOException
        //   4	50	79	java/io/IOException
        //   117	121	123	java/io/IOException
        //   4	50	130	finally
        //   86	111	130	finally
        //   135	139	141	java/io/IOException
        //   50	59	149	finally
        //   50	59	155	java/io/IOException
    }

    public static String getVersion()
    {
        if (sVersion == null) {
            check("");
        }
        return sVersion;
    }

    public static boolean is360()
    {
        return (check("QIKU")) || (check("360"));
    }

    public static boolean isEmui()
    {
        return check("EMUI");
    }

    public static boolean isFlyme()
    {
        return check("FLYME");
    }

    public static boolean isGotoLockedShow()
    {
        return (isMiui()) || (isVivo());
    }

    public static boolean isGotoWebGuide()
    {
        return (isOppo()) || (isVivo()) || (isSamsung()) || (isMiui());
    }

    public static boolean isLockedClear()
    {
        return (isMate8()) || (isMate9());
    }

    private static boolean isMate8()
    {
        return Build.MODEL.contains("NXT");
    }

    private static boolean isMate9()
    {
        return Build.MODEL.contains("MHA");
    }

    public static boolean isMiui()
    {
        return check("MIUI");
    }

    public static boolean isOppo()
    {
        return check("OPPO");
    }

    public static boolean isSamsung()
    {
        return getManufacturer().toLowerCase().contains("samsung");
    }

    public static boolean isSmartisan()
    {
        return check("SMARTISAN");
    }

    public static boolean isVivo()
    {
        return check("VIVO");
    }
}

