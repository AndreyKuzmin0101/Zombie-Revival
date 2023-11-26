package ru.kpfu.itis.kuzmin.server;

import ru.kpfu.itis.kuzmin.protocol.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection implements Runnable{

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
            throw new RuntimeException(e);
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
}
