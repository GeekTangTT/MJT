package com.olym.mjt.utils;

public class RootUtils
{
    /* Error */
    public static boolean RootCommand(String paramString)
    {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: aconst_null
        //   3: astore_2
        //   4: aconst_null
        //   5: astore 5
        //   7: aconst_null
        //   8: astore 6
        //   10: invokestatic 19	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
        //   13: ldc 21
        //   15: invokevirtual 25	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
        //   18: astore_1
        //   19: aload_1
        //   20: astore_2
        //   21: aload_1
        //   22: astore_3
        //   23: new 27	java/io/DataOutputStream
        //   26: dup
        //   27: aload_1
        //   28: invokevirtual 33	java/lang/Process:getOutputStream	()Ljava/io/OutputStream;
        //   31: invokespecial 36	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
        //   34: astore 4
        //   36: aload 4
        //   38: new 38	java/lang/StringBuilder
        //   41: dup
        //   42: invokespecial 39	java/lang/StringBuilder:<init>	()V
        //   45: aload_0
        //   46: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   49: ldc 45
        //   51: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   54: invokevirtual 49	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   57: invokevirtual 53	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
        //   60: aload 4
        //   62: ldc 55
        //   64: invokevirtual 53	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
        //   67: aload 4
        //   69: invokevirtual 58	java/io/DataOutputStream:flush	()V
        //   72: aload_1
        //   73: invokevirtual 62	java/lang/Process:waitFor	()I
        //   76: pop
        //   77: aload 4
        //   79: ifnull +8 -> 87
        //   82: aload 4
        //   84: invokevirtual 65	java/io/DataOutputStream:close	()V
        //   87: aload_1
        //   88: invokevirtual 68	java/lang/Process:destroy	()V
        //   91: iconst_1
        //   92: ireturn
        //   93: astore_0
        //   94: aload_2
        //   95: astore_1
        //   96: aload 6
        //   98: astore_0
        //   99: aload_0
        //   100: ifnull +7 -> 107
        //   103: aload_0
        //   104: invokevirtual 65	java/io/DataOutputStream:close	()V
        //   107: aload_1
        //   108: invokevirtual 68	java/lang/Process:destroy	()V
        //   111: iconst_0
        //   112: ireturn
        //   113: astore_0
        //   114: iconst_0
        //   115: ireturn
        //   116: astore_0
        //   117: aload_3
        //   118: astore_1
        //   119: aload 5
        //   121: astore_2
        //   122: aload_2
        //   123: ifnull +7 -> 130
        //   126: aload_2
        //   127: invokevirtual 65	java/io/DataOutputStream:close	()V
        //   130: aload_1
        //   131: invokevirtual 68	java/lang/Process:destroy	()V
        //   134: aload_0
        //   135: athrow
        //   136: astore_1
        //   137: goto -3 -> 134
        //   140: astore_0
        //   141: aload 4
        //   143: astore_2
        //   144: goto -22 -> 122
        //   147: astore_0
        //   148: aload 4
        //   150: astore_0
        //   151: goto -52 -> 99
        //   154: astore_0
        //   155: goto -64 -> 91
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	158	0	paramString	String
        //   18	113	1	localObject1	Object
        //   136	1	1	localException	Exception
        //   3	141	2	localObject2	Object
        //   1	117	3	localObject3	Object
        //   34	115	4	localDataOutputStream	java.io.DataOutputStream
        //   5	115	5	localObject4	Object
        //   8	89	6	localObject5	Object
        // Exception table:
        //   from	to	target	type
        //   10	19	93	java/lang/Exception
        //   23	36	93	java/lang/Exception
        //   103	107	113	java/lang/Exception
        //   107	111	113	java/lang/Exception
        //   10	19	116	finally
        //   23	36	116	finally
        //   126	130	136	java/lang/Exception
        //   130	134	136	java/lang/Exception
        //   36	77	140	finally
        //   36	77	147	java/lang/Exception
        //   82	87	154	java/lang/Exception
        //   87	91	154	java/lang/Exception
    }
}
