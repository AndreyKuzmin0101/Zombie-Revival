package ru.kpfu.itis.kuzmin.client;

import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.role.Role;
import ru.kpfu.itis.kuzmin.protocol.Message;

import java.io.IOException;

public interface IClient {
    void connect();
    void sendMessage(Message message);
    void start();
    void startGame(Role role) throws IOException;
    void finishGame();
}