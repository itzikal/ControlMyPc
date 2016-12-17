package com.common.remoteEvents;

import com.google.gson.Gson;

/**
 * Created by Itzik on 28/08/2014.
 */
public class KeyboardType
{
    private int Type;
    private String Before;
    private String After;


    public int getType()
    {
        return Type;
    }

    public void setType(int type)
    {
        Type = type;
    }

    public String getBefore()
    {
        return Before;
    }

    public void setBefore(String before)
    {
        Before = before;
    }

    public String getAfter()
    {
        return After;
    }

    public void setAfter(String after)
    {
        After = after;
    }

    public String toGsonString()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static KeyboardType fromGsonString(String gsonString)
    {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, KeyboardType.class);
    }
}
