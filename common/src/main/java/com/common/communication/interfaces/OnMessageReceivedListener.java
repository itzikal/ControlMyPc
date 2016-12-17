package com.common.communication.interfaces;

/**
 * Created by Itzik on 21/08/2014.
 */
public interface OnMessageReceivedListener
{
    void onMessageReceived(String message, ISocket socket);
}
