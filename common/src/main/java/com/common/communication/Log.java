package com.common.communication;

import java.io.IOException;

/**
 * Created by Itzik on 16/12/2016.
 */
public class Log
{
    public static void d(String logtag, String message)
    {
        System.out.println(logtag +"| "+ message);
    }

    public static void e(String tag, String s, IOException e)
    {
        System.out.println(tag +"| "+ s);
        e.printStackTrace();
    }
    public static void e(String tag, String s)
    {
        System.out.println(tag +"| "+ s);
    }

    public static void i(String tag, String s)
    {
        System.out.println(tag +"| "+ s);
    }
}
