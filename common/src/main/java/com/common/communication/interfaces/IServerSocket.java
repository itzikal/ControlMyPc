package com.common.communication.interfaces;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * Created by Itzik on 24/08/2014.
 */
public interface IServerSocket
{
    ISocket accept() throws IOException;
    void close() throws IOException;

    void bind(SocketAddress socketAddress) throws IOException;
}
