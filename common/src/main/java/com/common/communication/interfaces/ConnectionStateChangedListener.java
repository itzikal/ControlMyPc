package com.common.communication.interfaces;

/**
 * Created by Itzik on 21/08/2014.
 */
public interface ConnectionStateChangedListener
{
    void onNone();
    void onLost();
    void onListen();
    void onConnecting();
    void onConnected();
}
