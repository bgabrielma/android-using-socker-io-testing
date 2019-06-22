package com.bgabrielma.work.realtime_chat_bgabrielma_master;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bgabrielma.work.tasks.SocketBuild;
import com.github.nkzawa.emitter.Emitter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    protected SocketBuild socketBuild;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    EditText chat;
    private ArrayList<String> chat_data = new ArrayList<String>();
    private ArrayList<String> remetente = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.recycler);
        this.adapter = new ChatAdapter(chat_data, remetente);
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
        socketBuild.addOn("message", handling);
        socketBuild.startTaskConnection();
    }

    public Emitter.Listener handling = args -> runOnUiThread(() -> addMessage(args[0].toString(), null));

    public void send(View view) {
        sendMessage();
    }

    public void sendMessage() {
        String message = chat.getText().toString().trim();
        chat.setText("");
        addMessage(message, "me");
        this.socketBuild.getSocket().emit("message",  message);
    }

    public void addMessage(String message, String remetente) {
        chat_data.add(message);
        this.remetente.add(remetente);
        adapter.notifyItemInserted(chat_data.size() - 1);
        recyclerView.smoothScrollToPosition(chat_data.size() - 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.socketBuild.unsetEvents();
        this.socketBuild.getSocket().close();
    }
}
