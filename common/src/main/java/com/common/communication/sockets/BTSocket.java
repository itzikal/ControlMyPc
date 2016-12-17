//package com.common.communication.sockets;
//
//import android.bluetooth.BluetoothSocket;
//
//import com.imagineglasses.remote.common.communication.interfaces.ISocket;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
///**
// * Created by Itzik on 24/08/2014.
// */
//public class BTSocket implements ISocket
//{
//   private BluetoothSocket mSocket;
//
//
//    public BTSocket(BluetoothSocket socket)
//    {
//        mSocket = socket;
//    }
//
//    @Override
//    public void close() throws IOException
//    {
//        mSocket.close();
//    }
//
//    @Override
//    public void connect() throws IOException
//    {
//        mSocket.connect();
//    }
//
//    @Override
//    public boolean isConnected()
//    {
//        return mSocket.isConnected();
//    }
//
//    @Override
//    public InputStream getInputStream() throws IOException
//    {
//        return mSocket.getInputStream();
//    }
//
//    @Override
//    public OutputStream getOutputStream() throws IOException
//    {
//        return mSocket.getOutputStream();
//    }
//}
//
