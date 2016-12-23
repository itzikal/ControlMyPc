package com.common.helpers;

/**
 * Created by Itzik on 23/12/2016.
 */

public final class Point
{
    private final int mX;
    private final int mY;

    public Point(int mX, int mY)
    {
        this.mX = mX;
        this.mY = mY;
    }

    public int getX()
    {
        return mX;
    }

    public int getY()
    {
        return mY;
    }

    @Override
    public String toString()
    {
        return "Point{" +
                "mX=" + mX +
                ", mY=" + mY +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (mX != point.mX) return false;
        return mY == point.mY;

    }

    @Override
    public int hashCode()
    {
        int result = mX;
        result = 31 * result + mY;
        return result;
    }
}
