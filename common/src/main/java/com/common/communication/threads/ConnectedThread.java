package com.common.communication.threads;

import com.common.communication.Log;
import com.common.communication.interfaces.ICommunicationManager;
import com.common.communication.interfaces.ISocket;
import com.common.communication.interfaces.SendMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by Itzik on 24/08/2014.
 */
public class ConnectedThread extends BaseConnectionThread implements SendMessage
{
    private static final String TAG = ConnectedThread.class.getSimpleName();
    private final InputStream mInStream;
    private final OutputStream mOutStream;
    private ISocket mSocket;
    private final ICommunicationManager mManager;
    protected boolean mMessageReceived;
    private boolean mConnectionLost = false;

    public ConnectedThread(ISocket socket, ICommunicationManager manager)
    {
        this.mSocket = socket;
        mManager = manager;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try
        {
            tmpIn = mSocket.getInputStream();
            tmpOut = mSocket.getOutputStream();
        }
        catch (IOException e)
        {
            Log.e(TAG, "input/output stream didn't created", e);
        }

        mInStream = tmpIn;
        mOutStream = tmpOut;
    }

    public void run()
    {
        Log.i(TAG, "connected thread started");

        if (mInStream == null)
        {
            Log.e(TAG, "input stream was not created!");
            mManager.onConnectionFailed();
            return;
        }

        mMessageReceived = false;
        while (shellKeepThreadAlive())
        {
            Log.d(TAG, "Connected Thread - keep alive ");
            try
            {
                BufferedReader is = new BufferedReader(new InputStreamReader(mInStream));
                String request = is.readLine();
                if (request == null)
                {
                    onConnectionLost();
                    break;
                }
                mManager.onMessageReceived(request, mSocket);
                mMessageReceived = true;
            }
            catch (IOException e)
            {
                onConnectionLost();
                break;
            }
        }
        closeThread();
        Log.d(TAG, "Connected Thread ended.");
    }

    private void onConnectionLost()
    {
        closeThread();
        mConnectionLost = true;
        Log.e(TAG, "request is null - connecting lost");
        mManager.onConnectionLost();
        return;
    }

    protected boolean shellKeepThreadAlive()
    {
        return !mConnectionLost;
    }

    public void sendMessage(String message)
    {
        PrintWriter os;
        os = new PrintWriter(mOutStream, true);
        os.print(message + "\r\n");
        os.flush();
    }

    @Override
    public void closeThread()
    {
        try
        {
            if (mInStream != null)
            {
                mInStream.close();
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "closeThread: mInStream failed");
        }
        try
        {
            if (mOutStream != null)
            {
                mOutStream.close();
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "closeThread: mOutStream failed");
        }
        try
        {
            if (mSocket != null)
            {

                mSocket.close();
            }
        }
        catch (IOException e)
        {
            Log.e(TAG, "closeThread() mSocket failed", e);
        }
    }


}
