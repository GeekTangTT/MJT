package com.olym.mjt.glide;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class UnsafeOkHttpClient
{
    /* Error */
    public static okhttp3.OkHttpClient getUnsafeOkHttpClient()
    {
        // Byte code:
        //   0: aconst_null
        //   1: astore_0
        //   2: ldc 19
        //   4: invokestatic 25	javax/net/ssl/SSLContext:getInstance	(Ljava/lang/String;)Ljavax/net/ssl/SSLContext;
        //   7: astore_1
        //   8: aload_1
        //   9: astore_0
        //   10: new 27	com/olym/mjt/network/ssl/TrustAnyTrustManager
        //   13: dup
        //   14: invokespecial 28	com/olym/mjt/network/ssl/TrustAnyTrustManager:<init>	()V
        //   17: astore_1
        //   18: new 30	java/security/SecureRandom
        //   21: dup
        //   22: invokespecial 31	java/security/SecureRandom:<init>	()V
        //   25: astore_2
        //   26: aload_0
        //   27: aconst_null
        //   28: iconst_1
        //   29: anewarray 33	javax/net/ssl/TrustManager
        //   32: dup
        //   33: iconst_0
        //   34: aload_1
        //   35: aastore
        //   36: aload_2
        //   37: invokevirtual 37	javax/net/ssl/SSLContext:init	([Ljavax/net/ssl/KeyManager;[Ljavax/net/ssl/TrustManager;Ljava/security/SecureRandom;)V
        //   40: new 39	okhttp3/OkHttpClient$Builder
        //   43: dup
        //   44: invokespecial 40	okhttp3/OkHttpClient$Builder:<init>	()V
        //   47: ldc2_w 41
        //   50: getstatic 48	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
        //   53: invokevirtual 52	okhttp3/OkHttpClient$Builder:connectTimeout	(JLjava/util/concurrent/TimeUnit;)Lokhttp3/OkHttpClient$Builder;
        //   56: ldc2_w 41
        //   59: getstatic 48	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
        //   62: invokevirtual 55	okhttp3/OkHttpClient$Builder:readTimeout	(JLjava/util/concurrent/TimeUnit;)Lokhttp3/OkHttpClient$Builder;
        //   65: aload_0
        //   66: invokevirtual 59	javax/net/ssl/SSLContext:getSocketFactory	()Ljavax/net/ssl/SSLSocketFactory;
        //   69: invokevirtual 63	okhttp3/OkHttpClient$Builder:sslSocketFactory	(Ljavax/net/ssl/SSLSocketFactory;)Lokhttp3/OkHttpClient$Builder;
        //   72: new 6	com/olym/mjt/glide/UnsafeOkHttpClient$1
        //   75: dup
        //   76: invokespecial 64	com/olym/mjt/glide/UnsafeOkHttpClient$1:<init>	()V
        //   79: invokevirtual 68	okhttp3/OkHttpClient$Builder:hostnameVerifier	(Ljavax/net/ssl/HostnameVerifier;)Lokhttp3/OkHttpClient$Builder;
        //   82: invokevirtual 71	okhttp3/OkHttpClient$Builder:build	()Lokhttp3/OkHttpClient;
        //   85: areturn
        //   86: astore_1
        //   87: aload_1
        //   88: invokevirtual 74	java/security/NoSuchAlgorithmException:printStackTrace	()V
        //   91: goto -81 -> 10
        //   94: astore_1
        //   95: aload_1
        //   96: invokevirtual 75	java/security/KeyManagementException:printStackTrace	()V
        //   99: goto -59 -> 40
        // Local variable table:
        //   start	length	slot	name	signature
        //   1	65	0	localObject1	Object
        //   7	28	1	localObject2	Object
        //   86	2	1	localNoSuchAlgorithmException	java.security.NoSuchAlgorithmException
        //   94	2	1	localKeyManagementException	java.security.KeyManagementException
        //   25	12	2	localSecureRandom	java.security.SecureRandom
        // Exception table:
        //   from	to	target	type
        //   2	8	86	java/security/NoSuchAlgorithmException
        //   10	40	94	java/security/KeyManagementException
    }
}
