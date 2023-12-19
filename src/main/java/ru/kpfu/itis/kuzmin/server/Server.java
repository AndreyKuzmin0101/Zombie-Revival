package ru.kpfu.itis.kuzmin.server;

import ru.kpfu.itis.kuzmin.protocol.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Server implements IServer{
    private static final int PORT = 5555;
    private ServerSocket server;
    private ArrayList<Connection> connections = new ArrayList<>();
    private ArrayList<Lobby> lobbies = new ArrayList<>();

    @Override
    public void start() {
        try {
            server = new ServerSocket(PORT);
        } catch (IOException ex) {
            throw new RuntimeException("Server startup error", ex);
        }
        try {
            while (true) {
                Socket socket = server.accept();
                connections.add(new Connection(this, socket));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void handleMessage(Message message, Connection connection) {
        if (message.getType() == Message.CREATE_LOBBY) {
            connection.setUsername(new String(message.getData()));

            int id = Lobby.getRandomCode();
            while ( (findLobby(id)) != null) {
                id = Lobby.getRandomCode();
            }

            Lobby lobby = new Lobby(this, connection, id);
            lobbies.add(lobby);
            connection.sendMessage(Message.createMessage(Message.CREATE_LOBBY, ByteBuffer.allocate(4).putInt(lobby.getId()).array()));
        } else if (message.getType() == Message.USERNAME) {
            connection.setUsername(new String(message.getData()));
        } else if (message.getType() == Message.JOIN_LOBBY) {
            int code = ByteBuffer.allocate(4).put(message.getData()).rewind().getInt();

            Lobby lobby;
            if ((lobby = findLobby(code)) != null) {
                if (!lobby.isFull()) {
                    lobby.setPlayer2(connection);
                    connection.setLobby(lobby);
                    connection.sendMessage(Message.createMessage(Message.JOIN_LOBBY, message.getData()));
                    connection.sendMessage(Message.createMessage(Message.USERNAME, lobby.getPlayer2(connection).getUsername().getBytes(StandardCharsets.UTF_8)));
                    lobby.forwardMessage(Message.createMessage(Message.USERNAME, connection.getUsername().getBytes(StandardCharsets.UTF_8)), connection);
                } else {
                    connections.remove(connection);
                    connection.sendMessage(Message.createMessage(Message.JOIN_LOBBY, new byte[0]));
                    connection.close();
                }
            } else {
                connections.remove(connection);
                connection.sendMessage(Message.createMessage(Message.JOIN_LOBBY, new byte[0]));
                connection.close();
            }
        } else if (message.getType() == Message.LEAVE_LOBBY) {
            System.out.println(connection);
            try {
                connection.getLobby().forwardMessage(message, connection);
            } catch (NullPointerException ignored) { }

            connection.getLobby().leave(connection);
            connections.remove(connection);
            connection.close();
        } else if (message.getType() == Message.START_GAME) {
            connection.getLobby().startGame();
        }
        else if (connection.getLobby().getLevel() != null) handleGameMessage(message, connection);
    }

    private void handleGameMessage(Message message, Connection connection) {
        if (message.getType() == Message.MOVE || message.getType() == Message.SHOT || message.getType() == Message.CREATE_WALL
                || message.getType() == Message.CREATE_TURRET) {
            connection.getLobby().forwardMessage(message, connection);
        } else if (message.getType() == Message.ZOMBIE_DIE) {
            int id = ByteBuffer.allocate(4).put(message.getData()).rewind().getInt();

            if (connection.getLobby().getLevel().deleteZombie(id)) {
                connection.getLobby().forwardMessage(message, connection);
            }
        } else if (message.getType() == Message.PLAYER_DIE) {
            connection.getLobby().stopGame(Message.LOSE);
        }
    }

    private Lobby findLobby(int id) {
        for (Lobby lobby : lobbies) {
            if (lobby.getId() == id) return lobby;
        }
        return null;
    }

    @Override
    public void removeConnection(Connection connection) {
        connections.remove(connection);
    }

    @Override
    public void removeLobby(Lobby lobby) {
        lobbies.remove(lobby);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

}
