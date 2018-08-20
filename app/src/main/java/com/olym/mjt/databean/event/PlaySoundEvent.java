package com.olym.mjt.databean.event;

import com.olym.mjt.databean.bean.SoundBean;
import com.olym.mjt.utils.EventBusUtil;

public class PlaySoundEvent
{
    private SoundBean soundBean;

    public PlaySoundEvent(SoundBean paramSoundBean)
    {
        this.soundBean = paramSoundBean;
    }

    public static void post(PlaySoundEvent paramPlaySoundEvent)
    {
        EventBusUtil.post(paramPlaySoundEvent);
    }

    public SoundBean getSoundBean()
    {
        return this.soundBean;
    }
}
