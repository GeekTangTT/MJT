package com.olym.mjt.module.gallery.previewphoto;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.module.gallery.model.PhotoInfo;
import com.olym.mjt.utils.Applog;
import com.tmall.ultraviewpager.UltraViewPager;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427419)
public class PreviewPhotoActivity
        extends BaseTopbarActivity<PreviewPhotoPresenter>
        implements IPreviewPhotoView
{
    public static final String KEY_DATA = "key_data";
    public static final String KEY_INDEX = "key_index";
    private PreviewPhotoAdapter adapter;
    private ArrayList<PhotoInfo> datas;
    @ViewInject(2131231023)
    private ImageView imageView;
    private int index;
    private PhotoInfo photoInfo;
    @ViewInject(2131231518)
    private UltraViewPager ultraViewPager;

    private byte[] getByte(File paramFile)
            throws Exception
    {
        byte[] arrayOfByte = null;
        if (paramFile != null)
        {
            FileInputStream localFileInputStream = new FileInputStream(paramFile);
            int i = (int)paramFile.length();
            if (i > Integer.MAX_VALUE) {}
            do
            {
                return null;
                arrayOfByte = new byte[i];
                i = 0;
                while (i < arrayOfByte.length)
                {
                    int j = localFileInputStream.read(arrayOfByte, i, arrayOfByte.length - i);
                    if (j < 0) {
                        break;
                    }
                    i += j;
                }
            } while (i < arrayOfByte.length);
            localFileInputStream.close();
        }
        return arrayOfByte;
    }

    private void setIndexTitle(int paramInt)
    {
        setTitleText(getResources().getString(2131690123));
    }

    private void showImage(PhotoInfo paramPhotoInfo, ImageView paramImageView)
    {
        if (paramPhotoInfo.isEncry()) {
            paramPhotoInfo = new File(paramPhotoInfo.getPhotoPath());
        }
        do
        {
            try
            {
                paramPhotoInfo = getByte(paramPhotoInfo);
                if (paramPhotoInfo != null)
                {
                    int[] arrayOfInt = new int[1];
                    EngineUtils.getInstance().sm9P7DataDecrypt(paramPhotoInfo, paramPhotoInfo.length, null, arrayOfInt);
                    byte[] arrayOfByte = new byte[arrayOfInt[0]];
                    EngineUtils.getInstance().sm9P7DataDecrypt(paramPhotoInfo, paramPhotoInfo.length, arrayOfByte, arrayOfInt);
                    paramPhotoInfo = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
                    if (paramPhotoInfo != null) {
                        paramImageView.setImageBitmap(paramPhotoInfo);
                    }
                }
                return;
            }
            catch (Exception paramPhotoInfo)
            {
                LogFinalUtils.logForException(paramPhotoInfo);
                return;
            }
            paramPhotoInfo = BitmapFactory.decodeFile(paramPhotoInfo.getPhotoPath());
        } while (paramPhotoInfo == null);
        paramImageView.setImageBitmap(paramPhotoInfo);
    }

    public void destroy() {}

    public void handleBundle(Bundle paramBundle)
    {
        this.photoInfo = ((PhotoInfo)paramBundle.getParcelable("key_data"));
        if (this.photoInfo == null)
        {
            finish();
            return;
        }
        Applog.systemOut("----handleBundle----- " + this.photoInfo.getPhotoPath());
    }

    public void init()
    {
        setIndexTitle(this.index);
        showImage(this.photoInfo, this.imageView);
    }

    protected void setPresenter()
    {
        this.presenter = new PreviewPhotoPresenter(this);
    }
}

