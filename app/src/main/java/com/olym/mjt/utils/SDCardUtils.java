package com.olym.mjt.utils;

import android.os.storage.StorageManager;
import com.olym.mjt.module.MjtApplication;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SDCardUtils
{
    private SDCardUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static List<String> getSDCardPaths()
    {
        Object localObject = (StorageManager)MjtApplication.getInstance().getSystemService("storage");
        ArrayList localArrayList = new ArrayList();
        try
        {
            Method localMethod = StorageManager.class.getMethod("getVolumePaths", new Class[0]);
            localMethod.setAccessible(true);
            localObject = Arrays.asList((String[])localMethod.invoke(localObject, new Object[0]));
            return (List<String>)localObject;
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
            localNoSuchMethodException.printStackTrace();
            return localArrayList;
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
            localIllegalAccessException.printStackTrace();
            return localArrayList;
        }
        catch (InvocationTargetException localInvocationTargetException)
        {
            localInvocationTargetException.printStackTrace();
        }
        return localArrayList;
    }

    public static List<String> getSDCardPaths(boolean paramBoolean)
    {
        localArrayList = new ArrayList();
        Object localObject2 = (StorageManager)MjtApplication.getInstance().getSystemService("storage");
        try
        {
            Object localObject1 = Class.forName("android.os.storage.StorageVolume");
            Object localObject3 = StorageManager.class.getMethod("getVolumeList", new Class[0]);
            Method localMethod = ((Class)localObject1).getMethod("getPath", new Class[0]);
            localObject1 = ((Class)localObject1).getMethod("isRemovable", new Class[0]);
            localObject2 = ((Method)localObject3).invoke(localObject2, new Object[0]);
            int j = Array.getLength(localObject2);
            int i = 0;
            while (i < j)
            {
                localObject3 = Array.get(localObject2, i);
                String str = (String)localMethod.invoke(localObject3, new Object[0]);
                if (paramBoolean == ((Boolean)((Method)localObject1).invoke(localObject3, new Object[0])).booleanValue()) {
                    localArrayList.add(str);
                }
                i += 1;
            }
            return localArrayList;
        }
        catch (ClassNotFoundException localClassNotFoundException)
        {
            localClassNotFoundException.printStackTrace();
            return localArrayList;
        }
        catch (InvocationTargetException localInvocationTargetException)
        {
            localInvocationTargetException.printStackTrace();
            return localArrayList;
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
            localNoSuchMethodException.printStackTrace();
            return localArrayList;
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
            localIllegalAccessException.printStackTrace();
        }
    }

    public static boolean isSDCardEnable()
    {
        return !getSDCardPaths().isEmpty();
    }
}

