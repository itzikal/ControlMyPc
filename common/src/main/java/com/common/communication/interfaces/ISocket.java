package com.common.communication.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Itzik on 24/08/2014.
 */
public interface ISocket
{
    void close() throws IOException;
    void connect() throws IOException;
    boolean isConnected();
    InputStream getInputStream() throws IOException;
    OutputStream getOutputStream() throws IOException;
}
