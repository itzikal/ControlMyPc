package com.common.remoteEvents;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Itzik on 11/09/2014.
 */
public class BaseEvent
{
    @SerializedName("RemoteEventType")
    protected RemoteEventTypeEnum mRemoteEventType;

    protected BaseEvent(RemoteEventTypeEnum remoteEventType)
    {
        mRemoteEventType = remoteEventType;
    }

    public RemoteEventTypeEnum getRemoteEventType()
    {
        return mRemoteEventType;
    }
}
