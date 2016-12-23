package com.control.my.pc.activities;


import com.common.helpers.Point;

/**
 * Created by Itzik on 23/12/2016.
 */
public interface OnTouchEventListener
{
    void onMove(Point point);

    void onClick();

    void onDoubleTap();
}
