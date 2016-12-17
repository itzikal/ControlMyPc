package com.example;

import com.common.communication.Log;
import com.common.communication.interfaces.ConnectionStateChangedListener;
import com.common.communication.managers.WifiCommunicationManager;

/**
 * Created by Itzik on 17/12/2016.
 */
public class StartMyServices
{

    private static final String TAG = StartMyServices.class.getSimpleName();
    WifiCommunicationManager mWifiCommunicationManager = new WifiCommunicationManager();

    public void startServices()
    {
        Log.d(TAG, "startServices()");
        mWifiCommunicationManager.startServerAcceptThread();
        mWifiCommunicationManager.registerToConnectionStateChanged(new ConnectionStateChangedListener() {
            @Override
            public void onNone()
            {
                mWifiCommunicationManager.startServerAcceptThread();
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
