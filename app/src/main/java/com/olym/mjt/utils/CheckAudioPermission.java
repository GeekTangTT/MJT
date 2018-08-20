package com.olym.mjt.utils;

import android.media.AudioRecord;
import com.lc.methodex.LogFinalUtils;

public class CheckAudioPermission
{
    public static final int STATE_NO_PERMISSION = -2;
    public static final int STATE_RECORDING = -1;
    public static final int STATE_SUCCESS = 1;

    public static int getRecordState()
    {
        int i = AudioRecord.getMinBufferSize(44100, 16, 2);
        AudioRecord localAudioRecord = new AudioRecord(0, 44100, 16, 2, i * 100);
        short[] arrayOfShort = new short[i];
        try
        {
            localAudioRecord.startRecording();
            Applog.systemOut("------audioRecord1----- " + localAudioRecord.getRecordingState());
            if (localAudioRecord.getRecordingState() != 3)
            {
                if (localAudioRecord != null)
                {
                    localAudioRecord.stop();
                    localAudioRecord.release();
                }
                Applog.systemOut("------audioRecord2----- -1");
                return -1;
            }
        }
        catch (Exception localException)
        {
            LogFinalUtils.logForException(localException);
            Applog.info("----getRecordState--Exception--- " + localException);
            if (localAudioRecord != null) {
                localAudioRecord.release();
            }
            return -2;
        }
        i = localAudioRecord.read(localException, 0, localException.length);
        if (i <= 0)
        {
            if (localAudioRecord != null)
            {
                localAudioRecord.stop();
                localAudioRecord.release();
            }
            Applog.systemOut("------audioRecord3----- " + i);
            return -2;
        }
        if (localAudioRecord != null)
        {
            localAudioRecord.stop();
            localAudioRecord.release();
        }
        return 1;
    }
}
