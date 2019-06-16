package com.bgabrielma.work.tasks;

import android.os.AsyncTask;

import com.github.nkzawa.socketio.client.Socket;

public class SocketTask extends AsyncTask<Void, Void, Void> {

    private Socket socket;

    SocketTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    protected Void doInBackground(Void... sockets) {
        socket.connect();
        return null;
    }
}
