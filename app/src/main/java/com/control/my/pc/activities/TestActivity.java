package com.control.my.pc.activities;

import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.communication.managers.WifiCommunicationManager;
import com.common.remoteEvents.CursorEvent;
import com.common.remoteEvents.NewRemoteEvent;
import com.control.my.pc.R;
import com.control.my.pc.searchForHosts.NetworkHostFoundCallback;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static com.control.my.pc.R.id.my_ip;

public class TestActivity extends AppCompatActivity
{
    private static final String LOG_TAG = TestActivity.class.getSimpleName();
    private final WifiCommunicationManager mWifiCommunicationManager = new WifiCommunicationManager();
    private int LoopCurrentIP;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                mWifiCommunicationManager.clientConnectToDevice("10.0.0.6", "What the fuck?");
            }
        });
        findViewById(R.id.disconnect).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                mWifiCommunicationManager.closeCommunication();
            }
        });
        findViewById(R.id.send_command).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendCommandEvent();
            }
        });
        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(TestActivity.this));
        final HostListAddapter adapter = new HostListAddapter();
        list.setAdapter(adapter);

        findViewById(R.id.search_hosts).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getConnectedDevices(myIp(), new NetworkHostFoundCallback() {
                    @Override
                    public void onNetworksFound(final ArrayList<InetAddress> inetAddresses)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                adapter.setItems(inetAddresses);
                            }
                        });

                    }
                });
            }
        });

//        ((TextView)findViewById(R.id.my_ip)).setText(myIp() + " "+ myGateWay());
        getNetworkInfo();

    }

    private String myIp()
    {
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }

    private void getNetworkInfo()
    {
        WifiManager wifii = (WifiManager) getSystemService(WIFI_SERVICE);
        DhcpInfo d=wifii.getDhcpInfo();

        String s_dns1="DNS 1: "+ Formatter.formatIpAddress(d.dns1);
        String s_dns2="DNS 2: "+ Formatter.formatIpAddress(d.dns2);
        String s_gateway="Default Gateway: "+ Formatter.formatIpAddress(d.gateway);
        String s_ipAddress="IP Address: "+ Formatter.formatIpAddress(d.ipAddress);
        String s_leaseDuration="Lease Time: "+String.valueOf(d.leaseDuration);
        String s_netmask="Subnet Mask: "+ Formatter.formatIpAddress(d.netmask);
        String s_serverAddress="Server IP: "+ Formatter.formatIpAddress(d.serverAddress);

        //dispaly them
        TextView info = (TextView) findViewById(my_ip);
        info.setText("Network Info\n"+s_dns1+"\n"+s_dns2+"\n"+s_gateway+"\n"+s_ipAddress+"\n"+s_leaseDuration+"\n"+s_netmask+"\n"+s_serverAddress);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mWifiCommunicationManager.closeCommunication();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mWifiCommunicationManager.closeCommunication();
    }

    public void getConnectedDevices(final String YourPhoneIPAddress, final NetworkHostFoundCallback networkHostFound)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                ArrayList < InetAddress > ret = new ArrayList<>();

                LoopCurrentIP = 0;

                String IPAddress = "";
                String[] myIPArray = YourPhoneIPAddress.split("\\.");
                InetAddress currentPingAddr;

                for (int i = 0; i <= 10; i++)
                {
                    try
                    {

                        // build the next IP address
                        currentPingAddr = InetAddress.getByName(myIPArray[0] + "." +
                                myIPArray[1] + "." +
                                myIPArray[2] + "." +
                                Integer.toString(LoopCurrentIP));

                        Log.d(LOG_TAG, "getConnectedDevices() - trying " + currentPingAddr);
                        // 50ms Timeout for the "ping"
                        if (currentPingAddr.isReachable(50))
                        {

                            Log.d(LOG_TAG, "getConnectedDevices(), found host:" + currentPingAddr);
                            ret.add(currentPingAddr);
                        }
                    }
                    catch (UnknownHostException ex)
                    {
                    }
                    catch (IOException ex)
                    {
                    }

                    LoopCurrentIP++;
                }
                networkHostFound.onNetworksFound(ret);
            }
        }).start();
    }
    private void sendCommandEvent()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                NewRemoteEvent<CursorEvent> remoteEvent = new  NewRemoteEvent<>(new CursorEvent(50,50, CursorEvent.CursorEventEnum.Movement));
                mWifiCommunicationManager.sendMessage(remoteEvent.toGsonString());
            }
        }).start();

    }

    class HostListAddapter extends RecyclerView.Adapter<StringViewHolder>
    {
        private ArrayList<InetAddress> mItems = new ArrayList<>();

        public void setItems(ArrayList<InetAddress> items)
        {
            mItems.clear();
            mItems.addAll(items);
            notifyDataSetChanged();
        }
        @Override
        public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.test_list_item, parent, false);

            return new StringViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(StringViewHolder holder, final int position)
        {
            holder.mText.setText(mItems.get(position).getHostAddress());
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v)
             {
                 mWifiCommunicationManager.clientConnectToDevice(mItems.get(position).getHostAddress(), "What the fuck?");
             }
         });
        }

        @Override
        public int getItemCount()
        {
            return mItems.size();
        }
    }
    class StringViewHolder extends RecyclerView.ViewHolder
    {
        TextView mText;

        public StringViewHolder(View v)
        {
            super(v);
            mText = (TextView) v.findViewById(android.R.id.text1);

        }
    }
}
