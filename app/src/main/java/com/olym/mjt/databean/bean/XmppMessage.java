package com.olym.mjt.databean.bean;

import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.field.DatabaseField;

public abstract class XmppMessage
{
    public static final int NEW_MEMBER = 907;
    public static final int TYPE_201 = 201;
    public static final int TYPE_202 = 202;
    public static final int TYPE_203 = 203;
    public static final int TYPE_204 = 204;
    public static final int TYPE_205 = 205;
    public static final int TYPE_206 = 206;
    public static final int TYPE_207 = 207;
    public static final int TYPE_208 = 208;
    public static final int TYPE_209 = 209;
    public static final int TYPE_210 = 210;
    public static final int TYPE_211 = 211;
    public static final int TYPE_212 = 212;
    public static final int TYPE_299 = 299;
    public static final int TYPE_300 = 300;
    public static final int TYPE_301 = 301;
    public static final int TYPE_302 = 302;
    public static final int TYPE_303 = 303;
    public static final int TYPE_304 = 304;
    public static final int TYPE_305 = 305;
    public static final int TYPE_306 = 306;
    public static final int TYPE_307 = 307;
    public static final int TYPE_308 = 308;
    public static final int TYPE_398 = 398;
    public static final int TYPE_399 = 399;
    public static final int TYPE_600 = 600;
    public static final int TYPE_601 = 601;
    public static final int TYPE_602 = 602;
    public static final int TYPE_603 = 603;
    public static final int TYPE_800 = 800;
    public static final int TYPE_801 = 801;
    public static final int TYPE_802 = 802;
    public static final int TYPE_900 = 900;
    public static final int TYPE_901 = 901;
    public static final int TYPE_BLACK = 507;
    public static final int TYPE_CALL_PHONE = 801;
    public static final int TYPE_CARD = 8;
    public static final int TYPE_CHANGE_NICK_NAME = 901;
    public static final int TYPE_CHANGE_ROOM_NAME = 902;
    public static final int TYPE_DELALL = 505;
    public static final int TYPE_DELETE = 11;
    public static final int TYPE_DELETE_MEMBER = 904;
    public static final int TYPE_DELETE_ROOM = 903;
    public static final int TYPE_DELSEE = 504;
    public static final int TYPE_ENTERING = 400;
    public static final int TYPE_FEEDBACK = 502;
    public static final int TYPE_FILE = 9;
    public static final int TYPE_FIRE_CARD = 16;
    public static final int TYPE_FIRE_FILE = 17;
    public static final int TYPE_FIRE_GIF = 18;
    public static final int TYPE_FIRE_IMAGE = 13;
    public static final int TYPE_FIRE_LOCATION = 19;
    public static final int TYPE_FIRE_TEXT = 12;
    public static final int TYPE_FIRE_VIDEO = 15;
    public static final int TYPE_FIRE_VOICE = 14;
    public static final int TYPE_FRIEND = 508;
    public static final int TYPE_GAG = 906;
    public static final int TYPE_GIF = 5;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_LOCATION = 4;
    public static final int TYPE_MSG_DESTORY = 910;
    public static final int TYPE_MSG_REVOKE = 802;
    public static final int TYPE_NEWSEE = 503;
    public static final int TYPE_NEW_NOTICE = 905;
    public static final int TYPE_OPEN_LOUNDSPEAKER = 802;
    public static final int TYPE_OPEN_RECORD = 803;
    public static final int TYPE_PASS = 501;
    public static final int TYPE_READ = 26;
    public static final int TYPE_RECOMMEND = 506;
    public static final int TYPE_REFUSED = 509;
    public static final int TYPE_SAYHELLO = 500;
    public static final int TYPE_SENDED = 27;
    public static final int TYPE_SIP_AUDIO = 7;
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_TIP = 10;
    public static final int TYPE_UPLOADFILE = 909;
    public static final int TYPE_USER_DISABLE = 911;
    public static final int TYPE_VIDEO = 6;
    public static final int TYPE_VOICE = 3;
    @DatabaseField
    protected boolean isMySend = true;
    @DatabaseField(canBeNull=false)
    protected String packetId;
    @DatabaseField(canBeNull=false)
    protected int timeSend;
    @DatabaseField(canBeNull=false)
    protected int type;

    protected boolean getBooleanValueFromJSONObject(JSONObject paramJSONObject, String paramString)
    {
        try
        {
            boolean bool = paramJSONObject.getBooleanValue(paramString);
            return bool;
        }
        catch (Exception paramJSONObject)
        {
            paramJSONObject.printStackTrace();
        }
        return false;
    }

    protected int getIntValueFromJSONObject(JSONObject paramJSONObject, String paramString)
    {
        try
        {
            int i = paramJSONObject.getIntValue(paramString);
            return i;
        }
        catch (Exception paramJSONObject)
        {
            paramJSONObject.printStackTrace();
        }
        return 0;
    }

    public String getPacketId()
    {
        return this.packetId;
    }

    protected String getStringValueFromJSONObject(JSONObject paramJSONObject, String paramString)
    {
        String str = "";
        try
        {
            paramJSONObject = paramJSONObject.getString(paramString);
            paramString = paramJSONObject;
            if (paramJSONObject == null) {
                paramString = "";
            }
            return paramString;
        }
        catch (Exception paramJSONObject)
        {
            for (;;)
            {
                paramJSONObject.printStackTrace();
                paramJSONObject = str;
            }
        }
    }

    public int getTimeSend()
    {
        return this.timeSend;
    }

    public int getType()
    {
        return this.type;
    }

    public boolean isMySend()
    {
        return this.isMySend;
    }

    public void setMySend(boolean paramBoolean)
    {
        this.isMySend = paramBoolean;
    }

    public void setPacketId(String paramString)
    {
        this.packetId = paramString;
    }

    public void setTimeSend(int paramInt)
    {
        this.timeSend = paramInt;
    }

    public void setType(int paramInt)
    {
        this.type = paramInt;
    }
}

