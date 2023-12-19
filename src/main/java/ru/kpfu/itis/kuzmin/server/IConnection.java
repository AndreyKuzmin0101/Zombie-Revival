package ru.kpfu.itis.kuzmin.server;

import ru.kpfu.itis.kuzmin.protocol.Message;

public interface IConnection extends Runnable {
    void sendMessage(Message message);

    Lobby getLobby();

    void setLobby(Lobby lobby);

    String getUsername();

    void setUsername(String username);
    void close();
}
