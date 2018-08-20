package com.olym.mjt.init;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.lc.methodex.LogFinalUtils;
import com.nisc.GlobalSecurityEngine;
import com.nisc.SecurityEngine;
import com.nisc.SecurityEngineException;
import com.nisc.UKeyManageInterface;
import com.nisc.api.SecEngineException;
import com.olym.mjt.config.AppConfig;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.sms.utils.SmsManagerUtils;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.Base64;
import com.olym.mjt.utils.CachedThreadPoolUtils;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.SDCardUtils;
import com.olym.mjt.utils.cipher.CryptUtils;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class EngineUtils
{
    private static volatile EngineUtils engineUtils;
    private boolean bGetExchangeParameter = false;
    private String password;
    private SecurityEngine securityEngine;

    private void ensureInit()
    {
        if (this.securityEngine == null) {
            initSecurityEngine(MjtApplication.getInstance());
        }
        String str = UserSpUtil.getInstanse().getPhone();
        if (TextUtils.isEmpty(str)) {
            this.password = UserSpUtil.getInstanse().getRandomNumber();
        }
        if (!this.securityEngine.IsUserLogined(str)) {}
        try
        {
            UKeyManageInterface.getInstance().loginLocalDevice(str, this.password);
            return;
        }
        catch (SecEngineException localSecEngineException)
        {
            LogFinalUtils.logForException(localSecEngineException);
        }
    }

    private int forceExchangeParameters()
    {
        int i;
        if (this.bGetExchangeParameter) {
            i = 1;
        }
        int j;
        do
        {
            return i;
            j = SecurityEngine.ForceExchangeParameters(0);
            i = j;
        } while (j != 1);
        this.bGetExchangeParameter = true;
        return j;
    }

    private String getDevicePrivateInfo()
            throws SecEngineException
    {
        try
        {
            String str = this.securityEngine.GetDevicePrivateInfo();
            return str;
        }
        catch (SecurityEngineException localSecurityEngineException)
        {
            LogFinalUtils.logForException(localSecurityEngineException);
            throw new SecEngineException(localSecurityEngineException.getStatus());
        }
    }

    public static EngineUtils getInstance()
    {
        if (engineUtils == null) {}
        try
        {
            if (engineUtils == null) {
                engineUtils = new EngineUtils();
            }
            return engineUtils;
        }
        finally {}
    }

    private boolean initCipherHw(Context paramContext)
    {
        Object localObject = new File(getLibraryDirectory(paramContext));
        new StringBuilder().append(((File)localObject).getAbsolutePath()).append("/libtfcard.so").toString();
        int i = -1;
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return false;
        }
        localObject = SDCardUtils.getSDCardPaths().iterator();
        while (((Iterator)localObject).hasNext())
        {
            String str = (String)((Iterator)localObject).next();
            Applog.systemOut("-----path--1--- " + str);
            Applog.info("-----path--1--- " + str);
            str = str + "/Android/data/com.olym.mjt";
            Applog.systemOut("-------strTFDir------ " + str);
            try
            {
                j = SecurityEngine.InitJNCard(paramContext, str);
                Applog.systemOut("-----initCipherHw---ret----   " + j);
                Applog.info("-----initCipherHw---ret----   " + j);
                i = j;
                if (j != 0) {
                    continue;
                }
                MjtApplication.getInstance().setTF_path(str);
                return true;
            }
            catch (SecurityEngineException localSecurityEngineException)
            {
                for (;;)
                {
                    int j = i;
                }
            }
        }
    }

    public void SetIBCServer(String paramString1, String paramString2)
            throws SecurityEngineException
    {
        GlobalSecurityEngine.getInstance();
        GlobalSecurityEngine.setGlobalStringAttribute(40, paramString1 + paramString2);
    }

    public void activeIdentityAndDownloadEx(String paramString1, String paramString2, String paramString3, int paramInt)
            throws SecEngineException
    {
        ensureInit();
        try
        {
            this.securityEngine.ActiveIdentityAndDownloadEx(paramString1, paramString2, paramString3, paramInt);
            return;
        }
        catch (SecurityEngineException paramString1)
        {
            paramString1.printStackTrace();
            throw new SecEngineException(paramString1.getStatus());
        }
    }

    public void activeIdentityAndDownloadEx1(String paramString1, String paramString2, String paramString3, int paramInt)
            throws SecEngineException
    {
        ensureInit();
        try
        {
            this.securityEngine.ActiveIdentityAndDownloadEx(paramString1, paramString2, paramString3, paramInt);
            return;
        }
        catch (SecurityEngineException paramString1)
        {
            paramString1.printStackTrace();
            throw new SecEngineException(paramString1.getStatus());
        }
    }

    public void activeMobileIdAndDownloadKey(String paramString1, String paramString2, String paramString3)
            throws SecEngineException
    {
        try
        {
            this.securityEngine.LoginLocalDevice(1, "", "");
            this.securityEngine.ActiveMobileIdAndDownloadKey(paramString1, paramString2, paramString3);
            return;
        }
        catch (SecurityEngineException paramString1)
        {
            LogFinalUtils.logForException(paramString1);
            throw new SecEngineException(paramString1.getStatus());
        }
    }

    public void changePassword(String paramString1, String paramString2)
            throws SecurityEngineException
    {
        this.securityEngine.ChangePassword(paramString1, paramString2);
    }

    public void closeLog()
    {
        GlobalSecurityEngine.getInstance();
        try
        {
            GlobalSecurityEngine.setDebugMode(0, 0, "");
            return;
        }
        catch (SecEngineException localSecEngineException)
        {
            localSecEngineException.printStackTrace();
        }
    }

    public String cryptCMSCrossDomain(String paramString1, int paramInt, String paramString2, String paramString3)
            throws Exception
    {
        Object localObject2 = "";
        Object localObject1 = localObject2;
        if (paramString3 != null)
        {
            localObject1 = localObject2;
            if (!"".equals(paramString3))
            {
                localObject1 = UserSpUtil.getInstanse().getPhone();
                if (!SecurityEngine.getInstance().IsUserLogined((String)localObject1))
                {
                    localObject2 = UserSpUtil.getInstanse().getRandomNumber();
                    if (UKeyManageInterface.getInstance().loginLocalDevice((String)localObject1, (String)localObject2) != 1) {
                        return "";
                    }
                }
                localObject1 = SecurityEngine.getInstance();
                paramString3 = paramString3.getBytes();
                int i = paramString3.length;
                localObject2 = new int[1];
                localObject2[0] = 0;
                ((SecurityEngine)localObject1).CryptExportDataEx3(paramString1, paramInt, paramString2, paramString3, i, null, (int[])localObject2);
                byte[] arrayOfByte = new byte[localObject2[0]];
                ((SecurityEngine)localObject1).CryptExportDataEx3(paramString1, paramInt, paramString2, paramString3, i, arrayOfByte, (int[])localObject2);
                localObject1 = Base64.encode(arrayOfByte);
            }
        }
        return (String)localObject1;
    }

    public String cryptCms(String paramString1, String paramString2, String paramString3)
            throws Exception
    {
        ensureInit();
        Object localObject2 = "";
        Object localObject1 = localObject2;
        if (paramString3 != null)
        {
            localObject1 = localObject2;
            if (!"".equals(paramString3))
            {
                localObject1 = this.securityEngine;
                paramString3 = paramString3.getBytes();
                int i = paramString3.length;
                localObject2 = new int[1];
                localObject2[0] = 0;
                ((SecurityEngine)localObject1).CryptExportDataEx(paramString1, paramString2, paramString3, i, null, (int[])localObject2);
                byte[] arrayOfByte = new byte[localObject2[0]];
                ((SecurityEngine)localObject1).CryptExportDataEx(paramString1, paramString2, paramString3, i, arrayOfByte, (int[])localObject2);
                localObject1 = Base64.encode(arrayOfByte);
            }
        }
        return (String)localObject1;
    }

    public String decryptCms(String paramString1, String paramString2)
    {
        ensureInit();
        Object localObject2 = "";
        Object localObject1 = localObject2;
        if (paramString2 != null)
        {
            localObject1 = localObject2;
            if ("".equals(paramString2)) {}
        }
        try
        {
            paramString2 = Base64.decode(paramString2);
            int i = paramString2.length;
            localObject1 = new int[1];
            localObject1[0] = 0;
            this.securityEngine.CryptImportDataEx(paramString1, paramString2, i, null, (int[])localObject1);
            localObject2 = new byte[localObject1[0]];
            this.securityEngine.CryptImportDataEx(paramString1, paramString2, i, (byte[])localObject2, (int[])localObject1);
            localObject1 = new String((byte[])localObject2, "utf-8");
            return (String)localObject1;
        }
        catch (SecurityEngineException paramString1)
        {
            LogFinalUtils.logForException(paramString1);
            Applog.systemOut("------SecurityEngineException------ " + paramString1);
            return "";
        }
        catch (UnsupportedEncodingException paramString1)
        {
            LogFinalUtils.logForException(paramString1);
            Applog.systemOut("------UnsupportedEncodingException------ " + paramString1);
        }
        return "";
    }

    public void decryptP7File(String paramString1, String paramString2)
            throws SecurityEngineException
    {
        ensureInit();
        this.securityEngine.DecryptP7File(paramString1, paramString2);
    }

    public String decryptPhoneNumber(String paramString)
    {
        ensureInit();
        if (TextUtils.isEmpty(paramString)) {
            localObject = "";
        }
        do
        {
            return (String)localObject;
            localObject = paramString;
        } while (!paramString.contains(String.valueOf('*')));
        Object localObject = paramString.replace("*", "").trim().getBytes();
        try
        {
            if (ChannelUtil.isUsedTEE) {}
            for (localObject = this.securityEngine.CryptFpeDecrypt((byte[])localObject, localObject.length, 1, 19);; localObject = this.securityEngine.CryptFpeDecrypt("1234567890123456".getBytes(), "1234567890123456".getBytes().length, "234124124".getBytes(), "234124124".getBytes().length, (byte[])localObject, localObject.length, 1, 19)) {
                return new String((byte[])localObject, "utf-8");
            }
            return paramString;
        }
        catch (SecurityEngineException localSecurityEngineException)
        {
            LogFinalUtils.logForException(localSecurityEngineException);
            localSecurityEngineException.printStackTrace();
            return paramString;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
            LogFinalUtils.logForException(localUnsupportedEncodingException);
            localUnsupportedEncodingException.printStackTrace();
        }
    }

    public byte[] decryptSignData(String paramString1, String paramString2)
    {
        ensureInit();
        try
        {
            paramString2 = Base64.decode(paramString2);
            int i = paramString2.length;
            int[] arrayOfInt = new int[1];
            arrayOfInt[0] = 0;
            this.securityEngine.CryptImportDataEx(paramString1, paramString2, i, null, arrayOfInt);
            byte[] arrayOfByte = new byte[arrayOfInt[0]];
            this.securityEngine.CryptImportDataEx(paramString1, paramString2, i, arrayOfByte, arrayOfInt);
            return arrayOfByte;
        }
        catch (UnsupportedEncodingException paramString1)
        {
            LogFinalUtils.logForException(paramString1);
            paramString1.printStackTrace();
            return "".getBytes();
        }
        catch (SecurityEngineException paramString1)
        {
            for (;;)
            {
                LogFinalUtils.logForException(paramString1);
                paramString1.printStackTrace();
            }
        }
    }

    public int deletePrivate(String paramString)
    {
        return this.securityEngine.DeletePrivate(paramString);
    }

    public void downloadPrivateKey(String paramString1, String paramString2, String paramString3)
            throws SecEngineException
    {
        ensureInit();
        try
        {
            this.securityEngine.DownloadPrivateKey(paramString1, paramString2, "", -1, 1, paramString1, paramString3);
            return;
        }
        catch (SecurityEngineException paramString1)
        {
            paramString1.printStackTrace();
            throw new SecEngineException(paramString1.getStatus());
        }
    }

    public void encrptP7File(String paramString1, String paramString2, String paramString3, String paramString4)
            throws SecurityEngineException
    {
        ensureInit();
        this.securityEngine.EncryptP7FileEx(paramString1, paramString2, paramString3, paramString4);
    }

    public String encryptPhoneNumber(String paramString)
    {
        ensureInit();
        Object localObject = paramString.replace(" ", "");
        if (((String)localObject).contains("*")) {
            return (String)localObject;
        }
        try
        {
            if (ChannelUtil.isUsedTEE) {}
            for (localObject = this.securityEngine.CryptFpeEncrypt(((String)localObject).trim().getBytes(), ((String)localObject).trim().getBytes().length, 1, 19);; localObject = this.securityEngine.CryptFpeEncrypt("1234567890123456".getBytes(), "1234567890123456".getBytes().length, "234124124".getBytes(), "234124124".getBytes().length, ((String)localObject).trim().getBytes(), ((String)localObject).trim().getBytes().length, 1, 19))
            {
                localObject = new String((byte[])localObject, "utf-8");
                return "*" + (String)localObject;
            }
            return paramString;
        }
        catch (SecurityEngineException localSecurityEngineException)
        {
            LogFinalUtils.logForException(localSecurityEngineException);
            localSecurityEngineException.printStackTrace();
            return paramString;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
            LogFinalUtils.logForException(localUnsupportedEncodingException);
            localUnsupportedEncodingException.printStackTrace();
        }
    }

    public byte[] encryptSignData(String paramString1, String paramString2)
    {
        ensureInit();
        try
        {
            paramString2 = paramString2.getBytes();
            int i = paramString2.length;
            int[] arrayOfInt = new int[1];
            arrayOfInt[0] = 0;
            this.securityEngine.CryptSignDataEx(paramString1, paramString2, i, null, arrayOfInt);
            byte[] arrayOfByte = new byte[arrayOfInt[0]];
            this.securityEngine.CryptSignDataEx(paramString1, paramString2, i, arrayOfByte, arrayOfInt);
            return arrayOfByte;
        }
        catch (SecurityEngineException paramString1)
        {
            LogFinalUtils.logForException(paramString1);
            Applog.systemOut("-------encryptSignData----- " + paramString1);
        }
        return "".getBytes();
    }

    public boolean ensureGroupKeyExists(String paramString)
    {
        ensureInit();
        String str1 = MjtApplication.getInstance().getGroupPrivateKey();
        if (this.securityEngine.IsUserLogined(paramString)) {
            return true;
        }
        int j = 0;
        for (;;)
        {
            int i;
            try
            {
                String[] arrayOfString = UKeyManageInterface.getInstance().enumUsers();
                int k = arrayOfString.length;
                i = 0;
                if (i < k)
                {
                    if (arrayOfString[i].toLowerCase().equals(paramString.toLowerCase())) {
                        j = 1;
                    }
                }
                else if (j != 0)
                {
                    this.securityEngine.LoginLocalDevice(1, paramString, str1);
                    return true;
                }
            }
            catch (SecurityEngineException localSecurityEngineException1)
            {
                try
                {
                    String str3 = UserSpUtil.getInstanse().getPhone();
                    this.securityEngine.LoginLocalDevice(1, "", "");
                    this.securityEngine.GetGroupIdPrivates(AppConfig.CONFIG_KEY_SERVER_URL, str3, paramString, str1);
                    this.securityEngine.LoginLocalDevice(1, paramString, str1);
                    return true;
                }
                catch (SecurityEngineException localSecurityEngineException2)
                {
                    LogFinalUtils.logForException(localSecurityEngineException1);
                    Applog.info("MineMuchChatManager ensureGroupKeyExists:" + localSecurityEngineException2.getMessage() + "===========url:" + AppConfig.CONFIG_KEY_SERVER_URL + "==roomID:" + paramString + "==groupKey:" + str1);
                    return false;
                }
                String str2 = UserSpUtil.getInstanse().getPhone();
                this.securityEngine.LoginLocalDevice(1, "", "");
                this.securityEngine.GetGroupIdPrivates(AppConfig.CONFIG_KEY_SERVER_URL, str2, paramString, str1);
                this.securityEngine.LoginLocalDevice(1, paramString, str1);
                return true;
            }
            i += 1;
        }
    }

    public String[] enumUsers()
            throws SecEngineException
    {
        Object localObject = getDevicePrivateInfo();
        if (localObject == null) {
            return null;
        }
        String[] arrayOfString2 = ((String)localObject).split(";");
        if (Integer.parseInt(arrayOfString2[0]) < 1) {
            throw new SecEngineException(63523);
        }
        int j = Integer.parseInt(arrayOfString2[2]);
        if (j >= 1)
        {
            String[] arrayOfString1 = new String[j];
            int i = 0;
            for (;;)
            {
                localObject = arrayOfString1;
                if (i >= j) {
                    break;
                }
                arrayOfString1[i] = arrayOfString2[(i + 3)].toString().toLowerCase();
                i += 1;
            }
        }
        localObject = new String[1];
        localObject[0] = "";
        return (String[])localObject;
    }

    public void forgetPassword(String paramString1, String paramString2, int[] paramArrayOfInt)
            throws SecurityEngineException
    {
        ensureInit();
        this.securityEngine.ForgetPassword(paramString1, paramString2, paramArrayOfInt);
    }

    public String getFileRecpList(String paramString)
            throws SecurityEngineException
    {
        ensureInit();
        return this.securityEngine.GetFileRecpList(paramString);
    }

    public String getHostByName(String paramString)
            throws UnknownHostException
    {
        return InetAddress.getByName(paramString).getHostAddress();
    }

    public String getLibraryDirectory(Context paramContext)
    {
        int i = Build.VERSION.SDK_INT;
        if (i >= 9) {
            return paramContext.getApplicationInfo().nativeLibraryDir;
        }
        if (i >= 4) {
            return paramContext.getApplicationInfo().dataDir + "/lib";
        }
        return "/data/data/" + paramContext.getPackageName() + "/lib";
    }

    public int getMemberStatus(String paramString)
            throws SecurityEngineException
    {
        try
        {
            if (this.securityEngine == null)
            {
                Applog.systemOut("-----securityEngine == null------");
                return -1;
            }
            if (paramString == null)
            {
                Applog.systemOut("-----phone == null------");
                return -1;
            }
        }
        catch (SecurityEngineException paramString)
        {
            LogFinalUtils.logForException(paramString);
            return -1;
        }
        int i = this.securityEngine.GetMemberStatus(paramString);
        return i;
    }

    public void getMemberStatusEx(String paramString1, String paramString2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
            throws SecurityEngineException
    {
        ensureInit();
        this.securityEngine.GetMemberStatusEx(paramString1, paramString2, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
    }

    public String getPlainData(String paramString1, String paramString2)
    {
        ensureInit();
        paramString1 = decryptSignData(paramString1, paramString2);
        try
        {
            paramString1 = new String(paramString1, "utf-8");
            return paramString1;
        }
        catch (UnsupportedEncodingException paramString1)
        {
            LogFinalUtils.logForException(paramString1);
            paramString1.printStackTrace();
        }
        return null;
    }

    public byte[] getPlainDataForBytes(String paramString1, String paramString2)
    {
        ensureInit();
        return decryptSignData(paramString1, paramString2);
    }

    public void getVerifyCode(String paramString1, String paramString2)
            throws SecurityEngineException
    {
        this.securityEngine.GetVerifyCode(paramString1, paramString2);
    }

    public void initPassword(String paramString)
    {
        this.password = paramString;
    }

    public boolean initSecurityEngine(Context paramContext)
    {
        boolean bool = true;
        for (;;)
        {
            try
            {
                this.password = UserSpUtil.getInstanse().getRandomNumber();
                localGlobalSecurityEngine = GlobalSecurityEngine.getInstance();
                localGlobalSecurityEngine.initSecurityEngine(MjtApplication.getInstance(), false);
                closeLog();
                if (ChannelUtil.currentChannel == 106)
                {
                    paramContext = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
                    Applog.systemOut("-----imei---- " + paramContext);
                    GlobalSecurityEngine.setGlobalStringAttribute(45, paramContext);
                    GlobalSecurityEngine.setGlobalIntegerAttribute(48, 3);
                }
                if (ChannelUtil.currentChannel == 106)
                {
                    localGlobalSecurityEngine.setIBCServer("mobile.hcitsec.com:81");
                    GlobalSecurityEngine.setGlobalIntegerAttribute(38, 5);
                    if (ChannelUtil.currentChannel != 106) {
                        break label585;
                    }
                    SetIBCServer("mobile.hcitsec.com", ":81");
                    String str = MjtApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath();
                    paramContext = str;
                    if (str != null) {
                        paramContext = str.substring(0, str.lastIndexOf("/"));
                    }
                    GlobalSecurityEngine.setGlobalStringAttribute(22, paramContext);
                    GlobalSecurityEngine.setGlobalStringAttribute(24, "x.sm9.net.cn:80");
                    GlobalSecurityEngine.setGlobalIntegerAttribute(28, 1);
                    int i = UserSpUtil.getInstanse().getKeyBindStatus();
                    Applog.info("initSecurityEngine post device bindstatus======" + i);
                    if (i == 1)
                    {
                        paramContext = CryptUtils.deviceIdMaterial(MjtApplication.getInstance());
                        Applog.info("initSecurityEngine post device ======" + paramContext);
                        Applog.info("initSecurityEngine post device sha256 deviceID======" + sha256HexDigest(paramContext));
                        localGlobalSecurityEngine.setEngineStringAttribute(23, sha256HexDigest(paramContext));
                    }
                    if (ChannelUtil.action_mjt_ca)
                    {
                        str = MjtApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath();
                        paramContext = str;
                        if (str != null) {
                            paramContext = str.substring(0, str.lastIndexOf("/"));
                        }
                        str = paramContext + "/" + "rootbase.cer";
                        SmsManagerUtils.getInstance().writeFromAsset(paramContext, str, "rootbase.cer");
                        GlobalSecurityEngine.setGlobalStringAttribute(20, new File(str).getParent());
                        GlobalSecurityEngine.setGlobalIntegerAttribute(33, 1);
                        GlobalSecurityEngine.setGlobalIntegerAttribute(44, 1);
                    }
                    CachedThreadPoolUtils.getInstance().execute(new Runnable()
                    {
                        public void run()
                        {
                            for (;;)
                            {
                                if (EngineUtils.this.forceExchangeParameters() == 1) {
                                    return;
                                }
                                try
                                {
                                    Thread.sleep(1000L);
                                }
                                catch (InterruptedException localInterruptedException)
                                {
                                    localInterruptedException.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
            catch (SecurityEngineException paramContext)
            {
                GlobalSecurityEngine localGlobalSecurityEngine;
                Applog.systemOut("----SecurityEngineException---2-- " + paramContext);
                LogFinalUtils.logForException(paramContext);
                return true;
            }
            try
            {
                this.securityEngine = SecurityEngine.getInstance();
                if (ChannelUtil.currentChannel == 106) {
                    this.securityEngine.setJavathrowException(true);
                }
                Applog.systemOut("----------securityEngine------- " + this.securityEngine.GetModuleInfo());
                Applog.info("----------securityEngine------- " + this.securityEngine.GetModuleInfo());
                if (ChannelUtil.action_TF)
                {
                    GlobalSecurityEngine.setGlobalIntegerAttribute(43, 1);
                    bool = initCipherHw(MjtApplication.getInstance());
                }
                return bool;
            }
            catch (SecurityEngineException paramContext)
            {
                Applog.systemOut("----SecurityEngineException--1--- " + paramContext);
                LogFinalUtils.logForException(paramContext);
            }
            localGlobalSecurityEngine.setIBCServer(UserSpUtil.getInstanse().getUserUrl() + UserSpUtil.getInstanse().getUserPort());
            continue;
            label585:
            SetIBCServer(AppConfig.SERVER_IP, UserSpUtil.getInstanse().getUserPort());
        }
        return true;
    }

    public boolean initSecurityEngineFromUrl(String paramString1, String paramString2)
    {
        String str;
        if (paramString2.contains(":"))
        {
            paramString1 = paramString2.substring(0, paramString2.lastIndexOf(":"));
            str = paramString2.substring(paramString2.lastIndexOf(":"), paramString2.length());
            paramString2 = paramString1;
        }
        for (paramString1 = str;; paramString1 = ":443")
        {
            UserSpUtil.getInstanse().setUserIP(paramString2);
            UserSpUtil.getInstanse().setUserUrl(paramString2);
            UserSpUtil.getInstanse().setUserPort(paramString1);
            UserSpUtil.getInstanse().setIpCheckDate(System.currentTimeMillis());
            AppConfig.init();
            return getInstance().initSecurityEngine(MjtApplication.getInstance());
        }
    }

    public int loginLocalDeviceMultiEx(String paramString1, String paramString2, String paramString3, int paramInt)
            throws SecEngineException
    {
        try
        {
            this.securityEngine.LoginLocalDeviceMultiEx(1, paramString1, paramString2, paramString3, paramInt);
            this.securityEngine.CheckPrivatePeriod("", new int[] { 0 });
            return 1;
        }
        catch (SecurityEngineException paramString1)
        {
            LogFinalUtils.logForException(paramString1);
        }
        return -1;
    }

    public void logoutDevice()
            throws SecEngineException
    {
        try
        {
            this.securityEngine.Logout();
            return;
        }
        catch (SecurityEngineException localSecurityEngineException)
        {
            LogFinalUtils.logForException(localSecurityEngineException);
            throw new SecEngineException(localSecurityEngineException.getStatus());
        }
    }

    public void openLog()
    {
        String str = MjtApplication.getInstance().mLogDir + File.separator + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime()) + "_Security.txt";
        GlobalSecurityEngine.getInstance();
        try
        {
            GlobalSecurityEngine.setDebugMode(255, 255, str);
            return;
        }
        catch (SecEngineException localSecEngineException)
        {
            localSecEngineException.printStackTrace();
        }
    }

    public void registerMobileIdEx(String paramString1, String paramString2)
            throws SecurityEngineException
    {
        ensureInit();
        this.securityEngine.RegisterMailIdEx(paramString1, paramString2, "");
    }

    public void reportDeviceSerialInfo(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        ensureInit();
        try
        {
            this.securityEngine.ReportDeviceSerialInfo(paramString1, paramString2, paramString3, paramString4);
            return;
        }
        catch (SecurityEngineException paramString1)
        {
            paramString1.printStackTrace();
        }
    }

    public void resetMembetPwdByAuthCode(String paramString1, String paramString2, String paramString3)
            throws SecurityEngineException
    {
        ensureInit();
        this.securityEngine.ResetMemberPwdByAuthCode(paramString1, paramString2, 1, "", paramString3);
    }

    public void setAreaCode(String paramString)
    {
        Applog.info("-----setAreaCode---- " + paramString);
        Applog.systemOut("------setAreaCode----- " + paramString);
        try
        {
            GlobalSecurityEngine.setGlobalStringAttribute(42, paramString);
            return;
        }
        catch (SecurityEngineException paramString)
        {
            paramString.printStackTrace();
        }
    }

    public void setEngineStringAttribute(int paramInt, String paramString)
            throws SecEngineException
    {
        ensureInit();
        try
        {
            this.securityEngine.SetEngineStringAttribute(paramInt, paramString);
            return;
        }
        catch (SecurityEngineException paramString)
        {
            paramString = new SecEngineException(paramString.getStatus());
            Log.e("GifHeaderParser", "setEngineStringAttribute", paramString);
            throw paramString;
        }
    }

    public void setIBCServer(String paramString)
    {
        GlobalSecurityEngine localGlobalSecurityEngine = GlobalSecurityEngine.getInstance();
        try
        {
            localGlobalSecurityEngine.setIBCServer(paramString);
            localGlobalSecurityEngine.initSecurityEngine(MjtApplication.getInstance(), false);
            GlobalSecurityEngine.setGlobalIntegerAttribute(38, 15);
            return;
        }
        catch (SecEngineException paramString)
        {
            paramString.printStackTrace();
            return;
        }
        catch (SecurityEngineException paramString)
        {
            paramString.printStackTrace();
        }
    }

    public String sha256HexDigest(String paramString)
    {
        try
        {
            Object localObject = MessageDigest.getInstance("sha-256");
            ((MessageDigest)localObject).update(paramString.getBytes());
            byte[] arrayOfByte = ((MessageDigest)localObject).digest();
            StringBuffer localStringBuffer = new StringBuffer();
            int i = 0;
            while (i < arrayOfByte.length)
            {
                String str = Integer.toHexString(arrayOfByte[i] & 0xFF);
                localObject = str;
                if (str.length() == 1) {
                    localObject = "0" + str;
                }
                localStringBuffer.append((String)localObject);
                i += 1;
            }
            localObject = localStringBuffer.toString();
            return (String)localObject;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
        return paramString;
    }

    public void signFile(String paramString1, int paramInt, String paramString2)
            throws SecurityEngineException
    {
        ensureInit();
        this.securityEngine.SignFile(paramString1, paramInt, paramString2);
    }

    public void sm9P7DataDecrypt(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, int[] paramArrayOfInt)
            throws SecurityEngineException
    {
        ensureInit();
        this.securityEngine.SM9P7DataDecrypt(paramArrayOfByte1, paramInt, paramArrayOfByte2, paramArrayOfInt);
    }

    public String verifySignFile(String paramString1, String paramString2, String paramString3)
            throws SecurityEngineException
    {
        ensureInit();
        return this.securityEngine.VerifySignFile(paramString1, paramString2, paramString3);
    }
}

