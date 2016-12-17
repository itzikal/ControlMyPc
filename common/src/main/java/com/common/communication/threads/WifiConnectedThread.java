package com.common.communication.threads;

import com.common.communication.interfaces.ICommunicationManager;
import com.common.communication.interfaces.ISocket;

/**
 * Created by Itzik on 25/08/2014.
 */
public class WifiConnectedThread extends ConnectedThread
{
    private static final String TAG = WifiConnectedThread.class.getSimpleName();

//    @Override
//    protected boolean shellKeepThreadAlive()
//    {
//        return !mMessageReceived;
//    }

    public WifiConnectedThread(ISocket socket, ICommunicationManager manager)
    {
        super(socket, manager);
    }

//    public static void sendResponse(String message, ISocket socket)
//    {
//        PrintWriter printWriter = null;
//        try
//        {
//            printWriter = new PrintWriter(socket.getOutputStream());
//            printWriter.print(message);
//            printWriter.flush();
//            socket.close();
//        }
//        catch (IOException e)
//        {
//            Log.d(TAG, "sendMessage(), failed opening output stream", e);
//        }
//    }
}
