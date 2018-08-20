package cn.com.ukey;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.bhd.aidlBleService.ApduMaker;
import com.smartteam.ble.bluetooth.LeController;
import com.smartteam.ble.bluetooth.LeController.QuinticCallback;
import com.smartteam.ble.bluetooth.impl.ApduCallback;
import com.smartteam.ble.bluetooth.impl.ConnState;
import com.smartteam.ble.bluetooth.impl.InitializeCallback;
import com.smartteam.ble.bluetooth.impl.LESTATUS;
import com.smartteam.ble.bluetooth.impl.OnGattLeListener;
import com.smartteam.ble.bluetooth.impl.ScanType;
import com.smartteam.ble.entity.LeDeviceEntity;
import org.apache.http.util.ByteArrayBuffer;

public class BLEDeviceIoET
        extends AudioDeviceIo
{
    private static final String TAG = "BLET";
    private static boolean isRest = false;
    private static final int mTimeout = 7000;
    public String DeviceName = "";
    private byte[] Sendata;
    private boolean isBound = false;
    private boolean isPowerOn = false;
    private boolean isReturn = false;
    private boolean isback = false;
    private boolean isback_power = false;
    private boolean isreconnect_old_device = false;
    private Object mProcessLock = null;
    private Response mResponse = new Response();
    private Context mcontext = null;
    private Context myContext;

    private void InitOnGattLeListner(boolean paramBoolean)
    {
        this.isBound = false;
        try
        {
            LeController.INSTANCE.registerLeReceiver(this.myContext);
            LeController.INSTANCE.setOnGattLeListener(new OnGattLeListener()
            {
                public void onConnect(ConnState paramAnonymousConnState)
                {
                    try
                    {
                        Log.e("BLET", "onConnect==> state:" + paramAnonymousConnState.toString());
                        if (paramAnonymousConnState == ConnState.DISCONNECT)
                        {
                            Log.e("BLET", "******************DISCONNECT******************");
                            return;
                        }
                        if (paramAnonymousConnState == ConnState.CONNECTED)
                        {
                            BLEDeviceIoET.this.isBound = true;
                            Log.e("BLET", "******************CONNECTED******************");
                            return;
                        }
                    }
                    catch (Exception paramAnonymousConnState)
                    {
                        paramAnonymousConnState.printStackTrace();
                        return;
                    }
                    if (paramAnonymousConnState == ConnState.CONNECTING_1)
                    {
                        if (!BLEDeviceIoET.this.isreconnect_old_device) {
                            Log.e("-------connecting ", "------double tap------------");
                        }
                        Log.e("BLET", "******************CONNECTING_1******************");
                        return;
                    }
                    if (paramAnonymousConnState == ConnState.CONNECTING_3)
                    {
                        Log.e("BLET", "******************CONNECTING_3******************");
                        return;
                    }
                    if (paramAnonymousConnState == ConnState.CONNECTING_TIMEOUT) {
                        Log.e("-------connecting ", "------CONNECTING_TIMEOUT------------");
                    }
                }

                public void scanLeResult(LeDeviceEntity paramAnonymousLeDeviceEntity, ScanType paramAnonymousScanType) {}
            });
            if (!LeController.INSTANCE.isBluetoothLeOpen()) {
                LeController.INSTANCE.openBluetoothLe();
            }
            if ((LeController.INSTANCE.isBluetoothLeOpen()) && (!LeController.INSTANCE.isConnected)) {
                LeController.INSTANCE.startScanLe();
            }
            return;
        }
        catch (Exception localException)
        {
            for (;;)
            {
                localException.printStackTrace();
            }
        }
    }

    private void PauseThread(long paramLong)
    {
        if (this.mProcessLock == null) {
            this.mProcessLock = this;
        }
        synchronized (this.mProcessLock)
        {
            Log.e("BLET", "Pause Thread");
            try
            {
                this.mProcessLock.wait(paramLong);
                return;
            }
            catch (InterruptedException localInterruptedException)
            {
                for (;;)
                {
                    Log.e("BLET", "Pause Thread Exception");
                    localInterruptedException.printStackTrace();
                }
            }
        }
    }

    private void initSdk(sdkType paramsdkType)
    {
        LeController.INSTANCE.sdkInitialize(this.mcontext, new InitializeCallback()
        {
            public void onInitialized(boolean paramAnonymousBoolean)
            {
                LeController.INSTANCE.fetchConnectInfo(BLEDeviceIoET.this.mcontext);
            }
        });
        LeController.INSTANCE.setLeStatus(LESTATUS.LE_PAY_APDU);
        LeController.INSTANCE.registerLeReceiver(this.mcontext);
        paramsdkType = LeController.INSTANCE;
        LeController.setIsDebugEnabled(true);
    }

    public int autoConfig(Context paramContext, IUKeyResponseCallback paramIUKeyResponseCallback)
    {
        return super.autoConfig(paramContext, paramIUKeyResponseCallback);
    }

    public boolean bound(Context paramContext)
    {
        this.mcontext = paramContext;
        if (LeController.INSTANCE.isConnected) {
            return true;
        }
        if (!LeController.INSTANCE.isBluetoothLeOpen()) {
            LeController.INSTANCE.openBluetoothLe();
        }
        initSdk(sdkType.BRACELET);
        InitOnGattLeListner(false);
        long l = System.currentTimeMillis();
        while ((System.currentTimeMillis() - l < 14000L) && (!this.isBound)) {}
        return this.isBound;
    }

    public void clear()
    {
        super.clear();
    }

    public int connect()
    {
        return super.connect();
    }

    public int connect(String paramString)
    {
        if (this.mProcessLock == null) {
            this.mProcessLock = this;
        }
        if (!LeController.INSTANCE.isConnected) {}
        while (!poweron()) {
            try
            {
                int i = connect_se("ble");
                if (i != 36864) {
                    return -1;
                }
            }
            catch (InterruptedException paramString)
            {
                paramString.printStackTrace();
            }
        }
        SystemClock.sleep(200L);
        return 36864;
    }

    public int connect_se(String paramString)
            throws InterruptedException
    {
        if (toSendCmd()) {
            return 36864;
        }
        return -1;
    }

    public int disconnect()
    {
        if (!poweroff()) {
            return -1;
        }
        return 36864;
    }

    public int disconnect_se()
    {
        return 36864;
    }

    protected byte[] getChannelKeyPlain()
    {
        return super.getChannelKeyPlain();
    }

    protected String getResponse()
    {
        return super.getResponse();
    }

    public String init(Context paramContext, String paramString)
    {
        paramString = new String();
        this.myContext = paramContext;
        Log.i("BLET", "*******************Entry init******************");
        try
        {
            this.mcontext = paramContext;
            if (LeController.INSTANCE.isConnected)
            {
                if (isRest)
                {
                    Log.i("BLET", "******************* isRest");
                    isRest = false;
                    if (connect_se("ble") == 36864)
                    {
                        if (poweron())
                        {
                            this.DeviceName = "bleDevice=11:22:33:44:55\000";
                            Log.i("BLET", "*******************connect Return Device:" + this.DeviceName);
                            SystemClock.sleep(200L);
                            return this.DeviceName;
                        }
                        Log.i("BLET", "*******************reconnect Return Device:");
                        return "NULL";
                    }
                    return "NULL";
                }
                if (poweron())
                {
                    this.DeviceName = "bleDevice=11:22:33:44:55\000";
                    Log.i("BLET", "*******************reconnect Return Device:" + this.DeviceName);
                    SystemClock.sleep(200L);
                    return this.DeviceName;
                }
                Log.i("BLET", "*******************reconnect Return Device:");
                return "NULL";
            }
            if (connect_se("ble") == 36864)
            {
                if (poweron())
                {
                    this.DeviceName = "bleDevice=11:22:33:44:55\000";
                    Log.i("BLET", "*******************connect Return Device:" + this.DeviceName);
                    SystemClock.sleep(200L);
                    return this.DeviceName;
                }
                Log.i("BLET", "*******************reconnect Return Device:");
                return "NULL";
            }
            return "NULL";
        }
        catch (Exception paramContext)
        {
            paramContext.printStackTrace();
        }
        return paramString;
    }

    public void init(Context paramContext, IUKeyResponseCallback paramIUKeyResponseCallback)
    {
        super.init(paramContext, paramIUKeyResponseCallback);
    }

    public boolean poweroff()
    {
        try
        {
            this.isback = false;
            this.isPowerOn = true;
            LeController.INSTANCE.smartCardPowerOff(new LeController.QuinticCallback()
            {
                public void onComplete(Object arg1)
                {
                    BLEDeviceIoET.this.isback = true;
                    BLEDeviceIoET.this.isPowerOn = true;
                    synchronized (BLEDeviceIoET.this.mProcessLock)
                    {
                        BLEDeviceIoET.this.mProcessLock.notify();
                        return;
                    }
                }

                public void onError(Exception arg1)
                {
                    BLEDeviceIoET.this.isback = true;
                    synchronized (BLEDeviceIoET.this.mProcessLock)
                    {
                        BLEDeviceIoET.this.mProcessLock.notify();
                        return;
                    }
                }

                public void onProgress(int paramAnonymousInt) {}
            });
            PauseThread(7000L);
            return this.isPowerOn;
        }
        catch (Exception localException)
        {
            for (;;)
            {
                localException.printStackTrace();
            }
        }
    }

    public boolean poweron()
    {
        try
        {
            this.isback_power = false;
            this.isPowerOn = true;
            LeController.INSTANCE.smartCardPowerOn(new LeController.QuinticCallback()
            {
                public void onComplete(Object arg1)
                {
                    Log.e("BLET", "power on Complete");
                    synchronized (BLEDeviceIoET.this.mProcessLock)
                    {
                        BLEDeviceIoET.this.isPowerOn = true;
                        BLEDeviceIoET.this.mProcessLock.notifyAll();
                        return;
                    }
                }

                public void onError(Exception arg1)
                {
                    Log.e("BLET", "power on fialed");
                    synchronized (BLEDeviceIoET.this.mProcessLock)
                    {
                        BLEDeviceIoET.this.mProcessLock.notifyAll();
                        return;
                    }
                }

                public void onProgress(int paramAnonymousInt) {}
            });
            Log.e("BLET", "PauseThread power on ");
            PauseThread(7000L);
            Log.e("BLET", "return  power on :" + this.isPowerOn);
            return this.isPowerOn;
        }
        catch (Exception localException)
        {
            for (;;)
            {
                localException.printStackTrace();
            }
        }
    }

    public boolean reconnect(Context paramContext)
    {
        if (LeController.INSTANCE.isConnected) {}
        do
        {
            return true;
            this.mcontext = paramContext;
            initSdk(sdkType.BRACELET);
            LeController.INSTANCE.registerLeReceiver(paramContext);
            LeController.INSTANCE.setOnGattLeListener(new OnGattLeListener()
            {
                public void onConnect(ConnState paramAnonymousConnState)
                {
                    try
                    {
                        Log.e("BLET", "onConnect==> state:" + paramAnonymousConnState.toString());
                        if (paramAnonymousConnState == ConnState.DISCONNECT)
                        {
                            Log.e("onConnect", "DISCONNECT...");
                            LeController.INSTANCE.startScanLe();
                            return;
                        }
                        if (paramAnonymousConnState == ConnState.CONNECTING_UNPAIR)
                        {
                            BLEDeviceIoET.this.isBound = false;
                            return;
                        }
                    }
                    catch (Exception paramAnonymousConnState)
                    {
                        paramAnonymousConnState.printStackTrace();
                        return;
                    }
                    if (paramAnonymousConnState == ConnState.CONNECTED)
                    {
                        Log.e("onConnect", "CONNECTED...");
                        LeController.INSTANCE.stopScanLe();
                    }
                }

                public void scanLeResult(LeDeviceEntity paramAnonymousLeDeviceEntity, ScanType paramAnonymousScanType) {}
            });
            if (!LeController.INSTANCE.isBluetoothLeOpen()) {
                LeController.INSTANCE.openBluetoothLe();
            }
            if ((LeController.INSTANCE.isBluetoothLeOpen()) && (!LeController.INSTANCE.isConnected)) {
                LeController.INSTANCE.startScanLe();
            }
            long l = System.currentTimeMillis();
            while ((System.currentTimeMillis() - l < 7000L) && (!LeController.INSTANCE.isConnected)) {}
        } while (LeController.INSTANCE.isConnected);
        return false;
    }

    protected int sendCommand(String paramString, StringBuffer paramStringBuffer)
    {
        return super.sendCommand(paramString, paramStringBuffer);
    }

    public int sendCommand(byte[] paramArrayOfByte, ByteArrayBuffer paramByteArrayBuffer)
    {
        return super.sendCommand(paramArrayOfByte, paramByteArrayBuffer);
    }

    public Response sendCommand(String paramString)
    {
        Log.e("BLET", "ready send:" + paramString);
        this.mResponse = new Response();
        this.isReturn = false;
        if (isRest)
        {
            LeController.INSTANCE.disconnect();
            PauseThread(3500L);
            return this.mResponse;
        }
        LeController localLeController = LeController.INSTANCE;
        LeController.setIsDebugEnabled(true);
        LeController.INSTANCE.setApduCallback(new ApduCallback()
        {
            public void onSuccess(byte[] arg1)
            {
                Log.e("BLET", "<--return :" + ApduMaker.byte2HexStr(???, ???.length));
                if (???.length >= 2)
                {
                    BLEDeviceIoET.this.mResponse.setResponseApdu(ApduMaker.byte2HexStr(???, ???.length - 2));
                    int i = ???[(???.length - 2)];
                    int j = ???[(???.length - 1)];
                    BLEDeviceIoET.this.mResponse.setReturnCode((i << 8 & 0xFF00) + (j & 0xFF));
                }
                BLEDeviceIoET.this.isReturn = true;
                synchronized (BLEDeviceIoET.this.mProcessLock)
                {
                    BLEDeviceIoET.this.mProcessLock.notifyAll();
                    return;
                }
            }
        });
        paramString = ApduMaker.hexStr2Bytes(paramString);
        this.Sendata = paramString;
        LeController.INSTANCE.smartCardTransmission(paramString, new LeController.QuinticCallback()
        {
            public void onComplete(Object paramAnonymousObject)
            {
                Log.e("BLET", new StringBuilder(String.valueOf("-->send:")).append(ApduMaker.byte2HexStr(BLEDeviceIoET.this.Sendata, BLEDeviceIoET.this.Sendata.length)).toString() + "\n");
            }

            public void onError(Exception arg1)
            {
                Log.e("BLET", "-->send apdu Error\n");
                if (??? != null)
                {
                    BLEDeviceIoET.this.isReturn = true;
                    synchronized (BLEDeviceIoET.this.mProcessLock)
                    {
                        BLEDeviceIoET.this.mProcessLock.notifyAll();
                        return;
                    }
                }
            }

            public void onProgress(int paramAnonymousInt) {}
        });
        Log.e("BLET", "PauseThread sendCommand ");
        PauseThread(7000L);
        return this.mResponse;
    }

    public boolean toSendCmd()
            throws InterruptedException
    {
        if (this.mProcessLock == null) {
            this.mProcessLock = this;
        }
        this.isback = false;
        Log.e("BLET", "transBluetoothLe");
        try
        {
            initSdk(sdkType.PAY);
            LeController.INSTANCE.setOnGattLeListener(new OnGattLeListener()
            {
                public void onConnect(final ConnState paramAnonymousConnState)
                {
                    try
                    {
                        new Handler(Looper.getMainLooper()).post(new Runnable()
                        {
                            public void run()
                            {
                                Log.e("BLET", "onConnect==> state:" + paramAnonymousConnState.toString());
                                if (paramAnonymousConnState == ConnState.DISCONNECT)
                                {
                                    Log.e("BLET", "DISCONNECT to ScanLe...");
                                    if (BLEDeviceIoET.isRest) {
                                        synchronized (BLEDeviceIoET.this.mProcessLock)
                                        {
                                            BLEDeviceIoET.this.mProcessLock.notifyAll();
                                            return;
                                        }
                                    }
                                    if (LeController.INSTANCE.isBluetoothLeOpen()) {
                                        LeController.INSTANCE.startScanLe();
                                    }
                                }
                                else
                                {
                                    if (paramAnonymousConnState == ConnState.CONNECTED_SE)
                                    {
                                        LeController.INSTANCE.stopScanLe();
                                        BLEDeviceIoET.this.isback = true;
                                        BLEDeviceIoET.isRest = false;
                                        synchronized (BLEDeviceIoET.this.mProcessLock)
                                        {
                                            BLEDeviceIoET.this.mProcessLock.notifyAll();
                                            Log.e("BLET", "CONNECTED_JINGDIAN Finished");
                                            return;
                                        }
                                    }
                                    if (paramAnonymousConnState == ConnState.CONNECT_AUTO_POW_OFF)
                                    {
                                        BLEDeviceIoET.isRest = true;
                                        Log.e("BLET", "CONNECT AUTO POW OFF,isRest" + BLEDeviceIoET.isRest);
                                    }
                                }
                            }
                        });
                        return;
                    }
                    catch (Exception paramAnonymousConnState)
                    {
                        paramAnonymousConnState.printStackTrace();
                    }
                }

                public void scanLeResult(LeDeviceEntity paramAnonymousLeDeviceEntity, ScanType paramAnonymousScanType)
                {
                    Log.e("BLET", "scanLeResult:type=" + paramAnonymousScanType);
                    Log.e("info", "scanLeResult:type=" + paramAnonymousScanType);
                }
            });
            if (LeController.INSTANCE.isBluetoothLeOpen()) {
                if (!LeController.INSTANCE.isConnected) {
                    LeController.INSTANCE.startScanLe();
                }
            }
            for (;;)
            {
                Log.e("BLET", "PauseThread SCAN");
                PauseThread(49000L);
                return this.isback;
                LeController.INSTANCE.openBluetoothLe();
                LeController.INSTANCE.startScanLe();
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

    public boolean toSendCmd_Thread()
    {
        if (LeController.INSTANCE.isConnected) {
            return true;
        }
        this.isback = false;
        Log.e("BLET", "transBluetoothLe");
        try
        {
            initSdk(sdkType.PAY);
            LeController.INSTANCE.registerLeReceiver(this.myContext);
            LeController.INSTANCE.setOnGattLeListener(new OnGattLeListener()
            {
                public void onConnect(final ConnState paramAnonymousConnState)
                {
                    try
                    {
                        new Handler(Looper.getMainLooper()).post(new Runnable()
                        {
                            public void run()
                            {
                                Process.setThreadPriority(-19);
                                Log.e("BLET", "onConnect==> state:" + paramAnonymousConnState.toString());
                                if (paramAnonymousConnState == ConnState.DISCONNECT)
                                {
                                    Log.e("BLET", "DISCONNECT to ScanLe...");
                                    if (LeController.INSTANCE.isBluetoothLeOpen()) {}
                                }
                                while (paramAnonymousConnState != ConnState.CONNECTED_SE)
                                {
                                    return;
                                    LeController.INSTANCE.startScanLe();
                                    return;
                                }
                                LeController.INSTANCE.stopScanLe();
                                BLEDeviceIoET.this.isback = true;
                                Log.e("BLET", "CONNECTED_JINGDIAN Finished");
                            }
                        });
                        return;
                    }
                    catch (Exception paramAnonymousConnState)
                    {
                        paramAnonymousConnState.printStackTrace();
                    }
                }

                public void scanLeResult(LeDeviceEntity paramAnonymousLeDeviceEntity, final ScanType paramAnonymousScanType)
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable()
                    {
                        public void run()
                        {
                            Log.e("BLET", "scanLeResult:type=" + paramAnonymousScanType);
                            Log.e("info", "scanLeResult:type=" + paramAnonymousScanType);
                        }
                    });
                }
            });
            if (LeController.INSTANCE.isBluetoothLeOpen()) {
                if (!LeController.INSTANCE.isConnected)
                {
                    Log.e("BLET", "open and startscan");
                    LeController.INSTANCE.startScanLe();
                }
            }
            for (;;)
            {
                return this.isback;
                Log.e("BLET", "start scan");
                LeController.INSTANCE.openBluetoothLe();
                LeController.INSTANCE.startScanLe();
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

    public boolean unbound()
            throws InterruptedException
    {
        return this.isBound;
    }

    private static enum sdkType
    {
        BRACELET,  PAY;
    }
}
