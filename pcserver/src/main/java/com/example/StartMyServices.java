package com.example;

import com.common.communication.Log;
import com.common.communication.interfaces.ConnectionStateChangedListener;
import com.common.communication.managers.WifiCommunicationManager;
import com.common.remoteEvents.IncomingMessagesHandler;

/**
 * Created by Itzik on 17/12/2016.
 */
public class StartMyServices
{

    private static final String TAG = StartMyServices.class.getSimpleName();
    private final WifiCommunicationManager mWifiCommunicationManager = new WifiCommunicationManager();
    private IncomingMessagesHandler mMessagesHandler;

    public void startServices()
    {
        Log.d(TAG, "startServices()");
        mWifiCommunicationManager.startServerAcceptThread();
        mWifiCommunicationManager.registerToConnectionStateChanged(new ConnectionStateChangedListener() {
            @Override
            public void onNone()
            {
            }

            @Override
            public void onLost()
            {

            }

            @Override
            public void onListen()
            {

            }

            @Override
            public void onConnecting()
            {

            }

            @Override
            public void onConnected()
            {
                BotWrapper eventInjectHelper = new BotWrapper();
                eventInjectHelper.startBot();
                mMessagesHandler = new IncomingMessagesHandler(eventInjectHelper);
            }

        });
        mWifiCommunicationManager.registerToMessageReceived((message, socket) -> {
            if (mMessagesHandler != null)
            {
                mMessagesHandler.handleReceivedMessage(message);
            }
        });
    }

    @Override
    protected void finalize() throws Throwable
    {
        Log.d(TAG, "finalize: ");
        try
        {
            mWifiCommunicationManager.closeCommunication();
        }
        finally
        {
            super.finalize();
        }
    }
}
