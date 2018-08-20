package com.olym.mjt.module.message.sms.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.olym.mjt.database.dao.ChatMessageDao;
import com.olym.mjt.database.dao.FriendDao;
import com.olym.mjt.databean.bean.ChatMessage;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.main.MJTMainActivity;
import com.olym.mjt.module.message.listener.ListenerManager;
import com.olym.mjt.module.message.listener.SmsMessageListener;
import com.olym.mjt.module.message.sms.event.MessageNeedFireEvent;
import com.olym.mjt.module.message.sms.event.SendScreenHotMsg;
import com.olym.mjt.module.message.sms.event.SendSmsEvent;
import com.olym.mjt.module.message.sms.event.SmsDeleteEvent;
import com.olym.mjt.module.message.sms.event.SmsLoadDataFromDBEvent;
import com.olym.mjt.module.message.sms.event.SmsNumUpdateEvent;
import com.olym.mjt.module.message.sms.view.ChatSmsBottomView;
import com.olym.mjt.module.message.sms.view.ChatSmsBottomView.ChatBottomSmsInterface;
import com.olym.mjt.module.message.sms.view.ChatSmsContentView;
import com.olym.mjt.module.message.sms.view.ChatSmsContentView.SmsEventListener;
import com.olym.mjt.module.user.login.LoginActivity;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.LoginHelper;
import com.olym.mjt.utils.StatusBarUtil;
import com.olym.mjt.utils.TimeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SmsChatActivity
        extends AppCompatActivity
        implements ChatSmsBottomView.ChatBottomSmsInterface, View.OnClickListener, SmsMessageListener, ChatSmsContentView.SmsEventListener
{
    public static final String SMS_DISPLAYNAME = "sms_displayname";
    public static final String SMS_PHONE = "sms_phone";
    public static final String SMS_USERID = "sms_userid";
    private ChatSmsBottomView chatSmsBottomView;
    private ChatSmsContentView chatSmsContentView;
    private String displayName;
    private int fireMessage;
    private Handler handler = new Handler();
    private int mAddSize = 0;
    private List<ChatMessage> mChatMessages;
    private Friend mFriend;
    private boolean mHasMoreData = true;
    private String mLoginUserId;
    private int mMinTime = 0;
    private int mPageSize = 20;
    private LinearLayout mjt_chat_back;
    private TextView mjt_user_info;
    private String phone;
    private ImageView sms_burn;
    private ImageView sms_burn_open;
    private String userID;

    private void initData()
    {
        List localList;
        if (this.mChatMessages.size() > 0)
        {
            this.mMinTime = ((ChatMessage)this.mChatMessages.get(0)).getTimeSend();
            this.mAddSize = this.mChatMessages.size();
            new ArrayList();
            localList = ChatMessageDao.getInstance().getSmsChatMessages(this.mLoginUserId, this.userID, this.mMinTime, this.mPageSize);
            if ((localList != null) && (localList.size() > 0)) {
                break label132;
            }
            this.mAddSize = 0;
        }
        for (this.mHasMoreData = false;; this.mHasMoreData = true)
        {
            this.handler.postDelayed(new SmsChatActivity.2(this), 1000L);
            return;
            this.mAddSize = 0;
            this.mMinTime = 0;
            break;
            label132:
            this.mAddSize = localList.size();
            int i = 0;
            while (i < localList.size())
            {
                ChatMessage localChatMessage = (ChatMessage)localList.get(i);
                this.mChatMessages.add(0, localChatMessage);
                i += 1;
            }
        }
    }

    private void initView()
    {
        EventBusUtil.register(this);
        this.mLoginUserId = MjtApplication.getInstance().getLoginUser().getUserId();
        this.mFriend = FriendDao.getInstance().getFriend(this.mLoginUserId, this.userID);
        this.mjt_user_info = ((TextView)findViewById(2131231184));
        this.chatSmsContentView = ((ChatSmsContentView)findViewById(2131230825));
        this.chatSmsBottomView = ((ChatSmsBottomView)findViewById(2131230822));
        this.sms_burn = ((ImageView)findViewById(2131231366));
        this.sms_burn_open = ((ImageView)findViewById(2131231367));
        this.mjt_chat_back = ((LinearLayout)findViewById(2131231179));
        this.mjt_chat_back.setOnClickListener(this);
        this.sms_burn.setOnClickListener(this);
        this.sms_burn_open.setOnClickListener(this);
        this.chatSmsBottomView.setChatBottomSmsInterface(this);
        this.chatSmsContentView.setSmsEventListener(this);
        this.chatSmsContentView.setTranscriptMode(1);
        this.chatSmsContentView.setStackFromBottom(true);
        this.chatSmsContentView.setmLoginUserId(this.mLoginUserId);
        this.chatSmsContentView.setmFriendID(this.phone);
        ListenerManager.getInstance().addSmsMessageListener(this);
        FriendDao.getInstance().markUserMessageRead(this.mLoginUserId, this.userID);
        this.mChatMessages = new ArrayList();
        this.mjt_user_info.setText(this.displayName);
        this.chatSmsContentView.setData(this.mChatMessages);
        this.chatSmsContentView.setRefreshListener(new SmsChatActivity.1(this));
        initData();
        SmsLoadDataFromDBEvent.post(new SmsLoadDataFromDBEvent());
        SmsNumUpdateEvent.post(new SmsNumUpdateEvent());
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handlerMessageNeedFireEvent(MessageNeedFireEvent paramMessageNeedFireEvent)
    {
        String str = paramMessageNeedFireEvent.getPacketID();
        int j = paramMessageNeedFireEvent.getNeedfire();
        int i = 0;
        for (;;)
        {
            if (i < this.mChatMessages.size())
            {
                paramMessageNeedFireEvent = (ChatMessage)this.mChatMessages.get(i);
                if ((paramMessageNeedFireEvent.getPacketId() != null) && (paramMessageNeedFireEvent.getPacketId().equals(str)))
                {
                    paramMessageNeedFireEvent.setNeedfire(j);
                    this.chatSmsContentView.notifyDataSetInvalidated(false);
                }
            }
            else
            {
                return;
            }
            i += 1;
        }
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handlerSMsDeleteEvent(SmsDeleteEvent paramSmsDeleteEvent)
    {
        int j = paramSmsDeleteEvent.getPosition();
        String str = paramSmsDeleteEvent.getPacketID();
        int i = 0;
        if (i < this.mChatMessages.size())
        {
            paramSmsDeleteEvent = (ChatMessage)this.mChatMessages.get(i);
            boolean bool;
            if ((paramSmsDeleteEvent.getPacketId() != null) && (paramSmsDeleteEvent.getPacketId().equals(str)))
            {
                bool = false;
                if (this.mChatMessages.size() != 1) {
                    break label149;
                }
                paramSmsDeleteEvent = (ChatMessage)this.mChatMessages.get(j);
                bool = true;
            }
            for (;;)
            {
                FriendDao.getInstance().updateLastSms(this.mLoginUserId, this.userID, paramSmsDeleteEvent, paramSmsDeleteEvent.getFromUserName(), bool);
                this.mChatMessages.remove(i);
                this.chatSmsContentView.notifyDataSetInvalidated(false);
                SmsLoadDataFromDBEvent.post(new SmsLoadDataFromDBEvent());
                i += 1;
                break;
                label149:
                if (j == this.mChatMessages.size() - 1) {
                    paramSmsDeleteEvent = (ChatMessage)this.mChatMessages.get(j - 1);
                } else {
                    paramSmsDeleteEvent = (ChatMessage)this.mChatMessages.get(this.mChatMessages.size() - 1);
                }
            }
        }
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handlerSendScreenHot(SendScreenHotMsg paramSendScreenHotMsg)
    {
        sendScreenShotMsg(paramSendScreenHotMsg.getMsg());
    }

    public void onClick(View paramView)
    {
        switch (paramView.getId())
        {
            default:
                return;
            case 2131231179:
                paramView = (InputMethodManager)getSystemService("input_method");
                if (paramView != null) {
                    paramView.hideSoftInputFromWindow(this.chatSmsBottomView.getWindowToken(), 0);
                }
                finish();
                return;
            case 2131231366:
                this.sms_burn_open.setVisibility(0);
                this.sms_burn.setVisibility(8);
                this.chatSmsBottomView.setSms_chat_edit(getResources().getString(2131690047));
                setFireMessage(1);
                return;
        }
        this.sms_burn.setVisibility(0);
        this.sms_burn_open.setVisibility(8);
        this.chatSmsBottomView.setSms_chat_edit("");
        setFireMessage(0);
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(2131427437);
        StatusBarUtil.setStatusBar(this);
        paramBundle = getIntent().getExtras();
        if (paramBundle != null)
        {
            this.userID = paramBundle.getString("sms_userid");
            this.phone = paramBundle.getString("sms_phone");
            this.displayName = paramBundle.getString("sms_displayname");
        }
        initView();
        if (!MjtApplication.getInstance().isAppNormal)
        {
            if (LoginHelper.prepareUser()) {
                startActivity(new Intent(this, MJTMainActivity.class));
            }
        }
        else {
            return;
        }
        startActivity(new Intent(this, LoginActivity.class));
    }

    protected void onDestroy()
    {
        super.onDestroy();
        ListenerManager.getInstance().removeSmsMessageListener(this);
        EventBusUtil.unregister(this);
    }

    public void onFriendUpdate()
    {
        this.mFriend = FriendDao.getInstance().getFriend(this.mLoginUserId, this.userID);
    }

    public void onMessageSendStateChange(int paramInt1, int paramInt2)
    {
        int i = 0;
        while (i < this.mChatMessages.size())
        {
            ChatMessage localChatMessage = (ChatMessage)this.mChatMessages.get(i);
            if (paramInt2 == localChatMessage.get_id())
            {
                localChatMessage.setMessageState(paramInt1);
                this.chatSmsContentView.notifyDataSetInvalidated(false);
            }
            i += 1;
        }
    }

    public boolean onNewMessage(String paramString, ChatMessage paramChatMessage)
    {
        boolean bool = false;
        if (this.userID.compareToIgnoreCase(paramString) == 0)
        {
            this.mChatMessages.add(paramChatMessage);
            this.chatSmsContentView.notifyDataSetInvalidated(false);
            bool = true;
        }
        return bool;
    }

    protected void onResume()
    {
        super.onResume();
        if (this.mFriend == null) {
            this.mFriend = FriendDao.getInstance().getFriend(this.mLoginUserId, this.userID);
        }
        if (this.mFriend == null) {
            finish();
        }
    }

    public void onSendAgain(ChatMessage paramChatMessage)
    {
        String str = MjtApplication.getInstance().getLoginUser().getTelephone();
        byte b = 0;
        if (this.fireMessage == 1) {
            b = 1;
        }
        SendSmsEvent.post(new SendSmsEvent(paramChatMessage.getPacketId(), str, this.phone, paramChatMessage.getContent(), b, paramChatMessage.get_id(), this.mLoginUserId, this.mFriend.getDomain(), this.mFriend.getDomainVersion()));
    }

    public void sendScreenShotMsg(String paramString)
    {
        if (this.mFriend == null) {
            return;
        }
        String str1 = MjtApplication.getInstance().getLoginUser().getTelephone();
        ChatMessage localChatMessage = new ChatMessage();
        String str2 = UUID.randomUUID().toString().replaceAll("-", "");
        localChatMessage.setType(10);
        localChatMessage.setContent(paramString);
        localChatMessage.setFromUserId(this.mLoginUserId);
        localChatMessage.setTimeSend(TimeUtils.sms_time_current_time());
        localChatMessage.setPacketId(str2);
        localChatMessage.setMySend(true);
        ChatMessageDao.getInstance().saveNewSmsMessage(this.mLoginUserId, this.phone, localChatMessage, this.displayName);
        this.chatSmsContentView.notifyDataSetInvalidated(true);
        SendSmsEvent.post(new SendSmsEvent(str2, str1, this.phone, paramString, (byte)3, localChatMessage.get_id(), this.mLoginUserId, this.mFriend.getDomain(), this.mFriend.getDomainVersion()));
    }

    public void sendSms(String paramString)
    {
        if (this.mFriend == null) {
            return;
        }
        String str1 = MjtApplication.getInstance().getLoginUser().getTelephone();
        String str2 = MjtApplication.getInstance().getLoginUser().getNickName();
        ChatMessage localChatMessage = new ChatMessage();
        if (this.fireMessage == 1) {}
        for (int i = 2;; i = 1)
        {
            String str3 = UUID.randomUUID().toString().replaceAll("-", "");
            localChatMessage.setType(i);
            localChatMessage.setContent(paramString);
            localChatMessage.setFire(this.fireMessage);
            localChatMessage.setFromUserId(this.mLoginUserId);
            localChatMessage.setFromUserName(str2);
            localChatMessage.setTimeSend(TimeUtils.sms_time_current_time());
            localChatMessage.setPacketId(str3);
            localChatMessage.setMySend(true);
            ChatMessageDao.getInstance().saveNewSmsMessage(this.mLoginUserId, this.phone, localChatMessage, this.displayName);
            this.mChatMessages.add(localChatMessage);
            this.chatSmsContentView.notifyDataSetInvalidated(true);
            byte b = 0;
            if (this.fireMessage == 1) {
                b = 1;
            }
            SendSmsEvent.post(new SendSmsEvent(str3, str1, this.phone, paramString, b, localChatMessage.get_id(), this.mLoginUserId, this.mFriend.getDomain(), this.mFriend.getDomainVersion()));
            return;
        }
    }

    public void setFireMessage(int paramInt)
    {
        this.fireMessage = paramInt;
    }
}
