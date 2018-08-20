package com.olym.mjt.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileIOUtils
{
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static int sBufferSize = 8192;

    private FileIOUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static boolean createOrExistsDir(File paramFile)
    {
        if (paramFile != null) {
            if (paramFile.exists())
            {
                if (!paramFile.isDirectory()) {}
            }
            else {
                while (paramFile.mkdirs()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean createOrExistsFile(File paramFile)
    {
        if (paramFile == null) {}
        do
        {
            return false;
            if (paramFile.exists()) {
                return paramFile.isFile();
            }
        } while (!createOrExistsDir(paramFile.getParentFile()));
        try
        {
            boolean bool = paramFile.createNewFile();
            return bool;
        }
        catch (IOException paramFile)
        {
            paramFile.printStackTrace();
        }
        return false;
    }

    private static boolean createOrExistsFile(String paramString)
    {
        return createOrExistsFile(getFileByPath(paramString));
    }

    private static File getFileByPath(String paramString)
    {
        if (isSpace(paramString)) {
            return null;
        }
        return new File(paramString);
    }

    private static boolean isFileExists(File paramFile)
    {
        return (paramFile != null) && (paramFile.exists());
    }

    private static boolean isSpace(String paramString)
    {
        if (paramString == null) {}
        for (;;)
        {
            return true;
            int i = 0;
            int j = paramString.length();
            while (i < j)
            {
                if (!Character.isWhitespace(paramString.charAt(i))) {
                    return false;
                }
                i += 1;
            }
        }
    }

    /* Error */
    public static byte[] readFile2BytesByChannel(File paramFile)
    {
        // Byte code:
        //   0: aload_0
        //   1: invokestatic 97	com/olym/mjt/utils/FileIOUtils:isFileExists	(Ljava/io/File;)Z
        //   4: ifne +5 -> 9
        //   7: aconst_null
        //   8: areturn
        //   9: aconst_null
        //   10: astore_2
        //   11: aconst_null
        //   12: astore_1
        //   13: new 99	java/io/RandomAccessFile
        //   16: dup
        //   17: aload_0
        //   18: ldc 101
        //   20: invokespecial 104	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
        //   23: invokevirtual 108	java/io/RandomAccessFile:getChannel	()Ljava/nio/channels/FileChannel;
        //   26: astore_0
        //   27: aload_0
        //   28: astore_1
        //   29: aload_0
        //   30: astore_2
        //   31: aload_0
        //   32: invokevirtual 114	java/nio/channels/FileChannel:size	()J
        //   35: l2i
        //   36: invokestatic 120	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
        //   39: astore_3
        //   40: aload_0
        //   41: astore_1
        //   42: aload_0
        //   43: astore_2
        //   44: aload_0
        //   45: aload_3
        //   46: invokevirtual 124	java/nio/channels/FileChannel:read	(Ljava/nio/ByteBuffer;)I
        //   49: ifgt -9 -> 40
        //   52: aload_0
        //   53: astore_1
        //   54: aload_0
        //   55: astore_2
        //   56: aload_3
        //   57: invokevirtual 128	java/nio/ByteBuffer:array	()[B
        //   60: astore_3
        //   61: iconst_1
        //   62: anewarray 130	java/io/Closeable
        //   65: dup
        //   66: iconst_0
        //   67: aload_0
        //   68: aastore
        //   69: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   72: aload_3
        //   73: areturn
        //   74: astore_0
        //   75: aload_1
        //   76: astore_2
        //   77: aload_0
        //   78: invokevirtual 65	java/io/IOException:printStackTrace	()V
        //   81: iconst_1
        //   82: anewarray 130	java/io/Closeable
        //   85: dup
        //   86: iconst_0
        //   87: aload_1
        //   88: aastore
        //   89: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   92: aconst_null
        //   93: areturn
        //   94: astore_0
        //   95: iconst_1
        //   96: anewarray 130	java/io/Closeable
        //   99: dup
        //   100: iconst_0
        //   101: aload_2
        //   102: aastore
        //   103: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   106: aload_0
        //   107: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	108	0	paramFile	File
        //   12	76	1	localFile	File
        //   10	92	2	localObject1	Object
        //   39	34	3	localObject2	Object
        // Exception table:
        //   from	to	target	type
        //   13	27	74	java/io/IOException
        //   31	40	74	java/io/IOException
        //   44	52	74	java/io/IOException
        //   56	61	74	java/io/IOException
        //   13	27	94	finally
        //   31	40	94	finally
        //   44	52	94	finally
        //   56	61	94	finally
        //   77	81	94	finally
    }

    public static byte[] readFile2BytesByChannel(String paramString)
    {
        return readFile2BytesByChannel(getFileByPath(paramString));
    }

    /* Error */
    public static byte[] readFile2BytesByMap(File paramFile)
    {
        // Byte code:
        //   0: aload_0
        //   1: invokestatic 97	com/olym/mjt/utils/FileIOUtils:isFileExists	(Ljava/io/File;)Z
        //   4: ifne +5 -> 9
        //   7: aconst_null
        //   8: areturn
        //   9: aconst_null
        //   10: astore_3
        //   11: aconst_null
        //   12: astore_2
        //   13: new 99	java/io/RandomAccessFile
        //   16: dup
        //   17: aload_0
        //   18: ldc 101
        //   20: invokespecial 104	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
        //   23: invokevirtual 108	java/io/RandomAccessFile:getChannel	()Ljava/nio/channels/FileChannel;
        //   26: astore_0
        //   27: aload_0
        //   28: astore_2
        //   29: aload_0
        //   30: astore_3
        //   31: aload_0
        //   32: invokevirtual 114	java/nio/channels/FileChannel:size	()J
        //   35: l2i
        //   36: istore_1
        //   37: aload_0
        //   38: astore_2
        //   39: aload_0
        //   40: astore_3
        //   41: aload_0
        //   42: getstatic 146	java/nio/channels/FileChannel$MapMode:READ_ONLY	Ljava/nio/channels/FileChannel$MapMode;
        //   45: lconst_0
        //   46: iload_1
        //   47: i2l
        //   48: invokevirtual 150	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
        //   51: invokevirtual 156	java/nio/MappedByteBuffer:load	()Ljava/nio/MappedByteBuffer;
        //   54: astore 4
        //   56: aload_0
        //   57: astore_2
        //   58: aload_0
        //   59: astore_3
        //   60: iload_1
        //   61: newarray <illegal type>
        //   63: astore 5
        //   65: aload_0
        //   66: astore_2
        //   67: aload_0
        //   68: astore_3
        //   69: aload 4
        //   71: aload 5
        //   73: iconst_0
        //   74: iload_1
        //   75: invokevirtual 160	java/nio/MappedByteBuffer:get	([BII)Ljava/nio/ByteBuffer;
        //   78: pop
        //   79: iconst_1
        //   80: anewarray 130	java/io/Closeable
        //   83: dup
        //   84: iconst_0
        //   85: aload_0
        //   86: aastore
        //   87: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   90: aload 5
        //   92: areturn
        //   93: astore_0
        //   94: aload_2
        //   95: astore_3
        //   96: aload_0
        //   97: invokevirtual 65	java/io/IOException:printStackTrace	()V
        //   100: iconst_1
        //   101: anewarray 130	java/io/Closeable
        //   104: dup
        //   105: iconst_0
        //   106: aload_2
        //   107: aastore
        //   108: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   111: aconst_null
        //   112: areturn
        //   113: astore_0
        //   114: iconst_1
        //   115: anewarray 130	java/io/Closeable
        //   118: dup
        //   119: iconst_0
        //   120: aload_3
        //   121: aastore
        //   122: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   125: aload_0
        //   126: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	127	0	paramFile	File
        //   36	39	1	i	int
        //   12	95	2	localFile	File
        //   10	111	3	localObject	Object
        //   54	16	4	localMappedByteBuffer	java.nio.MappedByteBuffer
        //   63	28	5	arrayOfByte	byte[]
        // Exception table:
        //   from	to	target	type
        //   13	27	93	java/io/IOException
        //   31	37	93	java/io/IOException
        //   41	56	93	java/io/IOException
        //   60	65	93	java/io/IOException
        //   69	79	93	java/io/IOException
        //   13	27	113	finally
        //   31	37	113	finally
        //   41	56	113	finally
        //   60	65	113	finally
        //   69	79	113	finally
        //   96	100	113	finally
    }

    public static byte[] readFile2BytesByMap(String paramString)
    {
        return readFile2BytesByMap(getFileByPath(paramString));
    }

    /* Error */
    public static byte[] readFile2BytesByStream(File paramFile)
    {
        // Byte code:
        //   0: aload_0
        //   1: invokestatic 97	com/olym/mjt/utils/FileIOUtils:isFileExists	(Ljava/io/File;)Z
        //   4: ifne +5 -> 9
        //   7: aconst_null
        //   8: areturn
        //   9: aconst_null
        //   10: astore_2
        //   11: aconst_null
        //   12: astore 6
        //   14: aconst_null
        //   15: astore_3
        //   16: aconst_null
        //   17: astore 4
        //   19: aconst_null
        //   20: astore 5
        //   22: new 165	java/io/FileInputStream
        //   25: dup
        //   26: aload_0
        //   27: invokespecial 168	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   30: astore_0
        //   31: new 170	java/io/ByteArrayOutputStream
        //   34: dup
        //   35: invokespecial 171	java/io/ByteArrayOutputStream:<init>	()V
        //   38: astore_2
        //   39: getstatic 22	com/olym/mjt/utils/FileIOUtils:sBufferSize	I
        //   42: newarray <illegal type>
        //   44: astore_3
        //   45: aload_0
        //   46: aload_3
        //   47: iconst_0
        //   48: getstatic 22	com/olym/mjt/utils/FileIOUtils:sBufferSize	I
        //   51: invokevirtual 174	java/io/FileInputStream:read	([BII)I
        //   54: istore_1
        //   55: iload_1
        //   56: iconst_m1
        //   57: if_icmpeq +46 -> 103
        //   60: aload_2
        //   61: aload_3
        //   62: iconst_0
        //   63: iload_1
        //   64: invokevirtual 178	java/io/ByteArrayOutputStream:write	([BII)V
        //   67: goto -22 -> 45
        //   70: astore 4
        //   72: aload_2
        //   73: astore 5
        //   75: aload_0
        //   76: astore_2
        //   77: aload 5
        //   79: astore_3
        //   80: aload 4
        //   82: invokevirtual 65	java/io/IOException:printStackTrace	()V
        //   85: iconst_2
        //   86: anewarray 130	java/io/Closeable
        //   89: dup
        //   90: iconst_0
        //   91: aload_0
        //   92: aastore
        //   93: dup
        //   94: iconst_1
        //   95: aload 5
        //   97: aastore
        //   98: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   101: aconst_null
        //   102: areturn
        //   103: aload_2
        //   104: invokevirtual 181	java/io/ByteArrayOutputStream:toByteArray	()[B
        //   107: astore_3
        //   108: iconst_2
        //   109: anewarray 130	java/io/Closeable
        //   112: dup
        //   113: iconst_0
        //   114: aload_0
        //   115: aastore
        //   116: dup
        //   117: iconst_1
        //   118: aload_2
        //   119: aastore
        //   120: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   123: aload_3
        //   124: areturn
        //   125: astore_0
        //   126: iconst_2
        //   127: anewarray 130	java/io/Closeable
        //   130: dup
        //   131: iconst_0
        //   132: aload_2
        //   133: aastore
        //   134: dup
        //   135: iconst_1
        //   136: aload_3
        //   137: aastore
        //   138: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   141: aload_0
        //   142: athrow
        //   143: astore 5
        //   145: aload_0
        //   146: astore_2
        //   147: aload 4
        //   149: astore_3
        //   150: aload 5
        //   152: astore_0
        //   153: goto -27 -> 126
        //   156: astore 4
        //   158: aload_2
        //   159: astore_3
        //   160: aload_0
        //   161: astore_2
        //   162: aload 4
        //   164: astore_0
        //   165: goto -39 -> 126
        //   168: astore 4
        //   170: aload 6
        //   172: astore_0
        //   173: goto -98 -> 75
        //   176: astore 4
        //   178: goto -103 -> 75
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	181	0	paramFile	File
        //   54	10	1	i	int
        //   10	152	2	localObject1	Object
        //   15	145	3	localObject2	Object
        //   17	1	4	localObject3	Object
        //   70	78	4	localIOException1	IOException
        //   156	7	4	localObject4	Object
        //   168	1	4	localIOException2	IOException
        //   176	1	4	localIOException3	IOException
        //   20	76	5	localObject5	Object
        //   143	8	5	localObject6	Object
        //   12	159	6	localObject7	Object
        // Exception table:
        //   from	to	target	type
        //   39	45	70	java/io/IOException
        //   45	55	70	java/io/IOException
        //   60	67	70	java/io/IOException
        //   103	108	70	java/io/IOException
        //   22	31	125	finally
        //   80	85	125	finally
        //   31	39	143	finally
        //   39	45	156	finally
        //   45	55	156	finally
        //   60	67	156	finally
        //   103	108	156	finally
        //   22	31	168	java/io/IOException
        //   31	39	176	java/io/IOException
    }

    public static byte[] readFile2BytesByStream(String paramString)
    {
        return readFile2BytesByStream(getFileByPath(paramString));
    }

    public static List<String> readFile2List(File paramFile)
    {
        return readFile2List(paramFile, 0, Integer.MAX_VALUE, null);
    }

    public static List<String> readFile2List(File paramFile, int paramInt1, int paramInt2)
    {
        return readFile2List(paramFile, paramInt1, paramInt2, null);
    }

    /* Error */
    public static List<String> readFile2List(File paramFile, int paramInt1, int paramInt2, String paramString)
    {
        // Byte code:
        //   0: aload_0
        //   1: invokestatic 97	com/olym/mjt/utils/FileIOUtils:isFileExists	(Ljava/io/File;)Z
        //   4: ifne +5 -> 9
        //   7: aconst_null
        //   8: areturn
        //   9: iload_1
        //   10: iload_2
        //   11: if_icmple +5 -> 16
        //   14: aconst_null
        //   15: areturn
        //   16: aconst_null
        //   17: astore 8
        //   19: aconst_null
        //   20: astore 7
        //   22: iconst_1
        //   23: istore 4
        //   25: aload 7
        //   27: astore 6
        //   29: aload 8
        //   31: astore 5
        //   33: new 195	java/util/ArrayList
        //   36: dup
        //   37: invokespecial 196	java/util/ArrayList:<init>	()V
        //   40: astore 9
        //   42: aload 7
        //   44: astore 6
        //   46: aload 8
        //   48: astore 5
        //   50: aload_3
        //   51: invokestatic 75	com/olym/mjt/utils/FileIOUtils:isSpace	(Ljava/lang/String;)Z
        //   54: ifeq +69 -> 123
        //   57: aload 7
        //   59: astore 6
        //   61: aload 8
        //   63: astore 5
        //   65: new 198	java/io/BufferedReader
        //   68: dup
        //   69: new 200	java/io/InputStreamReader
        //   72: dup
        //   73: new 165	java/io/FileInputStream
        //   76: dup
        //   77: aload_0
        //   78: invokespecial 168	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   81: invokespecial 203	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
        //   84: invokespecial 206	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
        //   87: astore_0
        //   88: aload_0
        //   89: astore 6
        //   91: aload_0
        //   92: astore 5
        //   94: aload_0
        //   95: invokevirtual 210	java/io/BufferedReader:readLine	()Ljava/lang/String;
        //   98: astore_3
        //   99: aload_3
        //   100: ifnull +9 -> 109
        //   103: iload 4
        //   105: iload_2
        //   106: if_icmple +52 -> 158
        //   109: iconst_1
        //   110: anewarray 130	java/io/Closeable
        //   113: dup
        //   114: iconst_0
        //   115: aload_0
        //   116: aastore
        //   117: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   120: aload 9
        //   122: areturn
        //   123: aload 7
        //   125: astore 6
        //   127: aload 8
        //   129: astore 5
        //   131: new 198	java/io/BufferedReader
        //   134: dup
        //   135: new 200	java/io/InputStreamReader
        //   138: dup
        //   139: new 165	java/io/FileInputStream
        //   142: dup
        //   143: aload_0
        //   144: invokespecial 168	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   147: aload_3
        //   148: invokespecial 213	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
        //   151: invokespecial 206	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
        //   154: astore_0
        //   155: goto -67 -> 88
        //   158: iload_1
        //   159: iload 4
        //   161: if_icmpgt +24 -> 185
        //   164: iload 4
        //   166: iload_2
        //   167: if_icmpgt +18 -> 185
        //   170: aload_0
        //   171: astore 6
        //   173: aload_0
        //   174: astore 5
        //   176: aload 9
        //   178: aload_3
        //   179: invokeinterface 219 2 0
        //   184: pop
        //   185: iload 4
        //   187: iconst_1
        //   188: iadd
        //   189: istore 4
        //   191: goto -103 -> 88
        //   194: astore_0
        //   195: aload 6
        //   197: astore 5
        //   199: aload_0
        //   200: invokevirtual 65	java/io/IOException:printStackTrace	()V
        //   203: iconst_1
        //   204: anewarray 130	java/io/Closeable
        //   207: dup
        //   208: iconst_0
        //   209: aload 6
        //   211: aastore
        //   212: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   215: aconst_null
        //   216: areturn
        //   217: astore_0
        //   218: iconst_1
        //   219: anewarray 130	java/io/Closeable
        //   222: dup
        //   223: iconst_0
        //   224: aload 5
        //   226: aastore
        //   227: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   230: aload_0
        //   231: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	232	0	paramFile	File
        //   0	232	1	paramInt1	int
        //   0	232	2	paramInt2	int
        //   0	232	3	paramString	String
        //   23	167	4	i	int
        //   31	194	5	localObject1	Object
        //   27	183	6	localObject2	Object
        //   20	104	7	localObject3	Object
        //   17	111	8	localObject4	Object
        //   40	137	9	localArrayList	java.util.ArrayList
        // Exception table:
        //   from	to	target	type
        //   33	42	194	java/io/IOException
        //   50	57	194	java/io/IOException
        //   65	88	194	java/io/IOException
        //   94	99	194	java/io/IOException
        //   131	155	194	java/io/IOException
        //   176	185	194	java/io/IOException
        //   33	42	217	finally
        //   50	57	217	finally
        //   65	88	217	finally
        //   94	99	217	finally
        //   131	155	217	finally
        //   176	185	217	finally
        //   199	203	217	finally
    }

    public static List<String> readFile2List(File paramFile, String paramString)
    {
        return readFile2List(paramFile, 0, Integer.MAX_VALUE, paramString);
    }

    public static List<String> readFile2List(String paramString)
    {
        return readFile2List(getFileByPath(paramString), null);
    }

    public static List<String> readFile2List(String paramString, int paramInt1, int paramInt2)
    {
        return readFile2List(getFileByPath(paramString), paramInt1, paramInt2, null);
    }

    public static List<String> readFile2List(String paramString1, int paramInt1, int paramInt2, String paramString2)
    {
        return readFile2List(getFileByPath(paramString1), paramInt1, paramInt2, paramString2);
    }

    public static List<String> readFile2List(String paramString1, String paramString2)
    {
        return readFile2List(getFileByPath(paramString1), paramString2);
    }

    public static String readFile2String(File paramFile)
    {
        return readFile2String(paramFile, null);
    }

    /* Error */
    public static String readFile2String(File paramFile, String paramString)
    {
        // Byte code:
        //   0: aload_0
        //   1: invokestatic 97	com/olym/mjt/utils/FileIOUtils:isFileExists	(Ljava/io/File;)Z
        //   4: ifne +5 -> 9
        //   7: aconst_null
        //   8: areturn
        //   9: aconst_null
        //   10: astore 5
        //   12: aconst_null
        //   13: astore 4
        //   15: aload 4
        //   17: astore_3
        //   18: aload 5
        //   20: astore_2
        //   21: new 239	java/lang/StringBuilder
        //   24: dup
        //   25: invokespecial 240	java/lang/StringBuilder:<init>	()V
        //   28: astore 6
        //   30: aload 4
        //   32: astore_3
        //   33: aload 5
        //   35: astore_2
        //   36: aload_1
        //   37: invokestatic 75	com/olym/mjt/utils/FileIOUtils:isSpace	(Ljava/lang/String;)Z
        //   40: ifeq +109 -> 149
        //   43: aload 4
        //   45: astore_3
        //   46: aload 5
        //   48: astore_2
        //   49: new 198	java/io/BufferedReader
        //   52: dup
        //   53: new 200	java/io/InputStreamReader
        //   56: dup
        //   57: new 165	java/io/FileInputStream
        //   60: dup
        //   61: aload_0
        //   62: invokespecial 168	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   65: invokespecial 203	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
        //   68: invokespecial 206	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
        //   71: astore_0
        //   72: aload_0
        //   73: astore_3
        //   74: aload_0
        //   75: astore_2
        //   76: aload_0
        //   77: invokevirtual 210	java/io/BufferedReader:readLine	()Ljava/lang/String;
        //   80: astore_1
        //   81: aload_1
        //   82: ifnull +100 -> 182
        //   85: aload_0
        //   86: astore_3
        //   87: aload_0
        //   88: astore_2
        //   89: aload 6
        //   91: aload_1
        //   92: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   95: pop
        //   96: aload_0
        //   97: astore_3
        //   98: aload_0
        //   99: astore_2
        //   100: aload_0
        //   101: invokevirtual 210	java/io/BufferedReader:readLine	()Ljava/lang/String;
        //   104: astore_1
        //   105: aload_1
        //   106: ifnull +76 -> 182
        //   109: aload_0
        //   110: astore_3
        //   111: aload_0
        //   112: astore_2
        //   113: aload 6
        //   115: getstatic 20	com/olym/mjt/utils/FileIOUtils:LINE_SEP	Ljava/lang/String;
        //   118: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   121: aload_1
        //   122: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   125: pop
        //   126: goto -30 -> 96
        //   129: astore_0
        //   130: aload_3
        //   131: astore_2
        //   132: aload_0
        //   133: invokevirtual 65	java/io/IOException:printStackTrace	()V
        //   136: iconst_1
        //   137: anewarray 130	java/io/Closeable
        //   140: dup
        //   141: iconst_0
        //   142: aload_3
        //   143: aastore
        //   144: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   147: aconst_null
        //   148: areturn
        //   149: aload 4
        //   151: astore_3
        //   152: aload 5
        //   154: astore_2
        //   155: new 198	java/io/BufferedReader
        //   158: dup
        //   159: new 200	java/io/InputStreamReader
        //   162: dup
        //   163: new 165	java/io/FileInputStream
        //   166: dup
        //   167: aload_0
        //   168: invokespecial 168	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   171: aload_1
        //   172: invokespecial 213	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
        //   175: invokespecial 206	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
        //   178: astore_0
        //   179: goto -107 -> 72
        //   182: aload_0
        //   183: astore_3
        //   184: aload_0
        //   185: astore_2
        //   186: aload 6
        //   188: invokevirtual 247	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   191: astore_1
        //   192: iconst_1
        //   193: anewarray 130	java/io/Closeable
        //   196: dup
        //   197: iconst_0
        //   198: aload_0
        //   199: aastore
        //   200: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   203: aload_1
        //   204: areturn
        //   205: astore_0
        //   206: iconst_1
        //   207: anewarray 130	java/io/Closeable
        //   210: dup
        //   211: iconst_0
        //   212: aload_2
        //   213: aastore
        //   214: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   217: aload_0
        //   218: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	219	0	paramFile	File
        //   0	219	1	paramString	String
        //   20	193	2	localObject1	Object
        //   17	167	3	localObject2	Object
        //   13	137	4	localObject3	Object
        //   10	143	5	localObject4	Object
        //   28	159	6	localStringBuilder	StringBuilder
        // Exception table:
        //   from	to	target	type
        //   21	30	129	java/io/IOException
        //   36	43	129	java/io/IOException
        //   49	72	129	java/io/IOException
        //   76	81	129	java/io/IOException
        //   89	96	129	java/io/IOException
        //   100	105	129	java/io/IOException
        //   113	126	129	java/io/IOException
        //   155	179	129	java/io/IOException
        //   186	192	129	java/io/IOException
        //   21	30	205	finally
        //   36	43	205	finally
        //   49	72	205	finally
        //   76	81	205	finally
        //   89	96	205	finally
        //   100	105	205	finally
        //   113	126	205	finally
        //   132	136	205	finally
        //   155	179	205	finally
        //   186	192	205	finally
    }

    public static String readFile2String(String paramString)
    {
        return readFile2String(getFileByPath(paramString), null);
    }

    public static String readFile2String(String paramString1, String paramString2)
    {
        return readFile2String(getFileByPath(paramString1), paramString2);
    }

    public static void setBufferSize(int paramInt)
    {
        sBufferSize = paramInt;
    }

    public static boolean writeFileFromBytesByChannel(File paramFile, byte[] paramArrayOfByte, boolean paramBoolean)
    {
        return writeFileFromBytesByChannel(paramFile, paramArrayOfByte, false, paramBoolean);
    }

    /* Error */
    public static boolean writeFileFromBytesByChannel(File paramFile, byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2)
    {
        // Byte code:
        //   0: aload_1
        //   1: ifnonnull +5 -> 6
        //   4: iconst_0
        //   5: ireturn
        //   6: aconst_null
        //   7: astore 5
        //   9: aconst_null
        //   10: astore 4
        //   12: new 257	java/io/FileOutputStream
        //   15: dup
        //   16: aload_0
        //   17: iload_2
        //   18: invokespecial 260	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
        //   21: invokevirtual 261	java/io/FileOutputStream:getChannel	()Ljava/nio/channels/FileChannel;
        //   24: astore_0
        //   25: aload_0
        //   26: astore 4
        //   28: aload_0
        //   29: astore 5
        //   31: aload_0
        //   32: aload_0
        //   33: invokevirtual 114	java/nio/channels/FileChannel:size	()J
        //   36: invokevirtual 265	java/nio/channels/FileChannel:position	(J)Ljava/nio/channels/FileChannel;
        //   39: pop
        //   40: aload_0
        //   41: astore 4
        //   43: aload_0
        //   44: astore 5
        //   46: aload_0
        //   47: aload_1
        //   48: invokestatic 269	java/nio/ByteBuffer:wrap	([B)Ljava/nio/ByteBuffer;
        //   51: invokevirtual 271	java/nio/channels/FileChannel:write	(Ljava/nio/ByteBuffer;)I
        //   54: pop
        //   55: iload_3
        //   56: ifeq +14 -> 70
        //   59: aload_0
        //   60: astore 4
        //   62: aload_0
        //   63: astore 5
        //   65: aload_0
        //   66: iconst_1
        //   67: invokevirtual 275	java/nio/channels/FileChannel:force	(Z)V
        //   70: iconst_1
        //   71: anewarray 130	java/io/Closeable
        //   74: dup
        //   75: iconst_0
        //   76: aload_0
        //   77: aastore
        //   78: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   81: iconst_1
        //   82: ireturn
        //   83: astore_0
        //   84: aload 4
        //   86: astore 5
        //   88: aload_0
        //   89: invokevirtual 65	java/io/IOException:printStackTrace	()V
        //   92: iconst_1
        //   93: anewarray 130	java/io/Closeable
        //   96: dup
        //   97: iconst_0
        //   98: aload 4
        //   100: aastore
        //   101: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   104: iconst_0
        //   105: ireturn
        //   106: astore_0
        //   107: iconst_1
        //   108: anewarray 130	java/io/Closeable
        //   111: dup
        //   112: iconst_0
        //   113: aload 5
        //   115: aastore
        //   116: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   119: aload_0
        //   120: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	121	0	paramFile	File
        //   0	121	1	paramArrayOfByte	byte[]
        //   0	121	2	paramBoolean1	boolean
        //   0	121	3	paramBoolean2	boolean
        //   10	89	4	localFile	File
        //   7	107	5	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   12	25	83	java/io/IOException
        //   31	40	83	java/io/IOException
        //   46	55	83	java/io/IOException
        //   65	70	83	java/io/IOException
        //   12	25	106	finally
        //   31	40	106	finally
        //   46	55	106	finally
        //   65	70	106	finally
        //   88	92	106	finally
    }

    public static boolean writeFileFromBytesByChannel(String paramString, byte[] paramArrayOfByte, boolean paramBoolean)
    {
        return writeFileFromBytesByChannel(getFileByPath(paramString), paramArrayOfByte, false, paramBoolean);
    }

    public static boolean writeFileFromBytesByChannel(String paramString, byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2)
    {
        return writeFileFromBytesByChannel(getFileByPath(paramString), paramArrayOfByte, paramBoolean1, paramBoolean2);
    }

    public static boolean writeFileFromBytesByMap(File paramFile, byte[] paramArrayOfByte, boolean paramBoolean)
    {
        return writeFileFromBytesByMap(paramFile, paramArrayOfByte, false, paramBoolean);
    }

    /* Error */
    public static boolean writeFileFromBytesByMap(File paramFile, byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2)
    {
        // Byte code:
        //   0: aload_1
        //   1: ifnull +10 -> 11
        //   4: aload_0
        //   5: invokestatic 72	com/olym/mjt/utils/FileIOUtils:createOrExistsFile	(Ljava/io/File;)Z
        //   8: ifne +5 -> 13
        //   11: iconst_0
        //   12: ireturn
        //   13: aconst_null
        //   14: astore 5
        //   16: aconst_null
        //   17: astore 4
        //   19: new 257	java/io/FileOutputStream
        //   22: dup
        //   23: aload_0
        //   24: iload_2
        //   25: invokespecial 260	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
        //   28: invokevirtual 261	java/io/FileOutputStream:getChannel	()Ljava/nio/channels/FileChannel;
        //   31: astore_0
        //   32: aload_0
        //   33: astore 4
        //   35: aload_0
        //   36: astore 5
        //   38: aload_0
        //   39: getstatic 283	java/nio/channels/FileChannel$MapMode:READ_WRITE	Ljava/nio/channels/FileChannel$MapMode;
        //   42: aload_0
        //   43: invokevirtual 114	java/nio/channels/FileChannel:size	()J
        //   46: aload_1
        //   47: arraylength
        //   48: i2l
        //   49: invokevirtual 150	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
        //   52: astore 6
        //   54: aload_0
        //   55: astore 4
        //   57: aload_0
        //   58: astore 5
        //   60: aload 6
        //   62: aload_1
        //   63: invokevirtual 286	java/nio/MappedByteBuffer:put	([B)Ljava/nio/ByteBuffer;
        //   66: pop
        //   67: iload_3
        //   68: ifeq +15 -> 83
        //   71: aload_0
        //   72: astore 4
        //   74: aload_0
        //   75: astore 5
        //   77: aload 6
        //   79: invokevirtual 288	java/nio/MappedByteBuffer:force	()Ljava/nio/MappedByteBuffer;
        //   82: pop
        //   83: iconst_1
        //   84: anewarray 130	java/io/Closeable
        //   87: dup
        //   88: iconst_0
        //   89: aload_0
        //   90: aastore
        //   91: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   94: iconst_1
        //   95: ireturn
        //   96: astore_0
        //   97: aload 4
        //   99: astore 5
        //   101: aload_0
        //   102: invokevirtual 65	java/io/IOException:printStackTrace	()V
        //   105: iconst_1
        //   106: anewarray 130	java/io/Closeable
        //   109: dup
        //   110: iconst_0
        //   111: aload 4
        //   113: aastore
        //   114: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   117: iconst_0
        //   118: ireturn
        //   119: astore_0
        //   120: iconst_1
        //   121: anewarray 130	java/io/Closeable
        //   124: dup
        //   125: iconst_0
        //   126: aload 5
        //   128: aastore
        //   129: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   132: aload_0
        //   133: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	134	0	paramFile	File
        //   0	134	1	paramArrayOfByte	byte[]
        //   0	134	2	paramBoolean1	boolean
        //   0	134	3	paramBoolean2	boolean
        //   17	95	4	localFile	File
        //   14	113	5	localObject	Object
        //   52	26	6	localMappedByteBuffer	java.nio.MappedByteBuffer
        // Exception table:
        //   from	to	target	type
        //   19	32	96	java/io/IOException
        //   38	54	96	java/io/IOException
        //   60	67	96	java/io/IOException
        //   77	83	96	java/io/IOException
        //   19	32	119	finally
        //   38	54	119	finally
        //   60	67	119	finally
        //   77	83	119	finally
        //   101	105	119	finally
    }

    public static boolean writeFileFromBytesByMap(String paramString, byte[] paramArrayOfByte, boolean paramBoolean)
    {
        return writeFileFromBytesByMap(paramString, paramArrayOfByte, false, paramBoolean);
    }

    public static boolean writeFileFromBytesByMap(String paramString, byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2)
    {
        return writeFileFromBytesByMap(getFileByPath(paramString), paramArrayOfByte, paramBoolean1, paramBoolean2);
    }

    public static boolean writeFileFromBytesByStream(File paramFile, byte[] paramArrayOfByte)
    {
        return writeFileFromBytesByStream(paramFile, paramArrayOfByte, false);
    }

    /* Error */
    public static boolean writeFileFromBytesByStream(File paramFile, byte[] paramArrayOfByte, boolean paramBoolean)
    {
        // Byte code:
        //   0: aload_1
        //   1: ifnull +10 -> 11
        //   4: aload_0
        //   5: invokestatic 72	com/olym/mjt/utils/FileIOUtils:createOrExistsFile	(Ljava/io/File;)Z
        //   8: ifne +5 -> 13
        //   11: iconst_0
        //   12: ireturn
        //   13: aconst_null
        //   14: astore_3
        //   15: aconst_null
        //   16: astore 4
        //   18: new 296	java/io/BufferedOutputStream
        //   21: dup
        //   22: new 257	java/io/FileOutputStream
        //   25: dup
        //   26: aload_0
        //   27: iload_2
        //   28: invokespecial 260	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
        //   31: invokespecial 299	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
        //   34: astore_0
        //   35: aload_0
        //   36: aload_1
        //   37: invokevirtual 302	java/io/BufferedOutputStream:write	([B)V
        //   40: iconst_1
        //   41: anewarray 130	java/io/Closeable
        //   44: dup
        //   45: iconst_0
        //   46: aload_0
        //   47: aastore
        //   48: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   51: iconst_1
        //   52: ireturn
        //   53: astore_1
        //   54: aload 4
        //   56: astore_0
        //   57: aload_0
        //   58: astore_3
        //   59: aload_1
        //   60: invokevirtual 65	java/io/IOException:printStackTrace	()V
        //   63: iconst_1
        //   64: anewarray 130	java/io/Closeable
        //   67: dup
        //   68: iconst_0
        //   69: aload_0
        //   70: aastore
        //   71: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   74: iconst_0
        //   75: ireturn
        //   76: astore_0
        //   77: iconst_1
        //   78: anewarray 130	java/io/Closeable
        //   81: dup
        //   82: iconst_0
        //   83: aload_3
        //   84: aastore
        //   85: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   88: aload_0
        //   89: athrow
        //   90: astore_1
        //   91: aload_0
        //   92: astore_3
        //   93: aload_1
        //   94: astore_0
        //   95: goto -18 -> 77
        //   98: astore_1
        //   99: goto -42 -> 57
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	102	0	paramFile	File
        //   0	102	1	paramArrayOfByte	byte[]
        //   0	102	2	paramBoolean	boolean
        //   14	79	3	localFile	File
        //   16	39	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   18	35	53	java/io/IOException
        //   18	35	76	finally
        //   59	63	76	finally
        //   35	40	90	finally
        //   35	40	98	java/io/IOException
    }

    public static boolean writeFileFromBytesByStream(String paramString, byte[] paramArrayOfByte)
    {
        return writeFileFromBytesByStream(getFileByPath(paramString), paramArrayOfByte, false);
    }

    public static boolean writeFileFromBytesByStream(String paramString, byte[] paramArrayOfByte, boolean paramBoolean)
    {
        return writeFileFromBytesByStream(getFileByPath(paramString), paramArrayOfByte, paramBoolean);
    }

    public static boolean writeFileFromIS(File paramFile, InputStream paramInputStream)
    {
        return writeFileFromIS(paramFile, paramInputStream, false);
    }

    /* Error */
    public static boolean writeFileFromIS(File paramFile, InputStream paramInputStream, boolean paramBoolean)
    {
        // Byte code:
        //   0: aload_0
        //   1: invokestatic 72	com/olym/mjt/utils/FileIOUtils:createOrExistsFile	(Ljava/io/File;)Z
        //   4: ifeq +7 -> 11
        //   7: aload_1
        //   8: ifnonnull +5 -> 13
        //   11: iconst_0
        //   12: ireturn
        //   13: aconst_null
        //   14: astore 4
        //   16: aconst_null
        //   17: astore 6
        //   19: new 296	java/io/BufferedOutputStream
        //   22: dup
        //   23: new 257	java/io/FileOutputStream
        //   26: dup
        //   27: aload_0
        //   28: iload_2
        //   29: invokespecial 260	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
        //   32: invokespecial 299	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
        //   35: astore_0
        //   36: getstatic 22	com/olym/mjt/utils/FileIOUtils:sBufferSize	I
        //   39: newarray <illegal type>
        //   41: astore 4
        //   43: aload_1
        //   44: aload 4
        //   46: iconst_0
        //   47: getstatic 22	com/olym/mjt/utils/FileIOUtils:sBufferSize	I
        //   50: invokevirtual 311	java/io/InputStream:read	([BII)I
        //   53: istore_3
        //   54: iload_3
        //   55: iconst_m1
        //   56: if_icmpeq +41 -> 97
        //   59: aload_0
        //   60: aload 4
        //   62: iconst_0
        //   63: iload_3
        //   64: invokevirtual 314	java/io/OutputStream:write	([BII)V
        //   67: goto -24 -> 43
        //   70: astore 5
        //   72: aload_0
        //   73: astore 4
        //   75: aload 5
        //   77: invokevirtual 65	java/io/IOException:printStackTrace	()V
        //   80: iconst_2
        //   81: anewarray 130	java/io/Closeable
        //   84: dup
        //   85: iconst_0
        //   86: aload_1
        //   87: aastore
        //   88: dup
        //   89: iconst_1
        //   90: aload_0
        //   91: aastore
        //   92: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   95: iconst_0
        //   96: ireturn
        //   97: iconst_2
        //   98: anewarray 130	java/io/Closeable
        //   101: dup
        //   102: iconst_0
        //   103: aload_1
        //   104: aastore
        //   105: dup
        //   106: iconst_1
        //   107: aload_0
        //   108: aastore
        //   109: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   112: iconst_1
        //   113: ireturn
        //   114: astore_0
        //   115: iconst_2
        //   116: anewarray 130	java/io/Closeable
        //   119: dup
        //   120: iconst_0
        //   121: aload_1
        //   122: aastore
        //   123: dup
        //   124: iconst_1
        //   125: aload 4
        //   127: aastore
        //   128: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   131: aload_0
        //   132: athrow
        //   133: astore 5
        //   135: aload_0
        //   136: astore 4
        //   138: aload 5
        //   140: astore_0
        //   141: goto -26 -> 115
        //   144: astore 5
        //   146: aload 6
        //   148: astore_0
        //   149: goto -77 -> 72
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	152	0	paramFile	File
        //   0	152	1	paramInputStream	InputStream
        //   0	152	2	paramBoolean	boolean
        //   53	11	3	i	int
        //   14	123	4	localObject1	Object
        //   70	6	5	localIOException1	IOException
        //   133	6	5	localObject2	Object
        //   144	1	5	localIOException2	IOException
        //   17	130	6	localObject3	Object
        // Exception table:
        //   from	to	target	type
        //   36	43	70	java/io/IOException
        //   43	54	70	java/io/IOException
        //   59	67	70	java/io/IOException
        //   19	36	114	finally
        //   75	80	114	finally
        //   36	43	133	finally
        //   43	54	133	finally
        //   59	67	133	finally
        //   19	36	144	java/io/IOException
    }

    public static boolean writeFileFromIS(String paramString, InputStream paramInputStream)
    {
        return writeFileFromIS(getFileByPath(paramString), paramInputStream, false);
    }

    public static boolean writeFileFromIS(String paramString, InputStream paramInputStream, boolean paramBoolean)
    {
        return writeFileFromIS(getFileByPath(paramString), paramInputStream, paramBoolean);
    }

    public static boolean writeFileFromString(File paramFile, String paramString)
    {
        return writeFileFromString(paramFile, paramString, false);
    }

    /* Error */
    public static boolean writeFileFromString(File paramFile, String paramString, boolean paramBoolean)
    {
        // Byte code:
        //   0: aload_0
        //   1: ifnull +7 -> 8
        //   4: aload_1
        //   5: ifnonnull +5 -> 10
        //   8: iconst_0
        //   9: ireturn
        //   10: aload_0
        //   11: invokestatic 72	com/olym/mjt/utils/FileIOUtils:createOrExistsFile	(Ljava/io/File;)Z
        //   14: ifeq -6 -> 8
        //   17: aconst_null
        //   18: astore_3
        //   19: aconst_null
        //   20: astore 4
        //   22: new 323	java/io/BufferedWriter
        //   25: dup
        //   26: new 325	java/io/FileWriter
        //   29: dup
        //   30: aload_0
        //   31: iload_2
        //   32: invokespecial 326	java/io/FileWriter:<init>	(Ljava/io/File;Z)V
        //   35: invokespecial 329	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
        //   38: astore_0
        //   39: aload_0
        //   40: aload_1
        //   41: invokevirtual 331	java/io/BufferedWriter:write	(Ljava/lang/String;)V
        //   44: iconst_1
        //   45: anewarray 130	java/io/Closeable
        //   48: dup
        //   49: iconst_0
        //   50: aload_0
        //   51: aastore
        //   52: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   55: iconst_1
        //   56: ireturn
        //   57: astore_1
        //   58: aload 4
        //   60: astore_0
        //   61: aload_0
        //   62: astore_3
        //   63: aload_1
        //   64: invokevirtual 65	java/io/IOException:printStackTrace	()V
        //   67: iconst_1
        //   68: anewarray 130	java/io/Closeable
        //   71: dup
        //   72: iconst_0
        //   73: aload_0
        //   74: aastore
        //   75: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   78: iconst_0
        //   79: ireturn
        //   80: astore_0
        //   81: iconst_1
        //   82: anewarray 130	java/io/Closeable
        //   85: dup
        //   86: iconst_0
        //   87: aload_3
        //   88: aastore
        //   89: invokestatic 136	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   92: aload_0
        //   93: athrow
        //   94: astore_1
        //   95: aload_0
        //   96: astore_3
        //   97: aload_1
        //   98: astore_0
        //   99: goto -18 -> 81
        //   102: astore_1
        //   103: goto -42 -> 61
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	106	0	paramFile	File
        //   0	106	1	paramString	String
        //   0	106	2	paramBoolean	boolean
        //   18	79	3	localFile	File
        //   20	39	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   22	39	57	java/io/IOException
        //   22	39	80	finally
        //   63	67	80	finally
        //   39	44	94	finally
        //   39	44	102	java/io/IOException
    }

    public static boolean writeFileFromString(String paramString1, String paramString2)
    {
        return writeFileFromString(getFileByPath(paramString1), paramString2, false);
    }

    public static boolean writeFileFromString(String paramString1, String paramString2, boolean paramBoolean)
    {
        return writeFileFromString(getFileByPath(paramString1), paramString2, paramBoolean);
    }
}
