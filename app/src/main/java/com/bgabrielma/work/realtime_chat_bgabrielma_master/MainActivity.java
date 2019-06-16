package com.bgabrielma.work.realtime_chat_bgabrielma_master;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    EditText chat;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.recycler);
        this.adapter = new ChatAdapter();
        this.recyclerView.setAdapter(adapter);
        this.manager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(manager);
        this.chat = findViewById(R.id.chat);

        try {
            mSocket = IO.socket(BuildConfig.SOCKET_IP);
        }
        catch (URISyntaxException e) {
            Log.v("AvisActivity", "error connecting to socket");
        }

        Log.v("AvisActivity", "try to connect");
        mSocket.connect();
        Log.v("AvisActivity", "connection sucessful");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }
}
