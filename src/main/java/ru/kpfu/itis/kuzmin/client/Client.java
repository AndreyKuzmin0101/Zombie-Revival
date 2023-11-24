package ru.kpfu.itis.kuzmin.client;

import ru.kpfu.itis.kuzmin.Game;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.Teammate;
import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.model.role.Engineer;
import ru.kpfu.itis.kuzmin.model.role.Role;
import ru.kpfu.itis.kuzmin.model.role.Shooter;
import ru.kpfu.itis.kuzmin.protocol.Message;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


// Заглушка под клиент
public class Client implements IClient{

    private Socket socket;
    private final InetAddress host;
    private final int port;
    private ClientThread thread;
    private Game game;

    public Client(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }
    @Override
    public void start() {
        connect();
        BufferedReader input;
        BufferedWriter output;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        thread = new ClientThread(input, output);
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
    public Player startGame(Message message) {
        // парсинг message

        Role playerRole = new Shooter();
        Role teammateRole = new Engineer();

        Player player = new Player(playerRole, playerRole.getDefaultWeapon());
        World world = new World();
        Teammate teammate = new Teammate(teammateRole, teammateRole.getDefaultWeapon());


        this.game = new Game(1, world, player, teammate);
        this.game.initController();

        return player;
    }

    @Override
    public void finishGame() {
        this.game = null;
    }



    @Override
    public void sendMessage(Message message) {
        // Отправка данных
    }

    private static class ClientThread implements Runnable{

        private BufferedReader input;
        private BufferedWriter output;

        public ClientThread(BufferedReader input, BufferedWriter output) {
            this.input = input;
            this.output = output;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String message = input.readLine();

                    // Обработка принятых данных
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public BufferedWriter getOutput() {
            return output;
        }
    }
}
