package com.olym.mjt.module.message.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.database.dao.ChatMessageDao;
import com.olym.mjt.databean.bean.User;
import com.olym.mjt.module.MjtApplication;
import com.olym.mjt.module.main.MJTMainActivity;
import com.olym.mjt.module.message.audio.VoicePlayer;
import com.olym.mjt.module.message.audio.VoicePlayer.OnMediaStateChange;
import com.olym.mjt.module.message.audio.VoicePlayer.OnVoicePlayState;
import com.olym.mjt.module.message.event.MessageIsFireEvent;
import com.olym.mjt.module.message.event.MessageRedEvent;
import com.olym.mjt.module.user.login.LoginActivity;
import com.olym.mjt.utils.ChannelUtil;
import com.olym.mjt.utils.LoginHelper;
import com.zhy.autolayout.AutoLinearLayout;

public class MjtChatVoicePreview
        extends AppCompatActivity
        implements VoicePlayer.OnMediaStateChange
{
    public static final String CHAT_TO_MESSAGEPATH = "chat_to_messagepath";
    public static final String CHAT_TO_MESSAGE_CONTENT = "chat_to_message_content";
    public static final String CHAT_TO_MESSAGE_DOMAIN = "message_domain";
    public static final String CHAT_TO_MESSAGE_FIRE = "message_fire";
    public static final String CHAT_TO_MESSAGE_FRIENDID = "message_friendid";
    public static final String CHAT_TO_MESSAGE_ID = "chat_to_message_id";
    public static final String CHAT_TO_MESSAGE_PACKETID = "message_packetid";
    public static final String CHAT_TO_VOICE_LENGTH = "chat_to_voice_length";
    public static final String MESSAGE_GROUP = "message_group";
    public static final String MESSAGE_MYSEND = "message_mysend";
    private AutoLinearLayout burn_voice_message;
    private ImageView chat_voice_anim;
    private ImageView chat_voice_icon;
    private TextView chat_voice_length;
    private String filepath;
    private int fire;
    private String friendid;
    private String ibcdomain;
    private boolean isGroup;
    private boolean isMySend;
    private boolean isRead;
    private long mPlayVoiceId = -1L;
    private VoicePlayer mVoicePlayer;
    private int message_id;
    private String messagecontent;
    private String packetid;
    private String voicelength;
    public WebView webView;

    private void play(String paramString1, String paramString2, int paramInt)
    {
        if (this.mPlayVoiceId == -1L) {
            try
            {
                if (ChannelUtil.isLoadWebView) {
                    playAudio(paramString2, new MjtChatVoicePreview.7(this));
                }
                for (;;)
                {
                    this.mPlayVoiceId = paramInt;
                    this.chat_voice_anim.setVisibility(0);
                    this.chat_voice_icon.setVisibility(8);
                    return;
                    this.mVoicePlayer.play(getApplicationContext(), paramString2, new MjtChatVoicePreview.8(this));
                }
            }
            catch (Exception paramString1)
            {
                for (;;)
                {
                    LogFinalUtils.logForException(paramString1);
                    paramString1.printStackTrace();
                }
            }
        }
        if (this.mPlayVoiceId == paramInt)
        {
            this.mPlayVoiceId = -1L;
            this.chat_voice_anim.setVisibility(8);
            this.chat_voice_icon.setVisibility(0);
            return;
        }
        this.mPlayVoiceId = -1L;
        this.chat_voice_anim.setVisibility(8);
        this.chat_voice_icon.setVisibility(0);
        try
        {
            if (ChannelUtil.isLoadWebView) {
                playAudio(paramString2, new MjtChatVoicePreview.9(this));
            }
            for (;;)
            {
                this.mPlayVoiceId = paramInt;
                this.chat_voice_anim.setVisibility(0);
                this.chat_voice_icon.setVisibility(8);
                return;
                this.mVoicePlayer.play(getApplicationContext(), paramString2, new MjtChatVoicePreview.10(this));
            }
        }
        catch (Exception paramString1)
        {
            for (;;)
            {
                LogFinalUtils.logForException(paramString1);
                paramString1.printStackTrace();
            }
        }
    }

    public void finishPlay()
    {
        this.mPlayVoiceId = -1L;
        this.chat_voice_anim.setVisibility(8);
        this.chat_voice_icon.setVisibility(0);
        if ((!this.isMySend) && (this.isRead) && (this.fire == 1))
        {
            if (!this.isGroup) {
                break label103;
            }
            ChatMessageDao.getInstance().updateMuchMessageRead(MjtApplication.getInstance().getLoginUser().getUserId(), this.friendid, this.packetid, 0, 1, 0, null, null);
            MessageIsFireEvent.post(new MessageIsFireEvent(this.packetid, 1, 0, 0));
        }
        for (;;)
        {
            finish();
            return;
            label103:
            MessageRedEvent.post(new MessageRedEvent(this.packetid, this.friendid, 3, this.ibcdomain, 1));
        }
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        if (ChannelUtil.isLoadWebView)
        {
            setContentView(2131427403);
            this.webView = ((WebView)findViewById(2131231571));
            paramBundle = this.webView.getSettings();
            paramBundle.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            paramBundle.setUseWideViewPort(true);
            paramBundle.setLoadWithOverviewMode(true);
            paramBundle.setSaveFormData(true);
            paramBundle.setJavaScriptCanOpenWindowsAutomatically(true);
            paramBundle.setJavaScriptEnabled(true);
            paramBundle.setGeolocationEnabled(true);
            paramBundle.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
            paramBundle.setDomStorageEnabled(true);
            paramBundle.setSupportZoom(true);
            paramBundle.setBuiltInZoomControls(true);
            this.chat_voice_anim = ((ImageView)findViewById(2131230862));
            this.chat_voice_icon = ((ImageView)findViewById(2131230863));
            this.chat_voice_length = ((TextView)findViewById(2131230864));
            this.burn_voice_message = ((AutoLinearLayout)findViewById(2131230793));
            this.mVoicePlayer = new VoicePlayer();
            this.mVoicePlayer.setOnMediaStateChangeListener(this);
            paramBundle = getIntent().getExtras();
            if (paramBundle != null)
            {
                this.filepath = paramBundle.getString("chat_to_messagepath");
                this.voicelength = paramBundle.getString("chat_to_voice_length");
                this.messagecontent = paramBundle.getString("chat_to_message_content");
                this.message_id = paramBundle.getInt("chat_to_message_id");
                this.packetid = paramBundle.getString("message_packetid");
                this.friendid = paramBundle.getString("message_friendid");
                this.fire = paramBundle.getInt("message_fire");
                this.ibcdomain = paramBundle.getString("message_domain");
                this.isGroup = paramBundle.getBoolean("message_group");
                this.isMySend = paramBundle.getBoolean("message_mysend");
                this.chat_voice_length.setText(this.voicelength);
                this.burn_voice_message.setOnClickListener(new MjtChatVoicePreview.1(this));
            }
            if (!MjtApplication.getInstance().isAppNormal)
            {
                if (!LoginHelper.prepareUser()) {
                    break label407;
                }
                startActivity(new Intent(this, MJTMainActivity.class));
            }
        }
        for (;;)
        {
            if (ChannelUtil.isLoadWebView)
            {
                this.webView.addJavascriptInterface(this, "amrjs");
                this.webView.setWebChromeClient(new WebChromeClient());
                this.webView.setWebViewClient(new MjtChatVoicePreview.2(this));
                this.webView.loadUrl("file:///android_asset/playamr.html");
            }
            return;
            setContentView(2131427402);
            break;
            label407:
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void onErrorPlay()
    {
        playError();
    }

    public void onFinishPlay()
    {
        finishPlay();
    }

    public void onSecondsChange(int paramInt) {}

    protected void onStop()
    {
        super.onStop();
        reset();
    }

    @JavascriptInterface
    public void playAmrAudioDone()
    {
        runOnUiThread(new MjtChatVoicePreview.3(this));
    }

    @JavascriptInterface
    public void playAmrAudioError()
    {
        runOnUiThread(new MjtChatVoicePreview.4(this));
    }

    public void playAudio(String paramString, VoicePlayer.OnVoicePlayState paramOnVoicePlayState)
    {
        this.webView.post(new MjtChatVoicePreview.5(this, paramString));
    }

    public void playError()
    {
        this.mPlayVoiceId = -1L;
        this.chat_voice_anim.setVisibility(8);
        this.chat_voice_icon.setVisibility(0);
    }

    public void reset()
    {
        if (ChannelUtil.isLoadWebView)
        {
            stopPlayAudio();
            if ((!this.isMySend) && (this.isRead) && (this.fire == 1))
            {
                if (!this.isGroup) {
                    break label106;
                }
                ChatMessageDao.getInstance().updateMuchMessageRead(MjtApplication.getInstance().getLoginUser().getUserId(), this.friendid, this.packetid, 0, 1, 0, null, null);
                MessageIsFireEvent.post(new MessageIsFireEvent(this.packetid, 1, 0, 0));
            }
        }
        for (;;)
        {
            finish();
            return;
            if (this.mVoicePlayer == null) {
                break;
            }
            this.mVoicePlayer.stop();
            break;
            label106:
            MessageRedEvent.post(new MessageRedEvent(this.packetid, this.friendid, 3, this.ibcdomain, 1));
        }
    }

    public void stopPlayAudio()
    {
        this.webView.post(new MjtChatVoicePreview.6(this));
    }
}
