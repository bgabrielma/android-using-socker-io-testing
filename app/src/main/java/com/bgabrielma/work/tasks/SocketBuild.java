package com.bgabrielma.work.tasks;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;

public final class SocketBuild {
    private Socket socket;

    public SocketBuild(String ip) {
        try {
            this.socket = IO.socket(ip);
        } catch (URISyntaxException e) {
            Log.e("Socket I/O error", "Error on instance socket object. Invalid IP");
        }
    }

    public void setOnErrorListener(@NotNull Emitter.Listener errorListener) {
        this.socket.on(Socket.EVENT_ERROR, errorListener);
        this.socket.on(Socket.EVENT_CONNECT_ERROR, errorListener);
        this.socket.on(Socket.EVENT_RECONNECT_ERROR, errorListener);
    }

    public void setOnTimeOutListener(@NotNull Emitter.Listener timeOutListener) {
        this.socket.on(Socket.EVENT_RECONNECT_ERROR, timeOutListener);
        this.socket.on(Socket.EVENT_RECONNECT_FAILED, timeOutListener);
    }

    public void startTaskConnection() {
        if(!this.socket.connected())
            new SocketTask(this.socket).execute();
    }

    public Socket getSocket() {
        return this.socket;
    }
}
