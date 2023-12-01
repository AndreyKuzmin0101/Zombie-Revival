package ru.kpfu.itis.kuzmin.server;

import ru.kpfu.itis.kuzmin.protocol.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server {
    private static final int PORT = 5555;
    private ServerSocket server;
    private Connection client1;
    private Connection client2;
    private Lobby lobby;

    public void start() {
        try {
            server = new ServerSocket(PORT);
        } catch (IOException ex) {
            throw new RuntimeException("Server startup error", ex);
        }

        try {
            Socket s1 = server.accept();
            Socket s2 = server.accept();

            client1 = new Connection(this, s1);
            client2 = new Connection(this, s2);

            lobby = new Lobby(this, client1, client2, 1);


            lobby.startGame();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void handleMessage(Message message, Connection connection) {
        if (message.getType() == Message.MOVE || message.getType() == Message.SHOT) {
            connection.getLobby().forwardMessage(message, connection);
        } else if (message.getType() == Message.ZOMBIE_DIE) {
            int id = ByteBuffer.allocate(4).put(message.getData()).rewind().getInt();

            if (connection.getLobby().getLevel().deleteZombie(id)) {
                lobby.forwardMessage(message, connection);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

}
