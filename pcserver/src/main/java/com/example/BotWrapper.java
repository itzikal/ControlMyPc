package com.example;

import com.common.communication.Log;
import com.common.helpers.Point;
import com.common.remoteEvents.EventInjectHelper;
import com.common.remoteEvents.KeyStrokeEvent;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;

/**
 * Created by Itzik on 23/12/2016.
 */

public class BotWrapper implements EventInjectHelper
{
    private static final String TAG = BotWrapper.class.getSimpleName();
    private Robot mBot;

    public boolean startBot()
    {
        try
        {
            mBot = new Robot();
            return true;
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void home()
    {

    }

    @Override
    public void back()
    {

    }

    @Override
    public void moveLeft()
    {

    }

    @Override
    public void killapp()
    {

    }

    @Override
    public void moveRight()
    {

    }

    @Override
    public void select()
    {

    }

    @Override
    public void center()
    {

    }

    @Override
    public void setEnabled(boolean isEnable)
    {

    }

    @Override
    public void updateCursorPosition(Point cursorMovementDelta)
    {
        PointerInfo a = MouseInfo.getPointerInfo();
        java.awt.Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();
        Log.d(TAG, "updateCursorPosition to " +cursorMovementDelta +", mouse position" +b);
        mBot.mouseMove(x - cursorMovementDelta.getX(), y - cursorMovementDelta.getY());
    }

    @Override
    public void invokeCursorClick()
    {

    }

    @Override
    public void invokeKeyStroke(KeyStrokeEvent event)
    {

    }
}
