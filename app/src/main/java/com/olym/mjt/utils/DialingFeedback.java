package com.olym.mjt.utils;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Vibrator;
import com.lc.methodex.LogFinalUtils;
import com.olym.mjt.pjsip.sip.utils.PreferencesWrapper;
import java.util.Timer;
import java.util.TimerTask;

public class DialingFeedback
{
    private static final int HAPTIC_LENGTH_MS = 30;
    private static final int TONE_LENGTH_MS = 150;
    private static final int TONE_RELATIVE_VOLUME = 40;
    private Activity context;
    private boolean dialPressTone = false;
    private boolean dialPressVibrate = false;
    private boolean inCall;
    private PreferencesWrapper prefsWrapper;
    private int ringerMode;
    private ToneGenerator toneGenerator = null;
    private Object toneGeneratorLock = new Object();
    private int toneStream;
    private Timer toneTimer = null;
    private Vibrator vibrator = null;

    public DialingFeedback(Activity paramActivity, boolean paramBoolean)
    {
        this.context = paramActivity;
        this.inCall = paramBoolean;
        if (paramBoolean) {}
        for (;;)
        {
            this.toneStream = i;
            this.prefsWrapper = new PreferencesWrapper(paramActivity);
            return;
            i = 3;
        }
    }

    public void giveFeedback(int paramInt)
    {
        switch (this.ringerMode)
        {
        }
        do
        {
            do
            {
                return;
                if (this.dialPressVibrate) {
                    this.vibrator.vibrate(30L);
                }
            } while (!this.dialPressTone);
            new ThreadedTonePlay(paramInt).start();
            return;
        } while (!this.dialPressVibrate);
        this.vibrator.vibrate(30L);
    }

    public void hapticFeedback()
    {
        if ((this.dialPressVibrate) && (this.ringerMode != 0)) {
            this.vibrator.vibrate(30L);
        }
    }

    public void pause()
    {
        synchronized (this.toneGeneratorLock)
        {
            if (this.toneGenerator != null)
            {
                this.toneGenerator.stopTone();
                this.toneGenerator.release();
                this.toneGenerator = null;
            }
            if (this.toneTimer != null)
            {
                this.toneTimer.cancel();
                this.toneTimer.purge();
                this.toneTimer = null;
            }
            return;
        }
    }

    public void resume()
    {
        this.dialPressTone = this.prefsWrapper.dialPressTone(this.inCall);
        this.dialPressVibrate = this.prefsWrapper.dialPressVibrate();
        if (this.dialPressTone) {}
        for (;;)
        {
            synchronized (this.toneGeneratorLock)
            {
                if (this.toneTimer == null) {
                    this.toneTimer = new Timer("Dialtone-timer");
                }
                ToneGenerator localToneGenerator = this.toneGenerator;
                if (localToneGenerator == null) {}
                try
                {
                    this.toneGenerator = new ToneGenerator(this.toneStream, 40);
                    if (!this.inCall) {
                        this.context.setVolumeControlStream(this.toneStream);
                    }
                    if (!this.dialPressVibrate) {
                        break label187;
                    }
                    if (this.vibrator == null) {
                        this.vibrator = ((Vibrator)this.context.getSystemService("vibrator"));
                    }
                    this.ringerMode = ((AudioManager)this.context.getSystemService("audio")).getRingerMode();
                    return;
                }
                catch (RuntimeException localRuntimeException)
                {
                    this.toneGenerator = null;
                    LogFinalUtils.logForException(localRuntimeException);
                    continue;
                }
            }
            this.toneTimer = null;
            this.toneGenerator = null;
            continue;
            label187:
            this.vibrator = null;
        }
    }

    class StopTimerTask
            extends TimerTask
    {
        StopTimerTask() {}

        public void run()
        {
            synchronized (DialingFeedback.this.toneGeneratorLock)
            {
                if (DialingFeedback.this.toneGenerator == null) {
                    return;
                }
                DialingFeedback.this.toneGenerator.stopTone();
                return;
            }
        }
    }

    class ThreadedTonePlay
            extends Thread
    {
        private final int tone;

        ThreadedTonePlay(int paramInt)
        {
            this.tone = paramInt;
        }

        public void run()
        {
            synchronized (DialingFeedback.this.toneGeneratorLock)
            {
                if (DialingFeedback.this.toneGenerator == null) {
                    return;
                }
                DialingFeedback.this.toneGenerator.stopTone();
                DialingFeedback.this.toneGenerator.startTone(this.tone);
                DialingFeedback.this.toneTimer.schedule(new DialingFeedback.StopTimerTask(DialingFeedback.this), 150L);
                return;
            }
        }
    }
}
