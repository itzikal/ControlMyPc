package com.common.communication.interfaces;

import com.common.communication.enums.ConnectionStateEnum;

/**
 * Created by Itzik on 21/08/2014.
 */
public interface ICommunicationManager
{
    void onConnectionFailed();
    void onConnectionLost();
    ConnectionStateEnum getConnectionState();
    void onConnectSuccessfully(ISocket socket);
    void onMessageReceived(String message, ISocket socket);
}
