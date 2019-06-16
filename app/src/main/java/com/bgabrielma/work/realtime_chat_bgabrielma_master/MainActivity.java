package com.bgabrielma.work.realtime_chat_bgabrielma_master;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bgabrielma.work.tasks.SocketBuild;
import com.github.nkzawa.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    protected SocketBuild socketBuild;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    EditText chat;

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

        // Interface emitter
        Emitter.Listener listenerExample = args ->
            this.runOnUiThread(() -> Log.e("Socket I/O error", "Cannot connect or reconnect!"));

        socketBuild = new SocketBuild(BuildConfig.SOCKET_IP);
        socketBuild.setOnErrorListener(listenerExample);
        socketBuild.setOnTimeOutListener(listenerExample);
        socketBuild.startTaskConnection();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.socketBuild.getSocket().close();
    }
}
