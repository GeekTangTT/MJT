package com.olym.mjt.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.olym.mjt.database.DBManager;
import com.olym.mjt.database.SQLiteHelper;
import com.olym.mjt.databean.bean.SearchBean;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import java.util.ArrayList;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

public class SearchDao
{
    private static SearchDao instance = null;
    private SQLiteDatabase db = DBManager.getInstance().getDbHelper().getReadableDatabase(AppSpUtil.getInstanse().getDBPassword());

    public static final SearchDao getInstance()
    {
        if (instance == null) {}
        try
        {
            if (instance == null) {
                instance = new SearchDao();
            }
            return instance;
        }
        finally {}
    }

    public boolean deleteAllQueryInfo()
    {
        return this.db.delete("friend_fts", null, null) > 0;
    }

    public void deleteMuchMsg(String paramString1, String paramString2)
    {
        this.db.delete("friend_fts", "userId = ? and packetId = ?", new String[] { paramString1, paramString2 });
    }

    public void deleteSingleMsg(String paramString1, String paramString2, String paramString3)
    {
        this.db.delete("friend_fts", "userId = ? and ibcdomain = ? and packetId = ?", new String[] { paramString1, paramString2, paramString3 });
    }

    public void deleteTableMsg(String paramString)
    {
        this.db.delete("friend_fts", "userId = ?", new String[] { paramString });
    }

    public void insertQueryInfo(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt3, String paramString7)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("packetId", paramString1);
        localContentValues.put("roomFlag", Integer.valueOf(paramInt2));
        localContentValues.put("userId", paramString2);
        localContentValues.put("telephone", paramString3);
        localContentValues.put("content", paramString4);
        localContentValues.put("nickName", paramString5);
        localContentValues.put("ibcdomain", paramString6);
        localContentValues.put("timesend", Integer.valueOf(paramInt3));
        localContentValues.put("indexNum", Integer.valueOf(paramInt1));
        localContentValues.put("filepath", paramString7);
        this.db.insert("friend_fts", null, localContentValues);
    }

    public List<SearchBean> searchFilepathOnLibrary(String paramString)
    {
        ArrayList localArrayList = new ArrayList();
        localArrayList.clear();
        if (paramString.length() == 0) {
            this.db.rawQuery("SELECT * FROM friend_fts", null);
        }
        do
        {
            return localArrayList;
            paramString = this.db.rawQuery("SELECT count(friend_fts.userId) AS messagecounts,* FROM friend_fts WHERE friend_fts.filepath MATCH ? GROUP BY friend_fts.roomFlag,friend_fts.userId ORDER BY friend_fts.roomFlag,friend_fts.timesend DESC LIMIT 3", new String[] { paramString + "*" });
        } while (paramString == null);
        if (paramString.getCount() > 0) {
            while (paramString.moveToNext())
            {
                SearchBean localSearchBean = new SearchBean();
                localSearchBean.setCount(paramString.getString(paramString.getColumnIndex("messagecounts")));
                localSearchBean.setPacketId(paramString.getString(paramString.getColumnIndex("packetId")));
                localSearchBean.setRoomFlag(paramString.getInt(paramString.getColumnIndex("roomFlag")));
                localSearchBean.setUserId(paramString.getString(paramString.getColumnIndex("userId")));
                localSearchBean.setToTelephone(paramString.getString(paramString.getColumnIndex("telephone")));
                localSearchBean.setContent(paramString.getString(paramString.getColumnIndex("content")));
                localSearchBean.setNickName(paramString.getString(paramString.getColumnIndex("nickName")));
                localSearchBean.setIbcdomain(paramString.getString(paramString.getColumnIndex("ibcdomain")));
                localSearchBean.setTimesend(paramString.getInt(paramString.getColumnIndex("timesend")));
                localSearchBean.setIndexNum(4);
                localSearchBean.setFilepath(paramString.getString(paramString.getColumnIndex("filepath")));
                localArrayList.add(localSearchBean);
            }
        }
        paramString.close();
        return localArrayList;
    }

    public List<SearchBean> searchFilepathOnLibraryWithoutLimit(String paramString)
    {
        ArrayList localArrayList = new ArrayList();
        localArrayList.clear();
        if (paramString.length() == 0) {
            this.db.rawQuery("SELECT * FROM friend_fts", null);
        }
        do
        {
            return localArrayList;
            paramString = this.db.rawQuery("SELECT count(friend_fts.userId) AS messagecounts,* FROM friend_fts WHERE friend_fts.filepath MATCH ? GROUP BY friend_fts.roomFlag,friend_fts.userId ORDER BY friend_fts.roomFlag,friend_fts.timesend DESC", new String[] { paramString + "*" });
        } while (paramString == null);
        if (paramString.getCount() > 0) {
            while (paramString.moveToNext())
            {
                SearchBean localSearchBean = new SearchBean();
                localSearchBean.setCount(paramString.getString(paramString.getColumnIndex("messagecounts")));
                localSearchBean.setPacketId(paramString.getString(paramString.getColumnIndex("packetId")));
                localSearchBean.setRoomFlag(paramString.getInt(paramString.getColumnIndex("roomFlag")));
                localSearchBean.setUserId(paramString.getString(paramString.getColumnIndex("userId")));
                localSearchBean.setToTelephone(paramString.getString(paramString.getColumnIndex("telephone")));
                localSearchBean.setContent(paramString.getString(paramString.getColumnIndex("content")));
                localSearchBean.setNickName(paramString.getString(paramString.getColumnIndex("nickName")));
                localSearchBean.setIbcdomain(paramString.getString(paramString.getColumnIndex("ibcdomain")));
                localSearchBean.setTimesend(paramString.getInt(paramString.getColumnIndex("timesend")));
                localSearchBean.setIndexNum(4);
                localSearchBean.setFilepath(paramString.getString(paramString.getColumnIndex("filepath")));
                localArrayList.add(localSearchBean);
            }
        }
        paramString.close();
        return localArrayList;
    }

    public List<SearchBean> searchOnLibrary(String paramString)
    {
        ArrayList localArrayList = new ArrayList();
        localArrayList.clear();
        if (paramString.length() == 0) {
            this.db.rawQuery("SELECT * FROM friend_fts", null);
        }
        do
        {
            return localArrayList;
            paramString = this.db.rawQuery("SELECT count(friend_fts.userId) AS messagecounts,* FROM friend_fts WHERE friend_fts.content MATCH ? GROUP BY friend_fts.roomFlag,friend_fts.userId ORDER BY friend_fts.roomFlag,friend_fts.timesend DESC LIMIT 3", new String[] { paramString + "*" });
        } while (paramString == null);
        if (paramString.getCount() > 0) {
            while (paramString.moveToNext())
            {
                SearchBean localSearchBean = new SearchBean();
                localSearchBean.setCount(paramString.getString(paramString.getColumnIndex("messagecounts")));
                localSearchBean.setPacketId(paramString.getString(paramString.getColumnIndex("packetId")));
                localSearchBean.setRoomFlag(paramString.getInt(paramString.getColumnIndex("roomFlag")));
                localSearchBean.setUserId(paramString.getString(paramString.getColumnIndex("userId")));
                localSearchBean.setToTelephone(paramString.getString(paramString.getColumnIndex("telephone")));
                localSearchBean.setContent(paramString.getString(paramString.getColumnIndex("content")));
                localSearchBean.setNickName(paramString.getString(paramString.getColumnIndex("nickName")));
                localSearchBean.setIbcdomain(paramString.getString(paramString.getColumnIndex("ibcdomain")));
                localSearchBean.setTimesend(paramString.getInt(paramString.getColumnIndex("timesend")));
                localSearchBean.setIndexNum(paramString.getInt(paramString.getColumnIndex("indexNum")));
                localSearchBean.setFilepath(paramString.getString(paramString.getColumnIndex("filepath")));
                localArrayList.add(localSearchBean);
            }
        }
        paramString.close();
        return localArrayList;
    }

    public List<SearchBean> searchOnLibraryWithoutLimit(String paramString)
    {
        ArrayList localArrayList = new ArrayList();
        localArrayList.clear();
        if (paramString.length() == 0) {
            this.db.rawQuery("SELECT * FROM friend_fts", null);
        }
        do
        {
            return localArrayList;
            paramString = this.db.rawQuery("SELECT count(friend_fts.userId) AS messagecounts,* FROM friend_fts WHERE friend_fts.content MATCH ? GROUP BY friend_fts.roomFlag,friend_fts.userId ORDER BY friend_fts.roomFlag,friend_fts.timesend DESC", new String[] { paramString + "*" });
        } while (paramString == null);
        if (paramString.getCount() > 0) {
            while (paramString.moveToNext())
            {
                SearchBean localSearchBean = new SearchBean();
                localSearchBean.setCount(paramString.getString(paramString.getColumnIndex("messagecounts")));
                localSearchBean.setPacketId(paramString.getString(paramString.getColumnIndex("packetId")));
                localSearchBean.setRoomFlag(paramString.getInt(paramString.getColumnIndex("roomFlag")));
                localSearchBean.setUserId(paramString.getString(paramString.getColumnIndex("userId")));
                localSearchBean.setToTelephone(paramString.getString(paramString.getColumnIndex("telephone")));
                localSearchBean.setContent(paramString.getString(paramString.getColumnIndex("content")));
                localSearchBean.setNickName(paramString.getString(paramString.getColumnIndex("nickName")));
                localSearchBean.setIbcdomain(paramString.getString(paramString.getColumnIndex("ibcdomain")));
                localSearchBean.setTimesend(paramString.getInt(paramString.getColumnIndex("timesend")));
                localSearchBean.setIndexNum(paramString.getInt(paramString.getColumnIndex("indexNum")));
                localSearchBean.setFilepath(paramString.getString(paramString.getColumnIndex("filepath")));
                localArrayList.add(localSearchBean);
            }
        }
        paramString.close();
        return localArrayList;
    }

    public void updateQueryInfo(String paramString1, String paramString2)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("content", paramString1);
        this.db.update("friend_fts", localContentValues, "userId = ?", new String[] { paramString2 });
    }
}
