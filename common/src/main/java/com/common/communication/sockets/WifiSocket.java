package com.common.communication.sockets;

import com.common.communication.interfaces.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Itzik on 24/08/2014.
 */
public class WifiSocket implements ISocket
{

    private Socket mSocket;

    public WifiSocket(Socket socket)
    {
        mSocket = socket;
    }

    @Override
    public void close() throws IOException
    {
        mSocket.close();
    }

    @Override
    public void connect()
    {
        //mSocket.connect(remoteAddress);
    }

    @Override
    public boolean isConnected()
    {
        return mSocket.isConnected();
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        return mSocket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException
    {
        return mSocket.getOutputStream();
    }

    public void connect(String host, int port) throws IOException
    {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
        InetAddress address = inetSocketAddress.getAddress();
        mSocket.connect(inetSocketAddress);
    }

    public void bind(SocketAddress socketAddress) throws IOException
    {
        mSocket.bind(socketAddress);
    }
}
