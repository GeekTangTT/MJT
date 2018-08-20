package com.olym.mjt.module.dial;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.olym.mjt.base.fragment.BasePresenterFragment;
import com.olym.mjt.databean.event.CallMissedNumEvent;
import com.olym.mjt.databean.event.LocalContactsLoadEvent;
import com.olym.mjt.databean.event.ShowNotificationEvent;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.module.calllog.CallItem;
import com.olym.mjt.module.calllog.CallItem.ExtraItem;
import com.olym.mjt.module.calllog.CallLogAdapter;
import com.olym.mjt.module.calllog.CallLogHelper;
import com.olym.mjt.module.user.ibcdomain.ActionSheetDialog;
import com.olym.mjt.pjsip.sip.api.SipManager;
import com.olym.mjt.pjsip.sip.event.CallEvent;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.DialingFeedback;
import com.olym.mjt.utils.EventBusUtil;
import com.olym.mjt.utils.PhoneNumberUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.DialerCallBar;
import com.olym.mjt.widget.DialerCallBar.OnDialActionListener;
import com.olym.mjt.widget.Dialpad;
import com.olym.mjt.widget.Dialpad.OnDialKeyListener;
import com.olym.mjt.widget.DigitsEditText;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import com.olym.mjt.widget.swipeview.SwipeMenuCreator;
import com.olym.mjt.widget.swipeview.SwipeMenuListView;
import java.util.ArrayList;
import java.util.Iterator;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427504)
public class DialFragment
        extends BasePresenterFragment<DialPresenter>
        implements IDialView, LoaderManager.LoaderCallbacks<Cursor>
{
    private static final int LOADER_ID = 1000;
    private CallLogAdapter callLogAdapter;
    private String currentCallPhone = null;
    private ArrayList<CallItem> datas;
    private TipsDialog deleteCalllogTipsDialog;
    @ViewInject(2131230905)
    private TextView dial_title;
    @ViewInject(2131230906)
    private DialerCallBar dialerCallBar;
    private DialingFeedback dialingFeedback;
    private ActionSheetDialog dialog;
    @ViewInject(2131230904)
    private Dialpad dialpad;
    @ViewInject(2131230908)
    private DigitsEditText digitsText;
    private Handler handler = new Handler();
    private boolean isNormalmode = true;
    @ViewInject(2131231056)
    private ImageView iv_show_keyboard;
    private TranslateAnimation keyboardHideAnimation;
    private TranslateAnimation keyboardShowAnimation;
    @ViewInject(2131231074)
    private SwipeMenuListView listview;
    @ViewInject(2131231077)
    private LinearLayout ll_add_local_contact;
    @ViewInject(2131231082)
    private View ll_call;
    @ViewInject(2131231083)
    private View ll_call_phone_et;
    private SwipeMenuCreator menuCreator = new DialFragment.11(this);
    private DialerCallBar.OnDialActionListener onDialActionListener = new DialFragment.13(this);
    private Dialpad.OnDialKeyListener onDialKeyListener = new DialFragment.12(this);
    private PhoneNumberAdapter phoneNumberAdapter;
    private AnimationSet showButtonAnimation;
    @ViewInject(2131231526)
    private View v_add_local_contact_line;
    @ViewInject(2131231530)
    private View v_delete;

    private void deleteAll()
    {
        Object localObject1 = new StringBuilder();
        if (this.datas != null)
        {
            Iterator localIterator = this.datas.iterator();
            while (localIterator.hasNext())
            {
                CallItem localCallItem = (CallItem)localIterator.next();
                Object localObject2 = localCallItem.getExtraItems();
                if (localObject2 != null)
                {
                    localObject2 = ((ArrayList)localObject2).iterator();
                    while (((Iterator)localObject2).hasNext())
                    {
                        CallItem.ExtraItem localExtraItem = (CallItem.ExtraItem)((Iterator)localObject2).next();
                        ((StringBuilder)localObject1).append(localExtraItem.getId() + ",");
                    }
                }
                ((StringBuilder)localObject1).append(localCallItem.getId() + ",");
            }
        }
        if (((StringBuilder)localObject1).length() != 0)
        {
            localObject1 = ((StringBuilder)localObject1).substring(0, ((StringBuilder)localObject1).length() - 1);
            getContext().getContentResolver().delete(SipManager.CALLLOG_URI, "_id in (" + (String)localObject1 + ")", null);
        }
    }

    private void initShowButtonAnimation()
    {
        this.showButtonAnimation = new AnimationSet(false);
        RotateAnimation localRotateAnimation = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
        localRotateAnimation.setFillAfter(true);
        localRotateAnimation.setDuration(350L);
        ScaleAnimation localScaleAnimation = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F, 1, 0.5F, 1, 0.5F);
        localScaleAnimation.setFillAfter(true);
        localScaleAnimation.setDuration(350L);
        AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
        localAlphaAnimation.setFillAfter(true);
        localAlphaAnimation.setDuration(350L);
        this.showButtonAnimation.addAnimation(localRotateAnimation);
        this.showButtonAnimation.addAnimation(localScaleAnimation);
        this.showButtonAnimation.addAnimation(localAlphaAnimation);
        this.keyboardHideAnimation = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.0F, 1, 1.0F);
        this.keyboardHideAnimation.setDuration(300L);
        this.keyboardHideAnimation.setAnimationListener(new DialFragment.14(this));
        this.keyboardShowAnimation = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 1.0F, 1, 0.0F);
        this.keyboardShowAnimation.setDuration(300L);
    }

    private void keyPress(int paramInt)
    {
        KeyEvent localKeyEvent = new KeyEvent(0, paramInt);
        this.digitsText.onKeyDown(paramInt, localKeyEvent);
    }

    private void placeCallWithOption(Bundle paramBundle)
    {
        paramBundle = this.digitsText.getText().toString();
        if (TextUtils.isEmpty(paramBundle)) {
            return;
        }
        String str = PhoneNumberUtil.parse(paramBundle);
        paramBundle = str;
        if (str.contains("*")) {
            paramBundle = EngineUtils.getInstance().decryptPhoneNumber(str);
        }
        if (ChannelUtil.currentChannel == 105)
        {
            this.currentCallPhone = paramBundle;
            this.dialog.show();
            return;
        }
        this.digitsText.setText("");
        CallEvent.post(new CallEvent(paramBundle));
    }

    private void setCallMode(String paramString)
    {
        this.ll_call_phone_et.setVisibility(0);
        this.isNormalmode = false;
        this.listview.setMenuCreator(null);
        this.listview.setAdapter(this.phoneNumberAdapter);
        this.phoneNumberAdapter.getFilter().filter(PhoneNumberUtils.stripSeparators(paramString));
    }

    private void setNormalMode()
    {
        if (this.callLogAdapter != null)
        {
            this.ll_call_phone_et.setVisibility(8);
            this.isNormalmode = true;
            this.listview.setAdapter(this.callLogAdapter);
            this.listview.setMenuCreator(this.menuCreator);
        }
    }

    private void showDeleleCalllogDialog()
    {
        if (this.deleteCalllogTipsDialog == null) {
            this.deleteCalllogTipsDialog = new TipsDialog.Build(getContext()).setCancelable(true).setContent(getResources().getString(2131689719)).setDialogClickListener(new DialFragment.9(this)).build();
        }
        this.deleteCalllogTipsDialog.show();
    }

    private void showOrHideKeyboard()
    {
        if (this.ll_call.getVisibility() == 0)
        {
            this.ll_call.setVisibility(8);
            this.ll_call.startAnimation(this.keyboardHideAnimation);
            return;
        }
        this.iv_show_keyboard.setVisibility(8);
        this.ll_call.setVisibility(0);
        this.ll_call.startAnimation(this.keyboardShowAnimation);
    }

    public void call(String paramString)
    {
        String str = PhoneNumberUtil.parse(paramString);
        paramString = str;
        if (str.contains("*")) {
            paramString = EngineUtils.getInstance().decryptPhoneNumber(str);
        }
        if (ChannelUtil.currentChannel == 105)
        {
            this.currentCallPhone = paramString;
            this.dialog.show();
            return;
        }
        this.digitsText.setText("");
        CallEvent.post(new CallEvent(paramString));
    }

    public void destroy()
    {
        EventBusUtil.unregister(this);
        this.dialingFeedback.pause();
        getLoaderManager().destroyLoader(1000);
    }

    public void handleArguments() {}

    @Subscribe(sticky=true, threadMode=ThreadMode.MAIN)
    public void handleLocalContactsLoad(LocalContactsLoadEvent paramLocalContactsLoadEvent)
    {
        ((DialPresenter)this.presenter).setLocalContacts();
        if (this.ll_call_phone_et.getVisibility() == 0) {
            setCallMode(this.digitsText.getText().toString().trim());
        }
    }

    public void init()
    {
        if (ChannelUtil.currentChannel == 102) {
            this.dial_title.setText(getResources().getString(2131690112));
        }
        for (;;)
        {
            EventBusUtil.register(this);
            String[] arrayOfString = new String[2];
            arrayOfString[0] = "拨打普通电话";
            arrayOfString[1] = "拨打加密电话";
            this.dialog = new ActionSheetDialog(getContext(), arrayOfString, null);
            this.dialog.isTitleShow(false);
            this.dialog.setOnOperItemClickL(new DialFragment.1(this, arrayOfString));
            this.dialingFeedback = new DialingFeedback(this.activity, false);
            this.dialingFeedback.resume();
            this.dialpad.setOnDialKeyListener(this.onDialKeyListener);
            this.dialerCallBar.setOnDialActionListener(this.onDialActionListener);
            this.digitsText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
            this.ll_call_phone_et.setVisibility(8);
            this.iv_show_keyboard.setOnClickListener(new DialFragment.2(this));
            this.dialerCallBar.setEnabled(false);
            getLoaderManager().initLoader(1000, null, this);
            initShowButtonAnimation();
            this.listview.setDividerHeight(0);
            this.listview.setOnScrollListener(new DialFragment.3(this));
            this.listview.setOnMenuItemClickListener(new DialFragment.4(this));
            this.listview.setOnItemClickListener(new DialFragment.5(this));
            ((DialPresenter)this.presenter).setLocalContacts();
            this.phoneNumberAdapter = new PhoneNumberAdapter(getContext(), ((DialPresenter)this.presenter).getLocalContacts(), (DialPresenter)this.presenter);
            this.ll_add_local_contact.setVisibility(0);
            this.v_add_local_contact_line.setVisibility(0);
            this.ll_add_local_contact.setOnClickListener(new DialFragment.6(this));
            this.v_delete.setOnClickListener(new DialFragment.7(this));
            if (ChannelUtil.currentChannel == 102) {
                this.v_delete.setVisibility(8);
            }
            this.digitsText.addTextChangedListener(new DialFragment.8(this));
            return;
            this.dial_title.setText(getResources().getString(2131690111));
        }
    }

    public Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
    {
        return new CursorLoader(this.activity, SipManager.CALLLOG_URI, new String[] { "_id", "name", "numberlabel", "numbertype", "duration", "date", "new", "number", "type", "account_id" }, null, null, "date DESC");
    }

    public void onHiddenChanged(boolean paramBoolean)
    {
        super.onHiddenChanged(paramBoolean);
        if (!paramBoolean)
        {
            ShowNotificationEvent.post(new ShowNotificationEvent(9));
            UserSpUtil.getInstanse().setCallMissedNumClear();
            CallMissedNumEvent.post(new CallMissedNumEvent());
            getLoaderManager().initLoader(1000, null, this);
        }
    }

    public void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
    {
        this.datas = CallLogHelper.handleCursorNew(paramCursor);
        this.callLogAdapter = new CallLogAdapter(this.activity, this.datas);
        if (this.ll_call_phone_et.getVisibility() == 8) {
            setNormalMode();
        }
    }

    public void onLoaderReset(Loader<Cursor> paramLoader) {}

    public void onStart()
    {
        super.onStart();
    }

    protected void setPresenter()
    {
        this.presenter = new DialPresenter(this);
    }

    public void setUserVisibleHint(boolean paramBoolean)
    {
        super.setUserVisibleHint(paramBoolean);
        Applog.systemOut("------Dial----setUserVisibleHint-- " + paramBoolean);
        if (paramBoolean) {
            this.handler.postDelayed(new DialFragment.10(this), 500L);
        }
    }
}

