package com.olym.mjt.module.notification;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.text.TextUtils;
import com.olym.mjt.databean.bean.MucRoom;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.AutoStartNotificationEvent;
import com.olym.mjt.databean.event.DataReadyEvent;
import com.olym.mjt.databean.event.ExceptionLogoutEvent;
import com.olym.mjt.databean.event.NetworkChangeEvent;
import com.olym.mjt.databean.event.ShowNetBarEvent;
import com.olym.mjt.databean.event.ShowNotificationEvent;
import com.olym.mjt.databean.event.SipRegisterEvent;
import com.olym.mjt.databean.event.TransportStateEvent;
import com.olym.mjt.datastat.UploadDataUtils;
import com.olym.mjt.im.IMService;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.keeplive.ScreenBroadcastListener;
import com.olym.mjt.module.main.MJTMainActivity;
import com.olym.mjt.network.client.HttpsClient;
import com.olym.mjt.pjsip.sip.api.SipCallSession;
import com.olym.mjt.pjsip.sip.event.CallEvent;
import com.olym.mjt.pjsip.sip.service.SipService;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.NetworkUtil;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.utils.siputils.SipAccountUtils;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NotificationService
        extends Service
{
    private static final long checkDelay = 1000L;
    public static boolean isStarted = false;
    private Intent IMServiceIntent;
    private int checkTime = 0;
    private boolean checkTokenDone = false;
    private Handler checkTokenHandle;
    private HandlerThread checkTokenHandleThread;
    private Runnable checkTokenRunable = new NotificationService.3(this);
    private Handler handler;
    private long lastLoadTime = 0L;
    private ArrayList<MucRoom> list;
    private BroadcastReceiver mNetWorkChangeReceiver = new NotificationService.1(this);
    private NotificationShow notificationShow;
    private int notify_im_status;
    private boolean notify_sip_status;
    private ScreenBroadcastListener screenBroadcastListener;
    private Intent sipServiceIntent;

    private void canceChecklUserToken()
    {
        if (this.checkTokenHandle != null)
        {
            this.checkTokenHandle.removeCallbacks(this.checkTokenRunable);
            Applog.info("-------canceChecklUserToken------");
        }
    }

    private void checkToken()
    {
        HttpsClient.getInstanse().autoLogin(new NotificationService.4(this));
    }

    private void checkTokenSuccess()
    {
        this.checkTime = 0;
        canceChecklUserToken();
        if (this.checkTokenDone)
        {
            Applog.info("��������������� ������TOKEN������");
            NetworkChangeEvent.post(new NetworkChangeEvent(true));
            return;
        }
        Applog.info("��������������� ������TOKEN������");
        this.checkTokenDone = true;
        cleaniosToken();
        dataReadySuccess();
    }

    private void checkUserToken()
    {
        if (this.checkTokenHandle != null)
        {
            this.checkTokenHandle.removeCallbacks(this.checkTokenRunable);
            this.checkTokenHandle.postDelayed(this.checkTokenRunable, 1000L);
        }
    }

    private void cleaniosToken()
    {
        HttpsClient.getInstanse().cleanIOSToken(MjtApplication.getInstance().getLoginUser().getTelephone());
    }

    private void dataReadySuccess()
    {
        Applog.systemOut("-----dataReadySuccess--- " + AppSpUtil.getInstanse().getInitRoom() + " " + AppSpUtil.getInstanse().getInitContacts() + " " + this.list);
        if ((AppSpUtil.getInstanse().getInitRoom()) && (AppSpUtil.getInstanse().getInitContacts()) && (this.list != null))
        {
            startSipService();
            startIMService(this.list);
            setChannel();
            Applog.systemOut("------checkTokenSuccess----true--");
            Applog.info("------checkTokenSuccess----true--");
            return;
        }
        Applog.systemOut("------checkTokenSuccess----false--");
    }

    private void downloadAddress(String paramString)
    {
        Applog.systemOut("-------downloadAddress---- " + paramString);
        Applog.info("-------downloadAddress---- " + paramString);
        HttpsClient.getInstanse().getVoipDomain(paramString, new NotificationService.6(this, paramString));
    }

    private void getNewToken()
    {
        Applog.systemOut("----getNewToken----");
        String str = UserSpUtil.getInstanse().getPhone();
        HttpsClient.getInstanse().getSipPassword(str, new NotificationService.5(this, str));
    }

    private void initScreenListener()
    {
        this.screenBroadcastListener = new ScreenBroadcastListener(this);
        this.screenBroadcastListener.registerListener();
    }

    private void setChannel()
    {
        HttpsClient.getInstanse().setchannelId();
    }

    private void startIMService(ArrayList<MucRoom> paramArrayList)
    {
        if (this.IMServiceIntent == null)
        {
            Applog.systemOut("-----startIMService-----");
            Applog.info("-----startIMService-----");
            this.IMServiceIntent = new Intent(this, IMService.class);
            startService(this.IMServiceIntent);
        }
    }

    private void startSipService()
    {
        if (this.sipServiceIntent == null)
        {
            Applog.systemOut("-----startSipService-----");
            Applog.info("-----startSipService-----");
            SipAccountUtils.checkAccount(this, UserSpUtil.getInstanse().getPhone(), UserSpUtil.getInstanse().getPassword(), UserSpUtil.getInstanse().getIBCDomain());
            this.sipServiceIntent = new Intent(this, SipService.class);
            startService(this.sipServiceIntent);
        }
    }

    private void stopIMService()
    {
        if (this.IMServiceIntent != null)
        {
            Applog.systemOut("-----stopIMService-----");
            Applog.info("-----stopIMService-----");
            stopService(this.IMServiceIntent);
            this.IMServiceIntent = null;
        }
    }

    private void stopSipService()
    {
        if (this.sipServiceIntent != null)
        {
            Applog.systemOut("-----stopSipService-----");
            Applog.info("-----stopSipService-----");
            stopService(this.sipServiceIntent);
            this.sipServiceIntent = null;
        }
    }

    private void toDoReconnect(int paramInt)
    {
        Applog.info("-----toDoReconnect----- " + paramInt);
        Applog.systemOut("-----toDoReconnect----- " + paramInt);
        if (paramInt == 1)
        {
            ShowNotificationEvent.post(new ShowNotificationEvent(4, false));
            MjtApplication.getInstance().setSipRegisted(false);
            checkUserToken();
        }
        do
        {
            return;
            if (paramInt == 2)
            {
                ShowNotificationEvent.post(new ShowNotificationEvent(3, -1));
                checkUserToken();
                return;
            }
        } while (paramInt != 3);
        ShowNotificationEvent.post(new ShowNotificationEvent(3, -1));
        ShowNotificationEvent.post(new ShowNotificationEvent(4, false));
        MjtApplication.getInstance().setSipRegisted(false);
        checkUserToken();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleAutoStartNotificationEvent(AutoStartNotificationEvent paramAutoStartNotificationEvent)
    {
        Applog.info("--------handleAutoStartNotificationEvent-------");
        Applog.systemOut("--------handleAutoStartNotificationEvent-------");
        this.notificationShow.aotoStartNotification();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleDataReady(DataReadyEvent paramDataReadyEvent)
    {
        dataReadySuccess();
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleExceptionLogout(ExceptionLogoutEvent paramExceptionLogoutEvent)
    {
        paramExceptionLogoutEvent = paramExceptionLogoutEvent.getKey();
        Applog.systemOut("-------handleExceptionLogout-------- " + paramExceptionLogoutEvent);
        Applog.info("-------handleExceptionLogout-------- " + paramExceptionLogoutEvent);
        Intent localIntent = new Intent(this, MJTMainActivity.class);
        localIntent.putExtra(paramExceptionLogoutEvent, true);
        localIntent.addFlags(268435456);
        startActivity(localIntent);
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleShowNotificationEvent(ShowNotificationEvent paramShowNotificationEvent)
    {
        if (!ChannelUtil.isShowNotify) {}
        do
        {
            do
            {
                return;
                switch (paramShowNotificationEvent.getType())
                {
                    default:
                        return;
                }
            } while (System.currentTimeMillis() - this.lastLoadTime <= 500L);
            this.lastLoadTime = System.currentTimeMillis();
            this.handler.postDelayed(new NotificationService.2(this, paramShowNotificationEvent), 500L);
            return;
            if (MjtApplication.getInstance().getMainSipCallSession() == null) {
                break;
            }
        } while (MjtApplication.getInstance().getMainSipCallSession().isActive());
        this.notificationShow.removeSipContentNotification();
        return;
        this.notificationShow.removeSipContentNotification();
        return;
        this.notificationShow.removeXmppContentNotification();
        return;
        this.notificationShow.updateSipCallingNotification();
        return;
        this.notificationShow.updateSipConfirmNotification();
        return;
        this.notificationShow.updateSipIncomingCallNotification();
        return;
        this.notificationShow.updateMissCallNotification(paramShowNotificationEvent.getContentValues());
        return;
        boolean bool = paramShowNotificationEvent.isOnline();
        if (bool != this.notify_sip_status)
        {
            Applog.info("-------updateSipNotification----- " + paramShowNotificationEvent.isOnline());
            Applog.systemOut("-------updateSipNotification----- " + paramShowNotificationEvent.isOnline());
            this.notificationShow.updateSipNotification(paramShowNotificationEvent.isOnline());
            this.notify_sip_status = bool;
            return;
        }
        Applog.info("-------updateSipNotification--���������������--- " + paramShowNotificationEvent.isOnline());
        Applog.systemOut("-------updateSipNotification--���������������--- " + paramShowNotificationEvent.isOnline());
        return;
        int i = paramShowNotificationEvent.getXmppStatus();
        if (this.notify_im_status != i)
        {
            Applog.info("-------updateXmppNotification----- " + paramShowNotificationEvent.getXmppStatus());
            Applog.systemOut("-------updateXmppNotification----- " + paramShowNotificationEvent.getXmppStatus());
            this.notificationShow.updateXmppNotification(paramShowNotificationEvent.getXmppStatus());
            this.notify_im_status = i;
            return;
        }
        Applog.info("-------updateXmppNotification--���������������--- " + paramShowNotificationEvent.getXmppStatus());
        Applog.systemOut("-------updateXmppNotification--���������������--- " + paramShowNotificationEvent.getXmppStatus());
        return;
        Applog.info("-------deleteNotification----- ");
        Applog.systemOut("-------deleteNotification----- ");
        this.notificationShow.deleteNotification();
    }

    @Subscribe(threadMode=ThreadMode.BACKGROUND)
    public void handleSipRegister(SipRegisterEvent paramSipRegisterEvent)
    {
        int i = paramSipRegisterEvent.getCode();
        Applog.info("-------Sip������������------" + i);
        Applog.systemOut("-------Sip������������------" + i);
        if (i == 200)
        {
            UploadDataUtils.uploadOnlineEvent();
            ShowNotificationEvent.post(new ShowNotificationEvent(4, true));
            MjtApplication.getInstance().setSipRegisted(true);
            if ((ChannelUtil.currentChannel == 106) && (!TextUtils.isEmpty(MjtApplication.getInstance().callNumber)) && (System.currentTimeMillis() - MjtApplication.getInstance().callTime < 30000L))
            {
                CallEvent.post(new CallEvent(MjtApplication.getInstance().callNumber));
                MjtApplication.getInstance().callNumber = null;
                MjtApplication.getInstance().callTime = 0L;
            }
        }
        while (i != 403) {
            return;
        }
    }

    @Subscribe(threadMode=ThreadMode.BACKGROUND)
    public void handleTransportState(TransportStateEvent paramTransportStateEvent)
    {
        toDoReconnect(paramTransportStateEvent.getState());
    }

    public IBinder onBind(Intent paramIntent)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        Applog.info("----NotificationService --onCreate----");
        Applog.systemOut("----NotificationService --onCreate----");
        this.handler = new Handler(getMainLooper());
        EventBusUtil.register(this);
        this.checkTokenHandleThread = new HandlerThread("handleTransportState");
        this.checkTokenHandleThread.start();
        this.checkTokenHandle = new Handler(this.checkTokenHandleThread.getLooper());
        this.notificationShow = new NotificationShow(this);
        if (ChannelUtil.isShowNotify) {
            this.notificationShow.showNotification();
        }
        initScreenListener();
        IntentFilter localIntentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(this.mNetWorkChangeReceiver, localIntentFilter);
    }

    public void onDestroy()
    {
        super.onDestroy();
        isStarted = false;
        Applog.info("----NotificationService --onDestroy----");
        Applog.systemOut("----NotificationService --onDestroy----");
        stopSipService();
        stopIMService();
        unregisterReceiver(this.mNetWorkChangeReceiver);
        EventBusUtil.unregister(this);
        this.screenBroadcastListener.unregisterListener();
        if (ChannelUtil.isShowNotify) {
            this.notificationShow.deleteNotification();
        }
        if (this.checkTokenHandleThread != null)
        {
            this.checkTokenHandleThread.quit();
            this.checkTokenHandleThread = null;
            this.checkTokenHandle = null;
        }
    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
    {
        Applog.info("----NotificationService --onStartCommand----");
        Applog.systemOut("----NotificationService --onStartCommand----");
        if (isStarted) {
            if (NetworkUtil.isConnected(this))
            {
                Applog.systemOut("----������������������-----");
                Applog.info("----������������������-----");
                toDoReconnect(3);
                ShowNetBarEvent.post(new ShowNetBarEvent(false));
            }
        }
        for (;;)
        {
            return 2;
            Applog.systemOut("-----������������������----");
            Applog.info("----������������������-----");
            ShowNetBarEvent.post(new ShowNetBarEvent(true));
            canceChecklUserToken();
            continue;
            isStarted = true;
        }
    }
}

