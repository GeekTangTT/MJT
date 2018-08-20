package cn.com.ukey;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import com.bhd.aidlBleService.ApduMaker;
import com.bhd.aidlBleService.BleDevice;
import com.bhd.aidlBleService.BoundBleDevice;
import com.bhd.aidlBleService.DeviceBoundInfo;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class BLEDeviceIo
        extends AudioDeviceIo
{
    private static final int SERVERPORT = 6000;
    private static final String SERVER_IP = "127.0.0.1";
    private static final String TAG = "BLE";
    private int CONNECTED_INDEX = 0;
    public String DeviceName = "";
    private List<String> bleNameList = null;
    private List<Integer> bleNumList = null;
    private int connnectIndex = 0;
    private int connnectStat = 0;
    private String content = "";
    private HashMap<String, String> deviceAdressList = new HashMap();
    private HashMap<Integer, String> deviceList = new HashMap();
    private BufferedReader input = null;
    private Intent intent;
    private BleDevice mBleDevice = null;
    private String mConnectedDevicName = "";
    private EditText mEditText = null;
    private BleDevice mIUKeyDevice = null;
    private Response mResponse = new Response();
    private TextView mTextView = null;
    private String mconnectName = null;
    private Context myContext;
    private Handler myHandler = null;
    private PrintWriter output = null;
    private String receivMsg = null;
    private Socket socket;
    private boolean threadRunFlag = true;

    private String deviceAdress_enum()
            throws Exception
    {
        if (this.mBleDevice == null) {
            this.mBleDevice = new BleDevice(this.myContext);
        }
        this.deviceAdressList = this.mBleDevice.enumDeviceAdress();
        if (this.deviceAdressList == null) {
            return null;
        }
        String str = ApduMaker.HashStringMap2ApduString(this.deviceAdressList);
        this.mIUKeyDevice = this.mBleDevice;
        this.mBleDevice = null;
        return str;
    }

    private int device_connect(int paramInt)
            throws Exception
    {
        if (this.mIUKeyDevice == null) {
            Log.i("BLE", "call the connect_method, but mIUKeyDevice == null");
        }
        while (1 != this.mIUKeyDevice.connect(paramInt)) {
            return 0;
        }
        this.CONNECTED_INDEX = paramInt;
        return 1;
    }

    private int device_connect(String paramString)
            throws Exception
    {
        if (this.mIUKeyDevice == null) {
            Log.i("BLE", "call the connect_method, but mIUKeyDevice == null");
        }
        int i;
        do
        {
            return 0;
            i = this.mIUKeyDevice.connect(paramString);
        } while (i <= 0);
        this.CONNECTED_INDEX = i;
        return i;
    }

    private void device_disconnect()
            throws Exception
    {
        this.mIUKeyDevice.disconnect(this.CONNECTED_INDEX);
    }

    private String device_enum()
            throws Exception
    {
        if (this.mBleDevice == null) {
            this.mBleDevice = new BleDevice(this.myContext);
        }
        this.deviceList = this.mBleDevice.enumDevice();
        if (this.deviceList == null) {
            return null;
        }
        String str = ApduMaker.HashMap2ApduString(this.deviceList);
        this.mIUKeyDevice = this.mBleDevice;
        this.mBleDevice = null;
        return str;
    }

    private void device_send(String paramString)
            throws Exception
    {
        if (this.mIUKeyDevice == null)
        {
            Log.i("BLE", "call the sendCommand_method, but mIUKeyDevice == null");
            return;
        }
        paramString = this.mIUKeyDevice.sendCommand(paramString.getBytes());
        if (paramString.length == 0)
        {
            this.mResponse.setResponseApdu("");
            this.mResponse.setReturnCode(-1);
            return;
        }
        this.mResponse.setResponseApdu(ApduMaker.byte2HexStr(paramString, paramString.length - 2));
        int i = paramString[(paramString.length - 2)];
        int j = paramString[(paramString.length - 1)];
        this.mResponse.setReturnCode((0 + i << 8 & 0xFF00) + (j & 0xFF));
    }

    private int reSend(String paramString)
    {
        if (this.mIUKeyDevice == null)
        {
            Log.i("BLE", "call the resendCommand_method, but mIUKeyDevice == null");
            return -1;
        }
        this.mIUKeyDevice.disconnect(this.CONNECTED_INDEX);
        if (this.mIUKeyDevice.connect(this.CONNECTED_INDEX) != 1) {
            return -1;
        }
        paramString = this.mIUKeyDevice.sendCommand(paramString.getBytes());
        if (paramString.length == 0)
        {
            this.mResponse.setResponseApdu("");
            this.mResponse.setReturnCode(-1);
            return -1;
        }
        this.mResponse.setResponseApdu(ApduMaker.byte2HexStr(paramString, paramString.length - 2));
        int i = (0 + paramString[(paramString.length - 2)] << 8 & 0xFF00) + (paramString[(paramString.length - 1)] & 0xFF);
        this.mResponse.setReturnCode(i);
        return i;
    }

    public int boundDevice(String paramString, Context paramContext)
    {
        return new BoundBleDevice(paramContext).BoundDeviceAdress(paramString);
    }

    public void clear() {}

    public int connect()
    {
        try
        {
            int i = device_connect(1000);
            if (i != 1) {
                return -1;
            }
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 36864;
    }

    public int connect(String paramString)
    {
        Log.i("BLE", "IN connect_NAME : " + paramString);
        paramString = paramString.substring(0, paramString.lastIndexOf("="));
        Log.i("BLE", "connect_NAME : " + paramString);
        try
        {
            int i = device_connect(paramString);
            if (i == 0) {
                return -1;
            }
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
        }
        return 36864;
    }

    public int disconnect()
    {
        try
        {
            device_disconnect();
            return 0;
        }
        catch (Exception localException)
        {
            for (;;)
            {
                localException.printStackTrace();
            }
        }
    }

    public double getBatteryVoltage()
    {
        return 0.0D;
    }

    public String getBoundDeiceInfo(Context paramContext)
    {
        paramContext = new BoundBleDevice(paramContext);
        DeviceBoundInfo localDeviceBoundInfo = new DeviceBoundInfo();
        paramContext.GetBoundDeviceInfo(localDeviceBoundInfo);
        return localDeviceBoundInfo.GetDeviceAdres();
    }

    public String getConnectedDeviceName()
    {
        return (String)this.deviceList.get(Integer.valueOf(this.CONNECTED_INDEX));
    }

    public int getCosType()
    {
        return this.mCosType;
    }

    public String init(Context paramContext, String paramString)
    {
        String str = new String();
        this.myContext = paramContext;
        paramContext = str;
        try
        {
            this.DeviceName = deviceAdress_enum();
            paramString = str;
            paramContext = str;
            if (this.DeviceName == null) {
                paramString = "";
            }
            paramContext = paramString;
            paramString = this.DeviceName;
            paramContext = paramString;
            Log.i("BLE", "enum_device_NAME : " + paramString);
            return paramString;
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
        }
        return paramContext;
    }

    public void init(Context paramContext)
    {
        this.myContext = paramContext;
        try
        {
            this.DeviceName = device_enum();
            if (this.DeviceName == null) {
                return;
            }
            Log.i("BLE", "enum_device : " + this.DeviceName.toString());
            paramContext = ApduMaker.ApduString2ListString(this.DeviceName);
            this.bleNameList = ApduMaker.getDeviceNameListFromListString(paramContext);
            this.bleNumList = ApduMaker.getDeviceHandleListFromListString(paramContext);
            Log.i("BLE", "enum_device : " + this.bleNameList.toString() + this.bleNumList.toString());
            return;
        }
        catch (Exception paramContext)
        {
            paramContext.printStackTrace();
        }
    }

    public void init(Context paramContext, IUKeyResponseCallback paramIUKeyResponseCallback)
    {
        this.myContext = paramContext;
        try
        {
            this.DeviceName = device_enum();
            if (this.DeviceName == null) {
                return;
            }
            paramContext = ApduMaker.ApduString2ListString(this.DeviceName);
            this.bleNameList = ApduMaker.getDeviceNameListFromListString(paramContext);
            this.bleNumList = ApduMaker.getDeviceHandleListFromListString(paramContext);
            Log.i("BLE", "enum_device : " + this.bleNameList.toString() + this.bleNumList.toString());
            return;
        }
        catch (Exception paramContext)
        {
            paramContext.printStackTrace();
        }
    }

    public boolean isKeyPresent()
    {
        return true;
    }

    public Response sendCommand(String paramString)
    {
        try
        {
            device_send(paramString);
            return this.mResponse;
        }
        catch (Exception paramString)
        {
            for (;;)
            {
                paramString.printStackTrace();
            }
        }
    }

    public void setCallback(IUKeyResponseCallback paramIUKeyResponseCallback) {}

    public boolean setDeviceAddress(String paramString)
    {
        return this.mBleDevice.SetDeviceAddress(paramString);
    }

    public int unBoundDevice(Context paramContext)
    {
        return new BoundBleDevice(paramContext).UNBoundDeviceAdress();
    }
}

