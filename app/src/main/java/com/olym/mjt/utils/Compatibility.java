package com.olym.mjt.utils;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Contacts.People;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.text.TextUtils;
import com.olym.mjt.pjsip.sip.api.SipConfigManager;
import com.olym.mjt.pjsip.sip.utils.PhoneCapabilityTester;
import com.olym.mjt.pjsip.sip.utils.PreferencesWrapper;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

public final class Compatibility
{
    private static final String THIS_FILE = "Compat";

    public static boolean canMakeGSMCall(Context paramContext)
    {
        int i = SipConfigManager.getPreferenceIntegerValue(paramContext, "gsm_integration_type", Integer.valueOf(2)).intValue();
        if (i == 0) {
            return PhoneCapabilityTester.isPhone(paramContext);
        }
        return i != 2;
    }

    public static int getApiLevel()
    {
        return Build.VERSION.SDK_INT;
    }

    public static Intent getContactPhoneIntent()
    {
        Intent localIntent = new Intent("android.intent.action.PICK");
        if (isCompatible(5))
        {
            localIntent.setData(Uri.parse("content://com.android.contacts/contacts"));
            return localIntent;
        }
        localIntent.setData(Contacts.People.CONTENT_URI);
        return localIntent;
    }

    public static String getCpuAbi()
    {
        if (isCompatible(4)) {
            try
            {
                String str = Build.class.getField("CPU_ABI").get(null).toString();
                return str;
            }
            catch (Exception localException)
            {
                Log.w("Compat", "Announce to be android 1.6 but no CPU ABI field", localException);
            }
        }
        return "armeabi";
    }

    private static int getDefaultAudioImplementation()
    {
        if (Build.DEVICE.toLowerCase().startsWith("picasso")) {}
        do
        {
            return 0;
            if (isCompatible(11)) {
                return 1;
            }
            if ((Build.DEVICE.equalsIgnoreCase("ST25i")) && (isCompatible(10))) {
                return 1;
            }
            if ((Build.DEVICE.equalsIgnoreCase("u8510")) && (isCompatible(10))) {
                return 1;
            }
        } while (!Build.DEVICE.toLowerCase().startsWith("rk31sdk"));
        return 0;
    }

    public static String getDefaultFrequency()
    {
        if (Build.DEVICE.equalsIgnoreCase("olympus")) {
            return "32000";
        }
        if (Build.DEVICE.toUpperCase().equals("GT-P1010")) {
            return "32000";
        }
        if (isCompatible(4)) {
            return "16000";
        }
        return "8000";
    }

    public static String getDefaultMicroSource()
    {
        if ((!isCompatible(11)) && (Build.DEVICE.toUpperCase().startsWith("GT-I9100"))) {
            return Integer.toString(1);
        }
        if (isCompatible(10)) {
            return Integer.toString(7);
        }
        return Integer.toString(0);
    }

    public static int getHomeMenuId()
    {
        return 16908332;
    }

    public static int getInCallStream(boolean paramBoolean)
    {
        if ((Build.BRAND.equalsIgnoreCase("archos")) && (Build.DEVICE.equalsIgnoreCase("g7a"))) {
            return 3;
        }
        if (paramBoolean) {
            return 6;
        }
        return 0;
    }

    public static final int getNumCores()
    {
        try
        {
            int i = new File("/sys/devices/system/cpu/").listFiles(new FileFilter()
            {
                public boolean accept(File paramAnonymousFile)
                {
                    return Pattern.matches("cpu[0-9]", paramAnonymousFile.getName());
                }
            }).length;
            return i;
        }
        catch (Exception localException) {}
        return Runtime.getRuntime().availableProcessors();
    }

    @TargetApi(17)
    public static int getWifiSleepPolicy(ContentResolver paramContentResolver)
    {
        if (isCompatible(17)) {
            return Settings.Global.getInt(paramContentResolver, "wifi_sleep_policy", 0);
        }
        return Settings.System.getInt(paramContentResolver, "wifi_sleep_policy", 0);
    }

    @TargetApi(17)
    public static int getWifiSleepPolicyDefault()
    {
        if (isCompatible(17)) {}
        return 0;
    }

    @TargetApi(17)
    public static int getWifiSleepPolicyNever()
    {
        if (isCompatible(17)) {}
        return 2;
    }

    public static String guessInCallMode()
    {
        if ((!isCompatible(11)) && (Build.DEVICE.toUpperCase().startsWith("GT-I9100"))) {
            return Integer.toString(0);
        }
        if ((Build.BRAND.equalsIgnoreCase("sdg")) || (isCompatible(10))) {
            return "3";
        }
        if (Build.DEVICE.equalsIgnoreCase("blade")) {
            return Integer.toString(2);
        }
        if (!isCompatible(5)) {
            return Integer.toString(2);
        }
        return Integer.toString(0);
    }

    public static boolean isCompatible(int paramInt)
    {
        return Build.VERSION.SDK_INT >= paramInt;
    }

    public static boolean isInstalledOnSdCard(Context paramContext)
    {
        PackageManager localPackageManager;
        if (isCompatible(8)) {
            localPackageManager = paramContext.getPackageManager();
        }
        for (;;)
        {
            try
            {
                int i = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0).applicationInfo.flags;
                return (i & 0x40000) == 262144;
            }
            catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
            try
            {
                paramContext = paramContext.getFilesDir().getAbsolutePath();
                if (paramContext.startsWith("/data/")) {
                    return false;
                }
                boolean bool = paramContext.contains(Environment.getExternalStorageDirectory().getPath());
                if (bool) {}
            }
            catch (Throwable paramContext)
            {
                for (;;) {}
            }
        }
        return false;
    }

    public static boolean isTabletScreen(Context paramContext)
    {
        boolean bool = false;
        if (!isCompatible(4)) {
            return false;
        }
        paramContext = paramContext.getResources().getConfiguration();
        try
        {
            int i = ((Integer)Configuration.class.getDeclaredField("screenLayout").get(paramContext)).intValue();
            i &= 0xF;
            if ((i == 3) || (i == 4)) {
                bool = true;
            }
            return bool;
        }
        catch (Exception paramContext) {}
        return false;
    }

    private static boolean needPspWorkaround()
    {
        if (Build.DEVICE.equalsIgnoreCase("vivo")) {}
        do
        {
            do
            {
                return true;
                if (isCompatible(11)) {
                    return false;
                }
                if ((!Build.PRODUCT.toLowerCase().startsWith("htc")) && (!Build.BRAND.toLowerCase().startsWith("htc")) && (!Build.PRODUCT.toLowerCase().equalsIgnoreCase("inc")) && (!Build.DEVICE.equalsIgnoreCase("passion"))) {
                    break;
                }
                if ((Build.DEVICE.equalsIgnoreCase("hero")) || (Build.DEVICE.equalsIgnoreCase("magic")) || (Build.DEVICE.equalsIgnoreCase("tatoo")) || (Build.DEVICE.equalsIgnoreCase("dream")) || (Build.DEVICE.equalsIgnoreCase("legend"))) {
                    return false;
                }
            } while ((!isCompatible(9)) || (!Build.DEVICE.equalsIgnoreCase("passion")));
            return false;
        } while (((Build.BRAND.toLowerCase().startsWith("dell")) && (Build.DEVICE.equalsIgnoreCase("streak"))) || (((Build.DEVICE.toLowerCase().contains("milestone2")) || (Build.BOARD.toLowerCase().contains("sholes")) || (Build.PRODUCT.toLowerCase().contains("sholes")) || (Build.DEVICE.equalsIgnoreCase("olympus")) || (Build.DEVICE.toLowerCase().contains("umts_jordan"))) && ((!isCompatible(9)) || (Build.MODEL.equalsIgnoreCase("XT320")) || (Build.DEVICE.startsWith("one_touch_990")))));
        return false;
    }

    private static boolean needSGSWorkaround()
    {
        if (isCompatible(9)) {}
        while ((!Build.DEVICE.toUpperCase().startsWith("GT-I9000")) && (!Build.DEVICE.toUpperCase().startsWith("GT-P1000"))) {
            return false;
        }
        return true;
    }

    private static boolean needToneWorkaround()
    {
        return (Build.PRODUCT.toLowerCase().startsWith("gt-i5800")) || (Build.PRODUCT.toLowerCase().startsWith("gt-i5801")) || (Build.PRODUCT.toLowerCase().startsWith("gt-i9003"));
    }

    private static boolean needWebRTCImplementation()
    {
        if (Build.DEVICE.toLowerCase().contains("droid2")) {}
        while ((Build.MODEL.toLowerCase().contains("droid bionic")) || (Build.DEVICE.toLowerCase().contains("sunfire")) || (Build.DEVICE.equalsIgnoreCase("U8833"))) {
            return true;
        }
        return false;
    }

    private static void resetCodecsSettings(PreferencesWrapper paramPreferencesWrapper)
    {
        int k = 0;
        int i = 0;
        int m = 0;
        String str = getCpuAbi();
        int j = m;
        if (!TextUtils.isEmpty(str))
        {
            if ((str.equalsIgnoreCase("mips")) || (str.equalsIgnoreCase("x86"))) {
                i = 1;
            }
            if (!str.equalsIgnoreCase("armeabi-v7a"))
            {
                j = m;
                k = i;
                if (!str.equalsIgnoreCase("x86")) {}
            }
            else
            {
                j = 1;
                k = i;
            }
        }
        paramPreferencesWrapper.setCodecPriority("PCMU/8000/1", "nb", "60");
        paramPreferencesWrapper.setCodecPriority("PCMA/8000/1", "nb", "50");
        paramPreferencesWrapper.setCodecPriority("speex/8000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("speex/16000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("speex/32000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("GSM/8000/1", "nb", "230");
        paramPreferencesWrapper.setCodecPriority("G722/16000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("G729/8000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("iLBC/8000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("SILK/8000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("SILK/12000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("SILK/16000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("SILK/24000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("CODEC2/8000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("G7221/16000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("G7221/32000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("ISAC/16000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("ISAC/32000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("AMR/8000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("AMR-WB/16000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("opus/8000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("opus/16000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("opus/24000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("opus/48000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("G726-16/8000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("G726-24/8000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("G726-32/8000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("G726-40/8000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("mpeg4-generic/48000/1", "nb", "0");
        paramPreferencesWrapper.setCodecPriority("PCMU/8000/1", "wb", "60");
        paramPreferencesWrapper.setCodecPriority("PCMA/8000/1", "wb", "50");
        paramPreferencesWrapper.setCodecPriority("speex/8000/1", "wb", "0");
        paramPreferencesWrapper.setCodecPriority("speex/16000/1", "wb", "0");
        paramPreferencesWrapper.setCodecPriority("speex/32000/1", "wb", "0");
        paramPreferencesWrapper.setCodecPriority("GSM/8000/1", "wb", "0");
        if (k != 0)
        {
            str = "235";
            paramPreferencesWrapper.setCodecPriority("G722/16000/1", "wb", str);
            paramPreferencesWrapper.setCodecPriority("G729/8000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("iLBC/8000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("SILK/8000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("SILK/12000/1", "wb", "0");
            if (j == 0) {
                break label915;
            }
            str = "0";
            label615:
            paramPreferencesWrapper.setCodecPriority("SILK/16000/1", "wb", str);
            if (j == 0) {
                break label923;
            }
        }
        label915:
        label923:
        for (str = "220";; str = "0")
        {
            paramPreferencesWrapper.setCodecPriority("SILK/24000/1", "wb", str);
            paramPreferencesWrapper.setCodecPriority("CODEC2/8000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("G7221/16000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("G7221/32000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("ISAC/16000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("ISAC/32000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("AMR/8000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("AMR-WB/16000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("opus/8000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("opus/16000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("opus/24000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("opus/48000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("G726-16/8000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("G726-24/8000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("G726-32/8000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("G726-40/8000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("mpeg4-generic/48000/1", "wb", "0");
            paramPreferencesWrapper.setPreferenceStringValue("band_for_wifi", "wb");
            paramPreferencesWrapper.setPreferenceStringValue("band_for_other", "wb");
            paramPreferencesWrapper.setPreferenceStringValue("band_for_3g", "nb");
            paramPreferencesWrapper.setPreferenceStringValue("band_for_gprs", "nb");
            paramPreferencesWrapper.setPreferenceStringValue("band_for_edge", "nb");
            return;
            str = "0";
            break;
            str = "220";
            break label615;
        }
    }

    @TargetApi(19)
    public static void setExactAlarm(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
    {
        if (isCompatible(19))
        {
            paramAlarmManager.setExact(paramInt, paramLong, paramPendingIntent);
            return;
        }
        paramAlarmManager.set(paramInt, paramLong, paramPendingIntent);
    }

    public static void setFirstRunParameters(PreferencesWrapper paramPreferencesWrapper)
    {
        boolean bool2 = true;
        paramPreferencesWrapper.startEditing();
        resetCodecsSettings(paramPreferencesWrapper);
        String str;
        if (getCpuAbi().equalsIgnoreCase("armeabi-v7a"))
        {
            str = "4";
            paramPreferencesWrapper.setPreferenceStringValue("snd_media_quality", str);
            if (!isCompatible(4)) {
                break label391;
            }
            str = "1";
            label45:
            paramPreferencesWrapper.setPreferenceStringValue("snd_auto_close_time", str);
            paramPreferencesWrapper.setPreferenceStringValue("snd_clock_rate", getDefaultFrequency());
            paramPreferencesWrapper.setPreferenceBooleanValue("keep_awake_incall", needPspWorkaround());
            if (getNumCores() <= 1) {
                break label398;
            }
            str = "2";
            label84:
            paramPreferencesWrapper.setPreferenceStringValue("media_thread_count", str);
            if (Build.PRODUCT.equalsIgnoreCase("SPH-M900")) {
                paramPreferencesWrapper.setPreferenceBooleanValue("invert_proximity_sensor", true);
            }
            if (isTabletScreen(paramPreferencesWrapper.getContext())) {
                break label405;
            }
            bool1 = true;
            label124:
            paramPreferencesWrapper.setPreferenceBooleanValue("prevent_screen_rotation", bool1);
            if ((Build.DEVICE.toUpperCase().startsWith("GT-I9000")) && (!isCompatible(9)))
            {
                paramPreferencesWrapper.setPreferenceFloatValue("snd_mic_level", 0.4F);
                paramPreferencesWrapper.setPreferenceFloatValue("snd_speaker_level", 0.2F);
                paramPreferencesWrapper.setPreferenceBooleanValue("use_soft_volume", true);
            }
            if ((Build.PRODUCT.equalsIgnoreCase("htc_supersonic")) && (!isCompatible(9)))
            {
                paramPreferencesWrapper.setPreferenceFloatValue("snd_mic_level", 0.5F);
                paramPreferencesWrapper.setPreferenceFloatValue("snd_speaker_level", 1.5F);
            }
            paramPreferencesWrapper.setPreferenceBooleanValue("use_routing_api", shouldUseRoutingApi());
            paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
            paramPreferencesWrapper.setPreferenceBooleanValue("set_audio_generate_tone", needToneWorkaround());
            paramPreferencesWrapper.setPreferenceBooleanValue("use_sgs_call_hack", needSGSWorkaround());
            paramPreferencesWrapper.setPreferenceStringValue("sip_audio_mode", guessInCallMode());
            paramPreferencesWrapper.setPreferenceStringValue("micro_source", getDefaultMicroSource());
            paramPreferencesWrapper.setPreferenceBooleanValue("use_webrtc_hack", needWebRTCImplementation());
            paramPreferencesWrapper.setPreferenceBooleanValue("do_focus_audio", shouldFocusAudio());
            bool1 = shouldUsePriviledgedIntegration(paramPreferencesWrapper.getContext());
            paramPreferencesWrapper.setPreferenceBooleanValue("integrate_tel_privileged", bool1);
            if (bool1) {
                if (bool1) {
                    break label410;
                }
            }
        }
        label391:
        label398:
        label405:
        label410:
        for (boolean bool1 = bool2;; bool1 = false)
        {
            paramPreferencesWrapper.setPreferenceBooleanValue("integrate_with_native_dialer", bool1);
            if (Build.PRODUCT.startsWith("GoGear_Connect")) {
                paramPreferencesWrapper.setPreferenceBooleanValue("integrate_with_native_calllogs", false);
            }
            paramPreferencesWrapper.setPreferenceStringValue("audio_implementation", Integer.toString(getDefaultAudioImplementation()));
            paramPreferencesWrapper.setPreferenceBooleanValue("setup_audio_before_init", shouldSetupAudioBeforeInit());
            paramPreferencesWrapper.endEditing();
            return;
            str = "3";
            break;
            str = "5";
            break label45;
            str = "1";
            break label84;
            bool1 = false;
            break label124;
        }
    }

    @TargetApi(17)
    public static void setWifiSleepPolicy(ContentResolver paramContentResolver, int paramInt)
    {
        if (!isCompatible(17)) {
            Settings.System.putInt(paramContentResolver, "wifi_sleep_policy", paramInt);
        }
    }

    private static boolean shouldFocusAudio()
    {
        if ((Build.DEVICE.toLowerCase().startsWith("endeavoru")) || (Build.DEVICE.toLowerCase().startsWith("evita"))) {}
        while ((Build.DEVICE.toUpperCase().startsWith("GT-P7510")) && (isCompatible(15))) {
            return false;
        }
        return true;
    }

    public static boolean shouldSetupAudioBeforeInit()
    {
        return (Build.DEVICE.toLowerCase().startsWith("gt-")) || (Build.PRODUCT.toLowerCase().startsWith("gt-"));
    }

    public static boolean shouldUseModeApi()
    {
        if ((Build.DEVICE.equalsIgnoreCase("blade")) || (Build.DEVICE.equalsIgnoreCase("joe"))) {}
        while ((Build.DEVICE.toUpperCase().startsWith("GT-")) || (Build.PRODUCT.toUpperCase().startsWith("GT-")) || (Build.DEVICE.toUpperCase().startsWith("YP-")) || (Build.PRODUCT.equalsIgnoreCase("htc_supersonic")) || (Build.DEVICE.toLowerCase().startsWith("thunder")) || ((Build.MODEL.toUpperCase().startsWith("LG-E720")) && (!isCompatible(9))) || ((Build.DEVICE.toLowerCase().startsWith("g2")) && (Build.BRAND.toLowerCase().startsWith("lge"))) || (Build.DEVICE.toLowerCase().startsWith("cayman")) || (Build.DEVICE.equalsIgnoreCase("U8150")) || (Build.DEVICE.equalsIgnoreCase("U8110")) || (Build.DEVICE.equalsIgnoreCase("U8120")) || (Build.DEVICE.equalsIgnoreCase("U8100")) || (Build.DEVICE.toUpperCase().startsWith("U8836")) || (Build.PRODUCT.equalsIgnoreCase("U8655")) || (Build.DEVICE.toUpperCase().startsWith("HWU9700")) || (Build.MODEL.equalsIgnoreCase("XT320")) || (Build.DEVICE.toUpperCase().startsWith("ONE_TOUCH_993D")) || (Build.DEVICE.toUpperCase().startsWith("MAKO"))) {
            return true;
        }
        return false;
    }

    private static boolean shouldUsePriviledgedIntegration(Context paramContext)
    {
        return !PhoneCapabilityTester.isPhone(paramContext);
    }

    public static boolean shouldUseRoutingApi()
    {
        Log.d("Compat", "Current device " + Build.BRAND + " - " + Build.DEVICE);
        if (Build.PRODUCT.equalsIgnoreCase("htc_supersonic")) {}
        while ((Build.DEVICE.equalsIgnoreCase("joe")) || (Build.DEVICE.toUpperCase().startsWith("GT-S")) || (!isCompatible(4))) {
            return true;
        }
        return false;
    }

    public static void updateApiVersion(PreferencesWrapper paramPreferencesWrapper, int paramInt1, int paramInt2)
    {
        paramPreferencesWrapper.startEditing();
        paramPreferencesWrapper.setPreferenceBooleanValue("use_routing_api", shouldUseRoutingApi());
        paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
        paramPreferencesWrapper.setPreferenceBooleanValue("set_audio_generate_tone", needToneWorkaround());
        paramPreferencesWrapper.setPreferenceBooleanValue("use_sgs_call_hack", needSGSWorkaround());
        paramPreferencesWrapper.setPreferenceStringValue("sip_audio_mode", guessInCallMode());
        paramPreferencesWrapper.setPreferenceStringValue("micro_source", getDefaultMicroSource());
        if (isCompatible(9))
        {
            paramPreferencesWrapper.setPreferenceFloatValue("snd_mic_level", 1.0F);
            paramPreferencesWrapper.setPreferenceFloatValue("snd_speaker_level", 1.0F);
            paramPreferencesWrapper.setPreferenceBooleanValue("use_soft_volume", false);
        }
        paramPreferencesWrapper.setPreferenceBooleanValue("keep_awake_incall", needPspWorkaround());
        paramPreferencesWrapper.setPreferenceBooleanValue("do_focus_audio", shouldFocusAudio());
        paramPreferencesWrapper.endEditing();
    }

    public static void updateVersion(PreferencesWrapper paramPreferencesWrapper, int paramInt1, int paramInt2)
    {
        paramPreferencesWrapper.startEditing();
        if (paramInt1 < 14)
        {
            if ((Build.DEVICE.toUpperCase().startsWith("GT-I9000")) && (!isCompatible(9)))
            {
                paramPreferencesWrapper.setPreferenceFloatValue("snd_mic_level", 0.4F);
                paramPreferencesWrapper.setPreferenceFloatValue("snd_speaker_level", 0.2F);
            }
            if (TextUtils.isEmpty(paramPreferencesWrapper.getPreferenceStringValue("stun_server"))) {
                paramPreferencesWrapper.setPreferenceStringValue("stun_server", "stun.counterpath.com");
            }
        }
        if (paramInt1 < 15) {
            paramPreferencesWrapper.setPreferenceBooleanValue("enable_stun", false);
        }
        if ((paramInt1 < 369) && (Build.DEVICE.toUpperCase().startsWith("GT-I9000")) && (!isCompatible(9))) {
            paramPreferencesWrapper.setPreferenceBooleanValue("use_soft_volume", true);
        }
        if (paramInt1 < 385)
        {
            paramPreferencesWrapper.setPreferenceBooleanValue("use_routing_api", shouldUseRoutingApi());
            paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
            paramPreferencesWrapper.setPreferenceStringValue("sip_audio_mode", guessInCallMode());
        }
        if (paramInt1 < 575)
        {
            paramPreferencesWrapper.setPreferenceBooleanValue("set_audio_generate_tone", needToneWorkaround());
            if (paramInt1 > 0) {
                paramPreferencesWrapper.setPreferenceBooleanValue("has_already_setup_service", true);
            }
            paramPreferencesWrapper.setPreferenceBooleanValue("enable_qos", true);
            if (Build.PRODUCT.equalsIgnoreCase("htc_supersonic"))
            {
                paramPreferencesWrapper.setPreferenceFloatValue("snd_mic_level", 0.5F);
                paramPreferencesWrapper.setPreferenceFloatValue("snd_speaker_level", 1.5F);
                paramPreferencesWrapper.setPreferenceBooleanValue("use_routing_api", true);
            }
            paramPreferencesWrapper.setPreferenceBooleanValue("keep_awake_incall", needPspWorkaround());
            if (Build.PRODUCT.equalsIgnoreCase("SPH-M900")) {
                paramPreferencesWrapper.setPreferenceBooleanValue("invert_proximity_sensor", true);
            }
        }
        if (paramInt1 < 591) {
            resetCodecsSettings(paramPreferencesWrapper);
        }
        if (paramInt1 < 596) {
            paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
        }
        if (paramInt1 < 613) {
            resetCodecsSettings(paramPreferencesWrapper);
        }
        if (paramInt1 < 704) {
            paramPreferencesWrapper.setPreferenceBooleanValue("use_sgs_call_hack", needSGSWorkaround());
        }
        if (paramInt1 < 794)
        {
            paramPreferencesWrapper.setPreferenceStringValue("micro_source", getDefaultMicroSource());
            paramPreferencesWrapper.setPreferenceStringValue("snd_clock_rate", getDefaultFrequency());
            paramPreferencesWrapper.setPreferenceBooleanValue("keep_awake_incall", needPspWorkaround());
        }
        if (paramInt1 < 814)
        {
            paramPreferencesWrapper.setPreferenceStringValue("network_tcp_transport_port", "0");
            paramPreferencesWrapper.setPreferenceStringValue("network_udp_transport_port", "0");
            paramPreferencesWrapper.setPreferenceStringValue("network_tls_transport_port", "0");
        }
        if (paramInt1 < 882)
        {
            paramPreferencesWrapper.setCodecPriority("G7221/16000/1", "wb", "0");
            paramPreferencesWrapper.setCodecPriority("G7221/32000/1", "wb", "0");
        }
        boolean bool;
        if (paramInt1 < 906)
        {
            if (!isTabletScreen(paramPreferencesWrapper.getContext()))
            {
                bool = true;
                paramPreferencesWrapper.setPreferenceBooleanValue("prevent_screen_rotation", bool);
            }
        }
        else
        {
            if ((paramInt1 < 911) && (Build.DEVICE.toUpperCase().startsWith("GT-I9100")))
            {
                paramPreferencesWrapper.setPreferenceStringValue("micro_source", getDefaultMicroSource());
                paramPreferencesWrapper.setPreferenceStringValue("sip_audio_mode", guessInCallMode());
            }
            if (paramInt1 < 915) {
                paramPreferencesWrapper.setPreferenceBooleanValue("keep_awake_incall", needPspWorkaround());
            }
            if (paramInt1 < 939) {
                paramPreferencesWrapper.setPreferenceBooleanValue("do_focus_audio", true);
            }
            if ((paramInt1 < 955) && (Build.DEVICE.toLowerCase().contains("droid2"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_webrtc_hack", true);
            }
            if (paramInt1 < 997)
            {
                paramPreferencesWrapper.setPreferenceBooleanValue("echo_cancellation", true);
                paramPreferencesWrapper.setPreferenceStringValue("echo_mode", "899");
                paramPreferencesWrapper.setCodecPriority("ISAC/16000/1", "wb", "0");
                paramPreferencesWrapper.setCodecPriority("ISAC/32000/1", "wb", "0");
                paramPreferencesWrapper.setCodecPriority("ISAC/16000/1", "nb", "0");
                paramPreferencesWrapper.setCodecPriority("ISAC/32000/1", "nb", "0");
                paramPreferencesWrapper.setCodecPriority("AMR/8000/1", "wb", "0");
                paramPreferencesWrapper.setCodecPriority("AMR/8000/1", "nb", "0");
                paramPreferencesWrapper.setCodecPriority("G7221/16000/1", "nb", "0");
                paramPreferencesWrapper.setCodecPriority("G7221/32000/1", "nb", "0");
            }
            if ((paramInt1 < 1006) && (Build.DEVICE.equalsIgnoreCase("U8100"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
            }
            if ((paramInt1 < 1033) && (Build.PRODUCT.toLowerCase().startsWith("thunder"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
            }
            if ((paramInt1 < 1076) && (Build.DEVICE.toUpperCase().equals("GT-P1010"))) {
                paramPreferencesWrapper.setPreferenceStringValue("snd_clock_rate", getDefaultFrequency());
            }
            if (paramInt1 < 1109)
            {
                paramPreferencesWrapper.setPreferenceStringValue("timer_min_se", "86400");
                paramPreferencesWrapper.setPreferenceStringValue("timer_sess_expires", "86400");
                resetCodecsSettings(paramPreferencesWrapper);
            }
            if ((paramInt1 < 1581) && (needWebRTCImplementation())) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_webrtc_hack", needWebRTCImplementation());
            }
            if ((paramInt1 < 1634) && (Build.PRODUCT.toLowerCase().startsWith("gt-i9003"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("set_audio_generate_tone", needToneWorkaround());
            }
            if ((paramInt1 < 1653) && (!PhoneCapabilityTester.isPhone(paramPreferencesWrapper.getContext()))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("integrate_tel_privileged", true);
            }
            if (paramInt1 < 1688) {
                paramPreferencesWrapper.setPreferenceStringValue("thread_count", "0");
            }
            if (paramInt1 < 1729)
            {
                paramPreferencesWrapper.setPreferenceStringValue("audio_implementation", Integer.toString(getDefaultAudioImplementation()));
                if ((Build.DEVICE.toUpperCase().startsWith("GT-S")) || (Build.PRODUCT.equalsIgnoreCase("U8655")) || (Build.DEVICE.equalsIgnoreCase("joe")))
                {
                    paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
                    paramPreferencesWrapper.setPreferenceBooleanValue("use_routing_api", shouldUseRoutingApi());
                }
            }
            if (paramInt1 < 1752)
            {
                bool = shouldUsePriviledgedIntegration(paramPreferencesWrapper.getContext());
                if (bool)
                {
                    paramPreferencesWrapper.setPreferenceBooleanValue("integrate_tel_privileged", bool);
                    if (bool) {
                        break label1762;
                    }
                    bool = true;
                    label1026:
                    paramPreferencesWrapper.setPreferenceBooleanValue("integrate_with_native_dialer", bool);
                }
            }
            if ((paramInt1 < 1777) && (Build.DEVICE.startsWith("one_touch_990"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("keep_awake_incall", needPspWorkaround());
            }
            if ((paramInt1 < 1798) && (Build.DEVICE.toLowerCase().startsWith("picasso"))) {
                paramPreferencesWrapper.setPreferenceStringValue("audio_implementation", Integer.toString(getDefaultAudioImplementation()));
            }
            if ((paramInt1 < 1834) && (!shouldFocusAudio())) {
                paramPreferencesWrapper.setPreferenceBooleanValue("do_focus_audio", shouldFocusAudio());
            }
            if ((paramInt1 < 1931) && (Build.DEVICE.equalsIgnoreCase("ST25i"))) {
                paramPreferencesWrapper.setPreferenceStringValue("audio_implementation", Integer.toString(getDefaultAudioImplementation()));
            }
            if (paramInt1 < 1943)
            {
                paramPreferencesWrapper.setPreferenceBooleanValue("setup_audio_before_init", shouldSetupAudioBeforeInit());
                if ((Build.DEVICE.toUpperCase().startsWith("GT-")) || (Build.PRODUCT.toUpperCase().startsWith("GT-"))) {
                    paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
                }
            }
            if (paramInt1 < 2010) {
                paramPreferencesWrapper.setPreferenceStringValue("dtmf_press_tone_mode", Integer.toString(2));
            }
            if ((paramInt1 < 2030) && (((Build.MODEL.toUpperCase().startsWith("LG-E720")) && (!isCompatible(9))) || (Build.MODEL.equalsIgnoreCase("XT320")))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
            }
            if ((paramInt1 < 2052) && (Build.DEVICE.equalsIgnoreCase("u8510")) && (isCompatible(10))) {
                paramPreferencesWrapper.setPreferenceStringValue("audio_implementation", Integer.toString(getDefaultAudioImplementation()));
            }
            if ((paramInt1 < 2069) && (Build.DEVICE.toUpperCase().startsWith("ONE_TOUCH_993D"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
            }
            if (paramInt1 < 2081) {
                paramPreferencesWrapper.setPreferenceBooleanValue("disable_rport", false);
            }
            if ((paramInt1 < 2105) && (Build.DEVICE.toLowerCase().startsWith("cayman"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
            }
            if (paramInt1 < 2111)
            {
                paramPreferencesWrapper.setPreferenceStringValue("media_thread_count", "2");
                paramPreferencesWrapper.setPreferenceBooleanValue("has_io_queue", true);
            }
            if (paramInt1 < 2147) {
                paramPreferencesWrapper.setPreferenceStringValue("dscp_rtp_val", "48");
            }
            if ((paramInt1 < 2172) && (Build.DEVICE.toUpperCase().startsWith("MAKO"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
            }
            if (paramInt1 < 2175)
            {
                paramPreferencesWrapper.setCodecPriority("AMR-WB/16000/1", "wb", "0");
                paramPreferencesWrapper.setCodecPriority("AMR-WB/16000/1", "nb", "0");
            }
            if (paramInt1 < 2195) {
                if (getNumCores() <= 1) {
                    break label1767;
                }
            }
        }
        label1762:
        label1767:
        for (String str = "2";; str = "1")
        {
            paramPreferencesWrapper.setPreferenceStringValue("media_thread_count", str);
            if ((paramInt1 < 2202) && (Build.DEVICE.equalsIgnoreCase("U8833"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_webrtc_hack", needWebRTCImplementation());
            }
            if ((paramInt1 < 2254) && (Build.MODEL.equalsIgnoreCase("XT320"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("keep_awake_incall", needPspWorkaround());
            }
            if (paramInt1 < 2297) {
                paramPreferencesWrapper.setPreferenceStringValue("unlocker_type", Integer.toString(0));
            }
            if ((paramInt1 < 2302) && (Build.DEVICE.toUpperCase().startsWith("U8836"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
            }
            if ((paramInt1 < 2348) && (Build.DEVICE.toLowerCase().startsWith("g2")) && (Build.BRAND.toLowerCase().startsWith("lge"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
            }
            if ((paramInt1 < 2418) && (Build.DEVICE.toUpperCase().startsWith("HWU9700"))) {
                paramPreferencesWrapper.setPreferenceBooleanValue("use_mode_api", shouldUseModeApi());
            }
            if ((paramInt1 < 2442) && (Build.DEVICE.toLowerCase().startsWith("rk31sdk"))) {
                paramPreferencesWrapper.setPreferenceStringValue("audio_implementation", Integer.toString(getDefaultAudioImplementation()));
            }
            paramPreferencesWrapper.endEditing();
            return;
            bool = false;
            break;
            bool = false;
            break label1026;
        }
    }

    public static boolean useFlipAnimation()
    {
        return (!Build.BRAND.equalsIgnoreCase("archos")) || (!Build.DEVICE.equalsIgnoreCase("g7a"));
    }
}
