package com.common.remoteEvents;


import com.common.helpers.Point;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Itzik on 10/09/2014.
 */
public class CursorEvent extends BaseEvent
{
    public CursorEvent(int x, int y, CursorEventEnum movement)
    {
        this(new Point(x, y), movement);
    }

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
