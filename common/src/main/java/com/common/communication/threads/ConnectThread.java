package com.common.communication.threads;


import com.common.communication.Log;
import com.common.communication.interfaces.ICommunicationManager;
import com.common.communication.interfaces.ISocket;

import java.io.IOException;

/**
 * Created by Itzik on 24/08/2014.
 */
public class ConnectThread extends BaseConnectionThread
{
    private static final String TAG = ConnectThread.class.getSimpleName();
    protected final ISocket mSocket;
    private final ICommunicationManager mManager;

    public ConnectThread(ISocket socket, ICommunicationManager manager)
    {
        mSocket = socket;
        mManager = manager;
    }

    public void run()
    {
        setName("ConnectThread: " + TAG);

        try
        {
            doConnect();
        }
        catch (IOException e)
        {
            closeThread();
            mManager.onConnectionFailed();
            Log.e(TAG, "failed to connect(),", e);
            return;
        }

        mManager.onConnectSuccessfully(mSocket);
    }

    protected void doConnect() throws IOException
    {
        mSocket.connect();
    }

    @Override
    public void closeThread()
    {
        try
        {
            if (mSocket != null)
            {
                mSocket.close();
            }
        }
        catch (IOException e)
        {
            Log.e(TAG, "close() of connect socket failed", e);
        }
    }
}
