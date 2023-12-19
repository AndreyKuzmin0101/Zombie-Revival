package ru.kpfu.itis.kuzmin.server;

import ru.kpfu.itis.kuzmin.level.Level;
import ru.kpfu.itis.kuzmin.protocol.Message;

import java.nio.ByteBuffer;
import java.util.Random;


public class Lobby implements ILobby{
    private Server server;
    private Thread thread;
    private Connection player1;
    private Connection player2;
    private Level level;
    private int id;

    public Lobby(Server server, Connection player1, int id) {
        this.server = server;
        this.id = id;
        this.player1 = player1;
        this.player1.setLobby(this);
        player1.sendMessage(Message.createMessage(Message.CREATE_LOBBY,
                ByteBuffer.allocate(4).putInt(id).array()));
    }

    //TODO: фабрика уровней
    @Override
    public void run() {

        level = new Level(this);
        level.startGame();
    }

    @Override
    public void startGame() {
        this.thread = new Thread(this);

        Message message1 = Message.createMessage(Message.START_GAME, new byte[]{0});
        Message message2 = Message.createMessage(Message.START_GAME, new byte[]{1});
        player1.sendMessage(message1);
        player2.sendMessage(message2);

        this.thread.start();
    }

    @Override
    public void stopGame(byte reason) {
        if (this.level == null) return;

        if (reason == Message.VICTORY) {
            sendMessage(Message.createMessage(Message.VICTORY, new byte[0]));
        } else if (reason == Message.LOSE) {
            sendMessage(Message.createMessage(Message.LOSE, new byte[0]));
            level.stopGame();
        }

        this.thread = null;
        this.level = null;
    }

    @Override
    public void forwardMessage(Message message, Connection connection) {
        if (player1 == connection) {
            player2.sendMessage(message);
        } else {
            player1.sendMessage(message);
        }
    }

    @Override
    public void sendMessage(Message message) {
        player1.sendMessage(message);
        player2.sendMessage(message);
    }

    @Override
    public void setPlayer2(Connection connection) {
        if (player1 == null){
            player1 = connection;
        } else {
            player2 = connection;
        }
    }

    @Override
    public Connection getPlayer2(Connection connection) {
        if (player1 == connection) {
            return player2;
        } else {
            return player1;
        }
    }

    @Override
    public void leave(Connection connection) {
        if (player1 == connection) {
            player1 = null;
        } else if (player2 == connection){
            player2 = null;
        }
        if (player1 == null && player2 == null) {
            server.removeLobby(this);
        }
    }

    @Override
    public boolean isFull() {
        if (player1 != null && player2 != null) {
            return true;
        }
        return false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    public static int getRandomCode() {
        Random random = new Random();
        return random.nextInt(0,100000);
    }
}
