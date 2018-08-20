package com.olym.mjt.utils;

import android.content.Context;
import android.content.res.Resources;
import com.olym.mjt.databean.bean.UploadLogResponseBean;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.network.client.FastJsonObjectCallback;
import com.olym.mjt.network.client.HttpsFileClient;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Request;

public class AppLogUpload
{
    private static AppLogUpload INSTANCE = null;
    private Context appContext;

    private void deleteUploadFile(File paramFile)
    {
        if ((paramFile != null) && (paramFile.exists())) {
            paramFile.delete();
        }
    }

    public static AppLogUpload getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new AppLogUpload();
        }
        return INSTANCE;
    }

    public void UploadZipFile(final Context paramContext, final UploadInterface paramUploadInterface)
    {
        this.appContext = paramContext.getApplicationContext();
        final File localFile = new File(this.appContext.getExternalFilesDir(null).getAbsolutePath(), "sendfile.zip");
        if (!localFile.exists()) {}
        try
        {
            localFile.createNewFile();
            XZip.ZipFolder(MjtApplication.getInstance().mLogDir, localFile.getAbsolutePath());
            HttpsFileClient.getInstanse().uploadLog(localFile, new FastJsonObjectCallback()
            {
                public void onBefore(Request paramAnonymousRequest, int paramAnonymousInt)
                {
                    super.onBefore(paramAnonymousRequest, paramAnonymousInt);
                    paramUploadInterface.uploadBefore();
                }

                public void onError(Call paramAnonymousCall, Exception paramAnonymousException, int paramAnonymousInt)
                {
                    paramUploadInterface.uploadError(paramContext.getResources().getString(2131689941));
                    AppLogUpload.this.deleteUploadFile(localFile);
                }

                public void onResponse(UploadLogResponseBean paramAnonymousUploadLogResponseBean, int paramAnonymousInt)
                {
                    switch (paramAnonymousUploadLogResponseBean.getCode())
                    {
                        default:
                            paramUploadInterface.uploadError(paramAnonymousUploadLogResponseBean.getError());
                            AppLogUpload.this.deleteUploadFile(localFile);
                            return;
                        case 0:
                            paramUploadInterface.uploadSuccess();
                            AppLogUpload.this.deleteUploadFile(localFile);
                            return;
                        case 13:
                            paramUploadInterface.uploadError(AppLogUpload.this.appContext.getResources().getString(2131690259));
                            AppLogUpload.this.deleteUploadFile(localFile);
                            return;
                    }
                    paramUploadInterface.uploadError(AppLogUpload.this.appContext.getResources().getString(2131690256));
                    AppLogUpload.this.deleteUploadFile(localFile);
                }
            });
            return;
        }
        catch (IOException localIOException)
        {
            for (;;)
            {
                localIOException.printStackTrace();
            }
        }
        catch (Exception localException)
        {
            for (;;)
            {
                localException.printStackTrace();
            }
        }
    }

    public static abstract interface UploadInterface
    {
        public abstract void uploadBefore();

        public abstract void uploadError(String paramString);

        public abstract void uploadSuccess();
    }
}
