package ru.kpfu.itis.kuzmin.server;

import ru.kpfu.itis.kuzmin.level.Level;
import ru.kpfu.itis.kuzmin.protocol.Message;

public interface ILobby extends Runnable {
    void startGame();

    void stopGame(byte reason);

    void forwardMessage(Message message, Connection connection);

    void sendMessage(Message message);

    void setPlayer2(Connection connection);

    Connection getPlayer2(Connection connection);

    void leave(Connection connection);

    boolean isFull();

    int getId();

    void setId(int id);

    Level getLevel();

}
