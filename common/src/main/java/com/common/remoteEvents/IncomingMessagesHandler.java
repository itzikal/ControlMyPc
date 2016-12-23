package com.common.remoteEvents;


import com.common.communication.Log;
import com.common.enums.CommandType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class IncomingMessagesHandler
{
    private static final String TAG = IncomingMessagesHandler.class.getSimpleName();
    private EventInjectHelper mEventInjectHelper;

    public IncomingMessagesHandler(EventInjectHelper eventInjectHelper)
    {
        mEventInjectHelper = eventInjectHelper;
    }


    public void handleReceivedMessage(String message)
    {
        Log.d(TAG, "handleReceivedMessage()," + message);

        Gson gson = new Gson();
        Type fooType = new TypeToken<NewRemoteEvent<BaseEvent>>(){}.getType();
        NewRemoteEvent<BaseEvent> newRemoteEvent = gson.fromJson(message, fooType);

        switch (newRemoteEvent.getRemoteEventType())
        {
            case KeyStrokeEvent:
                Type keyStrokeType = new TypeToken<NewRemoteEvent<KeyStrokeEvent>>(){}.getType();
                KeyStrokeEvent event = ((NewRemoteEvent<KeyStrokeEvent>) gson.fromJson(message, keyStrokeType)).getEvent();
                handleKeyStrokeEvent(event);

                break;
            case CursorEvent:
                Type cursorEventType = new TypeToken<NewRemoteEvent<CursorEvent>>(){}.getType();
                CursorEvent cursorEvent = ((NewRemoteEvent<CursorEvent>) gson.fromJson(message, cursorEventType)).getEvent();
                handleCursorEvent(cursorEvent);
                break;

            case CommandEvent:
                Type CommandEventType = new TypeToken<NewRemoteEvent<CommandEvent>>(){}.getType();
                CommandEvent commandEvent = ((NewRemoteEvent<CommandEvent>) gson.fromJson(message, CommandEventType)).getEvent();
                handleCommandEvent(commandEvent);
                break;
        }
    }

    private void handleCommandEvent(CommandEvent commandEventType)
    {
        CommandType event = commandEventType.getEvent();
        switch (event)
        {
            case HOME:
                mEventInjectHelper.home();
                break;
            case BACK:
                mEventInjectHelper.back();
                break;
            case EXIT:
                mEventInjectHelper.killapp();
                break;
            case MOVE_LEFT:
                mEventInjectHelper.moveLeft();
                break;
            case MOVE_RIGHT:
                mEventInjectHelper.moveRight();
                break;
            case SELECT:
                mEventInjectHelper.select();
                break;
            case CENTER:
                mEventInjectHelper.center();
                break;
        }
    }

    private void handleCursorEvent(CursorEvent event)
    {

        Log.d(TAG, "handleCourserEvent(), " + event);
        switch (event.getEvent())
        {
            case Enable:
                mEventInjectHelper.setEnabled(true);
                break;
            case Disable:
                mEventInjectHelper.setEnabled(false);
                break;
            case Movement:
                mEventInjectHelper.updateCursorPosition(event.getCursorMovementDelta());
                break;
            case Click:
                mEventInjectHelper.invokeCursorClick();
                break;
            case DoubleClick:
                mEventInjectHelper.invokeCursorDoubleClick();
                break;
        }
    }

    private void handleKeyStrokeEvent(KeyStrokeEvent event)
    {
        Log.d(TAG, "handleKeyStrokeEvent(), " + event);
        mEventInjectHelper.invokeKeyStroke(event);
    }
}
