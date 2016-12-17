package com.common.communication.threads;

import com.common.communication.interfaces.ICommunicationManager;
import com.common.communication.interfaces.ISocket;
import com.common.communication.sockets.WifiSocket;

import java.io.IOException;

/**
* Created by Itzik on 21/08/2014.
*/
public class WifiConnectThread extends ConnectThread
{
    private static final String TAG = WifiConnectThread.class.getSimpleName();
    private final String mHost;
    private final int mPort;
    private final String mMessage;

    public WifiConnectThread(ISocket socket, ICommunicationManager manager, String host, int port, String message)
    {
        super(socket, manager);
        mHost = host;
        mPort = port;
        mMessage = message;
    }

    @Override
    protected void doConnect() throws IOException
    {
        WifiSocket socket = (WifiSocket)mSocket;
        if(socket == null) return;

        socket.bind(null);
        socket.connect(mHost, mPort);
    }
}
