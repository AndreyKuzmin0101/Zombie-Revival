package ru.kpfu.itis.kuzmin.client;

import ru.kpfu.itis.kuzmin.AppClient;
import ru.kpfu.itis.kuzmin.Game;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.Teammate;
import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.model.role.Engineer;
import ru.kpfu.itis.kuzmin.model.role.Role;
import ru.kpfu.itis.kuzmin.model.role.Shooter;
import ru.kpfu.itis.kuzmin.protocol.Message;
import ru.kpfu.itis.kuzmin.view.LevelView;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


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

        Player player = new Player(role, role.getDefaultWeapon());
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

    @Override
    public void sendMessage(Message message) {
        // Отправка данных
    }

    public void handleMessage(Message message) throws IOException {
        if (message.getType() == Message.START_GAME) {
            byte[] data = message.getData();
            if(data[0] == 0) {
                startGame(new Shooter());
            } else {
                startGame(new Engineer());
            }
        }
    }

    private static class ClientThread implements Runnable{

        private BufferedInputStream input;
        private BufferedOutputStream output;
        private Client client;

        public ClientThread(BufferedInputStream input, BufferedOutputStream output, Client client) {
            this.input = input;
            this.output = output;
            this.client = client;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Message message = Message.readMessage(input);
                    client.handleMessage(message);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public BufferedOutputStream getOutput() {
            return output;
        }
    }
}
