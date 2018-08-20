package com.olym.mjt.module.calllog;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog.Calls;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.databean.event.CallMissedNumEvent;
import com.olym.mjt.pjsip.sip.api.SipCallSession;
import com.olym.mjt.pjsip.sip.api.SipUri;
import com.olym.mjt.pjsip.sip.api.SipUri.ParsedSipContactInfos;
import com.olym.mjt.pjsip.sip.models.CallerInfo;
import com.olym.mjt.pjsip.sip.models.Filter;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.DateUtil;
import com.olym.mjt.utils.Log;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallLogHelper
{
    private static final String ACTION_ANNOUNCE_SIP_CALLLOG = "de.ub0r.android.callmeter.SAVE_SIPCALL";
    private static final String EXTRA_CALL_LOG_URI = "uri";
    public static final String EXTRA_SIP_PROVIDER = "provider";
    private static final String THIS_FILE = "CallLogHelper";

    public static void addCallLog(Context paramContext, ContentValues paramContentValues1, ContentValues paramContentValues2)
    {
        ContentResolver localContentResolver = paramContext.getContentResolver();
        Intent localIntent = null;
        try
        {
            paramContentValues1 = localContentResolver.insert(CallLog.Calls.CONTENT_URI, paramContentValues1);
            if (paramContentValues1 != null)
            {
                localIntent = new Intent("de.ub0r.android.callmeter.SAVE_SIPCALL");
                localIntent.putExtra("uri", paramContentValues1.toString());
                paramContentValues1 = paramContentValues2.getAsString("provider");
                if (paramContentValues1 != null) {
                    localIntent.putExtra("provider", paramContentValues1);
                }
                paramContext.sendBroadcast(localIntent);
            }
            return;
        }
        catch (IllegalArgumentException paramContentValues1)
        {
            for (;;)
            {
                LogFinalUtils.logForException(paramContentValues1);
                Log.w("CallLogHelper", "Cannot insert call log entry. Probably not a phone", paramContentValues1);
                paramContentValues1 = localIntent;
            }
        }
    }

    private static String getKey(long paramLong, String paramString, int paramInt)
    {
        String str = DateUtil.getTimeForYMD(paramLong);
        if (paramInt == 3) {
            return str + "_100_" + paramString;
        }
        return str + "_101_" + paramString;
    }

    public static ArrayList<CallItem> handleCursor(Cursor paramCursor)
    {
        ArrayList localArrayList = new ArrayList();
        Object localObject = null;
        long l1 = -1L;
        int i = -1;
        CallItem localCallItem = null;
        if ((paramCursor != null) && (paramCursor.moveToFirst())) {}
        for (;;)
        {
            String str2 = paramCursor.getString(paramCursor.getColumnIndex("number"));
            SipUri.ParsedSipContactInfos localParsedSipContactInfos = SipUri.parseSipContact(str2);
            String str1 = localParsedSipContactInfos.userName;
            int j = paramCursor.getInt(paramCursor.getColumnIndex("type"));
            long l2 = paramCursor.getLong(paramCursor.getColumnIndex("date"));
            long l3 = paramCursor.getLong(paramCursor.getColumnIndex("duration"));
            if ((i == j) && (localObject != null) && (((String)localObject).equals(str1)) && (l1 != -1L) && (DateUtil.isSameDay(l1, l2)))
            {
                if (localCallItem.getExtraItems() == null) {
                    localCallItem.setExtraItems(new ArrayList());
                }
                str1 = paramCursor.getString(paramCursor.getColumnIndex("_id"));
                localCallItem.getExtraItems().add(new CallItem.ExtraItem(str1, j, l2, l3));
            }
            while (!paramCursor.moveToNext())
            {
                return localArrayList;
                localObject = localParsedSipContactInfos.domain;
                localCallItem = new CallItem(j, str2, str1, l2, l3);
                localCallItem.setDomain((String)localObject);
                localCallItem.setDisplayName(paramCursor.getString(paramCursor.getColumnIndex("name")));
                localCallItem.setId(paramCursor.getString(paramCursor.getColumnIndex("_id")));
                localArrayList.add(localCallItem);
                localObject = str1;
                l1 = l2;
                i = j;
            }
        }
    }

    public static ArrayList<CallItem> handleCursorNew(Cursor paramCursor)
    {
        HashMap localHashMap = new HashMap();
        ArrayList localArrayList = new ArrayList();
        if ((paramCursor != null) && (paramCursor.moveToFirst())) {}
        for (;;)
        {
            Object localObject = paramCursor.getString(paramCursor.getColumnIndex("number"));
            String str4 = SipUri.parseSipContact((String)localObject).userName;
            int i = paramCursor.getInt(paramCursor.getColumnIndex("type"));
            long l1 = paramCursor.getLong(paramCursor.getColumnIndex("date"));
            long l2 = paramCursor.getLong(paramCursor.getColumnIndex("duration"));
            String str1 = paramCursor.getString(paramCursor.getColumnIndex("_id"));
            String str2 = paramCursor.getString(paramCursor.getColumnIndex("name"));
            String str3 = getKey(l1, str4, i);
            CallItem localCallItem = (CallItem)localHashMap.get(str3);
            if (localCallItem == null)
            {
                localObject = new CallItem(i, (String)localObject, str4, l1, l2);
                ((CallItem)localObject).setDisplayName(str2);
                ((CallItem)localObject).setId(str1);
                localHashMap.put(str3, localObject);
                localArrayList.add(localObject);
            }
            while (!paramCursor.moveToNext())
            {
                return localArrayList;
                if (localCallItem.getExtraItems() == null) {
                    localCallItem.setExtraItems(new ArrayList());
                }
                localCallItem.getExtraItems().add(new CallItem.ExtraItem(str1, i, l1, l2));
            }
        }
    }

    public static ContentValues logValuesForCall(Context paramContext, SipCallSession paramSipCallSession, long paramLong)
    {
        ContentValues localContentValues = new ContentValues();
        String str2 = paramSipCallSession.getRemoteContact();
        if (str2 == null)
        {
            paramContext = null;
            return paramContext;
        }
        localContentValues.put("number", str2);
        Matcher localMatcher = Pattern.compile("^(?:\")?([^<\"]*)(?:\")?[ ]*(?:<)?sip(?:s)?:([^@]*@[^>]*)(?:>)?", 2).matcher(str2);
        String str1 = str2;
        if (localMatcher.matches()) {
            str1 = localMatcher.group(2);
        }
        label75:
        int j;
        int i;
        if (paramLong > 0L)
        {
            l = paramLong;
            localContentValues.put("date", Long.valueOf(l));
            j = 2;
            i = 0;
            if (paramSipCallSession.isIncoming())
            {
                j = 3;
                i = 1;
                if (paramLong <= 0L) {
                    break label378;
                }
                j = 1;
                i = 0;
            }
            label118:
            if (Filter.isAutoAnswerNumber(paramContext, paramSipCallSession.getAccId(), str1, null) == paramSipCallSession.getLastStatusCode()) {
                i = 0;
            }
            localContentValues.put("type", Integer.valueOf(j));
            localContentValues.put("new", Integer.valueOf(i));
            if (paramLong <= 0L) {
                break label417;
            }
        }
        label378:
        label417:
        for (long l = (System.currentTimeMillis() - paramLong) / 1000L;; l = 0L)
        {
            localContentValues.put("duration", Long.valueOf(l));
            localContentValues.put("account_id", Long.valueOf(paramSipCallSession.getAccId()));
            localContentValues.put("status_code", Integer.valueOf(paramSipCallSession.getLastStatusCode()));
            localContentValues.put("status_text", paramSipCallSession.getLastStatusComment());
            paramContext = CallerInfo.getCallerInfoFromSipUri(paramContext, str2);
            if (paramContext != null)
            {
                localContentValues.put("name", paramSipCallSession.getDisplayName());
                localContentValues.put("numberlabel", paramContext.numberLabel);
                localContentValues.put("numbertype", Integer.valueOf(paramContext.numberType));
            }
            Applog.info_importance("----������������������--- " + str2 + " " + paramLong + " " + j + " " + paramSipCallSession.getDisplayName());
            paramContext = localContentValues;
            if (j != 3) {
                break;
            }
            UserSpUtil.getInstanse().setCallMissedNumAdd();
            CallMissedNumEvent.post(new CallMissedNumEvent());
            return localContentValues;
            l = System.currentTimeMillis();
            break label75;
            if ((paramSipCallSession.getLastStatusCode() != 603) && (paramSipCallSession.getLastStatusCode() != 486) && (paramSipCallSession.getLastReasonCode() != 200)) {
                break label118;
            }
            j = 1;
            i = 0;
            break label118;
        }
    }
}
