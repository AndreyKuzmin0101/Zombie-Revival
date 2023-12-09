package ru.kpfu.itis.kuzmin.client;

import ru.kpfu.itis.kuzmin.model.role.Role;

import java.io.IOException;

public interface IClient {


    void start(String nickname, Integer code);

    void connect();


    void startGame(Role role) throws IOException;

    void stopGame(byte result);

    void sendNewPosition(float positionX, float positionY);

    void sendShot(float vectorX, float vectorY);

    void sendZombieDie(int id);

    void sendPlayerDie();

    void sendWall(float positionX, float positionY);

    void sendTurret(float positionX, float positionY);
}