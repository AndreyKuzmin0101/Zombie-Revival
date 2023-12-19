package ru.kpfu.itis.kuzmin.level;

public interface ILevel {
    void startGame();

    void stopGame();

    boolean deleteZombie(int id);
}
