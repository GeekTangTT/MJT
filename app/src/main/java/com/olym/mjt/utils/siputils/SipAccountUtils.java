package com.olym.mjt.utils.siputils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.text.TextUtils;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.pjsip.sip.api.SipProfile;
import com.olym.mjt.pjsip.sip.models.Accounts;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import java.util.UUID;

public class SipAccountUtils
{
    private static final String[] ACC_PROJECTION = { "id", "acc_id", "reg_uri", "proxy", "default_uri_scheme", "display_name", "wizard" };

    private static void applyNewAccountDefault(SipProfile paramSipProfile)
    {
        if ((paramSipProfile.use_rfc5626) && (TextUtils.isEmpty(paramSipProfile.rfc5626_instance_id)))
        {
            String str = UUID.randomUUID().toString();
            paramSipProfile.rfc5626_instance_id = ("<urn:uuid:" + str + ">");
        }
    }

    /* Error */
    public static void checkAccount(Context paramContext, String paramString1, String paramString2, String paramString3)
    {
        // Byte code:
        //   0: aload_0
        //   1: invokevirtual 80	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
        //   4: getstatic 84	com/olym/mjt/pjsip/sip/api/SipProfile:ACCOUNT_URI	Landroid/net/Uri;
        //   7: getstatic 89	com/olym/mjt/pjsip/sip/db/DBProvider:ACCOUNT_FULL_PROJECTION	[Ljava/lang/String;
        //   10: ldc 91
        //   12: iconst_1
        //   13: anewarray 10	java/lang/String
        //   16: dup
        //   17: iconst_0
        //   18: ldc 93
        //   20: aastore
        //   21: aconst_null
        //   22: invokevirtual 99	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
        //   25: astore 4
        //   27: aload 4
        //   29: ifnull +45 -> 74
        //   32: aload 4
        //   34: invokeinterface 105 1 0
        //   39: ifle +36 -> 75
        //   42: aload 4
        //   44: invokeinterface 109 1 0
        //   49: pop
        //   50: new 34	com/olym/mjt/pjsip/sip/api/SipProfile
        //   53: dup
        //   54: aload 4
        //   56: invokespecial 112	com/olym/mjt/pjsip/sip/api/SipProfile:<init>	(Landroid/database/Cursor;)V
        //   59: astore_0
        //   60: invokestatic 118	com/olym/mjt/module/MjtApplication:getInstance	()Lcom/olym/mjt/module/MjtApplication;
        //   63: aload_0
        //   64: invokevirtual 121	com/olym/mjt/module/MjtApplication:setSipProfile	(Lcom/olym/mjt/pjsip/sip/api/SipProfile;)V
        //   67: aload 4
        //   69: invokeinterface 124 1 0
        //   74: return
        //   75: aload_0
        //   76: ldc 126
        //   78: aload_1
        //   79: aload_2
        //   80: aload_3
        //   81: invokestatic 130	com/olym/mjt/utils/siputils/SipAccountUtils:saveAccount	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/olym/mjt/pjsip/sip/api/SipProfile;
        //   84: astore_0
        //   85: goto -25 -> 60
        //   88: astore_0
        //   89: aload_0
        //   90: invokestatic 136	com/lc/methodex/LogFinalUtils:logForException	(Ljava/lang/Throwable;)V
        //   93: new 138	com/olym/mjt/databean/event/ExceptionLogoutEvent
        //   96: dup
        //   97: ldc -116
        //   99: invokespecial 143	com/olym/mjt/databean/event/ExceptionLogoutEvent:<init>	(Ljava/lang/String;)V
        //   102: invokestatic 147	com/olym/mjt/databean/event/ExceptionLogoutEvent:post	(Lcom/olym/mjt/databean/event/ExceptionLogoutEvent;)V
        //   105: aload 4
        //   107: invokeinterface 124 1 0
        //   112: return
        //   113: astore_0
        //   114: aload 4
        //   116: invokeinterface 124 1 0
        //   121: aload_0
        //   122: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	123	0	paramContext	Context
        //   0	123	1	paramString1	String
        //   0	123	2	paramString2	String
        //   0	123	3	paramString3	String
        //   25	90	4	localCursor	android.database.Cursor
        // Exception table:
        //   from	to	target	type
        //   32	60	88	java/lang/Exception
        //   60	67	88	java/lang/Exception
        //   75	85	88	java/lang/Exception
        //   32	60	113	finally
        //   60	67	113	finally
        //   75	85	113	finally
        //   89	105	113	finally
    }

    /* Error */
    public static SipProfile getLoginSipProfile(Context paramContext)
    {
        // Byte code:
        //   0: aload_0
        //   1: invokevirtual 80	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
        //   4: getstatic 84	com/olym/mjt/pjsip/sip/api/SipProfile:ACCOUNT_URI	Landroid/net/Uri;
        //   7: getstatic 26	com/olym/mjt/utils/siputils/SipAccountUtils:ACC_PROJECTION	[Ljava/lang/String;
        //   10: ldc 91
        //   12: iconst_1
        //   13: anewarray 10	java/lang/String
        //   16: dup
        //   17: iconst_0
        //   18: ldc 93
        //   20: aastore
        //   21: aconst_null
        //   22: invokevirtual 99	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
        //   25: astore 6
        //   27: aconst_null
        //   28: astore 4
        //   30: aconst_null
        //   31: astore_0
        //   32: aconst_null
        //   33: astore 5
        //   35: aconst_null
        //   36: astore_3
        //   37: aload 6
        //   39: ifnull +74 -> 113
        //   42: aload 4
        //   44: astore_2
        //   45: aload 5
        //   47: astore_0
        //   48: aload 6
        //   50: invokeinterface 105 1 0
        //   55: ifle +49 -> 104
        //   58: aload 5
        //   60: astore_0
        //   61: aload 6
        //   63: invokeinterface 109 1 0
        //   68: istore_1
        //   69: aload 4
        //   71: astore_2
        //   72: iload_1
        //   73: ifeq +31 -> 104
        //   76: aload_3
        //   77: astore_0
        //   78: new 34	com/olym/mjt/pjsip/sip/api/SipProfile
        //   81: dup
        //   82: aload 6
        //   84: invokespecial 112	com/olym/mjt/pjsip/sip/api/SipProfile:<init>	(Landroid/database/Cursor;)V
        //   87: astore_2
        //   88: aload_2
        //   89: astore_0
        //   90: aload 6
        //   92: invokeinterface 152 1 0
        //   97: istore_1
        //   98: aload_2
        //   99: astore_0
        //   100: iload_1
        //   101: ifne -23 -> 78
        //   104: aload 6
        //   106: invokeinterface 124 1 0
        //   111: aload_2
        //   112: astore_0
        //   113: aload_0
        //   114: astore_2
        //   115: aload_0
        //   116: ifnonnull +18 -> 134
        //   119: new 34	com/olym/mjt/pjsip/sip/api/SipProfile
        //   122: dup
        //   123: invokespecial 153	com/olym/mjt/pjsip/sip/api/SipProfile:<init>	()V
        //   126: astore_2
        //   127: aload_2
        //   128: ldc2_w 154
        //   131: putfield 158	com/olym/mjt/pjsip/sip/api/SipProfile:id	J
        //   134: aload_2
        //   135: areturn
        //   136: astore_2
        //   137: aload_2
        //   138: invokestatic 136	com/lc/methodex/LogFinalUtils:logForException	(Ljava/lang/Throwable;)V
        //   141: aload 6
        //   143: invokeinterface 124 1 0
        //   148: goto -35 -> 113
        //   151: astore_0
        //   152: aload 6
        //   154: invokeinterface 124 1 0
        //   159: aload_0
        //   160: athrow
        //   161: astore_0
        //   162: goto -10 -> 152
        //   165: astore_2
        //   166: goto -29 -> 137
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	169	0	paramContext	Context
        //   68	33	1	bool	boolean
        //   44	91	2	localObject1	Object
        //   136	2	2	localException1	Exception
        //   165	1	2	localException2	Exception
        //   36	41	3	localObject2	Object
        //   28	42	4	localObject3	Object
        //   33	26	5	localObject4	Object
        //   25	128	6	localCursor	android.database.Cursor
        // Exception table:
        //   from	to	target	type
        //   48	58	136	java/lang/Exception
        //   61	69	136	java/lang/Exception
        //   90	98	136	java/lang/Exception
        //   48	58	151	finally
        //   61	69	151	finally
        //   90	98	151	finally
        //   137	141	151	finally
        //   78	88	161	finally
        //   78	88	165	java/lang/Exception
    }

    private static SipProfile saveAccount(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
    {
        paramString2 = Accounts.buildAccount(paramString2, paramString3, paramString4);
        paramString2.wizard = paramString1;
        Applog.systemOut("--------saveAccount----- " + paramString2.id);
        if (paramString2.id == -1L)
        {
            applyNewAccountDefault(paramString2);
            paramString2.id = ContentUris.parseId(paramContext.getContentResolver().insert(SipProfile.ACCOUNT_URI, paramString2.getDbContentValues()));
            return paramString2;
        }
        Applog.systemOut("-----account------ " + paramString2.getDbContentValues());
        paramContext.getContentResolver().update(ContentUris.withAppendedId(SipProfile.ACCOUNT_ID_URI_BASE, paramString2.id), paramString2.getDbContentValues(), null, null);
        return paramString2;
    }

    public static void updateAccount(Context paramContext)
    {
        Applog.systemOut("_---SIP---updateAccount----");
        SipProfile localSipProfile = MjtApplication.getInstance().getSipProfile();
        if (localSipProfile != null)
        {
            paramContext.getContentResolver().delete(SipProfile.ACCOUNT_URI, null, null);
            paramContext = saveAccount(paramContext, localSipProfile.wizard, UserSpUtil.getInstanse().getPhone(), UserSpUtil.getInstanse().getPassword(), UserSpUtil.getInstanse().getIBCDomain());
            MjtApplication.getInstance().setSipProfile(paramContext);
            Applog.systemOut("-----sipProfile---- " + paramContext.reg_uri);
            Applog.info("-----sipProfile---- " + paramContext.reg_uri);
        }
    }
}

