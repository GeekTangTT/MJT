package com.olym.mjt.module.settings.notificationsettings.selectringtone;

import android.content.res.Resources;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.SoundBean;
import com.olym.mjt.databean.event.PlaySoundEvent;
import com.olym.mjt.module.fileexplorer.utils.FileCategoryHelper;
import com.olym.mjt.module.settings.notificationsettings.SoundBeanAdapter;
import com.olym.mjt.utils.CachedThreadPoolUtils;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.widget.dialog.LoadingDialog;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427430)
public class SelectRingToneActivity
        extends BaseTopbarActivity<SelectRingTonePresenter>
        implements ISelectRingToneView
{
    private SoundBeanAdapter leftAdapter;
    private ArrayList<SoundBean> leftDatas = new ArrayList();
    @ViewInject(2131231074)
    private ListView listview;
    private LoadingDialog loadingDialog;
    private MediaPlayer mediaPlayer;
    private SoundBeanAdapter rightAdapter;
    private ArrayList<SoundBean> rightDatas = new ArrayList();
    @ViewInject(2131231506)
    private TextView tv_tab_music;
    @ViewInject(2131231507)
    private TextView tv_tab_ring;

    private void checkTab(boolean paramBoolean)
    {
        if (paramBoolean)
        {
            this.tv_tab_ring.setSelected(true);
            this.tv_tab_music.setSelected(false);
            this.listview.setAdapter(this.leftAdapter);
            return;
        }
        this.tv_tab_music.setSelected(true);
        this.tv_tab_ring.setSelected(false);
        this.listview.setAdapter(this.rightAdapter);
    }

    private void initLeftDatas()
    {
        SoundBean localSoundBean1 = AppSpUtil.getInstanse().getRingTone();
        RingtoneManager localRingtoneManager = new RingtoneManager(this);
        localRingtoneManager.setType(1);
        int j = localRingtoneManager.getCursor().getCount();
        Object localObject1 = AppSpUtil.getInstanse().getDefaultRingTone();
        if (localSoundBean1.isSame((SoundBean)localObject1)) {
            ((SoundBean)localObject1).setSelected(true);
        }
        this.leftDatas.add(localObject1);
        int i = 0;
        while (i < j)
        {
            Object localObject2 = localRingtoneManager.getRingtone(i);
            localObject1 = localRingtoneManager.getRingtoneUri(i);
            localObject2 = ((Ringtone)localObject2).getTitle(this);
            SoundBean localSoundBean2 = new SoundBean();
            localSoundBean2.setTitle((String)localObject2);
            localSoundBean2.setUri(((Uri)localObject1).toString());
            if (localSoundBean2.isSame(localSoundBean1)) {
                localSoundBean2.setSelected(true);
            }
            this.leftDatas.add(localSoundBean2);
            i += 1;
        }
    }

    private void initRightDatas()
    {
        SoundBean localSoundBean1 = AppSpUtil.getInstanse().getRingTone();
        FileCategoryHelper localFileCategoryHelper = new FileCategoryHelper(this);
        Cursor localCursor = localFileCategoryHelper.query(3);
        if (localCursor != null) {
            while (localCursor.moveToNext())
            {
                String str1 = localCursor.getString(1);
                String str2 = localFileCategoryHelper.getNameFromFilepath(str1);
                if ((!str2.contains("SM9")) || (!str2.contains(".SM9")))
                {
                    SoundBean localSoundBean2 = new SoundBean();
                    localSoundBean2.setTitle(str2);
                    localSoundBean2.setUri(str1);
                    if (localSoundBean2.isSame(localSoundBean1)) {
                        localSoundBean2.setSelected(true);
                    }
                    this.rightDatas.add(localSoundBean2);
                }
            }
        }
    }

    private void initViews()
    {
        this.loadingDialog.show();
        CachedThreadPoolUtils.getInstance().execute(new SelectRingToneActivity.3(this));
    }

    private void stopPlay()
    {
        if ((this.mediaPlayer != null) && (this.mediaPlayer.isPlaying()))
        {
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }

    public void destroy()
    {
        if (this.loadingDialog != null) {
            this.loadingDialog.dismiss();
        }
        EventBusUtil.unregister(this);
        stopPlay();
    }

    public void handleBundle(Bundle paramBundle) {}

    @Subscribe(threadMode=ThreadMode.ASYNC)
    public void handlePlaySound(PlaySoundEvent paramPlaySoundEvent)
    {
        stopPlay();
        this.mediaPlayer = MediaPlayer.create(this, Uri.parse(paramPlaySoundEvent.getSoundBean().getUri()));
        this.mediaPlayer.start();
    }

    public void init()
    {
        setTitleText(getResources().getString(2131689845));
        EventBusUtil.register(this);
        this.loadingDialog = new LoadingDialog(this);
        this.tv_tab_ring.setSelected(true);
        this.tv_tab_music.setSelected(false);
        this.tv_tab_ring.setOnClickListener(new SelectRingToneActivity.1(this));
        this.tv_tab_music.setOnClickListener(new SelectRingToneActivity.2(this));
        initViews();
    }

    protected void setPresenter()
    {
        this.presenter = new SelectRingTonePresenter(this);
    }
}

