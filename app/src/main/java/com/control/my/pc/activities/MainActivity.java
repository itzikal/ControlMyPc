package com.control.my.pc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.common.communication.managers.WifiCommunicationManager;
import com.control.my.pc.R;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                WifiCommunicationManager c = new WifiCommunicationManager();
                c.clientConnectToDevice("10.0.0.6", "What the fuck?");
            }
        });
    }
}
