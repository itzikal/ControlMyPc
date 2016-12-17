package com.common.remoteEvents;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.common.enums.CommandType;

/**
 * Created by Itzik on 18/09/2014.
 */
public class CommandEvent extends BaseEvent
{
    @SerializedName("Event")
    private CommandType mEvent;

    public CommandEvent(CommandType commandType)
    {
        super(RemoteEventTypeEnum.CommandEvent);
        mEvent = commandType;
    }

    public static CommandEvent fromGsonString(String gsonString)
    {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, CommandEvent.class );
    }

    public CommandType getEvent()
    {
        return mEvent;
    }
}
