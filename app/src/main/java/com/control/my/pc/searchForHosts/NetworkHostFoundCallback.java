package com.control.my.pc.searchForHosts;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by Itzik on 20/12/2016.
 */
public interface NetworkHostFoundCallback
{
    void onNetworksFound(ArrayList<InetAddress> inetAddresses );
}
