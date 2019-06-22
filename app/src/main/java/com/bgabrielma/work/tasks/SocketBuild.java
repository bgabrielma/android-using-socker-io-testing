package com.bgabrielma.work.tasks;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.concurrent.ExecutionException;

public final class SocketBuild {
    private Socket socket;
    protected ArrayList<String> customOnSockets = new ArrayList<>();

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
        this.customOnSockets.add(Socket.EVENT_ERROR);
        this.customOnSockets.add(Socket.EVENT_CONNECT_ERROR);
        this.customOnSockets.add(Socket.EVENT_RECONNECT_ERROR);
    }

    public void setOnTimeOutListener(@NotNull Emitter.Listener timeOutListener) {
        this.socket.on(Socket.EVENT_RECONNECT_ERROR, timeOutListener);
        this.socket.on(Socket.EVENT_RECONNECT_FAILED, timeOutListener);
        this.customOnSockets.add(Socket.EVENT_RECONNECT_ERROR);
        this.customOnSockets.add(Socket.EVENT_RECONNECT_FAILED);
    }

    public void addOn(String eventName, Emitter.Listener listener) {
        try {
            for(String names : this.customOnSockets) {
                if(names.equals(eventName))
                    throw new DuplicateFormatFlagsException("Event " + eventName + "has already been register.");
            }
            // Register socket custom event
            this.socket.on(eventName, listener);
        }
        catch (DuplicateFormatFlagsException ex) {
            Log.e("Error", ex.getMessage());
        }
    }

    public void unsetEvents() {
        for(String names: this.customOnSockets) {
            this.socket.off(names);
        }
    }

    public void startTaskConnection() {
        if(!this.socket.connected())
            new SocketTask(this.socket).execute();
    }

    public Socket getSocket() {
        return this.socket;
    }
}
