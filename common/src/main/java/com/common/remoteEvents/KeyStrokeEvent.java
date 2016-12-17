package com.common.remoteEvents;

import com.google.gson.Gson;

/**
 * Created by Itzik on 28/08/2014.
 */
public class KeyStrokeEvent extends BaseEvent
{
    private int KeyCode;
    private int Unicode;

    public KeyStrokeEvent(int keyCode, int unicodeChar)
    {
        super(RemoteEventTypeEnum.KeyStrokeEvent);
        KeyCode = keyCode;
        Unicode = unicodeChar;
    }

    public int getKeyCode()
    {
        return KeyCode;
    }

    public int getCharacter()
    {
        return Unicode;
    }

    public static KeyStrokeEvent fromGsonString(String gsonString)
    {
        Gson gson = new Gson();
        return gson.fromJson(gsonString,KeyStrokeEvent.class );
    }

    public static String toGsonString(KeyStrokeEvent keyStrokeEvent)
    {
        Gson gson = new Gson();
        return gson.toJson(keyStrokeEvent);
    }

    @Override
    public String toString()
    {
        return "KeyStrokeEvent{" +
                "KeyCode=" + KeyCode +
                ", Unicode=" + Unicode +
                '}';
    }
}
