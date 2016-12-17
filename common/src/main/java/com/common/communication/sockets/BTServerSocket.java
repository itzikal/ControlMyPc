//package com.common.communication.sockets;
//
//import android.bluetooth.BluetoothServerSocket;
//import android.bluetooth.BluetoothSocket;
//
//import com.imagineglasses.remote.common.communication.interfaces.IServerSocket;
//import com.imagineglasses.remote.common.communication.interfaces.ISocket;
//
//import java.io.IOException;
//import java.net.SocketAddress;
//
///**
// * Created by Itzik on 24/08/2014.
// */
//public class BTServerSocket implements IServerSocket
//{
//    private final BluetoothServerSocket mServerSocket;
//
//    public BTServerSocket(BluetoothServerSocket serverSocket)
//    {
//        mServerSocket = serverSocket;
//    }
//
//    @Override
//    public ISocket accept() throws IOException
//    {
//        BluetoothSocket accept = mServerSocket.accept();
//        return new BTSocket(accept);
//    }
//
//    @Override
//    public void close() throws IOException
//    {
//        mServerSocket.close();
//    }
//
//    @Override
//    public void bind(SocketAddress o) throws IOException
//    {
//    }
//}
