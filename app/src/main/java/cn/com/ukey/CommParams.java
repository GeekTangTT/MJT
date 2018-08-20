package cn.com.ukey;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Locale;

public class CommParams
{
    private static final String iv = "12345678";
    private static final String key = "abcdefghijklmnopqrstuvwx";

    private static String decrypt(String paramString)
    {
        try
        {
            paramString = Base64.decode(paramString);
            paramString = new String(Des3.des3DecodeCBC("abcdefghijklmnopqrstuvwx".getBytes(), "12345678".getBytes(), paramString), "utf-8");
            if (paramString.startsWith("autoConfig="))
            {
                paramString = paramString.substring(11);
                return paramString;
            }
            return "";
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
        }
        return "";
    }

    private static String encrypt(String paramString)
    {
        paramString = "autoConfig=" + paramString;
        try
        {
            paramString = Base64.encodeBytes(Des3.des3EncodeCBC("abcdefghijklmnopqrstuvwx".getBytes(), "12345678".getBytes(), paramString.getBytes()));
            return paramString;
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
        }
        return "";
    }

    public static String getCommParam(Context paramContext)
    {
        String str1 = "";
        for (;;)
        {
            try
            {
                localObject = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/hdzb_ccb/files", "autoConfig.txt");
                paramContext = str1;
                if (((File)localObject).exists())
                {
                    paramContext = new BufferedReader(new FileReader((File)localObject));
                    localObject = new StringBuilder();
                    str2 = paramContext.readLine();
                    if (str2 != null) {
                        continue;
                    }
                    paramContext.close();
                    paramContext = ((StringBuilder)localObject).toString();
                }
            }
            catch (Exception paramContext)
            {
                Object localObject;
                String str2;
                paramContext.printStackTrace();
                paramContext = str1;
                continue;
            }
            paramContext = decrypt(paramContext);
            if (paramContext.compareTo("") != 0) {
                break;
            }
            return getSpecialDeviceCommParam(Build.MANUFACTURER.toUpperCase(Locale.getDefault()), Build.MODEL);
            ((StringBuilder)localObject).append(str2);
        }
        return paramContext;
    }

    private static String getSDCardDir()
    {
        File localFile = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            localFile = Environment.getExternalStorageDirectory();
        }
        return localFile.toString();
    }

    private static String getSpecialDeviceCommParam(String paramString1, String paramString2)
    {
        return "";
    }

    public static void setCommParam(String paramString)
    {
        if (paramString != null) {
            try
            {
                Object localObject = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/hdzb_ccb/files");
                if ((!((File)localObject).exists()) && (!((File)localObject).mkdirs())) {
                    return;
                }
                localObject = new File((File)localObject, "autoConfig.txt");
                if (!((File)localObject).exists()) {
                    ((File)localObject).createNewFile();
                }
                localObject = new FileWriter((File)localObject, false);
                BufferedWriter localBufferedWriter = new BufferedWriter((Writer)localObject);
                localBufferedWriter.write(encrypt(paramString));
                localBufferedWriter.close();
                ((FileWriter)localObject).close();
                return;
            }
            catch (Exception paramString)
            {
                paramString.printStackTrace();
            }
        }
    }
}
