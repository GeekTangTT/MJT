package com.olym.mjt.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.olym.mjt.databean.bean.ChatMessage;
import java.sql.SQLException;

public class ChatMessageDaoImpl
        extends BaseDaoImpl<ChatMessage, Integer>
{
    public ChatMessageDaoImpl(ConnectionSource paramConnectionSource, DatabaseTableConfig<ChatMessage> paramDatabaseTableConfig)
            throws SQLException
    {
        super(paramConnectionSource, paramDatabaseTableConfig);
    }
}
