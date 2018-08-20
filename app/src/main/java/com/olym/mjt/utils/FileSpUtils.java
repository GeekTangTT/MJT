package com.olym.mjt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import com.nisc.SecurityEngine;
import com.nisc.SecurityEngineException;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.utils.cipher.CryptUtils;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSpUtils
{
    private static final String KEY_DB_FILE = "file_db";
    private static final String KEY_IBC_DOMAIN = "ibc_domain";
    private static final String KEY_PHONE = "phone";
    private static volatile FileSpUtils instanse;
    private static SharedPreferences sPref;

    private String getDBFileKeyName()
    {
        return getPhone() + "_" + getUserDomain() + "_" + "file_db";
    }

    private static SharedPreferences.Editor getEditor(Context paramContext)
    {
        return getPreference(paramContext).edit();
    }

    public static FileSpUtils getInstanse()
    {
        if (instanse == null) {}
        try
        {
            if (instanse == null) {
                instanse = new FileSpUtils();
            }
            return instanse;
        }
        finally {}
    }

    private static SharedPreferences getPreference(Context paramContext)
    {
        if (sPref == null) {
            sPref = paramContext.getApplicationContext().getSharedPreferences("ASE_FILE", 0);
        }
        return sPref;
    }

    public String get(String paramString)
    {
        return getPreference(MjtApplication.getInstance()).getString(paramString, null);
    }

    public byte[] getKeyFileReandomNumber()
    {
        Object localObject = get(getDBFileKeyName());
        if (TextUtils.isEmpty((CharSequence)localObject))
        {
            localObject = new byte[16];
            try
            {
                SecurityEngine.getInstance().SMRand((byte[])localObject, 16);
                String str = CryptUtils.encryptFileRandomPass(UserSpUtil.getInstanse().getPhone(), (byte[])localObject);
                put(getDBFileKeyName(), str);
                return (byte[])localObject;
            }
            catch (SecurityEngineException localSecurityEngineException)
            {
                localSecurityEngineException.printStackTrace();
                return (byte[])localObject;
            }
        }
        return CryptUtils.decryptFileRandomPass(UserSpUtil.getInstanse().getPhone(), (String)localObject);
    }

    public String getPhone()
    {
        String str2 = get("phone");
        String str1 = str2;
        if (TextUtils.isEmpty(str2))
        {
            str1 = UserSpUtil.getInstanse().getPhone();
            setPhone(str1);
        }
        return str1;
    }

    public String getUserDomain()
    {
        String str2 = get("ibc_domain");
        String str1 = str2;
        if (TextUtils.isEmpty(str2))
        {
            str1 = UserSpUtil.getInstanse().getUserDomain();
            setUserDomian(str1);
        }
        return str1;
    }

    public void put(String paramString1, String paramString2)
    {
        getEditor(MjtApplication.getInstance()).putString(paramString1, paramString2).commit();
    }

    public Object readObject(Context paramContext, String paramString)
    {
        try
        {
            paramContext = getPreference(paramContext).getString(paramString, null);
            if (TextUtils.isEmpty(paramContext)) {
                return null;
            }
            paramContext = new ObjectInputStream(new ByteArrayInputStream(Base64.decode(paramContext))).readObject();
            return paramContext;
        }
        catch (Exception paramContext)
        {
            Log.e("test", "readObject error", paramContext);
        }
        return null;
    }

    public void setPhone(String paramString)
    {
        put("phone", paramString);
    }

    public void setUserDomian(String paramString)
    {
        put("ibc_domain", paramString);
    }

    public void writeObject(Context paramContext, String paramString, Object paramObject)
    {
        try
        {
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            new ObjectOutputStream(localByteArrayOutputStream).writeObject(paramObject);
            paramObject = new String(Base64.encode(localByteArrayOutputStream.toByteArray()));
            getEditor(paramContext).putString(paramString, (String)paramObject).commit();
            return;
        }
        catch (Exception paramContext)
        {
            Log.e("test", "saveObject error", paramContext);
        }
    }
}
