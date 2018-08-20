package com.olym.mjt.datastat;

import java.util.HashMap;

public class UploadData
{
    private int duration;
    private String error_text = "";
    private int event_module;
    private int event_type;
    private int op_code;
    private String peer_domain = "";
    private String peer_id = "";
    private int phone_type;
    private int ring_time;
    private String user_app_versio;
    private String user_dev_os;
    private String user_dev_type;
    private String user_domain;
    private String user_id;
    private int user_net;

    public int getDuration()
    {
        return this.duration;
    }

    public String getError_text()
    {
        return this.error_text;
    }

    public int getEvent_module()
    {
        return this.event_module;
    }

    public int getEvent_type()
    {
        return this.event_type;
    }

    public int getOp_code()
    {
        return this.op_code;
    }

    public String getPeer_domain()
    {
        return this.peer_domain;
    }

    public String getPeer_id()
    {
        return this.peer_id;
    }

    public int getPhone_type()
    {
        return this.phone_type;
    }

    public int getRing_time()
    {
        return this.ring_time;
    }

    public String getUser_app_versio()
    {
        return this.user_app_versio;
    }

    public String getUser_dev_os()
    {
        return this.user_dev_os;
    }

    public String getUser_dev_type()
    {
        return this.user_dev_type;
    }

    public String getUser_domain()
    {
        return this.user_domain;
    }

    public String getUser_id()
    {
        return this.user_id;
    }

    public int getUser_net()
    {
        return this.user_net;
    }

    public void setDuration(int paramInt)
    {
        this.duration = paramInt;
    }

    public void setError_text(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "";
        }
        this.error_text = str;
    }

    public void setEvent_module(int paramInt)
    {
        this.event_module = paramInt;
    }

    public void setEvent_type(int paramInt)
    {
        this.event_type = paramInt;
    }

    public void setOp_code(int paramInt)
    {
        this.op_code = paramInt;
    }

    public void setPeer_domain(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "";
        }
        this.peer_domain = str;
    }

    public void setPeer_id(String paramString)
    {
        this.peer_id = paramString;
    }

    public void setPhone_type(int paramInt)
    {
        this.phone_type = paramInt;
    }

    public void setRing_time(int paramInt)
    {
        this.ring_time = paramInt;
    }

    public void setUser_app_versio(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "";
        }
        this.user_app_versio = str;
    }

    public void setUser_dev_os(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "";
        }
        this.user_dev_os = str;
    }

    public void setUser_dev_type(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "";
        }
        this.user_dev_type = str;
    }

    public void setUser_domain(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "";
        }
        this.user_domain = str;
    }

    public void setUser_id(String paramString)
    {
        this.user_id = paramString;
    }

    public void setUser_net(int paramInt)
    {
        this.user_net = paramInt;
    }

    public HashMap<String, String> toMap()
    {
        HashMap localHashMap = new HashMap();
        localHashMap.put("event_type", this.event_type + "");
        localHashMap.put("event_module", this.event_module + "");
        localHashMap.put("user_id", this.user_id);
        localHashMap.put("user_domain", this.user_domain);
        localHashMap.put("user_net", this.user_net + "");
        localHashMap.put("user_dev_type", this.user_dev_type);
        localHashMap.put("user_dev_os", this.user_dev_os);
        localHashMap.put("user_app_version", this.user_app_versio);
        localHashMap.put("op_code", this.op_code + "");
        localHashMap.put("peer_id", this.peer_id);
        localHashMap.put("peer_domain", this.peer_domain);
        localHashMap.put("duration", this.duration + "");
        localHashMap.put("error_text", this.error_text);
        localHashMap.put("phone_type", this.phone_type + "");
        localHashMap.put("ring_time", this.ring_time + "");
        return localHashMap;
    }

    public String toString()
    {
        return "UploadData{event_type=" + this.event_type + ", event_module=" + this.event_module + ", user_id='" + this.user_id + '\'' + ", user_domain='" + this.user_domain + '\'' + ", user_net=" + this.user_net + ", user_dev_type='" + this.user_dev_type + '\'' + ", user_dev_os='" + this.user_dev_os + '\'' + ", user_app_versio='" + this.user_app_versio + '\'' + ", op_code=" + this.op_code + ", peer_id='" + this.peer_id + '\'' + ", peer_domain='" + this.peer_domain + '\'' + ", duration=" + this.duration + ", error_text='" + this.error_text + '\'' + ", phone_type=" + this.phone_type + ", ring_time=" + this.ring_time + '}';
    }
}
