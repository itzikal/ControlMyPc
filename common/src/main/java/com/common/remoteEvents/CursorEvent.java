package com.common.remoteEvents;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.awt.Point;

/**
 * Created by Itzik on 10/09/2014.
 */
public class CursorEvent extends BaseEvent
{
    public enum CursorEventEnum
    {
        Disable,
        Movement,
        Click,
        Enable,
        DoubleClick
    }

    @SerializedName("Event")
    public CursorEventEnum mEvent;

    @SerializedName("CursorMovementDelta")
    private Point mCursorMovementDelta;

    public CursorEvent(Point delta, CursorEventEnum event)
    {
        super(RemoteEventTypeEnum.CursorEvent);
        mEvent = event;
        mCursorMovementDelta = delta;
    }

    public CursorEventEnum getEvent()
    {
        return mEvent;
    }

    public Point getCursorMovementDelta()
    {
        return mCursorMovementDelta;
    }

    public static CursorEvent fromGsonString(String gsonString)
    {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, CursorEvent.class );
    }

    @Override
    public String toString()
    {
        return String.format("Cursor %s event, delta: %s", mEvent, mCursorMovementDelta);
    }
}
