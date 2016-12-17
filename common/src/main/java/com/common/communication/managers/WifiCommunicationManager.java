package com.common.communication.managers;

import com.common.communication.Log;
import com.common.communication.sockets.WifiServerSocket;
import com.common.communication.sockets.WifiSocket;
import com.common.communication.threads.WifiConnectThread;
import com.common.communication.threads.WifiConnectedThread;
import com.common.communication.threads.WifiServerAcceptThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Itzik on 21/08/2014.
 */
public class WifiCommunicationManager extends BaseCommunicationManager
{
    static final int HTTP_SERVER_PORT = 9000;
    private static final String TAG = WifiCommunicationManager.class.getSimpleName();

    @Override
    protected void startConcreteServerAcceptThread()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                Log.d(TAG, "startConcreteServerAcceptThread(), init wifi server thread");
                ServerSocket serverSocket = null;
                try
                {
                    serverSocket = new ServerSocket(HTTP_SERVER_PORT);
                }
                catch (IOException e)
                {
                    Log.e(TAG, "startConcreteServerAcceptThread(), failed", e);
                    onFailedCreatingServerSocket();
                    return;
                }

                mServerAcceptedThread = new WifiServerAcceptThread(new WifiServerSocket(serverSocket), WifiCommunicationManager.this);
                mServerAcceptedThread.start();
            }
        }).start();
    }

    @Override
    protected void startConcreteClientConnectThread(String remoteDevice, String message)
    {
        WifiSocket socket = new WifiSocket(new Socket());
        mClientConnectThread = new WifiConnectThread(socket, this, remoteDevice, HTTP_SERVER_PORT, message);
        mClientConnectThread.start();
    }

    @Override
    protected void startConcreteConnectedThread()
    {
        Log.d(TAG, "startConcreteConnectedThread(), ");
        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new WifiConnectedThread(mSocket, this);
        mConnectedThread.start();
    }
}
