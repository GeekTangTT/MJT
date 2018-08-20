package com.olym.mjt.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.olym.mjt.module.applock.AppLockActivity;
import com.olym.mjt.module.applock.gesture.GestureActivity;
import com.olym.mjt.module.calllog.calllogdetails.CallLogDetailsActivity;
import com.olym.mjt.module.company.CompanyListActivity;
import com.olym.mjt.module.contacts.addfriends.AddFriendActivity;
import com.olym.mjt.module.contacts.addlocalcontacts.AddLocalContactActivity;
import com.olym.mjt.module.contacts.baseinfo.BaseInfoActivity;
import com.olym.mjt.module.contacts.baseinfo.BaseInfoNewActivity;
import com.olym.mjt.module.contacts.companyuserinfo.CompanyUserInfoActivity;
import com.olym.mjt.module.contacts.contactinfo.ContactsInfoActivity;
import com.olym.mjt.module.contacts.contactinfo.ContactsInfoActivity1;
import com.olym.mjt.module.contacts.encryptcontacts.EncryptContactsActivity;
import com.olym.mjt.module.contacts.friendinfo.FriendInfoActivity;
import com.olym.mjt.module.contacts.friendinfonew.FriendInfoMoreActivity;
import com.olym.mjt.module.contacts.friendinfonew.FriendInfoNewActivity;
import com.olym.mjt.module.contacts.groupcontacts.GroupContactsActivity;
import com.olym.mjt.module.contacts.invitecontacts.InviteContactsActivity;
import com.olym.mjt.module.contacts.mucChatInfo.MucChatInfoActivity;
import com.olym.mjt.module.contacts.phonecontacts.PhoneContactsActivity;
import com.olym.mjt.module.contacts.search.SearchActivity;
import com.olym.mjt.module.contacts.searchuser.SearchUserActivity;
import com.olym.mjt.module.contacts.selectcontacts.SelectContactsActivity;
import com.olym.mjt.module.contacts.userlist.UserListActivity;
import com.olym.mjt.module.fileexplorer.FileExplorerQHActivity;
import com.olym.mjt.module.fileexplorer.filecategoryeccry.FileCategoryEncryActivity;
import com.olym.mjt.module.fileexplorer.filecategoryeccry.FileEncryActivity;
import com.olym.mjt.module.gallery.clipphoto.ClipPhotoActivity;
import com.olym.mjt.module.gallery.photofolder.PhotoFolderActivity;
import com.olym.mjt.module.gallery.photoselect.PhotoSelectActivity;
import com.olym.mjt.module.gallery.previewphoto.PreviewPhotoActivity;
import com.olym.mjt.module.main.MJTMainActivity;
import com.olym.mjt.module.message.chat.MJTChatActivity;
import com.olym.mjt.module.message.chat.atothers.AtOthersActivity;
import com.olym.mjt.module.message.chat.sendcard.SendCardActivity;
import com.olym.mjt.module.message.editroom.EditRoomActivity;
import com.olym.mjt.module.message.file.FilePreviewerActivity;
import com.olym.mjt.module.message.newfriend.MJTNewFriendActivity;
import com.olym.mjt.module.message.search.MuchInfoSearchActivity;
import com.olym.mjt.module.qrcode.QRCodeScanActivity;
import com.olym.mjt.module.qrcode.qrcoderesult.QRCodeResultActivity;
import com.olym.mjt.module.settings.SettingsActivity;
import com.olym.mjt.module.settings.aboutus.AboutUsActivity;
import com.olym.mjt.module.settings.keeplivesettings.FloatActivity;
import com.olym.mjt.module.settings.keeplivesettings.KeepLiveSettingsActivity;
import com.olym.mjt.module.settings.keeplivesettings.webguide.WebGuideActivity;
import com.olym.mjt.module.settings.messagestatus.MessageStatusActivity;
import com.olym.mjt.module.settings.notificationsettings.NotificaitonSettingsActivity;
import com.olym.mjt.module.settings.notificationsettings.selectringtone.SelectRingToneActivity;
import com.olym.mjt.module.settings.notificationsettings.selectsound.SelectSoundActivity;
import com.olym.mjt.module.settings.serviceinfo.ServiceInfoInfoActivity;
import com.olym.mjt.module.user.code.CodeActivity;
import com.olym.mjt.module.user.forgotpassword.ForgotPasswordActivity;
import com.olym.mjt.module.user.info.InfoActivity;
import com.olym.mjt.module.user.login.LoginActivity;
import com.olym.mjt.module.user.qhlogin.QHLoginActivity;
import com.olym.mjt.module.user.register.RegisterActivity;

public class ViewTransferUtil
{
    private static final int enterAnim = 2130771996;
    private static final int exitAnim = 2130771997;

    private static void toActivity(Activity paramActivity, Class<?> paramClass)
    {
        toActivity(paramActivity, paramClass, null, true);
    }

    private static void toActivity(Activity paramActivity, Class<?> paramClass, Bundle paramBundle)
    {
        toActivity(paramActivity, paramClass, paramBundle, true);
    }

    private static void toActivity(Activity paramActivity, Class<?> paramClass, Bundle paramBundle, boolean paramBoolean)
    {
        paramClass = new Intent(paramActivity, paramClass);
        if (paramBundle != null) {
            paramClass.putExtras(paramBundle);
        }
        paramActivity.startActivity(paramClass);
        if (paramBoolean)
        {
            paramActivity.overridePendingTransition(2130771996, 2130771997);
            return;
        }
        paramActivity.overridePendingTransition(0, 0);
    }

    private static void toActivityForResult(int paramInt, Activity paramActivity, Class<?> paramClass)
    {
        toActivityForResult(paramInt, paramActivity, paramClass, null, true);
    }

    private static void toActivityForResult(int paramInt, Activity paramActivity, Class<?> paramClass, Bundle paramBundle)
    {
        toActivityForResult(paramInt, paramActivity, paramClass, paramBundle, true);
    }

    private static void toActivityForResult(int paramInt, Activity paramActivity, Class<?> paramClass, Bundle paramBundle, boolean paramBoolean)
    {
        paramClass = new Intent(paramActivity, paramClass);
        if (paramBundle != null) {
            paramClass.putExtras(paramBundle);
        }
        paramActivity.startActivityForResult(paramClass, paramInt);
        if (paramBoolean) {
            paramActivity.overridePendingTransition(2130771996, 2130771997);
        }
    }

    private static void toActivityForResult(int paramInt, Activity paramActivity, Class<?> paramClass, Bundle paramBundle, boolean paramBoolean1, boolean paramBoolean2)
    {
        paramClass = new Intent(paramActivity, paramClass);
        if (paramBundle != null) {
            paramClass.putExtras(paramBundle);
        }
        paramClass.setFlags(268435456);
        paramActivity.startActivityForResult(paramClass, paramInt);
        if (paramBoolean1) {
            paramActivity.overridePendingTransition(2130771996, 2130771997);
        }
    }

    public static void trandferToNewFriendsActivity(Activity paramActivity)
    {
        toActivity(paramActivity, MJTNewFriendActivity.class);
    }

    public static void transferAppLockActivity(Activity paramActivity)
    {
        toActivity(paramActivity, AppLockActivity.class);
    }

    public static void transferGestureActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, GestureActivity.class, paramBundle, false);
    }

    public static void transferSettingsActivity(Activity paramActivity)
    {
        toActivity(paramActivity, SettingsActivity.class);
    }

    public static void transferToAboutUsActivity(Activity paramActivity)
    {
        toActivity(paramActivity, AboutUsActivity.class);
    }

    public static void transferToAddFriendActivity(Activity paramActivity)
    {
        toActivity(paramActivity, AddFriendActivity.class);
    }

    public static void transferToAddLocalContactActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, AddLocalContactActivity.class, paramBundle);
    }

    public static void transferToAtOthersActivityForResult(int paramInt, Activity paramActivity, Bundle paramBundle)
    {
        toActivityForResult(paramInt, paramActivity, AtOthersActivity.class, paramBundle);
    }

    public static void transferToBaseInfoActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, BaseInfoActivity.class, paramBundle);
    }

    public static void transferToBaseInfoNewActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, BaseInfoNewActivity.class, paramBundle);
    }

    public static void transferToCallLogDetailsActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, CallLogDetailsActivity.class, paramBundle);
    }

    public static void transferToCodeActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, CodeActivity.class, paramBundle);
    }

    public static void transferToCompanyListActivity(Activity paramActivity)
    {
        toActivity(paramActivity, CompanyListActivity.class);
    }

    public static void transferToCompanyUserInfoActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, CompanyUserInfoActivity.class, paramBundle);
    }

    public static void transferToContactsInfoActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, ContactsInfoActivity.class, paramBundle);
    }

    public static void transferToContactsInfoActivity1(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, ContactsInfoActivity1.class, paramBundle);
    }

    public static void transferToEditRoomActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, EditRoomActivity.class, paramBundle);
    }

    public static void transferToEncryptContactsActivity(Activity paramActivity)
    {
        toActivity(paramActivity, EncryptContactsActivity.class);
    }

    public static void transferToFileCategoryEncryActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, FileCategoryEncryActivity.class, paramBundle);
    }

    public static void transferToFileEncryActivity(Activity paramActivity)
    {
        toActivity(paramActivity, FileEncryActivity.class);
    }

    public static void transferToFileExplorerActivity(Activity paramActivity)
    {
        toActivity(paramActivity, FileExplorerQHActivity.class);
    }

    public static void transferToFilePreviewActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, FilePreviewerActivity.class, paramBundle);
    }

    public static void transferToFileReceiveActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, FilePreviewerActivity.class, paramBundle);
    }

    public static void transferToFloatActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, FloatActivity.class, paramBundle, false);
    }

    public static void transferToForgotPasswordActivity(Activity paramActivity)
    {
        toActivity(paramActivity, ForgotPasswordActivity.class);
    }

    public static void transferToFriendInfoActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, FriendInfoActivity.class, paramBundle);
    }

    public static void transferToFriendInfoMoreActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, FriendInfoMoreActivity.class, paramBundle);
    }

    public static void transferToFriendInfoNewActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, FriendInfoNewActivity.class, paramBundle);
    }

    public static void transferToGroupContactsActivity(Activity paramActivity)
    {
        toActivity(paramActivity, GroupContactsActivity.class);
    }

    public static void transferToInfoActivity(Activity paramActivity)
    {
        toActivity(paramActivity, InfoActivity.class);
    }

    public static void transferToInviteContactsActivity(Activity paramActivity)
    {
        toActivity(paramActivity, InviteContactsActivity.class);
    }

    public static void transferToKeepLiveSettingsActivity(Activity paramActivity)
    {
        toActivity(paramActivity, KeepLiveSettingsActivity.class);
    }

    public static void transferToLoginActivity(Activity paramActivity)
    {
        if (ChannelUtil.currentChannel == 106) {
            return;
        }
        toActivity(paramActivity, LoginActivity.class, null, false);
    }

    public static void transferToMJTNewFriendActity(Activity paramActivity)
    {
        toActivity(paramActivity, MJTNewFriendActivity.class);
    }

    public static void transferToMJtChatActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, MJTChatActivity.class, paramBundle);
    }

    public static void transferToMainActivity(Activity paramActivity)
    {
        toActivity(paramActivity, MJTMainActivity.class, null, false);
    }

    public static void transferToMessageStatusActivity(Activity paramActivity)
    {
        toActivity(paramActivity, MessageStatusActivity.class);
    }

    public static void transferToMucChatInfoActivity(Activity paramActivity)
    {
        toActivity(paramActivity, MucChatInfoActivity.class);
    }

    public static void transferToMuchInfoSearchActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, MuchInfoSearchActivity.class, paramBundle);
    }

    public static void transferToNotificationSettingsActivity(Activity paramActivity)
    {
        toActivity(paramActivity, NotificaitonSettingsActivity.class);
    }

    public static void transferToPhonecontactsActivity(Activity paramActivity)
    {
        toActivity(paramActivity, PhoneContactsActivity.class);
    }

    public static void transferToPhonecontactsActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, PhoneContactsActivity.class, paramBundle);
    }

    public static void transferToPhotoClipActivity(int paramInt, Activity paramActivity, Bundle paramBundle)
    {
        toActivityForResult(paramInt, paramActivity, ClipPhotoActivity.class, paramBundle);
    }

    public static void transferToPhotoFolderActivity(int paramInt, Activity paramActivity, Bundle paramBundle)
    {
        toActivityForResult(paramInt, paramActivity, PhotoFolderActivity.class, paramBundle);
    }

    public static void transferToPhotoPreviewActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, PreviewPhotoActivity.class, paramBundle);
    }

    public static void transferToPhotoSelectActivity(int paramInt, Activity paramActivity, Bundle paramBundle)
    {
        toActivityForResult(paramInt, paramActivity, PhotoSelectActivity.class, paramBundle);
    }

    public static void transferToQHLoginActivityAndBundle(Activity paramActivity, Bundle paramBundle)
    {
        if (ChannelUtil.currentChannel == 106) {
            toActivity(paramActivity, QHLoginActivity.class, paramBundle, false);
        }
    }

    public static void transferToQRCodeResultActivity(Activity paramActivity)
    {
        toActivity(paramActivity, QRCodeResultActivity.class);
    }

    public static void transferToQRCodeScanActivity(Activity paramActivity)
    {
        toActivity(paramActivity, QRCodeScanActivity.class);
    }

    public static void transferToRegisterActivity(Activity paramActivity)
    {
        toActivity(paramActivity, RegisterActivity.class);
    }

    public static void transferToRegisterActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, RegisterActivity.class, paramBundle);
    }

    public static void transferToSearchActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, SearchActivity.class, paramBundle);
    }

    public static void transferToSearchUserActivity(Activity paramActivity)
    {
        toActivity(paramActivity, SearchUserActivity.class);
    }

    public static void transferToSelectContectsActivity(Activity paramActivity)
    {
        toActivity(paramActivity, SelectContactsActivity.class);
    }

    public static void transferToSelectRingToneActivity(Activity paramActivity)
    {
        toActivity(paramActivity, SelectRingToneActivity.class);
    }

    public static void transferToSelectSoundActivity(Activity paramActivity)
    {
        toActivity(paramActivity, SelectSoundActivity.class);
    }

    public static void transferToSendCardsActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, SendCardActivity.class, paramBundle);
    }

    public static void transferToServiceInfoActivity(Activity paramActivity)
    {
        toActivity(paramActivity, ServiceInfoInfoActivity.class);
    }

    public static void transferToUserListActivity(Activity paramActivity, Bundle paramBundle)
    {
        toActivity(paramActivity, UserListActivity.class, paramBundle);
    }

    public static void transferToWebGuideActivity(Activity paramActivity)
    {
        toActivity(paramActivity, WebGuideActivity.class);
    }
}
