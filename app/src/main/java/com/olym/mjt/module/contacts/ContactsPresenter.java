package com.olym.mjt.module.contacts;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.olym.mjt.base.presenter.BasePresenter;
import com.olym.mjt.database.dao.FriendDao;
import com.olym.mjt.database.dao.FriendHelper;
import com.olym.mjt.database.dao.LocalContactDao;
import com.olym.mjt.database.dao.NewFriendDao;
import com.olym.mjt.databean.bean.AttentionUser;
import com.olym.mjt.databean.bean.ContactResultBean;
import com.olym.mjt.databean.bean.ContactResultBean.User_list;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.LocalContact;
import com.olym.mjt.databean.bean.NewFriendMessage;
import com.olym.mjt.databean.bean.PhoneContactsBean;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.DataReadyEvent;
import com.olym.mjt.databean.event.LocalContactsLoadEvent;
import com.olym.mjt.im.IMService;
import com.olym.mjt.init.EngineUtils;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.event.CardUIUpdateEvent;
import com.olym.mjt.module.message.event.MessageMsgUiUpdateEvent;
import com.olym.mjt.module.message.event.MsgNumRefreshEvent;
import com.olym.mjt.module.message.event.MsgNumUpdateEvent;
import com.olym.mjt.network.client.FastJsonBaseResponseCallback;
import com.olym.mjt.network.client.FastJsonObjectCallback;
import com.olym.mjt.network.client.HttpsClient;
import com.olym.mjt.network.responsedata.AddFriendResponseBean;
import com.olym.mjt.network.responsedata.AddFriendResponseBean.Type;
import com.olym.mjt.network.responsedata.SipResponseBean;
import com.olym.mjt.utils.Applog;
import com.olym.mjt.utils.CachedThreadPoolUtils;
import com.olym.mjt.utils.CharacterParser;
import com.olym.mjt.utils.ContactsUtils;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.utils.sharedpreferencesutils.AppSpUtil;
import com.olym.mjt.utils.sharedpreferencesutils.UserSpUtil;
import com.olym.mjt.widget.sortlist.BaseComparator;
import com.olym.mjt.widget.sortlist.BaseSortModel;
import com.olym.mjt.widget.sortlist.PingYinUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import okhttp3.Call;

public class ContactsPresenter
        extends BasePresenter
{
    private List<BaseSortModel<Friend>> datas = new ArrayList();
    private IContactsView iContactsView;
    private Runnable initDataFromPhoneContacts = new Runnable()
    {
        public void run()
        {
            Applog.systemOut("---Contacts---initDataFromPhoneContacts------");
            if (AppSpUtil.getInstanse().getClearDatas())
            {
                LocalContactDao.getInstance().deleteAll();
                FriendDao.getInstance().deleteAll();
                AppSpUtil.getInstanse().setClearDatas(false);
            }
            String str = UserSpUtil.getInstanse().getPhone();
            final PhoneContactsBean localPhoneContactsBean = ContactsUtils.getPhoneContactsAndSava(MjtApplication.getInstance());
            LocalContactsLoadEvent.post(new LocalContactsLoadEvent());
            HttpsClient.getInstanse().uploadContacts(str, localPhoneContactsBean.getPostData(), new FastJsonObjectCallback()
            {
                public void onError(Call paramAnonymous2Call, Exception paramAnonymous2Exception, int paramAnonymous2Int)
                {
                    CachedThreadPoolUtils.getInstance().execute(ContactsPresenter.this.initDataFromPhoneContacts);
                }

                public void onResponse(SipResponseBean paramAnonymous2SipResponseBean, int paramAnonymous2Int)
                {
                    if (paramAnonymous2SipResponseBean.getCode() == 0)
                    {
                        CachedThreadPoolUtils.getInstance().execute(new ContactsPresenter.HandleUploadResponse(ContactsPresenter.this, paramAnonymous2SipResponseBean, localPhoneContactsBean.getLocalContactList()));
                        return;
                    }
                    ToastUtils.showShortToastSafe(2131690164);
                    CachedThreadPoolUtils.getInstance().execute(ContactsPresenter.this.initDataFromPhoneContacts);
                }
            });
        }
    };
    private BaseComparator<Friend> mBaseComparator = new BaseComparator();
    private IMService mXmppService;

    public ContactsPresenter(IContactsView paramIContactsView)
    {
        this.iContactsView = paramIContactsView;
        loadData();
    }

    private List<AttentionUser> filledData(HashMap<String, String> paramHashMap, List<ContactResultBean.User_list> paramList)
    {
        ArrayList localArrayList = new ArrayList();
        if (paramList != null)
        {
            int i = 0;
            if (i < paramList.size())
            {
                Object localObject = (ContactResultBean.User_list)paramList.get(i);
                AttentionUser localAttentionUser = new AttentionUser();
                localAttentionUser.setUserId(MjtApplication.getInstance().getLoginUser().getUserId());
                if (!TextUtils.isEmpty(((ContactResultBean.User_list)localObject).getTigase_user_status()))
                {
                    localAttentionUser.setStatus(Integer.parseInt(((ContactResultBean.User_list)localObject).getTigase_user_status()));
                    label84:
                    localAttentionUser.setBlacklist(((ContactResultBean.User_list)localObject).getBlacklist());
                    localAttentionUser.setToUserId(((ContactResultBean.User_list)localObject).getUserId());
                    localAttentionUser.setToTelephone(((ContactResultBean.User_list)localObject).getUser());
                    localAttentionUser.setToNickName(((ContactResultBean.User_list)localObject).getTigase_user_nickname());
                    localAttentionUser.setDomain(((ContactResultBean.User_list)localObject).getDomain());
                    localAttentionUser.setVersion(((ContactResultBean.User_list)localObject).getVersion());
                    localObject = LocalContactDao.getInstance().getLocalContactsFromPhone(((ContactResultBean.User_list)localObject).getUser());
                    if (localObject == null) {
                        break label308;
                    }
                    String str = ((LocalContact)localObject).getRemarkName();
                    localAttentionUser.setRemarkName(str);
                    localAttentionUser.setLocalId(((LocalContact)localObject).getLocalId());
                    if (localAttentionUser.getToNickName().equals(localAttentionUser.getToTelephone())) {
                        break label295;
                    }
                    ((LocalContact)localObject).setNickName(localAttentionUser.getToNickName());
                    if (TextUtils.isEmpty(str))
                    {
                        ((LocalContact)localObject).setWholeSpell(new CharacterParser().getSellingWithPolyphone(((LocalContact)localObject).getNickName()));
                        ((LocalContact)localObject).setSimplespell(new CharacterParser().getSimpleSellingPolyphone(((LocalContact)localObject).getNickName()));
                    }
                    LocalContactDao.getInstance().createOrUpdateContact((LocalContact)localObject);
                }
                for (;;)
                {
                    localArrayList.add(localAttentionUser);
                    i += 1;
                    break;
                    localAttentionUser.setStatus(3);
                    break label84;
                    label295:
                    localAttentionUser.setToNickName(((LocalContact)localObject).getNickName());
                    continue;
                    label308:
                    localObject = (String)paramHashMap.get(localAttentionUser.getToTelephone());
                    if (!TextUtils.isEmpty((CharSequence)localObject)) {
                        localAttentionUser.setRemarkName((String)localObject);
                    }
                    LocalContactDao.getInstance().addLocalContact(localAttentionUser);
                }
            }
        }
        return localArrayList;
    }

    private void setSortCondition(BaseSortModel<Friend> paramBaseSortModel)
    {
        Object localObject = (Friend)paramBaseSortModel.getBean();
        if (localObject == null) {
            return;
        }
        localObject = ((Friend)localObject).getShowName();
        String str1 = new CharacterParser().getSellingWithPolyphone((String)localObject);
        if (!TextUtils.isEmpty(str1))
        {
            String str2 = Character.toString(str1.charAt(0));
            paramBaseSortModel.setWholeSpell(str1);
            if (PingYinUtil.isNumeric(str2)) {
                paramBaseSortModel.setFirstLetter("#");
            }
            for (;;)
            {
                paramBaseSortModel.setSimpleSpell(new CharacterParser().getSimpleSellingPolyphone((String)localObject));
                return;
                paramBaseSortModel.setFirstLetter(str2);
            }
        }
        paramBaseSortModel.setWholeSpell(str1);
        paramBaseSortModel.setFirstLetter("#");
        paramBaseSortModel.setSimpleSpell(new CharacterParser().getSimpleSellingPolyphone((String)localObject));
    }

    public void addFriend(final Friend paramFriend)
    {
        HttpsClient.getInstanse().addFriend(paramFriend.getUserId(), paramFriend.getDomain(), String.valueOf(1), paramFriend.getToTelephone(), new FastJsonBaseResponseCallback()
        {
            public void onGetDataSuccess(AddFriendResponseBean paramAnonymousAddFriendResponseBean)
            {
                int i = paramAnonymousAddFriendResponseBean.getData().getType();
                Object localObject;
                if ((i == 1) || (i == 3))
                {
                    paramFriend.setStatus(1);
                    paramAnonymousAddFriendResponseBean = NewFriendMessage.createWillSendMessage(MjtApplication.getInstance().getLoginUser(), 503, null, paramFriend);
                    localObject = new Gson().toJson(paramAnonymousAddFriendResponseBean);
                    localObject = (NewFriendMessage)new Gson().fromJson((String)localObject, NewFriendMessage.class);
                    ContactsPresenter.this.mXmppService.sendNewFriendMessage(paramFriend.getUserId() + "@" + paramFriend.getDomain(), (NewFriendMessage)localObject);
                    if (paramFriend != null)
                    {
                        paramAnonymousAddFriendResponseBean.setTelephone(paramFriend.getToTelephone());
                        paramAnonymousAddFriendResponseBean.setIbcdomain(paramFriend.getDomain());
                        if (paramAnonymousAddFriendResponseBean.getUserId().contains("@")) {
                            paramAnonymousAddFriendResponseBean.setUserId(paramAnonymousAddFriendResponseBean.getUserId().replace("@" + paramFriend.getDomain(), ""));
                        }
                        NewFriendDao.getInstance().ascensionNewFriend(paramAnonymousAddFriendResponseBean, 1);
                        ToastUtils.showShortToast(2131689581);
                        CardUIUpdateEvent.post(new CardUIUpdateEvent(paramFriend.getNickName(), paramFriend.getToTelephone()));
                    }
                }
                for (;;)
                {
                    FriendDao.getInstance().updateFriendStatus(MjtApplication.getInstance().getLoginUser().getUserId(), paramFriend.getUserId(), paramFriend.getStatus(), paramFriend.getDomain());
                    ContactsPresenter.this.iContactsView.updateAdapter();
                    return;
                    if (paramFriend == null) {
                        break;
                    }
                    paramAnonymousAddFriendResponseBean.setTelephone(UserSpUtil.getInstanse().getPhone());
                    break;
                    if ((i == 2) || (i == 4))
                    {
                        paramFriend.setStatus(2);
                        paramAnonymousAddFriendResponseBean = NewFriendMessage.createWillSendMessage(MjtApplication.getInstance().getLoginUser(), 508, null, paramFriend);
                        localObject = new Gson().toJson(paramAnonymousAddFriendResponseBean);
                        localObject = (NewFriendMessage)new Gson().fromJson((String)localObject, NewFriendMessage.class);
                        ContactsPresenter.this.mXmppService.sendNewFriendMessage(paramFriend.getUserId() + "@" + paramFriend.getDomain(), (NewFriendMessage)localObject);
                        if (paramFriend != null) {
                            paramAnonymousAddFriendResponseBean.setTelephone(paramFriend.getToTelephone());
                        }
                        for (;;)
                        {
                            paramAnonymousAddFriendResponseBean.setIbcdomain(paramFriend.getDomain());
                            if (paramAnonymousAddFriendResponseBean.getUserId().contains("@")) {
                                paramAnonymousAddFriendResponseBean.setUserId(paramAnonymousAddFriendResponseBean.getUserId().replace("@" + paramFriend.getDomain(), ""));
                            }
                            NewFriendDao.getInstance().ascensionNewFriend(paramAnonymousAddFriendResponseBean, 2);
                            FriendHelper.addFriendExtraOperation(MjtApplication.getInstance().getLoginUser().getUserId(), paramFriend.getUserId(), paramFriend.getDomain());
                            ToastUtils.showShortToast(2131689586);
                            MsgNumUpdateEvent.post(new MsgNumUpdateEvent(1, 1));
                            MsgNumRefreshEvent.post(new MsgNumRefreshEvent("10001", paramFriend.getDomain()));
                            MessageMsgUiUpdateEvent.post(new MessageMsgUiUpdateEvent());
                            break;
                            if (paramFriend != null) {
                                paramAnonymousAddFriendResponseBean.setTelephone(UserSpUtil.getInstanse().getPhone());
                            }
                        }
                    }
                    if (i == 5) {
                        ToastUtils.showShortToast(2131689584);
                    }
                }
            }
        });
    }

    public List<BaseSortModel<Friend>> getDatas()
    {
        return this.datas;
    }

    public void loadData()
    {
        if ((!AppSpUtil.getInstanse().getInitContacts()) || (AppSpUtil.getInstanse().getClearDatas()))
        {
            CachedThreadPoolUtils.getInstance().execute(this.initDataFromPhoneContacts);
            return;
        }
        CachedThreadPoolUtils.getInstance().execute(new Runnable()
        {
            public void run()
            {
                ContactsPresenter.this.loadDataFromDB();
            }
        });
    }

    public void loadDataFromDB()
    {
        Applog.systemOut("---Contacts---loadDataFromDB------");
        for (;;)
        {
            ArrayList localArrayList;
            int i;
            synchronized (this.datas)
            {
                List localList2 = FriendDao.getInstance().getFriends(MjtApplication.getInstance().getLoginUser().getUserId());
                if ((localList2 == null) || (localList2.size() <= 0)) {
                    break label222;
                }
                localArrayList = new ArrayList();
                i = 0;
                if (i < localList2.size())
                {
                    Object localObject2 = (Friend)localList2.get(i);
                    if ((((Friend)localObject2).getToTelephone() == null) || (((Friend)localObject2).getToTelephone().equals(MjtApplication.getInstance().getLoginUser().getTelephone())) || ((((Friend)localObject2).getStatus() != 5) && (((Friend)localObject2).getStatus() != 2) && (((Friend)localObject2).getStatus() != -1))) {
                        break label243;
                    }
                    localObject2 = new BaseSortModel();
                    ((BaseSortModel)localObject2).setBean(localList2.get(i));
                    setSortCondition((BaseSortModel)localObject2);
                    localArrayList.add(localObject2);
                }
            }
            this.datas.clear();
            this.datas.addAll(localArrayList);
            Collections.sort(this.datas, this.mBaseComparator);
            this.iContactsView.updateAdapter();
            for (;;)
            {
                return;
                label222:
                this.datas.clear();
                this.iContactsView.updateAdapter();
            }
            label243:
            i += 1;
        }
    }

    public void refreshData()
    {
        CachedThreadPoolUtils.getInstance().execute(this.initDataFromPhoneContacts);
    }

    public void setmImService(IMService paramIMService)
    {
        this.mXmppService = paramIMService;
    }

    public void updateDatas(Friend paramFriend)
    {
        List localList = this.datas;
        int j = 0;
        try
        {
            Object localObject = this.datas.iterator();
            BaseSortModel localBaseSortModel;
            do
            {
                i = j;
                if (!((Iterator)localObject).hasNext()) {
                    break;
                }
                localBaseSortModel = (BaseSortModel)((Iterator)localObject).next();
            } while ((!((Friend)localBaseSortModel.getBean()).getUserId().equals(paramFriend.getUserId())) || (!((Friend)localBaseSortModel.getBean()).getDomain().equals(paramFriend.getDomain())));
            int i = 1;
            localBaseSortModel.setBean(paramFriend);
            setSortCondition(localBaseSortModel);
            Collections.sort(this.datas, this.mBaseComparator);
            this.iContactsView.updateAdapter();
            if (i == 0)
            {
                localObject = new BaseSortModel();
                ((BaseSortModel)localObject).setBean(paramFriend);
                setSortCondition((BaseSortModel)localObject);
                this.datas.add(localObject);
                Collections.sort(this.datas, this.mBaseComparator);
                this.iContactsView.updateAdapter();
            }
            return;
        }
        finally {}
    }

    private class HandleUploadResponse
            implements Runnable
    {
        private ArrayList<LocalContact> localContacts;
        private SipResponseBean response;

        public HandleUploadResponse(ArrayList<LocalContact> paramArrayList)
        {
            this.response = paramArrayList;
            ArrayList localArrayList;
            this.localContacts = localArrayList;
        }

        public void run()
        {
            if (this.response.getCode() == 0)
            {
                Object localObject = (ContactResultBean)JSON.parseObject(EngineUtils.getInstance().getPlainData(UserSpUtil.getInstanse().getPhone(), this.response.getData()), ContactResultBean.class);
                if (localObject != null)
                {
                    HashMap localHashMap = FriendDao.getInstance().deleteAllFriends();
                    UserSpUtil.getInstanse().setVip(((ContactResultBean)localObject).getUser_type());
                    localObject = ContactsPresenter.this.filledData(localHashMap, ((ContactResultBean)localObject).getUser_list());
                    FriendDao.getInstance().addAttentionUsersForSip(MjtApplication.getInstance().getLoginUser().getUserId(), (List)localObject);
                    FriendDao.getInstance().addAttentionUsersForSms(MjtApplication.getInstance().getLoginUser().getUserId(), (List)localObject);
                    if (!AppSpUtil.getInstanse().getInitContacts())
                    {
                        AppSpUtil.getInstanse().setInitContacts(true);
                        DataReadyEvent.post(new DataReadyEvent());
                    }
                    ContactsPresenter.this.loadDataFromDB();
                }
            }
            else
            {
                return;
            }
            ToastUtils.showShortToastSafe(2131690164);
        }
    }
}
