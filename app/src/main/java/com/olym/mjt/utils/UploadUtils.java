package com.olym.mjt.utils;

import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.UploadTask.FileAuth.NiscHostnameVerifier;
import com.olym.mjt.network.ssl.TrustAnyTrustManager;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class UploadUtils
{
    private static final String CHARSET = "utf-8";
    private static final int TIME_OUT = 10000;

    public static String uploadFile(File paramFile, String paramString)
    {
        Object localObject6 = null;
        Object localObject5 = null;
        String str = UUID.randomUUID().toString();
        Object localObject3 = null;
        Object localObject4;
        try
        {
            Object localObject1 = SSLContext.getInstance("TLS");
            localObject3 = localObject1;
            Object localObject7;
            paramString = (String)localObject5;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
        {
            try
            {
                for (;;)
                {
                    localObject1 = new TrustAnyTrustManager();
                    localObject4 = new SecureRandom();
                    ((SSLContext)localObject3).init(null, new TrustManager[] { localObject1 }, (SecureRandom)localObject4);
                    localObject4 = null;
                    localObject1 = localObject6;
                    try
                    {
                        paramString = new URL(paramString);
                        localObject4 = paramString;
                    }
                    catch (MalformedURLException localMalformedURLException)
                    {
                        for (;;)
                        {
                            paramString = (String)localObject5;
                            localObject2 = localObject6;
                            localMalformedURLException.printStackTrace();
                        }
                    }
                    catch (IOException paramFile)
                    {
                        paramFile.printStackTrace();
                        return (String)localObject2;
                    }
                    paramString = (String)localObject5;
                    localObject1 = localObject6;
                    try
                    {
                        HttpsURLConnection.setDefaultSSLSocketFactory(((SSLContext)localObject3).getSocketFactory());
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        HttpsURLConnection.setDefaultHostnameVerifier(new FileAuth.NiscHostnameVerifier());
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        localObject3 = (HttpsURLConnection)((URL)localObject4).openConnection();
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((HttpsURLConnection)localObject3).setReadTimeout(10000);
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((HttpsURLConnection)localObject3).setConnectTimeout(10000);
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((HttpsURLConnection)localObject3).setDoInput(true);
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((HttpsURLConnection)localObject3).setDoOutput(true);
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((HttpsURLConnection)localObject3).setUseCaches(false);
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((HttpsURLConnection)localObject3).setRequestMethod("POST");
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((HttpsURLConnection)localObject3).setRequestProperty("Charset", "utf-8");
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((HttpsURLConnection)localObject3).setRequestProperty("connection", "keep-alive");
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((HttpsURLConnection)localObject3).setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + str);
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((HttpsURLConnection)localObject3).setRequestProperty("user", MjtApplication.getInstance().getLoginUser().getTelephone());
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((HttpsURLConnection)localObject3).setRequestProperty("note", "lalala");
                        if (paramFile == null) {
                            break label916;
                        }
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        Log.e("nail", "file is not null");
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        localObject4 = new DataOutputStream(((HttpsURLConnection)localObject3).getOutputStream());
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        localObject7 = new StringBuffer();
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((StringBuffer)localObject7).append("--");
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((StringBuffer)localObject7).append(str);
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((StringBuffer)localObject7).append("\r\n");
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((StringBuffer)localObject7).append("Content-Disposition: form-data; name=\"file\"; filename=\"" + paramFile.getName() + "\"" + "\r\n");
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((StringBuffer)localObject7).append("Content-Type: application/octet-stream; charset=utf-8" + "\r\n");
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((StringBuffer)localObject7).append("user").append("=").append(MjtApplication.getInstance().getLoginUser().getTelephone()).append("&").append("note").append("=").append("lalala");
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((StringBuffer)localObject7).append("\r\n");
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        ((DataOutputStream)localObject4).write(((StringBuffer)localObject7).toString().getBytes());
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        paramFile = new FileInputStream(paramFile);
                        paramString = (String)localObject5;
                        localObject1 = localObject6;
                        localObject7 = new byte['��'];
                        for (;;)
                        {
                            paramString = (String)localObject5;
                            localObject1 = localObject6;
                            i = paramFile.read((byte[])localObject7);
                            if (i == -1) {
                                break;
                            }
                            paramString = (String)localObject5;
                            localObject1 = localObject6;
                            ((DataOutputStream)localObject4).write((byte[])localObject7, 0, i);
                        }
                        localNoSuchAlgorithmException = localNoSuchAlgorithmException;
                    }
                    catch (MalformedURLException paramFile)
                    {
                        paramFile.printStackTrace();
                        return paramString;
                    }
                    LogFinalUtils.logForException(localNoSuchAlgorithmException);
                    localNoSuchAlgorithmException.printStackTrace();
                }
            }
            catch (KeyManagementException localKeyManagementException)
            {
                for (;;)
                {
                    localKeyManagementException.printStackTrace();
                    LogFinalUtils.logForException(localKeyManagementException);
                }
            }
        }
        Object localObject2 = localObject6;
        paramFile.close();
        paramString = (String)localObject5;
        localObject2 = localObject6;
        ((DataOutputStream)localObject4).write("\r\n".getBytes());
        paramString = (String)localObject5;
        localObject2 = localObject6;
        ((DataOutputStream)localObject4).write(("--" + str + "--" + "\r\n").getBytes());
        paramString = (String)localObject5;
        localObject2 = localObject6;
        ((DataOutputStream)localObject4).flush();
        paramString = (String)localObject5;
        localObject2 = localObject6;
        int i = ((HttpsURLConnection)localObject3).getResponseCode();
        paramString = (String)localObject5;
        localObject2 = localObject6;
        Log.e("nail", "response code:" + i);
        paramString = (String)localObject5;
        localObject2 = localObject6;
        Log.e("nail", "request success");
        paramString = (String)localObject5;
        localObject2 = localObject6;
        paramFile = ((HttpsURLConnection)localObject3).getInputStream();
        paramString = (String)localObject5;
        localObject2 = localObject6;
        localObject3 = new StringBuffer();
        for (;;)
        {
            paramString = (String)localObject5;
            localObject2 = localObject6;
            i = paramFile.read();
            if (i == -1) {
                break;
            }
            paramString = (String)localObject5;
            localObject2 = localObject6;
            ((StringBuffer)localObject3).append((char)i);
        }
        paramString = (String)localObject5;
        localObject2 = localObject6;
        paramFile = ((StringBuffer)localObject3).toString();
        paramString = paramFile;
        localObject2 = paramFile;
        Log.e("nail", "result : " + paramFile);
        return paramFile;
        label916:
        paramString = (String)localObject5;
        localObject2 = localObject6;
        Log.e("nail", "file is null");
        return null;
    }
}
