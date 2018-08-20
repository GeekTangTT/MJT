package cn.com.ukey;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.util.Log;
import com.ivt.bluetooth.ibridge.BluetoothIBridgeAdapter;
import com.ivt.bluetooth.ibridge.BluetoothIBridgeAdapter.DataReceiver;
import com.ivt.bluetooth.ibridge.BluetoothIBridgeAdapter.EventReceiver;
import com.ivt.bluetooth.ibridge.BluetoothIBridgeDevice;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.util.ByteArrayBuffer;

public class BlueToothDeviceIo
        extends AudioDeviceIo
        implements DeviceIoInterface, BluetoothIBridgeAdapter.EventReceiver, BluetoothIBridgeAdapter.DataReceiver
{
    private static String TAG = "BlueToothDeviceIo";
    private static String TAG_EVENT = "BlueToothDeviceIo.EventReceiver";
    private final int MSG_FOR_SCAN_DEVICE = 1;
    private int SCAN_PERIOD = 15000;
    private int SENDCMD_TIMEOUT = 5000;
    private final int STATE_CONNECTED = 2;
    private final int STATE_CONNECTING = 1;
    private final int STATE_IDLE = 0;
    private boolean bIsFirstResponse = true;
    private boolean bRecvResponseSuccess = false;
    private boolean bWriteFailed = false;
    private boolean isFundDevice = false;
    private boolean isScaning = false;
    private BluetoothIBridgeAdapter mAdapter = null;
    private ByteArrayBuffer mBufferForRecv = null;
    private IUKeyResponseCallback mCallback;
    private Context mContext;
    private int mDeviceState = 0;
    private ArrayList<BluetoothIBridgeDevice> mDevices = new ArrayList();
    private int mExpectedCmdLen = 0;
    private Handler mHandler = null;
    private HandlerThread mHandlerThread = new HandlerThread("Looper");
    private BluetoothIBridgeDevice mSelectedDevice = null;
    private String mTargetDeviceSn = "";
    private PowerManager.WakeLock mWakeLock;

    private static long CRCB(byte[] paramArrayOfByte, int paramInt)
    {
        long l1 = 65535L;
        int i = 0;
        int k;
        int j;
        for (;;)
        {
            if (i >= paramInt) {
                return (0xFFFFFFFFFFFFFFFF ^ l1) & 0xFFFF;
            }
            k = paramArrayOfByte[i];
            j = 0;
            if (j < 8) {
                break;
            }
            i += 1;
        }
        if (((k ^ l1) & 1L) != 0L) {}
        for (long l2 = 33800L;; l2 = 0L)
        {
            k = (byte)(k >> 1);
            l1 = l1 >> 1 ^ l2;
            j += 1;
            break;
        }
    }

    private void SendMessage(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
    {
        if (this.mHandler != null)
        {
            Message localMessage = this.mHandler.obtainMessage();
            localMessage.what = paramInt1;
            localMessage.arg1 = paramInt2;
            localMessage.arg2 = paramInt3;
            localMessage.obj = paramObject;
            localMessage.sendToTarget();
        }
    }

    private void acquireWakeLock(Context paramContext)
    {
        this.mWakeLock = ((PowerManager)paramContext.getSystemService("power")).newWakeLock(6, getClass().getCanonicalName());
        this.mWakeLock.acquire();
    }

    private boolean connectToBtDevice()
    {
        if (!this.mAdapter.connectDevice(this.mSelectedDevice))
        {
            this.mSelectedDevice = null;
            this.mChannelKey = null;
            this.mDeviceState = 0;
            Log.e(TAG, "Please Enable Bluetooth or InValid Bluetooth address");
            return false;
        }
        this.mDeviceState = 1;
        for (;;)
        {
            if (this.mDeviceState != 1)
            {
                if (this.mDeviceState != 2) {
                    break;
                }
                return true;
            }
            SystemClock.sleep(1L);
        }
        this.mChannelKey = null;
        this.mSelectedDevice = null;
        return false;
    }

    private boolean deviceExisted(BluetoothIBridgeDevice paramBluetoothIBridgeDevice)
    {
        if (paramBluetoothIBridgeDevice == null) {
            return false;
        }
        boolean bool1 = false;
        for (;;)
        {
            Iterator localIterator;
            synchronized (this.mDevices)
            {
                localIterator = this.mDevices.iterator();
                if (!localIterator.hasNext()) {
                    return bool1;
                }
            }
            BluetoothIBridgeDevice localBluetoothIBridgeDevice = (BluetoothIBridgeDevice)localIterator.next();
            if (localBluetoothIBridgeDevice != null)
            {
                boolean bool2 = localBluetoothIBridgeDevice.equals(paramBluetoothIBridgeDevice);
                if (bool2) {
                    bool1 = true;
                }
            }
        }
    }

    private BluetoothIBridgeDevice findDevice(String paramString)
    {
        Object localObject = null;
        synchronized (this.mDevices)
        {
            Iterator localIterator = this.mDevices.iterator();
            BluetoothIBridgeDevice localBluetoothIBridgeDevice;
            do
            {
                if (!localIterator.hasNext())
                {
                    paramString = (String)localObject;
                    return paramString;
                }
                localBluetoothIBridgeDevice = (BluetoothIBridgeDevice)localIterator.next();
            } while ((localBluetoothIBridgeDevice == null) || (localBluetoothIBridgeDevice.getDeviceName() == null) || (!localBluetoothIBridgeDevice.getDeviceName().equals(paramString)) || (!localBluetoothIBridgeDevice.getDeviceAddress().startsWith("00")));
            paramString = localBluetoothIBridgeDevice;
        }
    }

    private static byte[] makeCmdForBT(byte[] paramArrayOfByte)
    {
        ByteArrayBuffer localByteArrayBuffer = new ByteArrayBuffer(paramArrayOfByte.length + 4);
        localByteArrayBuffer.clear();
        localByteArrayBuffer.append((byte)(paramArrayOfByte.length + 3 & 0xFF));
        localByteArrayBuffer.append(paramArrayOfByte, 0, paramArrayOfByte.length);
        long l = CRCB(paramArrayOfByte, paramArrayOfByte.length);
        localByteArrayBuffer.append((byte)(int)(l >> 8 & 0xFF));
        localByteArrayBuffer.append((byte)(int)(l & 0xFF));
        return localByteArrayBuffer.toByteArray();
    }

    private static byte[] parseBTCmd(byte[] paramArrayOfByte, int paramInt)
    {
        if ((paramArrayOfByte == null) || (paramArrayOfByte.length < paramInt)) {
            return null;
        }
        if (Utility.getUnsignedByte(paramArrayOfByte[0]) * 256 + Utility.getUnsignedByte(paramArrayOfByte[1]) != paramInt) {
            return null;
        }
        if (paramInt < 5) {
            return null;
        }
        byte[] arrayOfByte = new byte[paramInt - 4];
        System.arraycopy(paramArrayOfByte, 2, arrayOfByte, 0, arrayOfByte.length);
        long l = CRCB(arrayOfByte, arrayOfByte.length);
        int i = Utility.getUnsignedByte(paramArrayOfByte[(paramInt - 2)]);
        paramInt = Utility.getUnsignedByte(paramArrayOfByte[(paramInt - 1)]);
        if ((i != (int)(l >> 8 & 0xFF)) || (paramInt != (int)(l & 0xFF)))
        {
            Log.e(TAG, "���������������������");
            return null;
        }
        Log.e(TAG, "���������������������������������: " + Utility.bytesToHexString(arrayOfByte));
        return arrayOfByte;
    }

    private void releaseWakeLock()
    {
        if ((this.mWakeLock != null) && (this.mWakeLock.isHeld()))
        {
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
    }

    public String GetDevices()
    {
        String str = "";
        Iterator localIterator = this.mDevices.iterator();
        for (;;)
        {
            if (!localIterator.hasNext()) {
                return str;
            }
            BluetoothIBridgeDevice localBluetoothIBridgeDevice = (BluetoothIBridgeDevice)localIterator.next();
            if (localBluetoothIBridgeDevice != null) {
                str = new StringBuilder(String.valueOf(str)).append(localBluetoothIBridgeDevice.getDeviceName()).toString() + "\n";
            }
        }
    }

    public void clear()
    {
        Log.d(TAG, "clear()");
        releaseWakeLock();
    }

    public int connect()
    {
        return 0;
    }

    public int connect(String paramString)
    {
        if ((this.mSelectedDevice != null) && (!this.mSelectedDevice.isConnected())) {
            new Handler(this.mContext.getMainLooper()).post(new Runnable()
            {
                public void run()
                {
                    if (!BlueToothDeviceIo.this.isScaning)
                    {
                        BlueToothDeviceIo.this.isScaning = true;
                        BlueToothDeviceIo.this.mAdapter.startDisovery();
                    }
                }
            });
        }
        SystemClock.sleep(100L);
        this.mSelectedDevice = findDevice("HDZBBT_A040000003");
        if (this.mSelectedDevice != null) {
            this.mAdapter.stopDiscovery();
        }
        for (;;)
        {
            if (this.mSelectedDevice != null) {
                break label94;
            }
            return -102;
            if (this.isScaning) {
                break;
            }
        }
        label94:
        this.mDeviceState = 1;
        if (!this.mSelectedDevice.isConnected()) {
            connectToBtDevice();
        }
        while (this.mDeviceState == 2)
        {
            Log.d(TAG, "������������");
            return 0;
            this.mDeviceState = 2;
        }
        Log.d(TAG, "������������");
        return -1;
    }

    public int disconnect()
    {
        Log.d(TAG, "disconnect()");
        if (this.mSelectedDevice != null)
        {
            this.mAdapter.disconnectDevice(this.mSelectedDevice);
            this.mSelectedDevice = null;
        }
        return 36864;
    }

    public double getBatteryVoltage()
    {
        Log.d(TAG, "getBatteryVoltage()");
        return 0.0D;
    }

    public int getCosType()
    {
        return this.mCosType;
    }

    public void handlerMessage(Context paramContext)
    {
        this.mHandler = new Handler(paramContext.getMainLooper())
        {
            public void handleMessage(Message paramAnonymousMessage)
            {
                switch (paramAnonymousMessage.what)
                {
                }
                for (;;)
                {
                    super.handleMessage(paramAnonymousMessage);
                    return;
                    if (!BlueToothDeviceIo.this.isScaning)
                    {
                        BlueToothDeviceIo.this.mAdapter.startDisovery();
                        BlueToothDeviceIo.this.isScaning = true;
                    }
                }
            }
        };
    }

    public void init(Context paramContext)
    {
        Log.d(TAG, "init()1");
        if (this.mAdapter == null)
        {
            this.mAdapter = new BluetoothIBridgeAdapter(paramContext);
            if (!this.mAdapter.isEnabled()) {
                this.mAdapter.setEnabled(true);
            }
            if (Build.VERSION.SDK_INT < 10) {
                break label197;
            }
            this.mAdapter.setLinkKeyNeedAuthenticated(false);
        }
        for (;;)
        {
            this.mAdapter.registerEventReceiver(this);
            this.mAdapter.registerDataReceiver(this);
            this.mAdapter.setConnectType("ivt.device.i482e");
            this.mCallback = null;
            this.mContext = paramContext;
            if (this.mHandler == null) {
                handlerMessage(this.mContext);
            }
            Log.d(TAG, "will mAdapter.startLeScan()");
            if ((!this.isScaning) && (this.mSelectedDevice == null))
            {
                this.mSelectedDevice = null;
                Log.d(TAG, "mAdapter.startLeScan()");
            }
            synchronized (this.mDevices)
            {
                this.mDevices.clear();
                this.mAdapter.startDisovery();
                this.isScaning = true;
                Log.d(TAG, "done mAdapter.startLeScan()");
                acquireWakeLock(paramContext);
                return;
                label197:
                this.mAdapter.setLinkKeyNeedAuthenticated(true);
            }
        }
    }

    public void init(Context paramContext, IUKeyResponseCallback arg2)
    {
        Log.d(TAG, "init()");
        if (this.mAdapter == null)
        {
            this.mAdapter = new BluetoothIBridgeAdapter(paramContext);
            if (!this.mAdapter.isEnabled()) {
                this.mAdapter.setEnabled(true);
            }
            if (Build.VERSION.SDK_INT < 10) {
                break label173;
            }
            this.mAdapter.setLinkKeyNeedAuthenticated(false);
        }
        for (;;)
        {
            this.mAdapter.registerEventReceiver(this);
            this.mAdapter.registerDataReceiver(this);
            this.mAdapter.setConnectType("ivt.device.i482e");
            this.mCallback = ???;
            this.mContext = paramContext;
            if (this.mHandler == null) {
                handlerMessage(this.mContext);
            }
            if ((!this.isScaning) && (this.mSelectedDevice == null))
            {
                this.mSelectedDevice = null;
                Log.d(TAG, "mAdapter.startLeScan()");
            }
            synchronized (this.mDevices)
            {
                this.mDevices.clear();
                SendMessage(1, 0, 0, null);
                acquireWakeLock(paramContext);
                return;
                label173:
                this.mAdapter.setLinkKeyNeedAuthenticated(true);
            }
        }
    }

    public boolean isKeyPresent()
    {
        Log.d(TAG, "isKeyPresent()");
        return true;
    }

    public void onDataReceived(BluetoothIBridgeDevice paramBluetoothIBridgeDevice, byte[] paramArrayOfByte, int paramInt)
    {
        if ((paramArrayOfByte == null) || (paramInt < 5)) {
            Log.e(TAG, "Empty received data");
        }
        do
        {
            do
            {
                return;
                Utility.bytesToHexString(paramArrayOfByte, paramInt);
                Log.d(TAG_EVENT, "onDataReceived(). data = " + Utility.bytesToHexString(paramArrayOfByte, paramInt));
                this.mDeviceState = 2;
                if (this.bIsFirstResponse)
                {
                    this.mExpectedCmdLen = (Utility.getUnsignedByte(paramArrayOfByte[0]) * 256 + Utility.getUnsignedByte(paramArrayOfByte[1]));
                    this.mBufferForRecv = new ByteArrayBuffer(this.mExpectedCmdLen);
                    this.bIsFirstResponse = false;
                }
                this.mBufferForRecv.append(paramArrayOfByte, 0, paramInt);
            } while (this.mBufferForRecv.length() < this.mExpectedCmdLen);
            paramBluetoothIBridgeDevice = parseBTCmd(this.mBufferForRecv.toByteArray(), this.mExpectedCmdLen);
        } while (paramBluetoothIBridgeDevice == null);
        this.strResponse = Utility.bytesToHexString(paramBluetoothIBridgeDevice);
        this.bRecvResponseSuccess = true;
    }

    public void onDeviceConnectFailed(BluetoothIBridgeDevice paramBluetoothIBridgeDevice, String paramString)
    {
        Log.d(TAG_EVENT, "onDeviceConnectFailed(). exceptionMsg = " + paramString);
        this.mDeviceState = 0;
    }

    public void onDeviceConnected(BluetoothIBridgeDevice paramBluetoothIBridgeDevice)
    {
        Log.d(TAG_EVENT, "onDeviceConnected(). [" + paramBluetoothIBridgeDevice.getDeviceName() + "][" + paramBluetoothIBridgeDevice.getDeviceAddress() + "]");
        SystemClock.sleep(500L);
        this.mDeviceState = 2;
    }

    public void onDeviceDisconnected(BluetoothIBridgeDevice arg1, String paramString)
    {
        Log.d(TAG_EVENT, "onDeviceDisconnected(). exceptionMsg = " + paramString);
        this.mDeviceState = 0;
        this.mSelectedDevice = null;
        this.mChannelKey = null;
        synchronized (this.mDevices)
        {
            this.mDevices.clear();
            this.bRecvResponseSuccess = true;
            this.bWriteFailed = true;
            return;
        }
    }

    public void onDeviceFound(BluetoothIBridgeDevice paramBluetoothIBridgeDevice)
    {
        if (!deviceExisted(paramBluetoothIBridgeDevice)) {}
        synchronized (this.mDevices)
        {
            Log.e(TAG_EVENT, "Find BT device => " + paramBluetoothIBridgeDevice.getDeviceName() + "[" + paramBluetoothIBridgeDevice.getDeviceAddress() + "]");
            this.mDevices.add(paramBluetoothIBridgeDevice);
            if (paramBluetoothIBridgeDevice.getDeviceName().contains("A040000003")) {
                this.isFundDevice = true;
            }
            Log.d(TAG_EVENT, "onDeviceFound(). [" + paramBluetoothIBridgeDevice.getDeviceName() + "][" + paramBluetoothIBridgeDevice.getDeviceAddress() + "]");
            return;
        }
    }

    public void onDiscoveryFinished()
    {
        Log.d(TAG_EVENT, "onDiscoveryFinished().");
        this.isScaning = false;
    }

    public void onWriteFailed(BluetoothIBridgeDevice paramBluetoothIBridgeDevice, String paramString)
    {
        Log.d(TAG_EVENT, "onWriteFailed(). exceptionMsg = " + paramString);
    }

    protected int sendCommand(String paramString, StringBuffer paramStringBuffer)
    {
        Utility.sendBroadcastForTest(this.mContext, "send: " + paramString);
        long l1 = SystemClock.uptimeMillis();
        int i;
        if ((this.mSelectedDevice != null) && (this.mAdapter != null) && (this.mSelectedDevice.isConnected()))
        {
            paramString = Utility.hexToBytes(paramString);
            if (paramString == null) {
                i = 65127;
            }
        }
        label281:
        do
        {
            return i;
            paramString = makeCmdForBT(paramString);
            Log.e(TAG, "���������������apdu = " + Utility.bytesToHexString(paramString));
            this.bRecvResponseSuccess = false;
            this.bWriteFailed = false;
            this.bIsFirstResponse = true;
            this.mAdapter.send(this.mSelectedDevice, paramString, paramString.length);
            i = 0;
            do
            {
                if (this.bRecvResponseSuccess)
                {
                    if (0 != 0) {
                        break label281;
                    }
                    paramString = getResponse();
                    MyLog.d(TAG, "recvResp = " + paramString);
                    long l2 = SystemClock.uptimeMillis();
                    Utility.sendBroadcastForTest(this.mContext, "recv: " + paramString + "  [" + (l2 - l1) + "ms]");
                    if ((!paramString.equals("9401")) || (this.mCallback == null)) {
                        break;
                    }
                    MyLog.e("AudioDeviceIo.sendCommand", "callback������������������");
                    this.mCallback.onLowBatteryVoltage();
                    return 65180;
                }
                SystemClock.uptimeMillis();
            } while (!this.bWriteFailed);
            return -1;
            paramStringBuffer.append(paramString);
            return 36864;
            MyLog.i(TAG, "ret = " + 0);
        } while (isKeyPresent());
        return -102;
    }

    public int sendCommand(byte[] paramArrayOfByte, ByteArrayBuffer paramByteArrayBuffer)
    {
        int i;
        if ((this.mSelectedDevice == null) || (!this.mSelectedDevice.isConnected()))
        {
            MyLog.e(TAG, "in sendCommand(byte[], ByteArrayBuffer), mSelectedDevice is not connected!");
            i = 65234;
        }
        int j;
        for (;;)
        {
            return i;
            StringBuffer localStringBuffer = new StringBuffer();
            i = 0;
            if (i >= paramArrayOfByte.length) {
                paramArrayOfByte = new StringBuffer();
            }
            try
            {
                j = sendCommand(localStringBuffer.toString(), paramArrayOfByte);
                i = j;
                if (j == 36864) {
                    if ((paramArrayOfByte.length() % 2 != 0) || (paramArrayOfByte.length() < 4))
                    {
                        return 65185;
                        localStringBuffer.append(Integer.toHexString(paramArrayOfByte[i] & 0xFF | 0xFF00).substring(6));
                        i += 1;
                    }
                }
            }
            catch (Exception localException1)
            {
                for (;;)
                {
                    localException1.printStackTrace();
                    j = 65223;
                }
                int m = paramArrayOfByte.length() - 4;
                int k = Integer.parseInt(paramArrayOfByte.substring(m, m + 2), 16);
                j = Integer.parseInt(paramArrayOfByte.substring(m + 2, m + 4), 16);
                i = k;
                if (k == 97)
                {
                    String str = "00C00000" + paramArrayOfByte.substring(m + 2, m + 4);
                    paramArrayOfByte.delete(0, paramArrayOfByte.length());
                    try
                    {
                        i = sendCommand(str, paramArrayOfByte);
                        if ((i != 36864) || (paramArrayOfByte.length() < 4)) {
                            return 65185;
                        }
                    }
                    catch (Exception localException2)
                    {
                        for (;;)
                        {
                            localException2.printStackTrace();
                            i = 65223;
                        }
                        j = paramArrayOfByte.length() - 4;
                        i = Integer.parseInt(paramArrayOfByte.substring(j, j + 2), 16);
                        j = Integer.parseInt(paramArrayOfByte.substring(j + 2, j + 4), 16);
                    }
                }
                j = i * 256 + j;
                paramByteArrayBuffer.clear();
                i = 0;
            }
        }
        for (;;)
        {
            if (i >= paramArrayOfByte.length() - 4)
            {
                i = j;
                if (j != 37889) {
                    break;
                }
                if (this.mCallback != null)
                {
                    MyLog.e("AudioDeviceIo.SendCommand", "callback������������������");
                    this.mCallback.onLowBatteryVoltage();
                }
                return 65180;
            }
            paramByteArrayBuffer.append(Integer.parseInt(paramArrayOfByte.substring(i, i + 2), 16));
            i += 2;
        }
    }

    public Response sendCommand(String paramString)
    {
        Log.d(TAG, "sendCommand(" + paramString + ")");
        Response localResponse = new Response();
        if (this.mSelectedDevice == null)
        {
            MyLog.e(TAG, "in sendCommand(), mSelectedDevice is null!");
            localResponse.setReturnCode(-102);
        }
        do
        {
            return localResponse;
            Object localObject2 = Utility.hexToBytes(paramString);
            ByteArrayBuffer localByteArrayBuffer1 = new ByteArrayBuffer(256);
            Object localObject1 = localObject2;
            ByteArrayBuffer localByteArrayBuffer2;
            if (this.mChannelKey != null)
            {
                MyLog.e("Channel", "���������������:" + paramString);
                Utility.sendBroadcastForTest(this.mContext, "\r\nchannel key: " + Utility.bytesToHexString(getChannelKeyPlain()));
                Utility.sendBroadcastForTest(this.mContext, "before channel enc: " + paramString);
                localByteArrayBuffer2 = new ByteArrayBuffer(24);
                paramString = getChannelKeyPlain();
                localByteArrayBuffer2.append(paramString, 0, 16);
                localByteArrayBuffer2.append(paramString, 0, 8);
                paramString = null;
            }
            try
            {
                localObject1 = Des3.des3EncodeECB(localByteArrayBuffer2.toByteArray(), (byte[])localObject2);
                paramString = (String)localObject1;
            }
            catch (Exception localException1)
            {
                for (;;)
                {
                    int j;
                    int i;
                    localException1.printStackTrace();
                }
            }
            localByteArrayBuffer2.clear();
            localObject1 = new ByteArrayBuffer(256);
            ((ByteArrayBuffer)localObject1).append(128);
            ((ByteArrayBuffer)localObject1).append(116);
            ((ByteArrayBuffer)localObject1).append(0);
            ((ByteArrayBuffer)localObject1).append(0);
            ((ByteArrayBuffer)localObject1).append(paramString.length);
            ((ByteArrayBuffer)localObject1).append(paramString, 0, paramString.length);
            localObject1 = ((ByteArrayBuffer)localObject1).toByteArray();
            j = sendCommand((byte[])localObject1, localByteArrayBuffer1);
            i = j;
            if (this.mChannelKey != null)
            {
                i = j;
                if (j == 36864)
                {
                    localObject2 = new ByteArrayBuffer(24);
                    paramString = getChannelKeyPlain();
                    ((ByteArrayBuffer)localObject2).append(paramString, 0, 16);
                    ((ByteArrayBuffer)localObject2).append(paramString, 0, 8);
                    paramString = null;
                }
            }
            try
            {
                localObject1 = Des3.des3DecodeECB(((ByteArrayBuffer)localObject2).toByteArray(), localByteArrayBuffer1.toByteArray());
                paramString = (String)localObject1;
            }
            catch (Exception localException2)
            {
                for (;;)
                {
                    localException2.printStackTrace();
                }
            }
            ((ByteArrayBuffer)localObject2).clear();
            i = Utility.getUnsignedByte(paramString[(paramString.length - 2)]) * 256 + Utility.getUnsignedByte(paramString[(paramString.length - 1)]);
            localByteArrayBuffer1.clear();
            localByteArrayBuffer1.append(paramString, 0, paramString.length - 2);
            MyLog.e("Channel", "���������������:" + Utility.bytesToHexString(paramString));
            Utility.sendBroadcastForTest(this.mContext, "after channel dec: " + Utility.bytesToHexString(paramString) + "\r\n");
            paramString = Utility.bytesToHexString(localByteArrayBuffer1.toByteArray());
            localResponse.setReturnCode(i);
            localResponse.setResponseApdu(paramString);
        } while ((i != 37889) || (this.mCallback == null));
        MyLog.e("AudioDeviceIo.SendCommand2", "callback������������������");
        this.mCallback.onLowBatteryVoltage();
        return localResponse;
    }

    public void setCallback(IUKeyResponseCallback paramIUKeyResponseCallback)
    {
        this.mCallback = paramIUKeyResponseCallback;
    }
}
