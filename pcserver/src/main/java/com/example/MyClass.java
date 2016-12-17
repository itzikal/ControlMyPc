package com.example;

import com.common.communication.managers.WifiCommunicationManager;

public class MyClass
{
    public static void main(String[] args)
    {
        WifiCommunicationManager x = new WifiCommunicationManager();
        x.startServerAcceptThread();
    }
}

