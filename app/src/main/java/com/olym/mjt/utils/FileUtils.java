package com.olym.mjt.utils;

import android.annotation.SuppressLint;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final char[] hexDigits = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };

    private FileUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @SuppressLint({"DefaultLocale"})
    private static String byte2FitMemorySize(long paramLong)
    {
        if (paramLong < 0L) {
            return "shouldn't be less than zero!";
        }
        if (paramLong < 1024L) {
            return String.format("%.2fB", new Object[] { Double.valueOf(paramLong + 5.0E-4D) });
        }
        if (paramLong < 1048576L) {
            return String.format("%.2fKB", new Object[] { Double.valueOf(paramLong / 1024.0D + 5.0E-4D) });
        }
        if (paramLong < 1073741824L) {
            return String.format("%.2fMB", new Object[] { Double.valueOf(paramLong / 1048576.0D + 5.0E-4D) });
        }
        return String.format("%.2fGB", new Object[] { Double.valueOf(paramLong / 1.073741824E9D + 5.0E-4D) });
    }

    private static String bytes2HexString(byte[] paramArrayOfByte)
    {
        if (paramArrayOfByte == null) {}
        int k;
        do
        {
            return null;
            k = paramArrayOfByte.length;
        } while (k <= 0);
        char[] arrayOfChar = new char[k << 1];
        int i = 0;
        int j = 0;
        while (i < k)
        {
            int m = j + 1;
            arrayOfChar[j] = hexDigits[(paramArrayOfByte[i] >>> 4 & 0xF)];
            j = m + 1;
            arrayOfChar[m] = hexDigits[(paramArrayOfByte[i] & 0xF)];
            i += 1;
        }
        return new String(arrayOfChar);
    }

    public static boolean copyDir(File paramFile1, File paramFile2)
    {
        return copyOrMoveDir(paramFile1, paramFile2, false);
    }

    public static boolean copyDir(String paramString1, String paramString2)
    {
        return copyDir(getFileByPath(paramString1), getFileByPath(paramString2));
    }

    public static boolean copyFile(File paramFile1, File paramFile2)
    {
        return copyOrMoveFile(paramFile1, paramFile2, false);
    }

    public static boolean copyFile(String paramString1, String paramString2)
    {
        return copyFile(getFileByPath(paramString1), getFileByPath(paramString2));
    }

    private static boolean copyOrMoveDir(File paramFile1, File paramFile2, boolean paramBoolean)
    {
        if ((paramFile1 == null) || (paramFile2 == null)) {}
        label192:
        do
        {
            String str2;
            int i;
            File localFile;
            do
            {
                String str1;
                do
                {
                    return false;
                    str2 = paramFile1.getPath() + File.separator;
                    str1 = paramFile2.getPath() + File.separator;
                } while ((str1.contains(str2)) || (!paramFile1.exists()) || (!paramFile1.isDirectory()) || (!createOrExistsDir(paramFile2)));
                paramFile2 = paramFile1.listFiles();
                int j = paramFile2.length;
                i = 0;
                if (i >= j) {
                    break label192;
                }
                str2 = paramFile2[i];
                localFile = new File(str1 + str2.getName());
                if (!str2.isFile()) {
                    break;
                }
            } while (!copyOrMoveFile(str2, localFile, paramBoolean));
            while ((!str2.isDirectory()) || (copyOrMoveDir(str2, localFile, paramBoolean)))
            {
                i += 1;
                break;
            }
            return false;
        } while ((paramBoolean) && (!deleteDir(paramFile1)));
        return true;
    }

    private static boolean copyOrMoveDir(String paramString1, String paramString2, boolean paramBoolean)
    {
        return copyOrMoveDir(getFileByPath(paramString1), getFileByPath(paramString2), paramBoolean);
    }

    private static boolean copyOrMoveFile(File paramFile1, File paramFile2, boolean paramBoolean)
    {
        if ((paramFile1 == null) || (paramFile2 == null)) {}
        for (;;)
        {
            return false;
            if ((paramFile1.exists()) && (paramFile1.isFile()) && ((!paramFile2.exists()) || (!paramFile2.isFile())) && (createOrExistsDir(paramFile2.getParentFile()))) {
                try
                {
                    if (FileIOUtils.writeFileFromIS(paramFile2, new FileInputStream(paramFile1), false)) {
                        if (paramBoolean)
                        {
                            paramBoolean = deleteFile(paramFile1);
                            if (!paramBoolean) {}
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
                catch (FileNotFoundException paramFile1)
                {
                    paramFile1.printStackTrace();
                }
            }
        }
        return false;
    }

    private static boolean copyOrMoveFile(String paramString1, String paramString2, boolean paramBoolean)
    {
        return copyOrMoveFile(getFileByPath(paramString1), getFileByPath(paramString2), paramBoolean);
    }

    public static boolean createFileByDeleteOldFile(File paramFile)
    {
        if (paramFile == null) {}
        while (((paramFile.exists()) && (!paramFile.delete())) || (!createOrExistsDir(paramFile.getParentFile()))) {
            return false;
        }
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

    public static boolean createOrExistsDir(File paramFile)
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

    public static boolean createOrExistsDir(String paramString)
    {
        return createOrExistsDir(getFileByPath(paramString));
    }

    public static boolean createOrExistsFile(File paramFile)
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

    public static boolean createOrExistsFile(String paramString)
    {
        return createOrExistsFile(getFileByPath(paramString));
    }

    public static boolean deleteAllInDir(File paramFile)
    {
        deleteFilesInDirWithFilter(paramFile, new FileFilter()
        {
            public boolean accept(File paramAnonymousFile)
            {
                return true;
            }
        });
    }

    public static boolean deleteAllInDir(String paramString)
    {
        return deleteAllInDir(getFileByPath(paramString));
    }

    public static boolean deleteDir(File paramFile)
    {
        if (paramFile == null) {}
        int i;
        File localFile;
        do
        {
            do
            {
                return false;
                if (!paramFile.exists()) {
                    return true;
                }
            } while (!paramFile.isDirectory());
            File[] arrayOfFile = paramFile.listFiles();
            if ((arrayOfFile == null) || (arrayOfFile.length == 0)) {
                break label92;
            }
            int j = arrayOfFile.length;
            i = 0;
            if (i >= j) {
                break label92;
            }
            localFile = arrayOfFile[i];
            if (!localFile.isFile()) {
                break;
            }
        } while (!localFile.delete());
        while ((!localFile.isDirectory()) || (deleteDir(localFile)))
        {
            i += 1;
            break;
        }
        return false;
        label92:
        return paramFile.delete();
    }

    public static boolean deleteDir(String paramString)
    {
        return deleteDir(getFileByPath(paramString));
    }

    public static boolean deleteFile(File paramFile)
    {
        return (paramFile != null) && ((!paramFile.exists()) || ((paramFile.isFile()) && (paramFile.delete())));
    }

    public static boolean deleteFile(String paramString)
    {
        return deleteFile(getFileByPath(paramString));
    }

    public static boolean deleteFilesInDir(File paramFile)
    {
        deleteFilesInDirWithFilter(paramFile, new FileFilter()
        {
            public boolean accept(File paramAnonymousFile)
            {
                return paramAnonymousFile.isFile();
            }
        });
    }

    public static boolean deleteFilesInDir(String paramString)
    {
        return deleteFilesInDir(getFileByPath(paramString));
    }

    public static boolean deleteFilesInDirWithFilter(File paramFile, FileFilter paramFileFilter)
    {
        if (paramFile == null) {}
        int i;
        File localFile;
        do
        {
            do
            {
                return false;
                if (!paramFile.exists()) {
                    return true;
                }
            } while (!paramFile.isDirectory());
            paramFile = paramFile.listFiles();
            if ((paramFile == null) || (paramFile.length == 0)) {
                break label103;
            }
            int j = paramFile.length;
            i = 0;
            if (i >= j) {
                break label103;
            }
            localFile = paramFile[i];
            if (!paramFileFilter.accept(localFile)) {
                break;
            }
            if (!localFile.isFile()) {
                break label85;
            }
        } while (!localFile.delete());
        label85:
        while ((!localFile.isDirectory()) || (deleteDir(localFile)))
        {
            i += 1;
            break;
        }
        return false;
        label103:
        return true;
    }

    public static boolean deleteFilesInDirWithFilter(String paramString, FileFilter paramFileFilter)
    {
        return deleteFilesInDirWithFilter(getFileByPath(paramString), paramFileFilter);
    }

    public static long getDirLength(File paramFile)
    {
        long l1;
        if (!isDir(paramFile)) {
            l1 = -1L;
        }
        long l2;
        int j;
        int i;
        do
        {
            do
            {
                do
                {
                    return l1;
                    l2 = 0L;
                    paramFile = paramFile.listFiles();
                    l1 = l2;
                } while (paramFile == null);
                l1 = l2;
            } while (paramFile.length == 0);
            j = paramFile.length;
            i = 0;
            l1 = l2;
        } while (i >= j);
        File localFile = paramFile[i];
        if (localFile.isDirectory()) {}
        for (l2 += getDirLength(localFile);; l2 += localFile.length())
        {
            i += 1;
            break;
        }
    }

    public static long getDirLength(String paramString)
    {
        return getDirLength(getFileByPath(paramString));
    }

    public static String getDirName(File paramFile)
    {
        if (paramFile == null) {
            return null;
        }
        return getDirName(paramFile.getPath());
    }

    public static String getDirName(String paramString)
    {
        if (isSpace(paramString)) {
            return paramString;
        }
        int i = paramString.lastIndexOf(File.separator);
        if (i == -1) {}
        for (paramString = "";; paramString = paramString.substring(0, i + 1)) {
            return paramString;
        }
    }

    public static String getDirSize(File paramFile)
    {
        long l = getDirLength(paramFile);
        if (l == -1L) {
            return "0.00B";
        }
        return byte2FitMemorySize(l);
    }

    public static String getDirSize(String paramString)
    {
        return getDirSize(getFileByPath(paramString));
    }

    public static File getFileByPath(String paramString)
    {
        if (isSpace(paramString)) {
            return null;
        }
        return new File(paramString);
    }

    /* Error */
    public static String getFileCharsetSimple(File paramFile)
    {
        // Byte code:
        //   0: iconst_0
        //   1: istore_1
        //   2: aconst_null
        //   3: astore 4
        //   5: aconst_null
        //   6: astore 5
        //   8: new 269	java/io/BufferedInputStream
        //   11: dup
        //   12: new 178	java/io/FileInputStream
        //   15: dup
        //   16: aload_0
        //   17: invokespecial 181	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   20: invokespecial 272	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
        //   23: astore_0
        //   24: aload_0
        //   25: invokevirtual 278	java/io/InputStream:read	()I
        //   28: istore_2
        //   29: aload_0
        //   30: invokevirtual 278	java/io/InputStream:read	()I
        //   33: istore_3
        //   34: iload_2
        //   35: bipush 8
        //   37: ishl
        //   38: iload_3
        //   39: iadd
        //   40: istore_1
        //   41: iconst_1
        //   42: anewarray 280	java/io/Closeable
        //   45: dup
        //   46: iconst_0
        //   47: aload_0
        //   48: aastore
        //   49: invokestatic 286	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   52: iload_1
        //   53: lookupswitch	default:+35->88, 61371:+85->138, 65279:+93->146, 65534:+89->142
        //   88: ldc_w 288
        //   91: areturn
        //   92: astore 4
        //   94: aload 5
        //   96: astore_0
        //   97: aload 4
        //   99: astore 5
        //   101: aload_0
        //   102: astore 4
        //   104: aload 5
        //   106: invokevirtual 203	java/io/IOException:printStackTrace	()V
        //   109: iconst_1
        //   110: anewarray 280	java/io/Closeable
        //   113: dup
        //   114: iconst_0
        //   115: aload_0
        //   116: aastore
        //   117: invokestatic 286	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   120: goto -68 -> 52
        //   123: astore_0
        //   124: iconst_1
        //   125: anewarray 280	java/io/Closeable
        //   128: dup
        //   129: iconst_0
        //   130: aload 4
        //   132: aastore
        //   133: invokestatic 286	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   136: aload_0
        //   137: athrow
        //   138: ldc_w 290
        //   141: areturn
        //   142: ldc_w 292
        //   145: areturn
        //   146: ldc_w 294
        //   149: areturn
        //   150: astore 5
        //   152: aload_0
        //   153: astore 4
        //   155: aload 5
        //   157: astore_0
        //   158: goto -34 -> 124
        //   161: astore 5
        //   163: goto -62 -> 101
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	166	0	paramFile	File
        //   1	52	1	i	int
        //   28	10	2	j	int
        //   33	7	3	k	int
        //   3	1	4	localObject1	Object
        //   92	6	4	localIOException1	IOException
        //   102	52	4	localFile	File
        //   6	99	5	localIOException2	IOException
        //   150	6	5	localObject2	Object
        //   161	1	5	localIOException3	IOException
        // Exception table:
        //   from	to	target	type
        //   8	24	92	java/io/IOException
        //   8	24	123	finally
        //   104	109	123	finally
        //   24	34	150	finally
        //   24	34	161	java/io/IOException
    }

    public static String getFileCharsetSimple(String paramString)
    {
        return getFileCharsetSimple(getFileByPath(paramString));
    }

    public static String getFileExtension(File paramFile)
    {
        if (paramFile == null) {
            return null;
        }
        return getFileExtension(paramFile.getPath());
    }

    public static String getFileExtension(String paramString)
    {
        if (isSpace(paramString)) {
            return paramString;
        }
        int i = paramString.lastIndexOf('.');
        int j = paramString.lastIndexOf(File.separator);
        if ((i == -1) || (j >= i)) {
            return "";
        }
        return paramString.substring(i + 1);
    }

    public static long getFileLastModified(File paramFile)
    {
        if (paramFile == null) {
            return -1L;
        }
        return paramFile.lastModified();
    }

    public static long getFileLastModified(String paramString)
    {
        return getFileLastModified(getFileByPath(paramString));
    }

    public static long getFileLength(File paramFile)
    {
        if (!isFile(paramFile)) {
            return -1L;
        }
        return paramFile.length();
    }

    public static long getFileLength(String paramString)
    {
        return getFileLength(getFileByPath(paramString));
    }

    /* Error */
    public static int getFileLines(File paramFile)
    {
        // Byte code:
        //   0: iconst_1
        //   1: istore_1
        //   2: iconst_1
        //   3: istore_2
        //   4: iconst_1
        //   5: istore 4
        //   7: iconst_1
        //   8: istore_3
        //   9: aconst_null
        //   10: astore 6
        //   12: aconst_null
        //   13: astore 7
        //   15: new 269	java/io/BufferedInputStream
        //   18: dup
        //   19: new 178	java/io/FileInputStream
        //   22: dup
        //   23: aload_0
        //   24: invokespecial 181	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   27: invokespecial 272	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
        //   30: astore_0
        //   31: iload 4
        //   33: istore_2
        //   34: sipush 1024
        //   37: newarray <illegal type>
        //   39: astore 6
        //   41: iload 4
        //   43: istore_2
        //   44: getstatic 26	com/olym/mjt/utils/FileUtils:LINE_SEP	Ljava/lang/String;
        //   47: ldc_w 320
        //   50: invokevirtual 323	java/lang/String:endsWith	(Ljava/lang/String;)Z
        //   53: ifeq +34 -> 87
        //   56: iload_3
        //   57: istore_1
        //   58: iload_1
        //   59: istore_2
        //   60: aload_0
        //   61: aload 6
        //   63: iconst_0
        //   64: sipush 1024
        //   67: invokevirtual 326	java/io/InputStream:read	([BII)I
        //   70: istore 4
        //   72: iload_1
        //   73: istore_2
        //   74: iload 4
        //   76: iconst_m1
        //   77: if_icmpeq +72 -> 149
        //   80: iconst_0
        //   81: istore_3
        //   82: iload_1
        //   83: istore_2
        //   84: goto +139 -> 223
        //   87: iload_1
        //   88: istore_2
        //   89: aload_0
        //   90: aload 6
        //   92: iconst_0
        //   93: sipush 1024
        //   96: invokevirtual 326	java/io/InputStream:read	([BII)I
        //   99: istore 4
        //   101: iload_1
        //   102: istore_2
        //   103: iload 4
        //   105: iconst_m1
        //   106: if_icmpeq +43 -> 149
        //   109: iconst_0
        //   110: istore_3
        //   111: iload_1
        //   112: istore_2
        //   113: iload_2
        //   114: istore_1
        //   115: iload_3
        //   116: iload 4
        //   118: if_icmpge -31 -> 87
        //   121: aload 6
        //   123: iload_3
        //   124: baload
        //   125: istore 5
        //   127: iload_2
        //   128: istore_1
        //   129: iload 5
        //   131: bipush 13
        //   133: if_icmpne +7 -> 140
        //   136: iload_2
        //   137: iconst_1
        //   138: iadd
        //   139: istore_1
        //   140: iload_3
        //   141: iconst_1
        //   142: iadd
        //   143: istore_3
        //   144: iload_1
        //   145: istore_2
        //   146: goto -33 -> 113
        //   149: iconst_1
        //   150: anewarray 280	java/io/Closeable
        //   153: dup
        //   154: iconst_0
        //   155: aload_0
        //   156: aastore
        //   157: invokestatic 286	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   160: iload_2
        //   161: ireturn
        //   162: astore 6
        //   164: aload 7
        //   166: astore_0
        //   167: aload 6
        //   169: astore 7
        //   171: aload_0
        //   172: astore 6
        //   174: aload 7
        //   176: invokevirtual 203	java/io/IOException:printStackTrace	()V
        //   179: iconst_1
        //   180: anewarray 280	java/io/Closeable
        //   183: dup
        //   184: iconst_0
        //   185: aload_0
        //   186: aastore
        //   187: invokestatic 286	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   190: iload_2
        //   191: ireturn
        //   192: astore_0
        //   193: iconst_1
        //   194: anewarray 280	java/io/Closeable
        //   197: dup
        //   198: iconst_0
        //   199: aload 6
        //   201: aastore
        //   202: invokestatic 286	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   205: aload_0
        //   206: athrow
        //   207: astore 7
        //   209: aload_0
        //   210: astore 6
        //   212: aload 7
        //   214: astore_0
        //   215: goto -22 -> 193
        //   218: astore 7
        //   220: goto -49 -> 171
        //   223: iload_2
        //   224: istore_1
        //   225: iload_3
        //   226: iload 4
        //   228: if_icmpge -170 -> 58
        //   231: iload_2
        //   232: istore_1
        //   233: aload 6
        //   235: iload_3
        //   236: baload
        //   237: bipush 10
        //   239: if_icmpne +7 -> 246
        //   242: iload_2
        //   243: iconst_1
        //   244: iadd
        //   245: istore_1
        //   246: iload_3
        //   247: iconst_1
        //   248: iadd
        //   249: istore_3
        //   250: iload_1
        //   251: istore_2
        //   252: goto -29 -> 223
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	255	0	paramFile	File
        //   1	250	1	i	int
        //   3	249	2	j	int
        //   8	242	3	k	int
        //   5	224	4	m	int
        //   125	9	5	n	int
        //   10	112	6	arrayOfByte	byte[]
        //   162	6	6	localIOException1	IOException
        //   172	62	6	localFile	File
        //   13	162	7	localIOException2	IOException
        //   207	6	7	localObject	Object
        //   218	1	7	localIOException3	IOException
        // Exception table:
        //   from	to	target	type
        //   15	31	162	java/io/IOException
        //   15	31	192	finally
        //   174	179	192	finally
        //   34	41	207	finally
        //   44	56	207	finally
        //   60	72	207	finally
        //   89	101	207	finally
        //   34	41	218	java/io/IOException
        //   44	56	218	java/io/IOException
        //   60	72	218	java/io/IOException
        //   89	101	218	java/io/IOException
    }

    public static int getFileLines(String paramString)
    {
        return getFileLines(getFileByPath(paramString));
    }

    /* Error */
    public static byte[] getFileMD5(File paramFile)
    {
        // Byte code:
        //   0: aload_0
        //   1: ifnonnull +5 -> 6
        //   4: aconst_null
        //   5: areturn
        //   6: aconst_null
        //   7: astore_1
        //   8: aconst_null
        //   9: astore_3
        //   10: aconst_null
        //   11: astore_2
        //   12: new 334	java/security/DigestInputStream
        //   15: dup
        //   16: new 178	java/io/FileInputStream
        //   19: dup
        //   20: aload_0
        //   21: invokespecial 181	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   24: ldc_w 336
        //   27: invokestatic 342	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
        //   30: invokespecial 345	java/security/DigestInputStream:<init>	(Ljava/io/InputStream;Ljava/security/MessageDigest;)V
        //   33: astore_0
        //   34: ldc_w 346
        //   37: newarray <illegal type>
        //   39: astore_1
        //   40: aload_0
        //   41: aload_1
        //   42: invokevirtual 349	java/security/DigestInputStream:read	([B)I
        //   45: ifgt -5 -> 40
        //   48: aload_0
        //   49: invokevirtual 353	java/security/DigestInputStream:getMessageDigest	()Ljava/security/MessageDigest;
        //   52: invokevirtual 357	java/security/MessageDigest:digest	()[B
        //   55: astore_1
        //   56: iconst_1
        //   57: anewarray 280	java/io/Closeable
        //   60: dup
        //   61: iconst_0
        //   62: aload_0
        //   63: aastore
        //   64: invokestatic 286	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   67: aload_1
        //   68: areturn
        //   69: astore_1
        //   70: aload_2
        //   71: astore_0
        //   72: aload_1
        //   73: astore_2
        //   74: aload_0
        //   75: astore_1
        //   76: aload_2
        //   77: invokevirtual 360	java/lang/Exception:printStackTrace	()V
        //   80: iconst_1
        //   81: anewarray 280	java/io/Closeable
        //   84: dup
        //   85: iconst_0
        //   86: aload_0
        //   87: aastore
        //   88: invokestatic 286	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   91: aconst_null
        //   92: areturn
        //   93: astore_0
        //   94: iconst_1
        //   95: anewarray 280	java/io/Closeable
        //   98: dup
        //   99: iconst_0
        //   100: aload_1
        //   101: aastore
        //   102: invokestatic 286	com/olym/mjt/utils/CloseUtils:closeIO	([Ljava/io/Closeable;)V
        //   105: aload_0
        //   106: athrow
        //   107: astore_1
        //   108: aload_3
        //   109: astore_0
        //   110: aload_1
        //   111: astore_2
        //   112: goto -38 -> 74
        //   115: astore_2
        //   116: aload_0
        //   117: astore_1
        //   118: aload_2
        //   119: astore_0
        //   120: goto -26 -> 94
        //   123: astore_1
        //   124: goto -52 -> 72
        //   127: astore_1
        //   128: goto -18 -> 110
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	131	0	paramFile	File
        //   7	61	1	arrayOfByte	byte[]
        //   69	4	1	localNoSuchAlgorithmException1	java.security.NoSuchAlgorithmException
        //   75	26	1	localFile1	File
        //   107	4	1	localIOException1	IOException
        //   117	1	1	localFile2	File
        //   123	1	1	localNoSuchAlgorithmException2	java.security.NoSuchAlgorithmException
        //   127	1	1	localIOException2	IOException
        //   11	101	2	localObject1	Object
        //   115	4	2	localObject2	Object
        //   9	100	3	localObject3	Object
        // Exception table:
        //   from	to	target	type
        //   12	34	69	java/security/NoSuchAlgorithmException
        //   12	34	93	finally
        //   76	80	93	finally
        //   12	34	107	java/io/IOException
        //   34	40	115	finally
        //   40	56	115	finally
        //   34	40	123	java/security/NoSuchAlgorithmException
        //   40	56	123	java/security/NoSuchAlgorithmException
        //   34	40	127	java/io/IOException
        //   40	56	127	java/io/IOException
    }

    public static byte[] getFileMD5(String paramString)
    {
        if (isSpace(paramString)) {}
        for (paramString = null;; paramString = new File(paramString)) {
            return getFileMD5(paramString);
        }
    }

    public static String getFileMD5ToString(File paramFile)
    {
        return bytes2HexString(getFileMD5(paramFile));
    }

    public static String getFileMD5ToString(String paramString)
    {
        if (isSpace(paramString)) {}
        for (paramString = null;; paramString = new File(paramString)) {
            return getFileMD5ToString(paramString);
        }
    }

    public static String getFileName(File paramFile)
    {
        if (paramFile == null) {
            return null;
        }
        return getFileName(paramFile.getPath());
    }

    public static String getFileName(String paramString)
    {
        if (isSpace(paramString)) {}
        int i;
        do
        {
            return paramString;
            i = paramString.lastIndexOf(File.separator);
        } while (i == -1);
        return paramString.substring(i + 1);
    }

    public static String getFileNameNoExtension(File paramFile)
    {
        if (paramFile == null) {
            return null;
        }
        return getFileNameNoExtension(paramFile.getPath());
    }

    public static String getFileNameNoExtension(String paramString)
    {
        if (isSpace(paramString)) {}
        int i;
        int j;
        do
        {
            return paramString;
            i = paramString.lastIndexOf('.');
            j = paramString.lastIndexOf(File.separator);
            if (j != -1) {
                break;
            }
        } while (i == -1);
        return paramString.substring(0, i);
        if ((i == -1) || (j > i)) {
            return paramString.substring(j + 1);
        }
        return paramString.substring(j + 1, i);
    }

    public static String getFileSize(File paramFile)
    {
        long l = getFileLength(paramFile);
        if (l == -1L) {
            return "";
        }
        return byte2FitMemorySize(l);
    }

    public static String getFileSize(String paramString)
    {
        return getFileSize(getFileByPath(paramString));
    }

    public static boolean isDir(File paramFile)
    {
        return (isFileExists(paramFile)) && (paramFile.isDirectory());
    }

    public static boolean isDir(String paramString)
    {
        return isDir(getFileByPath(paramString));
    }

    public static boolean isFile(File paramFile)
    {
        return (isFileExists(paramFile)) && (paramFile.isFile());
    }

    public static boolean isFile(String paramString)
    {
        return isFile(getFileByPath(paramString));
    }

    public static boolean isFileExists(File paramFile)
    {
        return (paramFile != null) && (paramFile.exists());
    }

    public static boolean isFileExists(String paramString)
    {
        return isFileExists(getFileByPath(paramString));
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

    public static List<File> listFilesInDir(File paramFile)
    {
        return listFilesInDir(paramFile, false);
    }

    public static List<File> listFilesInDir(File paramFile, boolean paramBoolean)
    {
        listFilesInDirWithFilter(paramFile, new FileFilter()
        {
            public boolean accept(File paramAnonymousFile)
            {
                return true;
            }
        }, paramBoolean);
    }

    public static List<File> listFilesInDir(String paramString)
    {
        return listFilesInDir(paramString, false);
    }

    public static List<File> listFilesInDir(String paramString, boolean paramBoolean)
    {
        return listFilesInDir(getFileByPath(paramString), paramBoolean);
    }

    public static List<File> listFilesInDirWithFilter(File paramFile, FileFilter paramFileFilter)
    {
        return listFilesInDirWithFilter(paramFile, paramFileFilter, false);
    }

    public static List<File> listFilesInDirWithFilter(File paramFile, FileFilter paramFileFilter, boolean paramBoolean)
    {
        if (!isDir(paramFile)) {
            paramFile = null;
        }
        ArrayList localArrayList;
        File[] arrayOfFile;
        do
        {
            do
            {
                return paramFile;
                localArrayList = new ArrayList();
                arrayOfFile = paramFile.listFiles();
                paramFile = localArrayList;
            } while (arrayOfFile == null);
            paramFile = localArrayList;
        } while (arrayOfFile.length == 0);
        int j = arrayOfFile.length;
        int i = 0;
        for (;;)
        {
            paramFile = localArrayList;
            if (i >= j) {
                break;
            }
            paramFile = arrayOfFile[i];
            if (paramFileFilter.accept(paramFile)) {
                localArrayList.add(paramFile);
            }
            if ((paramBoolean) && (paramFile.isDirectory())) {
                localArrayList.addAll(listFilesInDirWithFilter(paramFile, paramFileFilter, true));
            }
            i += 1;
        }
    }

    public static List<File> listFilesInDirWithFilter(String paramString, FileFilter paramFileFilter)
    {
        return listFilesInDirWithFilter(getFileByPath(paramString), paramFileFilter, false);
    }

    public static List<File> listFilesInDirWithFilter(String paramString, FileFilter paramFileFilter, boolean paramBoolean)
    {
        return listFilesInDirWithFilter(getFileByPath(paramString), paramFileFilter, paramBoolean);
    }

    public static boolean moveDir(File paramFile1, File paramFile2)
    {
        return copyOrMoveDir(paramFile1, paramFile2, true);
    }

    public static boolean moveDir(String paramString1, String paramString2)
    {
        return moveDir(getFileByPath(paramString1), getFileByPath(paramString2));
    }

    public static boolean moveFile(File paramFile1, File paramFile2)
    {
        return copyOrMoveFile(paramFile1, paramFile2, true);
    }

    public static boolean moveFile(String paramString1, String paramString2)
    {
        return moveFile(getFileByPath(paramString1), getFileByPath(paramString2));
    }

    public static boolean rename(File paramFile, String paramString)
    {
        boolean bool = true;
        if (paramFile == null) {}
        while ((!paramFile.exists()) || (isSpace(paramString))) {
            return false;
        }
        if (paramString.equals(paramFile.getName())) {
            return true;
        }
        paramString = new File(paramFile.getParent() + File.separator + paramString);
        if ((!paramString.exists()) && (paramFile.renameTo(paramString))) {}
        for (;;)
        {
            return bool;
            bool = false;
        }
    }

    public static boolean rename(String paramString1, String paramString2)
    {
        return rename(getFileByPath(paramString1), paramString2);
    }
}
