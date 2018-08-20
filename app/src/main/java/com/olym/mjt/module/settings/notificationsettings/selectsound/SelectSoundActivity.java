package com.olym.mjt.module.settings.notificationsettings.selectsound;

import android.content.res.Resources;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.databean.bean.SoundBean;
import com.olym.mjt.databean.event.PlaySoundEvent;
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

@ContentView(2131427431)
public class SelectSoundActivity
        extends BaseTopbarActivity<SelectSoundPresenter>
        implements ISelectSoundView
{
    private SoundBeanAdapter adapter;
    private ArrayList<SoundBean> datas = new ArrayList();
    @ViewInject(2131231074)
    private ListView listview;
    private LoadingDialog loadingDialog;
    private MediaPlayer mediaPlayer;

    private void initDatas()
    {
        SoundBean localSoundBean1 = AppSpUtil.getInstanse().getNotificationSound();
        RingtoneManager localRingtoneManager = new RingtoneManager(this);
        localRingtoneManager.setType(2);
        int j = localRingtoneManager.getCursor().getCount();
        Object localObject1 = AppSpUtil.getInstanse().getDefaultNotificationSound();
        if (localSoundBean1.isSame((SoundBean)localObject1)) {
            ((SoundBean)localObject1).setSelected(true);
        }
        this.datas.add(localObject1);
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
            this.datas.add(localSoundBean2);
            i += 1;
        }
    }

    private void initViews()
    {
        this.loadingDialog.show();
        CachedThreadPoolUtils.getInstance().execute(new SelectSoundActivity.1(this));
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
        setTitleText(getResources().getString(2131689954));
        EventBusUtil.register(this);
        this.loadingDialog = new LoadingDialog(this);
        initViews();
    }

    protected void setPresenter()
    {
        this.presenter = new SelectSoundPresenter(this);
    }
}

