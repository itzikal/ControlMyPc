package com.example;

import com.common.communication.Log;
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
