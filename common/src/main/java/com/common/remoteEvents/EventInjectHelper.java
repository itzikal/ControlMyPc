package com.common.remoteEvents;


import com.common.helpers.Point;

/**
 * Created by Itzik on 23/12/2016.
 */
public interface EventInjectHelper
{
    void home();

    void back();

    void moveLeft();

    void killapp();

    void moveRight();

    void select();

    void center();

    void setEnabled(boolean isEnable);

    void updateCursorPosition(Point cursorMovementDelta);

    void invokeCursorClick();

    void invokeKeyStroke(KeyStrokeEvent event);
}
