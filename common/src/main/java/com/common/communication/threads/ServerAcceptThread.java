package com.common.communication.threads;

import com.common.communication.Log;
import com.common.communication.enums.ConnectionStateEnum;
import com.common.communication.interfaces.ICommunicationManager;
import com.common.communication.interfaces.IServerSocket;
import com.common.communication.interfaces.ISocket;

import java.io.IOException;

/**
 * This thread runs while listening for incoming connections. It behaves
 * like a server-side client. It runs until a connection is accepted
 * (or until cancelled).
 */
public class ServerAcceptThread extends BaseConnectionThread
{
    private static final String TAG = ServerAcceptThread.class.getSimpleName();
    private final IServerSocket mServerSocket;
    private final ICommunicationManager mManager;

    public ServerAcceptThread(IServerSocket  serverSocket, ICommunicationManager manager)
    {
        mManager = manager;
        mServerSocket = serverSocket;
    }

    public void run()
    {
        if (mServerSocket == null)
        {
            Log.e(TAG, "run(): Failed, server socket didn't created.");
            return;
        }

        Log.d(TAG, " Begin server accept thread");
        setName(TAG);

        ISocket socket = null;

        // Listen to the server socket if we're not connected
        while (getTermsOfClosingServerThread())

        {
            try
            {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                socket = mServerSocket.accept();
            }
            catch (IOException e)
            {
                Log.e(TAG, "Socket accept() failed", e);
                mManager.onConnectionLost();
                break;
            }

            // If a connection was accepted
            if (socket != null)
            {
                mManager.onConnectSuccessfully(socket);
            }
        }
        Log.i(TAG, "Server Accept Thread finished");

    }

    protected boolean getTermsOfClosingServerThread()
    {
        return mManager.getConnectionState() != ConnectionStateEnum.Connected;
    }

    public void cancel()
    {
        try
        {
            if (mServerSocket != null)
            {
                mServerSocket.close();
            }
        }
        catch (IOException e)
        {
            Log.e(TAG, "Socket Type close() of server failed", e);
        }

    }

    @Override
    public void closeThread()
    {
        cancel();
    }
}
