package com.common.communication.threads;

import com.common.communication.interfaces.ICommunicationManager;
import com.common.communication.interfaces.IServerSocket;

/**
 * Created by Itzik on 25/08/2014.
 */
public class WifiServerAcceptThread extends ServerAcceptThread
{
    public WifiServerAcceptThread(IServerSocket serverSocket, ICommunicationManager manager)
    {
        super(serverSocket, manager);
    }

//    @Override
//    protected boolean getTermsOfClosingServerThread()
//    {
//        return true;
//    }
}
