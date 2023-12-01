package ru.kpfu.itis.kuzmin.server;

import javafx.scene.Parent;
import ru.kpfu.itis.kuzmin.level.Level;
import ru.kpfu.itis.kuzmin.protocol.Message;


public class Lobby implements Runnable{
    private Server server;
    private Thread thread;
    private Connection player1;
    private Connection player2;
    private Level level;
    private int lvl;

    public Lobby(Server server, Connection player1, Connection player2, int lvl) {
        this.server = server;
        this.player1 = player1;
        this.player2 = player2;
        this.lvl = lvl;

        this.player1.setLobby(this);
        this.player2.setLobby(this);

        this.thread = new Thread(this);
        this.thread.start();
    }

    //TODO: фабрика уровней
    @Override
    public void run() {
        level = new Level(this);
        level.startGame();
    }

    public void startGame() {
        Message message1 = Message.createMessage(Message.START_GAME, new byte[]{0});
        Message message2 = Message.createMessage(Message.START_GAME, new byte[]{1});
        player1.sendMessage(message1);
        player2.sendMessage(message2);
    }

    public void forwardMessage(Message message, Connection connection) {
        if (player1 == connection) {
            player2.sendMessage(message);
        } else {
            player1.sendMessage(message);
        }
    }

    public void sendMessage(Message message) {
        player1.sendMessage(message);
        player2.sendMessage(message);
    }

    public Level getLevel() {
        return level;
    }
}
