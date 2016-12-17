package com.common.remoteEvents;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Itzik on 11/09/2014.
 */
public class NewRemoteEvent<T extends BaseEvent>
{
    @SerializedName("Event")
    private T mEvent;

    @SerializedName("RemoteEventType")
    private RemoteEventTypeEnum mRemoteEventType;

    public NewRemoteEvent(T mEvent)
    {
        this.mEvent = mEvent;
        mRemoteEventType = mEvent.getRemoteEventType();
    }

    public String toGsonString()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public T getEvent()
    {
        return mEvent;
    }
    public RemoteEventTypeEnum getRemoteEventType()
    {
        return mRemoteEventType;
    }
}
