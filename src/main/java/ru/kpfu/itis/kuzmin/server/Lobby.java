package ru.kpfu.itis.kuzmin.server;

import ru.kpfu.itis.kuzmin.protocol.Message;


public class Lobby {
    private Server server;
    private Connection player1;
    private Connection player2;


    public Lobby(Server server, Connection player1, Connection player2) {
        this.server = server;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void startGame() {
        Message message1 = Message.createMessage(Message.START_GAME, new byte[]{0});
        Message message2 = Message.createMessage(Message.START_GAME, new byte[]{1});
        player1.sendMessage(message1);
        player2.sendMessage(message2);
    }
}
