//package com.common.communication.managers;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothServerSocket;
//import android.bluetooth.BluetoothSocket;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.util.Log;
//
//import com.imagineglasses.remote.common.communication.sockets.BTServerSocket;
//import com.imagineglasses.remote.common.communication.sockets.BTSocket;
//import com.imagineglasses.remote.common.communication.threads.ConnectThread;
//import com.imagineglasses.remote.common.communication.threads.ServerAcceptThread;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Set;
//import java.util.UUID;
//
///**
// * Created by Itzik on 21/08/2014.
// */
//public class BluetoothCommunicationManager extends BaseCommunicationManager
//{
//    private static final String TAG = BluetoothCommunicationManager.class.getSimpleName();
//
//    private static final UUID MY_UUID_SECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//    private static final String NAME_SECURE = "BluetoothManagerSecure";
//    private BluetoothAdapter mBluetoothAdapter;
//
//    @Override
//    protected void startConcreteServerAcceptThread()
//    {
//        if(!isEnabled())
//        {
//            Log.e(TAG, "startConcreteServerAcceptThread(), Bluetooth disabled, can't start AcceptThread");
//            return;
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run()
//            {
//                Log.d(TAG, "startConcreteServerAcceptThread(), init BT server thread");
//                BluetoothServerSocket serverSocket = null;
//                try
//                {
//                    serverSocket =  getBluetoothAdapter().listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE);
//                }
//                catch (IOException e)
//                {
//                    Log.e(TAG, "creations of server socket  failed", e);
//                    onFailedCreatingServerSocket();
//                    return;
//                }
//
//                mServerAcceptedThread = new ServerAcceptThread(new BTServerSocket(serverSocket), BluetoothCommunicationManager.this);
//                mServerAcceptedThread.start();
//            }
//        }).start();
//
//    }
//
//    @Override
//    protected void startConcreteClientConnectThread(String remoteDevice, String message)
//    {
//        Log.d(TAG, "startConcreteClientConnectThread(), connecting to: " + remoteDevice);
//        BluetoothDevice device = getBluetoothAdapter().getRemoteDevice(remoteDevice);
//        getBluetoothAdapter().cancelDiscovery();
//        BluetoothSocket bluetoothSocket;
//        try
//        {
//            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
//        }
//        catch (IOException e)
//        {
//            Log.e(TAG, "Socket create() failed", e);
//            onConnectionLost();
//            return;
//        }
//
//        mClientConnectThread = new ConnectThread(new BTSocket(bluetoothSocket), this);
//        mClientConnectThread.start();
//    }
//
//    @Override
//    public void registerToHardwareStateChanged(Context context)
//    {
//        Log.d(TAG, "registerToBluetoothStateChange()");
//        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
//        context.registerReceiver(mBluetoothStateChangedReceiver, filter);
//    }
//
//    public void unRegisterToHardwareStateChanged(Context context)
//    {
//        Log.d(TAG, "unRegisterToBluetoothStateChange()");
//        context.unregisterReceiver(mBluetoothStateChangedReceiver);
//    }
//
//    public BluetoothAdapter getBluetoothAdapter()
//    {
//        if (mBluetoothAdapter == null)
//        {
//            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        }
//        return mBluetoothAdapter;
//    }
//
//    public boolean isEnabled()
//    {
//        return getBluetoothAdapter().isEnabled();
//    }
//
//    public void enableBluetooth()
//    {
//        if (!isEnabled())
//        {
//            Log.d(TAG, "enableBluetooth(), - try enabling");
//            getBluetoothAdapter().enable();
//        }
//    }
//
//    public void startDiscovery()
//    {
//        getBluetoothAdapter().startDiscovery();
//    }
//
//    public void cancelDiscovery()
//    {
//        getBluetoothAdapter().cancelDiscovery();
//    }
//
//    //region mBluetoothStateChangeReceiver
//    private final BroadcastReceiver mBluetoothStateChangedReceiver = new BroadcastReceiver()
//    {
//        @Override
//        public void onReceive(Context context, Intent intent)
//        {
//            final String action = intent.getAction();
//            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED))
//            {
//                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
//                switch (state)
//                {
//                    case BluetoothAdapter.STATE_OFF:
//                        Log.v(TAG, "onReceive() , ACTION_STATE_CHANGED, bluetooth off");
//                        if(mHardwareStateChangeListener!=null)
//                        {
//                            mHardwareStateChangeListener.onHardwareTurnedOff();
//                        }
//                        closeCommunication();
//                        break;
//                    case BluetoothAdapter.STATE_TURNING_OFF:
//                        closeCommunication();
//                        Log.v(TAG, "onReceive() , ACTION_STATE_CHANGED, bluetooth turning off");
//                        break;
//                    case BluetoothAdapter.STATE_ON:
//                        Log.v(TAG, "onReceive() , ACTION_STATE_CHANGED, bluetooth on");
//                        tryToReStartServerAcceptedThread();
//                        if(mHardwareStateChangeListener!=null)
//                        {
//                            mHardwareStateChangeListener.onHardwareTurnedOn();
//                        }
//                        break;
//                    case BluetoothAdapter.STATE_TURNING_ON:
//                        Log.v(TAG, "onReceive() , ACTION_STATE_CHANGED, bluetooth turning on");
//                        break;
//                }
//            }
//        }
//    };
//    //endregion
//
//    public  ArrayList<BluetoothDevice> getPairedDevices()
//    {
//        ArrayList<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
//        Set<BluetoothDevice> pairedDevices = getBluetoothAdapter().getBondedDevices();
//        for(BluetoothDevice device :pairedDevices)
//        {
//            devices.add(device);
//        }
//        return devices;
//    }
//}
