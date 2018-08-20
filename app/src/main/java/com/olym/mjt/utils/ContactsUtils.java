package com.olym.mjt.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.text.TextUtils;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.database.dao.LocalContactDao;
import com.olym.mjt.databean.bean.LocalContact;
import com.olym.mjt.databean.bean.PhoneContactsBean;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.module.MjtApplication;
import java.util.ArrayList;
import java.util.Iterator;

public class ContactsUtils
{
    public static void ChangeContact(String paramString1, String paramString2)
    {
        ContentResolver localContentResolver = MjtApplication.getInstance().getContentResolver();
        Uri localUri = ContactsContract.Data.CONTENT_URI;
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("data1", paramString2);
        localContentResolver.update(localUri, localContentValues, "_id = ?", new String[] { paramString1 });
    }

    public static PhoneContactsBean getPhoneContactsAndSava(Context paramContext)
    {
        StringBuffer localStringBuffer = new StringBuffer();
        ArrayList localArrayList = new ArrayList();
        Object localObject;
        if (ChannelUtil.currentChannel != 102)
        {
            paramContext = paramContext.getContentResolver();
            for (;;)
            {
                LocalContact localLocalContact;
                try
                {
                    localObject = paramContext.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] { "_id", "data1", "display_name" }, null, null, null);
                    if ((localObject == null) || (!((Cursor)localObject).moveToNext())) {
                        break;
                    }
                    String str1 = ((Cursor)localObject).getString(((Cursor)localObject).getColumnIndex("_id"));
                    paramContext = ((Cursor)localObject).getString(((Cursor)localObject).getColumnIndex("data1"));
                    String str2 = ((Cursor)localObject).getString(((Cursor)localObject).getColumnIndex("display_name"));
                    if ((TextUtils.isEmpty(paramContext)) || (TextUtils.isEmpty(str2))) {
                        continue;
                    }
                    paramContext = PhoneNumberUtil.parse(paramContext.replace(" ", "").trim()).replace("-", "").trim();
                    localLocalContact = new LocalContact();
                    localLocalContact.setNickName(str2);
                    localLocalContact.setRemarkName(str2);
                    localLocalContact.setWholeSpell(new CharacterParser().getSellingWithPolyphone(str2));
                    localLocalContact.setSimplespell(new CharacterParser().getSimpleSellingPolyphone(str2));
                    localLocalContact.setLocalId(str1);
                    if (paramContext.contains("*"))
                    {
                        paramContext = PhoneNumberUtil.parse(EngineUtils.getInstance().decryptPhoneNumber(paramContext).replace(" ", "").trim()).replace("-", "").trim();
                        localLocalContact.setTelephone(paramContext);
                        localLocalContact.setIssecret(1);
                        if (!ValidateUtil.validatePhone(paramContext)) {
                            continue;
                        }
                        localStringBuffer.append(paramContext.replace(" ", "").trim() + ";");
                        localArrayList.add(localLocalContact);
                        continue;
                    }
                    localLocalContact.setTelephone(paramContext);
                }
                catch (Exception paramContext)
                {
                    LogFinalUtils.logForException(paramContext);
                    return new PhoneContactsBean("", localArrayList);
                }
                localLocalContact.setIssecret(0);
            }
            if (localObject != null) {
                ((Cursor)localObject).close();
            }
            LocalContactDao.getInstance().addLocalContacts(localArrayList);
        }
        for (;;)
        {
            return new PhoneContactsBean(localStringBuffer.toString(), localArrayList);
            paramContext = LocalContactDao.getInstance().getAllLocalContact();
            if (paramContext != null) {
                localArrayList.addAll(paramContext);
            }
            paramContext = localArrayList.iterator();
            while (paramContext.hasNext())
            {
                localObject = (LocalContact)paramContext.next();
                localStringBuffer.append(((LocalContact)localObject).getTelephone() + ";");
            }
        }
    }
}
