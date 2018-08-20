package com.olym.mjt.module.main;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.lc.methodex.LogFinalUtils;
import com.nisc.api.SecEngineException;
import com.olym.mjt.base.activity.BasePresenterActivity;
import com.olym.mjt.config.AppConstant;
import com.olym.mjt.database.DBManager;
import com.olym.mjt.database.dao.FriendDao;
import com.olym.mjt.database.dao.NewFriendDao;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.CallMissedNumEvent;
import com.olym.mjt.databean.event.GesturePasswordErrorEvent;
import com.olym.mjt.databean.event.InCallViewDestroyEvent;
import com.olym.mjt.databean.event.LogoutEvent;
import com.olym.mjt.databean.event.NewFriendNumEvent;
import com.olym.mjt.databean.event.ShowNotificationEvent;
import com.olym.mjt.datastat.UploadDataUtils;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.contacts.ContactsFragment;
import com.olym.mjt.module.dial.DialFragment;
import com.olym.mjt.module.message.MessageFragment;
import com.olym.mjt.module.message.chat.MJTChatActivity;
import com.olym.mjt.module.message.chat.ShareActivity;
import com.olym.mjt.module.message.event.MsgNumUpdateEvent;
import com.olym.mjt.module.message.sms.event.SmsNumUpdateEvent;
import com.olym.mjt.module.message.sms.ui.SmsFragment;
import com.olym.mjt.module.message.tools.shortcutbadger.ShortcutBadger;
import com.olym.mjt.module.mine.MineFragment;
import com.olym.mjt.module.notification.NotificationService;
import com.olym.mjt.network.client.HttpsClient;
import com.olym.mjt.pjsip.sip.api.SipManager;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.CachedThreadPoolUtils;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.CheckAudioPermission;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.NetworkUtil;
import com.olym.mjt.utils.StatusBarUtil;
import com.olym.mjt.utils.ViewTransferUtil;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.IBCSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.XmppSpUtil;
import com.olym.mjt.widget.DivideRadioGroup;
import com.olym.mjt.widget.DivideRadioGroup.OnCheckedChangeListener;
import com.olym.mjt.widget.applock.LockPatternUtils;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import com.tencent.bugly.crashreport.CrashReport;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MJTMainActivity
        extends BasePresenterActivity<MJTMainPresenter>
        implements IMJTMainView
{
    public static final String KEY_CHANGED_MESSAGEFRAGMENT = "key_change_messagefragment";
    public static final String KEY_FROM_NOTIFICATION_SIP_MISS = "key_from_notification";
    public static final String KEY_GESTURE_PASSWORD_ERROR = "key_geture_password_error";
    public static final String KEY_LOGOUT = "key_logout";
    public static final String KEY_TO_CHAT = "key_to_chat";
    public static final String KEY_TO_SHARE = "key_to_share";
    private static final String TAG_DIALER = "dialer";
    private static final String TAG_FRIEND = "friend";
    private static final String TAG_ME = "me";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_SMS = "sms";
    private TipsDialog accountDisabledTipsDialog;
    private ContactsFragment contactsFragment;
    private DialFragment dialerFragment;
    private Handler handler = new Handler();
    private boolean isHandleAllLogout = false;
    private boolean isMemoryReset = false;
    private boolean isPause = true;
    private TipsDialog keepLiveDialog;
    private TipsDialog keyDeleteTipsDialog;
    private TipsDialog loginErrorTipsDialog;
    private int mCallMissedNum = 0;
    private TextView mCallMissedTv;
    private int mFriendUnReadNum = 0;
    private TextView mFriendUnReadTv;
    private Fragment mLastFragment;
    private DivideRadioGroup mMainTabRadioGroup;
    private boolean mMsgNumNeedUpdate = false;
    private int mMsgUnReadNum = 0;
    private TextView mMsgUnReadTv;
    private int mSmsUnReadNum = 0;
    private TextView mSmsUnReadTv;
    private DivideRadioGroup.OnCheckedChangeListener mTabRadioGroupChangeListener = new MJTMainActivity.6(this);
    private Handler mUnReadHandler = new Handler();
    private View main_tab_dial_frame;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;
    private Intent notificationIntent;
    private ServiceConnection notificationServiceConnection = new MJTMainActivity.5(this);
    private TipsDialog otherLoginTipsDialog;
    private TipsDialog selfDestructTipsDialog;
    private SmsFragment smsFragment;
    private FrameLayout sms_framelayout;

    private void fragmentChange(Fragment paramFragment, String paramString)
    {
        Applog.info("-----fragmentChange-----" + paramString);
        FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (this.mLastFragment == paramFragment) {}
        do
        {
            return;
            if ((this.mLastFragment != null) && (this.mLastFragment != paramFragment)) {
                localFragmentTransaction.hide(this.mLastFragment);
            }
        } while (paramFragment == null);
        if (!paramFragment.isAdded())
        {
            Applog.info("-----fragmentChange-----add---");
            localFragmentTransaction.add(2131231145, paramFragment, paramString);
        }
        for (;;)
        {
            this.mLastFragment = paramFragment;
            localFragmentTransaction.commitNowAllowingStateLoss();
            return;
            Applog.info("-----fragmentChange-----show---");
            localFragmentTransaction.show(paramFragment);
        }
    }

    private int getVersionCode()
    {
        try
        {
            int i = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            return i;
        }
        catch (Exception localException)
        {
            LogFinalUtils.logForException(localException);
        }
        return -1;
    }

    private boolean handleAllLogout(Intent paramIntent)
    {
        if (this.isHandleAllLogout) {
            return true;
        }
        if (paramIntent.getBooleanExtra("key_token_null", false))
        {
            Applog.systemOut("------handleAllLogout---KEY_TOKEN_NULL--");
            Applog.info("------handleAllLogout---KEY_TOKEN_NULL--");
            this.isHandleAllLogout = true;
            UploadDataUtils.uploadConflictEvent(UserSpUtil.getInstanse().getPhone(), UserSpUtil.getInstanse().getIBCDomain());
            logoutAccount();
            if (this.otherLoginTipsDialog == null) {
                this.otherLoginTipsDialog = new TipsDialog.Build(this).setDialogClickListener(new MJTMainActivity.8(this)).setContent(getResources().getString(2131689727)).setCancelable(false).setSingleChoise(true).build();
            }
            this.otherLoginTipsDialog.show();
            return true;
        }
        if (paramIntent.getBooleanExtra("key_key_delete", false))
        {
            Applog.systemOut("------handleAllLogout---KEY_KEY_DELETE--");
            Applog.info("------handleAllLogout---KEY_KEY_DELETE--");
            this.isHandleAllLogout = true;
            logoutAccount();
            if (this.accountDisabledTipsDialog == null) {
                this.accountDisabledTipsDialog = new TipsDialog.Build(this).setDialogClickListener(new MJTMainActivity.9(this)).setContent(getResources().getString(2131689883)).setCancelable(false).setSingleChoise(true).build();
            }
            this.accountDisabledTipsDialog.show();
            return true;
        }
        if (paramIntent.getBooleanExtra("key_account_disabled", false))
        {
            Applog.systemOut("------handleAllLogout---KEY_ACCOUNT_DISABLED--");
            Applog.info("------handleAllLogout---KEY_ACCOUNT_DISABLED--");
            this.isHandleAllLogout = true;
            logoutAccount();
            if (this.keyDeleteTipsDialog == null) {
                this.keyDeleteTipsDialog = new TipsDialog.Build(this).setDialogClickListener(new MJTMainActivity.10(this)).setContent(getResources().getString(2131689579)).setCancelable(false).setSingleChoise(true).build();
            }
            this.keyDeleteTipsDialog.show();
            return true;
        }
        if (paramIntent.getBooleanExtra("key_login_error", false))
        {
            Applog.systemOut("------handleAllLogout---KEY_LOGIN_ERROR--");
            Applog.info("------handleAllLogout---KEY_LOGIN_ERROR--");
            this.isHandleAllLogout = true;
            logoutAccount();
            if (this.loginErrorTipsDialog == null) {
                this.loginErrorTipsDialog = new TipsDialog.Build(this).setDialogClickListener(new MJTMainActivity.11(this)).setContent(getResources().getString(2131689718)).setCancelable(false).setSingleChoise(true).build();
            }
            this.loginErrorTipsDialog.show();
            return true;
        }
        if (paramIntent.getBooleanExtra("key_tfcard_error", false))
        {
            Applog.systemOut("------handleAllLogout---KEY_TFCARD_ERROR--");
            this.isHandleAllLogout = true;
            logoutAccount();
            if (this.loginErrorTipsDialog == null) {
                this.loginErrorTipsDialog = new TipsDialog.Build(this).setDialogClickListener(new MJTMainActivity.12(this)).setContent(getResources().getString(2131690130)).setCancelable(false).setSingleChoise(true).build();
            }
            this.loginErrorTipsDialog.show();
            return true;
        }
        if (paramIntent.getBooleanExtra("key_self_destruct", false))
        {
            Applog.systemOut("------handleAllLogout---KEY_SELF_DESTRUCT--");
            this.isHandleAllLogout = true;
            logoutAccount();
            if (this.selfDestructTipsDialog == null) {
                this.selfDestructTipsDialog = new TipsDialog.Build(this).setDialogClickListener(new MJTMainActivity.13(this)).setContent(getResources().getString(2131690106)).setCancelable(false).setSingleChoise(true).build();
            }
            this.selfDestructTipsDialog.show();
            return true;
        }
        return false;
    }

    private void initKeepLiveDialog()
    {
        this.keepLiveDialog = new TipsDialog.Build(this).setCancelable(false).setContent(getResources().getString(2131689726)).setDialogClickListener(new MJTMainActivity.7(this)).build();
    }

    private void initMsgUnReadTips(String paramString)
    {
        this.mMsgUnReadNum = FriendDao.getInstance().getMsgUnReadNumTotal(paramString);
        this.mSmsUnReadNum = FriendDao.getInstance().getSmsUnReadNumTotal(paramString);
        this.mFriendUnReadNum = FriendDao.getInstance().getNewFriendUnReadNumTotal(paramString);
        this.mCallMissedNum = UserSpUtil.getInstanse().getCallMissedNum();
        this.mUnReadHandler.post(new MJTMainActivity.14(this));
    }

    private void initPage()
    {
        Applog.systemOut("-----initPage------- " + this.isMemoryReset);
        if (!this.isMemoryReset)
        {
            this.messageFragment = new MessageFragment();
            this.contactsFragment = new ContactsFragment();
            this.dialerFragment = new DialFragment();
            this.mineFragment = new MineFragment();
            this.smsFragment = new SmsFragment();
            if (ChannelUtil.currentChannel == 106)
            {
                this.mMainTabRadioGroup.check(2131231151);
                return;
            }
            this.mMainTabRadioGroup.check(2131231148);
            return;
        }
        this.messageFragment = ((MessageFragment)getSupportFragmentManager().findFragmentByTag("message"));
        this.contactsFragment = ((ContactsFragment)getSupportFragmentManager().findFragmentByTag("friend"));
        this.dialerFragment = ((DialFragment)getSupportFragmentManager().findFragmentByTag("dialer"));
        this.mineFragment = ((MineFragment)getSupportFragmentManager().findFragmentByTag("me"));
        this.smsFragment = ((SmsFragment)getSupportFragmentManager().findFragmentByTag("sms"));
    }

    private void initView()
    {
        this.mMainTabRadioGroup.setOnCheckedChangeListener(this.mTabRadioGroupChangeListener);
    }

    private void logoutAccount()
    {
        Applog.info("------logoutAccount----");
        Applog.systemOut("------logoutAccount----");
        stopNotificationService();
        ShortcutBadger.removeCount(MjtApplication.getInstance());
        setChannel();
        removeNeedUserFragment();
        String str1 = UserSpUtil.getInstanse().getPhone();
        String str2 = MjtApplication.getInstance().getAccessToken();
        UserSpUtil.getInstanse().clearKey();
        IBCSpUtil.getInstanse().clearKey();
        LockPatternUtils.clearLockPattern();
        MjtApplication.getInstance().clearDatas();
        try
        {
            if (ChannelUtil.currentChannel != 106)
            {
                int i = EngineUtils.getInstance().deletePrivate(str1);
                Applog.info("--------logoutAccount------- " + i);
                EngineUtils.getInstance().logoutDevice();
                UserSpUtil.getInstanse().deletePriKey();
            }
            getContentResolver().delete(SipManager.CALLLOG_URI, null, null);
            HttpsClient.getInstanse().logout(str1, str2);
            InCallViewDestroyEvent.post(new InCallViewDestroyEvent());
            return;
        }
        catch (SecEngineException localSecEngineException)
        {
            for (;;)
            {
                Applog.info("----logoutAccount----SecEngineException---- " + localSecEngineException);
                Applog.systemOut("----logoutAccount----SecEngineException---- " + localSecEngineException);
                LogFinalUtils.logForException(localSecEngineException);
            }
        }
    }

    private void logoutAccountWithToLogin()
    {
        if (ChannelUtil.currentChannel != 106) {
            ViewTransferUtil.transferToLoginActivity(this);
        }
        logoutAccount();
        finish();
    }

    private void msg_num_reset()
    {
        if (this.isPause)
        {
            this.mMsgNumNeedUpdate = true;
            return;
        }
        initMsgUnReadTips(MjtApplication.getInstance().getLoginUser().getUserId());
    }

    private void refreshUnread()
    {
        String str = MjtApplication.getInstance().getLoginUser().getUserId();
        if (str == null) {}
        Object localObject1;
        do
        {
            Object localObject2;
            do
            {
                return;
                localObject2 = FriendDao.getInstance().getAllFriends(str);
                localObject1 = FriendDao.getInstance().getAllRooms(str);
            } while (localObject1 == null);
            ((List)localObject2).addAll((Collection)localObject1);
            localObject1 = new Friend();
            int i = 0;
            Iterator localIterator = ((List)localObject2).iterator();
            while (localIterator.hasNext())
            {
                localObject2 = (Friend)localIterator.next();
                if (((Friend)localObject2).getUnReadNum() <= 0) {
                    localObject1 = localObject2;
                }
                i += ((Friend)localObject2).getUnReadNum();
            }
            this.mMsgUnReadNum = i;
            this.mSmsUnReadNum = FriendDao.getInstance().getSmsUnReadNumTotal(str);
            this.mCallMissedNum = UserSpUtil.getInstanse().getCallMissedNum();
            this.mFriendUnReadNum = FriendDao.getInstance().getNewFriendUnReadNumTotal(str);
            updateMsgUnReadTv();
        } while (this.messageFragment == null);
        this.messageFragment.refreshUnread((Friend)localObject1);
    }

    private void setChannel()
    {
        HttpsClient.getInstanse().setchannelId();
    }

    private void startNotificationService()
    {
        if (this.notificationIntent == null)
        {
            Applog.systemOut("----startNotificationService-----");
            this.notificationIntent = new Intent(this, NotificationService.class);
            startService(this.notificationIntent);
            bindService(this.notificationIntent, this.notificationServiceConnection, 1);
        }
    }

    private void stopNotificationService()
    {
        if (this.notificationIntent != null)
        {
            Applog.systemOut("----stopNotificationService-----");
            stopService(this.notificationIntent);
            unbindService(this.notificationServiceConnection);
            this.notificationIntent = null;
        }
    }

    private void updateMsgUnReadTv()
    {
        this.handler.postDelayed(new MJTMainActivity.15(this), 0L);
    }

    public void destroy()
    {
        Applog.systemOut("------MAIN destroy----");
        Applog.info("------MAIN destroy----");
        EventBusUtil.unregister(this);
        EventBusUtil.removeAllStickyEvents();
    }

    public void handleBundle(Bundle paramBundle) {}

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleCallMissedNumEvent(CallMissedNumEvent paramCallMissedNumEvent)
    {
        this.mCallMissedNum = UserSpUtil.getInstanse().getCallMissedNum();
        Applog.systemOut("-------handleCallMissedNumEvent------ " + this.mCallMissedNum);
        if (this.mCallMissedNum > 0)
        {
            this.mCallMissedTv.setVisibility(0);
            return;
        }
        this.mCallMissedTv.setVisibility(8);
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleGesturePasswordError(GesturePasswordErrorEvent paramGesturePasswordErrorEvent)
    {
        Applog.info("------handleGesturePasswordError------");
        if (paramGesturePasswordErrorEvent.getType() == 1) {
            logoutAccount();
        }
        while (paramGesturePasswordErrorEvent.getType() != 2) {
            return;
        }
        paramGesturePasswordErrorEvent = new Intent(this, MJTMainActivity.class);
        paramGesturePasswordErrorEvent.putExtra("key_geture_password_error", true);
        startActivity(paramGesturePasswordErrorEvent);
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void handleLogout(LogoutEvent paramLogoutEvent)
    {
        Applog.info("------handleLogout------");
        paramLogoutEvent = new Intent(this, MJTMainActivity.class);
        paramLogoutEvent.putExtra("key_logout", true);
        startActivity(paramLogoutEvent);
    }

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handleMsgNumUpdateEvent(MsgNumUpdateEvent paramMsgNumUpdateEvent)
    {
        int i = paramMsgNumUpdateEvent.getType();
        int j = paramMsgNumUpdateEvent.getCount();
        if (i == -1)
        {
            msg_num_reset();
            return;
        }
        if (i == 1) {}
        for (i = this.mMsgUnReadNum + j;; i = this.mMsgUnReadNum - j)
        {
            this.mMsgUnReadNum = i;
            updateMsgUnReadTv();
            return;
        }
    }

    protected void handleSavedInstanceState(Bundle paramBundle)
    {
        super.handleSavedInstanceState(paramBundle);
        if (paramBundle == null)
        {
            this.isMemoryReset = false;
            Applog.info("------savedInstanceState--null---");
            return;
        }
        this.isMemoryReset = true;
        Applog.info("------savedInstanceState--!null--- " + paramBundle);
    }

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handlerNewFriendNumUpdateEvent(NewFriendNumEvent paramNewFriendNumEvent)
    {
        paramNewFriendNumEvent = MjtApplication.getInstance().getLoginUser().getUserId();
        this.mFriendUnReadNum = NewFriendDao.getInstance().getMsgUnReadNumTotal(paramNewFriendNumEvent);
        Applog.systemOut("MjtMainActivity NewFriendNumEvent unreadNum:" + this.mFriendUnReadNum);
        if ((ChannelUtil.currentChannel != 102) && (this.mFriendUnReadTv != null))
        {
            if (this.mFriendUnReadNum > 0) {
                this.mFriendUnReadTv.setVisibility(0);
            }
        }
        else {
            return;
        }
        this.mFriendUnReadTv.setVisibility(8);
    }

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handlerSmsNumUpdateEvent(SmsNumUpdateEvent paramSmsNumUpdateEvent)
    {
        paramSmsNumUpdateEvent = MjtApplication.getInstance().getLoginUser().getUserId();
        this.mSmsUnReadNum = FriendDao.getInstance().getSmsUnReadNumTotal(paramSmsNumUpdateEvent);
        if (this.mSmsUnReadNum > 0)
        {
            this.mSmsUnReadTv.setVisibility(0);
            return;
        }
        this.mSmsUnReadTv.setVisibility(8);
    }

    public void init()
    {
        StatusBarUtil.setStatusBar(this);
        if (ChannelUtil.currentChannel == 102) {
            setContentView(2131427395);
        }
        for (;;)
        {
            this.mMainTabRadioGroup = ((DivideRadioGroup)findViewById(2131231154));
            this.sms_framelayout = ((FrameLayout)findViewById(2131231156));
            this.main_tab_dial_frame = findViewById(2131231149);
            this.mMsgUnReadTv = ((TextView)findViewById(2131231153));
            this.mSmsUnReadTv = ((TextView)findViewById(2131231157));
            this.mCallMissedTv = ((TextView)findViewById(2131231146));
            this.mFriendUnReadTv = ((TextView)findViewById(2131231150));
            MjtApplication.getInstance().isAppNormal = true;
            MjtApplication.getInstance().setStartFromBoot(false);
            if (ChannelUtil.action_sms) {
                this.sms_framelayout.setVisibility(0);
            }
            if (ChannelUtil.currentChannel == 106) {
                this.main_tab_dial_frame.setVisibility(8);
            }
            XmppSpUtil.getInstanse().putBoolean(this, AppConstant.IS_NOTIFICATION, true);
            EventBusUtil.register(this);
            startNotificationService();
            initView();
            initPage();
            try
            {
                if (CheckAudioPermission.getRecordState() != 1) {
                    Applog.info("---������������������������--���������������������-----");
                }
                if ((AppSpUtil.getInstanse().getKeepLive()) && (!ChannelUtil.isUsedTEE))
                {
                    initKeepLiveDialog();
                    this.keepLiveDialog.show();
                }
                if ((!TextUtils.isEmpty(UserSpUtil.getInstanse().getNickName())) && (!AppSpUtil.getInstanse().getInitContacts())) {
                    ((MJTMainPresenter)this.presenter).updateNickName(UserSpUtil.getInstanse().getNickName());
                }
                Intent localIntent = getIntent();
                if ((localIntent != null) && (localIntent.getBooleanExtra("key_to_share", false)))
                {
                    localIntent.setComponent(new ComponentName(this, ShareActivity.class));
                    localIntent.setAction("android.intent.action.SEND");
                    startActivity(localIntent);
                }
                return;
                setContentView(2131427394);
            }
            catch (Exception localException)
            {
                for (;;)
                {
                    Applog.info("-----������������������---Exception--" + localException);
                }
            }
        }
    }

    public void onBackPressed()
    {
        Applog.systemOut("------onBackPressed-----");
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.setFlags(268435456);
        localIntent.addCategory("android.intent.category.HOME");
        startActivity(localIntent);
    }

    protected void onCreate(@Nullable Bundle paramBundle)
    {
        if (UserSpUtil.getInstanse().getPhone().equals(""))
        {
            super.onCreate(paramBundle);
            Applog.systemOut("------onCreate-----");
            ViewTransferUtil.transferToLoginActivity(this);
            finish();
            return;
        }
        CrashReport.setUserId(UserSpUtil.getInstanse().getPhone());
        DBManager.getInstance();
        super.onCreate(paramBundle);
    }

    protected void onNewIntent(Intent paramIntent)
    {
        super.onNewIntent(paramIntent);
        ShowNotificationEvent.post(new ShowNotificationEvent(2));
        ShowNotificationEvent.post(new ShowNotificationEvent(9));
        Applog.systemOut("---onNewIntent----- " + paramIntent);
        if (handleAllLogout(paramIntent)) {}
        do
        {
            return;
            if ((ChannelUtil.action_gesture) && (AppSpUtil.getInstanse().getApplockGestureenable()) && (paramIntent != null) && (paramIntent.getBooleanExtra("key_to_share", false)))
            {
                LockPatternUtils.gotoCheckViewForShare(this, paramIntent);
                return;
            }
            if ((ChannelUtil.action_gesture) && (MjtApplication.getInstance().isCheckdGesture()))
            {
                LockPatternUtils.gotoCheckView(this);
                return;
            }
            if (paramIntent.getBooleanExtra("key_logout", false))
            {
                logoutAccountWithToLogin();
                return;
            }
            if (paramIntent.getBooleanExtra("key_geture_password_error", false))
            {
                ViewTransferUtil.transferToLoginActivity(this);
                finish();
                return;
            }
            if (paramIntent.getBooleanExtra("key_change_messagefragment", false))
            {
                this.mMainTabRadioGroup.check(2131231151);
                return;
            }
            if (paramIntent.getBooleanExtra("key_from_notification", false))
            {
                this.mMainTabRadioGroup.check(2131231148);
                return;
            }
            if (paramIntent.getBooleanExtra("key_to_chat", false))
            {
                Intent localIntent = new Intent(this, MJTChatActivity.class);
                localIntent.putExtras(paramIntent.getExtras());
                startActivity(localIntent);
                this.mMainTabRadioGroup.check(2131231151);
                return;
            }
        } while (!paramIntent.getBooleanExtra("key_to_share", false));
        paramIntent.setComponent(new ComponentName(this, ShareActivity.class));
        paramIntent.setAction("android.intent.action.SEND");
        startActivity(paramIntent);
    }

    protected void onPause()
    {
        super.onPause();
        this.isPause = true;
    }

    protected void onResume()
    {
        super.onResume();
        Intent localIntent = getIntent();
        Applog.systemOut("----onResume---- " + localIntent);
        if (handleAllLogout(localIntent)) {}
        do
        {
            do
            {
                return;
                if ((this.mLastFragment != null) && (this.mLastFragment == this.dialerFragment)) {
                    this.dialerFragment.setUserVisibleHint(true);
                }
            } while ((MjtApplication.getInstance().getLoginUser() == null) || (MjtApplication.getInstance().getLoginUser().isErrorUser()));
            this.isPause = false;
            if (this.mMsgNumNeedUpdate) {
                initMsgUnReadTips(MjtApplication.getInstance().getLoginUser().getUserId());
            }
            refreshUnread();
            if ((System.currentTimeMillis() - UserSpUtil.getInstanse().getIpCheckDate() >= 86400000L) && (NetworkUtil.isConnected(this))) {
                CachedThreadPoolUtils.getInstance().execute(new MJTMainActivity.1(this));
            }
            if ((System.currentTimeMillis() - UserSpUtil.getInstanse().getServiceInfoDate() >= 86400000L) && (NetworkUtil.isConnected(this))) {
                HttpsClient.getInstanse().getServiceInfo(new MJTMainActivity.2(this));
            }
            if (System.currentTimeMillis() - AppSpUtil.getInstanse().getChatMessageFireDate() >= 86400000L) {
                CachedThreadPoolUtils.getInstance().execute(new MJTMainActivity.3(this));
            }
        } while ((System.currentTimeMillis() - AppSpUtil.getInstanse().getVersionCheckData() < 86400000L) || (ChannelUtil.isUsedTEE));
        HttpsClient.getInstanse().checkUpdate(new MJTMainActivity.4(this));
    }

    protected void onStop()
    {
        super.onStop();
    }

    public void removeNeedUserFragment()
    {
        FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (this.messageFragment != null) {
            localFragmentTransaction.remove(this.messageFragment);
        }
        if (this.contactsFragment != null) {
            localFragmentTransaction.remove(this.contactsFragment);
        }
        if (this.dialerFragment != null) {
            localFragmentTransaction.remove(this.dialerFragment);
        }
        if (this.mineFragment != null) {
            localFragmentTransaction.remove(this.mineFragment);
        }
        localFragmentTransaction.commitAllowingStateLoss();
        this.messageFragment = null;
        this.contactsFragment = null;
        this.dialerFragment = null;
        this.mineFragment = null;
        this.mLastFragment = null;
    }

    protected void setPresenter()
    {
        this.presenter = new MJTMainPresenter(this);
    }
}

