package com.olym.mjt.module.contacts.addlocalcontacts;

import android.content.res.Resources;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import com.olym.mjt.base.activity.BaseTopbarActivity;
import com.olym.mjt.database.dao.FriendDao;
import com.olym.mjt.database.dao.LocalContactDao;
import com.olym.mjt.databean.bean.AttentionUser;
import com.olym.mjt.databean.bean.ContactResultBean.User_list;
import com.olym.mjt.databean.bean.Friend;
import com.olym.mjt.databean.bean.LocalContact;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.databean.event.LocalContactsLoadEvent;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.message.event.CardUIUpdateEvent;
import com.olym.mjt.network.client.HttpsClient;
import com.olym.mjt.utils.CharacterParser;
import com.olym.mjt.utils.KeyboardHideUtil;
import com.olym.mjt.utils.ToastUtils;
import com.olym.mjt.widget.dialog.LoadingDialog;
import com.olym.mjt.widget.dialog.SimpleDialogClickListener;
import com.olym.mjt.widget.dialog.TipsDialog;
import com.olym.mjt.widget.dialog.TipsDialog.Build;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(2131427356)
public class AddLocalContactActivity
        extends BaseTopbarActivity<AddLocalContactPresenter>
        implements IAddLocalContact
{
    public static final String KEY_NAME = "key_name";
    public static final String KEY_PHONE = "key_phone";
    public static final String KEY_TYPE = "key_type";
    public static final int TYPE_ADD = 1;
    public static final int TYPE_EDIT = 2;
    private TipsDialog deleteTipsDialog;
    @ViewInject(2131230927)
    private EditText et_name;
    @ViewInject(2131230930)
    private EditText et_phone;
    private TipsDialog existTipsDialog;
    private Friend friend;
    private LoadingDialog loadingDialog;
    private LocalContact localContact;
    private String name;
    private String phone;
    @ViewInject(2131231452)
    private TextView tv_delete;
    @ViewInject(2131231499)
    private TextView tv_save;
    private int type;

    private void addContact()
    {
        LocalContact localLocalContact = LocalContactDao.getInstance().getLocalContactsFromPhone(this.phone);
        if (localLocalContact != null)
        {
            if (this.existTipsDialog == null) {
                initExsitTipsDialog(localLocalContact);
            }
            this.existTipsDialog.show();
            return;
        }
        this.loadingDialog.show();
        this.localContact = new LocalContact();
        this.localContact.setNickName(this.name);
        this.localContact.setTelephone(this.phone);
        this.localContact.setRemarkName(this.name);
        this.localContact.setWholeSpell(new CharacterParser().getSellingWithPolyphone(this.name));
        this.localContact.setSimplespell(new CharacterParser().getSimpleSellingPolyphone(this.name));
        HttpsClient.getInstanse().uploadContacts(MjtApplication.getInstance().getLoginUser().getTelephone(), this.phone, new AddLocalContactActivity.4(this));
    }

    private void editContact()
    {
        if (this.friend != null)
        {
            if ((this.friend.getStatus() == 5) || (this.friend.getStatus() == 2) || (this.friend.getStatus() == -1))
            {
                this.loadingDialog.show();
                HttpsClient.getInstanse().remarkFriend(this.friend.getUserId(), this.friend.getDomain(), this.name, new AddLocalContactActivity.3(this));
            }
            FriendDao.getInstance().setRemarkName(MjtApplication.getInstance().getLoginUser().getUserId(), this.friend.getUserId(), this.name, this.friend.getDomain());
        }
        this.localContact.setRemarkName(this.name);
        this.localContact.setWholeSpell(new CharacterParser().getSellingWithPolyphone(this.name));
        this.localContact.setSimplespell(new CharacterParser().getSimpleSellingPolyphone(this.name));
        LocalContactDao.getInstance().createOrUpdateContact(this.localContact);
        ToastUtils.showLongToast(getResources().getString(2131690223));
        CardUIUpdateEvent.post(new CardUIUpdateEvent(this.name, this.phone));
        LocalContactsLoadEvent.post(new LocalContactsLoadEvent());
        finish();
    }

    private List<AttentionUser> filledData(List<ContactResultBean.User_list> paramList)
    {
        ArrayList localArrayList = new ArrayList();
        int i;
        if (paramList != null) {
            i = 0;
        }
        for (;;)
        {
            ContactResultBean.User_list localUser_list;
            if (i < paramList.size())
            {
                localUser_list = (ContactResultBean.User_list)paramList.get(i);
                if (!localUser_list.getUser().equals(this.phone)) {
                    break label214;
                }
                paramList = new AttentionUser();
                paramList.setUserId(MjtApplication.getInstance().getLoginUser().getUserId());
                if (TextUtils.isEmpty(localUser_list.getTigase_user_status())) {
                    break label206;
                }
                paramList.setStatus(Integer.parseInt(localUser_list.getTigase_user_status()));
            }
            for (;;)
            {
                paramList.setBlacklist(localUser_list.getBlacklist());
                paramList.setToUserId(localUser_list.getUserId());
                paramList.setToTelephone(localUser_list.getUser());
                paramList.setToNickName(localUser_list.getTigase_user_nickname());
                paramList.setDomain(localUser_list.getDomain());
                paramList.setVersion(localUser_list.getVersion());
                paramList.setRemarkName(this.name);
                if (!paramList.getToNickName().equals(this.localContact.getNickName()))
                {
                    this.localContact.setNickName(paramList.getToNickName());
                    LocalContactDao.getInstance().createOrUpdateContact(this.localContact);
                }
                localArrayList.add(paramList);
                return localArrayList;
                label206:
                paramList.setStatus(3);
            }
            label214:
            i += 1;
        }
    }

    private void initDeleteTipsDialog()
    {
        if (this.deleteTipsDialog == null) {
            this.deleteTipsDialog = new TipsDialog.Build(this).setContent(getResources().getString(2131689720)).setDialogClickListener(new AddLocalContactActivity.5(this)).build();
        }
    }

    private void initExsitTipsDialog(LocalContact paramLocalContact)
    {
        this.existTipsDialog = new TipsDialog.Build(this).setContent(getResources().getString(2131689728) + paramLocalContact.getNickName()).setSingleChoise(true).setDialogClickListener(new SimpleDialogClickListener()).build();
    }

    public void destroy()
    {
        KeyboardHideUtil.hideKeyboard(this, this.et_name.getWindowToken());
        if (this.type == 1) {
            KeyboardHideUtil.hideKeyboard(this, this.et_phone.getWindowToken());
        }
        if (this.loadingDialog != null) {
            this.loadingDialog.dismiss();
        }
    }

    public void handleBundle(Bundle paramBundle)
    {
        this.phone = paramBundle.getString("key_phone", "");
        this.phone = PhoneNumberUtils.stripSeparators(this.phone);
        this.name = paramBundle.getString("key_name", "");
        this.type = paramBundle.getInt("key_type");
        this.localContact = LocalContactDao.getInstance().getLocalContactsFromPhone(this.phone);
        if (this.localContact != null)
        {
            this.type = 2;
            this.name = this.localContact.getShowName();
            return;
        }
        this.type = 1;
    }

    public void init()
    {
        if (this.type == 1)
        {
            setTitleText(getResources().getString(2131689690));
            this.tv_delete.setVisibility(8);
            KeyboardHideUtil.init(this);
            this.loadingDialog = new LoadingDialog(this);
            this.et_phone.setText(this.phone);
            this.et_name.setFilters(new InputFilter[] { new InputFilter.LengthFilter(35) });
            this.et_name.setText(this.name);
            this.et_name.setSelection(this.et_name.getText().length());
            this.tv_save.setOnClickListener(new AddLocalContactActivity.2(this));
            return;
        }
        setTitleText(getResources().getString(2131689757));
        this.friend = FriendDao.getInstance().getFriendFromPhone(MjtApplication.getInstance().getLoginUser().getUserId(), this.phone);
        if (this.friend == null)
        {
            this.tv_delete.setVisibility(0);
            this.tv_delete.setOnClickListener(new AddLocalContactActivity.1(this));
        }
        for (;;)
        {
            this.et_phone.setEnabled(false);
            break;
            this.tv_delete.setVisibility(8);
        }
    }

    public void onBackPressed()
    {
        super.onBackPressed();
    }

    protected void setPresenter()
    {
        this.presenter = new AddLocalContactPresenter(this);
    }
}

