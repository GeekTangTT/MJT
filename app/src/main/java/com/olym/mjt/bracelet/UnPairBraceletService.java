package com.olym.mjt.bracelet;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.olym.mjt.bracelet.event.BraceleteAgainEvent;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.EventBusUtil;
import com.smartteam.ble.bluetooth.CmdRequest;
import com.smartteam.ble.bluetooth.LeController;
import com.smartteam.ble.bluetooth.TransUtil;
import com.smartteam.ble.bluetooth.impl.CmdStat;
import com.smartteam.ble.bluetooth.impl.LESTATUS;
import com.smartteam.ble.entity.PushInfoEntity;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UnPairBraceletService
        extends Service
{
    private static final int MAX_TIME_OUT_COUNT = 6;
    Handler mHandler = new UnPairBraceletService.5(this, Looper.getMainLooper());
    private int timeOutCount = 0;

    private void dealWithBluetoothLe()
    {
        try
        {
            LeController.INSTANCE.sdkInitialize(getApplicationContext(), new UnPairBraceletService.1(this));
            LeController.INSTANCE.registerLeReceiver(this);
            LeController.setIsDebugEnabled(true);
            LeController.INSTANCE.setScanTimeOut(5);
            LeController.INSTANCE.setLeStatus(LESTATUS.LE_BRACELET);
            LeController.INSTANCE.setReConnFlag(true);
            LeController.INSTANCE.setOnGattLeListener(new UnPairBraceletService.2(this));
            LeController.INSTANCE.setOnConnectStateListener(new UnPairBraceletService.3(this));
            LeController.INSTANCE.setOnSyncTimeListener(new UnPairBraceletService.4(this));
            if (!LeController.INSTANCE.isBluetoothLeOpen()) {
                LeController.INSTANCE.openBluetoothLe();
            }
            if ((LeController.INSTANCE.isBluetoothLeOpen()) && (!LeController.INSTANCE.connectState))
            {
                Applog.systemOut("-------startScanLe-------");
                Applog.info("-------startScanLe-------");
                LeController.INSTANCE.startScanLe();
            }
            LeController.INSTANCE.pushInfo.setDnd_switch("N");
            LeController.INSTANCE.pushInfo.setPhone_switch("Y");
            LeController.INSTANCE.pushInfo.setSms_switch("Y");
            LeController.INSTANCE.pushInfo.setSelfdefine_platform("11111");
            LeController.INSTANCE.pushInfo.setSelfdefine_switch("Y");
            return;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }

    private void unPair()
    {
        LeController.getInstance().enqueue(new CmdRequest(CmdStat.UNPAIR, TransUtil.UNPAIR()), new UnPairBraceletService.6(this));
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleAgain(BraceleteAgainEvent paramBraceleteAgainEvent)
    {
        dealWithBluetoothLe();
    }

    @Nullable
    public IBinder onBind(Intent paramIntent)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        Applog.systemOut("---UnPairBraceletService---onCreate----- ");
        EventBusUtil.register(this);
        dealWithBluetoothLe();
    }

    public void onDestroy()
    {
        super.onDestroy();
        Applog.systemOut("---UnPairBraceletService---onDestroy-----");
        LeController.INSTANCE.stopScanLe();
        LeController.INSTANCE.unregisterLeReceiver(this);
        LeController.INSTANCE.setOnGattLeListener(null);
        LeController.INSTANCE.setOnConnectStateListener(null);
        EventBusUtil.unregister(this);
    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
    {
        Applog.systemOut("---UnPairBraceletService---onStartCommand-----");
        return 2;
    }
}
