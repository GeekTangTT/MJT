package com.olym.mjt.module.contacts.selectcontacts;

import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.olym.mjt.base.activity.BasePresenterActivity;
import com.olym.mjt.config.Constants;
import com.olym.mjt.database.dao.FriendDao;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.MucRoomSimple;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.im.IMService;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.chat.MJTChatActivity;
import com.olym.mjt.module.message.event.UpdateGroupContactsEvent;
import com.olym.mjt.module.message.view.IconSearchView;
import com.olym.mjt.network.client.HttpsClient;
import com.olym.mjt.utils.StatusBarUtil;
import com.olym.mjt.utils.TimeUtils;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.sortlist.BaseSortModel;
import com.olym.mjt.widget.sortlist.SideBar;
import com.zhy.autolayout.AutoLinearLayout;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427429)
public class SelectContactsActivity
        extends BasePresenterActivity<SelectContactsPresenter>
        implements ISelectContactsView
{
    public static final int request_code = 4129;
    public static final int result_code = 4130;
    private ArrayList<BaseSortModel<Friend>> datas = new ArrayList();
    @ViewInject(2131230967)
    private AutoLinearLayout horizontalAbort;
    private ArrayList<BaseSortModel<Friend>> horizontaldatas = new ArrayList();
    private boolean isCreateRoom = false;
    private boolean isInViteRoom = false;
    private boolean mBind;
    private ServiceConnection mConnection = new SelectContactsActivity.8(this);
    private List<String> mExistIds;
    private String mLoginUserId;
    private String mRoomDes;
    private String mRoomId;
    private String mRoomJid;
    private String mRoomName;
    @ViewInject(2131231340)
    private IconSearchView mSearchView;
    private SelectContectLVAdapter mSelectContectLVAdapter;
    private IMService mService;
    @ViewInject(2131231346)
    private TextView selectContectCancle;
    @ViewInject(2131231347)
    private TextView selectContectConfirm;
    @ViewInject(2131231348)
    private TextView selectContectContent;
    @ViewInject(2131231349)
    private PullToRefreshListView selectContectListview;
    @ViewInject(2131231362)
    private SideBar sidebar;
    private int totalConnecter = 0;

    private void createGroupChat(String paramString1, String paramString2, String paramString3)
    {
        Object localObject = MjtApplication.getInstance().getLoginUser().getNickName();
        paramString2 = this.mService.createMucRoom((String)localObject, paramString1, paramString2, paramString3);
        if (TextUtils.isEmpty(paramString2))
        {
            ToastUtils.showLongToast(2131690152);
            this.isCreateRoom = false;
            return;
        }
        localObject = new ArrayList();
        int i = 0;
        while (i < this.horizontaldatas.size())
        {
            ((List)localObject).add(((Friend)((BaseSortModel)this.horizontaldatas.get(i)).getBean()).getUserId());
            i += 1;
        }
        HttpsClient.getInstanse().createGroupChat(paramString2, paramString1, paramString3, (List)localObject, new SelectContactsActivity.9(this, paramString2, paramString1, paramString3));
    }

    private void createRoomSuccess(String paramString1, String paramString2, String paramString3, String paramString4)
    {
        String str = MjtApplication.getInstance().getLoginUser().getUserId();
        Object localObject = new Friend();
        ((Friend)localObject).setOwnerId(str);
        ((Friend)localObject).setUserId(paramString2);
        ((Friend)localObject).setNickName(paramString3);
        ((Friend)localObject).setDescription(paramString4);
        ((Friend)localObject).setRoomFlag(1);
        ((Friend)localObject).setRoomId(paramString1);
        ((Friend)localObject).setRoomCreateUserId(str);
        ((Friend)localObject).setTimeSend(TimeUtils.sk_time_current_time());
        ((Friend)localObject).setStatus(2);
        FriendDao.getInstance().createOrUpdateFriend((Friend)localObject);
        localObject = new MucRoomSimple();
        ((MucRoomSimple)localObject).setId(paramString1);
        ((MucRoomSimple)localObject).setJid(paramString2);
        ((MucRoomSimple)localObject).setName(paramString3);
        ((MucRoomSimple)localObject).setDesc(paramString4);
        ((MucRoomSimple)localObject).setUserId(str);
        ((MucRoomSimple)localObject).setTimeSend(TimeUtils.sk_time_current_time());
        paramString4 = JSON.toJSONString(localObject);
        Log.d("roamer", "reason:" + paramString4);
        paramString1 = new String[this.horizontaldatas.size()];
        int i = 0;
        while (i < this.horizontaldatas.size())
        {
            str = ((Friend)((BaseSortModel)this.horizontaldatas.get(i)).getBean()).getUserId();
            paramString1[i] = str;
            this.mService.invite(paramString2, str, paramString4);
            i += 1;
        }
        paramString4 = new Intent(this, MJTChatActivity.class);
        paramString4.putExtra("userId", paramString2);
        paramString4.putExtra("nickName", paramString3);
        paramString4.putExtra("isGroupChat", true);
        paramString4.putExtra(Constants.GROUP_JOIN_NOTICE, paramString1);
        startActivity(paramString4);
        UpdateGroupContactsEvent.post(new UpdateGroupContactsEvent());
        finish();
    }

    private void showCreateGroupChatDialog()
    {
        this.isCreateRoom = true;
        if ((this.mService == null) || (!this.mService.isMucEnable()))
        {
            ToastUtils.showLongToast(2131690247);
            this.isCreateRoom = false;
            return;
        }
        Object localObject2 = MjtApplication.getInstance().getLoginUser().getNickName();
        Object localObject1 = localObject2;
        if (localObject2 == null) {
            localObject1 = UserSpUtil.getInstanse().getPhone();
        }
        getString(2131689691);
        switch (this.horizontaldatas.size())
        {
            default:
                localObject2 = (BaseSortModel)this.horizontaldatas.get(0);
                BaseSortModel localBaseSortModel = (BaseSortModel)this.horizontaldatas.get(1);
                localObject1 = (String)localObject1 + getResources().getString(2131689824) + ((Friend)((BaseSortModel)localObject2).getBean()).getNickName() + getResources().getString(2131689825) + ((Friend)localBaseSortModel.getBean()).getNickName() + getResources().getString(2131689826);
        }
        for (;;)
        {
            createGroupChat((String)localObject1, null, getString(2131690265));
            return;
            continue;
            localObject2 = (BaseSortModel)this.horizontaldatas.get(0);
            localObject1 = (String)localObject1 + getResources().getString(2131689824) + ((Friend)((BaseSortModel)localObject2).getBean()).getNickName() + getResources().getString(2131689826);
        }
    }

    public void destroy()
    {
        if (this.mBind) {
            unbindService(this.mConnection);
        }
    }

    public void handleBundle(Bundle paramBundle) {}

    public void init()
    {
        StatusBarUtil.setStatusBar(this);
        this.mLoginUserId = MjtApplication.getInstance().getLoginUser().getUserId();
        this.datas.addAll(((SelectContactsPresenter)this.presenter).getDatas());
        this.mSelectContectLVAdapter = new SelectContectLVAdapter(this, this.datas, new SelectContactsActivity.1(this));
        ((ListView)this.selectContectListview.getRefreshableView()).setDividerHeight(0);
        this.selectContectListview.setAdapter(this.mSelectContectLVAdapter);
        this.selectContectListview.setOnScrollListener(new SelectContactsActivity.2(this));
        this.sidebar.setOnTouchingLetterChangedListener(new SelectContactsActivity.3(this));
        this.mSearchView.setOnIconRemoveListener(new SelectContactsActivity.4(this));
        this.mSearchView.addTextChangedListener(new SelectContactsActivity.5(this));
        this.selectContectCancle.setOnClickListener(new SelectContactsActivity.6(this));
        this.selectContectConfirm.setOnClickListener(new SelectContactsActivity.7(this));
        this.mBind = bindService(new Intent(this, IMService.class), this.mConnection, 1);
    }

    public void inviteFriendSuccess()
    {
        setResult(4130);
        finish();
    }

    public void isUpdateInviteRoom(boolean paramBoolean)
    {
        this.isInViteRoom = paramBoolean;
    }

    public void onDestroy()
    {
        super.onDestroy();
    }

    protected void setPresenter()
    {
        if (getIntent() != null)
        {
            this.mRoomId = getIntent().getStringExtra("roomId");
            this.mRoomJid = getIntent().getStringExtra("roomJid");
            this.mRoomDes = getIntent().getStringExtra("roomDes");
            this.mRoomName = getIntent().getStringExtra("roomName");
            this.mExistIds = JSON.parseArray(getIntent().getStringExtra("exist_ids"), String.class);
            if (this.mExistIds != null) {}
            for (this.presenter = new SelectContactsPresenter(this, this.mExistIds);; this.presenter = new SelectContactsPresenter(this))
            {
                ((SelectContactsPresenter)this.presenter).setmRoomId(this.mRoomId);
                ((SelectContactsPresenter)this.presenter).setmRoomJid(this.mRoomJid);
                ((SelectContactsPresenter)this.presenter).setmRoomDes(this.mRoomDes);
                ((SelectContactsPresenter)this.presenter).setmRoomName(this.mRoomName);
                ((SelectContactsPresenter)this.presenter).setmExistIds(this.mExistIds);
                ((SelectContactsPresenter)this.presenter).setmLoginUserId(this.mLoginUserId);
                return;
            }
        }
        this.presenter = new SelectContactsPresenter(this);
    }

    public void updateDates() {}

    public void updateHorizontanList() {}

    public void updateSelectContectList() {}
}

