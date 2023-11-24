package ru.kpfu.itis.kuzmin.client;

import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.protocol.Message;

public interface IClient {
    void connect();
    void sendMessage(Message message);
    void start();
    Player startGame(Message message);
    void finishGame();
}