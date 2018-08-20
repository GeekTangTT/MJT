package com.olym.mjt.databean.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.olym.mjt.database.dao.ChatMessageDaoImpl;
import java.io.File;

@DatabaseTable(daoClass=ChatMessageDaoImpl.class)
public class ChatMessage
        extends XmppMessage
        implements Parcelable
{
    public static final Parcelable.Creator<ChatMessage> CREATOR = new ChatMessage.1();
    public static final int MESSAGE_SEND_FAILED = 2;
    public static final int MESSAGE_SEND_ING = 0;
    public static final int MESSAGE_SEND_SUCCESS = 1;
    @DatabaseField(generatedId=true)
    private int _id;
    @DatabaseField
    private String content;
    @DatabaseField
    private String filePath;
    @DatabaseField
    private int fileSize;
    @DatabaseField
    private int fire;
    @DatabaseField
    private String fromUserId;
    @DatabaseField
    private String fromUserName;
    @DatabaseField
    private String ibcdomain;
    @DatabaseField
    private String ibcversion;
    @DatabaseField
    private String imageHeight;
    @DatabaseField
    private String imageWidth;
    @DatabaseField
    private int isDecrypted;
    @DatabaseField
    private int isDownload;
    @DatabaseField
    private boolean isEncrypt;
    @DatabaseField
    private int isOnDownloading;
    @DatabaseField
    private boolean isRead;
    @DatabaseField
    private int isSend;
    @DatabaseField
    private boolean isUpload;
    @DatabaseField
    private String location_x;
    @DatabaseField
    private String location_y;
    @DatabaseField
    private int messageState;
    @DatabaseField
    private int myneedfire;
    @DatabaseField
    private int needfire;
    @DatabaseField
    private String objectId;
    @DatabaseField
    private int sipDuration;
    @DatabaseField
    private int sipStatus;
    @DatabaseField
    private String thumbnail;
    @DatabaseField
    private int timeLen;
    @DatabaseField
    private int timeReceive;
    @DatabaseField
    private String toUserKey;

    public ChatMessage() {}

    public ChatMessage(String paramString)
    {
        parserJsonData(paramString);
    }

    private void parserJsonData(String paramString)
    {
        try
        {
            paramString = JSON.parseObject(paramString);
            this.type = getIntValueFromJSONObject(paramString, "type");
            this.timeSend = getIntValueFromJSONObject(paramString, "timeSend");
            this.fromUserId = getStringValueFromJSONObject(paramString, "fromUserId");
            this.fromUserName = getStringValueFromJSONObject(paramString, "fromUserName");
            this.content = getStringValueFromJSONObject(paramString, "content");
            this.isSend = getIntValueFromJSONObject(paramString, "isSend");
            this.location_x = getStringValueFromJSONObject(paramString, "location_x");
            this.location_y = getStringValueFromJSONObject(paramString, "location_y");
            this.imageHeight = getStringValueFromJSONObject(paramString, "imageHeight");
            this.imageWidth = getStringValueFromJSONObject(paramString, "imageWidth");
            this.fileSize = getIntValueFromJSONObject(paramString, "fileSize");
            this.timeLen = getIntValueFromJSONObject(paramString, "timeLen");
            this.filePath = getStringValueFromJSONObject(paramString, "filePath");
            this.objectId = getStringValueFromJSONObject(paramString, "objectId");
            this.isEncrypt = getBooleanValueFromJSONObject(paramString, "isEncrypt");
            this.isDecrypted = getIntValueFromJSONObject(paramString, "isDecrypted");
            this.ibcdomain = getStringValueFromJSONObject(paramString, "ibcdomain");
            this.ibcversion = getStringValueFromJSONObject(paramString, "ibcversion");
            this.toUserKey = getStringValueFromJSONObject(paramString, "toUserKey");
            this.fire = getIntValueFromJSONObject(paramString, "fire");
            this.needfire = getIntValueFromJSONObject(paramString, "needfire");
            this.myneedfire = getIntValueFromJSONObject(paramString, "myneedfire");
            this._id = getIntValueFromJSONObject(paramString, "_id");
            this.thumbnail = getStringValueFromJSONObject(paramString, "thumbnail");
            this.isRead = false;
            this.isMySend = false;
            this.isOnDownloading = 0;
            this.isDownload = 0;
            return;
        }
        catch (Exception paramString)
        {
            paramString.printStackTrace();
        }
    }

    public ChatMessage clone(boolean paramBoolean)
    {
        return new ChatMessage(toJsonString(paramBoolean));
    }

    public int describeContents()
    {
        return 0;
    }

    public String getContent()
    {
        return this.content;
    }

    public String getFilePath()
    {
        return this.filePath;
    }

    public int getFileSize()
    {
        return this.fileSize;
    }

    public int getFire()
    {
        return this.fire;
    }

    public String getFromUserId()
    {
        return this.fromUserId;
    }

    public String getFromUserName()
    {
        return this.fromUserName;
    }

    public String getIbcdomain()
    {
        return this.ibcdomain;
    }

    public String getIbcversion()
    {
        return this.ibcversion;
    }

    public String getImageHeight()
    {
        return this.imageHeight;
    }

    public String getImageWidth()
    {
        return this.imageWidth;
    }

    public int getIsDecrypted()
    {
        return this.isDecrypted;
    }

    public int getIsDownload()
    {
        return this.isDownload;
    }

    public int getIsOnDownloading()
    {
        return this.isOnDownloading;
    }

    public int getIsSend()
    {
        return this.isSend;
    }

    public String getLocation_x()
    {
        return this.location_x;
    }

    public String getLocation_y()
    {
        return this.location_y;
    }

    public int getMessageState()
    {
        return this.messageState;
    }

    public int getMyneedfire()
    {
        return this.myneedfire;
    }

    public int getNeedfire()
    {
        return this.needfire;
    }

    public String getObjectId()
    {
        return this.objectId;
    }

    public String getThumbnail()
    {
        return this.thumbnail;
    }

    public int getTimeLen()
    {
        return this.timeLen;
    }

    public int getTimeReceive()
    {
        return this.timeReceive;
    }

    public String getToUserKey()
    {
        return this.toUserKey;
    }

    public int get_id()
    {
        return this._id;
    }

    public boolean isEncrypt()
    {
        return this.isEncrypt;
    }

    public boolean isRead()
    {
        return this.isRead;
    }

    public boolean isUpload()
    {
        return this.isUpload;
    }

    public void setContent(String paramString)
    {
        this.content = paramString;
    }

    public void setFilePath(String paramString)
    {
        this.filePath = paramString;
    }

    public void setFileSize(int paramInt)
    {
        this.fileSize = paramInt;
    }

    public void setFire(int paramInt)
    {
        this.fire = paramInt;
    }

    public void setFromUserId(String paramString)
    {
        this.fromUserId = paramString;
    }

    public void setFromUserName(String paramString)
    {
        this.fromUserName = paramString;
    }

    public void setIbcdomain(String paramString)
    {
        this.ibcdomain = paramString;
    }

    public void setIbcversion(String paramString)
    {
        this.ibcversion = paramString;
    }

    public void setImageHeight(String paramString)
    {
        this.imageHeight = paramString;
    }

    public void setImageWidth(String paramString)
    {
        this.imageWidth = paramString;
    }

    public void setIsDecrypted(int paramInt)
    {
        this.isDecrypted = paramInt;
    }

    public void setIsDownload(int paramInt)
    {
        this.isDownload = paramInt;
    }

    public void setIsEncrypt(boolean paramBoolean)
    {
        this.isEncrypt = paramBoolean;
    }

    public void setIsOnDownloading(int paramInt)
    {
        this.isOnDownloading = paramInt;
    }

    public void setIsSend(int paramInt)
    {
        this.isSend = paramInt;
    }

    public void setLocation_x(String paramString)
    {
        this.location_x = paramString;
    }

    public void setLocation_y(String paramString)
    {
        this.location_y = paramString;
    }

    public void setMessageState(int paramInt)
    {
        this.messageState = paramInt;
    }

    public void setMyneedfire(int paramInt)
    {
        this.myneedfire = paramInt;
    }

    public void setNeedfire(int paramInt)
    {
        this.needfire = paramInt;
    }

    public void setObjectId(String paramString)
    {
        this.objectId = paramString;
    }

    public void setRead(boolean paramBoolean)
    {
        this.isRead = paramBoolean;
    }

    public void setThumbnail(String paramString)
    {
        this.thumbnail = paramString;
    }

    public void setTimeLen(int paramInt)
    {
        this.timeLen = paramInt;
    }

    public void setTimeReceive(int paramInt)
    {
        this.timeReceive = paramInt;
    }

    public void setToUserKey(String paramString)
    {
        this.toUserKey = paramString;
    }

    public void setUpload(boolean paramBoolean)
    {
        this.isUpload = paramBoolean;
    }

    public void set_id(int paramInt)
    {
        this._id = paramInt;
    }

    public String toJsonString(boolean paramBoolean)
    {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("_id", Integer.valueOf(this._id));
        localJSONObject.put("type", Integer.valueOf(this.type));
        localJSONObject.put("messageId", this.packetId);
        localJSONObject.put("timeSend", Integer.valueOf(this.timeSend));
        localJSONObject.put("fire", Integer.valueOf(this.fire));
        if (!TextUtils.isEmpty(this.thumbnail)) {
            localJSONObject.put("thumbnail", this.thumbnail);
        }
        if (paramBoolean) {
            localJSONObject.put("fromUserId", this.fromUserId);
        }
        if (!TextUtils.isEmpty(this.fromUserName)) {
            localJSONObject.put("fromUserName", this.fromUserName);
        }
        if (!TextUtils.isEmpty(this.ibcversion)) {
            localJSONObject.put("ibcversion", this.ibcversion);
        }
        if (!TextUtils.isEmpty(this.ibcdomain)) {
            localJSONObject.put("ibcdomain", this.ibcdomain);
        }
        if (!TextUtils.isEmpty(this.content)) {
            localJSONObject.put("content", this.content);
        }
        if (!TextUtils.isEmpty(this.imageWidth)) {
            localJSONObject.put("imageWidth", this.imageWidth);
        }
        if (!TextUtils.isEmpty(this.imageHeight)) {
            localJSONObject.put("imageHeight", this.imageHeight);
        }
        if (!TextUtils.isEmpty(this.objectId)) {
            localJSONObject.put("objectId", this.objectId);
        }
        if (!TextUtils.isEmpty(this.filePath)) {
            localJSONObject.put("filePath", new File(this.filePath).getName());
        }
        if (this.timeLen > 0) {
            localJSONObject.put("timeLen", Integer.valueOf(this.timeLen));
        }
        return localJSONObject.toString();
    }

    public String toString()
    {
        return "ChatMessage{fromUserId='" + this.fromUserId + '\'' + ", toUserKey='" + this.toUserKey + '\'' + ", fromUserName='" + this.fromUserName + '\'' + ", content='" + this.content + '\'' + ", location_x='" + this.location_x + '\'' + ", location_y='" + this.location_y + '\'' + ", fileSize=" + this.fileSize + ", timeLen=" + this.timeLen + ", _id=" + this._id + ", timeReceive=" + this.timeReceive + ", filePath='" + this.filePath + '\'' + ", isUpload=" + this.isUpload + ", messageState=" + this.messageState + ", isRead=" + this.isRead + ", isEncrypt=" + this.isEncrypt + ", isDownload=" + this.isDownload + ", isOnDownloading=" + this.isOnDownloading + ", sipStatus=" + this.sipStatus + ", sipDuration=" + this.sipDuration + ", objectId='" + this.objectId + '\'' + ", imageHeight='" + this.imageHeight + '\'' + ", imageWidth='" + this.imageWidth + '\'' + ", isSend=" + this.isSend + ", ibcdomain='" + this.ibcdomain + '\'' + ", ibcversion='" + this.ibcversion + '\'' + '}';
    }

    public boolean validate()
    {
        return (this.type != 0) && (!TextUtils.isEmpty(this.fromUserId));
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeInt(this._id);
        paramParcel.writeString(this.content);
        paramParcel.writeString(this.filePath);
        paramParcel.writeInt(this.fileSize);
        paramParcel.writeString(this.fromUserId);
        paramParcel.writeString(this.toUserKey);
        paramParcel.writeString(this.fromUserName);
        paramParcel.writeString(this.location_x);
        paramParcel.writeString(this.location_y);
        paramParcel.writeString(this.imageHeight);
        paramParcel.writeString(this.imageWidth);
        paramParcel.writeInt(this.messageState);
        paramParcel.writeString(this.objectId);
        paramParcel.writeString(this.packetId);
        paramParcel.writeInt(this.sipDuration);
        paramParcel.writeInt(this.sipStatus);
        paramParcel.writeInt(this.timeLen);
        paramParcel.writeInt(this.timeReceive);
        paramParcel.writeInt(this.timeSend);
        paramParcel.writeInt(this.type);
        paramParcel.writeInt(this.isDownload);
        paramParcel.writeInt(this.isOnDownloading);
        paramParcel.writeString(this.ibcdomain);
        paramParcel.writeString(this.ibcversion);
        paramParcel.writeInt(this.fire);
        paramParcel.writeInt(this.needfire);
        paramParcel.writeInt(this.myneedfire);
        paramParcel.writeString(this.thumbnail);
    }
}

