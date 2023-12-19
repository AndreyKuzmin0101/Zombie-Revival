package ru.kpfu.itis.kuzmin.server;

import ru.kpfu.itis.kuzmin.protocol.Message;

public interface IServer {
    void start();

    void handleMessage(Message message, Connection connection);

    void removeConnection(Connection connection);

    void removeLobby(Lobby lobby);
}
