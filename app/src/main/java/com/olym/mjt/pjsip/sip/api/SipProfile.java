package com.olym.mjt.pjsip.sip.api;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.lc.methodex.LogFinalUtils;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SipProfile
        implements Parcelable
{
    public static final String ACCOUNTS_STATUS_TABLE_NAME = "accounts_status";
    public static final String ACCOUNTS_TABLE_NAME = "accounts";
    public static final String ACCOUNT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.csipsimple.account";
    public static final String ACCOUNT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.csipsimple.account";
    public static final Uri ACCOUNT_ID_URI_BASE;
    public static final String ACCOUNT_STATUS_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.csipsimple.account_status";
    public static final String ACCOUNT_STATUS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.csipsimple.account_status";
    public static final Uri ACCOUNT_STATUS_ID_URI_BASE;
    public static final Uri ACCOUNT_STATUS_URI;
    public static final Uri ACCOUNT_URI = Uri.parse("content://com.olym.mjt.pjsip.sip.db/accounts");
    public static final Parcelable.Creator<SipProfile> CREATOR = new SipProfile.1();
    public static final int CRED_CRED_DATA_EXT_AKA = 2;
    public static final int CRED_DATA_DIGEST = 1;
    public static final int CRED_DATA_PLAIN_PASSWD = 0;
    public static final String CRED_SCHEME_DIGEST = "Digest";
    public static final String CRED_SCHEME_PGP = "PGP";
    public static final String FIELD_ACC_ID = "acc_id";
    public static final String FIELD_ACTIVE = "active";
    public static final String FIELD_ALLOW_CONTACT_REWRITE = "allow_contact_rewrite";
    public static final String FIELD_ALLOW_SDP_NAT_REWRITE = "allow_sdp_nat_rewrite";
    public static final String FIELD_ALLOW_VIA_REWRITE = "allow_via_rewrite";
    public static final String FIELD_ANDROID_GROUP = "android_group";
    public static final String FIELD_AUTH_ALGO = "auth_algo";
    public static final String FIELD_AUTH_INITIAL_AUTH = "initial_auth";
    public static final String FIELD_CONTACT_PARAMS = "contact_params";
    public static final String FIELD_CONTACT_REWRITE_METHOD = "contact_rewrite_method";
    public static final String FIELD_CONTACT_URI_PARAMS = "contact_uri_params";
    public static final String FIELD_DATA = "data";
    public static final String FIELD_DATATYPE = "datatype";
    public static final String FIELD_DEFAULT_URI_SCHEME = "default_uri_scheme";
    public static final String FIELD_DISPLAY_NAME = "display_name";
    public static final String FIELD_FORCE_CONTACT = "force_contact";
    public static final String FIELD_ICE_CFG_ENABLE = "ice_cfg_enable";
    public static final String FIELD_ICE_CFG_USE = "ice_cfg_use";
    public static final String FIELD_ID = "id";
    public static final String FIELD_IPV6_MEDIA_USE = "ipv6_media_use";
    public static final String FIELD_KA_INTERVAL = "ka_interval";
    public static final String FIELD_MEDIA_STUN_USE = "media_stun_use";
    public static final String FIELD_MWI_ENABLED = "mwi_enabled";
    public static final String FIELD_PIDF_TUPLE_ID = "pidf_tuple_id";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_PROXY = "proxy";
    public static final String FIELD_PUBLISH_ENABLED = "publish_enabled";
    public static final String FIELD_REALM = "realm";
    public static final String FIELD_REG_DELAY_BEFORE_REFRESH = "reg_dbr";
    public static final String FIELD_REG_TIMEOUT = "reg_timeout";
    public static final String FIELD_REG_URI = "reg_uri";
    public static final String FIELD_REG_USE_PROXY = "reg_use_proxy";
    public static final String FIELD_RFC5626_INSTANCE_ID = "rfc5626_instance_id";
    public static final String FIELD_RFC5626_REG_ID = "rfc5626_reg_id";
    public static final String FIELD_RTP_BOUND_ADDR = "rtp_bound_addr";
    public static final String FIELD_RTP_ENABLE_QOS = "rtp_enable_qos";
    public static final String FIELD_RTP_PORT = "rtp_port";
    public static final String FIELD_RTP_PUBLIC_ADDR = "rtp_public_addr";
    public static final String FIELD_RTP_QOS_DSCP = "rtp_qos_dscp";
    public static final String FIELD_SCHEME = "scheme";
    public static final String FIELD_SIP_STACK = "sip_stack";
    public static final String FIELD_SIP_STUN_USE = "sip_stun_use";
    public static final String FIELD_TRANSPORT = "transport";
    public static final String FIELD_TRY_CLEAN_REGISTERS = "try_clean_reg";
    public static final String FIELD_TURN_CFG_ENABLE = "turn_cfg_enable";
    public static final String FIELD_TURN_CFG_PASSWORD = "turn_cfg_pwd";
    public static final String FIELD_TURN_CFG_SERVER = "turn_cfg_server";
    public static final String FIELD_TURN_CFG_USE = "turn_cfg_use";
    public static final String FIELD_TURN_CFG_USER = "turn_cfg_user";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_USERPASS = "userpass";
    public static final String FIELD_USE_RFC5626 = "use_rfc5626";
    public static final String FIELD_USE_SRTP = "use_srtp";
    public static final String FIELD_USE_ZRTP = "use_zrtp";
    public static final String FIELD_VID_IN_AUTO_SHOW = "vid_in_auto_show";
    public static final String FIELD_VID_OUT_AUTO_TRANSMIT = "vid_out_auto_transmit";
    public static final String FIELD_VOICE_MAIL_NBR = "vm_nbr";
    public static final String FIELD_VOICE_MAIL_NBR2 = "vm_nbr2";
    public static final String FIELD_VOICE_MAIL_NBR3 = "vm_nbr3";
    public static final String FIELD_VOICE_MAIL_NBR4 = "vm_nbr4";
    public static final String FIELD_VOICE_MAIL_NBR5 = "vm_nbr5";
    public static final String FIELD_VOICE_MAIL_NBR6 = "vm_nbr6";
    public static final String FIELD_VOICE_MAIL_NBR7 = "vm_nbr7";
    public static final String FIELD_VOICE_MAIL_NBR8 = "vm_nbr8";
    public static final String FIELD_VOICE_MAIL_NBR9 = "vm_nbr9";
    public static final String FIELD_WIZARD = "wizard";
    public static final String FIELD_WIZARD_DATA = "wizard_data";
    public static final int GOOGLE_STACK = 1;
    public static final long INVALID_ID = -1L;
    public static final String[] LISTABLE_PROJECTION;
    public static final int PJSIP_STACK = 0;
    public static final String PROXIES_SEPARATOR = "|";
    public static final String SIPHOME_CONTACTFRAGMENT_PHONE = "postdatafrom_siphome_contactfragment";
    private static final String THIS_FILE = "SipProfile";
    public static final int TRANSPORT_AUTO = 0;
    public static final int TRANSPORT_TCP = 2;
    public static final int TRANSPORT_TLS = 3;
    public static final int TRANSPORT_UDP = 1;
    public String acc_id = null;
    public boolean active = true;
    public boolean allow_contact_rewrite = true;
    public boolean allow_sdp_nat_rewrite = false;
    public boolean allow_via_rewrite = false;
    public String android_group = "";
    public String auth_algo = "";
    public int contact_rewrite_method = 2;
    public String data = null;
    public int datatype = 0;
    public String default_uri_scheme = "sip";
    public String display_name = "";
    public String force_contact = null;
    public int ice_cfg_enable = 0;
    public int ice_cfg_use = -1;
    public Bitmap icon = null;
    public long id = -1L;
    public boolean initial_auth = false;
    public int ipv6_media_use = 0;
    public int ka_interval = 0;
    public int media_stun_use = -1;
    public boolean mwi_enabled = true;
    public String pidf_tuple_id = null;
    public int primaryKey = -1;
    public int priority = 100;
    public String[] proxies = null;
    public int publish_enabled = 0;
    public String realm = null;
    public int reg_delay_before_refresh = -1;
    public int reg_timeout = 86400;
    public String reg_uri = null;
    public int reg_use_proxy = 3;
    public String rfc5626_instance_id = "";
    public String rfc5626_reg_id = "";
    public String rtp_bound_addr = "";
    public int rtp_enable_qos = -1;
    public int rtp_port = -1;
    public String rtp_public_addr = "";
    public int rtp_qos_dscp = -1;
    public String scheme = null;
    public int sip_stack = 0;
    public int sip_stun_use = -1;
    public Integer transport = Integer.valueOf(0);
    public int try_clean_registers = 1;
    public int turn_cfg_enable = 0;
    public String turn_cfg_password = "";
    public String turn_cfg_server = "";
    public int turn_cfg_use = -1;
    public String turn_cfg_user = "";
    public boolean use_rfc5626 = true;
    public int use_srtp = -1;
    public int use_zrtp = -1;
    public String username = null;
    public String userpass = null;
    public int vid_in_auto_show = -1;
    public int vid_out_auto_transmit = -1;
    public String vm_nbr = null;
    public String wizard = "EXPERT";
    public String wizard_data = "";

    static
    {
        ACCOUNT_ID_URI_BASE = Uri.parse("content://com.olym.mjt.pjsip.sip.db/accounts/");
        ACCOUNT_STATUS_URI = Uri.parse("content://com.olym.mjt.pjsip.sip.db/accounts_status");
        ACCOUNT_STATUS_ID_URI_BASE = Uri.parse("content://com.olym.mjt.pjsip.sip.db/accounts_status/");
        LISTABLE_PROJECTION = new String[] { "id", "acc_id", "active", "display_name", "wizard", "priority", "reg_uri" };
    }

    public SipProfile()
    {
        this.display_name = "";
        this.wizard = "EXPERT";
        this.active = true;
    }

    public SipProfile(Cursor paramCursor)
    {
        createFromDb(paramCursor);
    }

    private SipProfile(Parcel paramParcel)
    {
        this.primaryKey = paramParcel.readInt();
        this.id = paramParcel.readInt();
        this.display_name = paramParcel.readString();
        this.wizard = paramParcel.readString();
        this.transport = Integer.valueOf(paramParcel.readInt());
        if (paramParcel.readInt() != 0)
        {
            bool1 = true;
            this.active = bool1;
            this.priority = paramParcel.readInt();
            this.acc_id = getReadParcelableString(paramParcel.readString());
            this.reg_uri = getReadParcelableString(paramParcel.readString());
            this.publish_enabled = paramParcel.readInt();
            this.reg_timeout = paramParcel.readInt();
            this.ka_interval = paramParcel.readInt();
            this.pidf_tuple_id = getReadParcelableString(paramParcel.readString());
            this.force_contact = getReadParcelableString(paramParcel.readString());
            this.proxies = TextUtils.split(getReadParcelableString(paramParcel.readString()), Pattern.quote("|"));
            this.realm = getReadParcelableString(paramParcel.readString());
            this.username = getReadParcelableString(paramParcel.readString());
            this.userpass = getReadParcelableString(paramParcel.readString());
            this.datatype = paramParcel.readInt();
            this.data = getReadParcelableString(paramParcel.readString());
            this.use_srtp = paramParcel.readInt();
            if (paramParcel.readInt() == 0) {
                break label954;
            }
            bool1 = true;
            label567:
            this.allow_contact_rewrite = bool1;
            this.contact_rewrite_method = paramParcel.readInt();
            this.sip_stack = paramParcel.readInt();
            this.reg_use_proxy = paramParcel.readInt();
            this.use_zrtp = paramParcel.readInt();
            this.vm_nbr = getReadParcelableString(paramParcel.readString());
            this.reg_delay_before_refresh = paramParcel.readInt();
            this.icon = ((Bitmap)paramParcel.readParcelable(getClass().getClassLoader()));
            this.try_clean_registers = paramParcel.readInt();
            if (paramParcel.readInt() == 0) {
                break label959;
            }
            bool1 = true;
            label659:
            this.use_rfc5626 = bool1;
            this.rfc5626_instance_id = getReadParcelableString(paramParcel.readString());
            this.rfc5626_reg_id = getReadParcelableString(paramParcel.readString());
            this.vid_in_auto_show = paramParcel.readInt();
            this.vid_out_auto_transmit = paramParcel.readInt();
            this.rtp_port = paramParcel.readInt();
            this.rtp_public_addr = getReadParcelableString(paramParcel.readString());
            this.rtp_bound_addr = getReadParcelableString(paramParcel.readString());
            this.rtp_enable_qos = paramParcel.readInt();
            this.rtp_qos_dscp = paramParcel.readInt();
            this.android_group = getReadParcelableString(paramParcel.readString());
            if (paramParcel.readInt() == 0) {
                break label964;
            }
            bool1 = true;
            label773:
            this.mwi_enabled = bool1;
            if (paramParcel.readInt() == 0) {
                break label969;
            }
            bool1 = true;
            label787:
            this.allow_via_rewrite = bool1;
            this.sip_stun_use = paramParcel.readInt();
            this.media_stun_use = paramParcel.readInt();
            this.ice_cfg_use = paramParcel.readInt();
            this.ice_cfg_enable = paramParcel.readInt();
            this.turn_cfg_use = paramParcel.readInt();
            this.turn_cfg_enable = paramParcel.readInt();
            this.turn_cfg_server = getReadParcelableString(paramParcel.readString());
            this.turn_cfg_user = getReadParcelableString(paramParcel.readString());
            this.turn_cfg_password = getReadParcelableString(paramParcel.readString());
            this.ipv6_media_use = paramParcel.readInt();
            if (paramParcel.readInt() == 0) {
                break label974;
            }
            bool1 = true;
            label893:
            this.initial_auth = bool1;
            this.auth_algo = getReadParcelableString(paramParcel.readString());
            this.wizard_data = getReadParcelableString(paramParcel.readString());
            this.default_uri_scheme = getReadParcelableString(paramParcel.readString());
            if (paramParcel.readInt() == 0) {
                break label979;
            }
        }
        label954:
        label959:
        label964:
        label969:
        label974:
        label979:
        for (boolean bool1 = bool2;; bool1 = false)
        {
            this.allow_sdp_nat_rewrite = bool1;
            return;
            bool1 = false;
            break;
            bool1 = false;
            break label567;
            bool1 = false;
            break label659;
            bool1 = false;
            break label773;
            bool1 = false;
            break label787;
            bool1 = false;
            break label893;
        }
    }

    private final void createFromContentValue(ContentValues paramContentValues)
    {
        boolean bool2 = true;
        Object localObject = paramContentValues.getAsInteger("id");
        if (localObject != null) {
            this.id = ((Integer)localObject).intValue();
        }
        localObject = paramContentValues.getAsString("display_name");
        if (localObject != null) {
            this.display_name = ((String)localObject);
        }
        localObject = paramContentValues.getAsString("wizard");
        if (localObject != null) {
            this.wizard = ((String)localObject);
        }
        localObject = paramContentValues.getAsInteger("transport");
        if (localObject != null) {
            this.transport = ((Integer)localObject);
        }
        localObject = paramContentValues.getAsString("default_uri_scheme");
        if (localObject != null) {
            this.default_uri_scheme = ((String)localObject);
        }
        localObject = paramContentValues.getAsInteger("active");
        if (localObject != null) {
            if (((Integer)localObject).intValue() != 0)
            {
                bool1 = true;
                this.active = bool1;
                label129:
                localObject = paramContentValues.getAsString("android_group");
                if (localObject != null) {
                    this.android_group = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("wizard_data");
                if (localObject != null) {
                    this.wizard_data = ((String)localObject);
                }
                localObject = paramContentValues.getAsInteger("priority");
                if (localObject != null) {
                    this.priority = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsString("acc_id");
                if (localObject != null) {
                    this.acc_id = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("reg_uri");
                if (localObject != null) {
                    this.reg_uri = ((String)localObject);
                }
                localObject = paramContentValues.getAsInteger("publish_enabled");
                if (localObject != null) {
                    this.publish_enabled = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsInteger("reg_timeout");
                if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                    this.reg_timeout = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsInteger("reg_dbr");
                if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                    this.reg_delay_before_refresh = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsInteger("ka_interval");
                if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                    this.ka_interval = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsString("pidf_tuple_id");
                if (localObject != null) {
                    this.pidf_tuple_id = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("force_contact");
                if (localObject != null) {
                    this.force_contact = ((String)localObject);
                }
                localObject = paramContentValues.getAsInteger("allow_contact_rewrite");
                if (localObject != null)
                {
                    if (((Integer)localObject).intValue() != 1) {
                        break label1576;
                    }
                    bool1 = true;
                    label401:
                    this.allow_contact_rewrite = bool1;
                }
                localObject = paramContentValues.getAsInteger("contact_rewrite_method");
                if (localObject != null) {
                    this.contact_rewrite_method = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsInteger("allow_via_rewrite");
                if (localObject != null)
                {
                    if (((Integer)localObject).intValue() != 1) {
                        break label1581;
                    }
                    bool1 = true;
                    label452:
                    this.allow_via_rewrite = bool1;
                }
                localObject = paramContentValues.getAsInteger("allow_sdp_nat_rewrite");
                if (localObject != null)
                {
                    if (((Integer)localObject).intValue() != 1) {
                        break label1586;
                    }
                    bool1 = true;
                    label481:
                    this.allow_sdp_nat_rewrite = bool1;
                }
                localObject = paramContentValues.getAsInteger("use_srtp");
                if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                    this.use_srtp = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsInteger("use_zrtp");
                if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                    this.use_zrtp = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsString("proxy");
                if (localObject != null) {
                    this.proxies = TextUtils.split((String)localObject, Pattern.quote("|"));
                }
                localObject = paramContentValues.getAsInteger("reg_use_proxy");
                if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                    this.reg_use_proxy = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsString("realm");
                if (localObject != null) {
                    this.realm = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("scheme");
                if (localObject != null) {
                    this.scheme = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("username");
                if (localObject != null) {
                    this.username = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("userpass");
                if (localObject != null) {
                    this.userpass = ((String)localObject);
                }
                localObject = paramContentValues.getAsInteger("datatype");
                if (localObject != null) {
                    this.datatype = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsString("data");
                if (localObject != null) {
                    this.data = ((String)localObject);
                }
                localObject = paramContentValues.getAsInteger("initial_auth");
                if (localObject != null)
                {
                    if (((Integer)localObject).intValue() != 1) {
                        break label1591;
                    }
                    bool1 = true;
                    label745:
                    this.initial_auth = bool1;
                }
                localObject = paramContentValues.getAsString("auth_algo");
                if (localObject != null) {
                    this.auth_algo = ((String)localObject);
                }
                localObject = paramContentValues.getAsInteger("sip_stack");
                if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                    this.sip_stack = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsInteger("mwi_enabled");
                if ((localObject != null) && (((Integer)localObject).intValue() >= 0))
                {
                    if (((Integer)localObject).intValue() != 1) {
                        break label1596;
                    }
                    bool1 = true;
                    label831:
                    this.mwi_enabled = bool1;
                }
                localObject = paramContentValues.getAsString("vm_nbr");
                if (localObject != null) {
                    this.vm_nbr = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("vm_nbr2");
                if (localObject != null) {
                    this.vm_nbr = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("vm_nbr3");
                if (localObject != null) {
                    this.vm_nbr = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("vm_nbr4");
                if (localObject != null) {
                    this.vm_nbr = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("vm_nbr5");
                if (localObject != null) {
                    this.vm_nbr = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("vm_nbr6");
                if (localObject != null) {
                    this.vm_nbr = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("vm_nbr7");
                if (localObject != null) {
                    this.vm_nbr = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("vm_nbr8");
                if (localObject != null) {
                    this.vm_nbr = ((String)localObject);
                }
                localObject = paramContentValues.getAsString("vm_nbr9");
                if (localObject != null) {
                    this.vm_nbr = ((String)localObject);
                }
                localObject = paramContentValues.getAsInteger("try_clean_reg");
                if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                    this.try_clean_registers = ((Integer)localObject).intValue();
                }
                localObject = paramContentValues.getAsInteger("use_rfc5626");
                if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                    if (((Integer)localObject).intValue() == 0) {
                        break label1601;
                    }
                }
            }
        }
        label1576:
        label1581:
        label1586:
        label1591:
        label1596:
        label1601:
        for (boolean bool1 = bool2;; bool1 = false)
        {
            this.use_rfc5626 = bool1;
            localObject = paramContentValues.getAsString("rfc5626_instance_id");
            if (localObject != null) {
                this.rfc5626_instance_id = ((String)localObject);
            }
            localObject = paramContentValues.getAsString("rfc5626_reg_id");
            if (localObject != null) {
                this.rfc5626_reg_id = ((String)localObject);
            }
            localObject = paramContentValues.getAsInteger("vid_in_auto_show");
            if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                this.vid_in_auto_show = ((Integer)localObject).intValue();
            }
            localObject = paramContentValues.getAsInteger("vid_out_auto_transmit");
            if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                this.vid_out_auto_transmit = ((Integer)localObject).intValue();
            }
            localObject = paramContentValues.getAsInteger("rtp_port");
            if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                this.rtp_port = ((Integer)localObject).intValue();
            }
            localObject = paramContentValues.getAsString("rtp_public_addr");
            if (localObject != null) {
                this.rtp_public_addr = ((String)localObject);
            }
            localObject = paramContentValues.getAsString("rtp_bound_addr");
            if (localObject != null) {
                this.rtp_bound_addr = ((String)localObject);
            }
            localObject = paramContentValues.getAsInteger("rtp_enable_qos");
            if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                this.rtp_enable_qos = ((Integer)localObject).intValue();
            }
            localObject = paramContentValues.getAsInteger("rtp_qos_dscp");
            if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                this.rtp_qos_dscp = ((Integer)localObject).intValue();
            }
            localObject = paramContentValues.getAsInteger("sip_stun_use");
            if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                this.sip_stun_use = ((Integer)localObject).intValue();
            }
            localObject = paramContentValues.getAsInteger("media_stun_use");
            if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                this.media_stun_use = ((Integer)localObject).intValue();
            }
            localObject = paramContentValues.getAsInteger("ice_cfg_use");
            if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                this.ice_cfg_use = ((Integer)localObject).intValue();
            }
            localObject = paramContentValues.getAsInteger("ice_cfg_enable");
            if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                this.ice_cfg_enable = ((Integer)localObject).intValue();
            }
            localObject = paramContentValues.getAsInteger("turn_cfg_use");
            if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                this.turn_cfg_use = ((Integer)localObject).intValue();
            }
            localObject = paramContentValues.getAsInteger("turn_cfg_enable");
            if ((localObject != null) && (((Integer)localObject).intValue() >= 0)) {
                this.turn_cfg_enable = ((Integer)localObject).intValue();
            }
            localObject = paramContentValues.getAsString("turn_cfg_server");
            if (localObject != null) {
                this.turn_cfg_server = ((String)localObject);
            }
            localObject = paramContentValues.getAsString("turn_cfg_user");
            if (localObject != null) {
                this.turn_cfg_user = ((String)localObject);
            }
            localObject = paramContentValues.getAsString("turn_cfg_pwd");
            if (localObject != null) {
                this.turn_cfg_password = ((String)localObject);
            }
            paramContentValues = paramContentValues.getAsInteger("ipv6_media_use");
            if ((paramContentValues != null) && (paramContentValues.intValue() >= 0)) {
                this.ipv6_media_use = paramContentValues.intValue();
            }
            return;
            bool1 = false;
            break;
            this.active = true;
            break label129;
            bool1 = false;
            break label401;
            bool1 = false;
            break label452;
            bool1 = false;
            break label481;
            bool1 = false;
            break label745;
            bool1 = false;
            break label831;
        }
    }

    private final void createFromDb(Cursor paramCursor)
    {
        ContentValues localContentValues = new ContentValues();
        DatabaseUtils.cursorRowToContentValues(paramCursor, localContentValues);
        createFromContentValue(localContentValues);
    }

    public static ArrayList<SipProfile> getAllProfiles(Context paramContext, boolean paramBoolean)
    {
        return getAllProfiles(paramContext, paramBoolean, LISTABLE_PROJECTION);
    }

    public static ArrayList<SipProfile> getAllProfiles(Context paramContext, boolean paramBoolean, String[] paramArrayOfString)
    {
        ArrayList localArrayList = new ArrayList();
        String str = null;
        String[] arrayOfString = null;
        if (paramBoolean)
        {
            str = "active=?";
            arrayOfString = new String[1];
            arrayOfString[0] = "1";
        }
        paramContext = paramContext.getContentResolver().query(ACCOUNT_URI, paramArrayOfString, str, arrayOfString, null);
        if (paramContext != null) {}
        try
        {
            if (paramContext.moveToFirst()) {
                do
                {
                    localArrayList.add(new SipProfile(paramContext));
                    paramBoolean = paramContext.moveToNext();
                } while (paramBoolean);
            }
            return localArrayList;
        }
        catch (Exception paramArrayOfString)
        {
            LogFinalUtils.logForException(paramArrayOfString);
            Log.e("SipProfile", "Error on looping over sip profiles", paramArrayOfString);
            return localArrayList;
        }
        finally
        {
            paramContext.close();
        }
    }

    public static SipProfile getProfileFromDbId(Context paramContext, long paramLong, String[] paramArrayOfString)
    {
        SipProfile localSipProfile = new SipProfile();
        Object localObject = localSipProfile;
        if (paramLong != -1L)
        {
            paramArrayOfString = paramContext.getContentResolver().query(ContentUris.withAppendedId(ACCOUNT_ID_URI_BASE, paramLong), paramArrayOfString, null, null, null);
            localObject = localSipProfile;
            if (paramArrayOfString != null) {
                paramContext = localSipProfile;
            }
        }
        try
        {
            if (paramArrayOfString.getCount() > 0)
            {
                paramArrayOfString.moveToFirst();
                paramContext = new SipProfile(paramArrayOfString);
            }
            paramArrayOfString.close();
            localObject = paramContext;
            return (SipProfile)localObject;
        }
        catch (Exception paramContext)
        {
            LogFinalUtils.logForException(paramContext);
            Log.e("SipProfile", "Something went wrong while retrieving the account", paramContext);
            return localSipProfile;
        }
        finally
        {
            paramArrayOfString.close();
        }
    }

    private String getReadParcelableString(String paramString)
    {
        String str = paramString;
        if (paramString.equalsIgnoreCase("null")) {
            str = null;
        }
        return str;
    }

    private String getWriteParcelableString(String paramString)
    {
        String str = paramString;
        if (paramString == null) {
            str = "null";
        }
        return str;
    }

    public int describeContents()
    {
        return 0;
    }

    public SipUri.ParsedSipContactInfos formatCalleeNumber(String paramString)
    {
        paramString = SipUri.parseSipContact(paramString);
        String str;
        if (TextUtils.isEmpty(paramString.domain))
        {
            str = getDefaultDomain();
            if (!TextUtils.isEmpty(str)) {
                break label70;
            }
            paramString.domain = paramString.userName;
            paramString.userName = null;
        }
        for (;;)
        {
            if (TextUtils.isEmpty(paramString.scheme))
            {
                if (TextUtils.isEmpty(this.default_uri_scheme)) {
                    break;
                }
                paramString.scheme = this.default_uri_scheme;
            }
            return paramString;
            label70:
            paramString.domain = str;
        }
        paramString.scheme = "sip";
        return paramString;
    }

    public boolean getAutoRegistration()
    {
        return true;
    }

    public ContentValues getDbContentValues()
    {
        int j = 1;
        ContentValues localContentValues = new ContentValues();
        if (this.id != -1L) {
            localContentValues.put("id", Long.valueOf(this.id));
        }
        if (this.active)
        {
            i = 1;
            localContentValues.put("active", Integer.valueOf(i));
            localContentValues.put("wizard", this.wizard);
            localContentValues.put("display_name", this.display_name);
            localContentValues.put("transport", this.transport);
            localContentValues.put("default_uri_scheme", this.default_uri_scheme);
            localContentValues.put("wizard_data", this.wizard_data);
            localContentValues.put("priority", Integer.valueOf(this.priority));
            localContentValues.put("acc_id", this.acc_id);
            localContentValues.put("reg_uri", this.reg_uri);
            localContentValues.put("publish_enabled", Integer.valueOf(this.publish_enabled));
            localContentValues.put("reg_timeout", Integer.valueOf(this.reg_timeout));
            localContentValues.put("ka_interval", Integer.valueOf(this.ka_interval));
            localContentValues.put("pidf_tuple_id", this.pidf_tuple_id);
            localContentValues.put("force_contact", this.force_contact);
            if (!this.allow_contact_rewrite) {
                break label843;
            }
            i = 1;
            label204:
            localContentValues.put("allow_contact_rewrite", Integer.valueOf(i));
            if (!this.allow_via_rewrite) {
                break label848;
            }
            i = 1;
            label223:
            localContentValues.put("allow_via_rewrite", Integer.valueOf(i));
            if (!this.allow_sdp_nat_rewrite) {
                break label853;
            }
            i = 1;
            label242:
            localContentValues.put("allow_sdp_nat_rewrite", Integer.valueOf(i));
            localContentValues.put("contact_rewrite_method", Integer.valueOf(this.contact_rewrite_method));
            localContentValues.put("use_srtp", Integer.valueOf(this.use_srtp));
            localContentValues.put("use_zrtp", Integer.valueOf(this.use_zrtp));
            if (this.proxies == null) {
                break label858;
            }
            localContentValues.put("proxy", TextUtils.join("|", this.proxies));
            label314:
            localContentValues.put("reg_use_proxy", Integer.valueOf(this.reg_use_proxy));
            localContentValues.put("realm", this.realm);
            localContentValues.put("scheme", this.scheme);
            localContentValues.put("username", this.username);
            localContentValues.put("userpass", this.userpass);
            localContentValues.put("datatype", Integer.valueOf(this.datatype));
            if (!TextUtils.isEmpty(this.data)) {
                localContentValues.put("data", this.data);
            }
            if (!this.initial_auth) {
                break label870;
            }
            i = 1;
            label409:
            localContentValues.put("initial_auth", Integer.valueOf(i));
            if (!TextUtils.isEmpty(this.auth_algo)) {
                localContentValues.put("auth_algo", this.auth_algo);
            }
            localContentValues.put("sip_stack", Integer.valueOf(this.sip_stack));
            localContentValues.put("mwi_enabled", Boolean.valueOf(this.mwi_enabled));
            localContentValues.put("vm_nbr", this.vm_nbr);
            localContentValues.put("vm_nbr2", this.vm_nbr);
            localContentValues.put("vm_nbr3", this.vm_nbr);
            localContentValues.put("vm_nbr4", this.vm_nbr);
            localContentValues.put("vm_nbr5", this.vm_nbr);
            localContentValues.put("vm_nbr6", this.vm_nbr);
            localContentValues.put("vm_nbr7", this.vm_nbr);
            localContentValues.put("vm_nbr8", this.vm_nbr);
            localContentValues.put("vm_nbr9", this.vm_nbr);
            localContentValues.put("reg_dbr", Integer.valueOf(this.reg_delay_before_refresh));
            localContentValues.put("try_clean_reg", Integer.valueOf(this.try_clean_registers));
            localContentValues.put("rtp_bound_addr", this.rtp_bound_addr);
            localContentValues.put("rtp_enable_qos", Integer.valueOf(this.rtp_enable_qos));
            localContentValues.put("rtp_port", Integer.valueOf(this.rtp_port));
            localContentValues.put("rtp_public_addr", this.rtp_public_addr);
            localContentValues.put("rtp_qos_dscp", Integer.valueOf(this.rtp_qos_dscp));
            localContentValues.put("vid_in_auto_show", Integer.valueOf(this.vid_in_auto_show));
            localContentValues.put("vid_out_auto_transmit", Integer.valueOf(this.vid_out_auto_transmit));
            localContentValues.put("rfc5626_instance_id", this.rfc5626_instance_id);
            localContentValues.put("rfc5626_reg_id", this.rfc5626_reg_id);
            if (!this.use_rfc5626) {
                break label875;
            }
        }
        label843:
        label848:
        label853:
        label858:
        label870:
        label875:
        for (int i = j;; i = 0)
        {
            localContentValues.put("use_rfc5626", Integer.valueOf(i));
            localContentValues.put("android_group", this.android_group);
            localContentValues.put("sip_stun_use", Integer.valueOf(this.sip_stun_use));
            localContentValues.put("media_stun_use", Integer.valueOf(this.media_stun_use));
            localContentValues.put("ice_cfg_use", Integer.valueOf(this.ice_cfg_use));
            localContentValues.put("ice_cfg_enable", Integer.valueOf(this.ice_cfg_enable));
            localContentValues.put("turn_cfg_use", Integer.valueOf(this.turn_cfg_use));
            localContentValues.put("turn_cfg_enable", Integer.valueOf(this.turn_cfg_enable));
            localContentValues.put("turn_cfg_server", this.turn_cfg_server);
            localContentValues.put("turn_cfg_user", this.turn_cfg_user);
            localContentValues.put("turn_cfg_pwd", this.turn_cfg_password);
            localContentValues.put("ipv6_media_use", Integer.valueOf(this.ipv6_media_use));
            return localContentValues;
            i = 0;
            break;
            i = 0;
            break label204;
            i = 0;
            break label223;
            i = 0;
            break label242;
            localContentValues.put("proxy", "");
            break label314;
            i = 0;
            break label409;
        }
    }

    public String getDefaultDomain()
    {
        Object localObject2 = null;
        Object localObject1 = SipUri.parseSipContact(this.acc_id);
        String str = null;
        if (TextUtils.isEmpty(((SipUri.ParsedSipContactInfos)localObject1).domain)) {
            if (!TextUtils.isEmpty(this.reg_uri))
            {
                localObject1 = SipUri.parseSipUri(this.reg_uri);
                if (localObject1 != null) {
                    break label86;
                }
            }
        }
        label86:
        do
        {
            return (String)localObject2;
            localObject1 = str;
            if (this.proxies == null) {
                break;
            }
            localObject1 = str;
            if (this.proxies.length <= 0) {
                break;
            }
            localObject1 = SipUri.parseSipUri(this.proxies[0]);
            break;
            localObject1 = ((SipUri.ParsedSipContactInfos)localObject1).getServerSipUri();
            break;
            if (((SipUri.ParsedSipUriInfos)localObject1).domain == null) {
                break label141;
            }
            str = ((SipUri.ParsedSipUriInfos)localObject1).domain;
            localObject2 = str;
        } while (((SipUri.ParsedSipUriInfos)localObject1).port == 5060);
        return str + ":" + Integer.toString(((SipUri.ParsedSipUriInfos)localObject1).port);
        label141:
        Log.d("SipProfile", "Domain not found for this account");
        return null;
    }

    public String getDisplayName()
    {
        if (this.acc_id != null)
        {
            SipUri.ParsedSipContactInfos localParsedSipContactInfos = SipUri.parseSipContact(this.acc_id);
            if (localParsedSipContactInfos.displayName != null) {
                return localParsedSipContactInfos.displayName;
            }
        }
        return "";
    }

    public String getPassword()
    {
        return this.data;
    }

    public String getProfileName()
    {
        return this.display_name;
    }

    public String getProxyAddress()
    {
        if ((this.proxies != null) && (this.proxies.length > 0)) {
            return this.proxies[0];
        }
        return "";
    }

    public String getSipDomain()
    {
        SipUri.ParsedSipContactInfos localParsedSipContactInfos = SipUri.parseSipContact(this.acc_id);
        if (localParsedSipContactInfos.domain != null) {
            return localParsedSipContactInfos.domain;
        }
        return "";
    }

    public String getSipUserName()
    {
        SipUri.ParsedSipContactInfos localParsedSipContactInfos = SipUri.parseSipContact(this.acc_id);
        if (localParsedSipContactInfos.userName != null) {
            return localParsedSipContactInfos.userName;
        }
        return "";
    }

    public String getUriString()
    {
        return this.acc_id;
    }

    public String getUserPass()
    {
        return this.userpass;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        int j = 1;
        paramParcel.writeInt(this.primaryKey);
        paramParcel.writeInt((int)this.id);
        paramParcel.writeString(this.display_name);
        paramParcel.writeString(this.wizard);
        paramParcel.writeInt(this.transport.intValue());
        int i;
        if (this.active)
        {
            i = 1;
            paramParcel.writeInt(i);
            paramParcel.writeInt(this.priority);
            paramParcel.writeString(getWriteParcelableString(this.acc_id));
            paramParcel.writeString(getWriteParcelableString(this.reg_uri));
            paramParcel.writeInt(this.publish_enabled);
            paramParcel.writeInt(this.reg_timeout);
            paramParcel.writeInt(this.ka_interval);
            paramParcel.writeString(getWriteParcelableString(this.pidf_tuple_id));
            paramParcel.writeString(getWriteParcelableString(this.force_contact));
            if (this.proxies == null) {
                break label618;
            }
            paramParcel.writeString(getWriteParcelableString(TextUtils.join("|", this.proxies)));
            label166:
            paramParcel.writeString(getWriteParcelableString(this.realm));
            paramParcel.writeString(getWriteParcelableString(this.username));
            paramParcel.writeString(getWriteParcelableString(this.userpass));
            paramParcel.writeInt(this.datatype);
            paramParcel.writeString(getWriteParcelableString(this.data));
            paramParcel.writeInt(this.use_srtp);
            if (!this.allow_contact_rewrite) {
                break label628;
            }
            i = 1;
            label239:
            paramParcel.writeInt(i);
            paramParcel.writeInt(this.contact_rewrite_method);
            paramParcel.writeInt(this.sip_stack);
            paramParcel.writeInt(this.reg_use_proxy);
            paramParcel.writeInt(this.use_zrtp);
            paramParcel.writeString(getWriteParcelableString(this.vm_nbr));
            paramParcel.writeInt(this.reg_delay_before_refresh);
            paramParcel.writeParcelable(this.icon, paramInt);
            paramParcel.writeInt(this.try_clean_registers);
            if (!this.use_rfc5626) {
                break label633;
            }
            paramInt = 1;
            label322:
            paramParcel.writeInt(paramInt);
            paramParcel.writeString(getWriteParcelableString(this.rfc5626_instance_id));
            paramParcel.writeString(getWriteParcelableString(this.rfc5626_reg_id));
            paramParcel.writeInt(this.vid_in_auto_show);
            paramParcel.writeInt(this.vid_out_auto_transmit);
            paramParcel.writeInt(this.rtp_port);
            paramParcel.writeString(getWriteParcelableString(this.rtp_public_addr));
            paramParcel.writeString(getWriteParcelableString(this.rtp_bound_addr));
            paramParcel.writeInt(this.rtp_enable_qos);
            paramParcel.writeInt(this.rtp_qos_dscp);
            paramParcel.writeString(getWriteParcelableString(this.android_group));
            if (!this.mwi_enabled) {
                break label638;
            }
            paramInt = 1;
            label436:
            paramParcel.writeInt(paramInt);
            if (!this.allow_via_rewrite) {
                break label643;
            }
            paramInt = 1;
            label450:
            paramParcel.writeInt(paramInt);
            paramParcel.writeInt(this.sip_stun_use);
            paramParcel.writeInt(this.media_stun_use);
            paramParcel.writeInt(this.ice_cfg_use);
            paramParcel.writeInt(this.ice_cfg_enable);
            paramParcel.writeInt(this.turn_cfg_use);
            paramParcel.writeInt(this.turn_cfg_enable);
            paramParcel.writeString(getWriteParcelableString(this.turn_cfg_server));
            paramParcel.writeString(getWriteParcelableString(this.turn_cfg_user));
            paramParcel.writeString(getWriteParcelableString(this.turn_cfg_password));
            paramParcel.writeInt(this.ipv6_media_use);
            if (!this.initial_auth) {
                break label648;
            }
            paramInt = 1;
            label556:
            paramParcel.writeInt(paramInt);
            paramParcel.writeString(getWriteParcelableString(this.auth_algo));
            paramParcel.writeString(getWriteParcelableString(this.wizard_data));
            paramParcel.writeString(getWriteParcelableString(this.default_uri_scheme));
            if (!this.allow_sdp_nat_rewrite) {
                break label653;
            }
        }
        label618:
        label628:
        label633:
        label638:
        label643:
        label648:
        label653:
        for (paramInt = j;; paramInt = 0)
        {
            paramParcel.writeInt(paramInt);
            return;
            i = 0;
            break;
            paramParcel.writeString("");
            break label166;
            i = 0;
            break label239;
            paramInt = 0;
            break label322;
            paramInt = 0;
            break label436;
            paramInt = 0;
            break label450;
            paramInt = 0;
            break label556;
        }
    }
}
