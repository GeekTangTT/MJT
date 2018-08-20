package org.xutils.http.app;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

public class DefaultParamsBuilder
        implements ParamsBuilder
{
    private static SSLSocketFactory trustAllSSlSocketFactory;

    /* Error */
    public static SSLSocketFactory getTrustAllSSLSocketFactory()
    {
        // Byte code:
        //   0: getstatic 21	org/xutils/http/app/DefaultParamsBuilder:trustAllSSlSocketFactory	Ljavax/net/ssl/SSLSocketFactory;
        //   3: ifnonnull +50 -> 53
        //   6: ldc 2
        //   8: monitorenter
        //   9: getstatic 21	org/xutils/http/app/DefaultParamsBuilder:trustAllSSlSocketFactory	Ljavax/net/ssl/SSLSocketFactory;
        //   12: ifnonnull +38 -> 50
        //   15: new 8	org/xutils/http/app/DefaultParamsBuilder$1
        //   18: dup
        //   19: invokespecial 22	org/xutils/http/app/DefaultParamsBuilder$1:<init>	()V
        //   22: astore_0
        //   23: ldc 24
        //   25: invokestatic 30	javax/net/ssl/SSLContext:getInstance	(Ljava/lang/String;)Ljavax/net/ssl/SSLContext;
        //   28: astore_1
        //   29: aload_1
        //   30: aconst_null
        //   31: iconst_1
        //   32: anewarray 32	javax/net/ssl/TrustManager
        //   35: dup
        //   36: iconst_0
        //   37: aload_0
        //   38: aastore
        //   39: aconst_null
        //   40: invokevirtual 36	javax/net/ssl/SSLContext:init	([Ljavax/net/ssl/KeyManager;[Ljavax/net/ssl/TrustManager;Ljava/security/SecureRandom;)V
        //   43: aload_1
        //   44: invokevirtual 39	javax/net/ssl/SSLContext:getSocketFactory	()Ljavax/net/ssl/SSLSocketFactory;
        //   47: putstatic 21	org/xutils/http/app/DefaultParamsBuilder:trustAllSSlSocketFactory	Ljavax/net/ssl/SSLSocketFactory;
        //   50: ldc 2
        //   52: monitorexit
        //   53: getstatic 21	org/xutils/http/app/DefaultParamsBuilder:trustAllSSlSocketFactory	Ljavax/net/ssl/SSLSocketFactory;
        //   56: areturn
        //   57: astore_0
        //   58: aload_0
        //   59: invokevirtual 43	java/lang/Throwable:getMessage	()Ljava/lang/String;
        //   62: aload_0
        //   63: invokestatic 49	org/xutils/common/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   66: goto -16 -> 50
        //   69: astore_0
        //   70: ldc 2
        //   72: monitorexit
        //   73: aload_0
        //   74: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   22	16	0	local1	1
        //   57	6	0	localThrowable	Throwable
        //   69	5	0	localObject	Object
        //   28	16	1	localSSLContext	javax.net.ssl.SSLContext
        // Exception table:
        //   from	to	target	type
        //   23	50	57	java/lang/Throwable
        //   9	23	69	finally
        //   23	50	69	finally
        //   50	53	69	finally
        //   58	66	69	finally
        //   70	73	69	finally
    }

    public String buildCacheKey(RequestParams paramRequestParams, String[] paramArrayOfString)
    {
        String str1 = null;
        Object localObject = str1;
        if (paramArrayOfString != null)
        {
            localObject = str1;
            if (paramArrayOfString.length > 0)
            {
                str1 = paramRequestParams.getUri() + "?";
                HashMap localHashMap = paramRequestParams.getQueryStringParams();
                localObject = str1;
                if (localHashMap != null)
                {
                    int j = paramArrayOfString.length;
                    int i = 0;
                    for (paramRequestParams = str1;; paramRequestParams = (RequestParams)localObject)
                    {
                        localObject = paramRequestParams;
                        if (i >= j) {
                            break;
                        }
                        str1 = paramArrayOfString[i];
                        String str2 = (String)localHashMap.get(str1);
                        localObject = paramRequestParams;
                        if (str2 != null) {
                            localObject = paramRequestParams + str1 + "=" + str2 + "&";
                        }
                        i += 1;
                    }
                }
            }
        }
        return (String)localObject;
    }

    public void buildParams(RequestParams paramRequestParams) {}

    public void buildSign(RequestParams paramRequestParams, String[] paramArrayOfString) {}

    public String buildUri(HttpRequest paramHttpRequest)
    {
        return paramHttpRequest.host() + "/" + paramHttpRequest.path();
    }

    public SSLSocketFactory getSSLSocketFactory()
    {
        return getTrustAllSSLSocketFactory();
    }
}

