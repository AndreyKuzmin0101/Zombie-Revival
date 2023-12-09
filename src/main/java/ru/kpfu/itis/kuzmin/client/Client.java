package ru.kpfu.itis.kuzmin.client;

import javafx.application.Platform;
import ru.kpfu.itis.kuzmin.AppClient;
import ru.kpfu.itis.kuzmin.Game;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.contoller.LevelResultController;
import ru.kpfu.itis.kuzmin.contoller.LobbyController;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.Teammate;
import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.model.role.Engineer;
import ru.kpfu.itis.kuzmin.model.role.Role;
import ru.kpfu.itis.kuzmin.model.role.Shooter;
import ru.kpfu.itis.kuzmin.model.zombie.Zombie;
import ru.kpfu.itis.kuzmin.protocol.Message;
import ru.kpfu.itis.kuzmin.util.ZombieFactory;
import ru.kpfu.itis.kuzmin.view.LevelView;
import ru.kpfu.itis.kuzmin.view.LobbyView;
import ru.kpfu.itis.kuzmin.view.MainMenuView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


public class Client implements IClient {
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
    public void start(String nickname, Integer code) {
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

        LobbyController.playerNickname = nickname;
        if (code == null) {
            sendMessage(Message.createMessage(Message.CREATE_LOBBY, nickname.getBytes(StandardCharsets.UTF_8)));
        } else {
            sendMessage(Message.createMessage(Message.USERNAME, nickname.getBytes(StandardCharsets.UTF_8)));
            sendMessage(Message.createMessage(Message.JOIN_LOBBY, ByteBuffer.allocate(4).putInt(code).array()));
        }

    }

    public void leaveLobby() {
        sendMessage(Message.createMessage(Message.LEAVE_LOBBY, new byte[0]));
        MainMenuView mainMenuView = new MainMenuView();
        appClient.setView(mainMenuView);
    }

    @Override
    public void connect() {
        try {
            socket = new Socket(host, port);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void startGame(Role role) throws IOException {
        Role teammateRole;
        if (role.getRoleCode() == Role.SHOOTER) teammateRole = new Engineer();
        else teammateRole = new Shooter();

        Player player = new Player(this, role, role.getDefaultWeapon());
        World world = new World(this);
        Teammate teammate = new Teammate(teammateRole, teammateRole.getDefaultWeapon());

        this.game = new Game(1, world, player, teammate);
        this.game.initController();

        appClient.setView(new LevelView(player, game));

    }

    @Override
    public void stopGame(byte result) {
        LevelController.stopGameLogic();
        LevelResultController.showResult(result, appClient.getView());

        this.game = null;
    }


    public void handleMessage(Message message) throws IOException {
        if (message.getType() == Message.CREATE_LOBBY) {
            LobbyController.code = ByteBuffer.allocate(4).put(message.getData()).rewind().getInt();
            LobbyView lobbyView = new LobbyView();
            appClient.setView(lobbyView);
        } else if (message.getType() == Message.USERNAME) {
            LobbyController.teammateNickname = new String(message.getData(), StandardCharsets.UTF_8);
            Platform.runLater(() -> {
                LobbyController.scene = appClient.getView();
            });
            LobbyController.addPlayer();
        } else if (message.getType() == Message.JOIN_LOBBY) {
            if (message.getData().length != 0) {
                LobbyController.code = ByteBuffer.allocate(4).put(message.getData()).rewind().getInt();
                LobbyView lobbyView = new LobbyView();
                appClient.setView(lobbyView);
            } else {
                System.out.println("Лобби с таким кодом не существует, либо оно уже заполнено");
            }
        } else if (message.getType() == Message.LEAVE_LOBBY) {
            LobbyController.removePlayer();
        }
        else if (message.getType() == Message.START_GAME) {
            byte[] data = message.getData();
            if (data[0] == 0) {
                startGame(new Shooter());
            } else {
                startGame(new Engineer());
            }
        }

        if (game != null) handleGameMessage(message);
    }

    private void handleGameMessage(Message message) {
        if (message.getType() == Message.MOVE) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(8).put(message.getData());
            byteBuffer.rewind();

            double positionX = byteBuffer.getFloat();
            double positionY = byteBuffer.getFloat();

            game.getTeammate().move(positionX, positionY);
        } else if (message.getType() == Message.SHOT) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(8).put(message.getData());
            byteBuffer.rewind();

            double vectorX = byteBuffer.getFloat();
            double vectorY = byteBuffer.getFloat();

            game.getTeammate().shoot(game.getWorld(), vectorX, vectorY);
        } else if (message.getType() == Message.SPAWN_ZOMBIE) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(4 + 1 + 8).put(message.getData());
            byteBuffer.rewind();

            int id = byteBuffer.getInt();
            byte type = byteBuffer.get();
            float positionX = byteBuffer.getFloat();
            float positionY = byteBuffer.getFloat();

            ZombieFactory zombieFactory = new ZombieFactory();
            Zombie zombie = zombieFactory.createZombie(id, type, positionX, positionY);

            game.getWorld().addZombie(zombie);

            LevelController.addZombie(zombie.getImage());
        } else if (message.getType() == Message.ZOMBIE_DIE) {
            int id = ByteBuffer.allocate(4).put(message.getData()).rewind().getInt();

            this.game.getWorld().addDeadZombieId(id);
        } else if (message.getType() == Message.LOSE || message.getType() == Message.VICTORY) {
            stopGame(message.getType());
        } else if (message.getType() == Message.CREATE_WALL) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(8).put(message.getData());
            byteBuffer.rewind();
            float positionX = byteBuffer.getFloat();
            float positionY = byteBuffer.getFloat();

            this.game.getWorld().addWall(positionX, positionY);
        } else if (message.getType() == Message.CREATE_TURRET) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(8).put(message.getData());
            byteBuffer.rewind();
            float positionX = byteBuffer.getFloat();
            float positionY = byteBuffer.getFloat();
            this.game.getWorld().addTurret(positionX, positionY);
        }
    }

    @Override
    public void sendNewPosition(float positionX, float positionY) {
        byte type = Message.MOVE;

        byte[] data = ByteBuffer.allocate(8)
                .putFloat(positionX)
                .putFloat(positionY)
                .array();

        sendMessage(Message.createMessage(type, data));
    }

    @Override
    public void sendShot(float vectorX, float vectorY) {
        byte type = Message.SHOT;

        byte[] data = ByteBuffer.allocate(8)
                .putFloat(vectorX)
                .putFloat(vectorY)
                .array();
        sendMessage(Message.createMessage(type, data));
    }

    @Override
    public void sendZombieDie(int id) {
        byte type = Message.ZOMBIE_DIE;

        byte[] data = ByteBuffer.allocate(4).putInt(id).array();

        sendMessage(Message.createMessage(type, data));
    }

    @Override
    public void sendPlayerDie() {
        sendMessage(Message.createMessage(Message.PLAYER_DIE, new byte[0]));
    }

    @Override
    public void sendWall(float positionX, float positionY) {
        byte[] data = ByteBuffer.allocate(8)
                .putFloat(positionX)
                .putFloat(positionY)
                .array();

        sendMessage(Message.createMessage(Message.CREATE_WALL, data));
    }

    @Override
    public void sendTurret(float positionX, float positionY) {
        byte[] data = ByteBuffer.allocate(8)
                .putFloat(positionX)
                .putFloat(positionY)
                .array();

        sendMessage(Message.createMessage(Message.CREATE_TURRET, data));
    }

    public void sendStartGame() {
        sendMessage(Message.createMessage(Message.START_GAME, new byte[0]));
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
