package com.common.communication.managers;


import com.common.communication.Log;
import com.common.communication.enums.ConnectionStateEnum;
import com.common.communication.enums.ConnectionType;
import com.common.communication.interfaces.ConnectionStateChangedListener;
import com.common.communication.interfaces.HardwareStateChangeListener;
import com.common.communication.interfaces.ICommunicationManager;
import com.common.communication.interfaces.ISocket;
import com.common.communication.interfaces.OnMessageReceivedListener;
import com.common.communication.interfaces.SendMessage;
import com.common.communication.threads.BaseConnectionThread;
import com.common.communication.threads.ConnectedThread;

/**
 * Created by Itzik on 21/08/2014.
 */
public abstract class BaseCommunicationManager implements ICommunicationManager
{
    protected ConnectionType mConnectionType = ConnectionType.Notset;
    protected ISocket mSocket;
    protected BaseConnectionThread mServerAcceptedThread;
    protected BaseConnectionThread mClientConnectThread;
    protected BaseConnectionThread mConnectedThread;
    protected HardwareStateChangeListener mHardwareStateChangeListener;

    private static final String TAG = BaseCommunicationManager.class.getSimpleName();
    private ConnectionStateEnum mConnectionState = ConnectionStateEnum.None;
    private ConnectionStateChangedListener mCommunicationStateChangedListener;
    private OnMessageReceivedListener mOnMessageReceivedListener;


    protected abstract void startConcreteServerAcceptThread();
    protected abstract void startConcreteClientConnectThread(String remoteDevice, String message);

    public void startServerAcceptThread()
    {
        Log.d(TAG, "startServerAcceptThread(), first stopping all other threads, then activate listen mode.");
        mConnectionType = ConnectionType.Server;
        closeCommunication();
        setConnectionState(ConnectionStateEnum.Listen);
        startConcreteServerAcceptThread();
    }

    public void clientConnectToDevice(String remoteDevice, String message)
    {
        Log.d(TAG, "clientConnectToDevice(), will try to connect to remote device: " + remoteDevice);
        mConnectionType = ConnectionType.Client;
        setConnectionState(ConnectionStateEnum.Connecting);
        startConcreteClientConnectThread(remoteDevice, message);
    }

    public ConnectionStateEnum getConnectionState()
    {
        return mConnectionState;
    }

    public boolean isConnected()
    {
        return mConnectionState == ConnectionStateEnum.Connected;
    }

    public void setHardwareStateChangeListener(HardwareStateChangeListener hardwareStateChangeListener)
    {
        mHardwareStateChangeListener = hardwareStateChangeListener;
    }

    public void sendMessage(String message)
    {
        if (mConnectedThread instanceof SendMessage)
        {
            ((SendMessage) mConnectedThread).sendMessage(message);
        }
    }

    public void closeCommunication()
    {
        Log.d(TAG, "stopping all threads");
        if (mClientConnectThread != null)
        {
            mClientConnectThread.closeThread();
            mClientConnectThread = null;
        }

        if (mConnectedThread != null)
        {
            mConnectedThread.closeThread();
            mConnectedThread = null;
        }

        if (mServerAcceptedThread != null)
        {
            mServerAcceptedThread.closeThread();
            mServerAcceptedThread = null;
        }

        setConnectionState(ConnectionStateEnum.None);
    }

    public void registerToMessageReceived(OnMessageReceivedListener onMessageReceivedListener)
    {
        mOnMessageReceivedListener = onMessageReceivedListener;
    }

    public void registerToConnectionStateChanged(ConnectionStateChangedListener communicationStateChangedListener)
    {
        mCommunicationStateChangedListener = communicationStateChangedListener;
    }

    protected void startConcreteConnectedThread()
    {
        Log.d(TAG, "startConcreteConnectedThread(), ");
        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(mSocket, this);
        mConnectedThread.start();
    }

    protected  void onFailedCreatingServerSocket()
    {
        Log.d(TAG, "onFailedCreatingServerSocket(), Failed to establish connection");
        startServerAcceptThread();
    }

    protected void onConnectionEstablished()
    {
        Log.d(TAG, "onConnectionEstablished(), successfully connected to remote device, starting connected thread");
        setConnectionState(ConnectionStateEnum.Connected);
        startConcreteConnectedThread();
    }


    //region ICommunicationManager
    @Override
    public void onConnectSuccessfully(ISocket socket)
    {
        mSocket = socket;
        onConnectionEstablished();
    }

    @Override
    public void onMessageReceived(String message, ISocket socket)
    {
        invokeMessageReceived(message, socket);
    }

    @Override
    public void onConnectionFailed()
    {
        setConnectionState(ConnectionStateEnum.None);
        closeCommunication();
    }

    @Override
    public void onConnectionLost()
    {
        setConnectionState(ConnectionStateEnum.Lost);
        closeCommunication();
        tryToReStartServerAcceptedThread();
    }
    //endregion

    protected void tryToReStartServerAcceptedThread()
    {
        if(mConnectionType == ConnectionType.Server)
        {
            Log.d(TAG, "tryToReStartServerAcceptedThread(), ");
            startServerAcceptThread();
        }
    }

    private synchronized void setConnectionState(ConnectionStateEnum state)
    {
        Log.d(TAG, "setState() " + mConnectionState.toString() + " -> " + state);
        if (mConnectionState == state)
        {
            return;
        }
        mConnectionState = state;
        invokeCommunicationChanged();
    }

    private void invokeCommunicationChanged()
    {
        if (mCommunicationStateChangedListener != null)
        {
            switch (mConnectionState)
            {
                case Lost:
                    mCommunicationStateChangedListener.onLost();
                    break;
                case None:
                    mCommunicationStateChangedListener.onNone();
                    break;
                case Listen:
                    mCommunicationStateChangedListener.onListen();
                    break;
                case Connecting:
                    mCommunicationStateChangedListener.onConnecting();
                    break;
                case Connected:
                    mCommunicationStateChangedListener.onConnected();
                    break;
            }
        }
    }

    private void invokeMessageReceived(String message, ISocket socket)
    {
        if (mOnMessageReceivedListener != null)
        {
            mOnMessageReceivedListener.onMessageReceived(message, socket);
        }
    }
}
