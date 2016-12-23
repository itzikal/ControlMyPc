package com.control.my.pc.activities;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.common.helpers.Point;


/**
 * Created by Itzik on 16/09/2014.
 */
public class TouchPadView extends View
{
    private static final String TAG = TouchPadView.class.getSimpleName();

    private GestureDetector mGestureDetector;
    private Point mCurrentTouchPosition;
    private OnTouchEventListener mTouchEventListener;

    public TouchPadView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    public TouchPadView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public TouchPadView(Context context)
    {
        this(context, null);
    }

    public void setTouchEventListener(OnTouchEventListener touchEventListener)
    {
        mTouchEventListener = touchEventListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.d(TAG, "onTouchEvent");
        if(!isEnabled())
        {
            return false;
        }

        if (mGestureDetector.onTouchEvent(event))
        {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE)
        {
            Point point = calculateMovementDelta((int) event.getX(), (int) event.getY());
            Log.d(TAG, "onTouchEvent() " + point);
            if (mTouchEventListener != null)
            {
                mTouchEventListener.onMove(point);
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setEnabled(boolean isEnabled)
    {
        super.setEnabled(isEnabled);
//        CursorEvent.CursorEventEnum enable;
//        enable = isEnabled ? CursorEvent.CursorEventEnum.Enable : CursorEvent.CursorEventEnum.Disable;
//
//        NewRemoteEvent<CursorEvent> remoteEvent = new NewRemoteEvent<CursorEvent>(new CursorEvent(new Point(), enable));
//        ClientBluetoothManager.getInstance().sendEvent(remoteEvent);
    }

    private Point calculateMovementDelta(int x, int y)
    {
        Point point = new Point(x - mCurrentTouchPosition.getX(), y - mCurrentTouchPosition.getY());
        mCurrentTouchPosition = new Point(x, y);
        return point;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onDown(MotionEvent e)
        {
            Log.d(TAG, "onDown(), Sending mouse position");
            mCurrentTouchPosition = new Point((int) e.getX(), (int) e.getY());
            return true;
        }


        @Override
        public boolean onDoubleTapEvent(MotionEvent e)
        {
            Log.d(TAG, "onDoubleTapEvent(), Double tap");
            if (mTouchEventListener != null)
            {
                mTouchEventListener.onDoubleTap();
            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e)
        {
            Log.d(TAG, "onSingleTapConfirmed(), Single tap");
//            NewRemoteEvent<CursorEvent> remoteEvent = new  NewRemoteEvent<CursorEvent>(new CursorEvent(new Point(0, 0), CursorEvent.CursorEventEnum.Click));
//            ClientBluetoothManager.getInstance().sendEvent(remoteEvent);
            if (mTouchEventListener != null)
            {
                mTouchEventListener.onClick();
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e)
        {
        }
    }
}
