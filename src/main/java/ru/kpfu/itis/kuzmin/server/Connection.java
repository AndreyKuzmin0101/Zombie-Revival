package ru.kpfu.itis.kuzmin.server;

import ru.kpfu.itis.kuzmin.protocol.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable {

    private Socket socket;
    private Thread thread;
    private Server server;
    private BufferedInputStream input;
    private BufferedOutputStream output;
    private String username;
    private Lobby lobby;

    public Connection(Server server, Socket socket) {
        try {
            this.socket = socket;
            this.server = server;

            input = new BufferedInputStream(socket.getInputStream());
            output = new BufferedOutputStream(socket.getOutputStream());


            this.thread = new Thread(this);
            thread.start();
        } catch (IOException ex) {
            throw new RuntimeException("Connection could not be established", ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = Message.readMessage(input);
                server.handleMessage(message, this);
            }
        } catch (IOException e) {
            if (lobby != null) getLobby().leave(this);

            server.removeConnection(this);
        }
    }

    public void sendMessage(Message message) {
        try {
            output.write(Message.getBytes(message));
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
