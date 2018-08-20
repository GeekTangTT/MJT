package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class SpeakerphoneOnEvent
{
    private boolean isOn;

    public SpeakerphoneOnEvent(boolean paramBoolean)
    {
        this.isOn = paramBoolean;
    }

    public static void post(SpeakerphoneOnEvent paramSpeakerphoneOnEvent)
    {
        EventBusUtil.post(paramSpeakerphoneOnEvent);
    }

    public boolean isOn()
    {
        return this.isOn;
    }
}

