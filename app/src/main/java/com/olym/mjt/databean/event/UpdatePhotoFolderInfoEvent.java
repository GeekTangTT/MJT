package com.olym.mjt.databean.event;

import com.olym.mjt.utils.EventBusUtil;

public class UpdatePhotoFolderInfoEvent
{
    public static void post(UpdatePhotoFolderInfoEvent paramUpdatePhotoFolderInfoEvent)
    {
        EventBusUtil.post(paramUpdatePhotoFolderInfoEvent);
    }
}

