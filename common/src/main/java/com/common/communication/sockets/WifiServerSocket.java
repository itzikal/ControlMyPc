package com.common.communication.sockets;

import com.common.communication.interfaces.IServerSocket;
import com.common.communication.interfaces.ISocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Itzik on 24/08/2014.
 */
public class WifiServerSocket implements IServerSocket
{
    private final ServerSocket mServerSocket;

    public WifiServerSocket(ServerSocket serverSocket)
    {
        mServerSocket = serverSocket;
    }

    @Override
    public ISocket accept() throws IOException
    {
        Socket accept = mServerSocket.accept();
        return new WifiSocket(accept);
    }

    @Override
    public void close() throws IOException
    {
        mServerSocket.close();
    }

    @Override
    public void bind(SocketAddress o) throws IOException
    {
        mServerSocket.bind(o);
    }
}
