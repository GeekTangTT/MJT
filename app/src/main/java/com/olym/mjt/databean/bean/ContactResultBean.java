package com.olym.mjt.databean.bean;

import java.util.List;

public class ContactResultBean
{
    private String token;
    private List<User_list> user_list;
    private int user_type;

    public String getToken()
    {
        return this.token;
    }

    public List<User_list> getUser_list()
    {
        return this.user_list;
    }

    public int getUser_type()
    {
        return this.user_type;
    }

    public void setToken(String paramString)
    {
        this.token = paramString;
    }

    public void setUser_list(List<User_list> paramList)
    {
        this.user_list = paramList;
    }

    public void setUser_type(int paramInt)
    {
        this.user_type = paramInt;
    }

    public String toString()
    {
        return "ContactResultBean{user_list=" + this.user_list + ", token='" + this.token + '\'' + ", user_type=" + this.user_type + '}';
    }

    public static class User_list
    {
        private int blacklist;
        private String domain;
        private String status;
        private String tigase_user_nickname;
        private String tigase_user_remarkname;
        private String tigase_user_status;
        private String user;
        private String userId;
        private String version;

        public int getBlacklist()
        {
            return this.blacklist;
        }

        public String getDomain()
        {
            return this.domain;
        }

        public String getStatus()
        {
            return this.status;
        }

        public String getTigase_user_nickname()
        {
            return this.tigase_user_nickname;
        }

        public String getTigase_user_remarkname()
        {
            return this.tigase_user_remarkname;
        }

        public String getTigase_user_status()
        {
            return this.tigase_user_status;
        }

        public String getUser()
        {
            return this.user;
        }

        public String getUserId()
        {
            return this.userId;
        }

        public String getVersion()
        {
            return this.version;
        }

        public void setBlacklist(int paramInt)
        {
            this.blacklist = paramInt;
        }

        public void setDomain(String paramString)
        {
            this.domain = paramString;
        }

        public void setStatus(String paramString)
        {
            this.status = paramString;
        }

        public void setTigase_user_nickname(String paramString)
        {
            this.tigase_user_nickname = paramString;
        }

        public void setTigase_user_remarkname(String paramString)
        {
            this.tigase_user_remarkname = paramString;
        }

        public void setTigase_user_status(String paramString)
        {
            this.tigase_user_status = paramString;
        }

        public void setUser(String paramString)
        {
            this.user = paramString;
        }

        public void setUserId(String paramString)
        {
            this.userId = paramString;
        }

        public void setVersion(String paramString)
        {
            this.version = paramString;
        }

        public String toString()
        {
            return "User_list{user='" + this.user + '\'' + ", status='" + this.status + '\'' + ", userId='" + this.userId + '\'' + ", tigase_user_status='" + this.tigase_user_status + '\'' + ", tigase_user_nickname='" + this.tigase_user_nickname + '\'' + ", tigase_user_remarkname='" + this.tigase_user_remarkname + '\'' + ", domain='" + this.domain + '\'' + ", version='" + this.version + '\'' + '}';
        }
    }
}
