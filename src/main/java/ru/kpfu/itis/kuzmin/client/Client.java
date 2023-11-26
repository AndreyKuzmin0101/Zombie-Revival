package ru.kpfu.itis.kuzmin.client;

import ru.kpfu.itis.kuzmin.AppClient;
import ru.kpfu.itis.kuzmin.Game;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.Teammate;
import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;
import ru.kpfu.itis.kuzmin.model.role.Engineer;
import ru.kpfu.itis.kuzmin.model.role.Role;
import ru.kpfu.itis.kuzmin.model.role.Shooter;
import ru.kpfu.itis.kuzmin.protocol.Message;
import ru.kpfu.itis.kuzmin.view.LevelView;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class Client implements IClient{
    private AppClient appClient;
    private Socket socket;
    private final InetAddress host;
    private final int port;
    private ClientThread thread;
    private Game game;

    public Client(InetAddress host, int port, AppClient appClient) {
        this.host = host;
        this.port = port;
        this.appClient = appClient;
    }
    @Override
    public void start() {
        connect();
        BufferedInputStream input;
        BufferedOutputStream output;
        try {
            input = new BufferedInputStream(socket.getInputStream());
            output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        thread = new ClientThread(input, output, this);
        new Thread(thread).start();
    }
    @Override
    public void connect() {
        try{
            socket = new Socket(host, port);
        }
        catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void startGame(Role role) throws IOException {
        Role teammateRole;
        if (role.getRoleCode() == Role.SHOOTER) teammateRole = new Engineer();
        else teammateRole = new Shooter();

        Player player = new Player(this, role, role.getDefaultWeapon());
        World world = new World();
        Teammate teammate = new Teammate(teammateRole, teammateRole.getDefaultWeapon());

        this.game = new Game(1, world, player, teammate);
        this.game.initController();

        appClient.setView(new LevelView(player));
    }

    @Override
    public void finishGame() {
        this.game = null;
    }



    public void handleMessage(Message message) throws IOException {
        if (message.getType() == Message.START_GAME) {
            byte[] data = message.getData();
            if(data[0] == 0) {
                startGame(new Shooter());
            } else {
                startGame(new Engineer());
            }
        } else if (message.getType() == Message.MOVE) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(16).put(message.getData());
            byteBuffer.rewind();

            double positionX = byteBuffer.getDouble();
            double positionY = byteBuffer.getDouble();

            game.getTeammate().move(positionX, positionY);
        } else if (message.getType() == Message.SHOT) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(16).put(message.getData());
            byteBuffer.rewind();

            double vectorX = byteBuffer.getDouble();
            double vectorY = byteBuffer.getDouble();

            game.getTeammate().shoot(game.getWorld(), vectorX, vectorY);
        }
    }

    @Override
    public void sendNewPosition(double positionX, double positionY) {
        byte type = Message.MOVE;

        byte[] data = ByteBuffer.allocate(16)
                .putDouble(positionX)
                .putDouble(positionY)
                .array();

        sendMessage(Message.createMessage(type, data));
    }

   @Override
    public void sendShot(double vectorX, double vectorY) {
        byte type = Message.SHOT;

        byte[] data = ByteBuffer.allocate(16)
                .putDouble(vectorX)
                .putDouble(vectorY)
                .array();

        sendMessage(Message.createMessage(type, data));
    }

    private void sendMessage(Message message) {
        try {
            thread.getOutput().write(Message.getBytes(message));
            thread.getOutput().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
